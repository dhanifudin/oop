# Jobsheet Praktikum: Pertemuan 3
## Enkapsulasi dan Konstruktor

| | |
|---|---|
| **Mata Kuliah** | Praktikum Pemrograman Berbasis Objek (RTI253008) |
| **Pertemuan** | 3 (Minggu 3) |
| **Durasi** | 1 &times; 4 &times; 50' praktikum; 1 &times; 1 &times; 50' tugas/laporan mandiri |
| **Kode Awal** | `code/bank-mini/pertemuan-02/` (checkpoint Pertemuan 2) |
| **Kode Akhir** | proyek `bank-mini` setelah Langkah 2, disalin sebagai checkpoint `code/bank-mini/pertemuan-03/` |

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, mahasiswa mampu:

1. Menjelaskan risiko atribut publik dan menerapkan encapsulation (atribut `private`, akses lewat method).
2. Menulis getter dan setter, termasuk method yang memvalidasi nilai masukan.
3. Menulis lebih dari satu constructor untuk kelas yang sama (overloading constructor).

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, NetBeans (editor yang digunakan sepanjang praktikum ini).
- **Proyek**: pertemuan ini melanjutkan proyek `bank-mini` dari Pertemuan 2.
- **Verifikasi cepat** sebelum memulai:
  ```bash
  java -version
  javac -version
  ```
  Apabila keduanya menampilkan nomor versi tanpa galat, proses dapat dilanjutkan.

> **Tanpa NetBeans?** Jobsheet ini tetap dapat diikuti menggunakan editor teks biasa, lanjutkan folder `bank-mini/` dari Pertemuan 2:
> ```bash
> cd bank-mini
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> Checkpoint dan output yang dihasilkan tetap sama persis, apa pun editor yang digunakan.

## C. Langkah Kerja

### Langkah 1: Melanjutkan Proyek, Membersihkan Rectangle

Proyek `bank-mini` dari Pertemuan 2 dilanjutkan pada pertemuan ini. Kelas `Rectangle` hanya berperan sebagai contoh pengantar pada Pertemuan 2 dan tidak menjadi bagian dari Bank Mini, jadi hapus berkas `Rectangle.java` beserta baris pengujiannya (blok array `Rectangle[]`) di `Main.java`, dengan tetap mempertahankan bagian pengujian `Account`:

![Main.java setelah blok pengujian Rectangle dihapus](../assets/code/pertemuan-03/p03-01-main.png){width=70%}

> ✅ **Checkpoint:** program masih mengompilasi dan berjalan, menampilkan `Nadia - balance: 350000.0` seperti sebelumnya, tanpa berkas `Rectangle.java` di package `id.ac.polinema`.

### Langkah 2: Menerapkan Encapsulation ke Account

`Account` dari Pertemuan 2 masih memiliki atribut publik `ownerName` dan `balance`: kode lain dapat langsung menulis `acc.balance = -999999;` tanpa melalui `deposit()`/`withdraw()`, tanpa ada satu titik pun yang memvalidasi nilainya. Ganti isi `Account.java` sesuai diagram kelas yang telah dibahas di slide konsep: atribut privat, tambahan atribut `accountNumber`, dua constructor (dengan dan tanpa saldo awal), getter untuk setiap atribut, serta `deposit()`/`withdraw()` yang memvalidasi nilai masukan dan mengembalikan `boolean`:

![Account.java setelah encapsulation diterapkan](../assets/code/pertemuan-03/p03-02-account.png){width=65%}

Karena constructor kini mewajibkan data lengkap, perbarui pengujian di `Main.java`:

![Main.java memakai constructor Account yang baru](../assets/code/pertemuan-03/p03-02-main.png){width=70%}

> ✅ **Checkpoint:** program berhasil dikompilasi ulang dan menampilkan `A001 - Nadia - balance: 350000.0`.

> ⚠️ **Jika gagal:** apabila muncul galat `constructor Account in class Account cannot be applied to given types`, periksa apakah jumlah dan urutan argumen pada `new Account(...)` sudah sesuai dengan salah satu dari dua constructor yang tersedia.

## D. Tugas dan Deliverable

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- Screenshot output program setelah Langkah 2.
- **Tugas mandiri:**
  1. `Account` yang kamu buat belum membatasi jumlah penarikan per hari. Tambahkan atribut privat `dailyWithdrawalLimit` (diisi lewat constructor), lalu ubah `withdraw()` agar juga menolak penarikan yang melebihi batas ini, selain aturan saldo yang sudah ada:

     ![Account.java dengan tambahan dailyWithdrawalLimit](../assets/code/pertemuan-03/p03-tugas-account.png){width=65%}

     Buktikan dengan membuat satu `Account` bersaldo 1000000 dan batas harian 200000 di `Main`, lalu coba tarik 300000 (harus ditolak) dan 150000 (harus berhasil):

     ![Main.java menguji dailyWithdrawalLimit](../assets/code/pertemuan-03/p03-tugas-main.png){width=70%}
  2. Jawab secara singkat (2-3 kalimat untuk masing-masing pertanyaan): (a) mengapa mengembalikan nilai `boolean` dari `deposit()`/`withdraw()` lebih aman dibandingkan tidak memberi tahu pemanggil sama sekali ketika nilainya ditolak? (b) sebutkan satu atribut pada `Account` yang menurutmu sebaiknya hanya memiliki getter, tanpa setter, dan jelaskan alasannya.

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot/output) | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | Batas penarikan harian benar dan jawaban konsep tepat | Batas penarikan harian ada meski jawaban belum lengkap |
