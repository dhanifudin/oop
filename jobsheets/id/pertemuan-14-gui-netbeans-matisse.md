# Jobsheet Praktikum: Pertemuan 14
## GUI dengan NetBeans Matisse (Bagian 2)

| | |
|---|---|
| **Mata Kuliah** | Praktikum Pemrograman Berbasis Objek (RTI253008) |
| **Pertemuan** | 14 (Minggu 14) |
| **Durasi** | 1 &times; 4 &times; 50' praktikum; 1 &times; 1 &times; 50' tugas/laporan mandiri |

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, mahasiswa mampu:

1. Membuat dialog input (`JDialog`) memakai Matisse untuk menambah rekening baru, dengan nomor rekening dibangkitkan otomatis oleh aplikasi (bukan diketik pengguna), lalu memvalidasi isian lain sebelum dipakai.
2. Menghubungkan tombol GUI ke method `Account`/`Bank` yang sudah dibangun sejak pertemuan-pertemuan sebelumnya, termasuk menampilkan exception sebagai dialog alih-alih mencetaknya ke konsol.
3. Membaca baris yang sedang dipilih pada `JTable`, lalu memakainya untuk MENCEGAH aksi yang tidak valid (menonaktifkan tombol) alih-alih hanya menampilkan peringatan setelah aksi terlanjur diklik.

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, NetBeans.
- **Proyek**: pertemuan ini melanjutkan proyek Maven `bank-mini` dan berkas `BankMiniFrame` dari Pertemuan 13.
- **Verifikasi cepat** sebelum memulai:
  ```bash
  mvn -version
  ```

> **Tanpa NetBeans?** Langkah desain form dan dialog (memakai GUI Builder) membutuhkan NetBeans. Mahasiswa tanpa NetBeans dapat langsung memakai berkas `BankMiniFrame.java` dan `AddAccountDialog.java` hasil jadi dari checkpoint (lengkap tanpa `.form`), lalu menjalankan `mvn -q compile exec:java`; checkpoint dan tampilan GUI yang dihasilkan tetap sama persis.

## C. Langkah Kerja

### Langkah 1: Dialog Tambah Rekening

Sejauh ini, dua rekening contoh hanya ditambahkan lewat kode (`seedSampleAccounts()`), belum ada cara menambah rekening baru lewat GUI.

> **Konsep Singkat: `JDialog`, Jendela Modal.** `JFrame` cocok untuk jendela utama aplikasi, tetapi kurang tepat untuk form sekali-pakai seperti "tambah rekening": begitu form itu selesai atau dibatalkan, jendelanya semestinya langsung tertutup dan mengembalikan kendali ke jendela pemanggilnya. `JDialog` dirancang untuk kebutuhan ini. Dialog yang dibuat modal (`true`) MEMBLOKIR interaksi dengan jendela pemanggilnya selama dialog masih terbuka, memaksa pengguna menyelesaikan atau membatalkan dialog itu dulu sebelum kembali ke jendela utama, persis seperti `JOptionPane.showMessageDialog(...)` yang sudah dipakai sejak Pertemuan 14 (Bagian 1) tetapi kali ini dengan tampilan buatan sendiri.

Buat dialognya di NetBeans:

1. Klik kanan paket `id.ac.polinema.ui` > **New > Other... > Swing GUI Forms > JDialog Form**. Beri nama kelas `AddAccountDialog`.
2. NetBeans membuatkan constructor `AddAccountDialog(java.awt.Frame parent, boolean modal)`. Pada tab **Source**, ubah agar menerima `Bank` langsung dan selalu modal, sebagaimana Pertemuan 15 nanti akan mengubah constructor `BankMiniFrame` dengan cara serupa untuk menerima `username`:

   ```java
   public AddAccountDialog(java.awt.Frame parent, Bank bank) {
       super(parent, true);
       initComponents();
       this.bank = bank;
       accountNumberValueLabel.setText(bank.nextAccountNumber());
   }
   ```

3. Kembali ke tab **Design**. Dari palette **Swing Containers**, seret komponen **Panel** ke form. Beri nama `formPanel`, **Set Layout > Grid Layout** dengan **rows** = 5, **columns** = 2, **hgap** dan **vgap** = 6.
4. Seret ke dalam `formPanel`, berurutan: Label "Account Number:", Label (BUKAN Text Field, sebab nilainya dibangkitkan otomatis dan tidak boleh diketik pengguna) dengan nama `accountNumberValueLabel`; Label "Owner:", Text Field (`ownerField`); Label "Phone:", Text Field (`phoneField`); Label "Type:", Combo Box (`accountTypeCombo`); Label "Initial Balance:", Text Field (`initialBalanceField`).
5. Klik kanan `accountTypeCombo` > **Properties** > properti **model**, buka editor, isi dua nilai: `Savings` dan `Checking`.
6. Seret **Panel** kedua di bawah `formPanel`, beri nama `buttonsPanel`, **Set Layout > Grid Layout** dengan rows = 1, columns = 2. Seret ke dalamnya Button "Save" (`saveButton`) dan Button "Cancel" (`cancelButton`).
7. Klik ganda **Save** dan **Cancel** masing-masing untuk membuat method `saveButtonActionPerformed` dan `cancelButtonActionPerformed`.

> ✅ **Checkpoint (desain):** tab **Design** menampilkan lima pasang label-input, dengan baris pertama berupa dua Label (bukan Label dan Text Field), lalu tombol Save dan Cancel di bawahnya.

Isi handler-nya di tab **Source**:

![AddAccountDialog.java, constructor](../assets/code/pertemuan-14/p14-01-addaccountdialog-constructor.png){width=68%}

![AddAccountDialog.java, saveButtonActionPerformed](../assets/code/pertemuan-14/p14-01-addaccountdialog-savehandler.png){width=72%}

Perhatikan: `accountNumberValueLabel` sebuah `JLabel` (bukan `JTextField`), sehingga nomor rekening TIDAK BISA diedit pengguna. Dengan nomor selalu berasal dari `Bank.nextAccountNumber()`, tidak ada cara bagi pengguna untuk mengetikkan nomor yang bentrok dengan rekening lain; kelas galat "nomor rekening duplikat" dicegah lewat desain, bukan diperiksa lalu ditolak setelah terlanjur diketik.

Sekarang perbarui `BankMiniFrame` supaya tombol **Add Account...** membuka dialog ini, bukan mengisi form di jendela utama:

1. Buka `BankMiniFrame` dalam mode **Design**. Hapus komponen `formPanel` yang mungkin sudah pernah dibuat pada latihan sebelumnya (bila ada), sisakan `accountScrollPane` dan panel tombol.
2. Seret **Panel** di bawah `accountScrollPane`, beri nama `buttonsPanel`, **Set Layout > Grid Layout** dengan rows = 1, columns = 2. Seret ke dalamnya Button "Add Account..." (`addAccountButton`) dan Button "Refresh" (`refreshButton`).
3. Klik ganda **Add Account...** untuk membuat method `addAccountButtonActionPerformed`.

Isi method-method pendukung di tab **Source**:

![BankMiniFrame.java, constructor, seedSampleAccounts, loadAccounts](../assets/code/pertemuan-14/p14-01-bankminiframe-fields.png){width=68%}

![BankMiniFrame.java, addAccountButtonActionPerformed](../assets/code/pertemuan-14/p14-01-bankminiframe-addaccounthandler.png){width=60%}

<!-- TODO(screenshot): mock-up SVG, bukan tangkapan layar asli (sandbox penulisan tidak punya X server/Xvfb). Ganti dengan screenshot AddAccountDialog sungguhan begitu ada akses ke display; lihat conventions/bank-mini.md bagian "Verifikasi visual GUI tanpa NetBeans/X server". -->
![Mock-up dialog Add Account dengan nomor rekening A003 terisi otomatis](../assets/uml/p14-add-account-dialog.png){width=55%}

> ✅ **Checkpoint:** jalankan **Run Project** (F6), klik **Add Account...**. Dialog terbuka dengan nomor rekening (mis. `A003`) sudah terisi otomatis dan tidak bisa diketik ulang. Isi nama pemilik dan saldo awal, pilih jenis rekening, lalu klik **Save**. Dialog tertutup dan baris baru muncul di tabel jendela utama.

> ⚠️ **Jika gagal:** apabila muncul dialog "Invalid input" padahal isian tampak benar, periksa apakah **Initial Balance** hanya berisi angka (tanpa titik ribuan atau simbol mata uang), sebab `Double.parseDouble()` tidak bisa mengurai format semacam itu. Apabila dialog terbuka tetapi nomor rekening kosong atau selalu `A001`, periksa apakah `accountNumberValueLabel.setText(bank.nextAccountNumber())` benar-benar dipanggil di constructor SETELAH `initComponents()`.

### Langkah 2: Setor dan Tarik Saldo

> **Konsep Singkat: Baris Terpilih pada JTable.** `JTable` menyediakan `getSelectedRow()`, mengembalikan indeks baris yang sedang disorot pengguna (atau `-1` bila belum ada yang dipilih). Nilai pada sel tertentu di baris itu bisa diambil lewat `getValueAt(baris, kolom)`. Alih-alih memeriksa `-1` di dalam setiap tombol aksi lalu menampilkan peringatan, indeks terpilih ini juga bisa dipantau lewat `ListSelectionListener` untuk MENGAKTIFKAN atau MENONAKTIFKAN tombol aksinya sendiri; pengguna secara fisik tidak akan bisa mengklik tombol yang belum boleh diklik, sebuah galat yang dicegah lewat desain, bukan ditangkap setelah terjadi.

Tambahkan dua tombol aksi, dengan langkah serupa Langkah 1:

1. Buka `BankMiniFrame` Design. Ubah `buttonsPanel` menjadi rows = 1, columns = 4.
2. Seret dua Button baru DI ANTARA "Add Account..." dan "Refresh": "Deposit..." (`depositButton`) dan "Withdraw..." (`withdrawButton`).
3. Pada panel **Properties** kedua tombol tsb, ubah properti **enabled** menjadi `false`, sehingga keduanya tampil abu-abu (nonaktif) sampai ada baris yang dipilih.
4. Klik ganda masing-masing tombol untuk membuat method `depositButtonActionPerformed` dan `withdrawButtonActionPerformed`.

> ✅ **Checkpoint (desain):** tombol Deposit... dan Withdraw... tampil abu-abu (nonaktif) di tab **Design**.

Isi kedua method, ditambah satu method bantu untuk membaca rekening yang sedang dipilih, dan satu method yang menghubungkan pemilihan baris ke status tombol:

![BankMiniFrame.java, configureSelectionListener](../assets/code/pertemuan-14/p14-02-selectionlistener.png){width=62%}

![BankMiniFrame.java, getSelectedAccount](../assets/code/pertemuan-14/p14-02-getselectedaccount.png){width=60%}

![BankMiniFrame.java, depositButtonActionPerformed](../assets/code/pertemuan-14/p14-02-deposithandler.png){width=72%}

![BankMiniFrame.java, withdrawButtonActionPerformed](../assets/code/pertemuan-14/p14-02-withdrawhandler.png){width=72%}

Panggil `configureSelectionListener()` di constructor, SETELAH `loadAccounts()`, supaya listener-nya terpasang sebelum pengguna sempat memilih baris apa pun.

<!-- TODO(screenshot): mock-up SVG, bukan tangkapan layar asli. Ganti dengan screenshot BankMiniFrame sungguhan begitu ada akses ke display; lihat conventions/bank-mini.md bagian "Verifikasi visual GUI tanpa NetBeans/X server". -->
![Mock-up jendela BankMiniFrame dengan satu baris terpilih, tombol Deposit dan Withdraw aktif](../assets/uml/p14-window-selected.png){width=62%}

> ✅ **Checkpoint:** jalankan **Run Project** (F6). Tombol **Deposit...** dan **Withdraw...** tampil abu-abu sampai sebuah baris rekening diklik. Pilih satu baris, kedua tombol menyala; klik **Deposit...**, sebuah dialog input muncul bertuliskan "Deposit amount for A001 (Nadia):"; ketik sebuah angka, saldo pada baris itu bertambah. Coba juga **Withdraw...** dengan jumlah yang melebihi batas rekening tsb (lihat Pertemuan 6-7 untuk aturan tiap jenis rekening): sebuah dialog error muncul menampilkan pesan `InsufficientBalanceException`, bukan program yang berhenti paksa.

> ⚠️ **Jika gagal:** apabila tombol Deposit/Withdraw tetap bisa diklik walau belum ada baris terpilih, periksa properti **enabled** kedua tombol sudah diatur `false` di Matisse, dan apakah `configureSelectionListener()` benar-benar dipanggil di constructor. Apabila mengklik Cancel pada dialog input jumlah malah menampilkan dialog "Amount must be a number", periksa apakah method memeriksa `input == null` (Cancel) SEBELUM mencoba mem-parse isiannya.

## D. Tugas dan Hasil Kerja

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- Screenshot jendela `BankMiniFrame` setelah Langkah 2, termasuk satu percobaan Withdraw yang gagal (dialog error tampil).
- **Tugas mandiri:**
  1. Tambahkan tombol **Process Month End** (perluas `buttonsPanel` menjadi rows = 1, columns = 5), memanggil `bank.processMonthEnd()` (dari Pertemuan 10) lalu menampilkan dialog konfirmasi "Month-end processing complete.":

     ![BankMiniFrame.java, processMonthEndButtonActionPerformed](../assets/code/pertemuan-14/p14-tugas-processmonthend.png){width=68%}

  2. Jawab secara singkat (2-3 kalimat untuk masing-masing pertanyaan):
     - (a) mengapa validasi `NumberFormatException` tetap diperlukan di GUI, padahal dialog input jumlah "seharusnya" hanya diisi angka?
     - (b) Bandingkan bagaimana `InsufficientBalanceException` ditangani di Pertemuan 10 (dicetak ke konsol) dengan di jobsheet ini (ditampilkan sebagai dialog). Apa yang berubah, dan apa yang tetap sama?

Perhatikan satu hal yang sengaja belum dibahas: siapa pun yang menjalankan `BankMiniFrame` langsung mendapat akses penuh ke seluruh rekening, tanpa login sama sekali. Aplikasi perbankan sungguhan tidak pernah dirilis seperti ini. Pertemuan 15 menutup celah ini dengan menambahkan mekanisme autentikasi yang sesungguhnya, sekaligus alasan konkret pertama mengapa Bank Mini butuh database: kredensial login harus disimpan dan diperiksa dari data yang tersimpan, bukan dari nilai yang ditulis langsung di kode Java.

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot) | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | Tombol Process Month End benar dan jawaban konsep tepat | Sebagian tugas selesai meski jawaban belum lengkap |
