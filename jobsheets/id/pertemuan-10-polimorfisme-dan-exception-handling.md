# Jobsheet Praktikum: Pertemuan 10
## Polimorfisme dan Exception Handling

| | |
|---|---|
| **Mata Kuliah** | Praktikum Pemrograman Berbasis Objek (RTI253008) |
| **Pertemuan** | 10 (Minggu 10) |
| **Durasi** | 1 &times; 4 &times; 50' praktikum; 1 &times; 1 &times; 50' tugas/laporan mandiri |

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, mahasiswa mampu:

1. Membuat exception sendiri, lalu memakai `throw`, `try`, dan `catch` untuk menangani kegagalan tanpa menghentikan program.
2. Menulis method yang memproses objek dari berbagai subclass lewat satu perulangan (polimorfisme), termasuk memeriksa kemampuan objek dengan `instanceof`.

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, NetBeans (editor yang digunakan sepanjang praktikum ini).
- **Proyek**: jobsheet ini melanjutkan proyek `bank-mini` dari topik Kelas Abstrak dan Interface. Proyek itu sudah berisi `Account` abstrak dengan `monthlyFee()`, serta interface `InterestBearing` yang diterapkan `SavingsAccount`.

> **Tanpa NetBeans?** Jobsheet ini tetap dapat diikuti menggunakan editor teks biasa:
> ```bash
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> Checkpoint dan output yang dihasilkan tetap sama persis, apa pun editor yang digunakan.

## C. Langkah Kerja

### Langkah 1: withdraw() Melempar InsufficientBalanceException

> **Konsep Singkat: Exception.** Bayangkan alarm kebakaran: saat ada masalah, alarm berbunyi dan kegiatan berhenti sampai ada yang menanganinya. Exception bekerja seperti itu. Method yang menemukan masalah melempar (`throw`) sebuah objek exception, lalu berhenti saat itu juga. Kode pemanggil menaruh pemanggilan itu di dalam blok `try`, dan menyiapkan rencana cadangan di blok `catch`. Exception buatan sendiri cukup berupa kelas yang `extends Exception`.

**Tujuan langkah ini:** membuat penarikan yang gagal tidak bisa lagi lewat diam-diam.

Saat ini `withdraw()` mengembalikan `false` ketika penarikan gagal. Pemanggil bisa lupa memeriksa nilai itu, lalu melanjutkan seolah penarikan berhasil. Diagram berikut adalah hasil yang dituju langkah ini:

![InsufficientBalanceException sebagai subclass Exception, dilempar oleh Account](../assets/uml/p10-insufficientbalance-exception.png){width=60%}

Cara membaca diagram: `InsufficientBalanceException` adalah subclass dari `Exception`. Panah putus-putus berlabel "throws" berarti `withdraw()` di `Account` bisa melempar exception itu. Tipe kembalian `withdraw()` kini `void`, bukan `boolean`.

1. Buat berkas baru `InsufficientBalanceException.java`:

![InsufficientBalanceException.java](../assets/code/pertemuan-10/p10-01-insufficientbalanceexception.png){width=55%}

2. Buka `Account.java`. Ubah `withdraw()` supaya melempar exception ketika `canWithdraw()` bernilai `false`:

![Account.java, withdraw melempar InsufficientBalanceException](../assets/code/pertemuan-10/p10-01-account.png){width=65%}

3. Perbarui `Main.java`. Bungkus tiap pemanggilan `withdraw()` dengan `try`/`catch`:

![Main.java menguji withdraw yang melempar exception](../assets/code/pertemuan-10/p10-01-main.png){width=70%}

**Output yang diharapkan:**

```text
Withdrawal failed: A003: insufficient balance for a withdrawal of 70000.0
Withdrawal succeeded, new balance: 70000.0
```

**Mengapa demikian?**

- Saldo A003 adalah 100000 dengan saldo minimum 50000. Penarikan 70000 ditolak `canWithdraw()`, jadi `withdraw()` melempar exception. Blok `catch` mencetak pesannya lewat `e.getMessage()`.
- Exception dilempar sebelum baris `balance -= amount` dijalankan, jadi saldo tetap 100000.
- Penarikan kedua, 30000, diizinkan. Saldo menjadi 70000, dan baris "succeeded" di dalam `try` ikut dijalankan.

> ✅ **Checkpoint:** output program sama dengan blok di atas.

> ⚠️ **Jika gagal:** apabila muncul error `unreported exception InsufficientBalanceException; must be caught or declared to be thrown`, periksa apakah setiap pemanggilan `withdraw()` di `Main.java` sudah berada di dalam blok `try`/`catch`.

### Langkah 2: processMonthEnd(), Polimorfisme dan instanceof

> **Konsep Singkat: Polimorfisme.** Seorang pelatih berteriak "mulai!": perenang mulai berenang, pelari mulai berlari. Perintahnya satu, tiap atlet menjalankannya dengan caranya sendiri. Polimorfisme bekerja seperti itu: satu pemanggilan method yang sama menjalankan versi milik objek yang menerimanya. Bila kode perlu tahu apakah sebuah objek punya kemampuan tertentu, pakai `instanceof`. Bentuk `if (obj instanceof Tipe nama)` memeriksa sekaligus menyediakan variabel `nama` yang siap dipakai.

![Satu titik pemanggilan area() yang diselesaikan secara berbeda-beda saat program berjalan](../assets/uml/p10-polymorphic-dispatch.png){width=68%}

**Tujuan langkah ini:** memproses semua rekening di akhir bulan lewat satu perulangan, tanpa cabang `if` untuk tiap jenis rekening.

Semua rekening punya biaya bulanan (`monthlyFee()`). Hanya rekening yang `InterestBearing` yang menerima bunga:

![Account sebagai kelas abstrak, SavingsAccount meng-implement interface InterestBearing](../assets/uml/p10-account-abstract.png){width=72%}

1. Buka `Bank.java`. Tambahkan method `processMonthEnd()`:

![Bank.java dengan method processMonthEnd](../assets/code/pertemuan-10/p10-02-bank.png){width=68%}

2. Tambahkan pemanggilannya di akhir `Main.java`:

![Main.java memanggil processMonthEnd](../assets/code/pertemuan-10/p10-02-main.png){width=45%}

**Output yang diharapkan:** dua baris dari Langkah 1 tetap sama, diikuti lima baris baru:

```text
A001 interest applied, new balance: 505000.0
A001 monthly fee: 0.0
A002 monthly fee: 15000.0
A003 interest applied, new balance: 71400.0
A003 monthly fee: 0.0
```

**Mengapa demikian?**

- `monthlyFee()` dipanggil untuk ketiga rekening. Rekening tabungan menjawab 0.0, rekening giro menjawab 15000.0. Itulah polimorfisme: satu pemanggilan, jawaban mengikuti objeknya.
- Baris "interest applied" hanya muncul untuk A001 dan A003, sebab hanya keduanya yang `InterestBearing`. A002 adalah rekening giro, jadi dilewati.
- Bunga A001 adalah 1% dari 500000. Bunga A003 adalah 2% dari 70000.

> ✅ **Checkpoint:** lima baris terakhir output sama dengan blok di atas.

> ⚠️ **Jika gagal:** apabila tidak ada baris "interest applied" sama sekali, periksa apakah pengecekannya memakai `instanceof InterestBearing`, dan apakah `SavingsAccount` menyatakan `implements InterestBearing`.

## D. Tugas dan Hasil Kerja

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- Screenshot output program setelah Langkah 2.
- **Tugas mandiri:**
  1. Bank memerlukan laporan audit untuk rekening yang `Auditable` (interface dari topik Kelas Abstrak dan Interface). Tambahkan `Bank.printAuditLog()`: satu perulangan atas semua rekening, yang mencetak `auditLog()` hanya untuk rekening yang `Auditable`. Diagram berikut hanya sketsa method yang perlu ditambahkan, BUKAN kode jadi, isinya diserahkan sepenuhnya padamu:

     ![Sketsa Bank.printAuditLog(), memeriksa Auditable lewat instanceof](../assets/uml/p10-tugas-auditlog.png){width=55%}

     Hasilmu benar apabila hanya rekening giro yang tercetak, dan pengecekannya memakai `instanceof Auditable`, bukan nama kelas.
  2. `Bank.findAccount()` mengembalikan `null` ketika rekening tidak ditemukan. Pemanggil bisa lupa memeriksa `null`. Ubah supaya method itu melempar exception `AccountNotFoundException`. Diagram berikut hanya sketsa struktur dan signature yang berubah, BUKAN kode jadi:

     ![Sketsa AccountNotFoundException dan Bank.findAccount() yang melemparnya](../assets/uml/p10-tugas-accountnotfound.png){width=60%}

     Buktikan lewat `Main.java`: panggil `findAccount()` untuk satu nomor rekening yang ada dan satu yang tidak ada, masing-masing di dalam `try`/`catch`. Hasilmu benar apabila nomor yang tidak ada menghasilkan pesan dari blok `catch`, dan program tetap berjalan sampai selesai.
  3. Jawab secara singkat (1-2 kalimat untuk masing-masing pertanyaan):
     - (a) Mengapa melempar exception lebih aman daripada mengembalikan `null` pada `findAccount()`?
     - (b) Mengapa `processMonthEnd()` memeriksa `instanceof InterestBearing`, bukan `instanceof SavingsAccount`?

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot/output) | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | Kedua method tugas benar, jawaban konsep tepat | Sebagian tugas selesai meski jawaban belum lengkap |
