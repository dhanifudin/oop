# Jobsheet Praktikum: Pertemuan 14
## GUI dengan NetBeans Matisse (Bagian 2)

| | |
|---|---|
| **Mata Kuliah** | Praktikum Pemrograman Berbasis Objek (RTI253008) |
| **Pertemuan** | 14 (Minggu 14) |
| **Durasi** | 1 &times; 4 &times; 50' praktikum; 1 &times; 1 &times; 50' tugas/laporan mandiri |
| **Kode Awal** | `code/bank-mini/pertemuan-13/` (checkpoint Pertemuan 13) |
| **Kode Akhir** | proyek Maven `bank-mini` setelah Langkah 2, disalin sebagai checkpoint `code/bank-mini/pertemuan-14/` |

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, mahasiswa mampu:

1. Menambahkan form input pada GUI memakai Matisse, lalu memvalidasi isiannya sebelum dipakai.
2. Menghubungkan tombol GUI ke method `Account`/`Bank` yang sudah dibangun sejak pertemuan-pertemuan sebelumnya, termasuk menampilkan exception sebagai dialog alih-alih mencetaknya ke konsol.
3. Membaca baris yang sedang dipilih pada `JTable` untuk menentukan objek mana yang diproses.

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, NetBeans.
- **Proyek**: pertemuan ini melanjutkan proyek Maven `bank-mini` dan berkas `BankMiniFrame` dari Pertemuan 13.
- **Verifikasi cepat** sebelum memulai:
  ```bash
  mvn -version
  ```

> **Tanpa NetBeans?** Langkah desain form (memakai GUI Builder) membutuhkan NetBeans. Mahasiswa tanpa NetBeans dapat langsung memakai berkas `BankMiniFrame.java` hasil jadi dari checkpoint (lengkap tanpa `.form`), lalu menjalankan `mvn -q compile exec:java`; checkpoint dan tampilan GUI yang dihasilkan tetap sama persis.

## C. Langkah Kerja

### Langkah 1: Form Tambah Rekening

Sejauh ini, dua rekening contoh hanya ditambahkan lewat kode (`seedSampleAccounts()`), belum ada cara menambah rekening baru lewat GUI. Tambahkan form input di NetBeans:

1. Buka `BankMiniFrame` dalam mode **Design**. Dari palette **Swing Containers**, seret komponen **Panel** ke bawah Scroll Pane yang sudah ada. Beri nama `formPanel` (klik kanan > **Change Variable Name...**).
2. Klik kanan `formPanel` > **Set Layout > Grid Layout**. Pada panel **Properties**, atur **rows** = 3, **columns** = 4, **hgap** dan **vgap** = 6.
3. Seret ke dalam `formPanel`, berurutan kiri ke kanan lalu baris berikutnya: Label "Account Number:", Text Field (beri nama `accountNumberField`), Label "Owner:", Text Field (`ownerField`), Label "Phone:", Text Field (`phoneField`), Label "Type:", Combo Box (`accountTypeCombo`), Label "Initial Balance:", Text Field (`initialBalanceField`), Label kosong (pengisi sel), Button "Add Account" (`addAccountButton`).
4. Klik kanan `accountTypeCombo` > **Properties** > properti **model**, buka editor, isi dua nilai: `Savings` dan `Checking`.
5. Klik ganda tombol **Add Account** untuk membuat method `addAccountButtonActionPerformed`.

> ✅ **Checkpoint (desain):** tab **Design** menampilkan tabel di atas, form berisi lima pasang label-input dan tombol Add Account di bawahnya.

Isi method-method pendukung di tab **Source**:

![BankMiniFrame.java, constructor, seedSampleAccounts, loadAccounts, clearAddAccountFields](../assets/code/pertemuan-14/p14-01-bankminiframe-fields.png){width=68%}

![BankMiniFrame.java, addAccountButtonActionPerformed](../assets/code/pertemuan-14/p14-01-addaccounthandler.png){width=72%}

![Jendela BankMiniFrame setelah menambah rekening baru A003 lewat form](../assets/screenshots/pertemuan-14/p14-add-account.png){width=62%}

> ✅ **Checkpoint:** isi form dengan nomor rekening baru (mis. `A003`), nama pemilik, saldo awal, pilih jenis rekening, lalu klik **Add Account**. Baris baru muncul di tabel, dan seluruh isian form kembali kosong.

> ⚠️ **Jika gagal:** apabila muncul dialog "Invalid input" padahal isian tampak benar, periksa apakah **Initial Balance** hanya berisi angka (tanpa titik ribuan atau simbol mata uang), sebab `Double.parseDouble()` tidak bisa mengurai format semacam itu.

### Langkah 2: Setor dan Tarik Saldo

> **Konsep Singkat: Baris Terpilih pada JTable.** `JTable` menyediakan `getSelectedRow()`, mengembalikan indeks baris yang sedang disorot pengguna (atau `-1` bila belum ada yang dipilih). Nilai pada sel tertentu di baris itu bisa diambil lewat `getValueAt(baris, kolom)`. Kombinasi keduanya memungkinkan kode mengetahui objek mana, dari sekian banyak baris yang tampil, yang sedang ingin diproses pengguna.

Tambahkan panel aksi berisi input jumlah dan dua tombol, dengan langkah serupa Langkah 1:

1. Seret **Panel** baru di bawah `formPanel`, beri nama `actionsPanel`, **Set Layout > Grid Layout** dengan rows = 1, columns = 4.
2. Seret ke dalamnya: Label "Amount:", Text Field (`amountField`), Button "Deposit" (`depositButton`), Button "Withdraw" (`withdrawButton`).
3. Klik ganda masing-masing tombol untuk membuat method `depositButtonActionPerformed` dan `withdrawButtonActionPerformed`.

Isi kedua method, ditambah satu method bantu untuk membaca rekening yang sedang dipilih:

![BankMiniFrame.java, getSelectedAccount](../assets/code/pertemuan-14/p14-02-getselectedaccount.png){width=65%}

![BankMiniFrame.java, depositButtonActionPerformed](../assets/code/pertemuan-14/p14-02-deposithandler.png){width=72%}

![BankMiniFrame.java, withdrawButtonActionPerformed](../assets/code/pertemuan-14/p14-02-withdrawhandler.png){width=72%}

![Jendela BankMiniFrame setelah menarik saldo rekening yang dipilih](../assets/screenshots/pertemuan-14/p14-deposit-withdraw.png){width=62%}

> ✅ **Checkpoint:** pilih satu baris rekening di tabel, isi **Amount**, klik **Deposit**. Saldo pada baris itu bertambah dan kolom Amount kembali kosong. Coba juga **Withdraw** dengan jumlah yang melebihi batas rekening tsb (lihat Pertemuan 6-7 untuk aturan tiap jenis rekening): sebuah dialog error muncul menampilkan pesan `InsufficientBalanceException`, bukan program yang berhenti paksa.

> ⚠️ **Jika gagal:** apabila mengklik Deposit/Withdraw tanpa memilih baris tabel menampilkan `NullPointerException` alih-alih dialog "No account selected", periksa apakah `getSelectedAccount()` benar-benar dipanggil dan hasilnya diperiksa `== null` SEBELUM method itu dipakai lebih lanjut.

## D. Tugas dan Deliverable

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- Screenshot jendela `BankMiniFrame` setelah Langkah 2, termasuk satu percobaan Withdraw yang gagal (dialog error tampil).
- **Tugas mandiri:**
  1. Tambahkan tombol **Process Month End** (perluas `actionsPanel` menjadi rows = 1, columns = 5), memanggil `bank.processMonthEnd()` (dari Pertemuan 10) lalu menampilkan dialog konfirmasi "Month-end processing complete.":

     ![BankMiniFrame.java, processMonthEndButtonActionPerformed](../assets/code/pertemuan-14/p14-tugas-processmonthend.png){width=68%}

  2. Jawab secara singkat (2-3 kalimat untuk masing-masing pertanyaan): (a) mengapa validasi `NumberFormatException` tetap diperlukan di GUI, padahal pengguna "seharusnya" hanya mengetik angka pada kolom Amount? (b) Bandingkan bagaimana `InsufficientBalanceException` ditangani di Pertemuan 10 (dicetak ke konsol) dengan di jobsheet ini (ditampilkan sebagai dialog). Apa yang berubah, dan apa yang tetap sama?

Perhatikan satu hal yang sengaja belum dibahas: siapa pun yang menjalankan `BankMiniFrame` langsung mendapat akses penuh ke seluruh rekening, tanpa login sama sekali. Aplikasi perbankan sungguhan tidak pernah dirilis seperti ini. Pertemuan 15 menutup celah ini dengan menambahkan mekanisme autentikasi yang sesungguhnya, sekaligus alasan konkret pertama mengapa Bank Mini butuh database: kredensial login harus disimpan dan diperiksa dari data yang tersimpan, bukan dari nilai yang ditulis langsung di kode Java.

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot) | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | Tombol Process Month End benar dan jawaban konsep tepat | Sebagian tugas selesai meski jawaban belum lengkap |
