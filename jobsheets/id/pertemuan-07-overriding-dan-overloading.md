# Jobsheet Praktikum: Pertemuan 7
## Overriding dan Overloading

| | |
|---|---|
| **Mata Kuliah** | Praktikum Pemrograman Berbasis Objek (RTI253008) |
| **Pertemuan** | 7 (Minggu 7) |
| **Durasi** | 1 &times; 4 &times; 50' praktikum; 1 &times; 1 &times; 50' tugas/laporan mandiri |

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, mahasiswa mampu:

1. Meng-override method warisan di dalam subclass, ditandai anotasi `@Override`, termasuk memanggil versi superclass lewat `super.method(...)`.
2. Membedakan overriding dari overloading dengan menulis method yang namanya sama tetapi daftar parameternya berbeda.

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, NetBeans (editor yang digunakan sepanjang praktikum ini).
- **Proyek**: jobsheet ini melanjutkan proyek `bank-mini` dari topik Inheritance. Proyek itu sudah berisi `Account`, `SavingsAccount`, `CheckingAccount`, `Customer`, `Bank`, dan `Main`.

> **Tanpa NetBeans?** Jobsheet ini tetap dapat diikuti menggunakan editor teks biasa:
> ```bash
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> Checkpoint dan output yang dihasilkan tetap sama persis, apa pun editor yang digunakan.

## C. Langkah Kerja

Diagram berikut adalah hasil akhir yang dituju jobsheet ini:

![Diagram kelas Account, SavingsAccount, dan CheckingAccount setelah overriding](../assets/uml/p07-account-hierarchy.png){width=70%}

Cara membaca diagram:

- Tanda `#` berarti `protected`: boleh dipakai dan ditulis ulang oleh subclass.
- `canWithdraw()` dan `printInfo()` tertulis di `Account`, lalu tertulis lagi di kedua subclass. Method yang tertulis lagi di subclass berarti di-override.

> **Konsep Singkat: Overriding.** Bayangkan resep keluarga: seorang anak memasak hidangan dengan nama yang sama, tetapi memakai resepnya sendiri. Overriding bekerja seperti itu. Subclass menulis ulang method warisan dengan nama dan parameter yang sama, hanya isinya yang berbeda. Nama method ditambah daftar parameternya disebut **signature**.

### Langkah 1: Account Menyediakan Titik untuk Di-override

**Tujuan langkah ini:** memisahkan aturan "boleh menarik atau tidak" ke method tersendiri, supaya nanti tiap jenis rekening bisa punya aturannya sendiri.

Saat ini `withdraw()` di `Account` hanya tahu satu aturan: penarikan tidak boleh melebihi saldo. Atribut `interestRate` dan `overdraftLimit` di subclass belum berpengaruh apa pun.

1. Buka `Account.java`.
2. Tambahkan method baru `canWithdraw(double amount)` bertanda `protected`. Isinya aturan lama: jumlah harus positif dan tidak melebihi saldo.
3. Ubah `withdraw()` supaya memanggil `canWithdraw(amount)`.

![Account.java, withdraw() memanggil canWithdraw() yang baru](../assets/code/pertemuan-07/p07-01-account.png){width=65%}

Jalankan program tanpa mengubah `Main.java`.

**Output yang diharapkan:**

```text
A001 - Nadia - balance: 350000.0
A002 - Sari - balance: 200000.0
```

**Mengapa demikian?** Output-nya sama seperti sebelum langkah ini. `canWithdraw()` masih berisi aturan lama, jadi perilaku program belum berubah. Langkah ini hanya menata ulang kode (refactoring) supaya langkah berikutnya bisa dilakukan.

> ✅ **Checkpoint:** program berjalan dan output-nya sama dengan blok di atas.

> ⚠️ **Jika gagal:** apabila muncul error `cannot find symbol: method canWithdraw`, periksa apakah nama method dan parameternya di dalam `withdraw()` sama persis dengan deklarasinya.

### Langkah 2: SavingsAccount Meng-override canWithdraw dan printInfo

**Tujuan langkah ini:** membuat rekening tabungan menjaga saldo minimum 50000, dan mencetak info yang lebih lengkap.

1. Buka `SavingsAccount.java`.
2. Tulis ulang `canWithdraw(double amount)` dengan anotasi `@Override`. Aturan barunya: saldo setelah penarikan tidak boleh kurang dari 50000.
3. Tulis ulang `printInfo()` dengan anotasi `@Override`. Panggil `super.printInfo()` lebih dulu, lalu cetak jenis rekening dan suku bunganya.

![SavingsAccount.java meng-override canWithdraw dan printInfo](../assets/code/pertemuan-07/p07-02-savingsaccount.png){width=65%}

4. Perbarui `Main.java`:

![Main.java menguji penarikan yang melanggar saldo minimum](../assets/code/pertemuan-07/p07-02-main.png){width=70%}

**Output yang diharapkan:**

```text
Withdraw 70000 allowed? false
A003 - Rian - balance: 100000.0
Account type: Savings, interest rate: 0.02
```

**Mengapa demikian?**

- Penarikan 70000 dari saldo 100000 akan menyisakan 30000. Jumlah itu di bawah saldo minimum 50000, jadi `canWithdraw()` milik `SavingsAccount` mengembalikan `false`.
- Baris kedua dicetak oleh `super.printInfo()`, yaitu versi milik `Account`. Baris ketiga adalah tambahan dari `SavingsAccount`.
- `@Override` meminta compiler memeriksa bahwa method itu memang ada di superclass dengan signature yang sama. Salah ketik nama method langsung ketahuan.

> ✅ **Checkpoint:** output program sama dengan blok di atas.

> ⚠️ **Jika gagal:** apabila muncul error `method does not override a method from its superclass`, periksa apakah signature method di subclass sama persis dengan yang ada di `Account`.

### Langkah 3: CheckingAccount Meng-override canWithdraw dan printInfo

**Tujuan langkah ini:** membuat rekening giro boleh ditarik melebihi saldo, sampai batas `overdraftLimit`.

1. Buka `CheckingAccount.java`.
2. Tulis ulang `canWithdraw(double amount)` dan `printInfo()` dengan pola yang sama seperti Langkah 2. Aturan barunya: penarikan boleh sampai saldo ditambah `overdraftLimit`.

![CheckingAccount.java meng-override canWithdraw dan printInfo](../assets/code/pertemuan-07/p07-03-checkingaccount.png){width=65%}

3. Perbarui `Main.java`:

![Main.java menguji penarikan melebihi saldo lewat overdraft](../assets/code/pertemuan-07/p07-03-main.png){width=70%}

**Output yang diharapkan:**

```text
Withdraw 250000 allowed? true
A003 - Rian - balance: 100000.0
Account type: Savings, interest rate: 0.02
A004 - Dewi - balance: -150000.0
Account type: Checking, overdraft limit: 200000.0
```

**Mengapa demikian?**

- Saldo A004 adalah 100000 dan batas overdraft-nya 200000. Penarikan 250000 masih dalam batas, jadi diizinkan dan saldonya menjadi -150000.
- `Bank.printAllAccounts()` tidak diubah sama sekali. Method itu tetap memanggil `printInfo()` untuk tiap rekening. Java menjalankan versi milik objeknya: versi `SavingsAccount` untuk A003, versi `CheckingAccount` untuk A004.

> ✅ **Checkpoint:** output program sama dengan blok di atas, termasuk saldo negatif pada A004.

> ⚠️ **Jika gagal:** apabila saldo A004 tidak pernah menjadi negatif, periksa apakah `canWithdraw()` di `CheckingAccount` membandingkan `amount` dengan `getBalance() + overdraftLimit`, bukan dengan `getBalance()` saja.

### Langkah 4: Overloading, deposit() dengan Catatan

> **Konsep Singkat: Overloading.** Di kasir, satu kata "bayar" berlaku untuk tunai, kartu, dan QR. Kasir memilih caranya dari apa yang diserahkan. Overloading bekerja seperti itu: beberapa method memakai nama yang sama, tetapi daftar parameternya berbeda. Compiler memilih versinya dari argumen yang diberikan saat method dipanggil.

**Tujuan langkah ini:** menambah cara kedua untuk menyetor, yaitu setoran yang disertai catatan.

1. Buka `Account.java`.
2. Tambahkan method `deposit(double amount, String note)`. Method ini mencetak catatannya, lalu memanggil `deposit(amount)` yang sudah ada.

![Account.java, deposit(double, String) sebagai overload dari deposit(double)](../assets/code/pertemuan-07/p07-04-account.png){width=65%}

3. Perbarui `Main.java`:

![Main.java memanggil deposit dengan catatan](../assets/code/pertemuan-07/p07-04-main.png){width=70%}

**Output yang diharapkan:**

```text
A003 deposit note: Initial top-up
A003 - Rian - balance: 150000.0
Account type: Savings, interest rate: 0.02
```

**Mengapa demikian?** Pemanggilan `deposit(50000, "Initial top-up")` membawa dua argumen, jadi compiler memilih versi dua parameter. Versi itu mencetak catatan, lalu menyerahkan penambahan saldo ke `deposit(double)`. Kedua versi tetap bisa dipakai.

> ✅ **Checkpoint:** output program sama dengan blok di atas.

> ⚠️ **Jika gagal:** apabila muncul error `reference to deposit is ambiguous`, periksa apakah kedua method `deposit` benar-benar berbeda daftar parameternya (jumlah atau tipe), bukan hanya berbeda nama parameter.

## D. Tugas dan Hasil Kerja

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- Screenshot output program setelah Langkah 4.
- **Tugas mandiri:**
  1. Terapkan pola overriding yang sama pada `BusinessAccount` yang kamu buat pada topik Inheritance. Override `canWithdraw(double)` agar penarikan wajib menyisakan saldo minimum 1000000, dan override `printInfo()` agar ikut mencetak jenis rekening. Diagram berikut hanya sketsa method yang perlu ditulis ulang, BUKAN kode jadi, isinya diserahkan sepenuhnya padamu:

     ![Sketsa BusinessAccount yang meng-override canWithdraw dan printInfo](../assets/uml/p07-tugas-businessaccount.png){width=85%}

     Buktikan lewat `Main.java`: buat satu `BusinessAccount` bersaldo 2000000, coba tarik 1500000, lalu cetak infonya. Hasilmu benar apabila:
     - penarikan 1500000 ditolak;
     - `printInfo()` mencetak baris milik `Account`, diikuti baris jenis rekening;
     - kedua method memakai `@Override`.
  2. Jawab secara singkat (1-2 kalimat untuk masing-masing pertanyaan):
     - (a) Apa perbedaan signature antara method yang di-override (`canWithdraw()`, `printInfo()`) dan method yang di-overload (`deposit()`)?
     - (b) Siapa yang menentukan versi method yang dijalankan pada overriding, dan siapa pada overloading?

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot/output) | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | Override `BusinessAccount` benar dan jawaban konsep tepat | Override ada meski jawaban belum lengkap |
