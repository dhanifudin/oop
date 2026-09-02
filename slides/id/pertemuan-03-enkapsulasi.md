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

Pertemuan 3: **Enkapsulasi**

Melindungi data sebuah objek dari akses yang tidak terkendali

---

## Yang Akan Kamu Pelajari

- Risiko yang muncul apabila atribut sebuah objek dapat diakses langsung dari luar
- Konsep encapsulation: menyembunyikan data, menyediakan akses lewat method
- Pola getter dan setter, termasuk setter yang memvalidasi nilai masukan
- Penerapan encapsulation pada kelas `Account` di studi kasus Bank Mini

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 3.
</div>

---

<!-- _class: divider -->

# Bagian 1
## Risiko Akses Langsung

---

## Ketika Atribut Bisa Diubah Siapa Saja

Bayangkan kelas `Thermostat` dengan atribut publik `temperature`: kode lain dapat langsung menulis nilai apa pun ke `temperature`, tanpa melalui method apa pun. Selama nilainya masuk akal, hal ini tidak terlihat bermasalah. Namun apa yang mencegah kode lain mengisi `temperature` dengan angka yang mustahil secara fisik, misalnya `-50`, atau melewati validasi sama sekali?

<div class="warn-box">
Atribut publik berarti tidak ada satu pun titik yang menjamin data objek selalu berada dalam kondisi valid.
</div>

---

## Ilustrasi: Akses Langsung yang Tidak Diperiksa

![h:340 Kode luar menulis langsung ke atribut publik, tanpa validasi apa pun](../assets/illustrations/direct-access-bug.svg)

<div class="warn-box">
Karena atribut bersifat publik, tidak ada kode yang dijalankan untuk memeriksa nilai baru sebelum disimpan. Nilai yang tidak masuk akal, misalnya suhu <code>-50</code>, diterima begitu saja.
</div>

---

## Mengapa Ini Penting?

Bug seperti ini bukan sekadar risiko teoretis. Pada aplikasi nyata, atribut publik berarti setiap bagian program, termasuk kode yang ditulis tim lain atau ditambahkan bertahun-tahun kemudian, punya akses langsung untuk mengubah data tersebut. Programmer yang memperbaiki bug semacam ini sering harus menelusuri seluruh basis kode untuk menemukan setiap tempat yang mengubah atribut itu, karena tidak ada satu titik tunggal yang bisa diperiksa.

<div class="term-box">
Inilah salah satu alasan encapsulation dianggap salah satu prinsip paling mendasar dalam OOP: dengan menyembunyikan data di balik method, tim pengembang bisa mengubah cara data disimpan di dalam kelas kapan pun, tanpa perlu khawatir kode di luar kelas ikut rusak, selama method publiknya tidak berubah.
</div>

---

<!-- _class: divider -->

# Bagian 2
## Encapsulation: Data di Balik Method

---

## Konsep Encapsulation

![h:340 Data privat sebuah objek hanya bisa dicapai lewat method, tidak ada jalan pintas](../assets/illustrations/capsule-shield.svg)

<div class="term-box">
<b>Encapsulation</b> berarti data sebuah objek disembunyikan (dibuat <code>private</code>) dan hanya dapat diakses atau diubah melalui method yang disediakan objek itu sendiri. Method inilah satu-satunya "pintu" menuju data tersebut.
</div>

---

## Validasi Terjamin di Satu Tempat

Dengan encapsulation, setiap perubahan pada data sebuah objek wajib melewati method yang telah ditentukan. Method tersebut bisa memvalidasi nilai baru sebelum benar-benar disimpan, sehingga objek tidak pernah berada dalam kondisi yang tidak masuk akal.

<div class="term-box">
Prinsip ini sering disingkat sebagai <b>"sembunyikan data, ekspos perilaku"</b>: dunia luar tidak perlu tahu bagaimana data disimpan di dalam, cukup tahu method apa yang bisa dipanggil.
</div>

---

<!-- _class: divider -->

# Bagian 3
## Getter dan Setter

---

## Setter yang Memvalidasi

![h:340 Setter memeriksa nilai masukan sebelum menyimpannya ke field](../assets/illustrations/getter-setter-gate.svg)

<div class="term-box">
<b>Setter</b> adalah method yang mengubah nilai sebuah atribut privat. Karena berbentuk method biasa, setter bebas berisi logika pemeriksaan, misalnya membatasi nilai ke rentang yang aman, sebelum nilai tersebut benar-benar disimpan ke field.
</div>

---

## Getter: Membaca Data dengan Aman

**Getter** adalah method yang mengembalikan nilai sebuah atribut privat, tanpa mengizinkan kode luar mengubahnya secara langsung.

<div class="term-box">
Konvensi penamaan umum di Java: setter diberi nama <code>setNamaAtribut(...)</code>, getter diberi nama <code>getNamaAtribut()</code>. Kombinasi keduanya disebut pola <b>getter-setter</b>.
</div>

<div class="tip-box">
Sebuah atribut tidak wajib memiliki keduanya. Atribut yang tidak boleh diubah dari luar sama sekali cukup diberi getter, tanpa setter.
</div>

---

<!-- _class: divider -->

# Bagian 4
## Menerapkan Encapsulation ke Account

---

## Account Sebelum Encapsulation

Kelas `Account` pada Pertemuan 2 memiliki atribut publik `ownerName` dan `balance`, tanpa validasi apa pun pada `deposit()` maupun `withdraw()`.

<div class="warn-box">
Risiko yang sama seperti pada <code>Thermostat</code> berlaku di sini: saldo dapat diubah langsung ke nilai berapa pun, dan jumlah setoran atau penarikan negatif dapat diterima tanpa ditolak.
</div>

---

## Account Sesudah Encapsulation

![h:340 Diagram kelas Account setelah encapsulation diterapkan](../assets/uml/p03-account-encapsulated.png)

<div class="term-box">
Seluruh atribut kini bersifat <code>private</code>, diakses lewat <b>getter</b> (<code>getBalance()</code>, dst.). Method <code>deposit()</code> dan <code>withdraw()</code> mengembalikan nilai <code>boolean</code>: <code>true</code> bila berhasil, <code>false</code> bila nilai yang diberikan tidak valid.
</div>

---

## Constructor Lebih dari Satu: Overloading

Kelas `Account` yang baru memiliki dua constructor: satu menerima nomor rekening dan nama pemilik saja (saldo awal otomatis nol), satu lagi menerima saldo awal secara eksplisit.

<div class="term-box">
Java mengizinkan beberapa constructor (atau method) berbagi nama yang sama selama daftar parameternya berbeda. Kemampuan ini disebut <b>overloading</b>, dan dibahas lebih lengkap pada Pertemuan 7.
</div>

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Classes and Objects: Encapsulation

Oracle Java Tutorials: "Controlling Access to Members of a Class"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 3

---

## Diskusi

Bandingkan dua atribut `Account`: `accountNumber` dan `balance`. Untuk masing-masing, tentukan apakah sebaiknya punya setter atau tidak, lalu berikan satu alasan konkret untuk tiap keputusanmu (bukan sekadar "supaya aman", jelaskan skenario nyata yang menjadi risikonya bila setter tetap disediakan).
