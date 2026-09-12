# Pemrograman Berbasis Objek (RTI253007) - Konvensi Materi

Mata kuliah: Pemrograman Berbasis Objek (RTI253007), D-IV Teknik Informatika,
Politeknik Negeri Malang. Bahasa Java (JDK 17+). Editor utama: **NetBeans**
(sesuai RPS); semua jobsheet menyertakan alternatif baris perintah untuk
mahasiswa yang memakai editor teks biasa. Konsep berjalan 4x50 menit,
seluruhnya berbasis slide; praktikum ada di sesi/jobsheet terpisah.

Detail konvensi dipecah per topik di `conventions/`, supaya berkas ini
tetap ringkas. Baca berkas yang relevan SEBELUM mengedit area tsb:

| Sedang mengerjakan... | Baca dulu |
|---|---|
| Narasi/bahasa (id atau en), literal kode | `conventions/writing.md` |
| Cakupan Bank Mini, urutan topik mingguan, evolusi kelas per pertemuan | `conventions/bank-mini.md` |
| Slide Marp (`slides/`), termasuk SOP dek 4x50 menit | `conventions/slides.md` |
| Jobsheet praktikum (`jobsheets/`) | `conventions/jobsheets.md` |
| Struktur direktori, scripts, checkpoint/zip, CI/Pages | `conventions/pipeline.md` |

## Aturan inti yang selalu berlaku

- Jangan pernah memakai em-dash (—); lihat `conventions/writing.md` untuk
  aturan bahasa lengkap.
- Semua kode Java memakai package `id.ac.polinema`. Identifier DAN string
  literal di kode SELALU bahasa Inggris, di kedua versi bahasa materi.
- **Studi kasus tunggal untuk satu semester: Bank Mini**, dari Pertemuan 2
  sampai 16. Tiap pertemuan memperkenalkan konsep lewat toy generik dulu,
  baru diterapkan ke Bank Mini; jobsheet sepenuhnya Bank-Mini-only. Detail
  cakupan kelas dan evolusi per pertemuan: `conventions/bank-mini.md`.
- **Tidak ada git di jobsheet.**
- Build: `make pdf` (semua), `make slides`/`make jobsheets` (salah satu),
  `make checkpoints`/`make zips` (kode Bank Mini), `make diagrams` (UML +
  ilustrasi). Lihat `Makefile` dan `conventions/pipeline.md`.
- Repo publik `github.com/dhanifudin/oop`, CI men-deploy ke GitHub Pages
  (`https://dhanifudin.com/oop/`) setiap push ke `main`.

Setiap gotcha rendering/build yang pernah lolos tanpa disadari (warna
tersembunyi, sintaks gambar salah, PlantUML terpotong, dst.) sudah dicatat
di berkas `conventions/` yang relevan; periksa berkas tsb sebelum
menganggap sebuah perubahan selesai, dan verifikasi lewat render
sungguhan (PDF/gambar per halaman), bukan hanya membaca markdown.
