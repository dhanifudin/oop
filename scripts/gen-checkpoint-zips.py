#!/usr/bin/env python3
"""Zip every Bank Mini checkpoint under code/bank-mini/pertemuan-NN/ into
code/bank-mini-zips/pertemuan-NN.zip, so students can download a runnable
snapshot straight from the Pages site instead of needing git (this course
has none) or a manual hand-off from the Dosen.

Run after scripts/build-checkpoints.py (which scripts/render-all.sh
already calls). Stdlib only (zipfile), no venv needed.

Each zip wraps its contents in a self-labeled top-level folder
(bank-mini-pertemuan-NN/...) so a student who downloads several weeks
into the same Downloads folder gets distinctly named extracted folders
instead of everything colliding into a bare src/.
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


def main():
    if not CHECKPOINTS.exists():
        print("No checkpoints found; run scripts/build-checkpoints.py first.")
        return

    OUT.mkdir(parents=True, exist_ok=True)
    written = []
    for checkpoint_dir in sorted(CHECKPOINTS.iterdir()):
        if not checkpoint_dir.is_dir():
            continue
        nn = checkpoint_dir.name.removeprefix("pertemuan-")
        top_level = f"bank-mini-pertemuan-{nn}"
        zip_path = OUT / f"pertemuan-{nn}.zip"

        with ZipFile(zip_path, "w", ZIP_DEFLATED) as zf:
            for file_path in sorted(checkpoint_dir.rglob("*")):
                if file_path.is_dir() or is_excluded(file_path, checkpoint_dir):
                    continue
                arcname = Path(top_level) / file_path.relative_to(checkpoint_dir)
                zf.write(file_path, arcname)

        written.append(zip_path.relative_to(REPO_ROOT))

    for path in written:
        print(f"Wrote {path}")
    if not written:
        print("No checkpoint directories found under code/bank-mini/.")


if __name__ == "__main__":
    main()
