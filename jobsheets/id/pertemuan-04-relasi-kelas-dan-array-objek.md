# Jobsheet Praktikum: Pertemuan 4
## Relasi Kelas dan Array Objek

| | |
|---|---|
| **Mata Kuliah** | Praktikum Pemrograman Berbasis Objek (RTI253008) |
| **Pertemuan** | 4 (Minggu 4) |
| **Durasi** | 1 &times; 4 &times; 50' praktikum; 1 &times; 1 &times; 50' tugas/laporan mandiri |
| **Kode Awal** | `code/bank-mini/pertemuan-03/` (checkpoint Pertemuan 3) |
| **Kode Akhir** | proyek `bank-mini` setelah Langkah 2, disalin sebagai checkpoint `code/bank-mini/pertemuan-04/` |

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, mahasiswa mampu:

1. Menulis kelas yang memiliki kelas lain sebagai atributnya (relasi association).
2. Mengelola sekumpulan objek lewat array yang menjadi atribut sebuah kelas (relasi aggregation), termasuk pencarian di dalamnya.

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, NetBeans (editor yang digunakan sepanjang praktikum ini).
- **Proyek**: pertemuan ini melanjutkan proyek `bank-mini` dari Pertemuan 3.
- **Verifikasi cepat** sebelum memulai:
  ```bash
  java -version
  javac -version
  ```
  Apabila keduanya menampilkan nomor versi tanpa galat, proses dapat dilanjutkan.

> **Tanpa NetBeans?** Jobsheet ini tetap dapat diikuti menggunakan editor teks biasa, lanjutkan folder `bank-mini/` dari Pertemuan 3:
> ```bash
> cd bank-mini
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> Checkpoint dan output yang dihasilkan tetap sama persis, apa pun editor yang digunakan.

## C. Langkah Kerja

### Langkah 1: Account Berelasi dengan Customer

Proyek `bank-mini` dari Pertemuan 3 dilanjutkan pada pertemuan ini. Sejauh ini, `Account` hanya menyimpan nama pemiliknya sebagai teks biasa. Tambahkan kelas `Customer`:

![Customer.java](../assets/code/pertemuan-04/p04-01-customer.png){width=60%}

Ganti isi `Account.java` sehingga menyimpan referensi ke sebuah `Customer` (association), bukan lagi nama pemilik berupa teks:

![Account.java, atribut ownerName diganti menjadi owner bertipe Customer](../assets/code/pertemuan-04/p04-01-account.png){width=65%}

Karena constructor `Account` kini menerima objek `Customer`, perbarui pengujian di `Main.java`:

![Main.java memakai Customer dan constructor Account yang baru](../assets/code/pertemuan-04/p04-01-main.png){width=70%}

> ✅ **Checkpoint:** program berhasil dikompilasi ulang dan menampilkan `A001 - Nadia - balance: 350000.0`, kali ini diambil melalui objek `Customer`.

> ⚠️ **Jika gagal:** apabila muncul galat `incompatible types: String cannot be converted to Customer`, periksa apakah argumen kedua pada `new Account(...)` sudah berupa objek `Customer`, bukan teks nama pemilik.

### Langkah 2: Bank Mengelola Banyak Account

Tambahkan kelas `Bank`, yang menyimpan banyak `Account` dalam sebuah array (aggregation):

![Bank.java](../assets/code/pertemuan-04/p04-02-bank.png){width=65%}

Perbarui `Main.java` agar membuat dua rekening dan mengelolanya lewat `Bank`:

![Main.java memakai Bank untuk mengelola dua Account](../assets/code/pertemuan-04/p04-02-main.png){width=70%}

> ✅ **Checkpoint:** program mencetak dua baris dari `printAllAccounts()` (`A001 - Nadia - balance: 350000.0` dan `A002 - Sari - balance: 200000.0`), diikuti satu baris lagi hasil `findAccount("A002")` yang menampilkan data yang sama untuk `A002`.

> ⚠️ **Jika gagal:** apabila `findAccount(...)` selalu mengembalikan `null` walaupun nomor rekeningnya ada, periksa apakah perbandingan menggunakan `.equals(...)`, bukan `==`, karena `==` pada `String` membandingkan referensi, bukan isi teksnya.

## D. Tugas dan Deliverable

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- Screenshot output program setelah Langkah 2.
- **Tugas mandiri:**
  1. Seorang customer bisa saja memiliki lebih dari satu rekening. Tambahkan method `findAccountsByOwnerName(String name)` ke `Bank`, yang mengembalikan array berisi seluruh `Account` milik customer dengan nama tersebut:

     ![Bank.java dengan tambahan findAccountsByOwnerName](../assets/code/pertemuan-04/p04-tugas-bank.png){width=65%}

     Buktikan dengan membuat satu customer bernama "Nadia" yang memiliki dua `Account`, satu customer lain dengan satu `Account`, memasukkan ketiganya ke `Bank`, lalu memanggil `findAccountsByOwnerName("Nadia")` dan mencetak jumlah serta isi hasilnya.
  2. Jawab secara singkat (2-3 kalimat untuk masing-masing pertanyaan): (a) mengapa relasi `Account`-`Customer` disebut association, bukan aggregation atau composition? (b) bayangkan objek `Bank` dihapus dari memori. Menurutmu, apakah objek `Account` yang pernah ditambahkan ke dalamnya seharusnya ikut terhapus, atau tetap bisa berdiri sendiri? Apa maksud jawabanmu terhadap jenis relasi `Bank`-`Account`?

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot/output) | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | Method pencarian per nama customer benar dan jawaban konsep tepat | Method pencarian ada meski jawaban belum lengkap |
