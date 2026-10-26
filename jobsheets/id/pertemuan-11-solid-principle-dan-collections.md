# Jobsheet Praktikum: Pertemuan 11
## SOLID Principle dan Collections

| | |
|---|---|
| **Mata Kuliah** | Praktikum Pemrograman Berbasis Objek (RTI253008) |
| **Pertemuan** | 11 (Minggu 11) |
| **Durasi** | 1 &times; 4 &times; 50' praktikum; 1 &times; 1 &times; 50' tugas/laporan mandiri |

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, mahasiswa mampu:

1. Mengganti array biasa dengan `ArrayList`/`Map` dari Java Collections Framework, dan menjelaskan keuntungannya dibandingkan array.
2. Menerapkan Single Responsibility Principle dengan memisahkan tanggung jawab pencatatan transaksi ke kelas tersendiri.
3. Menerapkan Dependency Inversion Principle dengan membuat kelas bergantung pada interface, bukan pada implementasi konkret.
4. Menelusuri dan membuktikan Open/Closed Principle, Liskov Substitution Principle, dan Interface Segregation Principle lewat kode `Account`/`Bank` yang sudah dibangun sejak topik Inheritance hingga Polimorfisme.
5. Menambahkan subclass `Account` baru tanpa mengubah kode `Account`/`Bank` yang sudah ada, sebagai bukti langsung kelima prinsip SOLID bekerja bersama.

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, NetBeans (editor yang digunakan sepanjang praktikum ini).
- **Proyek**: pertemuan ini melanjutkan proyek `bank-mini` dari topik Polimorfisme dan Exception Handling.

> **Tanpa NetBeans?** Jobsheet ini tetap dapat diikuti menggunakan editor teks biasa:
> ```bash
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> Checkpoint dan output yang dihasilkan tetap sama persis, apa pun editor yang digunakan.

## C. Langkah Kerja

### Langkah 1: Bank Beralih dari Array ke Map

> **Konsep Singkat: Collections.** Array biasa berukuran tetap sejak dibuat, dan pencariannya memeriksa elemen satu per satu. Java Collections Framework menyediakan penggantinya. `ArrayList` adalah daftar yang ukurannya menyesuaikan otomatis. `Map` menyimpan pasangan kunci dan nilai, sehingga pencarian lewat kunci bisa langsung. Bank Mini memakai `LinkedHashMap`, yaitu `Map` yang menjaga urutan elemen sesuai urutan penambahan.

> **Konsep Singkat: Generics.** Tulisan `<...>` pada `Map<String, Account>` disebut *type parameter*. Isinya menyatakan tipe apa yang boleh disimpan. Pada `Map<String, Account>`, kuncinya harus `String` dan nilainya harus `Account`. Bila tipenya salah, compiler langsung menolak kode itu. Tanpa `<...>`, kesalahan baru muncul sebagai `ClassCastException` saat program berjalan.

![Array berukuran tetap dengan pencarian satu per satu, dibandingkan Map dengan pencarian langsung lewat kunci](../assets/uml/p11-collections-motivation.png){width=75%}

**Tujuan langkah ini:** mengganti penyimpanan rekening dari array ke `Map`, tanpa mengubah output program.

Sejauh ini, `Bank` menyimpan rekening di `Account[] accounts` berukuran tetap, dan `findAccount()` memeriksa elemen satu per satu. Dengan `Map<String, Account>`, nomor rekening menjadi kunci, sehingga pencarian langsung.

1. Buka `Bank.java`. Ganti array dengan `LinkedHashMap`. Semua method yang menelusuri rekening kini memakai `accounts.values()`:

![Bank.java memakai LinkedHashMap menggantikan Account array](../assets/code/pertemuan-11/p11-01-bank.png){width=85%}

2. Perbarui `Main.java`. Konstruktor `Bank` tidak lagi memerlukan kapasitas:

![Main.java membuat Bank tanpa parameter kapasitas](../assets/code/pertemuan-11/p11-01-main.png){width=70%}

**Output yang diharapkan:**

```text
Withdrawal failed: A003: insufficient balance for a withdrawal of 70000.0
Withdrawal succeeded, new balance: 70000.0
A001 interest applied, new balance: 505000.0
A001 monthly fee: 0.0
A002 monthly fee: 15000.0
A003 interest applied, new balance: 71400.0
A003 monthly fee: 0.0
```

**Mengapa demikian?**

- Output sama dengan Pertemuan 10, sebab yang berubah hanya cara penyimpanan, bukan perilaku `Bank`.
- `LinkedHashMap` menjaga urutan penambahan, sehingga A001, A002, dan A003 tetap berurutan.
- `accounts.values()` mengembalikan semua rekening. `findAccount()` memakai `accounts.get(...)`, yang langsung mencari lewat nomor rekening.

> ✅ **Checkpoint:** output program sama dengan blok di atas.

> ⚠️ **Jika gagal:** apabila muncul error `for-each not applicable to expression type`, periksa apakah looping `for` memakai `accounts.values()`, bukan `accounts` secara langsung. `Map` tidak bisa dilooping langsung seperti array.

### Langkah 2: Transaction, Single Responsibility Principle

> **Konsep Singkat: Single Responsibility Principle.** Single Responsibility Principle (SRP) menyatakan bahwa satu kelas sebaiknya punya satu tanggung jawab, yaitu satu alasan untuk berubah. Kelas yang mencampur banyak tanggung jawab sulit dipahami. Perubahan pada satu tanggung jawab berisiko merusak tanggung jawab lain.

![Satu kelas dengan tiga tanggung jawab, dipisah menjadi tiga kelas masing-masing satu tanggung jawab](../assets/uml/p11-srp-split.png){width=72%}

> **Konsep Singkat: enum.** `enum` mendeklarasikan sekumpulan konstanta bernama yang jumlahnya tetap. Compiler menolak nilai di luar konstanta itu. `TransactionType.DEPOSIT` pasti valid. Salah ketik seperti `"WITHDRAWL"` pada `String` tetap berhasil dikompilasi, dan baru ketahuan salah saat riwayat transaksi sudah tidak masuk akal.

**Tujuan langkah ini:** mencatat riwayat transaksi, dengan satu kelas khusus yang hanya mewakili satu transaksi.

Bank Mini sejauh ini tidak mencatat riwayat transaksi. Tambahkan kelas `Transaction`, yang tanggung jawabnya hanya merepresentasikan satu transaksi:

1. Buat berkas baru `TransactionType.java`:

![TransactionType.java](../assets/code/pertemuan-11/p11-02-transactiontype.png){width=45%}

2. Buat berkas baru `Transaction.java`:

![Transaction.java](../assets/code/pertemuan-11/p11-02-transaction.png){width=55%}

![Account dan Transaction, satu Account memiliki banyak Transaction](../assets/uml/p11-transaction.png){width=68%}

3. Buka `Account.java`. `Account` menyimpan daftar `Transaction` miliknya sendiri, dan menambahkannya setiap kali `deposit()` atau `withdraw()` berhasil:

![Account.java mencatat Transaction pada deposit dan withdraw](../assets/code/pertemuan-11/p11-02-account.png){width=68%}

4. Buka `Bank.java`. Tambahkan method untuk menampilkan riwayat satu rekening:

![Bank.java dengan method printHistory](../assets/code/pertemuan-11/p11-02-bank.png){width=68%}

5. Perbarui `Main.java`:

![Main.java memanggil printHistory](../assets/code/pertemuan-11/p11-02-main.png){width=70%}

**Output yang diharapkan:** tujuh baris dari Langkah 1 tetap sama, diikuti dua baris riwayat:

```text
Withdrawal failed: A003: insufficient balance for a withdrawal of 70000.0
Withdrawal succeeded, new balance: 70000.0
A001 interest applied, new balance: 505000.0
A001 monthly fee: 0.0
A002 monthly fee: 15000.0
A003 interest applied, new balance: 71400.0
A003 monthly fee: 0.0
A003 WITHDRAW 30000.0
A003 DEPOSIT 1400.0
```

**Mengapa demikian?**

- Penarikan 70000 ditolak, jadi tidak tercatat. Pencatatan `history.add(...)` hanya dijalankan setelah validasi berhasil.
- Penarikan 30000 berhasil, sehingga tercatat sebagai `WITHDRAW`.
- Bunga A003 disetor lewat `deposit()`, sehingga tercatat sebagai `DEPOSIT 1400.0` (2% dari 70000).

> ✅ **Checkpoint:** dua baris terakhir output sama dengan blok di atas.

> ⚠️ **Jika gagal:** apabila riwayat transaksi kosong, periksa apakah `history.add(...)` dipanggil **setelah** validasi berhasil (di dalam `deposit()` dan `withdraw()`), bukan sebelum pengecekan `canWithdraw()` atau jumlah minimum. Apabila muncul error `cannot find symbol: variable DEPOSIT`, periksa apakah konstanta dipanggil sebagai `TransactionType.DEPOSIT`, bukan `Transaction.DEPOSIT`. Konstanta itu berasal dari kelas `TransactionType`.

### Langkah 3: AccountRepository, Dependency Inversion Principle

> **Konsep Singkat: Dependency Inversion Principle.** Dependency Inversion Principle (DIP) menyatakan bahwa kelas yang mengatur alur (kelas tingkat tinggi) sebaiknya bergantung pada interface, bukan pada kelas konkret. Dengan begitu, implementasi konkretnya bisa diganti tanpa mengubah kelas yang memakainya.

**Tujuan langkah ini:** memisahkan tanggung jawab penyimpanan dari `Bank`, sehingga `Bank` hanya mengenal interface.

`Bank` sejauh ini menyimpan `Map<String, Account>` di dalam dirinya sendiri. Pisahkan penyimpanan ke interface tersendiri:

1. Buat berkas baru `AccountRepository.java`:

![AccountRepository.java](../assets/code/pertemuan-11/p11-03-accountrepository.png){width=55%}

2. Buat berkas baru `InMemoryAccountRepository.java`:

![InMemoryAccountRepository.java](../assets/code/pertemuan-11/p11-03-inmemoryaccountrepository.png){width=65%}

![Bank bergantung pada interface AccountRepository, diimplementasikan InMemoryAccountRepository](../assets/uml/p11-accountrepository.png){width=75%}

Cara membaca diagram: label `«interface»` menandai `AccountRepository` sebagai interface. Panah bergaris putus-putus berarti `InMemoryAccountRepository` mengimplementasikannya. `Bank` hanya mengenal interface itu.

3. Buka `Bank.java`. `Bank` kini bergantung pada `AccountRepository`, bukan pada `Map`. Semua method memakai `repository.findAll()` dan `repository.findByNumber(...)`:

![Bank.java bergantung pada AccountRepository](../assets/code/pertemuan-11/p11-03-bank.png){width=85%}

4. Perbarui `Main.java`:

![Main.java membuat Bank dengan InMemoryAccountRepository](../assets/code/pertemuan-11/p11-03-main.png){width=70%}

Interface ini sengaja tidak dibuat generik (`Repository<T>`), karena Bank Mini baru menyimpan satu jenis data, yaitu `Account`.

**Output yang diharapkan:** sama persis dengan Langkah 2:

```text
Withdrawal failed: A003: insufficient balance for a withdrawal of 70000.0
Withdrawal succeeded, new balance: 70000.0
A001 interest applied, new balance: 505000.0
A001 monthly fee: 0.0
A002 monthly fee: 15000.0
A003 interest applied, new balance: 71400.0
A003 monthly fee: 0.0
A003 WITHDRAW 30000.0
A003 DEPOSIT 1400.0
```

**Mengapa demikian?**

- Yang berganti hanya cara penyimpanan. Perilaku `Bank` tidak berubah, jadi tidak ada satu baris output pun yang berbeda.
- `Bank` tidak tahu bahwa penyimpanannya berupa `Map`. Ia hanya memanggil method milik `AccountRepository`.
- Kode yang membuat `Bank` (di `Main.java`) yang memilih implementasi mana yang dipakai.

> ✅ **Checkpoint:** output program sama dengan blok di atas.

> ⚠️ **Jika gagal:** apabila muncul error `constructor Bank in class Bank cannot be applied to given types`, periksa apakah `Main.java` sudah memanggil `new Bank(new InMemoryAccountRepository())`, bukan `new Bank()` seperti Langkah 1 dan 2.

### Langkah 4: Menelusuri OCP, LSP, dan ISP pada Kode yang Sudah Dibangun

> **Konsep Singkat: Open/Closed Principle.** Open/Closed Principle (OCP) menyatakan bahwa kelas terbuka untuk diperluas (subclass baru), tetapi tertutup untuk diubah (kode lama tidak disentuh). Bank Mini sudah menerapkannya sejak topik Overriding dan Overloading.

> **Konsep Singkat: Liskov Substitution Principle.** Liskov Substitution Principle (LSP) menyatakan bahwa subclass harus bisa menggantikan superclass-nya di mana pun tanpa membuat program salah. `Bank` yang memproses `Account` secara polimorfik sudah menunjukkannya.

> **Konsep Singkat: Interface Segregation Principle.** Interface Segregation Principle (ISP) menyatakan bahwa interface sebaiknya kecil dan fokus, sehingga kelas tidak dipaksa mengisi method yang tidak relevan. `InterestBearing` adalah contohnya.

**Tujuan langkah ini:** membuktikan bahwa ketiga prinsip itu sudah bekerja pada kode yang kamu bangun, tanpa menulis kode baru.

**4a. Open/Closed.** `SavingsAccount.canWithdraw()` (menahan saldo minimum) dan `CheckingAccount.canWithdraw()` (mengizinkan overdraft) meng-override method yang sama dengan aturan berbeda. `Account` dan `Bank` tidak pernah tahu aturan keduanya:

![SavingsAccount.java meng-override canWithdraw untuk menahan saldo minimum](../assets/code/pertemuan-11/p11-04-savingsaccount.png){width=65%}

![CheckingAccount.java meng-override canWithdraw untuk overdraft](../assets/code/pertemuan-11/p11-04-checkingaccount.png){width=65%}

1. Buka `Account.java` dan `Bank.java`. Gunakan fitur Cari (Ctrl+F, atau Find pada NetBeans) untuk mencari kata `SavingsAccount` dan `CheckingAccount` di kedua berkas.

> ✅ **Checkpoint:** pencarian tidak menemukan satu pun kemunculan `SavingsAccount` maupun `CheckingAccount` di `Account.java` atau `Bank.java`. Aturan penarikan baru cukup ditulis lewat override, tanpa mengubah `Account` dan `Bank`. Inilah **Open/Closed Principle**.

**4b. Liskov Substitution.** `Bank.processMonthEnd()` memanggil `acc.monthlyFee()` lewat tipe `Account`. Method itu dipercaya selalu terpenuhi oleh subclass mana pun:

![Bank.java dengan method processMonthEnd, iterasi polimorfik lewat repository](../assets/code/pertemuan-11/p11-04-bank.png){width=68%}

2. Lihat kembali output Langkah 3. Baris `monthly fee` berbeda untuk tiap jenis rekening.

> ✅ **Checkpoint:** output menunjukkan `A001 monthly fee: 0.0` dan `A003 monthly fee: 0.0` (`SavingsAccount`), tetapi `A002 monthly fee: 15000.0` (`CheckingAccount`). Padahal `processMonthEnd()` memakai satu baris `acc.monthlyFee()` yang sama, tanpa cabang `if`/`else` per jenis. Kedua subclass menggantikan `Account` tanpa membuat program salah. Inilah **Liskov Substitution Principle**.

**4c. Interface Segregation.** `InterestBearing` hanya berisi satu method, `applyInterest()`, dan hanya `SavingsAccount` yang mengimplementasikannya:

![InterestBearing.java, interface kecil satu method](../assets/code/pertemuan-11/p11-04-interestbearing.png){width=45%}

3. Cari kata `InterestBearing` di `SavingsAccount.java` dan di `CheckingAccount.java`.

> ✅ **Checkpoint:** `InterestBearing` muncul di `SavingsAccount.java` (pada baris `implements`), tetapi tidak muncul sama sekali di `CheckingAccount.java`. `CheckingAccount` tidak dipaksa mengimplementasikan `applyInterest()` yang tidak masuk akal baginya. Inilah **Interface Segregation Principle**.

**Mengapa demikian?**

- `Account` dan `Bank` tidak pernah menyebut nama kelas konkret, jadi subclass baru tidak memaksa keduanya berubah.
- Pemanggil memperlakukan semua `Account` dengan cara yang sama, dan tiap subclass menjawab dengan aturannya sendiri.
- Kemampuan yang hanya dimiliki sebagian kelas dijadikan interface kecil, bukan method di `Account`.

> ⚠️ **Jika gagal:** apabila `SavingsAccount` atau `CheckingAccount` justru muncul di `Account.java` atau `Bank.java`, periksa apakah salah satu berkas menuliskan nama kelas konkret, misalnya `if (acc instanceof SavingsAccount)`. Pengecekan seharusnya lewat method polimorfik atau `instanceof` terhadap interface.

## D. Tugas dan Hasil Kerja

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- Screenshot output program setelah Langkah 3, dan setelah Tugas mandiri.
- **Tugas mandiri:**
  1. **BusinessAccount: membuktikan OCP, LSP, dan ISP lewat kode baru.** Tambahkan `BusinessAccount extends Account` (rekening bisnis, saldo minimum Rp 1.000.000, tidak berbunga). Diagram berikut hanya sketsa method yang perlu di-override, BUKAN kode jadi, implementasi `canWithdraw()`/`monthlyFee()`/`printInfo()` (persis pola `SavingsAccount`/`CheckingAccount` yang sudah ditelusuri di Langkah 4) diserahkan sepenuhnya padamu:

     ![Sketsa BusinessAccount, method yang perlu di-override tanpa implementasi](../assets/uml/p11-tugas-businessaccount.png){width=60%}

     Daftarkan ke `Bank`, lalu jalankan `processMonthEnd()` dan `printAllAccounts()`:

     ![Main.java menambahkan BusinessAccount, memanggil processMonthEnd dan printAllAccounts](../assets/code/pertemuan-11/p11-tugas-main.png){width=70%}

     Buktikan dua hal berikut dari output program, tanpa mengubah satu baris pun `Account.java` atau `Bank.java`:
     - **(Open/Closed + Liskov Substitution)** baris `A004 monthly fee: 0.0` muncul di antara baris `processMonthEnd()` yang lain, dan `A004 - Budi - balance: 2000000.0` diikuti `Account type: Business` muncul di `printAllAccounts()`, persis seperti `SavingsAccount`/`CheckingAccount` diproses sebelumnya.
     - **(Interface Segregation)** tidak ada baris `A004 interest applied` yang muncul, sebab `BusinessAccount` tidak meng-implement `InterestBearing`.
  2. Jawab secara singkat (2-3 kalimat untuk masing-masing pertanyaan):
     - (a) sebutkan prinsip SOLID mana yang dibuktikan oleh fakta bahwa `Account.java`/`Bank.java` tidak berubah sama sekali setelah `BusinessAccount` ditambahkan, dan jelaskan mengapa.
     - (b) `processMonthEnd()` memakai `instanceof InterestBearing`, bukan `instanceof SavingsAccount`. Jelaskan mengapa `BusinessAccount` otomatis diperlakukan benar (tidak dikenai bunga) tanpa `Bank.java` perlu tahu apa pun tentang keberadaannya.
     - (c) Suatu saat nanti, `InMemoryAccountRepository` akan diganti kelas lain yang menyimpan data ke database. Jelaskan mengapa `Bank.java` tidak perlu diubah satu baris pun untuk pergantian itu, dan prinsip SOLID mana yang membuat ini mungkin.

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot/output) | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | `BusinessAccount` benar, OCP/LSP/ISP terbukti lewat output, jawaban konsep tepat | Sebagian tugas selesai meski jawaban belum lengkap |
