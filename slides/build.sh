#!/usr/bin/env bash
# Render every slides/{id,en}/pertemuan-*.md into a PDF under slides/build/.
#
# Usage: ./build.sh              (build all)
#        ./build.sh id/FILE.md   (build a single file, path relative to slides/)
set -euo pipefail

cd "$(dirname "${BASH_SOURCE[0]}")"
mkdir -p build

build_one() {
  local src="$1"
  local lang
  local name
  lang="$(basename "$(dirname "$src")")"
  name="$(basename "${src%.md}")"
  echo "==> ${lang}-${name}"
  marp --pdf --allow-local-files "$src" -o "build/${lang}-${name}.pdf"
}

if [ "$#" -gt 0 ]; then
  build_one "$1"
else
  shopt -s nullglob
  for f in id/pertemuan-*.md en/pertemuan-*.md; do
    build_one "$f"
  done
fi
