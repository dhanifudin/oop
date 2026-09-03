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
- Dependency Inversion Principle beraksi lagi: mengganti implementasi penyimpanan tanpa mengubah kode yang memakainya
- Mengapa aplikasi tanpa mekanisme login adalah risiko nyata, dan bagaimana password semestinya disimpan (di-hash, bukan apa adanya)
- Penerapan pada Bank Mini: `JdbcAccountRepository`, `JdbcUserRepository`, dan `LoginFrame`

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 15.
</div>

---

<!-- _class: divider -->

# Bagian 1
## Persistensi: Data yang Bertahan

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

![Kontras antara penyimpanan in-memory yang hilang saat restart dan penyimpanan database yang bertahan](../assets/illustrations/persistence-restart.svg)

<div class="term-box">
JDBC (Java Database Connectivity) adalah API bawaan Java untuk terhubung ke database relasional lewat perintah SQL. SQLite menyimpan seluruh database dalam satu berkas biasa di disk, sehingga tidak butuh server database terpisah, cocok untuk aplikasi kecil sampai menengah.
</div>

---

<!-- _class: divider -->

# Bagian 2
## Autentikasi: Siapa yang Boleh Masuk

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

![Kontras antara aplikasi tanpa login dan aplikasi dengan gerbang login](../assets/illustrations/login-gate.svg)

<div class="warn-box">
Password tidak boleh disimpan apa adanya (plain text). Password di-hash (diubah lewat fungsi satu arah yang tidak bisa dibalik) sebelum disimpan; saat login, password yang diketik di-hash ulang lalu dibandingkan dengan hash tersimpan.
</div>

---

<!-- _class: divider -->

# Bagian 3
## Menerapkan ke Bank Mini

---

## Mengganti Penyimpanan Tanpa Mengubah Pemakainya

![h:340 AccountRepository sekarang diimplementasikan oleh JdbcAccountRepository, menggantikan versi in-memory](../assets/uml/p15-accountrepository-jdbc.png)

<div class="tip-box">
Ini adalah Dependency Inversion Principle (Pertemuan 11) beraksi lagi: <code>Bank</code> hanya bergantung pada interface <code>AccountRepository</code>, sehingga penyimpanan in-memory bisa diganti penyimpanan database hanya dengan menulis implementasi baru, tanpa menyentuh <code>Bank</code> sama sekali.
</div>

---

## JdbcAccountRepository dan Bank.saveAccount()

`BankMiniFrame` kini memakai `JdbcAccountRepository`, menyimpan seluruh data rekening ke berkas `bankmini.db`. Satu detail penting: penyimpanan in-memory "menyimpan" perubahan secara otomatis (objek yang diubah adalah objek yang sama dengan yang tersimpan), sedangkan penyimpanan JDBC tidak, `Bank` mendapat method baru, `saveAccount()`, yang harus dipanggil ulang setelah setiap `deposit()`, `withdraw()`, atau `processMonthEnd()`.

<div class="tip-box">
Checkpoint jobsheet ini membuktikan persistensi secara konkret: ubah saldo sebuah rekening, tutup aplikasi sepenuhnya, jalankan ulang, saldo yang berubah tetap ada.
</div>

---

## Pola yang Sama, Diterapkan Lagi

![h:340 UserRepository diimplementasikan oleh InMemoryUserRepository dan JdbcUserRepository, persis pola AccountRepository](../assets/uml/p15-userrepository-auth.png)

<div class="term-box">
<code>UserRepository</code> mengikuti bentuk persis sama dengan <code>AccountRepository</code>: satu interface, satu implementasi in-memory sebagai preview, satu implementasi JDBC untuk penyimpanan sungguhan. Begitu sebuah pola desain dikuasai, ia bisa dipakai berulang untuk kebutuhan yang berbeda.
</div>

---

## LoginFrame dan PasswordHasher

`Main.java` kini menjalankan `LoginFrame` lebih dulu, bukan langsung membuka `BankMiniFrame`. Password yang diketik pengguna di-hash lewat `PasswordHasher` (SHA-256 lewat `java.security.MessageDigest`) sebelum dibandingkan dengan hash yang tersimpan di tabel `users`. `BankMiniFrame` baru terbuka setelah kredensial yang dimasukkan cocok dengan data yang tersimpan di database.

<div class="warn-box">
SHA-256 polos di jobsheet ini murni penyederhanaan untuk latihan. Sistem produksi memakai algoritma yang dirancang khusus untuk password, seperti bcrypt, Argon2, atau PBKDF2.
</div>

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab JDBC, Security

Oracle Java Tutorials: "JDBC Basics", "MessageDigest Class"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 15

---

## Diskusi

`seedSampleAccountsIfEmpty()` dan `seedDefaultUserIfEmpty()` sama-sama memeriksa lebih dulu apakah tabelnya masih kosong sebelum menambahkan data contoh. Jelaskan dengan kata-katamu sendiri: apa yang akan terjadi pada data rekening dan kredensial pengguna apabila pengecekan itu dihapus dan data contoh langsung ditambahkan tanpa syarat setiap aplikasi dijalankan? Kaitkan jawabanmu dengan konsep persistensi yang baru dipelajari.
