// Builds a printable answer key for the Dosen from quiz/questions/*.md:
// writes quiz/build/answer-key.md, then renders quiz/build/answer-key.pdf with
// pandoc + lualatex (same options as jobsheets/build.sh).
//
// Run via `npm run key`.

const fs = require("fs");
const path = require("path");
const { execFileSync } = require("child_process");
const { readBlocks, listQuestionFiles } = require("./build-questions");

const QUIZ_DIR = path.join(__dirname, "..");
const BUILD_DIR = path.join(QUIZ_DIR, "build");
const MD_FILE = path.join(BUILD_DIR, "answer-key.md");
const PDF_FILE = path.join(BUILD_DIR, "answer-key.pdf");
const HEADER_TEX = path.join(QUIZ_DIR, "..", "jobsheets", "assets", "header.tex");
const KEY_HEADER_TEX = path.join(__dirname, "answer-key-header.tex");

const DIFFICULTY_LABEL = { easy: "mudah", medium: "sedang", hard: "sulit" };

// Question bodies reference images as ../assets/uml/x.png (relative to
// questions/); cap their width so tall diagrams fit on a page.
function sizeImages(markdown) {
  return markdown.replace(/(!\[[^\]]*\]\([^)]+\))(?!\{)/g, "$1{width=65%}");
}

// Keeps a multi-part answer readable: each "(2) ..." part starts its own
// paragraph, and answers written on several lines keep their line breaks
// (the quiz app renders them with breaks: true).
function formatAnswer(answer) {
  return answer.replace(/\n/g, "\\\n").replace(/\s(?=\(\d\)\s)/g, "\n\n");
}

function loadQuestions() {
  const all = [];
  for (const file of listQuestionFiles()) {
    for (const { data, content } of readBlocks(file)) {
      all.push({
        id: String(data.id),
        meeting: Number(data.meeting),
        difficulty: data.difficulty,
        points: data.points,
        answer: String(data.answer).trim(),
        explanation: String(data.explanation).trim(),
        reasoningHint: data.reasoningHint ? String(data.reasoningHint).trim() : null,
        scoring: data.scoring ? String(data.scoring).trim() : null,
        body: content.trim(),
      });
    }
  }
  return all;
}

function groupByMeeting(questions) {
  const groups = new Map();
  for (const q of questions) {
    if (!groups.has(q.meeting)) groups.set(q.meeting, []);
    groups.get(q.meeting).push(q);
  }
  return [...groups.entries()].sort((a, b) => a[0] - b[0]);
}

function sum(questions) {
  return questions.reduce((total, q) => total + q.points, 0);
}

// A bold label (Jawaban, Penjelasan, ...) must not be left alone at the
// bottom of a page, away from its text.
function label(text) {
  return ["```{=latex}", "\\needspace{4\\baselineskip}", "```", "", `**${text}**`, ""].join("\n");
}

function renderQuestion(q, number) {
  // Questions with a diagram need more room so the heading is not stranded
  // at the bottom of a page, away from its image.
  const needed = /!\[/.test(q.body) ? 22 : 12;
  const parts = [
    "```{=latex}",
    `\\needspace{${needed}\\baselineskip}`,
    "```",
    "",
    `### Soal ${number} (${q.id}, ${q.points} poin, ${DIFFICULTY_LABEL[q.difficulty]})`,
    "",
    label("Soal"),
    sizeImages(q.body),
    "",
    label("Jawaban"),
    formatAnswer(q.answer),
    "",
    label("Penjelasan"),
    q.explanation,
    "",
  ];
  if (q.reasoningHint) {
    parts.push(label("Syarat jawaban"), q.reasoningHint, "");
  }
  if (q.scoring) {
    parts.push(label("Catatan penilaian"), q.scoring, "");
  }
  return parts.join("\n");
}

function renderDocument(questions) {
  const groups = groupByMeeting(questions);
  const lines = [
    "# Kunci Jawaban Kuis",
    "## Pemrograman Berbasis Objek (RTI253007), Pertemuan 1-4",
    "",
    "Dokumen untuk Dosen. Jangan dibagikan kepada mahasiswa.",
    "",
    "| Pertemuan | Jumlah soal | Total poin |",
    "|---|---:|---:|",
    ...groups.map(([meeting, qs]) => `| ${meeting} | ${qs.length} | ${sum(qs)} |`),
    `| **Semua** | **${questions.length}** | **${sum(questions)}** |`,
    "",
    `Nomor soal mengikuti urutan di aplikasi kuis saat semua kelompok soal dipilih (${questions.length} soal).`,
    "",
  ];
  let number = 0;
  groups.forEach(([meeting, qs], index) => {
    if (index > 0) lines.push("```{=latex}", "\\newpage", "```", "");
    lines.push(`## Pertemuan ${meeting} (${sum(qs)} poin)`, "");
    for (const q of qs) lines.push(renderQuestion(q, ++number));
  });
  return lines.join("\n");
}

function main() {
  const questions = loadQuestions();
  fs.mkdirSync(BUILD_DIR, { recursive: true });
  fs.writeFileSync(MD_FILE, renderDocument(questions));

  // Run pandoc from build/ so ../assets/uml/... resolves to quiz/assets/uml/.
  try {
    execFileSync(
      "pandoc",
      [
        "answer-key.md",
        "-o", "answer-key.pdf",
        "--resource-path", ".:../questions",
        "--pdf-engine=lualatex",
        "-V", "papersize=a4",
        "-V", "geometry:margin=2cm",
        "-V", "mainfont=DejaVu Sans",
        "-V", "monofont=DejaVu Sans Mono",
        "-V", "fontsize=10pt",
        "-V", "colorlinks",
        `--include-in-header=${HEADER_TEX}`,
        `--include-in-header=${KEY_HEADER_TEX}`,
      ],
      { cwd: BUILD_DIR, stdio: "inherit" }
    );
  } catch (err) {
    throw new Error(`pandoc failed (is pandoc with lualatex installed?): ${err.message}`);
  }

  console.log(`Built answer key for ${questions.length} questions (${sum(questions)} points)`);
  console.log(`  ${path.relative(process.cwd(), MD_FILE)}`);
  console.log(`  ${path.relative(process.cwd(), PDF_FILE)}`);
}

main();
