#!/usr/bin/env bash
# Render every slides/{id,en}/pertemuan-*.md into a PDF under slides/build/,
# and into a navigable web deck (Marp's default "bespoke" HTML template,
# with keyboard/click navigation, fullscreen, and an on-screen controller)
# under slides/build-html/{id,en}/.
#
# The web decks reference images via the same relative "../assets/..." paths
# the Markdown source uses, so each HTML file only resolves correctly when
# it stays one directory level below a sibling assets/ folder. build-html/
# gets its own assets/ (mirroring slides/assets/) so the id/ and en/ decks
# in build-html/ are self-contained and can be copied anywhere as a unit.
#
# Usage: ./build.sh              (build all)
#        ./build.sh id/FILE.md   (build a single file, path relative to slides/)
set -euo pipefail

cd "$(dirname "${BASH_SOURCE[0]}")"
mkdir -p build build-html/id build-html/en

build_one() {
  local src="$1"
  local lang
  local name
  lang="$(basename "$(dirname "$src")")"
  name="$(basename "${src%.md}")"
  echo "==> ${lang}-${name}"
  marp --pdf --allow-local-files "$src" -o "build/${lang}-${name}.pdf"
  marp --allow-local-files "$src" -o "build-html/${lang}/${name}.html"
}

if [ "$#" -gt 0 ]; then
  build_one "$1"
else
  shopt -s nullglob
  for f in id/pertemuan-*.md en/pertemuan-*.md; do
    build_one "$f"
  done
fi

rm -rf build-html/assets
cp -r assets build-html/assets
