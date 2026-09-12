---
marp: true
theme: default
paginate: true
size: 16:9
style: |
  section {
    font-family: 'Helvetica Neue', Arial, sans-serif;
    padding: 56px 72px;
    justify-content: center;
  }
  section.lead {
    background: linear-gradient(135deg, #1e3a8a 0%, #1d4ed8 55%, #2563eb 100%);
    color: #fff;
    justify-content: center;
  }
  section.lead h1, section.lead h2, section.lead p {
    color: #fff;
  }
  section.divider {
    background: #1d4ed8;
    color: #fff;
  }
  section.divider h1 {
    color: #fff;
    font-size: 2.2em;
  }
  section.divider h2 {
    color: #bfdbfe;
  }
  section.divider p {
    color: #bfdbfe;
  }
  h1 {
    color: #1d4ed8;
    font-size: 1.6em;
  }
  h2 {
    color: #1d4ed8;
  }
  table {
    font-size: 0.72em;
    width: 100%;
  }
  code {
    background: #f1f5f9;
    color: #0f172a;
  }
  .term-box {
    border-left: 6px solid #1d4ed8;
    background: #eff6ff;
    padding: 10px 18px;
    margin: 10px 0;
    font-size: 0.82em;
  }
  .term-box b {
    color: #1d4ed8;
  }
  .tip-box {
    border-left: 6px solid #16a34a;
    background: #f0fdf4;
    padding: 10px 18px;
    margin: 10px 0;
    font-size: 0.8em;
  }
  .warn-box {
    border-left: 6px solid #dc2626;
    background: #fef2f2;
    padding: 10px 18px;
    margin: 10px 0;
    font-size: 0.8em;
  }
  .cols {
    display: flex;
    gap: 28px;
    align-items: center;
  }
  .cols > div {
    flex: 1;
  }
  .cols img {
    display: block;
    margin: 0 auto;
    max-width: 100%;
    max-height: 460px;
  }
  .footnote {
    font-size: 0.55em;
    color: #64748b;
    margin-top: 8px;
  }
  img {
    display: block;
    margin: 0 auto 12px auto;
    max-width: 90%;
    max-height: 420px;
  }
---

<!-- _class: lead -->

# Pemrograman Berbasis Objek
## RTI253007 &nbsp;|&nbsp; D-IV Teknik Informatika

Pertemuan 15: **Persistensi dengan JDBC dan Mekanisme Autentikasi**

Dari data yang menguap saat aplikasi ditutup menjadi data yang benar-benar tersimpan

---

## Yang Akan Kamu Pelajari

- Mengapa penyimpanan in-memory kehilangan seluruh datanya setiap aplikasi ditutup, dan bagaimana JDBC mengatasinya
- Cara Dependency Inversion Principle beraksi lagi: mengganti implementasi penyimpanan tanpa mengubah kode yang memakainya
- Mengapa aplikasi tanpa mekanisme login adalah risiko nyata, dan bagaimana password semestinya disimpan (di-hash, bukan apa adanya)
- Penerapan pada Bank Mini: `JdbcAccountRepository`, `JdbcUserRepository`, dan `LoginFrame`

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 15.
</div>

---

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Persistensi, data yang bertahan
- **Sesi 2 (50')**: Autentikasi, siapa yang boleh masuk
- **Sesi 3 (50')**: Menerapkan JDBC ke Bank Mini
- **Sesi 4 (50')**: Menerapkan autentikasi ke Bank Mini

---

<!-- _class: divider -->

# Bagian 1
## Persistensi: Data yang Bertahan

Sesi 1 dari 4

---

## Aplikasi yang Lupa Segalanya

Sebuah aplikasi catatan sederhana menyimpan seluruh datanya di variabel, dalam struktur seperti `ArrayList` atau `HashMap`, selama aplikasi berjalan. Begitu aplikasi ditutup, seluruh isi memori (RAM) dibersihkan oleh sistem operasi, termasuk data yang baru saja dimasukkan pengguna.

<div class="warn-box">
Menutup aplikasi lalu membukanya kembali membuat seluruh data yang sempat dimasukkan pengguna hilang tanpa jejak, seolah tidak pernah ada.
</div>

---

## Mengapa Ini Penting?

Hampir semua aplikasi nyata, dari aplikasi kasir kecil sampai sistem perbankan skala besar, harus mengingat datanya lintas sesi: pengguna menutup laptop, me-restart komputer, atau server aplikasi di-deploy ulang, tanpa mengharapkan data pelanggan ikut hilang. Aplikasi yang datanya menguap setiap restart tidak layak dipakai untuk pekerjaan sungguhan, sebesar apa pun kualitas logika bisnisnya.

<div class="term-box">
Persistensi (data yang bertahan melewati siklus hidup satu proses aplikasi) adalah salah satu alasan paling mendasar mengapa hampir setiap aplikasi nyata terhubung ke sebuah database, bukan sekadar menyimpan data di variabel.
</div>

---

## RAM Dibersihkan, Disk Tidak

![h:260 Kontras antara penyimpanan in-memory yang hilang saat restart dan penyimpanan database yang bertahan](../assets/illustrations/persistence-restart.svg)

<div class="term-box">
JDBC (Java Database Connectivity) adalah API bawaan Java untuk terhubung ke database relasional lewat perintah SQL. SQLite menyimpan seluruh database dalam satu berkas biasa di disk, sehingga tidak butuh server database terpisah, cocok untuk aplikasi kecil sampai menengah.
</div>

---

## Mengelola Dependency Lewat Maven

SQLite bukan bagian dari Java bawaan, ia adalah pustaka (library) pihak ketiga. Pertanyaannya: bagaimana `import org.sqlite...` bisa dipakai, padahal tidak pernah diketik ulang isi kodenya sendiri?

<div class="term-box">
Proyek Maven mendeklarasikan pustaka yang dibutuhkan sebagai <b>dependency</b> di dalam <code>pom.xml</code>, cukup nama dan nomor versinya. Maven mengunduh pustaka itu (dan seluruh pustaka lain yang dibutuhkannya) dari server pusat bernama <i>Maven Central</i>, lalu menaruhnya di classpath proyek secara otomatis. Tidak ada berkas <code>.jar</code> yang perlu diunduh atau disalin manual.
</div>

---

## Contoh: Mendeklarasikan Dependency

```xml
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.45.1.0</version>
</dependency>
```

Tiga koordinat ini (`groupId`, `artifactId`, `version`) sudah cukup bagi Maven untuk menemukan, mengunduh, dan memasang pustaka SQLite JDBC Driver yang dipakai pertemuan ini.

---

## Apache Commons DbUtils: Menyederhanakan JDBC

Menulis `Connection`/`Statement`/`ResultSet` secara manual di setiap method itu berulang dan gampang lupa ditutup, seperti dibahas di slide sebelumnya. Maven memudahkan pemakaian pustaka yang sudah memecahkan masalah ini.

<div class="term-box">
<b>Apache Commons DbUtils</b> adalah pustaka pihak ketiga (dependency Maven kedua di pertemuan ini) yang membungkus pola JDBC berulang lewat kelas <code>QueryRunner</code>: satu pemanggilan method menggantikan seluruh blok <code>Connection</code>/<code>PreparedStatement</code>/<code>try</code>-with-resources.
</div>

---

## Contoh Kode: Koneksi JDBC, Mentah vs Lewat DbUtils

```java
// JDBC mentah
try (Connection conn = DriverManager.getConnection(url);
        PreparedStatement stmt = conn.prepareStatement(sql)) {
    stmt.setString(1, accountNumber);
    stmt.executeUpdate();
}

// Lewat QueryRunner (Apache Commons DbUtils)
run.update(sql, accountNumber);
```

Baris kedua melakukan hal yang PERSIS sama seperti blok pertama: membuka koneksi, menyiapkan `PreparedStatement`, mengisi parameter, mengeksekusinya, lalu menutup semuanya, hanya saja dilakukan di dalam `run.update(...)`.

---

## Kesalahan Umum: Lupa Menutup Koneksi

<div class="warn-box">
<b>Salah:</b> membuka <code>Connection conn = DriverManager.getConnection(url);</code> tanpa <code>try</code>-with-resources maupun <code>conn.close()</code> manual setelahnya.
</div>

**Benar:** setiap `Connection`, `Statement`, dan `ResultSet` wajib ditutup setelah dipakai, sebab masing-masing memegang sumber daya sistem (berkas, memori) yang tidak dilepaskan otomatis. Pola `try (Connection conn = ...; PreparedStatement stmt = ...) { ... }` menutup keduanya otomatis begitu blok selesai. `QueryRunner` yang dibuat dari `DataSource` (bukan dari `Connection` langsung) menghilangkan seluruh kelas bug ini: setiap `run.update(...)`/`run.query(...)` mengambil dan menutup koneksinya sendiri.

---

## Latihan

Sebuah method membuka `Connection` di baris pertama, lalu melakukan beberapa query, TANPA `try`-with-resources maupun `close()` di akhir method.

Apa risikonya bila method ini dipanggil ribuan kali dalam aplikasi yang berjalan lama? Jelaskan.

---

## Jawaban Latihan

**Kebocoran sumber daya (resource leak).** Setiap pemanggilan membuka koneksi baru yang tidak pernah ditutup, sumber daya sistem yang dipegangnya (soket, berkas database) terus menumpuk. Setelah ribuan pemanggilan, aplikasi bisa kehabisan koneksi yang tersedia atau bahkan menyebabkan seluruh aplikasi berhenti merespons. Perbaikan: bungkus `Connection` dalam `try`-with-resources supaya selalu ditutup otomatis, apa pun yang terjadi di dalam bloknya.

---

## Rangkuman Bagian 1

- Data yang hanya disimpan di variabel (RAM) hilang setiap aplikasi ditutup; persistensi berarti data bertahan melewati siklus hidup aplikasi.
- JDBC menghubungkan Java ke database lewat SQL; SQLite menyimpan seluruh database dalam satu berkas di disk.
- `Connection`, `Statement`, dan `ResultSet` wajib ditutup setelah dipakai, `try`-with-resources melakukannya otomatis.

Selanjutnya: Bagian 2 membahas autentikasi, cara memastikan hanya pengguna yang sah yang bisa mengakses data yang kini tersimpan.

---

<!-- _class: divider -->

# Bagian 2
## Autentikasi: Siapa yang Boleh Masuk

Sesi 2 dari 4

---

## Pintu Tanpa Kunci

Sebuah aplikasi desktop yang langsung menampilkan seluruh data begitu dijalankan, tanpa pernah menanyakan siapa penggunanya, sama seperti kantor yang pintunya dibiarkan terbuka untuk siapa saja. Siapa pun yang bisa menjalankan programnya otomatis mendapat akses penuh ke seluruh data di dalamnya.

<div class="warn-box">
Tanpa mekanisme login, tidak ada cara membedakan pengguna yang sah dari siapa pun yang kebetulan bisa menjalankan aplikasinya.
</div>

---

## Mengapa Ini Penting?

Aplikasi bisnis sungguhan, dari sistem kasir sampai perbankan, menyimpan data yang tidak boleh diakses sembarang orang: saldo rekening, riwayat transaksi, data pelanggan. Insiden kebocoran data yang berulang kali muncul di berita nyaris selalu melibatkan sistem yang gagal memverifikasi identitas penggunanya sebelum memberi akses. Login bukan fitur tambahan yang bisa ditunda, melainkan syarat dasar sebuah aplikasi layak disebut aman.

<div class="term-box">
Autentikasi (memverifikasi siapa penggunanya) berbeda dari otorisasi (menentukan apa yang boleh dilakukan pengguna itu). Jobsheet ini baru membangun autentikasi; otorisasi bertingkat (mis. teller vs. admin) adalah topik lanjutan yang bisa dikembangkan sebagai proyek mandiri di Pertemuan 16.
</div>

---

## Gerbang Sebelum Data

![h:260 Kontras antara aplikasi tanpa login dan aplikasi dengan gerbang login](../assets/illustrations/login-gate.svg)

<div class="term-box">
Sebuah <b>login gate</b> memeriksa kredensial (username dan password) SEBELUM mengizinkan akses ke data sungguhan. Tanpa gerbang ini, seluruh data sama saja terbuka untuk siapa pun yang menjalankan aplikasinya.
</div>

---

## Hashing: Fungsi Satu Arah

<div class="warn-box">
Password tidak boleh disimpan apa adanya (plain text). Password di-hash (diubah lewat fungsi satu arah yang tidak bisa dibalik) sebelum disimpan; saat login, password yang diketik di-hash ulang lalu dibandingkan dengan hash tersimpan.
</div>

<div class="term-box">
Fungsi hash mengubah input apa pun menjadi deretan karakter tetap panjangnya, dan MUSTAHIL dibalik untuk mendapatkan kembali input aslinya. Bahkan bila database bocor, penyerang hanya mendapat hash-nya, bukan password asli penggunanya.
</div>

---

## Contoh Kode: Meng-hash Password dengan SHA-256

```java
MessageDigest digest = MessageDigest.getInstance("SHA-256");
byte[] hashBytes = digest.digest(plainPassword.getBytes("UTF-8"));
StringBuilder hex = new StringBuilder();
for (byte b : hashBytes) {
    hex.append(String.format("%02x", b));
}
return hex.toString();
```

Password yang sama selalu menghasilkan hash yang sama persis, tetapi hash-nya sendiri tidak bisa dibalik menjadi password aslinya.

---

## Kesalahan Umum: Membandingkan Password Polos dengan Hash

<div class="warn-box">
<b>Salah:</b> menulis <code>if (user.getPasswordHash().equals(password))</code>, membandingkan hash yang tersimpan langsung dengan password polos yang baru diketik pengguna.
</div>

**Benar:** password yang baru diketik harus DI-HASH DULU dengan algoritma yang sama, baru dibandingkan dengan hash tersimpan: `user.getPasswordHash().equals(PasswordHasher.hash(password))`. Membandingkan hash dengan teks polos hampir selalu bernilai `false`, bahkan untuk password yang sebenarnya benar.

---

## Latihan

Tabel `users` menyimpan `passwordHash` untuk `"nadia"` sebagai hasil `PasswordHasher.hash("rahasia123")`.

Pengguna login dengan username `"nadia"` dan password `"rahasia123"`. Jelaskan langkah yang harus dilakukan kode `LoginFrame` untuk memutuskan apakah login ini berhasil.

---

## Jawaban Latihan

Kode HARUS meng-hash ulang password yang baru diketik (`PasswordHasher.hash("rahasia123")`), baru membandingkan hasilnya dengan `passwordHash` yang tersimpan di database untuk `"nadia"`. Karena fungsi hash menghasilkan output yang sama persis untuk input yang sama, kedua hash ini akan cocok dan login dinyatakan berhasil, TANPA pernah membandingkan password polosnya secara langsung.

---

## Rangkuman Bagian 2

- Aplikasi tanpa login gate tidak bisa membedakan pengguna sah dari siapa pun yang menjalankan programnya.
- Password di-hash (fungsi satu arah) sebelum disimpan; password asli tidak pernah tersimpan sama sekali.
- Verifikasi login membandingkan hash dengan hash, bukan membandingkan hash dengan password polos.

Selanjutnya: Bagian 3 menerapkan JDBC ke penyimpanan data rekening Bank Mini.

---

<!-- _class: divider -->

# Bagian 3
## Menerapkan JDBC ke Bank Mini

Sesi 3 dari 4

---

## Mengganti Penyimpanan Tanpa Mengubah Pemakainya

![h:300 AccountRepository sekarang diimplementasikan oleh JdbcAccountRepository, menggantikan versi in-memory](../assets/uml/p15-accountrepository-jdbc.png)

<div class="tip-box">
Ini adalah Dependency Inversion Principle (Pertemuan 11) beraksi lagi: <code>Bank</code> hanya bergantung pada interface <code>AccountRepository</code>, sehingga penyimpanan in-memory bisa diganti penyimpanan database hanya dengan menulis implementasi baru, tanpa menyentuh <code>Bank</code> sama sekali.
</div>

---

## Contoh Kode: JdbcAccountRepository.save()

```java
public JdbcAccountRepository(String databasePath) {
    SQLiteDataSource dataSource = new SQLiteDataSource();
    dataSource.setUrl("jdbc:sqlite:" + databasePath);
    this.run = new QueryRunner(dataSource);
    createTableIfNotExists();
}

public void save(Account account) {
    String sql = "INSERT OR REPLACE INTO accounts "
            + "(account_number, owner_name, balance) VALUES (?, ?, ?)";
    run.update(sql, account.getAccountNumber(), account.getOwner().getName(), account.getBalance());
}
```

`QueryRunner` dibuat sekali di constructor dari `SQLiteDataSource`. `INSERT OR REPLACE` menyimpan baris baru, atau menimpa baris lama bila nomor rekeningnya sudah ada; `run.update(...)` yang mengurus koneksi dan parameter-binding-nya.

---

## Bank.saveAccount(): Menyimpan Ulang Setelah Perubahan

Penyimpanan in-memory "menyimpan" perubahan secara otomatis: objek yang diubah di memori adalah objek yang sama persis dengan yang tersimpan di `HashMap`. Penyimpanan JDBC TIDAK bekerja seperti ini, mengubah objek `Account` di memori tidak pernah otomatis mengubah barisnya di database.

```java
public void saveAccount(Account account) {
    repository.save(account);
}
```

---

## Contoh Kode: Memanggil saveAccount() Setelah Perubahan

```java
account.deposit(amount);
bank.saveAccount(account);
amountField.setText("");
loadAccounts();
```

`saveAccount(account)` wajib dipanggil ulang setelah setiap `deposit()`, `withdraw()`, atau `processMonthEnd()`, persis di titik yang sama tempat perubahan saldo terjadi.

---

## Kesalahan Umum: Lupa Memanggil saveAccount()

<div class="warn-box">
<b>Salah:</b> memanggil <code>account.deposit(amount);</code> lalu langsung <code>loadAccounts();</code>, tanpa <code>bank.saveAccount(account);</code> di antaranya.
</div>

**Benar:** tanpa `saveAccount(...)`, perubahan saldo hanya terjadi di objek `Account` dalam memori, TIDAK PERNAH tersimpan ke database. `loadAccounts()` kebetulan tetap menampilkan saldo yang benar (sebab membaca dari objek yang sama di memori), sehingga bug ini mudah lolos tanpa disadari, sampai aplikasi ditutup dan dibuka lagi, saldo kembali ke nilai sebelum setoran.

---

## Latihan

Pengguna menyetor Rp 50.000 ke `SavingsAccount` A001, tabel langsung menampilkan saldo baru yang benar. Namun baris `bank.saveAccount(account);` ternyata terlewat ditulis di kode `depositButtonActionPerformed`.

Apa yang terjadi pada saldo A001 setelah aplikasi ditutup lalu dibuka kembali? Jelaskan.

---

## Jawaban Latihan

**Saldo kembali ke nilai SEBELUM setoran Rp 50.000.** Setoran hanya mengubah objek `Account` di memori, dan `loadAccounts()` membaca dari objek yang sama sehingga tabel sempat menampilkan saldo yang "benar". Tetapi tanpa `saveAccount(...)`, baris di database tidak pernah diperbarui; begitu aplikasi ditutup, objek di memori hilang, dan saat dibuka kembali data dimuat ulang dari database yang masih menyimpan saldo lama.

---

## Rangkuman Bagian 3

- `AccountRepository` kini diimplementasikan `JdbcAccountRepository`, menyimpan data ke berkas `bankmini.db`, tanpa mengubah `Bank` sama sekali (Dependency Inversion Principle).
- Penyimpanan JDBC tidak otomatis tersinkron dengan objek di memori; `Bank.saveAccount(...)` wajib dipanggil ulang setelah setiap perubahan.
- Lupa memanggil `saveAccount(...)` adalah bug yang mudah lolos, sebab tampilannya tetap benar sampai aplikasi benar-benar ditutup dan dibuka kembali.

Selanjutnya: Bagian 4 menerapkan pola yang sama untuk autentikasi pengguna.

---

<!-- _class: divider -->

# Bagian 4
## Menerapkan Autentikasi ke Bank Mini

Sesi 4 dari 4

---

## Pola yang Sama, Diterapkan Lagi

![h:300 UserRepository diimplementasikan oleh InMemoryUserRepository dan JdbcUserRepository, persis pola AccountRepository](../assets/uml/p15-userrepository-auth.png)

<div class="term-box">
<code>UserRepository</code> mengikuti bentuk persis sama dengan <code>AccountRepository</code>: satu interface, satu implementasi in-memory sebagai preview, satu implementasi JDBC untuk penyimpanan sungguhan. Begitu sebuah pola desain dikuasai, ia bisa dipakai berulang untuk kebutuhan yang berbeda.
</div>

---

## Contoh Kode: JdbcUserRepository.findByUsername()

```java
public User findByUsername(String username) {
    String sql = "SELECT * FROM users WHERE username = ?";
    return run.query(sql, new UserHandler(), username);
}
```

`QueryRunner` yang sama dari Bagian 3 dipakai lagi di sini; `UserHandler` adalah `ResultSetHandler` kecil yang mengubah satu baris hasil query menjadi objek `User`, pola yang PERSIS sama dengan `AccountHandler` pada `JdbcAccountRepository`.

---

## LoginFrame: Gerbang Sebelum BankMiniFrame

![h:220 Jendela LoginFrame kosong sebelum diisi](../assets/screenshots/pertemuan-15/p15-login-screen.png)

`Main.java` kini menjalankan `LoginFrame` lebih dulu, bukan langsung membuka `BankMiniFrame`. `BankMiniFrame` baru terbuka setelah kredensial yang dimasukkan cocok dengan data yang tersimpan di tabel `users`.

---

## Contoh Kode: loginButtonActionPerformed

```java
User user = userRepository.findByUsername(username);
if (user == null || !user.getPasswordHash().equals(PasswordHasher.hash(password))) {
    JOptionPane.showMessageDialog(this,
            "Invalid username or password.",
            "Login failed", JOptionPane.ERROR_MESSAGE);
    passwordField.setText("");
    return;
}
dispose();
new BankMiniFrame().setVisible(true);
```

---

## Login Gagal, Password Dikosongkan

![h:200 Dialog galat setelah mencoba login dengan password salah](../assets/screenshots/pertemuan-15/p15-login-failed.png)

`user == null` (username tidak ditemukan) dan password yang salah ditangani lewat pengecekan yang SAMA, keduanya menampilkan pesan generik "Invalid username or password.", tidak membocorkan mana yang sebenarnya salah.

<div class="warn-box">
SHA-256 polos di jobsheet ini murni penyederhanaan untuk latihan. Sistem produksi memakai algoritma yang dirancang khusus untuk password, seperti bcrypt, Argon2, atau PBKDF2.
</div>

---

## Kesalahan Umum: Pesan Galat yang Membocorkan Informasi

<div class="warn-box">
<b>Salah:</b> menampilkan pesan berbeda untuk "username tidak ditemukan" dan "password salah", mis. <code>"Username not found"</code> vs <code>"Wrong password"</code>.
</div>

**Benar:** kode `LoginFrame` yang sebenarnya sengaja menampilkan pesan generik yang SAMA untuk keduanya, `"Invalid username or password."`. Pesan yang berbeda membocorkan informasi ke penyerang: mereka jadi tahu username mana yang valid, tinggal menebak passwordnya saja.

---

## Latihan

Pengguna mencoba login dengan username `"admin"` yang TIDAK ADA di tabel `users`.

Apa yang dikembalikan `userRepository.findByUsername("admin")`, dan pesan apa yang ditampilkan ke pengguna? Jelaskan mengapa pesannya dibuat seperti itu.

---

## Jawaban Latihan

`findByUsername("admin")` mengembalikan `null`, sebab tidak ada baris dengan username tersebut. Pengecekan `user == null || ...` bernilai `true` (short-circuit, bagian setelah `||` tidak perlu dievaluasi), sehingga dialog "Invalid username or password." ditampilkan, PERSIS pesan yang sama seandainya usernya ada tetapi passwordnya salah. Pesan generik ini sengaja dipakai supaya penyerang tidak bisa membedakan "username salah" dari "password salah".

---

## Rangkuman Bagian 4

- `UserRepository` mengikuti pola persis `AccountRepository`: satu interface, implementasi in-memory dan JDBC.
- `LoginFrame` memeriksa kredensial lewat `PasswordHasher.hash(...)` sebelum membuka `BankMiniFrame`.
- Pesan galat login sengaja dibuat generik, tidak membedakan "username salah" dari "password salah", supaya tidak membocorkan informasi ke penyerang.

---

## Rangkuman Pertemuan 15

- Persistensi berarti data bertahan melewati siklus hidup aplikasi; JDBC menghubungkan Java ke database, `Connection`/`Statement`/`ResultSet` wajib ditutup setelah dipakai.
- Autentikasi memverifikasi identitas pengguna sebelum memberi akses; password disimpan sebagai hash satu arah, tidak pernah apa adanya.
- Bank Mini menerapkan keduanya: `JdbcAccountRepository` menggantikan penyimpanan in-memory tanpa mengubah `Bank`, dan `LoginFrame` menjadi gerbang sebelum `BankMiniFrame` terbuka.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab JDBC, Security

Oracle Java Tutorials: "JDBC Basics", "MessageDigest Class"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 15

---

## Diskusi

`seedSampleAccountsIfEmpty()` dan `seedDefaultUserIfEmpty()` sama-sama memeriksa lebih dulu apakah tabelnya masih kosong sebelum menambahkan data contoh. Jelaskan dengan kata-katamu sendiri: apa yang akan terjadi pada data rekening dan kredensial pengguna apabila pengecekan itu dihapus dan data contoh langsung ditambahkan tanpa syarat setiap aplikasi dijalankan? Kaitkan jawabanmu dengan konsep persistensi yang baru dipelajari.
