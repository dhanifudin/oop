#!/usr/bin/env bash
# Render every jobsheets/{id,en}/pertemuan-*.md into an A4 PDF under jobsheets/build/.
#
# Usage: ./build.sh              (build all)
#        ./build.sh id/FILE.md   (build a single file, path relative to jobsheets/)
set -euo pipefail

cd "$(dirname "${BASH_SOURCE[0]}")"
mkdir -p build

PANDOC_OPTS=(
  --pdf-engine=lualatex
  -V papersize=a4
  -V geometry:margin=2cm
  -V mainfont="DejaVu Sans"
  -V monofont="DejaVu Sans Mono"
  -V fontsize=10pt
  -V colorlinks
  --include-in-header=assets/header.tex
)

build_one() {
  local src="$1"
  local lang
  local name
  lang="$(basename "$(dirname "$src")")"
  name="$(basename "${src%.md}")"
  echo "==> ${lang}-${name}"
  # --resource-path lets pandoc resolve the markdown's "../assets/..." image
  # references (relative to id/ or en/) even though pandoc itself runs from
  # jobsheets/ so that -o and --include-in-header paths stay simple.
  pandoc "$src" -o "build/${lang}-${name}.pdf" --resource-path=".:$(dirname "$src")" "${PANDOC_OPTS[@]}"
}

if [ "$#" -gt 0 ]; then
  build_one "$1"
else
  shopt -s nullglob
  for f in id/pertemuan-*.md en/pertemuan-*.md; do
    build_one "$f"
  done
fi
