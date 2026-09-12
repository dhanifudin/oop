# Struktur direktori dan pipeline build

- `slides/id/`, `slides/en/`: slide Marp (`pertemuan-NN-<slug>.md`).
  `slides/assets/uml/`: diagram UML (PNG, hasil render PlantUML, dipakai
  bersama oleh `id/` dan `en/`). `slides/build.sh` merender ke PDF via
  `marp --pdf` (lihat `scripts/render-all.sh` untuk alur lengkap). Lihat
  [[slides]] untuk konvensi isi/gaya.
- `jobsheets/id/`, `jobsheets/en/`: jobsheet praktikum (`pertemuan-NN-<slug>.md`).
  `TEMPLATE.md` adalah kerangka baku. `jobsheets/assets/code/`: gambar
  cuplikan kode (PNG hasil render `scripts/render-code.py`, dipakai bersama
  `id/`/`en/`). `jobsheets/assets/code-src/`: sumber Java asli per langkah
  (satu sumber kebenaran; jangan edit gambar tanpa mengedit sumber ini dan
  merender ulang). `jobsheets/assets/uml/`: salinan diagram UML yang sama
  dengan `slides/assets/uml/`. `build.sh` merender ke PDF A4 (butuh `pandoc`
  + `lualatex`). Lihat [[jobsheets]] untuk konvensi isi/gaya.
- `code/bank-mini/pertemuan-NN/`: checkpoint proyek Bank Mini, snapshot
  lengkap yang bisa dijalankan/dibuka NetBeans, DIHASILKAN OTOMATIS oleh
  `scripts/build-checkpoints.py` dari `jobsheets/assets/code-src/` (jangan
  diedit manual, edit sumbernya lalu jalankan `make checkpoints`).
  Pertemuan 2-11: struktur `src/id/ac/polinema/*.java` polos (javac/java).
  Pertemuan 13-16: proyek Maven (`pom.xml` dari `scripts/pom-template.xml`,
  `src/main/java/id/ac/polinema/{model,repository,ui}/`); Pertemuan 15
  menambah dependency `org.xerial:sqlite-jdbc` dan
  `commons-dbutils:commons-dbutils` (dipakai lewat `QueryRunner` di
  `JdbcAccountRepository`/`JdbcUserRepository`, menggantikan
  `Connection`/`PreparedStatement`/`ResultSet` manual).

  `code/bank-mini-zips/pertemuan-NN-starter.zip`: checkpoint pertemuan
  praktikum SEBELUMNYA (bukan checkpoint pertemuan NN sendiri), dikemas
  jadi satu berkas zip (folder teratas `bank-mini-pertemuan-NN-starter/`)
  oleh `scripts/gen-checkpoint-zips.py`, ditautkan dari situs GitHub Pages
  (`scripts/gen-pages-index.py` + `.github/workflows/pages.yml`, lihat
  `https://dhanifudin.com/oop/`) supaya mahasiswa yang tertinggal bisa
  mengunduh titik awal yang benar untuk jobsheet yang sedang dibuka, tanpa
  git dan tanpa menunggu Dosen membagikan berkas secara manual. **Kode
  LENGKAP/jawaban akhir suatu pertemuan tidak pernah dipublikasikan atas
  nama pertemuan itu sendiri**, supaya mahasiswa yang membuka jobsheet
  tidak bisa mengunduh langsung jawabannya; kode akhir pertemuan N baru
  muncul di situs sebagai starter pertemuan berikutnya. Pertemuan
  praktikum pertama (01) tidak punya starter (proyek dibangun dari nol di
  jobsheet 1).

  **Gotcha nyata, ditemukan saat mengemas checkpoint 13-14 jadi zip (jauh
  lebih terlihat begitu file dikemas untuk diunduh langsung dibanding
  sekadar duduk di direktori lokal)**: reorganisasi paket
  `model`/`repository`/`ui` di Pertemuan 13 (lihat "Cakupan kelas Bank
  Mini" di [[bank-mini]]) tidak pernah menghapus salinan LAMA di paket
  induk. `build-checkpoints.py` menumpuk file lintas pertemuan dan hanya
  membuang satu file kalau langkah yang menambahkannya menyertakan
  manifest `.delete` (satu path relatif per baris, relatif terhadap
  `id/ac/polinema/`; lihat docstring skrip itu), langkah repackaging
  Pertemuan 13 tidak pernah membuat `.delete`-nya, sehingga checkpoint 13
  dan 14 diam-diam berisi SEMBILAN salinan basi (`Account.java`,
  `AccountRepository.java`, `CheckingAccount.java`, `Customer.java`,
  `InMemoryAccountRepository.java`, `InsufficientBalanceException.java`,
  `InterestBearing.java`, `SavingsAccount.java`, `Transaction.java`) di
  paket induk berdampingan dengan salinan benar di `model`/`repository`.
  (`Bank.java`/`Main.java` BUKAN basi, keduanya memang seharusnya tetap di
  paket induk.) Tetap berhasil dikompilasi (nama kelas berbeda paket,
  tidak bentrok), jadi baru ketahuan setelah benar-benar mendaftar isi
  checkpoint satu per satu, bukan dari galat compiler. Sudah diperbaiki
  lewat `jobsheets/assets/code-src/pertemuan-13/langkah-01/.delete`; kalau
  pertemuan mana pun ke depan memindahkan kelas antar paket, selalu
  tambahkan manifest `.delete` yang sesuai di langkah yang sama.
- `archive/pertemuan-11-solid/`: materi Pertemuan 11 versi lama (SOLID
  principles, domain pemrosesan pesanan), diarsipkan saat urutan lama
  (diturunkan dari PDF RPS) menempatkan Interface di minggu 11. Pemetaan
  topik resmi terbaru ("POKOK MATERI PBO 2026") mengembalikan SOLID ke
  minggu 11, tetapi jobsheet AKTIF untuk minggu itu tetap harus
  Bank-Mini-only (bukan memakai ulang domain pemrosesan pesanan lama);
  materi arsip ini hanya rujukan konsep/toy untuk slide, tidak ditautkan
  langsung dari jobsheet aktif mana pun.
- `assets/uml/src/*.puml`: sumber PlantUML (satu sumber kebenaran untuk
  semua diagram kelas; `_common.iuml` berisi skinparam bersama).
- `assets/screenshots/pertemuan-NN/*.png`: screenshot GUI asli (bukan hasil
  render dari sumber lain, lihat catatan verifikasi GUI di [[bank-mini]]),
  disalin ke `slides/assets/screenshots/` dan `jobsheets/assets/screenshots/`
  oleh `scripts/render-screenshots.sh`. Dipakai mulai Pertemuan 13 (fase GUI).
- `scripts/`:
  - `setup.sh` (venv + pygments/pillow)
  - `render-code.py` + `gen-manifest.py` (cuplikan kode -> gambar, dengan
    highlight baris baru/berubah, satu fungsi `rows_pNN()` per pertemuan)
  - `render-uml.sh` (PlantUML -> PNG, disalin ke `slides/assets/uml/` dan
    `jobsheets/assets/uml/`)
  - `render-screenshots.sh` (salin screenshot GUI ke `slides/`/`jobsheets/`)
  - `build-checkpoints.py` (susun snapshot `code/bank-mini/pertemuan-NN/`
    dari `jobsheets/assets/code-src/`, termasuk berkas `.form` sejak
    Pertemuan 13)
  - `gen-checkpoint-zips.py` (kemas checkpoint pertemuan N-1 sebagai
    starter pertemuan N, jadi
    `code/bank-mini-zips/pertemuan-NN-starter.zip`, bukan checkpoint N
    sendiri, supaya kode lengkap/jawaban pertemuan N tidak pernah
    dipublikasikan atas nama pertemuan itu; hanya modul standar Python,
    tidak perlu venv; secara sengaja mengecualikan `target/` dan metadata
    IDE seperti `.classpath`/`.project`/`.settings` andai direktori
    checkpoint pernah ikut ter-compile manual saat pengujian lokal)
  - `pom-template.xml` (kerangka `pom.xml` untuk checkpoint Maven)
  - `gen-pages-index.py` (susun `docs-site/` berisi seluruh PDF slide/
    jobsheet dan zip checkpoint plus `index.html` yang menautkannya, untuk
    diunggah `.github/workflows/pages.yml` ke GitHub Pages)
  - `render-all.sh` (jalankan semuanya lalu build seluruh PDF; TIDAK
    termasuk `gen-checkpoint-zips.py`/`gen-pages-index.py`, keduanya cuma
    dipanggil dari alur CI Pages, lihat `make zips` untuk menjalankannya
    manual)
- `docs/`: dokumen RPS resmi (PDF), tidak diubah oleh materi ini.
- Repo ini di-hosting di `github.com/dhanifudin/oop` (publik), dengan
  GitHub Actions (`.github/workflows/pages.yml`, runner `ubuntu-latest`)
  yang menjalankan seluruh pipeline lalu men-deploy ke GitHub Pages
  (`https://dhanifudin.com/oop/`) setiap push ke `main`. Semua yang
  di-gitignore di atas (checkpoint, gambar hasil render, PDF, zip) DIBUAT
  ULANG oleh CI itu sendiri, bukan disalin dari commit manapun.

## Gotcha rendering yang berlaku lintas slide dan jobsheet

- **Ukuran tampilan gambar di slide itu terpisah dari ukuran font di
  sumbernya, dan keduanya harus benar sebelum gambar terlihat besar di
  proyektor.** Rendered pixel size sebuah teks SVG = `font-size (unit SVG)
  x (lebar tampilan piksel / lebar viewBox)`. Menaikkan font-size
  SEKALIGUS ukuran seluruh elemen lain secara proporsional (termasuk
  viewBox) TIDAK mengubah apa pun secara visual, karena rasio
  font-ke-viewBox tetap sama; hanya menaikkan rasio itu (font lebih besar
  RELATIF terhadap viewBox yang sama, lewat kotak yang diperlebar atau
  margin yang dipangkas) yang benar-benar memperbesar tampilan akhir.
  Gambar dua-kolom (`.cols`) dibatasi lebar tampilan hanya ~554-560px oleh
  layout flex-nya sendiri, jauh lebih kecil dari anggaran tinggi
  (~420-460px) yang tersedia; mengonversi slide semacam itu jadi tumpukan
  (gambar penuh-lebar di atas, teks di bawah) jauh lebih murah dan rendah
  risiko daripada mendesain ulang SVG-nya, dan terbukti bekerja baik di
  seluruh dek. `img { max-height }` global dipakai 420px (naik dari
  320px), `.cols img` (bila masih dipakai) 460px.
- **PlantUML diam-diam MEMOTONG (bukan menskalakan atau memberi galat)
  diagram yang lebih lebar dari batas amannya (default 4096px).** Setelah
  `defaultFontSize` dinaikkan, beberapa diagram tiga-kelas melebihi batas
  ini dan terpotong tanpa peringatan (constructor signature ter-crop di
  tengah). `scripts/render-uml.sh` menaikkan batas ini lewat
  `JDK_JAVA_OPTIONS="-DPLANTUML_LIMIT_SIZE=8192"`; jangan hapus baris ini.
- **PlantUML butuh Graphviz (`dot`) terpasang eksplisit di CI, bukan cuma
  `plantuml`.** Paket `plantuml` Ubuntu hanya men-Recommends `graphviz`,
  bukan men-Depends, sehingga `apt-get install --no-install-recommends`
  (dipakai `.github/workflows/pages.yml` demi instalasi yang ramping)
  diam-diam melewatkannya; tanpa `dot`, seluruh diagram kelas (semua UML
  pertemuan pakai diagram kelas) gagal tampil dengan benar di situs Pages
  hasil build CI. Bug ini tidak pernah muncul saat verifikasi lokal di
  lingkungan Nix milik developer, karena wrapper PlantUML dari Nix men-set
  `GRAPHVIZ_DOT` secara eksplisit ke binary Graphviz miliknya sendiri;
  workflow CI sekarang menambahkan `graphviz` secara eksplisit ke daftar
  paket `apt-get install` demi konsistensi lingkungan.
- **Gambar penuh-lebar (bukan dua-kolom) yang diikuti paragraf/kotak teks
  panjang bisa meluber ke luar slide** setelah `max-height` global
  dinaikkan, karena gambar kini benar-benar lebih tinggi dari sebelumnya.
  Ini baru terlihat lewat rendering per-halaman yang sesungguhnya, tidak
  lewat membaca markdown-nya. Perbaikannya: beri directive `![h:NNN
  ...]` eksplisit pada gambar itu (bukan membiarkannya mengandalkan
  `max-height` global) untuk menyisakan ruang bagi teks di bawahnya. Lihat
  juga [[slides]] untuk perbedaan sintaks ukuran gambar Marp vs jobsheet.
