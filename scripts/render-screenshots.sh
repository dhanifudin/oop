#!/usr/bin/env bash
# Copy hand-captured GUI screenshots (assets/screenshots/pertemuan-NN/*.png,
# captured via the offscreen Swing paint technique, not rendered from any
# source format) into both slides/ and jobsheets/ so decks and jobsheets can
# reference them with short relative paths, same pattern as render-uml.sh.
set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")/.."

mkdir -p assets/screenshots slides/assets/screenshots jobsheets/assets/screenshots

for dir in assets/screenshots/pertemuan-*/; do
  nn="$(basename "$dir")"
  mkdir -p "slides/assets/screenshots/$nn" "jobsheets/assets/screenshots/$nn"
  for f in "$dir"*.png; do
    name="$(basename "$f")"
    cp "$f" "slides/assets/screenshots/$nn/$name"
    cp "$f" "jobsheets/assets/screenshots/$nn/$name"
    echo "==> $nn/$name"
  done
done
