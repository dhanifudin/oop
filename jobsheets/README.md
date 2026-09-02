# Jobsheet Praktikum

Jobsheet Markdown, satu berkas per pertemuan per bahasa: `id/pertemuan-NN-<slug>.md`
dan `en/pertemuan-NN-<slug>.md`. Salin `TEMPLATE.md` saat menulis pertemuan baru.

Render ke PDF A4: `./build.sh` (semua berkas) atau `./build.sh id/pertemuan-02-kelas-dan-objek.md`
(satu berkas). Output masuk ke `build/` (tidak di-commit), diberi awalan
`id-`/`en-`. Butuh `pandoc` + `lualatex`.

Cuplikan kode di jobsheet adalah GAMBAR, bukan blok kode teks, supaya bisa
menampilkan syntax highlighting dan menyorot baris yang baru/berubah di
setiap langkah. Sumber Java asli ada di `assets/code-src/pertemuan-NN/`;
jangan edit gambar PNG di `assets/code/` secara langsung. Untuk menambah
langkah baru atau mengubah kode:

1. Edit/tambah berkas Java di `assets/code-src/pertemuan-NN/langkah-NN/...`.
2. Tambahkan barisnya ke `ROWS` di `../scripts/gen-manifest.py`, lalu jalankan
   `../scripts/.venv/bin/python ../scripts/gen-manifest.py` untuk menghitung
   ulang `assets/code/manifest.tsv` (baris yang berubah dihitung otomatis
   lewat diff terhadap langkah sebelumnya).
3. Jalankan `../scripts/.venv/bin/python ../scripts/render-code.py assets/code/manifest.tsv`
   untuk merender ulang PNG-nya.

Diagram UML ada di `assets/uml/` (disalin dari `../assets/uml/src/*.puml`
lewat `../scripts/render-uml.sh`). Lihat `CLAUDE.md` di root repo untuk
konvensi lengkap.
