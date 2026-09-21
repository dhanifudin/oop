(function () {
  "use strict";

  // Timer formula: base seconds by difficulty, plus an add-on by question
  // format (reading a UML diagram or a code snippet takes longer than a
  // one-line question). This is the "adjustable timer based on difficulty
  // and format" the app is built around. Every question is either code
  // (trace output / compile-runtime judgment) or uml (read a diagram) -
  // both have a single deterministic answer, no open-ended "why" questions.
  var DIFFICULTY_BASE_SECONDS = { easy: 35, medium: 55, hard: 80 };
  var FORMAT_ADDON_SECONDS = { uml: 20, code: 30 };

  // Shown once before the real session so students see how the timer,
  // reveal, and navigation controls behave before anything is scored.
  // Deliberately NOT part of questions.json/the 25-question bank: it asks
  // a plain definition on purpose (something the real bank never does)
  // precisely because it's a UI walkthrough, not an exam question.
  var EXAMPLE_QUESTION = {
    questionHtml: "<p>(Contoh) Apa itu PBO (Pemrograman Berorientasi Objek)?</p>",
    answerHtml: "<p>Ini contoh soal untuk berlatih memakai aplikasi kuis, bukan soal yang dinilai.</p>",
    explanationHtml:
      "<p>Coba tombol <strong>-10s</strong>/<strong>+10s</strong> untuk mengatur waktu, " +
      "<strong>Jeda</strong> untuk menjeda hitung mundur, dan <strong>Tampilkan Jawaban</strong> " +
      "untuk melihat jawaban seperti ini. Soal sesungguhnya TIDAK akan menanyakan definisi seperti " +
      "ini, semuanya berupa kode atau diagram UML dengan jawaban yang pasti. Tekan tombol di kanan " +
      "untuk mulai soal nomor 1.</p>",
  };
  var EXAMPLE_DURATION_SECONDS = 45;

  var allQuestions = [];
  var session = [];
  var pendingSession = [];
  var currentIndex = 0;
  var inExample = false;
  var revealed = false;
  var paused = false;
  var remainingMs = 0;
  var totalMs = 0;
  var tickHandle = null;

  var el = {};

  function cacheElements() {
    el.screenStart = document.getElementById("screen-start");
    el.screenQuestion = document.getElementById("screen-question");
    el.screenEnd = document.getElementById("screen-end");

    el.inputCount = document.getElementById("input-count");
    el.meetingFilters = Array.prototype.slice.call(
      document.querySelectorAll(".meeting-filter")
    );
    el.startError = document.getElementById("start-error");
    el.estimateLabel = document.getElementById("estimate-label");
    el.btnStart = document.getElementById("btn-start");

    el.progressLabel = document.getElementById("progress-label");
    el.navStrip = document.getElementById("nav-strip");

    el.timerBar = document.getElementById("timer-bar");
    el.timerNumber = document.getElementById("timer-number");

    el.questionBody = document.getElementById("question-body");
    el.reasoningHint = document.getElementById("reasoning-hint");
    el.answerBox = document.getElementById("answer-box");
    el.answerHtml = document.getElementById("answer-html");
    el.explanationBox = document.getElementById("explanation-box");
    el.explanationHtml = document.getElementById("explanation-html");

    el.btnReveal = document.getElementById("btn-reveal");
    el.btnNext = document.getElementById("btn-next");
    el.btnPause = document.getElementById("btn-pause");
    el.btnTimerMinus = document.getElementById("btn-timer-minus");
    el.btnTimerPlus = document.getElementById("btn-timer-plus");
    el.btnRestart = document.getElementById("btn-restart");
    el.btnRestartEnd = document.getElementById("btn-restart-end");
  }

  function showScreen(name) {
    el.screenStart.hidden = name !== "start";
    el.screenQuestion.hidden = name !== "question";
    el.screenEnd.hidden = name !== "end";
  }

  function computeTotalSeconds(question) {
    var base = DIFFICULTY_BASE_SECONDS[question.difficulty] || 30;
    var addon = FORMAT_ADDON_SECONDS[question.format] || 0;
    return base + addon;
  }

  function loadQuestions() {
    return fetch("./questions.json").then(function (res) {
      if (!res.ok) {
        throw new Error("Gagal memuat questions.json (status " + res.status + ")");
      }
      return res.json();
    });
  }

  function getSelectedMeetings() {
    return el.meetingFilters
      .filter(function (cb) {
        return cb.checked;
      })
      .map(function (cb) {
        return Number(cb.value);
      });
  }

  function getFilteredPool() {
    var meetings = getSelectedMeetings();
    return allQuestions.filter(function (q) {
      return meetings.indexOf(q.meeting) !== -1;
    });
  }

  function updateEstimate() {
    if (!el.estimateLabel) {
      return;
    }
    if (allQuestions.length === 0) {
      el.estimateLabel.textContent = "";
      return;
    }
    var pool = getFilteredPool();
    if (pool.length === 0) {
      el.estimateLabel.textContent = "Tidak ada soal untuk pilihan ini.";
      return;
    }
    var requestedCount = parseInt(el.inputCount.value, 10) || 0;
    var count = Math.max(0, Math.min(requestedCount, pool.length));
    var poolAvgSeconds =
      pool.reduce(function (sum, q) {
        return sum + computeTotalSeconds(q);
      }, 0) / pool.length;
    var estimatedSeconds = poolAvgSeconds * count;
    var minutes = Math.round(estimatedSeconds / 60);
    el.estimateLabel.textContent =
      "Estimasi durasi: ~" + minutes + " menit untuk " + count + " soal.";
  }

  function handleStart() {
    var meetings = getSelectedMeetings();
    if (meetings.length === 0) {
      el.startError.hidden = false;
      el.startError.textContent = "Pilih minimal satu kelompok soal.";
      return;
    }

    var pool = getFilteredPool();

    if (pool.length === 0) {
      el.startError.hidden = false;
      el.startError.textContent = "Tidak ada soal untuk pilihan ini.";
      return;
    }

    var requestedCount = parseInt(el.inputCount.value, 10) || 48;
    var count = Math.min(requestedCount, pool.length);

    el.startError.hidden = true;
    pendingSession = pool.slice(0, count);
    startExample();
  }

  function startExample() {
    inExample = true;
    showScreen("question");
    renderExampleQuestion();
  }

  function renderExampleQuestion() {
    revealed = false;

    el.progressLabel.textContent = "Contoh soal (latihan, bukan bagian dari 25 soal ujian)";
    el.navStrip.innerHTML = "";

    el.questionBody.innerHTML = EXAMPLE_QUESTION.questionHtml;

    el.reasoningHint.innerHTML = "";
    el.reasoningHint.hidden = true;

    el.answerBox.hidden = true;
    el.answerHtml.innerHTML = EXAMPLE_QUESTION.answerHtml;

    el.explanationBox.hidden = true;
    el.explanationHtml.innerHTML = EXAMPLE_QUESTION.explanationHtml;

    el.btnReveal.disabled = false;
    el.btnNext.textContent = "Mulai Soal Nomor 1";

    startTimer(EXAMPLE_DURATION_SECONDS * 1000);
  }

  function beginRealSession() {
    inExample = false;
    el.btnNext.textContent = "Soal Berikutnya";
    session = pendingSession;
    currentIndex = 0;
    renderQuestion();
  }

  function renderQuestion(opts) {
    opts = opts || {};
    var q = session[currentIndex];
    revealed = false;

    el.progressLabel.textContent =
      "Soal " + (currentIndex + 1) + "/" + session.length + " (" + q.points + " poin)";
    renderNavStrip();

    el.questionBody.innerHTML = q.questionHtml;
    if (q.twoColumnLayout) {
      applyTwoColumnCodeLayout(el.questionBody);
    }

    if (q.reasoningHintHtml) {
      el.reasoningHint.innerHTML = q.reasoningHintHtml;
      el.reasoningHint.hidden = false;
    } else {
      el.reasoningHint.innerHTML = "";
      el.reasoningHint.hidden = true;
    }

    el.answerBox.hidden = true;
    el.answerHtml.innerHTML = q.answerHtml;

    el.explanationBox.hidden = true;
    el.explanationHtml.innerHTML = q.explanationHtml;

    el.btnReveal.disabled = false;

    startTimer(computeTotalSeconds(q) * 1000);

    // Jumping via the navigator opens the question for review: timer
    // visible, but paused until the teacher explicitly resumes it.
    if (opts.startPaused) {
      paused = true;
      el.btnPause.textContent = "Lanjut";
      updateTimerDisplay();
    }
  }

  // Places a question's first two code blocks side by side instead of
  // stacked, so a two-class-plus-driver question can fit one screen
  // height instead of needing the full sum of both blocks' heights.
  function applyTwoColumnCodeLayout(container) {
    var pres = container.querySelectorAll("pre");
    if (pres.length < 2) {
      return;
    }
    var wrapper = document.createElement("div");
    wrapper.className = "two-col-code";
    pres[0].parentNode.insertBefore(wrapper, pres[0]);
    wrapper.appendChild(pres[0]);
    wrapper.appendChild(pres[1]);
  }

  function renderNavStrip() {
    el.navStrip.innerHTML = "";
    session.forEach(function (_q, i) {
      var btn = document.createElement("button");
      btn.type = "button";
      btn.className = "nav-tick" + (i === currentIndex ? " nav-tick-current" : "");
      btn.textContent = String(i + 1);
      btn.title = "Lompat ke soal " + (i + 1) + " (jeda untuk ditinjau)";
      btn.addEventListener("click", function () {
        goToQuestion(i, { startPaused: true });
      });
      el.navStrip.appendChild(btn);
    });
  }

  function goToQuestion(index, opts) {
    if (index < 0 || index >= session.length) {
      return;
    }
    currentIndex = index;
    renderQuestion(opts);
  }

  function startTimer(ms) {
    stopTimer();
    remainingMs = ms;
    totalMs = ms;
    paused = false;
    el.btnPause.textContent = "Jeda";
    updateTimerDisplay();
    tickHandle = setInterval(tick, 100);
  }

  function stopTimer() {
    if (tickHandle) {
      clearInterval(tickHandle);
      tickHandle = null;
    }
  }

  function tick() {
    if (paused || revealed) {
      return;
    }
    remainingMs = Math.max(0, remainingMs - 100);
    updateTimerDisplay();
    if (remainingMs <= 0) {
      next();
    }
  }

  function updateTimerDisplay() {
    var pct = totalMs > 0 ? (remainingMs / totalMs) * 100 : 0;
    el.timerBar.style.width = pct + "%";

    el.timerBar.classList.remove("timer-warn", "timer-danger");
    el.timerNumber.classList.remove("timer-danger", "timer-paused");

    if (pct <= 20) {
      el.timerBar.classList.add("timer-danger");
      el.timerNumber.classList.add("timer-danger");
    } else if (pct <= 50) {
      el.timerBar.classList.add("timer-warn");
    }

    if (paused) {
      el.timerNumber.classList.add("timer-paused");
    }

    var seconds = Math.ceil(remainingMs / 1000);
    el.timerNumber.textContent = remainingMs <= 0 ? "Waktu Habis!" : seconds + "s";
  }

  function togglePause() {
    if (revealed) {
      return;
    }
    paused = !paused;
    el.btnPause.textContent = paused ? "Lanjut" : "Jeda";
    updateTimerDisplay();
  }

  function adjustTimer(deltaSeconds) {
    if (revealed) {
      return;
    }
    var deltaMs = deltaSeconds * 1000;
    remainingMs = Math.max(0, remainingMs + deltaMs);
    if (deltaMs > 0) {
      totalMs += deltaMs;
    }
    updateTimerDisplay();
  }

  function reveal() {
    if (revealed) {
      return;
    }
    revealed = true;
    paused = true;
    el.btnReveal.disabled = true;

    el.answerBox.hidden = false;
    el.explanationBox.hidden = false;
  }

  function next() {
    if (inExample) {
      beginRealSession();
      return;
    }
    currentIndex++;
    if (currentIndex >= session.length) {
      stopTimer();
      showScreen("end");
      return;
    }
    renderQuestion();
  }

  function restartToStart() {
    stopTimer();
    inExample = false;
    showScreen("start");
  }

  function handleSpace() {
    if (el.screenQuestion.hidden) {
      return;
    }
    if (!revealed) {
      reveal();
    } else {
      next();
    }
  }

  function bindEvents() {
    el.btnStart.addEventListener("click", handleStart);
    el.btnReveal.addEventListener("click", reveal);
    el.btnNext.addEventListener("click", next);
    el.btnPause.addEventListener("click", togglePause);
    el.btnTimerMinus.addEventListener("click", function () {
      adjustTimer(-10);
    });
    el.btnTimerPlus.addEventListener("click", function () {
      adjustTimer(10);
    });
    el.btnRestart.addEventListener("click", restartToStart);
    el.btnRestartEnd.addEventListener("click", restartToStart);

    el.inputCount.addEventListener("input", updateEstimate);
    el.meetingFilters.forEach(function (cb) {
      cb.addEventListener("change", updateEstimate);
    });

    document.addEventListener("keydown", function (e) {
      var tag = (e.target.tagName || "").toLowerCase();
      if (tag === "input") {
        return;
      }
      if (e.code === "Space") {
        e.preventDefault();
        handleSpace();
      } else if (e.key === "p" || e.key === "P") {
        if (!el.screenQuestion.hidden) {
          togglePause();
        }
      } else if (e.key === "r" || e.key === "R") {
        restartToStart();
      }
    });
  }

  function init() {
    cacheElements();
    bindEvents();
    showScreen("start");
    loadQuestions()
      .then(function (questions) {
        allQuestions = questions;
        updateEstimate();
      })
      .catch(function (err) {
        el.startError.hidden = false;
        el.startError.textContent =
          "Gagal memuat bank soal. Pastikan server lokal berjalan (npm start). " +
          "Detail: " + err.message;
      });
  }

  document.addEventListener("DOMContentLoaded", init);
})();
