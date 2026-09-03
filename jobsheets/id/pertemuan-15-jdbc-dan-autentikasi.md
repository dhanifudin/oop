# Jobsheet Praktikum: Pertemuan 15
## Persistensi dengan JDBC dan Mekanisme Autentikasi

| | |
|---|---|
| **Mata Kuliah** | Praktikum Pemrograman Berbasis Objek (RTI253008) |
| **Pertemuan** | 15 (Minggu 15) |
| **Durasi** | 1 &times; 4 &times; 50' praktikum; 1 &times; 1 &times; 50' tugas/laporan mandiri |
| **Kode Awal** | `code/bank-mini/pertemuan-14/` (checkpoint Pertemuan 14) |
| **Kode Akhir** | proyek Maven `bank-mini` setelah Langkah 2, disalin sebagai checkpoint `code/bank-mini/pertemuan-15/` |

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, mahasiswa mampu:

1. Mengganti implementasi `AccountRepository` dari penyimpanan in-memory menjadi penyimpanan JDBC/SQLite, tanpa mengubah kode `Bank` atau `BankMiniFrame` yang memakainya.
2. Menjelaskan mengapa method yang mengubah data (`deposit()`, `withdraw()`, `processMonthEnd()`) harus memanggil ulang penyimpanan secara eksplisit pada penyimpanan berbasis database, berbeda dengan penyimpanan in-memory.
3. Membangun mekanisme login sederhana (username dan password ter-hash) yang membatasi akses ke `BankMiniFrame` hanya untuk pengguna yang kredensialnya tersimpan di database.

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, NetBeans.
- **Proyek**: pertemuan ini melanjutkan proyek Maven `bank-mini` dari Pertemuan 14. Tambahkan dependency SQLite JDBC Driver pada `pom.xml` (klik kanan proyek > **Properties > Libraries > Add Dependency**, cari `org.xerial:sqlite-jdbc:3.45.1.0`).
- **Verifikasi cepat** sebelum memulai:
  ```bash
  mvn -version
  ```

> **Tanpa NetBeans?** Tambahkan dependency berikut secara manual ke `pom.xml`, di dalam elemen `<dependencies>`:
> ```xml
> <dependency>
>   <groupId>org.xerial</groupId>
>   <artifactId>sqlite-jdbc</artifactId>
>   <version>3.45.1.0</version>
> </dependency>
> ```
> Seluruh langkah kerja jobsheet ini tidak membutuhkan NetBeans (tidak ada desain form baru), sehingga bisa diikuti penuh dengan `mvn -q compile exec:java`.

## C. Langkah Kerja

### Langkah 1: JdbcAccountRepository, Rekening Tersimpan di Database

> **Konsep Singkat: JDBC.** JDBC (Java Database Connectivity) adalah API bawaan Java untuk berkomunikasi dengan database relasional lewat perintah SQL biasa. Tiga elemen utamanya: `Connection` (koneksi ke satu berkas/server database), `Statement`/`PreparedStatement` (pembawa perintah SQL, `PreparedStatement` memakai tanda tanya `?` sebagai placeholder nilai supaya aman dari SQL injection), dan `ResultSet` (baris hasil query, dibaca satu per satu lewat `next()`). SQLite menyimpan seluruh database dalam satu berkas biasa di disk (`bankmini.db`), sehingga tidak butuh server database terpisah, cocok untuk latihan.

Sejak Pertemuan 11, `Bank` sudah bergantung pada interface `AccountRepository`, bukan pada implementasi konkretnya secara langsung (Dependency Inversion Principle). Berkat itu, penyimpanan in-memory bisa diganti penyimpanan JDBC hanya dengan menulis implementasi baru, tanpa menyentuh `Bank` sama sekali:

![JdbcAccountRepository.java, constructor dan save](../assets/code/pertemuan-15/p15-01-jdbcaccountrepository-save.png){width=75%}

![JdbcAccountRepository.java, findByNumber, findAll, dan mapRow](../assets/code/pertemuan-15/p15-01-jdbcaccountrepository-find.png){width=75%}

`createTableIfNotExists()` dipanggil di constructor, membuat tabel `accounts` otomatis pada kontak pertama ke database, sehingga checkpoint ini tidak membutuhkan langkah setup database terpisah. Kolom `account_type` menyimpan `"SAVINGS"` atau `"CHECKING"` supaya `mapRow()` tahu subclass `Account` mana yang harus dibuat ulang saat membaca baris.

Perbarui `BankMiniFrame` supaya memakai `JdbcAccountRepository`, bukan lagi `InMemoryAccountRepository`:

![BankMiniFrame.java, constructor memakai JdbcAccountRepository](../assets/code/pertemuan-15/p15-01-bankminiframe-constructor.png){width=75%}

Perhatikan `seedSampleAccountsIfEmpty()`: dua rekening contoh hanya ditambahkan apabila tabel `accounts` masih kosong. Tanpa pengecekan ini, kedua rekening contoh akan ditulis ulang dengan saldo awalnya setiap kali aplikasi dijalankan, menimpa perubahan saldo yang sudah tersimpan dari sesi sebelumnya, persis kebalikan dari tujuan persistensi.

Satu hal penting yang mudah terlewat: penyimpanan in-memory "menyimpan" perubahan secara otomatis, sebab objek `Account` yang diubah adalah objek YANG SAMA dengan yang tersimpan di map. Penyimpanan JDBC tidak bekerja seperti itu, mengubah objek `Account` di memori tidak otomatis menulis ulang barisnya di database. `Bank` mendapat method baru untuk menutup celah ini:

![Bank.java, method saveAccount](../assets/code/pertemuan-15/p15-01-bank-saveaccount.png){width=68%}

![Bank.java, processMonthEnd memanggil repository.save setelah bunga diterapkan](../assets/code/pertemuan-15/p15-01-bank-processmonthend.png){width=68%}

`BankMiniFrame` memanggil `bank.saveAccount(account)` setelah setiap transaksi berhasil:

![BankMiniFrame.java, depositButtonActionPerformed dan withdrawButtonActionPerformed memanggil saveAccount](../assets/code/pertemuan-15/p15-01-bankminiframe-savecalls.png){width=75%}

> ✅ **Checkpoint:** jalankan **Run Project** (F6), lakukan **Deposit** pada salah satu rekening, lalu **tutup aplikasi sepenuhnya** dan jalankan ulang. Saldo yang baru saja diubah tetap tampil, tidak kembali ke nilai awal, bukti bahwa data benar-benar tersimpan di berkas `bankmini.db`, bukan hanya di memori selama aplikasi berjalan.

> ⚠️ **Jika gagal:** apabila saldo kembali ke nilai awal setiap kali aplikasi dijalankan ulang, periksa dua kemungkinan: (1) `bank.saveAccount(account)` benar-benar dipanggil setelah `account.deposit(amount)`/`account.withdraw(amount)`, bukan hanya `loadAccounts()`; (2) `seedSampleAccountsIfEmpty()` memeriksa `bank.getAllAccounts().isEmpty()` sebelum menambah rekening contoh, bukan menambahkannya tanpa syarat.

### Langkah 2: Login Sederhana dengan Password Ter-hash

> **Konsep Singkat: Hashing Password.** Menyimpan password apa adanya (plain text) di database sangat berisiko: siapa pun yang mengakses berkas database bisa membaca seluruh password pengguna. Hashing mengubah password menjadi deretan karakter acak (hash) lewat fungsi satu arah, tidak bisa dibalik untuk mendapatkan password asli. Saat login, password yang diketik pengguna di-hash ulang dengan fungsi yang sama, lalu hasilnya dibandingkan dengan hash tersimpan, password asli tidak pernah disimpan maupun dibandingkan langsung.

Bank Mini memakai SHA-256 (tersedia langsung lewat `java.security.MessageDigest`, tanpa dependency tambahan) untuk latihan ini:

![PasswordHasher.java](../assets/code/pertemuan-15/p15-02-passwordhasher.png){width=68%}

> ⚠️ **Bukan untuk produksi.** SHA-256 polos (tanpa salt, satu kali hash) mudah diserang lewat rainbow table pada sistem produksi sungguhan. Aplikasi nyata memakai algoritma yang secara khusus dirancang untuk password, seperti bcrypt, Argon2, atau PBKDF2, yang menambahkan salt acak dan sengaja dibuat lambat dihitung. `PasswordHasher` di jobsheet ini murni penyederhanaan untuk latihan, bukan contoh siap pakai.

Mengikuti pola `AccountRepository` yang sudah dipelajari sejak Pertemuan 11 (interface plus implementasi in-memory sebagai preview, baru kemudian versi JDBC), kredensial pengguna memakai struktur yang sama persis:

![InMemoryUserRepository.java](../assets/code/pertemuan-15/p15-02-inmemoryuserrepository.png){width=68%}

`InMemoryUserRepository` di atas menunjukkan bahwa `UserRepository` bisa saja diimplementasikan sesederhana `HashMap` untuk keperluan pengujian. Namun karena kredensial harus tetap ada meski aplikasi ditutup, checkpoint ini memakai versi JDBC:

![JdbcUserRepository.java, constructor dan seedDefaultUserIfEmpty](../assets/code/pertemuan-15/p15-02-jdbcuserrepository-seed.png){width=75%}

![JdbcUserRepository.java, save dan findByUsername](../assets/code/pertemuan-15/p15-02-jdbcuserrepository-find.png){width=75%}

Buat kelas `LoginFrame` (JFrame biasa, form login: `Username` sebagai `JTextField`, `Password` sebagai **`JPasswordField`**, bukan `JTextField` biasa, sehingga karakter yang diketik tersembunyi sebagai titik) dengan satu tombol **Login**. Isi handler tombolnya:

![LoginFrame.java, loginButtonActionPerformed](../assets/code/pertemuan-15/p15-02-loginhandler.png){width=75%}

![Jendela LoginFrame kosong sebelum diisi](../assets/screenshots/pertemuan-15/p15-login-screen.png){width=55%}

![Dialog galat setelah mencoba login dengan password salah](../assets/screenshots/pertemuan-15/p15-login-failed.png){width=55%}

Terakhir, `Main.java` menjalankan `LoginFrame` lebih dulu, bukan langsung membuka `BankMiniFrame`:

![Main.java menjalankan LoginFrame](../assets/code/pertemuan-15/p15-02-main.png){width=68%}

![Jendela BankMiniFrame setelah login berhasil](../assets/screenshots/pertemuan-15/p15-bankmini-after-login.png){width=60%}

> ✅ **Checkpoint:** jalankan **Run Project** (F6). Jendela `LoginFrame` muncul lebih dulu. Coba login dengan username `teller1` dan password yang salah, dialog galat "Invalid username or password." muncul dan jendela login tetap terbuka. Login ulang dengan password yang benar, `teller123`, jendela login tertutup dan `BankMiniFrame` terbuka menampilkan daftar rekening seperti biasa.

> ⚠️ **Jika gagal:** apabila login dengan kredensial yang benar tetap ditolak, periksa apakah password yang diketik di-hash dulu lewat `PasswordHasher.hash()` sebelum dibandingkan, bukan dibandingkan langsung dengan `user.getPasswordHash()` (hash tidak akan pernah sama dengan teks polos).

## D. Tugas dan Deliverable

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- Screenshot jendela `LoginFrame`, dialog galat percobaan login yang gagal, dan `BankMiniFrame` setelah login berhasil.
- **Tugas mandiri:**
  1. Ubah constructor `BankMiniFrame` supaya menerima parameter `username`, lalu tampilkan `"Bank Mini - Logged in as: <username>"` sebagai judul jendela. Sesuaikan `LoginFrame` supaya meneruskan username yang berhasil login ke constructor tersebut:

     ![BankMiniFrame.java, constructor menerima parameter username](../assets/code/pertemuan-15/p15-tugas-bankminiframe-constructor.png){width=75%}

     ![LoginFrame.java, meneruskan username ke BankMiniFrame](../assets/code/pertemuan-15/p15-tugas-loginhandler.png){width=75%}

  2. Tambahkan satu pengguna kedua pada `seedDefaultUserIfEmpty()` (mis. `teller2` dengan password `teller456`):

     ![JdbcUserRepository.java, dua pengguna contoh](../assets/code/pertemuan-15/p15-tugas-seconduser.png){width=68%}

     ![Jendela BankMiniFrame menampilkan username pada judul setelah login sebagai teller2](../assets/screenshots/pertemuan-15/p15-bankmini-tugas-login.png){width=60%}

  3. Jawab secara singkat (2-3 kalimat untuk masing-masing pertanyaan): (a) mengapa `seedSampleAccountsIfEmpty()` dan `seedDefaultUserIfEmpty()` sama-sama memeriksa kondisi kosong sebelum menambah data, apa yang terjadi bila pengecekan itu dihapus? (b) `UserRepository` dan `AccountRepository` adalah dua interface yang berbeda, tetapi keduanya mengikuti pola desain yang sama. Sebutkan pola tersebut, dan jelaskan satu keuntungan konkret dari mengikutinya di sini.

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot) | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | Judul jendela dan pengguna kedua benar, jawaban konsep tepat | Sebagian tugas selesai meski jawaban belum lengkap |
