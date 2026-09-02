#!/usr/bin/env python3
"""Render Java (or any Pygments-supported) source files to syntax-highlighted
PNG images, with optional highlighted lines to mark newly added/changed code.

Reads a manifest TSV (default: jobsheets/assets/code/manifest.tsv) with columns:
    src_path    hl_lines    out_path    keep_ranges
- src_path: path to the source file to render, relative to repo root.
- hl_lines: comma-separated 1-indexed line numbers (in the FULL source file)
  to highlight, or "-" for none.
- out_path: output PNG path, relative to repo root.
- keep_ranges (optional, 4th column): comma-separated 1-indexed inclusive
  line ranges from the FULL source file to actually render, e.g. "1-3,48-57".
  Everything outside these ranges is collapsed into a single placeholder
  comment line. Omit this column (or use "-") to render the whole file.
  Used to keep long, accumulated files (e.g. Account.java after many
  meetings) focused on just the new/changed method for a given step,
  instead of re-showing the entire file every time.

Blank lines and lines starting with # are ignored.
"""
import sys
from pathlib import Path

from pygments import highlight
from pygments.lexers import JavaLexer, BashLexer, XmlLexer
from pygments.formatters import ImageFormatter

REPO_ROOT = Path(__file__).resolve().parent.parent

LEXERS = {
    ".java": JavaLexer,
    ".sh": BashLexer,
    ".xml": XmlLexer,
}

FONT_SIZE = 28
LINE_PAD = 6
HL_COLOR = "#d7f8d7"
OMISSION_PLACEHOLDER = "    // ... (unchanged code omitted)"


def lexer_for(path: Path):
    return LEXERS.get(path.suffix, JavaLexer)()


def apply_keep_ranges(full_lines, hl_lines, keep_ranges):
    """Collapse full_lines down to just keep_ranges (1-indexed inclusive),
    inserting OMISSION_PLACEHOLDER between non-adjacent ranges, and remap
    hl_lines (1-indexed into full_lines) to their new 1-indexed positions.
    Returns (trimmed_lines, remapped_hl_lines).
    """
    trimmed = []
    remap = {}
    prev_end = None
    for start, end in keep_ranges:
        if prev_end is not None and start > prev_end + 1:
            trimmed.append(OMISSION_PLACEHOLDER)
        for i in range(start, end + 1):
            trimmed.append(full_lines[i - 1])
            remap[i] = len(trimmed)
        prev_end = end
    new_hl = sorted({remap[n] for n in hl_lines if n in remap})
    return trimmed, new_hl


def render_one(src_path: Path, hl_lines, out_path: Path, keep_ranges=None):
    full_lines = src_path.read_text().splitlines()
    if keep_ranges:
        lines, hl_lines = apply_keep_ranges(full_lines, hl_lines, keep_ranges)
        code = "\n".join(lines) + "\n"
    else:
        code = src_path.read_text()
    lexer = lexer_for(src_path)
    formatter = ImageFormatter(
        font_name="DejaVu Sans Mono",
        font_size=FONT_SIZE,
        line_number_bg="#f1f5f9",
        line_number_fg="#64748b",
        line_number_pad=8,
        line_pad=LINE_PAD,
        line_numbers=True,
        hl_lines=hl_lines,
        hl_color=HL_COLOR,
        style="default",
        image_pad=14,
    )
    out_path.parent.mkdir(parents=True, exist_ok=True)
    with open(out_path, "wb") as f:
        highlight(code, lexer, formatter, outfile=f)
    try:
        shown = out_path.relative_to(REPO_ROOT)
    except ValueError:
        shown = out_path
    print(f"==> {shown}")


def parse_hl(spec: str):
    spec = spec.strip()
    if spec in ("", "-"):
        return []
    return [int(x) for x in spec.split(",") if x.strip()]


def parse_ranges(spec: str):
    spec = spec.strip()
    if spec in ("", "-"):
        return None
    ranges = []
    for part in spec.split(","):
        part = part.strip()
        if not part:
            continue
        start, end = part.split("-")
        ranges.append((int(start), int(end)))
    return ranges


def main():
    manifest = Path(sys.argv[1]) if len(sys.argv) > 1 else REPO_ROOT / "jobsheets/assets/code/manifest.tsv"
    for lineno, line in enumerate(manifest.read_text().splitlines(), start=1):
        line = line.strip()
        if not line or line.startswith("#"):
            continue
        parts = line.split("\t")
        if len(parts) not in (3, 4):
            print(f"manifest.tsv:{lineno}: expected 3 or 4 tab-separated fields, got {len(parts)}: {line!r}", file=sys.stderr)
            sys.exit(1)
        src, hl, out = parts[0], parts[1], parts[2]
        keep_ranges = parse_ranges(parts[3]) if len(parts) == 4 else None
        render_one(REPO_ROOT / src.strip(), parse_hl(hl), REPO_ROOT / out.strip(), keep_ranges)


if __name__ == "__main__":
    main()
