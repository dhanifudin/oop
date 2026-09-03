#!/usr/bin/env bash
# Copy hand-authored SVG illustrations into slides/assets/illustrations/
# (Marp/Chromium renders SVG natively, no rasterization needed there), and
# rasterize the stack/heap ones into jobsheets/assets/uml/ as PNG
# (pandoc/lualatex needs a raster or a converter). The jobsheet (Pertemuan
# 2 practicum, now Bank-Mini-only) renders the Account-labeled variants
# under the legacy filenames p02-memory-new.png / p02-memory-alias.png;
# the slide deck's own generic Rectangle example keeps using
# stack-heap-single.svg / stack-heap-alias.svg directly (copied above,
# unrasterized) since Marp renders SVG natively.
set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")/.."

SRC=assets/illustrations/src
mkdir -p "$SRC" slides/assets/illustrations jobsheets/assets/uml

for f in "$SRC"/*.svg; do
  name="$(basename "$f")"
  cp "$f" "slides/assets/illustrations/$name"
  echo "==> slides/assets/illustrations/$name"
done

rsvg-convert -w 1400 "$SRC/stack-heap-single-account.svg" -o jobsheets/assets/uml/p02-memory-new.png
echo "==> jobsheets/assets/uml/p02-memory-new.png (from stack-heap-single-account.svg)"

rsvg-convert -w 1400 "$SRC/stack-heap-alias-account.svg" -o jobsheets/assets/uml/p02-memory-alias.png
echo "==> jobsheets/assets/uml/p02-memory-alias.png (from stack-heap-alias-account.svg)"

rsvg-convert -w 1400 "$SRC/polymorphic-dispatch.svg" -o jobsheets/assets/uml/p10-polymorphic-dispatch.png
echo "==> jobsheets/assets/uml/p10-polymorphic-dispatch.png (from polymorphic-dispatch.svg)"

rsvg-convert -w 1400 "$SRC/collections-motivation.svg" -o jobsheets/assets/uml/p11-collections-motivation.png
echo "==> jobsheets/assets/uml/p11-collections-motivation.png (from collections-motivation.svg)"

rsvg-convert -w 1400 "$SRC/srp-split.svg" -o jobsheets/assets/uml/p11-srp-split.png
echo "==> jobsheets/assets/uml/p11-srp-split.png (from srp-split.svg)"

rsvg-convert -w 1400 "$SRC/persistence-restart.svg" -o jobsheets/assets/uml/p15-persistence-restart.png
echo "==> jobsheets/assets/uml/p15-persistence-restart.png (from persistence-restart.svg)"

rsvg-convert -w 1400 "$SRC/login-gate.svg" -o jobsheets/assets/uml/p15-login-gate.png
echo "==> jobsheets/assets/uml/p15-login-gate.png (from login-gate.svg)"
