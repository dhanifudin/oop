# Jobsheet Praktikum: Pertemuan 9
## Kelas Abstrak dan Interface

| | |
|---|---|
| **Mata Kuliah** | Praktikum Pemrograman Berbasis Objek (RTI253008) |
| **Pertemuan** | 9 (Minggu 9) |
| **Durasi** | 1 &times; 4 &times; 50' praktikum; 1 &times; 1 &times; 50' tugas/laporan mandiri |

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, mahasiswa mampu:

1. Mendeklarasikan kelas abstrak dengan method abstrak yang wajib diisi setiap subclass.
2. Mendeklarasikan interface dan menerapkannya pada kelas yang membutuhkan kemampuan tertentu.

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, NetBeans (editor yang digunakan sepanjang praktikum ini).
- **Proyek**: jobsheet ini melanjutkan proyek `bank-mini` dari topik Overriding dan Overloading. Proyek itu sudah berisi `Account` dengan `canWithdraw()`, serta `SavingsAccount` dan `CheckingAccount` yang meng-override-nya.

> **Tanpa NetBeans?** Jobsheet ini tetap dapat diikuti menggunakan editor teks biasa:
> ```bash
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> Checkpoint dan output yang dihasilkan tetap sama persis, apa pun editor yang digunakan.

## C. Langkah Kerja

### Langkah 1: Account Menjadi Kelas Abstrak

> **Konsep Singkat: Kelas Abstrak.** Bayangkan resep dasar kue yang satu langkahnya masih kosong: "isi sesuai selera". Resep itu belum bisa dimasak sebelum langkah kosongnya diisi. Kelas abstrak (`abstract class`) bekerja seperti itu: kelas yang belum lengkap, sehingga objeknya tidak boleh dibuat lewat `new`. Langkah kosongnya disebut method abstrak, yaitu method tanpa isi yang wajib diisi setiap subclass.

**Tujuan langkah ini:** mencegah pembuatan `Account` polos, dan mewajibkan tiap jenis rekening menentukan biaya bulanannya sendiri.

Di Bank Mini, rekening selalu berupa `SavingsAccount` atau `CheckingAccount`. Tidak pernah ada rekening "umum". Diagram berikut adalah hasil yang dituju langkah ini:

![Account abstrak dengan method abstrak monthlyFee, diisi SavingsAccount dan CheckingAccount](../assets/uml/p09-account-monthlyfee.png){width=55%}

Cara membaca diagram: nama `Account` dan method `monthlyFee()` ditulis miring, artinya abstrak. `monthlyFee()` tertulis lagi di kedua subclass, artinya kedua subclass mengisinya.

1. Buka `Account.java`. Tambahkan kata `abstract` pada deklarasi kelas, lalu tambahkan method abstrak `monthlyFee()`:

![Account.java menjadi abstract class dengan method abstrak monthlyFee](../assets/code/pertemuan-09/p09-01-account.png){width=65%}

2. Buka `Bank.java`. Tambahkan method `printMonthlyFees()` untuk mencetak biaya bulanan setiap rekening:

![Bank.java dengan method printMonthlyFees](../assets/code/pertemuan-09/p09-01-bank.png){width=65%}

3. Isi `monthlyFee()` di `SavingsAccount` (tanpa biaya) dan `CheckingAccount` (biaya tetap):

![SavingsAccount.java mengisi monthlyFee](../assets/code/pertemuan-09/p09-01-savingsaccount.png){width=65%}

![CheckingAccount.java mengisi monthlyFee](../assets/code/pertemuan-09/p09-01-checkingaccount.png){width=65%}

4. Perbarui `Main.java`:

![Main.java memanggil printMonthlyFees](../assets/code/pertemuan-09/p09-01-main.png){width=70%}

**Output yang diharapkan:**

```text
A001 - Nadia - balance: 350000.0
Account type: Savings, interest rate: 0.01
A002 - Sari - balance: 200000.0
Account type: Checking, overdraft limit: 50000.0
A003 - Rian - balance: 100000.0
Account type: Savings, interest rate: 0.02
A004 - Dewi - balance: -150000.0
Account type: Checking, overdraft limit: 200000.0
A001 fee: 0.0
A002 fee: 15000.0
A003 fee: 0.0
A004 fee: 15000.0
```

**Mengapa demikian?**

- Delapan baris pertama berasal dari `printAllAccounts()`. Empat baris terakhir berasal dari `printMonthlyFees()`.
- `Bank` memanggil `monthlyFee()` yang sama untuk tiap rekening. Rekening tabungan menjawab 0.0, rekening giro menjawab 15000.0, sebab masing-masing mengisi method abstrak itu dengan caranya sendiri.
- Baris `new Account(...)` kini tidak bisa dikompilasi. Itu memang tujuannya.

> ✅ **Checkpoint:** output program sama dengan blok di atas.

> ⚠️ **Jika gagal:** apabila muncul error `SavingsAccount is not abstract and does not override abstract method monthlyFee()`, periksa apakah `monthlyFee()` sudah diisi di kedua subclass, dengan signature yang sama persis seperti di `Account`.

### Langkah 2: InterestBearing, Interface untuk Rekening Berbunga

> **Konsep Singkat: Interface.** Bayangkan port USB-C. Benda apa pun yang punya port itu bisa diisi daya dengan charger yang sama. Interface bekerja seperti itu: daftar method tanpa isi. Kelas yang menyatakan `implements` berjanji mengisi semua method itu. Satu kelas hanya boleh `extends` satu superclass, tetapi boleh `implements` banyak interface.

**Tujuan langkah ini:** memberi kemampuan "menerima bunga" hanya kepada rekening yang memang berbunga.

Hanya `SavingsAccount` yang berbunga. `CheckingAccount` tidak. Kalau `applyInterest()` ditaruh di `Account`, rekening giro ikut wajib mengisinya. Karena itu kemampuan ini dijadikan interface tersendiri:

![Account, SavingsAccount, dan interface InterestBearing](../assets/uml/p09-account-abstract.png){width=75%}

Cara membaca diagram: panah bergaris putus-putus dari `SavingsAccount` ke `InterestBearing` berarti `implements`. `CheckingAccount` tidak punya panah itu.

1. Buat berkas baru `InterestBearing.java`:

![InterestBearing.java](../assets/code/pertemuan-09/p09-02-interestbearing.png){width=55%}

2. Buka `SavingsAccount.java`. Tambahkan `implements InterestBearing` pada deklarasi kelas, lalu isi `applyInterest()`:

![SavingsAccount.java meng-implement InterestBearing](../assets/code/pertemuan-09/p09-02-savingsaccount.png){width=65%}

3. Tambahkan pengujian di akhir `Main.java`:

![Main.java menguji applyInterest](../assets/code/pertemuan-09/p09-02-main.png){width=70%}

**Output yang diharapkan:** dua belas baris dari Langkah 1 tetap sama, diikuti dua baris baru:

```text
Before interest: 100000.0
After interest: 102000.0
```

**Mengapa demikian?** Suku bunga rekening A003 adalah 0.02. Bunganya 2% dari 100000, yaitu 2000, lalu disetor ke saldo. `CheckingAccount` tidak punya `applyInterest()` sama sekali, sebab tidak menyatakan `implements InterestBearing`.

> ✅ **Checkpoint:** dua baris terakhir output sama dengan blok di atas.

> ⚠️ **Jika gagal:** apabila muncul error `SavingsAccount is not abstract and does not override abstract method applyInterest()`, periksa apakah `implements InterestBearing` dan isi `applyInterest()` ditambahkan bersamaan. Kelas yang menyatakan `implements` wajib mengisi seluruh method interface itu.

## D. Tugas dan Hasil Kerja

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- Screenshot output program setelah Langkah 2.
- **Tugas mandiri:**
  1. Bank memerlukan jejak audit untuk rekening yang saldonya bisa negatif (rekening dengan overdraft). Tambahkan interface `Auditable`, lalu terapkan pada `CheckingAccount`. Diagram berikut hanya sketsa method yang perlu diisi, BUKAN kode jadi, isi `auditLog()` diserahkan sepenuhnya padamu:

     ![Sketsa Auditable dan CheckingAccount, method yang perlu diisi](../assets/uml/p09-tugas-auditable.png){width=60%}

     Buktikan lewat `Main.java`: panggil `auditLog()` pada kedua `CheckingAccount` yang sudah ada, lalu cetak hasilnya. Hasilmu benar apabila:
     - `Auditable` adalah interface dengan satu method `auditLog()`;
     - `CheckingAccount` menyatakan `implements Auditable`, sedangkan `SavingsAccount` tidak;
     - teks yang dicetak memuat nomor rekening dan saldonya.
  2. Jawab secara singkat (1-2 kalimat untuk masing-masing pertanyaan):
     - (a) Mengapa `applyInterest()` dijadikan interface `InterestBearing`, bukan method abstrak di `Account`?
     - (b) `SavingsAccount` memakai `extends Account` dan `implements InterestBearing`. Apa beda makna kedua kata kunci itu?

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot/output) | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | Interface `Auditable` benar dan jawaban konsep tepat | Interface ada meski jawaban belum lengkap |
