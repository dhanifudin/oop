# Gaya jobsheet

Ikuti `jobsheets/TEMPLATE.md`: tabel metadata (tanpa baris git), bagian
A Capaian Praktikum, B Persiapan dan Prasyarat (NetBeans-first, dengan kotak
kutipan "Tanpa NetBeans?" berisi alternatif baris perintah), C Langkah Kerja
(narasi singkat -> gambar kode -> `> ✅ Checkpoint:` -> `> ⚠️ Jika gagal:`
bila relevan), D Tugas dan Deliverable, E Kriteria Penilaian (tabel bobot
40/35/25: Langkah kerja / Checkpoint / Tugas mandiri, tanpa baris commit).
Sapaan "kamu" dengan nada formal instruksional (lihat [[writing]]).

**Cuplikan kode di jobsheet adalah gambar** (bukan blok kode teks),
dirender dari `jobsheets/assets/code-src/` lewat `scripts/render-code.py`;
baris kode yang baru/berubah pada suatu langkah disorot hijau otomatis
(dihitung dari diff terhadap langkah sebelumnya, termasuk lintas pertemuan,
di `scripts/gen-manifest.py`). Diagram UML memakai gambar PlantUML, bukan
ASCII art. Pertemuan 2-11 memakai struktur `src/id/ac/polinema/*.java`
polos (javac/java). Pertemuan 13 dan seterusnya beralih ke proyek Maven
(checkpoint `code/bank-mini/pertemuan-NN/`), dijalankan dengan
`mvn -q compile exec:java` atau Run Project di NetBeans.

**Jobsheet sepenuhnya Bank-Mini-only**, tidak mengetik toy generik apa pun
ke dalam proyek; lihat [[bank-mini]] untuk pola "konsep dulu, baru studi
kasus" dan cakupan kelas.

## Aturan urutan: konsep vs praktikum tidak selalu selaras minggu

Jobsheet boleh mengasumsikan semua yang SUDAH dibahas kelas konsep sampai
minggu kalender yang sama. Di titik-titik praktikum mendahului konsep,
langkah jobsheet yang pertama kali butuh ide tsb membuka dengan kotak
kutipan pendek "Konsep Singkat" (maksimal setengah halaman, narasi plus
opsional satu gambar, tanpa kode baru) yang mandiri menjelaskan
secukupnya. **Jangan taruh gambar DI DALAM blockquote "Konsep Singkat"**:
`jobsheets/assets/header.tex` mendefinisikan ulang `quote` memakai
environment `leftbar` (paket `framed`), dan LaTeX menolak float `figure`
(yang otomatis dibuat pandoc dari gambar Markdown ber-alt-text) di dalam
environment semacam ini dengan galat "Not in outer par mode". Taruh gambar
sebagai paragraf biasa segera SETELAH blockquote, bukan di dalamnya. Slide
konsep sebaliknya HANYA boleh merujuk kelas Bank Mini yang sudah dibangun
praktikum sampai minggu itu.

## Hindari token kode panjang digabung slash di prosa/tabel sempit

Mis. `` `SavingsAccount`/`CheckingAccount` `` atau
`` `dailyWithdrawalLimit` `` sendirian di sel tabel Kriteria Penilaian yang
sempit: pernah menyebabkan teks benar-benar menempel/terklip di render PDF
akhir (ditemukan lewat pemeriksaan per-halaman, bukan dari membaca
markdown-nya). Tulis ulang jadi prosa dengan spasi/koma alami, atau di
tabel sempit ganti istilah kode dengan frasa deskriptif singkat.

## Kelanjutan proyek tanpa git

Checkpoint `code/bank-mini/pertemuan-<N-1>/` (snapshot lengkap yang bisa
langsung dijalankan/dibuka NetBeans, bukan version control) dihasilkan
otomatis oleh `scripts/build-checkpoints.py` (lihat [[pipeline]]), jangan
diedit manual. **Path internal ini TIDAK LAGI disebut di teks jobsheet
mana pun** (bukan cuma tidak disebut dari awal): tabel metadata jobsheet
sebelumnya punya baris "Kode Awal"/"Kode Akhir" yang menyebut path ini
secara eksplisit, plus kotak "Tanpa NetBeans?" yang menyuruh mahasiswa `cd
bank-mini` untuk melanjutkan folder pertemuan sebelumnya; keduanya dihapus
dari seluruh jobsheet (dan dari `jobsheets/TEMPLATE.md`) karena path
repo-internal ini tidak berarti apa pun bagi mahasiswa yang membaca PDF,
starter code untuk mahasiswa yang tertinggal kini diunduh langsung dari
situs GitHub Pages (lihat starter zip di [[pipeline]]).

Narasi "melanjutkan proyek `bank-mini` dari Pertemuan N-1" (tanpa menyebut
path checkpoint) tetap dipertahankan di setiap jobsheet; hanya path
repo-internal-nya yang dihapus. Jobsheet 1 masih memakai `cd bank-mini` di
kotak "Tanpa NetBeans?"-nya karena baris itu untuk MEMBUAT folder proyek
baru dari nol (`mkdir -p bank-mini/...`), bukan melanjutkan checkpoint
pertemuan sebelumnya, jadi sengaja dibiarkan.
