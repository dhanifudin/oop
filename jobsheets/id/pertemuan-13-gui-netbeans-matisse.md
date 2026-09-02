# Jobsheet Praktikum: Pertemuan 13
## GUI dengan NetBeans Matisse (Bagian 1)

| | |
|---|---|
| **Mata Kuliah** | Praktikum Pemrograman Berbasis Objek (RTI253008) |
| **Pertemuan** | 13 (Minggu 13) |
| **Durasi** | 1 &times; 4 &times; 50' praktikum; 1 &times; 1 &times; 50' tugas/laporan mandiri |
| **Kode Awal** | `code/bank-mini/pertemuan-11/` (checkpoint Pertemuan 11) |
| **Kode Akhir** | proyek Maven `bank-mini` setelah Langkah 2, disalin sebagai checkpoint `code/bank-mini/pertemuan-13/` |

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, mahasiswa mampu:

1. Mengorganisasikan kelas Bank Mini yang sudah ada ke dalam subpaket `model`, `repository`, dan `ui` di dalam proyek Maven.
2. Mendesain antarmuka pengguna (GUI) memakai NetBeans Matisse (drag-and-drop), tanpa menulis kode layout secara manual.
3. Menghubungkan komponen GUI ke kelas bisnis (`Bank`, `AccountRepository`) yang sudah dibangun sejak pertemuan-pertemuan sebelumnya, tanpa mengubah satu baris pun logikanya.

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, NetBeans (editor utama, sudah menyertakan dukungan Maven dan GUI Builder/Matisse secara bawaan).
- **Proyek**: mulai pertemuan ini, Bank Mini beralih dari proyek `javac`/`java` polos menjadi proyek **Maven**. Buat proyek baru: **File > New Project > Java with Maven > Java Application**, beri nama `bank-mini`, Group Id `id.ac.polinema`.
- **Verifikasi cepat** sebelum memulai:
  ```bash
  java -version
  mvn -version
  ```
  Apabila keduanya menampilkan nomor versi tanpa galat, proses dapat dilanjutkan.

> **Tanpa NetBeans?** Langkah 1 (reorganisasi paket) tetap bisa diikuti dengan editor teks biasa dan Maven baris perintah:
> ```bash
> cd bank-mini
> mvn -q compile exec:java
> ```
> Langkah 2 (desain GUI dengan Matisse) membutuhkan NetBeans, karena GUI Builder adalah fitur bawaan NetBeans yang tidak punya padanan baris perintah. Mahasiswa tanpa NetBeans dapat langsung memakai berkas `BankMiniFrame.java` hasil jadi dari checkpoint (lengkap tanpa `.form`) dan mengompilasinya dengan `mvn -q compile exec:java`; checkpoint dan tampilan GUI yang dihasilkan tetap sama persis.

## C. Langkah Kerja

### Langkah 1: Reorganisasi Paket model/repository/ui

Sejauh ini, seluruh kelas Bank Mini berada langsung di paket `id.ac.polinema`. Seiring proyek bertambah besar dengan tambahan antarmuka GUI, kelas-kelas ini dikelompokkan menurut perannya: `id.ac.polinema.model` untuk kelas data dan aturan bisnis inti (`Account` dan turunannya, `Customer`, `Transaction`, exception, interface), `id.ac.polinema.repository` untuk kelas penyimpanan data (`AccountRepository`, `InMemoryAccountRepository`), sementara `Bank` dan `Main` tetap di paket induk `id.ac.polinema` sebagai penghubung antar lapisan. Paket `id.ac.polinema.ui` disiapkan untuk kelas antarmuka GUI yang akan dibangun pada Langkah 2.

Pindahkan setiap berkas ke paket barunya (klik kanan `src/main/java` > **New > Java Package**, lalu pindahkan berkas lewat drag-and-drop di jendela Projects), perbarui deklarasi `package` di baris pertama tiap berkas, dan tambahkan `import` untuk kelas yang kini berasal dari paket berbeda:

![Bank.java dengan import lintas paket model dan repository](../assets/code/pertemuan-13/p13-01-bank.png){width=68%}

![Account.java dengan package model](../assets/code/pertemuan-13/p13-01-account.png){width=55%}

`Bank` juga mendapat method baru, `getAllAccounts()`, mengembalikan seluruh rekening tanpa mencetaknya, sebab GUI pada Langkah 2 butuh data mentahnya, bukan teks tercetak di konsol:

![Bank.java dengan method getAllAccounts](../assets/code/pertemuan-13/p13-01-bank-getall.png){width=60%}

> ✅ **Checkpoint:** setelah seluruh berkas dipindah dan `import`-nya diperbaiki, jalankan **Run Project** (F6). Output tetap identik dengan Pertemuan 11 (baris `Withdrawal failed`, `Withdrawal succeeded`, `interest applied`, `monthly fee`, dan riwayat transaksi A003).

> ⚠️ **Jika gagal:** apabila muncul galat `package id.ac.polinema does not exist` atau `cannot find symbol`, periksa apakah setiap berkas yang dipindah sudah diperbarui deklarasi `package`-nya sesuai lokasi barunya, dan apakah kelas yang dipakai lintas paket sudah di-`import`.

### Langkah 2: BankMiniFrame, Daftar Rekening dengan Matisse

> **Konsep Singkat: GUI Builder (Matisse).** Menulis tata letak (layout) GUI secara manual lewat kode itu merepotkan: posisi dan ukuran tiap komponen harus dihitung dan disesuaikan satu per satu setiap kali tampilan berubah. NetBeans menyediakan GUI Builder (dikenal sebagai Matisse) yang memungkinkan komponen (tombol, tabel, dan sebagainya) disusun dengan cara diseret (drag-and-drop) di editor visual, kode Java layout-nya (`GroupLayout`) dibuat otomatis oleh NetBeans di baliknya.

Ikuti langkah berikut di NetBeans:

1. Klik kanan paket `id.ac.polinema.ui` > **New > Other... > Swing GUI Forms > JFrame Form**. Beri nama kelas `BankMiniFrame`.
2. NetBeans membuka `BankMiniFrame` dalam mode **Design**. Dari panel **Palette**, kelompok **Swing Containers**, seret komponen **Scroll Pane** ke atas form.
3. Dari kelompok **Swing Controls**, seret komponen **Table** ke DALAM Scroll Pane yang baru saja ditambahkan.
4. Klik kanan tabel tersebut > **Table Contents...**. Pada dialog yang terbuka, hapus baris kolom bawaan, lalu tambahkan tiga kolom bertipe `Object`: `Account Number`, `Owner`, `Balance`. Kosongkan baris datanya (0 baris), sebab tabel akan diisi lewat kode.
5. Dari kelompok **Swing Controls**, seret komponen **Button** ke bawah Scroll Pane.
6. Pada panel **Properties** komponen tombol tersebut, ubah properti **text** menjadi `Refresh`.
7. Klik kanan Scroll Pane dan tombol masing-masing, pilih **Change Variable Name...**, beri nama `accountScrollPane` dan `refreshButton` (nama tabel di dalam Scroll Pane diberi nama `accountTable`).
8. Klik ganda tombol **Refresh** di Design view. NetBeans membuka tab **Source** dan membuatkan method kosong `refreshButtonActionPerformed`.

> ✅ **Checkpoint (desain):** kembali ke tab **Design**, tampilan form menunjukkan tabel kosong dengan tiga kolom di bagian atas dan tombol Refresh di bagian bawah kanan.

Isi method yang dibuatkan NetBeans, ditambah beberapa method dan field pendukung, semuanya di tab **Source** (di luar blok kode abu-abu yang dijaga NetBeans):

![BankMiniFrame.java, constructor dan method loadAccounts/seedSampleAccounts](../assets/code/pertemuan-13/p13-02-bankminiframe-fields.png){width=68%}

![BankMiniFrame.java, refreshButtonActionPerformed](../assets/code/pertemuan-13/p13-02-bankminiframe-handler.png){width=60%}

Perbarui `Main.java` supaya menjalankan `BankMiniFrame`, bukan lagi mencetak ke konsol:

![Main.java menjalankan BankMiniFrame](../assets/code/pertemuan-13/p13-02-main.png){width=68%}

![Tampilan BankMiniFrame setelah dijalankan, menampilkan dua rekening contoh](../assets/screenshots/pertemuan-13/p13-account-list.png){width=60%}

> ✅ **Checkpoint:** jalankan **Run Project** (F6). Jendela `BankMiniFrame` muncul menampilkan tabel berisi dua rekening contoh (A001 - Nadia - 500000.0, A002 - Sari - 200000.0). Menekan tombol **Refresh** tidak mengubah apa pun untuk saat ini (belum ada cara menambah rekening baru lewat GUI), tetapi tidak menampilkan galat.

> ⚠️ **Jika gagal:** apabila tabel tampil kosong, periksa apakah `loadAccounts()` benar-benar dipanggil di constructor SETELAH `seedSampleAccounts()`, dan apakah `accountTable.getModel()` di-cast ke `DefaultTableModel` (bukan `TableModel` biasa, yang tidak memiliki method `addRow`/`setRowCount`).

## D. Tugas dan Deliverable

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- Screenshot jendela `BankMiniFrame` setelah Langkah 2 dijalankan.
- **Tugas mandiri:**
  1. Tambahkan satu kolom baru pada tabel, **Type**, menampilkan `"Savings"` atau `"Checking"` sesuai jenis rekening (gunakan `instanceof` seperti dipelajari pada Pertemuan 10).
  2. Jawab secara singkat (2-3 kalimat untuk masing-masing pertanyaan): (a) mengapa `Bank.getAllAccounts()` mengembalikan `Collection<Account>`, bukan langsung mencetaknya seperti `printAllAccounts()`? (b) Apa yang akan terjadi pada kode `BankMiniFrame` apabila suatu hari `InMemoryAccountRepository` diganti implementasi lain (mis. tersambung ke database)? Kaitkan jawabanmu dengan Dependency Inversion Principle yang dipelajari pada Pertemuan 11.

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot) | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | Kolom Type benar dan jawaban konsep tepat | Sebagian tugas selesai meski jawaban belum lengkap |
