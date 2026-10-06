# Jobsheet Praktikum: Pertemuan 6
## Inheritance

| | |
|---|---|
| **Mata Kuliah** | Praktikum Pemrograman Berbasis Objek (RTI253008) |
| **Pertemuan** | 6 (Minggu 6) |
| **Durasi** | 1 &times; 4 &times; 50' praktikum; 1 &times; 1 &times; 50' tugas/laporan mandiri |

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, mahasiswa mampu:

1. Menulis subclass yang mewarisi atribut dan method dari sebuah superclass memakai `extends` dan `super(...)`.
2. Menambahkan atribut dan method baru pada subclass tanpa mengubah superclass-nya.

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, NetBeans (editor yang digunakan sepanjang praktikum ini).
- **Proyek**: pertemuan ini melanjutkan proyek `bank-mini` dari Pertemuan 4.

> **Tanpa NetBeans?** Jobsheet ini tetap dapat diikuti menggunakan editor teks biasa:
> ```bash
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> Checkpoint dan output yang dihasilkan tetap sama persis, apa pun editor yang digunakan.

## C. Langkah Kerja

### Langkah 1: SavingsAccount, Subclass Pertama

Proyek `bank-mini` dari Pertemuan 4 dilanjutkan pada pertemuan ini. Sejauh ini hanya ada satu jenis rekening, `Account`. Bank Mini yang sesungguhnya melayani beberapa jenis rekening dengan sifat masing-masing, tanpa menulis ulang `accountNumber`, `owner`, `balance`, `deposit()`, atau `withdraw()` dari nol. Tambahkan kelas `SavingsAccount` yang mewarisi seluruhnya dari `Account` lewat `extends`, ditambah atribut dan method barunya sendiri:

![Diagram kelas Account, SavingsAccount, dan CheckingAccount](../assets/uml/p06-account-hierarchy.png){width=70%}

Menerjemahkan diagram ini ke kode Java mengikuti aturan yang sudah dibahas di kelas konsep: panah berujung segitiga kosong dari `SavingsAccount` ke `Account` menjadi `class SavingsAccount extends Account`, baris atribut `-interestRate : double` menjadi field `private double interestRate;`, dan baris method `+printAccountType() : void` menjadi method `public void printAccountType() { ... }`. Method `deposit()`, `withdraw()`, dan `printInfo()` sengaja tidak muncul lagi di kotak `SavingsAccount`, sebab ketiganya sudah diwarisi apa adanya dari `Account` dan tidak perlu dideklarasikan ulang. Mulai dari `SavingsAccount`:

![SavingsAccount.java](../assets/code/pertemuan-06/p06-01-savingsaccount.png){width=65%}

Constructor `SavingsAccount` memanggil `super(accountNumber, owner, balance)` di baris pertama untuk membangun bagian yang diwarisi dari `Account`, baru kemudian mengisi `interestRate` miliknya sendiri. Kode ini juga menambahkan `getInterestRate()`, method yang tidak tergambar di diagram: atribut `private` butuh cara dibaca dari luar kelas (kebiasaan encapsulation dari Pertemuan 3), sehingga getter seperti ini wajar ditambahkan meski tidak selalu digambar eksplisit pada setiap diagram. Perbarui `Main.java` untuk mengujinya:

![Main.java membuat SavingsAccount dan memanggil method warisan maupun method barunya](../assets/code/pertemuan-06/p06-01-main.png){width=70%}

> ✅ **Checkpoint:** program mencetak `A001 - Nadia - balance: 350000.0` (dari `printInfo()`, method warisan yang tidak ditulis ulang) diikuti `Account type: Savings, interest rate: 0.01` (dari `printAccountType()`, method baru milik `SavingsAccount`).

> ⚠️ **Jika gagal:** apabila muncul error `constructor Account in class Account cannot be applied to given types`, periksa apakah `super(...)` di `SavingsAccount` dipanggil dengan urutan dan jumlah argumen yang persis sama dengan constructor `Account` yang dituju.

### Langkah 2: CheckingAccount, Subclass Kedua

Tambahkan kelas `CheckingAccount`, subclass kedua dari `Account`, dengan pola penerjemahan diagram yang sama seperti `SavingsAccount`:

![CheckingAccount.java](../assets/code/pertemuan-06/p06-02-checkingaccount.png){width=65%}

Perbarui `Main.java` untuk menguji kedua jenis rekening:

![Main.java menguji SavingsAccount dan CheckingAccount](../assets/code/pertemuan-06/p06-02-main.png){width=70%}

> ✅ **Checkpoint:** program mencetak empat baris: dua baris pertama untuk `acc1` sama seperti Langkah 1, diikuti `A002 - Sari - balance: 200000.0` dan `Account type: Checking, overdraft limit: 50000.0`. Saldo `acc2` tetap 200000.0 karena penarikan 230000 ditolak: `withdraw()` yang diwarisi dari `Account` hanya mengizinkan penarikan sebesar saldo yang tersedia, dan belum tahu cara memakai `overdraftLimit`. Ini bukan error, melainkan hal yang sengaja diamati: atribut baru saja tidak cukup, subclass juga butuh cara menulis ulang perilaku yang diwarisi, dan itulah yang diselesaikan lewat overriding pada Pertemuan 7.

> ⚠️ **Jika gagal:** apabila `overdraftLimit` tidak pernah tersimpan dengan benar, periksa apakah nama parameter constructor tidak keliru tertukar urutannya dengan `balance`.

### Langkah 3: Bank Mini Mengelola Beberapa Jenis Rekening

Tanpa mengubah satu baris pun kode `Bank`, kelas ini sudah bisa menyimpan `SavingsAccount` maupun `CheckingAccount` sekaligus, karena keduanya tetap berjenis `Account` (diwariskan lewat `extends`). Perbarui `Main.java` untuk membuktikannya:

![Main.java menaruh SavingsAccount dan CheckingAccount ke dalam Bank yang sama](../assets/code/pertemuan-06/p06-03-main.png){width=70%}

> ✅ **Checkpoint:** `printAllAccounts()` mencetak `A001 - Nadia - balance: 350000.0` diikuti `A002 - Sari - balance: 200000.0`, kedua baris memakai bentuk yang sama persis karena `printInfo()` belum ditulis ulang oleh subclass mana pun. `Bank` belum bisa menampilkan bunga atau limit overdraft masing-masing jenis rekening lewat `printAllAccounts()`.

> ⚠️ **Jika gagal:** apabila muncul error `incompatible types` saat `addAccount(acc1)` dipanggil, periksa apakah parameter `addAccount` di `Bank` bertipe `Account`, bukan tipe subclass tertentu.

## D. Tugas dan Hasil Kerja

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- Screenshot output program setelah Langkah 3.
- **Tugas mandiri:**
  1. Bank Mini menambah satu jenis rekening lagi untuk nasabah korporat. Tambahkan kelas `BusinessAccount extends Account`, dengan atribut `monthlyTransactionFee` dan method baru `printAccountType()`, mengikuti pola yang sama seperti `SavingsAccount` dan `CheckingAccount`:

     ![BusinessAccount.java](../assets/code/pertemuan-06/p06-tugas-businessaccount.png){width=65%}

     Buktikan dengan membuat satu `BusinessAccount`, memasukkannya ke `Bank` bersama rekening lain, lalu memanggil `printAllAccounts()` dan `printAccountType()` miliknya.
  2. Jawab secara singkat (2-3 kalimat): `Bank.printAllAccounts()` belum bisa menampilkan info khusus tiap jenis rekening (bunga, limit overdraft, atau biaya bulanan), padahal method `printAccountType()` sudah ada di setiap subclass. Mengapa demikian, dan apa yang menurutmu perlu diubah agar `printAllAccounts()` bisa menampilkannya secara otomatis?
  3. Jawab secara singkat (2-3 kalimat): apakah `SavingsAccount` sebaiknya dipecah lagi menjadi subclass (misalnya jenis dengan bunga tetap dan jenis dengan bunga berjenjang)? Beri satu contoh subclass yang menurutmu masuk akal beserta atribut barunya, atau jelaskan mengapa pemecahan lebih lanjut belum diperlukan untuk Bank Mini.

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot/output) | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | `BusinessAccount` benar dan jawaban konsep tepat | `BusinessAccount` ada meski jawaban belum lengkap |
