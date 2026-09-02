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

Pertemuan 14: **GUI dengan NetBeans Matisse (Bagian 2)**

Menerima input pengguna dengan aman

---

## Yang Akan Kamu Pelajari

- Validasi input di titik masuk (boundary): input dari pengguna tidak pernah bisa dipercaya begitu saja
- Menampilkan exception sebagai dialog, bukan mencetaknya ke konsol atau membiarkan program berhenti paksa
- Membaca baris yang sedang dipilih pada `JTable` untuk menentukan objek mana yang diproses
- Penerapan pada Bank Mini: form tambah rekening, tombol setor dan tarik saldo pada `BankMiniFrame`

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 14.
</div>

---

<!-- _class: divider -->

# Bagian 1
## Validasi Input di Titik Masuk

---

## Pengguna Bisa Mengetik Apa Saja

Kolom input teks pada GUI tidak membatasi apa yang bisa diketik pengguna: kolom yang dimaksudkan untuk angka tetap saja bisa diisi huruf, dikosongkan, atau diisi format yang tidak terduga.

<div class="warn-box">
Kode yang langsung memakai isi kolom input tanpa memeriksanya lebih dulu akan berhenti paksa (exception) begitu isiannya tidak sesuai harapan.
</div>

---

## Mengapa Ini Penting?

Sebagian besar bug yang dilaporkan pengguna aplikasi nyata bukan berasal dari algoritma yang salah, melainkan dari input tak terduga yang tidak pernah divalidasi: kolom dikosongkan, format tanggal berbeda dari yang diharapkan, angka desimal ditulis dengan pemisah yang berbeda. Validasi input, terutama di titik masuk data (boundary) sebuah aplikasi, adalah salah satu praktik paling mendasar untuk mencegah kelas bug ini.

<div class="term-box">
Aplikasi yang gagal memvalidasi input pada titik masuknya juga membuka celah keamanan: banyak kerentanan perangkat lunak, dari yang sekadar mengganggu sampai yang serius, berakar dari data yang dipercaya begitu saja tanpa diperiksa lebih dulu.
</div>

---

## Validasi, Bukan Sekadar Berharap

![Perbandingan input yang tidak divalidasi (program berhenti paksa) dengan input yang divalidasi (dialog pesan, program tetap berjalan)](../assets/illustrations/input-validation-boundary.svg)

<div class="term-box">
Pola yang dipakai sama seperti exception handling di Pertemuan 10: bungkus operasi yang berisiko gagal (mis. <code>Double.parseDouble(...)</code>) dalam <code>try</code>, tangani kegagalannya di <code>catch</code>. Bedanya, di GUI, penanganannya berupa dialog yang bisa langsung dibaca pengguna, bukan pesan di konsol.
</div>

---

<!-- _class: divider -->

# Bagian 2
## Baris Terpilih pada JTable

---

## Satu Tabel, Banyak Baris, Satu yang Dipilih

Sebuah `JTable` bisa menampilkan puluhan baris sekaligus. Ketika pengguna mengklik sebuah tombol aksi (mis. "Withdraw"), kode itu sendiri tidak tahu rekening mana yang dimaksud, kecuali diberi tahu baris mana yang sedang disorot pengguna.

---

## getSelectedRow() dan getValueAt()

![h:260 Baris yang sedang dipilih pada tabel menentukan objek mana yang diproses](../assets/illustrations/table-selection.svg)

<div class="term-box">
<code>JTable.getSelectedRow()</code> mengembalikan indeks baris yang sedang disorot pengguna (atau <code>-1</code> bila belum ada yang dipilih). <code>getValueAt(baris, kolom)</code> mengambil nilai pada sel tertentu di baris itu. Pola "cari indeks terpilih, lalu ambil datanya" ini dipakai berulang di hampir semua aplikasi yang menampilkan daftar dan tombol aksi, e-mail client, pengelola berkas, atau aplikasi spreadsheet sekalipun.
</div>

---

<!-- _class: divider -->

# Bagian 3
## Menerapkan ke Bank Mini

---

## Form Tambah Rekening

`BankMiniFrame` kini punya form untuk menambah rekening baru: nomor rekening, nama pemilik, telepon, jenis rekening, dan saldo awal. Sebelum objek `Account` benar-benar dibuat, seluruh isian diperiksa: nomor rekening dan nama tidak boleh kosong, saldo awal harus berhasil diurai sebagai angka (`Double.parseDouble`, dibungkus `try`/`catch`). Kegagalan validasi ditampilkan lewat `JOptionPane`, bukan membiarkan `NumberFormatException` menghentikan program.

---

## Setor dan Tarik Saldo

Tombol **Deposit** dan **Withdraw** memakai `getSelectedAccount()`, method bantu yang membaca baris terpilih di tabel lalu mencari objek `Account`-nya lewat `Bank.findAccount()`. `Withdraw` membungkus pemanggilan `account.withdraw()` dalam `try`/`catch InsufficientBalanceException`, persis pola yang sudah dipelajari di Pertemuan 10, kali ini pesannya ditampilkan lewat dialog alih-alih dicetak ke konsol.

<div class="tip-box">
Tidak satu pun kelas <code>Account</code>, <code>Bank</code>, atau <code>InsufficientBalanceException</code> yang diubah untuk mendukung GUI ini. GUI hanya memanggil method yang sudah ada sejak beberapa pertemuan lalu, lewat cara yang berbeda.
</div>

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab GUI Components, Exception Handling

Oracle Java Tutorials: "How to Use Tables", "Validating Input"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 14

---

## Diskusi

`depositButtonActionPerformed` dan `withdrawButtonActionPerformed` sama-sama memanggil `getSelectedAccount()` dan menampilkan dialog peringatan bila belum ada baris yang dipilih. Jelaskan dengan kata-katamu sendiri: mengapa validasi "apakah ada baris yang dipilih" ini perlu dilakukan di KEDUA method secara terpisah, dan bagaimana caranya method itu bisa dipakai bersama tanpa menduplikasi logikanya (kaitkan jawabanmu dengan Single Responsibility Principle dari Pertemuan 11).
