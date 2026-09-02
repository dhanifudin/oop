#!/usr/bin/env bash
# Render every assets/uml/src/*.puml into PNG, then copy into both slides/
# and jobsheets/ so decks and jobsheets can reference the images with short
# relative paths.
#
# Note: the stack/heap memory diagrams are NOT PlantUML anymore, they are
# hand-authored SVG illustrations under assets/illustrations/src/, handled
# by scripts/render-illustrations.sh instead (nicer visuals than plain
# PlantUML boxes for that specific concept). This script only ever touches
# the standard UML class-box diagrams.
set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")/.."

mkdir -p assets/uml/src slides/assets/uml jobsheets/assets/uml

# PlantUML silently CROPS (does not scale or error) any diagram whose
# rendered size exceeds its internal safety cap (default 4096px). At the
# larger defaultFontSize used by this project, some multi-class diagrams
# exceed that cap, which for a PNG output visibly clips the right/bottom
# edge (e.g. a truncated method signature) with no warning. Raise the cap
# well above anything this project renders.
JDK_JAVA_OPTIONS="-DPLANTUML_LIMIT_SIZE=8192" plantuml -tpng -o "$(pwd)/assets/uml/src" assets/uml/src/*.puml

for f in assets/uml/src/*.png; do
  name="$(basename "$f")"
  cp "$f" "slides/assets/uml/$name"
  cp "$f" "jobsheets/assets/uml/$name"
  echo "==> $name"
done
