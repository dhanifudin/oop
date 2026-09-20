// Reads quiz/questions/*.md, parses each question block (frontmatter + markdown
// body, blocks separated by a line containing only "%%%"), renders markdown,
// and writes quiz/questions.json for the browser app to fetch().
//
// Run via `npm run build` (also runs automatically before `npm start`).

const fs = require("fs");
const path = require("path");
const matter = require("gray-matter");
const { marked } = require("marked");

// Single newlines (e.g. a multi-line console-output option) should render
// as visible line breaks, not collapse into one run-on line.
marked.setOptions({ breaks: true, gfm: true });

const QUESTIONS_DIR = path.join(__dirname, "..", "questions");
const OUT_FILE = path.join(__dirname, "..", "questions.json");

const VALID_FORMATS = ["theory", "concept", "code"];
const VALID_DIFFICULTIES = ["easy", "medium", "hard"];

function splitBlocks(raw) {
  return raw
    .split(/\n%%%\s*\n/)
    .map((block) => block.trim())
    .filter((block) => block.length > 0);
}

function parseFile(filePath) {
  const raw = fs.readFileSync(filePath, "utf8");
  const blocks = splitBlocks(raw);
  const fileName = path.basename(filePath);

  return blocks.map((block, index) => {
    const { data, content } = matter(block);
    const where = `${fileName} (block ${index + 1})`;

    if (!VALID_FORMATS.includes(data.format)) {
      throw new Error(`${where}: invalid or missing "format" (${data.format})`);
    }
    if (!VALID_DIFFICULTIES.includes(data.difficulty)) {
      throw new Error(`${where}: invalid or missing "difficulty" (${data.difficulty})`);
    }
    if (!data.answer || !String(data.answer).trim()) {
      throw new Error(`${where}: missing "answer"`);
    }
    if (!data.id) {
      throw new Error(`${where}: missing "id"`);
    }
    if (!data.meeting) {
      throw new Error(`${where}: missing "meeting"`);
    }
    if (!data.explanation) {
      throw new Error(`${where}: missing "explanation"`);
    }

    return {
      id: String(data.id),
      meeting: Number(data.meeting),
      format: data.format,
      difficulty: data.difficulty,
      questionHtml: marked.parse(content.trim()),
      answerHtml: marked.parse(String(data.answer)),
      explanationHtml: marked.parse(String(data.explanation)),
    };
  });
}

function main() {
  const files = fs
    .readdirSync(QUESTIONS_DIR)
    .filter((f) => f.endsWith(".md"))
    .sort();

  if (files.length === 0) {
    throw new Error(`No .md files found in ${QUESTIONS_DIR}`);
  }

  const all = [];
  const seenIds = new Set();

  for (const file of files) {
    const questions = parseFile(path.join(QUESTIONS_DIR, file));
    for (const q of questions) {
      if (seenIds.has(q.id)) {
        throw new Error(`Duplicate question id "${q.id}" (in ${file})`);
      }
      seenIds.add(q.id);
      all.push(q);
    }
  }

  fs.writeFileSync(OUT_FILE, JSON.stringify(all, null, 2));

  const byMeeting = {};
  for (const q of all) {
    byMeeting[q.meeting] = (byMeeting[q.meeting] || 0) + 1;
  }
  console.log(`Built ${all.length} questions -> ${path.relative(process.cwd(), OUT_FILE)}`);
  console.log("Per meeting:", byMeeting);
}

main();
