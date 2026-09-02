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

Pertemuan 7: **Overriding dan Overloading**

Menulis ulang perilaku warisan, dan menambah versi baru sebuah method

---

## Yang Akan Kamu Pelajari

- Method overriding: subclass menulis ulang method warisan superclass dengan tanda tangan yang sama, ditandai `@Override`
- Cara memanggil versi superclass lewat `super.method(...)` alih-alih menulis ulang semuanya dari awal
- Kata kunci `final` untuk mencegah sebuah method di-override
- Method overloading: beberapa method dengan nama sama tetapi daftar parameter berbeda, dan bagaimana ini berbeda dari overriding
- Penerapan pada Bank Mini: `canWithdraw()` yang di-override tiap jenis rekening, dan `deposit()` yang di-overload

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 7.
</div>

---

<!-- _class: divider -->

# Bagian 1
## Method Overriding

---

## Menulis Ulang Method Warisan

Pertemuan 6 menunjukkan bahwa subclass mewarisi method superclass apa adanya. Kadang perilaku yang diwarisi tidak cocok untuk subclass tertentu: `Dog` dan `Cat` sama-sama mewarisi `makeSound()` dari `Animal`, tetapi tentu saja bunyinya seharusnya berbeda.

<div class="term-box">
<b>Overriding</b> adalah menulis ulang method superclass di dalam subclass, dengan nama dan daftar parameter (tanda tangan) yang sama persis. Java memanggil versi milik objek yang sebenarnya saat program berjalan, bukan versi yang dideklarasikan di tipe variabelnya.
</div>

---

## Mengapa Ini Penting?

Bayangkan sebuah sistem pembayaran dengan puluhan jenis metode (kartu kredit, transfer bank, e-wallet), dengan superclass `PaymentMethod` yang subclass-nya terus bertambah seiring waktu. Tanpa overriding, setiap kali ditambahkan jenis pembayaran baru, kode yang memproses pembayaran juga harus diubah untuk menangani kasus baru itu, berisiko merusak jenis pembayaran lain yang sudah berjalan baik.

<div class="term-box">
Overriding memungkinkan setiap subclass menyediakan perilakunya sendiri tanpa mengubah satu baris pun kode superclass atau subclass lain yang sudah ada. Prinsip "boleh diperluas, tetapi tidak boleh diubah" ini adalah salah satu dari lima prinsip SOLID, disebut Open/Closed Principle, yang dibahas lebih lanjut pada Pertemuan 11.
</div>

---

## Anotasi `@Override`

<div class="tip-box">
Anotasi <code>@Override</code> memberi tahu compiler untuk memeriksa bahwa method benar-benar menulis ulang method superclass dengan tanda tangan yang sama persis. Bila ada kesalahan ketik pada nama method, compiler menampilkan galat alih-alih diam-diam membuat method baru yang tidak pernah terpanggil.
</div>

<div class="warn-box">
<code>@Override</code> sendiri tidak wajib secara sintaks, tetapi selalu disertakan sebagai kebiasaan baik: galat yang terdeteksi lebih awal jauh lebih murah diperbaiki daripada bug yang baru ketahuan saat program berjalan.
</div>

---

## Memanggil Versi Superclass: `super.method(...)`

<div class="term-box">
Sebuah method yang di-override boleh tetap memanggil versi superclass-nya lewat <code>super.namaMethod(...)</code>, biasanya untuk menambahkan perilaku baru tanpa menulis ulang seluruh isi method dari awal.
</div>

Pola ini sering dipakai ketika subclass hanya ingin menambahkan sedikit informasi pada perilaku yang sudah ada, misalnya mencetak baris tambahan setelah baris yang sudah dicetak superclass.

---

## Mencegah Method Di-override: `final`

<div class="term-box">
Method yang ditandai <code>final</code> tidak dapat di-override oleh subclass mana pun. Java akan menampilkan galat compile bila ada subclass yang mencoba menulis ulang method tersebut.
</div>

<div class="warn-box">
Gunakan <code>final</code> secukupnya: hanya ketika ada alasan kuat suatu perilaku harus selalu sama di seluruh subclass. Menandai semua method sebagai <code>final</code> justru menghilangkan manfaat utama inheritance, yaitu kemampuan subclass menyesuaikan perilaku.
</div>

---

<!-- _class: divider -->

# Bagian 2
## Method Overloading

---

## Nama Sama, Parameter Berbeda

<div class="term-box">
<b>Overloading</b> adalah menambahkan method dengan nama yang sama tetapi daftar parameter (jumlah atau tipe) yang berbeda. Compiler memilih versi mana yang dipanggil berdasarkan argumen yang diberikan saat pemanggilan, ditentukan sejak program dikompilasi, bukan saat program berjalan.
</div>

Contoh umum: `println()` pada `System.out` sebenarnya adalah puluhan method overload, masing-masing menerima tipe argumen yang berbeda (`String`, `int`, `double`, `boolean`, dan seterusnya), tetapi semuanya dipanggil dengan nama yang sama.

---

## Mengapa Ini Penting?

Tanpa overloading, setiap variasi cara memanggil sebuah operasi butuh nama method yang berbeda, misalnya `depositAmount()`, `depositAmountWithNote()`, `depositAmountWithNoteAndDate()`. Semakin banyak variasi, semakin sulit programmer lain mengingat nama mana yang harus dipakai untuk kebutuhan tertentu.

<div class="term-box">
Overloading membuat API sebuah kelas terasa alami untuk dipakai: satu nama method yang sama, <code>deposit(...)</code>, cukup untuk seluruh variasi, dan compiler yang menentukan versi mana yang cocok berdasarkan argumen yang diberikan. Inilah sebabnya method seperti <code>println()</code> di Java memiliki puluhan versi overload, alih-alih puluhan nama method yang berbeda-beda.
</div>

---

## Overriding vs Overloading

![h:300 Perbandingan overriding dan overloading](../assets/illustrations/override-vs-overload.svg)

Keduanya terdengar mirip namanya, tetapi mekanismenya sangat berbeda: overriding mengganti perilaku method warisan di subclass (diputuskan saat program berjalan), sementara overloading menambah versi baru sebuah method di kelas yang sama (diputuskan saat program dikompilasi).

---

<!-- _class: divider -->

# Bagian 3
## Menerapkan ke Bank Mini

---

## canWithdraw() yang Di-override Tiap Jenis Rekening

![Account dengan canWithdraw sebagai titik override, SavingsAccount dan CheckingAccount menulis ulang aturannya masing-masing](../assets/uml/p07-account-hierarchy.png)

Pertemuan 6 menunjukkan bahwa `overdraftLimit` milik `CheckingAccount` belum memengaruhi apa pun, karena `withdraw()` yang diwarisi hanya tahu satu aturan generik. Dengan `canWithdraw()` yang di-override, `SavingsAccount` kini menjaga saldo minimum dan `CheckingAccount` kini benar-benar bisa ditarik melebihi saldo hingga batas overdraft-nya.

---

## deposit() yang Di-overload

<div class="term-box">
<code>Account</code> mendapat versi kedua dari <code>deposit(double amount)</code>, yaitu <code>deposit(double amount, String note)</code>, yang menerima catatan tambahan lalu memanggil versi pertama untuk logika penyimpanannya. Keduanya adalah method yang berbeda di kelas yang sama, dipilih Java berdasarkan jumlah argumen yang diberikan saat pemanggilan.
</div>

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Object-Oriented Programming: Overriding, Overloading

Oracle Java Tutorials: "Overriding and Hiding Methods", "Defining Methods" (overloading)

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 7

---

## Diskusi

`Employee.describe()` (Pertemuan 6) ditandai `final` karena formatnya harus selalu konsisten untuk seluruh jenis pegawai. Perhatikan kembali method-method `Account` yang baru saja kamu buat (`printInfo()`, `canWithdraw()`, `deposit()`, dan lain-lain): apakah ada salah satu di antaranya yang menurutmu juga layak ditandai `final`? Jelaskan alasanmu, atau jelaskan mengapa tidak ada yang membutuhkannya.
