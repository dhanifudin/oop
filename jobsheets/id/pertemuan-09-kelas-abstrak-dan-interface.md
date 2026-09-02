# Jobsheet Praktikum: Pertemuan 9
## Kelas Abstrak dan Interface

| | |
|---|---|
| **Mata Kuliah** | Praktikum Pemrograman Berbasis Objek (RTI253008) |
| **Pertemuan** | 9 (Minggu 9) |
| **Durasi** | 1 &times; 4 &times; 50' praktikum; 1 &times; 1 &times; 50' tugas/laporan mandiri |
| **Kode Awal** | `code/bank-mini/pertemuan-07/` (checkpoint Pertemuan 7) |
| **Kode Akhir** | proyek `bank-mini` setelah Langkah 2, disalin sebagai checkpoint `code/bank-mini/pertemuan-09/` |

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, mahasiswa mampu:

1. Mendeklarasikan kelas abstrak dengan method abstrak yang wajib diimplementasikan setiap subclass.
2. Mendeklarasikan dan menerapkan interface pada kelas yang membutuhkan kontrak perilaku tertentu.

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, NetBeans (editor yang digunakan sepanjang praktikum ini).
- **Proyek**: pertemuan ini melanjutkan proyek `bank-mini` dari Pertemuan 7.
- **Verifikasi cepat** sebelum memulai:
  ```bash
  java -version
  javac -version
  ```
  Apabila keduanya menampilkan nomor versi tanpa galat, proses dapat dilanjutkan.

> **Tanpa NetBeans?** Jobsheet ini tetap dapat diikuti menggunakan editor teks biasa, lanjutkan folder `bank-mini/` dari Pertemuan 7:
> ```bash
> cd bank-mini
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> Checkpoint dan output yang dihasilkan tetap sama persis, apa pun editor yang digunakan.

## C. Langkah Kerja

### Langkah 1: Account Menjadi Kelas Abstrak

> **Konsep Singkat: Kelas Abstrak.** Sebuah kelas abstrak (`abstract class`) tidak boleh diinstansiasi langsung lewat `new`; ia hanya boleh menjadi superclass. Kelas abstrak boleh berisi method abstrak, yaitu method yang hanya dideklarasikan tanda tangannya (tanpa isi), dan setiap subclass konkret wajib menyediakan isinya sendiri. Contoh generik: kelas abstrak `Shape` mendeklarasikan `abstract double area()` tanpa tahu bagaimana cara menghitungnya, sementara `Circle` dan `Square` masing-masing mengimplementasikan rumus luasnya sendiri.

![Shape sebagai kelas abstrak, Circle dan Square mengimplementasikan area()](../assets/uml/p09-shape-abstract.png){width=70%}

Tidak ada satu pun `Account` polos yang pernah dibuat langsung di Bank Mini sejauh ini, semua instansiasi selalu berupa `SavingsAccount` atau `CheckingAccount`. Ini pertanda baik bahwa `Account` sebaiknya menjadi kelas abstrak. Tambahkan method abstrak `monthlyFee()`:

![Account.java menjadi abstract class dengan method abstrak monthlyFee](../assets/code/pertemuan-09/p09-01-account.png){width=65%}

`Bank` juga mendapat method baru untuk menampilkan biaya bulanan setiap rekening:

![Bank.java dengan method printMonthlyFees](../assets/code/pertemuan-09/p09-01-bank.png){width=65%}

Karena `Account` sekarang mendeklarasikan `monthlyFee()` sebagai abstrak, `SavingsAccount` dan `CheckingAccount` wajib mengimplementasikannya:

![SavingsAccount.java mengimplementasikan monthlyFee](../assets/code/pertemuan-09/p09-01-savingsaccount.png){width=65%}

![CheckingAccount.java mengimplementasikan monthlyFee](../assets/code/pertemuan-09/p09-01-checkingaccount.png){width=65%}

Perbarui `Main.java`:

![Main.java memanggil printMonthlyFees](../assets/code/pertemuan-09/p09-01-main.png){width=70%}

> ✅ **Checkpoint:** program mencetak `A001 fee: 0.0`, `A002 fee: 15000.0`, `A003 fee: 0.0`, `A004 fee: 15000.0`, sesuai jenis masing-masing rekening.

> ⚠️ **Jika gagal:** apabila muncul galat `SavingsAccount is not abstract and does not override abstract method monthlyFee()`, periksa apakah `monthlyFee()` benar-benar diimplementasikan di kedua subclass, dengan tanda tangan yang sama persis seperti yang dideklarasikan di `Account`.

### Langkah 2: InterestBearing, Interface untuk Rekening Berbunga

> **Konsep Singkat: Interface.** Sebuah `interface` mendeklarasikan kontrak method (tanda tangan tanpa isi) yang wajib dipenuhi kelas mana pun yang menyatakan `implements` terhadapnya, tanpa mewajibkan hubungan `extends` sama sekali. Berbeda dari kelas abstrak, sebuah kelas boleh meng-implement banyak interface sekaligus, sehingga interface cocok dipakai untuk kemampuan lintas hierarki kelas yang berbeda-beda.

Hanya rekening yang menghasilkan bunga yang perlu kemampuan `applyInterest()`, `CheckingAccount` tidak membutuhkannya. Daripada menambah method itu ke `Account` (yang berarti seluruh subclass mewarisinya, termasuk yang tidak relevan), deklarasikan sebagai interface tersendiri:

![InterestBearing.java](../assets/code/pertemuan-09/p09-02-interestbearing.png){width=55%}

![Account, SavingsAccount, dan interface InterestBearing](../assets/uml/p09-account-abstract.png){width=75%}

`SavingsAccount` menyatakan `implements InterestBearing` dan mengimplementasikan `applyInterest()`:

![SavingsAccount.java meng-implement InterestBearing](../assets/code/pertemuan-09/p09-02-savingsaccount.png){width=65%}

Perbarui `Main.java`:

![Main.java menguji applyInterest](../assets/code/pertemuan-09/p09-02-main.png){width=70%}

> ✅ **Checkpoint:** program menampilkan `Before interest: 100000.0` diikuti `After interest: 102000.0` (bunga 2% dari saldo 100000).

> ⚠️ **Jika gagal:** apabila muncul galat `SavingsAccount is not abstract and does not override abstract method applyInterest()`, periksa apakah `implements InterestBearing` dan isi `applyInterest()` sudah ditambahkan bersamaan; sebuah kelas yang menyatakan `implements` tetap wajib mengimplementasikan seluruh method dari interface tersebut.

## D. Tugas dan Deliverable

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- Screenshot output program setelah Langkah 2.
- **Tugas mandiri:**
  1. Bank memerlukan jejak audit untuk rekening yang berisiko negatif (rekening dengan overdraft). Tambahkan interface `Auditable`, lalu terapkan pada `CheckingAccount`:

     ![Auditable.java](../assets/code/pertemuan-09/p09-tugas-auditable.png){width=55%}

     ![CheckingAccount.java meng-implement Auditable](../assets/code/pertemuan-09/p09-tugas-checkingaccount.png){width=65%}

     Buktikan dengan memanggil `auditLog()` pada kedua `CheckingAccount` yang sudah ada di `Main.java` dan mencetak hasilnya.
  2. Jawab secara singkat (2-3 kalimat untuk masing-masing pertanyaan): (a) mengapa `applyInterest()` lebih cocok dideklarasikan sebagai interface `InterestBearing`, dibandingkan sebagai method abstrak langsung di `Account`? (b) `SavingsAccount` sekarang punya dua "kontrak" sekaligus, yaitu mewarisi `Account` (abstract class) dan meng-implement `InterestBearing` (interface). Apa perbedaan mendasar antara kedua jenis kontrak ini?

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot/output) | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | Interface `Auditable` benar dan jawaban konsep tepat | Interface ada meski jawaban belum lengkap |
