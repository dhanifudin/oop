(function () {
  "use strict";

  // Timer formula: base seconds by difficulty, plus an add-on by question
  // format (reading a code snippet needs more time than a quick recall
  // question). This is the "adjustable timer based on difficulty and
  // format" the app is built around.
  var DIFFICULTY_BASE_SECONDS = { easy: 20, medium: 35, hard: 50 };
  var FORMAT_ADDON_SECONDS = { theory: 0, concept: 10, code: 20 };

  var FORMAT_LABELS = { theory: "Teori", concept: "Konsep", code: "Kode" };
  var DIFFICULTY_LABELS = { easy: "Mudah", medium: "Sedang", hard: "Sulit" };

  var allQuestions = [];
  var session = [];
  var currentIndex = 0;
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
    el.btnStart = document.getElementById("btn-start");

    el.badgeMeeting = document.getElementById("badge-meeting");
    el.badgeFormat = document.getElementById("badge-format");
    el.badgeDifficulty = document.getElementById("badge-difficulty");
    el.progressLabel = document.getElementById("progress-label");

    el.timerBar = document.getElementById("timer-bar");
    el.timerNumber = document.getElementById("timer-number");

    el.questionBody = document.getElementById("question-body");
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

  function shuffle(array) {
    var result = array.slice();
    for (var i = result.length - 1; i > 0; i--) {
      var j = Math.floor(Math.random() * (i + 1));
      var tmp = result[i];
      result[i] = result[j];
      result[j] = tmp;
    }
    return result;
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

  function handleStart() {
    var meetings = getSelectedMeetings();
    if (meetings.length === 0) {
      el.startError.hidden = false;
      el.startError.textContent = "Pilih minimal satu pertemuan.";
      return;
    }

    var pool = allQuestions.filter(function (q) {
      return meetings.indexOf(q.meeting) !== -1;
    });

    if (pool.length === 0) {
      el.startError.hidden = false;
      el.startError.textContent = "Tidak ada soal untuk pilihan ini.";
      return;
    }

    var requestedCount = parseInt(el.inputCount.value, 10) || 20;
    var count = Math.min(requestedCount, pool.length);

    el.startError.hidden = true;
    session = shuffle(pool).slice(0, count);
    currentIndex = 0;
    showScreen("question");
    renderQuestion();
  }

  function renderQuestion() {
    var q = session[currentIndex];
    revealed = false;

    el.badgeMeeting.textContent = "Pertemuan " + q.meeting;

    el.badgeFormat.textContent = FORMAT_LABELS[q.format] || q.format;
    el.badgeFormat.className = "badge badge-format-" + q.format;

    el.badgeDifficulty.textContent = DIFFICULTY_LABELS[q.difficulty] || q.difficulty;
    el.badgeDifficulty.className = "badge badge-difficulty-" + q.difficulty;

    el.progressLabel.textContent = "Soal " + (currentIndex + 1) + "/" + session.length;

    el.questionBody.innerHTML = q.questionHtml;

    el.answerBox.hidden = true;
    el.answerHtml.innerHTML = q.answerHtml;

    el.explanationBox.hidden = true;
    el.explanationHtml.innerHTML = q.explanationHtml;

    el.btnReveal.disabled = false;

    startTimer(computeTotalSeconds(q) * 1000);
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
      } else if (e.key === "+" || e.key === "=") {
        adjustTimer(10);
      } else if (e.key === "-" || e.key === "_") {
        adjustTimer(-10);
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
