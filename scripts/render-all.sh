#!/usr/bin/env bash
# Regenerate every derived asset and rebuild every deliverable PDF:
# UML diagrams -> illustrations -> code snippet images -> slide PDFs -> jobsheet PDFs.
set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")/.."

./scripts/render-uml.sh
./scripts/render-illustrations.sh
./scripts/render-screenshots.sh
./scripts/.venv/bin/python scripts/gen-manifest.py
./scripts/.venv/bin/python scripts/render-code.py jobsheets/assets/code/manifest.tsv
./scripts/.venv/bin/python scripts/build-checkpoints.py
./slides/build.sh
./jobsheets/build.sh
