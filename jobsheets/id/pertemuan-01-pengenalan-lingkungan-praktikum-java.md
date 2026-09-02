# Jobsheet Praktikum: Pertemuan 1
## Pengenalan Lingkungan Praktikum Java dan IDE

| | |
|---|---|
| **Mata Kuliah** | Praktikum Pemrograman Berbasis Objek (RTI253008) |
| **Pertemuan** | 1 (Minggu 1) |
| **Durasi** | 1 &times; 4 &times; 50' praktikum; 1 &times; 1 &times; 50' tugas/laporan mandiri |
| **Kode Awal** | tidak ada, proyek dibuat dari nol |
| **Kode Akhir** | proyek `bank-mini` setelah Langkah 3, disalin sebagai checkpoint `code/bank-mini/pertemuan-01/` |

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, kamu mampu:

1. Memverifikasi instalasi JDK dan NetBeans di komputer sendiri.
2. Membuat proyek Java pertama dengan struktur package `id.ac.polinema`.
3. Menjelaskan siklus compile-run (`javac` lalu `java`) dan mempraktikkannya, baik lewat NetBeans maupun baris perintah.

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, NetBeans (editor utama sepanjang mata kuliah ini).
- **Proyek**: pada pertemuan ini, sebuah proyek baru bernama `bank-mini` dibuat. Proyek inilah yang akan tumbuh menjadi aplikasi Bank Mini lengkap sepanjang semester, sehingga seluruh kelas Java ditempatkan di dalam package `id.ac.polinema` (konvensi penamaan package Java standar: domain institusi yang dibalik, "polinema.ac.id" menjadi `id.ac.polinema`).
- **Verifikasi cepat** sebelum mulai:
  ```bash
  java -version
  javac -version
  ```
  Jika keduanya menampilkan nomor versi 17 atau lebih tanpa galat, proses dapat dilanjutkan.

> **Tanpa NetBeans?** Jobsheet ini tetap dapat diikuti dengan editor teks biasa:
> ```bash
> mkdir -p bank-mini/src/id/ac/polinema
> cd bank-mini
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> Checkpoint dan output program tetap sama persis, apa pun editornya.

## C. Langkah Kerja

### Langkah 1: Memverifikasi Instalasi JDK dan NetBeans

JDK (Java Development Kit) menyediakan dua alat utama: `javac` (compiler, mengubah kode sumber `.java` menjadi bytecode `.class`) dan `java` (menjalankan bytecode tersebut). NetBeans memakai kedua alat ini di baliknya, tetapi tetap penting memastikan keduanya sudah terpasang dengan benar sebelum membuka NetBeans.

Buka terminal (atau Command Prompt), lalu jalankan perintah verifikasi pada bagian B. Buka juga NetBeans untuk memastikan aplikasinya bisa berjalan.

> ✅ **Checkpoint:** `java -version` dan `javac -version` menampilkan nomor versi yang sama, 17 atau lebih tinggi. NetBeans terbuka tanpa galat.

> ⚠️ **Jika gagal:** jika muncul pesan `command not found` atau `'java' is not recognized`, JDK belum terpasang atau lokasinya belum ditambahkan ke PATH sistem. Pasang ulang JDK dan pastikan opsi penambahan ke PATH dicentang saat instalasi.

### Langkah 2: Membuat Proyek `bank-mini`

Buka NetBeans, buat proyek baru bertipe **Java Application**, dan beri nama `bank-mini`. Saat NetBeans meminta nama package untuk kelas utama, isi dengan `id.ac.polinema`. Struktur folder dan berkas `Main.java` akan dibuat otomatis di dalam package tersebut.

Ganti isi `Main.java` dengan kode berikut:

![Main.java: mencetak pesan selamat datang](../assets/code/pertemuan-01/p01-02-main.png){width=70%}

Jalankan proyek (klik kanan proyek > Run, atau tekan F6). Bila memakai terminal: `javac -d out src/id/ac/polinema/*.java && java -cp out id.ac.polinema.Main`.

> ✅ **Checkpoint:** proyek `bank-mini` tampil di panel Projects dengan package `id.ac.polinema` berisi `Main.java`, dan program menampilkan tulisan `Welcome to Bank Mini!`.

> ⚠️ **Jika gagal:** jika muncul `error: class Main is public, should be declared in a file named Main.java`, periksa apakah nama berkas persis sama dengan nama class (`Main.java` untuk `class Main`). Java bersifat case-sensitive, huruf besar dan kecil harus cocok persis.

### Langkah 3: Memahami Siklus Compile dan Run

Kode Java tidak langsung dijalankan seperti bahasa scripting. Ada dua tahap terpisah: **compile** (`javac` membaca berkas `.java` dan menghasilkan berkas `.class` berisi bytecode) dan **run** (`java` menjalankan bytecode tersebut). NetBeans menjalankan kedua tahap ini secara otomatis di balik layar setiap kali tombol Run ditekan, tetapi memahami kedua tahap ini secara terpisah penting untuk menelusuri galat di kemudian hari: galat compile berarti kode belum berhasil diterjemahkan sama sekali, sedangkan galat run berarti kode sudah berhasil diterjemahkan tetapi bermasalah saat dijalankan.

Tambahkan satu baris lagi ke `Main.java`:

![Main.java dengan baris cetak tambahan](../assets/code/pertemuan-01/p01-03-main.png){width=70%}

Jalankan kembali proyek.

> ✅ **Checkpoint:** program mencetak dua baris, `Welcome to Bank Mini!` diikuti `This program was prepared by Nadia.` (atau namamu sendiri, bila diganti).

> ⚠️ **Jika gagal:** jika hanya baris pertama yang tampil atau muncul galat `';' expected`, periksa apakah tanda titik koma di akhir setiap pernyataan `System.out.println(...)` sudah lengkap.

## D. Tugas dan Deliverable

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- Screenshot output program setelah Langkah 3.
- **Tugas mandiri:**
  1. Ganti baris kedua pada `Main.java` sehingga menampilkan namamu sendiri, lalu jalankan ulang dan sertakan screenshot hasilnya.
  2. Jawab singkat (2-3 kalimat per pertanyaan): (a) apa perbedaan antara proses compile dan proses run pada Java? (b) apa yang terjadi jika nama berkas `.java` tidak sama dengan nama class public di dalamnya?

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot/output) | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | Jawaban lengkap dan tepat | Jawaban ada meski belum lengkap |
