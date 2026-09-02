#!/usr/bin/env bash
# One-time setup: create a local venv with the packages render-code.py needs.
set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")"

python3 -m venv .venv
./.venv/bin/pip install --quiet --upgrade pip pygments pillow
echo "Done. Venv at scripts/.venv"
