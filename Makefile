# Build entry point for RTI253007/RTI253008 course materials.
#
# Usage:
#   make            build everything (diagrams, illustrations, code images, all PDFs)
#   make pdf        same as above
#   make slides     just rebuild slide-deck PDFs
#   make jobsheets  just rebuild jobsheet PDFs
#   make diagrams   just re-render UML diagrams + SVG illustrations
#   make images     just regenerate jobsheet code-snippet images
#   make checkpoints  regenerate code/bank-mini/pertemuan-NN/ snapshots
#   make setup      create the Python venv used by the image-rendering scripts
#   make clean      remove build/ output directories (source files untouched)
#
# See CLAUDE.md for the full asset pipeline and conventions.

.PHONY: all pdf slides jobsheets diagrams images checkpoints setup clean

all: pdf

pdf:
	./scripts/render-all.sh

slides:
	./slides/build.sh

jobsheets:
	./jobsheets/build.sh

diagrams:
	./scripts/render-uml.sh
	./scripts/render-illustrations.sh
	./scripts/render-screenshots.sh

images: diagrams
	./scripts/.venv/bin/python scripts/gen-manifest.py
	./scripts/.venv/bin/python scripts/render-code.py jobsheets/assets/code/manifest.tsv

checkpoints:
	./scripts/.venv/bin/python scripts/build-checkpoints.py

setup:
	./scripts/setup.sh

clean:
	rm -rf slides/build jobsheets/build
	rm -rf code/bank-mini
