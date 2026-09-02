#!/usr/bin/env python3
"""Assemble the GitHub Pages site under docs/: copy every built slide and
jobsheet PDF plus a generated index.html linking them, grouped by meeting.

Run after `make pdf` has produced slides/build/*.pdf and
jobsheets/build/*.pdf. This script's own output directory (docs/) is
gitignored and rebuilt fresh by CI on every deploy, never committed.
"""
import re
import shutil
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parent.parent
SLIDES_BUILD = REPO_ROOT / "slides/build"
JOBSHEETS_BUILD = REPO_ROOT / "jobsheets/build"
CHECKPOINT_ZIPS = REPO_ROOT / "code/bank-mini-zips"
DOCS = REPO_ROOT / "docs-site"

# id-pertemuan-01-pengantar-konsep-pbo.pdf -> lang="id", nn="01", slug="pengantar-konsep-pbo"
NAME_RE = re.compile(r"^(id|en)-pertemuan-(\d+)-(.+)\.pdf$")


def collect(build_dir: Path):
    """Returns {nn: {lang: (slug, src_path)}} for every PDF in build_dir."""
    by_meeting = {}
    if not build_dir.exists():
        return by_meeting
    for pdf in sorted(build_dir.glob("*.pdf")):
        m = NAME_RE.match(pdf.name)
        if not m:
            continue
        lang, nn, slug = m.groups()
        by_meeting.setdefault(nn, {})[lang] = (slug, pdf)
    return by_meeting


def title_from_slug(slug: str) -> str:
    return slug.replace("-", " ").title()


def main():
    if DOCS.exists():
        shutil.rmtree(DOCS)
    (DOCS / "slides").mkdir(parents=True)
    (DOCS / "jobsheets").mkdir(parents=True)
    (DOCS / "code").mkdir(parents=True)

    slides = collect(SLIDES_BUILD)
    jobsheets = collect(JOBSHEETS_BUILD)

    all_nn = sorted(set(slides) | set(jobsheets), key=lambda n: int(n))

    rows = []
    for nn in all_nn:
        s = slides.get(nn, {})
        j = jobsheets.get(nn, {})
        slug = next(iter(s.values()), next(iter(j.values()), (None, None)))[0]
        title = title_from_slug(slug) if slug else f"Pertemuan {nn}"

        cells = []
        for lang, label in (("id", "ID"), ("en", "EN")):
            if lang in s:
                _, src = s[lang]
                dest = DOCS / "slides" / src.name
                shutil.copyfile(src, dest)
                cells.append(f'<a href="slides/{src.name}">Slides ({label})</a>')
            if lang in j:
                _, src = j[lang]
                dest = DOCS / "jobsheets" / src.name
                shutil.copyfile(src, dest)
                cells.append(f'<a href="jobsheets/{src.name}">Jobsheet ({label})</a>')

        zip_src = CHECKPOINT_ZIPS / f"pertemuan-{nn}.zip"
        if zip_src.exists():
            zip_name = zip_src.name
            shutil.copyfile(zip_src, DOCS / "code" / zip_name)
            cells.append(f'<a href="code/{zip_name}">Code (ZIP)</a>')

        rows.append((nn, title, cells))

    links_html = "\n".join(
        f'<li><span class="week">Pertemuan {nn}</span> '
        f'<span class="title">{title}</span> '
        f'<span class="links">{" &middot; ".join(cells) if cells else "&mdash;"}</span></li>'
        for nn, title, cells in rows
    )

    html = f"""<!doctype html>
<html lang="id">
<head>
<meta charset="utf-8">
<title>Pemrograman Berbasis Objek (RTI253007/RTI253008)</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
  body {{ font-family: -apple-system, 'Helvetica Neue', Arial, sans-serif; max-width: 860px; margin: 40px auto; padding: 0 20px; color: #0f172a; }}
  h1 {{ color: #1d4ed8; font-size: 1.6em; }}
  p.sub {{ color: #64748b; }}
  ul {{ list-style: none; padding: 0; }}
  li {{ padding: 12px 0; border-bottom: 1px solid #e2e8f0; display: flex; flex-wrap: wrap; gap: 10px; align-items: baseline; }}
  .week {{ font-weight: 700; color: #1d4ed8; min-width: 110px; }}
  .title {{ flex: 1; min-width: 200px; }}
  .links a {{ color: #1d4ed8; text-decoration: none; margin-right: 4px; }}
  .links a:hover {{ text-decoration: underline; }}
</style>
</head>
<body>
<h1>Pemrograman Berbasis Objek</h1>
<p class="sub">RTI253007 (konsep) &amp; RTI253008 (praktikum) &mdash; D-IV Teknik Informatika, Politeknik Negeri Malang. Studi kasus semester: Bank Mini.</p>
<ul>
{links_html}
</ul>
</body>
</html>
"""
    (DOCS / "index.html").write_text(html)
    print(f"Wrote {DOCS.relative_to(REPO_ROOT)}/index.html with {len(rows)} meetings")


if __name__ == "__main__":
    main()
