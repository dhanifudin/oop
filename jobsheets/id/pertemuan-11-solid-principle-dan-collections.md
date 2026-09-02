# Jobsheet Praktikum: Pertemuan 11
## SOLID Principle dan Collections

| | |
|---|---|
| **Mata Kuliah** | Praktikum Pemrograman Berbasis Objek (RTI253008) |
| **Pertemuan** | 11 (Minggu 11) |
| **Durasi** | 1 &times; 4 &times; 50' praktikum; 1 &times; 1 &times; 50' tugas/laporan mandiri |
| **Kode Awal** | `code/bank-mini/pertemuan-10/` (checkpoint Pertemuan 10) |
| **Kode Akhir** | proyek `bank-mini` setelah Langkah 3, disalin sebagai checkpoint `code/bank-mini/pertemuan-11/` |

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, mahasiswa mampu:

1. Mengganti array biasa dengan `ArrayList`/`Map` dari Java Collections Framework, dan menjelaskan keuntungannya dibandingkan array.
2. Menerapkan Single Responsibility Principle dengan memisahkan tanggung jawab pencatatan transaksi ke kelas tersendiri.
3. Menerapkan Dependency Inversion Principle dengan membuat kelas bergantung pada interface, bukan pada implementasi konkret.

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, NetBeans (editor yang digunakan sepanjang praktikum ini).
- **Proyek**: pertemuan ini melanjutkan proyek `bank-mini` dari Pertemuan 10.
- **Verifikasi cepat** sebelum memulai:
  ```bash
  java -version
  javac -version
  ```
  Apabila keduanya menampilkan nomor versi tanpa galat, proses dapat dilanjutkan.

> **Tanpa NetBeans?** Jobsheet ini tetap dapat diikuti menggunakan editor teks biasa, lanjutkan folder `bank-mini/` dari Pertemuan 10:
> ```bash
> cd bank-mini
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> Checkpoint dan output yang dihasilkan tetap sama persis, apa pun editor yang digunakan.

## C. Langkah Kerja

### Langkah 1: Bank Beralih dari Array ke Map

> **Konsep Singkat: Collections.** Java Collections Framework menyediakan struktur data siap pakai seperti `ArrayList` (daftar yang ukurannya menyesuaikan otomatis, tidak perlu ditentukan di awal) dan `HashMap`/`LinkedHashMap` (menyimpan pasangan kunci-nilai, pencarian berdasarkan kunci dilakukan langsung tanpa memeriksa elemen satu per satu). Keduanya menggantikan array biasa, yang ukurannya tetap sejak dibuat dan pencariannya harus memeriksa elemen satu per satu.

![Array berukuran tetap dengan pencarian satu per satu, dibandingkan Map dengan pencarian langsung lewat kunci](../assets/uml/p11-collections-motivation.png){width=75%}

Sejauh ini, `Bank` menyimpan rekening di `Account[] accounts` berukuran tetap, `findAccount()` memeriksa elemen satu per satu. Ganti dengan `Map<String, Account>`, memakai nomor rekening sebagai kunci:

![Bank.java memakai LinkedHashMap menggantikan Account array](../assets/code/pertemuan-11/p11-01-bank.png){width=70%}

Perbarui `Main.java`, konstruktor `Bank` tidak lagi memerlukan kapasitas:

![Main.java membuat Bank tanpa parameter kapasitas](../assets/code/pertemuan-11/p11-01-main.png){width=70%}

> ✅ **Checkpoint:** output program tetap identik dengan Pertemuan 10 (baris `Withdrawal failed`, `Withdrawal succeeded`, `interest applied`, dan `monthly fee` untuk A001, A002, A003).

> ⚠️ **Jika gagal:** apabila muncul galat `incompatible types: Account cannot be converted to ...` pada perulangan, periksa apakah perulangan `for` memakai `accounts.values()` (bukan `accounts` secara langsung), sebab `Map` tidak bisa di-iterasi seperti array.

### Langkah 2: Transaction, Single Responsibility Principle

> **Konsep Singkat: Single Responsibility Principle.** Salah satu dari lima prinsip SOLID, Single Responsibility Principle, menyatakan bahwa satu kelas sebaiknya memiliki satu tanggung jawab, satu alasan untuk berubah. Kelas yang mencampur banyak tanggung jawab sekaligus (menghitung, memformat, mengirim, dan seterusnya) menjadi sulit dipahami dan setiap perubahan pada satu tanggung jawab berisiko memengaruhi tanggung jawab lain yang sebenarnya tidak berhubungan.

![Satu kelas dengan tiga tanggung jawab, dipisah menjadi tiga kelas masing-masing satu tanggung jawab](../assets/uml/p11-srp-split.png){width=72%}

Bank Mini sejauh ini tidak mencatat riwayat transaksi sama sekali. Tambahkan kelas `Transaction`, tanggung jawabnya hanya merepresentasikan satu transaksi:

![Transaction.java](../assets/code/pertemuan-11/p11-02-transaction.png){width=55%}

![Account dan Transaction, satu Account memiliki banyak Transaction](../assets/uml/p11-transaction.png){width=68%}

`Account` menyimpan daftar `Transaction` miliknya sendiri, ditambahkan setiap kali `deposit()` atau `withdraw()` berhasil:

![Account.java mencatat Transaction pada deposit dan withdraw](../assets/code/pertemuan-11/p11-02-account.png){width=68%}

`Bank` mendapat method untuk menampilkan riwayat satu rekening:

![Bank.java dengan method printHistory](../assets/code/pertemuan-11/p11-02-bank.png){width=68%}

Perbarui `Main.java`:

![Main.java memanggil printHistory](../assets/code/pertemuan-11/p11-02-main.png){width=70%}

> ✅ **Checkpoint:** program menambahkan baris `A003 WITHDRAW 30000.0` dan `A003 DEPOSIT 1400.0` setelah baris dari Langkah 1.

> ⚠️ **Jika gagal:** apabila riwayat transaksi kosong, periksa apakah `history.add(...)` dipanggil SETELAH validasi berhasil (di dalam `deposit()` dan `withdraw()`), bukan sebelum pengecekan `canWithdraw()`/jumlah minimum.

### Langkah 3: AccountRepository, Dependency Inversion Principle

> **Konsep Singkat: Dependency Inversion Principle.** Salah satu dari lima prinsip SOLID, Dependency Inversion Principle, menyatakan bahwa kelas tingkat tinggi (yang mengatur alur bisnis) sebaiknya bergantung pada interface (abstraksi), bukan pada kelas implementasi konkret secara langsung. Dengan begitu, implementasi konkretnya boleh diganti kapan saja tanpa mengubah satu baris pun kode yang bergantung padanya.

`Bank` sejauh ini menyimpan `Map<String, Account>` secara langsung di dalam dirinya sendiri. Pisahkan tanggung jawab penyimpanan ke interface tersendiri:

![AccountRepository.java](../assets/code/pertemuan-11/p11-03-accountrepository.png){width=55%}

![InMemoryAccountRepository.java](../assets/code/pertemuan-11/p11-03-inmemoryaccountrepository.png){width=65%}

`Bank` sekarang bergantung pada interface `AccountRepository`, bukan pada `Map` secara langsung:

![Bank.java bergantung pada AccountRepository](../assets/code/pertemuan-11/p11-03-bank.png){width=70%}

![Bank bergantung pada interface AccountRepository, diimplementasikan InMemoryAccountRepository](../assets/uml/p11-accountrepository.png){width=75%}

Perbarui `Main.java`:

![Main.java membuat Bank dengan InMemoryAccountRepository](../assets/code/pertemuan-11/p11-03-main.png){width=70%}

> ✅ **Checkpoint:** output program tetap identik dengan Langkah 2, tidak ada satu baris output pun yang berubah meskipun cara penyimpanan rekening berganti total.

> ⚠️ **Jika gagal:** apabila muncul galat `constructor Bank in class Bank cannot be applied to given types`, periksa apakah `Main.java` sudah memanggil `new Bank(new InMemoryAccountRepository())`, bukan `new Bank()` seperti Langkah 1-2.

## D. Tugas dan Deliverable

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- Screenshot output program setelah Langkah 3.
- **Tugas mandiri:**
  1. Tambahkan `Bank.totalAssets()`, mengembalikan `double` total saldo seluruh rekening (memakai `repository.findAll()`), lalu cetak hasilnya di `Main.java`:

     ![Bank.java dengan method totalAssets](../assets/code/pertemuan-11/p11-tugas-bank.png){width=68%}

  2. Jawab secara singkat (2-3 kalimat untuk masing-masing pertanyaan): (a) `findAccount()` sekarang mencari lewat `Map`, bukan lagi memeriksa array satu per satu. Jelaskan perbedaan kecepatan pencariannya secara konseptual. (b) Pertemuan 15 akan mengganti `InMemoryAccountRepository` dengan `JdbcAccountRepository` yang menyimpan data ke database. Jelaskan mengapa `Bank.java` tidak perlu diubah satu baris pun untuk pergantian itu, dan prinsip SOLID mana yang membuat ini mungkin.

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot/output) | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | `totalAssets()` benar, jawaban konsep tepat | Sebagian tugas selesai meski jawaban belum lengkap |
