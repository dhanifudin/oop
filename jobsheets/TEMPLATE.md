# Jobsheet Praktikum: Pertemuan N
## <Judul Topik Pertemuan>

| | |
|---|---|
| **Mata Kuliah** | Praktikum Pemrograman Berbasis Objek (RTI253008) |
| **Pertemuan** | N (Minggu N) |
| **Durasi** | 1 &times; 4 &times; 50' praktikum; 1 &times; 1 &times; 50' tugas/laporan mandiri |
| **Kode Awal** | <tidak ada, proyek dibuat dari nol (Pertemuan 2)> ATAU `code/bank-mini/pertemuan-<N-1>/` (checkpoint minggu sebelumnya) |
| **Kode Akhir** | <deskripsi state akhir proyek, mis. "proyek Bank Mini setelah Langkah N", checkpoint disalin ke `code/bank-mini/pertemuan-N/`> |

<!--
Catatan penulisan (hapus komentar ini di jobsheet final):
- Bahasa Indonesia, sapaan "kamu", nada FORMAL instruksional (bahasa baku
  seorang dosen menulis di handout resmi), BUKAN prosa kaku hasil
  terjemahan kata per kata dan BUKAN pula gaya ngobrol/santai (hindari
  "nggak", "kayak", "gimana", "banget", "kok", "sih", pembuka basa-basi
  seperti "Nah,"/"Yuk,"). Hindari huruf kapital semua untuk penekanan
  ("TAPI JUGA", "HANYA") dan frasa transisi kaku ("Perhatikan bahwa...").
  Baca ulang tiap kalimat: apakah ini terdengar seperti tulisan
  instruksional formal, bukan terjemahan mekanis dan bukan pesan chat?
- Sebutan dosen memakai "Dosen" saja, tidak ada "asisten/Asisten".
- Jobsheet ini adalah bagian dari studi kasus tunggal semester, **Bank
  Mini** (lihat CLAUDE.md): setiap pertemuan menambah kelas/fitur baru ke
  proyek yang sama secara bertahap, bukan case study berdiri sendiri.
  Konsep yang benar-benar baru tetap diperkenalkan lewat contoh generik
  kecil terlebih dahulu (mis. Thermostat, Animal), baru diterapkan ke Bank
  Mini. Jangan perkenalkan sistem bernama lain yang bersaing dengan Bank
  Mini.
- TIDAK ADA git di jobsheet ini: tidak ada langkah git init/branch/commit,
  tidak ada baris git di tabel metadata, tidak ada deliverable git log.
  OOP adalah fokus mata kuliah ini, bukan version control. Mahasiswa yang
  tertinggal melanjutkan dari checkpoint `code/bank-mini/pertemuan-<N-1>/`
  yang dibagikan Dosen, bukan dari version control.
- Semua kode Java memakai package id.ac.polinema. Pertemuan 2-11 memakai
  javac/java langsung; Pertemuan 13 dan seterusnya (fase GUI/JDBC) beralih
  ke proyek Maven (mvn -q compile exec:java), dengan subpaket
  id.ac.polinema.model/repository/ui.
- Bila langkah pertama di pertemuan ini butuh konsep yang belum tuntas
  dibahas di kelas konsep (lihat aturan urutan di CLAUDE.md), buka langkah
  itu dengan kotak kutipan pendek "Konsep Singkat" (maksimal setengah
  halaman, narasi plus satu gambar/diagram, tanpa kode baru) sebelum kode.
- NetBeans adalah editor utama: tulis instruksi langkah kerja dalam istilah
  NetBeans (New Class, Run Project, F6, Run File), lalu tambahkan SATU kotak
  kutipan "Tanpa NetBeans?" di bagian B berisi alternatif baris perintah
  yang setara persis (javac/java atau mvn), supaya mahasiswa dengan editor
  teks biasa tetap bisa mengikuti checkpoint yang sama.
- Setiap langkah: narasi singkat "mengapa" sebelum kode, lalu GAMBAR kode
  (bukan blok kode teks) yang dirender dari jobsheets/assets/code-src/ lewat
  scripts/render-code.py (lihat CLAUDE.md), lalu Checkpoint, lalu (bila
  relevan) blok "Jika gagal". Baris kode yang baru/berubah pada suatu
  langkah disorot hijau otomatis oleh pipeline gambar; jangan coba
  mensimulasikan highlight itu secara manual di markdown.
- Diagram UML memakai gambar hasil render PlantUML dari assets/uml/src/,
  BUKAN ASCII art kotak kelas.
- Istilah teknis bahasa Inggris (dependency injection, constructor
  injection, interface, refactoring, code smell, dst.) tetap bahasa
  Inggris, tidak diterjemahkan.
-->

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, kamu mampu:

1. <capaian 1: kata kerja aktif, terukur>
2. <capaian 2>
3. <capaian 3>

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, NetBeans (editor utama). <tambahkan Maven bila pertemuan ini memakai proyek starter>
- **Kelanjutan kode**: <instruksi NetBeans: buka proyek, atau buat proyek baru>
- **Verifikasi cepat** sebelum mulai:
  ```bash
  <perintah verifikasi>
  ```

> **Tanpa NetBeans?** <alternatif baris perintah yang setara persis, mis. `javac -d out src/id/ac/polinema/*.java && java -cp out id.ac.polinema.Main`, atau `mvn -q compile exec:java` untuk proyek Maven>. Checkpoint dan output program di jobsheet ini sama persis, apa pun editornya.

## C. Langkah Kerja

### Langkah 1: <judul aksi>

<narasi singkat 1-3 kalimat: apa yang dilakukan langkah ini dan mengapa>

![<Nama berkas>.java: <deskripsi singkat isi/perubahan>](../assets/code/pertemuan-NN/<nama-gambar>.png){width=70%}

> ✅ **Checkpoint:** <output persis/kondisi yang menandakan langkah ini berhasil>

> ⚠️ **Jika gagal:** <gejala umum → penyebab → cara memperbaiki>

### Langkah 2: <judul aksi>

...

## D. Tugas dan Deliverable

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- <deliverable 1, mis. screenshot output program>
- **Tugas mandiri:** <1-2 latihan singkat yang dikerjakan di luar sesi kelas>

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot/output) | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | Jawaban lengkap dan tepat | Jawaban ada meski belum lengkap |
