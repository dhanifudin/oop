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
- Konsep encapsulation dan information hiding, serta access modifier di Java
- Class invariant: kondisi yang harus selalu benar sepanjang umur sebuah objek
- Pola getter dan setter, termasuk atribut read-only dan setter yang memvalidasi nilai masukan
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

Bayangkan kelas `Thermostat` dengan atribut publik `temperature`. Perangkat ini hanya bekerja pada rentang 16 sampai 30 derajat. Karena `temperature` publik, kode lain bisa langsung mengubah nilainya tanpa melalui method apa pun. Apa yang mencegah kode itu mengisi `temperature` dengan angka jauh di luar rentang, misalnya `-50`?

<div class="warn-box">
Atribut publik berarti tidak ada satu titik pun yang menjamin data objek selalu valid.
</div>

---

## Ilustrasi: Akses Langsung yang Tidak Diperiksa

![h:340 Kode luar menulis langsung ke atribut publik, tanpa validasi apa pun](../assets/illustrations/direct-access-bug.svg)

<div class="warn-box">
Karena atribut publik, tidak ada kode yang memeriksa nilai baru sebelum disimpan. Nilai di luar rentang perangkat, misalnya <code>-50</code>, diterima begitu saja.
</div>

---

## Mengapa Ini Penting?

Bug seperti ini bukan sekadar risiko teoretis. Pada aplikasi nyata, atribut publik berarti setiap bagian program, termasuk kode tim lain, bisa langsung mengubah data itu. Saat bug muncul, programmer harus menelusuri seluruh basis kode untuk menemukan tempat yang mengubahnya, karena tidak ada satu titik yang bisa diperiksa.

<div class="term-box">
Inilah salah satu alasan encapsulation dianggap prinsip paling mendasar dalam OOP. Dengan menyembunyikan data di balik method, tim bisa mengubah cara data disimpan kapan pun, tanpa merusak kode lain, selama method publiknya tidak berubah.
</div>

---

<!-- _class: divider -->

# Bagian 2
## Encapsulation: Data di Balik Method

---

## Konsep Encapsulation

![h:340 Data privat sebuah objek hanya bisa dicapai lewat method, tidak ada jalan pintas](../assets/illustrations/capsule-shield.svg)

<div class="term-box">
<b>Encapsulation</b> berarti data sebuah objek disembunyikan (dibuat <code>private</code>), hanya bisa diakses lewat method milik objek itu sendiri. Method inilah satu-satunya "pintu" menuju data tersebut.
</div>

---

## Encapsulation dan Information Hiding

Kedua istilah ini sering dianggap sama, padahal berbeda. **Encapsulation** adalah caranya: membungkus data bersama method dalam satu kelas. **Information hiding** adalah tujuannya: menyembunyikan detail penyimpanan data, sehingga kode luar hanya bergantung pada method publik, bukan pada isi di dalamnya.

<div class="term-box">
Sebuah kelas bisa membungkus data dan method (encapsulation) tanpa benar-benar menyembunyikan apa pun, misalnya bila atributnya tetap <code>public</code>. Access modifier <code>private</code>-lah yang membuat information hiding benar-benar tercapai.
</div>

---

## Access Modifier di Java

| Modifier | Kelas sendiri | Kelas lain di package sama | Subclass beda package | Kelas lain beda package |
|---|:---:|:---:|:---:|:---:|
| `private` | ya | tidak | tidak | tidak |
| (tanpa modifier) | ya | ya | tidak | tidak |
| `protected` | ya | ya | ya | tidak |
| `public` | ya | ya | ya | ya |

<div class="tip-box">
Pertemuan ini baru membutuhkan <code>private</code> dan <code>public</code>. <code>protected</code> muncul lagi saat inheritance dibahas di Pertemuan 6; akses tanpa modifier (package-private) jarang dipakai eksplisit di Bank Mini.
</div>

<div class="term-box">
Aturan praktis: pilih access modifier paling ketat yang kelas masih bisa bekerja dengannya. Atribut hampir selalu <code>private</code>; method dibuka (<code>public</code>) hanya yang perlu dipanggil dari luar.
</div>

---

## Validasi Terjamin di Satu Tempat

Dengan encapsulation, setiap perubahan data wajib melewati method yang sudah ditentukan. Method itu bisa memvalidasi nilai baru sebelum disimpan, sehingga objek tidak pernah berada dalam kondisi yang tidak masuk akal.

<div class="term-box">
Prinsip ini sering disingkat sebagai <b>"sembunyikan data, ekspos perilaku"</b>: dunia luar tidak perlu tahu bagaimana data disimpan di dalam, cukup tahu method apa yang bisa dipanggil.
</div>

---

## Class Invariant

**Class invariant** adalah kondisi yang harus selalu benar untuk setiap objek, sepanjang umur objek itu. Pada `Thermostat`, invariant-nya adalah "`temperature` selalu di rentang 16 sampai 30". Kondisi ini harus tetap benar kapan pun objek diperiksa, apa pun method yang baru dipanggil.

<div class="term-box">
Encapsulation membuat invariant benar-benar bisa ditegakkan. Satu-satunya jalan mengubah data adalah lewat method milik kelas, jadi method itu bisa memeriksa invariant lebih dulu. Tanpa encapsulation, atribut publik membuat invariant hanya jadi harapan, bukan jaminan, karena kode mana pun bisa melanggarnya kapan saja.
</div>

---

<!-- _class: divider -->

# Bagian 3
## Getter dan Setter

---

## Setter yang Memvalidasi

![h:340 Setter memeriksa nilai masukan sebelum menyimpannya ke field](../assets/illustrations/getter-setter-gate.svg)

<div class="term-box">
<b>Setter</b> adalah method yang mengubah nilai sebuah atribut privat. Karena berbentuk method biasa, setter bisa memeriksa nilai dulu, misalnya membatasi ke rentang yang aman, sebelum menyimpannya ke field.
</div>

---

## Getter: Membaca Data dengan Aman

**Getter** adalah method yang mengembalikan nilai sebuah atribut privat, tanpa mengizinkan kode luar mengubahnya secara langsung.

<div class="term-box">
Konvensi penamaan umum di Java: setter diberi nama <code>setNamaAtribut(...)</code>, getter diberi nama <code>getNamaAtribut()</code>. Kombinasi keduanya disebut pola <b>getter-setter</b>.
</div>

---

## Atribut Read-Only

Sebuah atribut tidak wajib punya getter dan setter sekaligus. Atribut yang nilainya ditetapkan sekali saat objek dibuat, dan tidak boleh berubah lagi, cukup diberi getter saja. Pola ini disebut **atribut read-only**: constructor menetapkan nilainya di awal, dan tanpa setter, tidak ada method lain yang bisa mengubahnya.

<div class="term-box">
Atribut read-only adalah bentuk encapsulation paling ketat: bukan cuma validasi yang terjamin, tapi perubahannya sendiri tidak mungkin terjadi lagi. Contoh umum: nomor identitas seperti nomor rekening atau NIM, yang secara alami tidak pernah berubah.
</div>

---

<!-- _class: divider -->

# Bagian 4
## Menerapkan Encapsulation ke Account

---

## Account Sebelum Encapsulation

Kelas `Account` pada Pertemuan 2 memiliki atribut publik `ownerName` dan `balance`, tanpa validasi apa pun pada `deposit()` maupun `withdraw()`.

<div class="warn-box">
Risiko yang sama seperti <code>Thermostat</code> berlaku di sini: saldo bisa diubah langsung ke nilai berapa pun, dan setoran atau penarikan negatif bisa diterima tanpa ditolak.
</div>

---

## Account Sesudah Encapsulation

![h:340 Diagram kelas Account setelah encapsulation diterapkan](../assets/uml/p03-account-encapsulated.png)

<div class="term-box">
Seluruh atribut kini bersifat <code>private</code>, diakses lewat <b>getter</b> (<code>getBalance()</code>, dst.). Method <code>deposit()</code> dan <code>withdraw()</code> mengembalikan nilai <code>boolean</code>: <code>true</code> bila berhasil, <code>false</code> bila nilai yang diberikan tidak valid.
</div>

---

## Constructor Menetapkan Data Sekali di Awal

Constructor `Account(accountNumber, ownerName, balance)` mewajibkan ketiga nilai ini diberikan sejak objek dibuat. Atribut `accountNumber` sengaja hanya diberi getter, tanpa setter. Begitu ditetapkan lewat constructor, nomor rekening sebuah `Account` tidak pernah berubah lagi.

<div class="term-box">
Inilah pola atribut read-only dari Bagian 3, diterapkan secara nyata. <code>accountNumber</code> adalah identitas sebuah rekening, sama seperti NIM bagi mahasiswa, jadi tidak masuk akal bila ada method yang bisa mengubahnya.
</div>

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Classes and Objects: Encapsulation

Oracle Java Tutorials: "Controlling Access to Members of a Class"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 3

---

## Tugas Mandiri: Mencari Encapsulation di Dunia Nyata

Cari satu sistem nyata di luar Bank Mini yang kamu kenal atau gunakan sehari-hari, bebas memilih domain apa pun. Identifikasi bagaimana sistem itu menerapkan encapsulation: data apa yang disembunyikan, invariant apa yang dijaga, dan lewat apa data itu bisa diakses.

<div class="tip-box">
Tuliskan temuanmu secara singkat (nama sistem, data yang disembunyikan, invariant yang dijaga, cara mengaksesnya) dan siap mendiskusikannya pada awal Pertemuan 4.
</div>
