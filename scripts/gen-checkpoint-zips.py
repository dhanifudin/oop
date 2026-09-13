#!/usr/bin/env python3
"""Zip Bank Mini checkpoints under code/bank-mini/pertemuan-NN/ into
starter and end-code zips under code/bank-mini-zips/, so students can
download a runnable starting point (or that week's own finished code)
straight from the Pages site instead of needing git (this course has
none) or a manual hand-off from the Dosen.

Each meeting's STARTER zip is the PREVIOUS practicum meeting's checkpoint
(the state a student should have before opening that meeting's jobsheet):
pertemuan-06-starter.zip holds checkpoint 04's files, pertemuan-13-starter.zip
holds checkpoint 11's, and so on across the 01/02/03/04/06/07/09/10/11/
13/14/15 sequence (05/08/12 are quiz/UTS weeks with no checkpoint). The
first practicum meeting (01) has no starter: its jobsheet builds the
project from scratch.

Each meeting's END zip is that SAME meeting's own finished checkpoint
(pertemuan-06-end.zip holds checkpoint 06's files). Published at the
user's explicit request even though it doubles as that week's answer
key (a student could skip the jobsheet and just download the end
state); this is a deliberate policy choice, not an oversight.

Run after scripts/build-checkpoints.py (which scripts/render-all.sh
already calls). Stdlib only (zipfile), no venv needed.

Each zip wraps its contents in a self-labeled top-level folder
(bank-mini-pertemuan-NN-starter/... or bank-mini-pertemuan-NN-end/...)
so a student who downloads several weeks into the same Downloads folder
gets distinctly named extracted folders instead of everything colliding
into a bare src/.
"""
from pathlib import Path
from zipfile import ZipFile, ZIP_DEFLATED

REPO_ROOT = Path(__file__).resolve().parent.parent
CHECKPOINTS = REPO_ROOT / "code/bank-mini"
OUT = REPO_ROOT / "code/bank-mini-zips"

# Build output and IDE metadata that can end up inside a checkpoint
# directory (e.g. from manually running `mvn compile` there while testing)
# but was never part of the checkpoint's own source and must never ship
# to students. build-checkpoints.py wipes the checkpoint dir on every
# regeneration, so a fresh `make checkpoints` avoids this too, but this
# script excludes it defensively regardless of run order.
EXCLUDED_DIR_NAMES = {"target", ".classpath", ".project", ".settings", ".idea", "nbproject", ".git"}


def is_excluded(path: Path, checkpoint_dir: Path) -> bool:
    rel_parts = path.relative_to(checkpoint_dir).parts
    return bool(rel_parts) and rel_parts[0] in EXCLUDED_DIR_NAMES


def write_zip(zip_path: Path, checkpoint_dir: Path, top_level: str):
    with ZipFile(zip_path, "w", ZIP_DEFLATED) as zf:
        for file_path in sorted(checkpoint_dir.rglob("*")):
            if file_path.is_dir() or is_excluded(file_path, checkpoint_dir):
                continue
            arcname = Path(top_level) / file_path.relative_to(checkpoint_dir)
            zf.write(file_path, arcname)


def main():
    if not CHECKPOINTS.exists():
        print("No checkpoints found; run scripts/build-checkpoints.py first.")
        return

    checkpoint_dirs = sorted(
        (d for d in CHECKPOINTS.iterdir() if d.is_dir()),
        key=lambda d: int(d.name.removeprefix("pertemuan-")),
    )
    if not checkpoint_dirs:
        print("No checkpoint directories found under code/bank-mini/.")
        return

    OUT.mkdir(parents=True, exist_ok=True)
    written = []
    for previous_dir, current_dir in zip(checkpoint_dirs, checkpoint_dirs[1:]):
        nn = current_dir.name.removeprefix("pertemuan-")
        top_level = f"bank-mini-pertemuan-{nn}-starter"
        zip_path = OUT / f"pertemuan-{nn}-starter.zip"
        write_zip(zip_path, previous_dir, top_level)
        written.append(zip_path.relative_to(REPO_ROOT))

    for current_dir in checkpoint_dirs:
        nn = current_dir.name.removeprefix("pertemuan-")
        top_level = f"bank-mini-pertemuan-{nn}-end"
        zip_path = OUT / f"pertemuan-{nn}-end.zip"
        write_zip(zip_path, current_dir, top_level)
        written.append(zip_path.relative_to(REPO_ROOT))

    for path in written:
        print(f"Wrote {path}")
    first_nn = checkpoint_dirs[0].name.removeprefix("pertemuan-")
    print(f"No starter zip for pertemuan-{first_nn} (built from scratch in its jobsheet).")


if __name__ == "__main__":
    main()
