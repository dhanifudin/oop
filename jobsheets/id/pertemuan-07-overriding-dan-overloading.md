# Jobsheet Praktikum: Pertemuan 7
## Overriding dan Overloading

| | |
|---|---|
| **Mata Kuliah** | Praktikum Pemrograman Berbasis Objek (RTI253008) |
| **Pertemuan** | 7 (Minggu 7) |
| **Durasi** | 1 &times; 4 &times; 50' praktikum; 1 &times; 1 &times; 50' tugas/laporan mandiri |
| **Kode Awal** | `code/bank-mini/pertemuan-06/` (checkpoint Pertemuan 6) |
| **Kode Akhir** | proyek `bank-mini` setelah Langkah 4, disalin sebagai checkpoint `code/bank-mini/pertemuan-07/` |

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, mahasiswa mampu:

1. Meng-override method warisan superclass di dalam subclass, ditandai anotasi `@Override`, termasuk memanggil versi superclass lewat `super.method(...)`.
2. Membedakan overriding dari overloading dengan menulis method yang sama namanya tetapi berbeda daftar parameternya.

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, NetBeans (editor yang digunakan sepanjang praktikum ini).
- **Proyek**: pertemuan ini melanjutkan proyek `bank-mini` dari Pertemuan 6.
- **Verifikasi cepat** sebelum memulai:
  ```bash
  java -version
  javac -version
  ```
  Apabila keduanya menampilkan nomor versi tanpa galat, proses dapat dilanjutkan.

> **Tanpa NetBeans?** Jobsheet ini tetap dapat diikuti menggunakan editor teks biasa, lanjutkan folder `bank-mini/` dari Pertemuan 6:
> ```bash
> cd bank-mini
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> Checkpoint dan output yang dihasilkan tetap sama persis, apa pun editor yang digunakan.

## C. Langkah Kerja

### Langkah 1: Account Menyediakan Titik untuk Di-override

Pekan lalu, `SavingsAccount` dan `CheckingAccount` masing-masing punya atribut (`interestRate`, `overdraftLimit`) yang belum benar-benar memengaruhi aturan penarikan, karena `withdraw()` yang diwarisi hanya tahu satu aturan generik: penarikan tidak boleh melebihi saldo. Agar setiap subclass bisa punya aturannya sendiri, pindahkan pemeriksaan itu ke method terpisah bertanda `protected` bernama `canWithdraw(double)`, lalu panggil method itu dari dalam `withdraw()`:

![Account.java, withdraw() memanggil canWithdraw() yang baru](../assets/code/pertemuan-07/p07-01-account.png){width=65%}

`protected` berarti method ini bisa dipanggil dan ditulis ulang oleh subclass, tetapi tetap tidak terlihat dari luar package seperti `public`. `withdraw()` sendiri tidak berubah perilakunya sama sekali untuk saat ini, sebab `canWithdraw(double)` masih mengembalikan aturan generik yang sama.

> ✅ **Checkpoint:** program masih berjalan dan menampilkan output yang persis sama seperti sebelumnya; perubahan ini murni penataan ulang (refactoring), belum mengubah perilaku apa pun.

> ⚠️ **Jika gagal:** apabila muncul galat `cannot find symbol: method canWithdraw`, periksa apakah nama method dan urutan parameter di pemanggilan dalam `withdraw()` sama persis dengan deklarasinya.

### Langkah 2: SavingsAccount Meng-override canWithdraw dan printInfo

Sekarang `SavingsAccount` bisa menulis ulang (override) `canWithdraw(double)` agar penarikan wajib menyisakan saldo minimum, dan menulis ulang `printInfo()` agar ikut mencetak jenis rekening dan suku bunganya:

![SavingsAccount.java meng-override canWithdraw dan printInfo](../assets/code/pertemuan-07/p07-02-savingsaccount.png){width=65%}

Anotasi `@Override` memberi tahu compiler untuk memeriksa bahwa method ini benar-benar menulis ulang method superclass dengan tanda tangan (nama dan parameter) yang sama persis; bila ada kesalahan ketik pada nama method, compiler akan menampilkan galat alih-alih diam-diam membuat method baru yang tidak pernah dipanggil. `printInfo()` memanggil `super.printInfo()` terlebih dahulu untuk mencetak bagian yang diwarisi, baru menambahkan barisnya sendiri, sehingga tidak perlu menulis ulang seluruh isi `printInfo()` dari awal. Perbarui `Main.java`:

![Main.java menguji penarikan yang melanggar saldo minimum](../assets/code/pertemuan-07/p07-02-main.png){width=70%}

> ✅ **Checkpoint:** program mencetak `Withdraw 70000 allowed? false`, diikuti `A003 - Rian - balance: 100000.0` dan `Account type: Savings, interest rate: 0.02`. Penarikan ditolak karena akan menyisakan saldo 30000, di bawah saldo minimum 50000.

> ⚠️ **Jika gagal:** apabila `@Override` menampilkan galat compile `method does not override a method from its superclass`, periksa kembali apakah tanda tangan method di subclass sama persis dengan yang dideklarasikan di `Account`.

### Langkah 3: CheckingAccount Meng-override canWithdraw dan printInfo

Terapkan pola yang sama pada `CheckingAccount`, kali ini aturannya membolehkan penarikan melebihi saldo hingga batas overdraft:

![CheckingAccount.java meng-override canWithdraw dan printInfo](../assets/code/pertemuan-07/p07-03-checkingaccount.png){width=65%}

Perbarui `Main.java` untuk membuktikan bahwa penarikan yang pekan lalu ditolak sekarang diperbolehkan:

![Main.java menguji penarikan melebihi saldo lewat overdraft](../assets/code/pertemuan-07/p07-03-main.png){width=70%}

> ✅ **Checkpoint:** program mencetak `Withdraw 250000 allowed? true`, lalu `printAllAccounts()` menampilkan `A003 - Rian - balance: 100000.0` diikuti `Account type: Savings, interest rate: 0.02`, dan `A004 - Dewi - balance: -150000.0` diikuti `Account type: Checking, overdraft limit: 200000.0`. Penarikan 250000 dari saldo 100000 kini diperbolehkan karena masih dalam batas overdraft 200000, sesuatu yang pekan lalu ditolak sebelum `canWithdraw()` di-override.

> ⚠️ **Jika gagal:** apabila saldo `checking` tidak pernah menjadi negatif walaupun overdraft seharusnya mengizinkannya, periksa apakah `canWithdraw()` di `CheckingAccount` membandingkan `amount` terhadap `getBalance() + overdraftLimit`, bukan `getBalance()` saja.

### Langkah 4: Overloading, deposit() dengan Catatan

Overriding menulis ulang method yang sudah ada di superclass dengan tanda tangan yang sama. Overloading berbeda: menambahkan method dengan nama yang sama tetapi daftar parameter yang berbeda, dan compiler memilih versi mana yang dipanggil berdasarkan argumen yang diberikan, bukan berdasarkan jenis objek saat program berjalan. Tambahkan versi kedua dari `deposit()` di `Account` yang menerima catatan tambahan:

![Account.java, deposit(double, String) sebagai overload dari deposit(double)](../assets/code/pertemuan-07/p07-04-account.png){width=65%}

Perbarui `Main.java` untuk memanggil versi baru ini:

![Main.java memanggil deposit dengan catatan](../assets/code/pertemuan-07/p07-04-main.png){width=70%}

> ✅ **Checkpoint:** program mencetak `A003 deposit note: Initial top-up`, diikuti `A003 - Rian - balance: 150000.0` dan `Account type: Savings, interest rate: 0.02`.

> ⚠️ **Jika gagal:** apabila muncul galat `reference to deposit is ambiguous`, periksa apakah kedua method `deposit` benar-benar berbeda daftar parameternya (jumlah atau tipe), bukan sekadar berbeda nama parameter.

## D. Tugas dan Deliverable

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- Screenshot output program setelah Langkah 4.
- **Tugas mandiri:**
  1. Terapkan pola overriding yang sama pada `BusinessAccount` dari Pertemuan 6: override `canWithdraw(double)` agar penarikan wajib menyisakan saldo minimum sebesar 1000000, dan override `printInfo()` agar ikut mencetak jenis rekening:

     ![BusinessAccount.java meng-override canWithdraw dan printInfo](../assets/code/pertemuan-07/p07-tugas-businessaccount.png){width=65%}

     Buktikan dengan membuat satu `BusinessAccount` bersaldo 2000000, mencoba menarik 1500000 (harus ditolak), lalu mencetak info lengkapnya.
  2. Jawab secara singkat (2-3 kalimat): berdasarkan `canWithdraw()`/`printInfo()` (overriding) dan `deposit()` (overloading) yang baru saja kamu buat, apa yang membedakan keduanya dari sisi tanda tangan (signature) method, dan dari sisi kapan Java menentukan method mana yang benar-benar dipanggil?

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot/output) | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | Override `BusinessAccount` benar dan jawaban konsep tepat | Override ada meski jawaban belum lengkap |
