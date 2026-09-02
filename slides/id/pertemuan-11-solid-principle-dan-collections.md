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

Pertemuan 11: **SOLID Principle dan Collections**

Lima prinsip desain kelas yang baik, dan struktur data siap pakai

---

## Yang Akan Kamu Pelajari

- Collections: `ArrayList` dan `Map`, menggantikan array biasa yang ukurannya tetap dan pencariannya harus satu per satu
- Lima prinsip SOLID: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion
- Penerapan pada Bank Mini: `Bank` beralih ke `Map`, `Transaction` memisahkan tanggung jawab pencatatan, `AccountRepository` memisahkan Bank dari cara penyimpanan data

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 11.
</div>

---

<!-- _class: divider -->

# Bagian 1
## Collections

---

## Batasan Array Biasa

Array biasa harus ditentukan ukurannya sejak awal dibuat, dan mencari satu elemen di dalamnya berarti memeriksa elemen satu per satu sampai ditemukan.

<div class="warn-box">
Menentukan ukuran array di awal berarti menebak: terlalu kecil berarti kehabisan tempat, terlalu besar berarti memori terbuang percuma. Mencari data lewat pemeriksaan satu per satu juga semakin lambat seiring bertambahnya data.
</div>

---

## Mengapa Ini Penting?

Bayangkan aplikasi dengan jutaan data pelanggan disimpan di array. Mencari satu pelanggan berarti, dalam kasus terburuk, memeriksa jutaan elemen satu per satu sebelum ditemukan (atau dipastikan tidak ada). Semakin besar aplikasi berkembang, semakin terasa lambat setiap operasi pencarian, sementara batas ukuran array yang ditentukan di awal cepat atau lambat pasti terlampaui.

<div class="term-box">
Inilah sebabnya hampir semua aplikasi nyata memakai struktur data dari Java Collections Framework, bukan array biasa: ukurannya menyesuaikan otomatis, dan pencarian lewat kunci bisa dilakukan langsung tanpa memeriksa data lain sama sekali.
</div>

---

## ArrayList dan Map

![Array berukuran tetap dengan pencarian satu per satu, dibandingkan Map dengan pencarian langsung lewat kunci](../assets/illustrations/collections-motivation.svg)

<div class="term-box">
<b>ArrayList</b> adalah daftar yang ukurannya menyesuaikan otomatis. <b>Map</b> (paling umum <code>HashMap</code> atau <code>LinkedHashMap</code>) menyimpan pasangan kunci-nilai, pencarian berdasarkan kunci dilakukan langsung tanpa memeriksa elemen lain.
</div>

---

<!-- _class: divider -->

# Bagian 2
## SOLID Principle

---

## Lima Prinsip Desain Kelas yang Baik

<div class="term-box">
<b>SOLID</b> adalah lima prinsip yang membantu kelas tetap mudah dipahami, diperluas, dan diuji seiring aplikasi bertambah besar: <b>S</b>ingle Responsibility, <b>O</b>pen/Closed, <b>L</b>iskov Substitution, <b>I</b>nterface Segregation, <b>D</b>ependency Inversion.
</div>

Beberapa di antaranya sudah kamu praktikkan tanpa disadari sejak beberapa pertemuan lalu. Bagian ini memberi nama formalnya, sekaligus melengkapi dua yang belum pernah dibahas.

---

## Single Responsibility Principle (SRP)

![Satu kelas dengan tiga tanggung jawab, dipisah menjadi tiga kelas masing-masing satu tanggung jawab](../assets/illustrations/srp-split.svg)

<div class="term-box">
Satu kelas sebaiknya memiliki satu tanggung jawab, satu alasan untuk berubah. Kelas yang mencampur banyak tanggung jawab menjadi sulit dipahami, dan perubahan pada satu tanggung jawab berisiko memengaruhi tanggung jawab lain yang sebenarnya tidak berhubungan.
</div>

---

## Mengapa Ini Penting?

Bayangkan kelas `Report` yang sekaligus menghitung total, memformat tampilan, dan mengirim email, semuanya bercampur dalam satu kelas. Tim yang mengerjakan perubahan format tampilan (misalnya dari teks ke PDF) bisa saja tanpa sengaja mengubah baris yang memengaruhi perhitungan total, karena keduanya berada di file yang sama, meskipun keduanya sebenarnya sama sekali tidak berhubungan.

<div class="term-box">
Kelas dengan satu tanggung jawab jauh lebih aman diubah: mengganti cara pengiriman email tidak pernah menyentuh logika perhitungan sama sekali, karena keduanya sudah berada di kelas yang berbeda. Semakin besar aplikasi, semakin mahal harga yang dibayar ketika prinsip ini diabaikan sejak awal.
</div>

---

## Open/Closed dan Liskov Substitution (Recap)

<div class="term-box">
<b>Open/Closed Principle</b>: kelas sebaiknya terbuka untuk diperluas, tertutup untuk diubah. Kamu sudah mempraktikkan ini sejak Pertemuan 7: menambah jenis rekening baru tidak pernah mengubah kode <code>Account</code> yang sudah ada, hanya menambah subclass baru dengan <code>canWithdraw()</code>-nya sendiri.
</div>

<div class="term-box">
<b>Liskov Substitution Principle</b>: subclass harus bisa menggantikan superclass-nya di mana pun tanpa mengubah kebenaran program. <code>SavingsAccount</code> dan <code>CheckingAccount</code> selalu bisa dipakai di mana pun kode mengharapkan <code>Account</code>, sejak Pertemuan 6-7, tanpa membuat kode itu berperilaku salah.
</div>

---

## Interface Segregation dan Dependency Inversion (Recap dan Baru)

<div class="term-box">
<b>Interface Segregation Principle</b>: interface sebaiknya kecil dan fokus, kelas tidak dipaksa mengimplementasikan method yang tidak relevan baginya. Kamu sudah mempraktikkan ini di Pertemuan 9: <code>InterestBearing</code> hanya diterapkan pada rekening berbunga, bukan ditambahkan ke <code>Account</code> untuk semua jenis rekening.
</div>

<div class="term-box">
<b>Dependency Inversion Principle</b>: kelas tingkat tinggi sebaiknya bergantung pada interface (abstraksi), bukan pada implementasi konkret. Ini baru diterapkan secara eksplisit pada Bank Mini di pertemuan ini, dibahas pada Bagian 3.
</div>

---

## Mengapa Ini Penting?

Bayangkan kelas `OrderProcessor` yang bergantung langsung pada kelas konkret `MySqlDatabase`. Migrasi ke database lain, atau menambahkan pengujian otomatis (yang butuh basis data tiruan agar tidak menyentuh data sungguhan), sama-sama menjadi sulit tanpa mengubah `OrderProcessor` itu sendiri, karena ia "tahu" secara langsung bahwa penyimpanannya pasti MySQL.

<div class="term-box">
Dependency Inversion Principle membalik arah ketergantungan ini: <code>OrderProcessor</code> cukup bergantung pada interface <code>Repository</code>, implementasi konkretnya (MySQL, penyimpanan sementara, atau versi tiruan untuk pengujian) bebas berganti tanpa <code>OrderProcessor</code> pernah tahu atau peduli. Inilah prinsip yang sama yang membuat <code>AccountRepository</code> pada Bank Mini bisa berganti implementasi di Pertemuan 15 tanpa mengubah <code>Bank.java</code>.
</div>

---

<!-- _class: divider -->

# Bagian 3
## Menerapkan ke Bank Mini

---

## Bank Beralih dari Array ke Map

`Bank` sejauh ini menyimpan rekening di `Account[] accounts` berukuran tetap, `findAccount()` memeriksa elemen satu per satu. `Bank` kini menyimpan rekening di `Map<String, Account>`, memakai nomor rekening sebagai kunci, `findAccount()` menjadi pencarian langsung.

---

## Transaction: Single Responsibility Principle pada Bank Mini

![h:300 Account dan Transaction, satu Account memiliki banyak Transaction](../assets/uml/p11-transaction.png)

`Account` sejauh ini tidak mencatat riwayat transaksinya sama sekali. Kelas `Transaction` kini menjadi satu-satunya yang bertanggung jawab merepresentasikan satu transaksi, dipisah dari `Account` yang bertanggung jawab menjaga saldo dan aturan bisnis.

---

## AccountRepository: Dependency Inversion Principle pada Bank Mini

![Bank bergantung pada interface AccountRepository, diimplementasikan InMemoryAccountRepository](../assets/uml/p11-accountrepository.png)

`Bank` kini bergantung pada interface `AccountRepository`, bukan pada `Map` secara langsung. `InMemoryAccountRepository` adalah implementasi hari ini; Pertemuan 15 mengganti cara penyimpanan menjadi database lewat `JdbcAccountRepository`, tanpa mengubah `Bank.java` satu baris pun.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Collections; Martin, *Agile Software Development* (SOLID Principles)

Oracle Java Tutorials: "The Collections Framework"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 11

---

## Diskusi

`AccountRepository` disebut memungkinkan Pertemuan 15 mengganti `InMemoryAccountRepository` dengan `JdbcAccountRepository` tanpa mengubah `Bank.java` sama sekali. Jelaskan dengan kata-katamu sendiri: apa yang akan terjadi (kode apa yang harus diubah, dan di berapa banyak tempat) apabila `Bank` sejak awal bergantung langsung pada `Map<String, Account>` tanpa lewat interface `AccountRepository`, lalu suatu hari cara penyimpanannya harus diganti ke database?
