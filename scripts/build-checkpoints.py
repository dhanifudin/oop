#!/usr/bin/env python3
"""Build runnable Bank Mini checkpoint snapshots under code/bank-mini/.

Walks jobsheets/assets/code-src/pertemuan-NN/ in the semester's teaching
order, overlaying each meeting's steps onto a running set of files (a file
written by an earlier step or meeting stays unless a later one replaces
it). After each meeting is fully overlaid, the cumulative file set is
written out as a complete, runnable snapshot to code/bank-mini/pertemuan-NN/,
so a student who missed a week can open the previous week's checkpoint and
continue.

Two step kinds are excluded from the overlay (they do not represent the
"official" cumulative state of the project):
  - directories containing "bug" (a deliberately broken state shown before
    its "-fix" counterpart)
  - directories named "tugas" or starting with "tugas-" (an independent
    take-home exercise, not part of the main case study line)

A step directory may also contain a `.delete` file (sibling to its `id/`
package tree, one relative path per line, e.g. `Rectangle.java`) to retire
a file from the accumulated snapshot as of that step. This is how
meeting-local toy classes (introduced to teach a concept, never meant to
become a permanent part of Bank Mini) get cleaned out of the ongoing
project instead of piling up in every later checkpoint forever.

Meetings from MAVEN_FROM onward are emitted as Maven projects (pom.xml
from scripts/pom-template.xml, sources under
src/main/java/id/ac/polinema/..., preserving any model/repository/ui
subpackage folders already present in code-src). Earlier meetings are
emitted as a plain src/id/ac/polinema/*.java tree, runnable with
javac/java directly.

Meetings whose code-src directory does not exist yet are skipped (content
is authored incrementally, batch by batch).
"""
import shutil
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parent.parent
CS = REPO_ROOT / "jobsheets/assets/code-src"
OUT = REPO_ROOT / "code/bank-mini"
POM_TEMPLATE = (REPO_ROOT / "scripts/pom-template.xml").read_text()

# Teaching-week order for the whole semester (assessment weeks omitted).
MEETING_ORDER = ["01", "02", "03", "04", "06", "07", "09", "10", "11", "13", "14", "15", "16"]
MAVEN_FROM = "13"
MAIN_CLASS = "id.ac.polinema.Main"

SQLITE_DEPENDENCY = """  <dependencies>
    <dependency>
      <groupId>org.xerial</groupId>
      <artifactId>sqlite-jdbc</artifactId>
      <version>3.45.1.0</version>
    </dependency>
  </dependencies>
"""


def is_excluded_step(step_dir: Path) -> bool:
    name = step_dir.name
    return "bug" in name or name == "tugas" or name.startswith("tugas-")


def steps_for(nn: str):
    meeting_dir = CS / f"pertemuan-{nn}"
    if not meeting_dir.exists():
        return []
    return sorted(
        d for d in meeting_dir.iterdir()
        if d.is_dir() and not is_excluded_step(d)
    )


def overlay_meeting(files: dict, nn: str):
    for step_dir in steps_for(nn):
        pkg_root = step_dir / "id/ac/polinema"
        if pkg_root.exists():
            for source_file in list(pkg_root.rglob("*.java")) + list(pkg_root.rglob("*.form")):
                rel = source_file.relative_to(step_dir)
                files[rel] = source_file
        delete_manifest = step_dir / ".delete"
        if delete_manifest.exists():
            for line in delete_manifest.read_text().splitlines():
                name = line.strip()
                if not name or name.startswith("#"):
                    continue
                files.pop(Path("id/ac/polinema") / name, None)


def write_snapshot(files: dict, nn: str):
    dest_root = OUT / f"pertemuan-{nn}"
    if dest_root.exists():
        shutil.rmtree(dest_root)
    is_maven = nn >= MAVEN_FROM
    src_base = dest_root / ("src/main/java" if is_maven else "src")
    for rel, abs_path in files.items():
        target = src_base / rel
        target.parent.mkdir(parents=True, exist_ok=True)
        shutil.copyfile(abs_path, target)
    if is_maven:
        dest_root.mkdir(parents=True, exist_ok=True)
        deps = SQLITE_DEPENDENCY if nn >= "15" else ""
        pom = (
            POM_TEMPLATE
            .replace("{{ARTIFACT_ID}}", f"bank-mini-pertemuan-{nn}")
            .replace("{{MAIN_CLASS}}", MAIN_CLASS)
            .replace("{{DEPENDENCIES}}", deps)
        )
        (dest_root / "pom.xml").write_text(pom)
    return dest_root


def main():
    files: dict[Path, Path] = {}
    written = []
    for nn in MEETING_ORDER:
        if not steps_for(nn):
            continue
        overlay_meeting(files, nn)
        dest = write_snapshot(files, nn)
        written.append(dest.relative_to(REPO_ROOT))
    if not written:
        print("No code-src content found yet; nothing to checkpoint.")
        return
    for path in written:
        print(f"Wrote {path}")


if __name__ == "__main__":
    main()
