# Jobsheet Praktikum: Pertemuan 10
## Polimorfisme dan Exception Handling

| | |
|---|---|
| **Mata Kuliah** | Praktikum Pemrograman Berbasis Objek (RTI253008) |
| **Pertemuan** | 10 (Minggu 10) |
| **Durasi** | 1 &times; 4 &times; 50' praktikum; 1 &times; 1 &times; 50' tugas/laporan mandiri |
| **Kode Awal** | `code/bank-mini/pertemuan-09/` (checkpoint Pertemuan 9) |
| **Kode Akhir** | proyek `bank-mini` setelah Langkah 2, disalin sebagai checkpoint `code/bank-mini/pertemuan-10/` |

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, mahasiswa mampu:

1. Mendeklarasikan exception kustom dan menerapkan `throw`/`try`/`catch` untuk menangani kondisi galat tanpa menghentikan program secara paksa.
2. Menulis method yang memanfaatkan polimorfisme, termasuk pengecekan `instanceof` dengan pattern matching, untuk memproses objek dari berbagai subclass lewat satu titik kode yang sama.

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, NetBeans (editor yang digunakan sepanjang praktikum ini).
- **Proyek**: pertemuan ini melanjutkan proyek `bank-mini` dari Pertemuan 9.
- **Verifikasi cepat** sebelum memulai:
  ```bash
  java -version
  javac -version
  ```
  Apabila keduanya menampilkan nomor versi tanpa galat, proses dapat dilanjutkan.

> **Tanpa NetBeans?** Jobsheet ini tetap dapat diikuti menggunakan editor teks biasa, lanjutkan folder `bank-mini/` dari Pertemuan 9:
> ```bash
> cd bank-mini
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> Checkpoint dan output yang dihasilkan tetap sama persis, apa pun editor yang digunakan.

## C. Langkah Kerja

### Langkah 1: withdraw() Melempar InsufficientBalanceException

> **Konsep Singkat: Exception Handling.** Ketika sebuah method menemui kondisi yang tidak bisa ditangani secara wajar (misalnya saldo tidak mencukupi untuk sebuah penarikan), method itu bisa melempar (`throw`) sebuah objek exception, menghentikan eksekusinya saat itu juga. Kode pemanggil membungkus pemanggilan method dalam blok `try`, lalu menangani exception yang mungkin dilempar lewat blok `catch`. Sebuah exception kustom dibuat dengan mendeklarasikan kelas yang meng-`extends` `Exception`. Contoh generik: `Thermostat.setTemperature(-50)` melempar `InvalidTemperatureException` alih-alih diam-diam membatasi nilainya, sehingga kode pemanggil tahu persis ada yang salah dan wajib menanganinya.

![Exception, InvalidTemperatureException, dan Thermostat yang melemparnya](../assets/uml/p10-invalidtemperature-exception.png){width=60%}

Sejauh ini, `withdraw()` diam-diam mengembalikan `false` ketika penarikan gagal, kode pemanggil bisa saja lupa memeriksa nilai kembaliannya dan melanjutkan seolah penarikan berhasil. Ubah kontrak `withdraw()` supaya melempar exception alih-alih mengembalikan boolean. Tambahkan kelas exception kustom:

![InsufficientBalanceException.java](../assets/code/pertemuan-10/p10-01-insufficientbalanceexception.png){width=55%}

![Account.java, withdraw melempar InsufficientBalanceException](../assets/code/pertemuan-10/p10-01-account.png){width=65%}

Perbarui `Main.java` untuk membungkus pemanggilan `withdraw()` dalam `try`/`catch`:

![Main.java menguji withdraw yang melempar exception](../assets/code/pertemuan-10/p10-01-main.png){width=70%}

> ✅ **Checkpoint:** program mencetak `Withdrawal failed: A003: insufficient balance for a withdrawal of 70000.0`, diikuti `Withdrawal succeeded, new balance: 70000.0`.

> ⚠️ **Jika gagal:** apabila muncul galat compile `unreported exception InsufficientBalanceException; must be caught or declared to be thrown`, periksa apakah pemanggilan `withdraw()` di `Main.java` sudah dibungkus blok `try`/`catch`, bukan dipanggil langsung seperti sebelumnya.

### Langkah 2: processMonthEnd(), Polimorfisme lewat instanceof

> **Konsep Singkat: Polimorfisme.** Ketika sebuah array atau koleksi bertipe superclass (atau interface) menyimpan objek dari berbagai subclass, satu pemanggilan method yang sama, misalnya `s.area()` pada tiap elemen `Shape[] shapes`, otomatis menjalankan versi milik objek yang sebenarnya saat program berjalan, bukan versi yang dideklarasikan di tipe variabelnya. Inilah polimorfisme: satu titik kode, perilaku yang berbeda-beda tergantung objek yang menerimanya. Kadang kode tetap perlu tahu tipe konkret suatu objek, misalnya untuk memanggil kemampuan yang hanya dimiliki sebagian subclass; `instanceof` dengan pattern matching (`if (obj instanceof TipeTertentu variabel)`) memeriksa sekaligus melakukan downcasting dengan aman dalam satu langkah.

![Satu titik pemanggilan area() yang diselesaikan secara berbeda-beda saat program berjalan](../assets/uml/p10-polymorphic-dispatch.png){width=68%}

Hanya rekening yang meng-`implements` `InterestBearing` yang membutuhkan `applyInterest()`, `CheckingAccount` tidak. Tambahkan `processMonthEnd()` pada `Bank`, memproses seluruh rekening secara polimorfik lewat `monthlyFee()`, dan memakai `instanceof` untuk menerapkan bunga hanya pada rekening yang relevan:

![Bank.java dengan method processMonthEnd](../assets/code/pertemuan-10/p10-02-bank.png){width=68%}

![Account sebagai kelas abstrak, SavingsAccount meng-implement interface InterestBearing](../assets/uml/p09-account-abstract.png){width=72%}

Perbarui `Main.java`:

![Main.java memanggil processMonthEnd](../assets/code/pertemuan-10/p10-02-main.png){width=70%}

> ✅ **Checkpoint:** program menambahkan baris `A001 interest applied, new balance: 505000.0`, `A001 monthly fee: 0.0`, `A002 monthly fee: 15000.0`, `A003 interest applied, new balance: 71400.0`, `A003 monthly fee: 0.0` setelah baris dari Langkah 1.

> ⚠️ **Jika gagal:** apabila `applyInterest()` tidak pernah terpanggil untuk rekening manapun, periksa kembali apakah pengecekan memakai `instanceof InterestBearing` (bukan `instanceof SavingsAccount`), sebab polimorfisme di sini justru sengaja tidak bergantung pada nama kelas konkretnya, hanya pada interface yang diterapkan.

## D. Tugas dan Deliverable

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- Screenshot output program setelah Langkah 2.
- **Tugas mandiri:**
  1. Bank memerlukan laporan audit yang hanya mencetak rekening yang meng-implement `Auditable` (dari Pertemuan 9). Tambahkan `Bank.printAuditLog()`, memproses seluruh rekening secara polimorfik dan memakai `instanceof Auditable` untuk mencetak `auditLog()` hanya pada rekening yang relevan:

     ![Bank.java dengan method printAuditLog](../assets/code/pertemuan-10/p10-tugas-bank.png){width=68%}

  2. `Bank.findAccount()` sejauh ini mengembalikan `null` ketika rekening tidak ditemukan, kode pemanggil bisa lupa memeriksa `null` dan memicu `NullPointerException` di baris berikutnya. Ubah agar melempar exception kustom `AccountNotFoundException` alih-alih mengembalikan `null`:

     ![AccountNotFoundException.java](../assets/code/pertemuan-10/p10-tugas-accountnotfoundexception.png){width=55%}

     ![Bank.java, findAccount melempar AccountNotFoundException](../assets/code/pertemuan-10/p10-tugas-bank-findaccount.png){width=65%}

     Buktikan dengan memanggil `findAccount()` di `Main.java` untuk satu nomor rekening yang ada dan satu yang tidak ada, masing-masing dibungkus `try`/`catch`.
  3. Jawab secara singkat (2-3 kalimat untuk masing-masing pertanyaan): (a) mengapa mengubah `findAccount()` agar melempar exception, dibandingkan tetap mengembalikan `null`, membuat kode pemanggil lebih aman? (b) `processMonthEnd()` memakai `instanceof InterestBearing`, bukan `instanceof SavingsAccount`. Jelaskan mengapa perbedaan ini penting apabila suatu hari Bank Mini menambah jenis rekening berbunga baru selain `SavingsAccount`.

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot/output) | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | Kedua method tugas benar, jawaban konsep tepat | Sebagian tugas selesai meski jawaban belum lengkap |
