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

Pertemuan 2: **Kelas dan Objek**

Fondasi pemrograman berorientasi objek

---

## Yang Akan Dipelajari

- Perbedaan **kelas** dan **objek**
- Anatomi kelas: atribut, method, konstruktor
- Apa yang sebenarnya terjadi di memori pada saat objek dibuat
- Referensi dibandingkan objek, serta risiko referensi kosong
- Cara membaca diagram kelas UML sederhana

<div class="term-box">
<b>Prasyarat (Pertemuan 1):</b> mahasiswa telah mengenal perbedaan antara paradigma prosedural (data dan fungsi terpisah) dengan paradigma objek (data dan fungsi digabungkan menjadi satu unit). Pertemuan ini membahas konsep unit tersebut secara lebih mendalam.
</div>

<div class="tip-box">
Mata kuliah ini (RTI253007) berfokus pada konsep. Seluruh latihan pemrograman untuk materi hari ini disediakan pada mata kuliah pendamping, <b>Praktikum Pemrograman Berbasis Objek (RTI253008)</b>, jobsheet Pertemuan 2.
</div>

---

<!-- _class: divider -->

# Bagian 1
## Dari Dunia Nyata ke Kelas

---

## Analogi Cetakan Kue

![h:340 Satu kelas menghasilkan banyak objek, masing-masing dengan datanya sendiri](../assets/illustrations/class-object-cutter.svg)

<div class="term-box">
<b>Kelas</b> merupakan cetakan atau blueprint, sedangkan <b>objek</b> adalah wujud konkret yang dihasilkan dari cetakan tersebut. Satu kelas dapat menghasilkan banyak objek, dan setiap objek memiliki datanya masing-masing.
</div>

---

## Objek Sebagai State dan Behavior

![h:300 Kontras antara data dan perilaku yang digabungkan menjadi satu (berorientasi objek) dibandingkan tersebar (prosedural)](../assets/illustrations/state-behavior-bundle.svg)

Setiap objek menggabungkan dua hal: **state** (data yang dimiliki objek itu sendiri) dan **behavior** (perilaku yang dapat dilakukan objek terhadap datanya). Hal ini yang membedakannya dari pendekatan prosedural, di mana data dan fungsi yang mengolahnya biasanya tersebar di lokasi yang berbeda-beda, sehingga kesesuaiannya harus dijaga secara manual.

---

## Mengapa Ini Penting?

Kelas dan objek bukan sekadar cara mengorganisasi kode. Keduanya adalah fondasi yang menopang seluruh konsep OOP lain yang akan dipelajari sepanjang semester ini: encapsulation, inheritance, dan polymorphism semuanya beroperasi pada satu-satuan yang sama, yaitu objek. Tanpa satu-satuan ini, tidak ada "sesuatu" yang sifatnya bisa diwarisi, datanya bisa disembunyikan, atau perilakunya bisa diperlakukan berbeda-beda tergantung jenisnya.

<div class="term-box">
Membundel data dan perilaku ke dalam satu objek juga membuat setiap bagian program bisa diuji dan dipahami secara terpisah, tanpa harus menelusuri seluruh basis kode untuk mengetahui bagaimana suatu data digunakan. Inilah salah satu alasan aplikasi besar yang tersusun dari banyak objek kecil jauh lebih mudah dirawat dibandingkan satu program raksasa yang datanya saling terkait tanpa batas yang jelas.
</div>

---

## Definisi Kunci

<div class="term-box">
<b>Kelas:</b> cetakan atau template yang mendefinisikan atribut (data) dan method (perilaku) yang akan dimiliki oleh objek-objeknya.
</div>

<div class="term-box">
<b>Objek:</b> wujud konkret yang dibuat dari sebuah kelas, dengan datanya sendiri.
</div>

<div class="term-box">
<b>Instansiasi:</b> proses pembuatan objek baru dari sebuah kelas.
</div>

---

<!-- _class: divider -->

# Bagian 2
## Anatomi Sebuah Kelas

---

## Atribut dan Method

Sebuah kelas mendeskripsikan dua hal untuk setiap objek yang akan dibuat darinya:

- **Atribut**: data yang dimiliki objek itu sendiri, misalnya ukuran sebuah persegi panjang.
- **Method**: perilaku yang dapat dilakukan objek dengan datanya, misalnya menghitung luasnya sendiri.

<div class="warn-box">
Apabila sebuah objek dibuat namun atributnya belum diisi, objek tersebut masih berada dalam kondisi "setengah jadi". Bagian berikutnya membahas bagaimana konstruktor menutup celah ini.
</div>

---

## Konstruktor: Menutup Jeda "Setengah Jadi"

![h:400 Objek sebelum dan sesudah konstruktor mengisi atributnya](../assets/illustrations/constructor-before-after.svg)

**Konstruktor** adalah bagian kelas yang dijalankan secara otomatis pada saat objek baru dibuat. Tugasnya adalah memastikan seluruh atribut langsung terisi secara lengkap, sehingga objek tidak pernah berada dalam kondisi "setengah jadi".

---

## Mengapa Diperlukan `this`?

![h:360 this merujuk pada objek itu sendiri, berbeda dengan parameter yang berasal dari luar](../assets/illustrations/this-self-reference.svg)

Parameter konstruktor sering diberi nama yang sama persis dengan atributnya agar maksudnya jelas. Agar Java dapat membedakan keduanya, tersedia kata kunci `this` yang merujuk pada objek yang sedang dibuat atau digunakan saat ini, berbeda dengan parameter yang hanya berupa nilai yang diterima dari luar.

---

## Konstruktor Default dan Konstruktor Berparameter

<div class="cols">
<div>

**Tanpa konstruktor dituliskan**

Java secara otomatis menyediakan konstruktor default tanpa parameter, dengan atribut yang diberi nilai kosong.

</div>
<div>

**Konstruktor berparameter**

Setelah satu konstruktor dituliskan secara eksplisit, konstruktor default tersebut otomatis tidak lagi tersedia.

</div>
</div>

---

## Parameter dan Nilai Kembali

![h:300 Method sebagai mesin kecil: menerima input dan mengembalikan hasil](../assets/illustrations/function-io.svg)

Sebuah method dapat menerima input (**parameter**) dan mengembalikan hasil (**return value**), serupa dengan mesin kecil yang mengolah masukan menjadi keluaran.

<div class="tip-box">
Java juga mengizinkan beberapa method memiliki nama yang sama dengan parameter yang berbeda (overloading). Materi ini dibahas secara lengkap pada Pertemuan 7.
</div>

---

<!-- _class: divider -->

# Bagian 3
## Objek di Memori

---

## Tahapan Pembuatan Objek

![Empat tahap yang terjadi pada saat sebuah objek baru dibuat](../assets/illustrations/object-creation-flow.svg)

---

## Stack dan Heap: Variabel Bukan Objeknya Sendiri

![h:400 Variabel di stack menunjuk pada objek di heap](../assets/illustrations/stack-heap-single.svg)

Variabel yang berada di **stack** hanya menyimpan alamat (referensi), bukan objeknya secara langsung. Objek yang sesungguhnya, lengkap dengan seluruh datanya, disimpan secara terpisah di **heap**.

---

## Menyalin Variabel Berbeda dengan Menyalin Objek

Karena variabel hanya menyimpan alamat, dua variabel dapat menunjuk ke objek yang persis sama. Apabila hal ini terjadi, perubahan data melalui salah satu variabel akan otomatis terlihat melalui variabel lainnya, karena keduanya menunjuk ke objek yang sama persis di heap.

---

## Ilustrasi: Dua Variabel, Satu Objek

![h:420 Dua variabel di stack menunjuk pada satu objek yang sama di heap](../assets/illustrations/stack-heap-alias.svg)

Kondisi ini disebut **aliasing**, yaitu dua atau lebih variabel yang menunjuk ke objek yang persis sama di heap.

---

## Referensi yang Belum Menunjuk ke Objek Mana Pun

![h:280 Referensi null menunjuk ke ruang kosong](../assets/illustrations/null-reference.svg)

<div class="warn-box">
Referensi yang belum menunjuk ke objek mana pun disebut bernilai kosong (null). Apabila method dipanggil pada referensi yang masih kosong, program akan langsung berhenti dengan galat. Solusinya selalu sama, yaitu memastikan objek telah benar-benar dibuat sebelum method-nya digunakan.
</div>

---

## Banyak Objek dari Satu Kelas

![h:320 Satu kelas menghasilkan beberapa objek independen dalam sebuah array](../assets/illustrations/multiple-objects-array.svg)

Satu kelas dapat menghasilkan banyak objek sekaligus, dan seluruh objek tersebut dapat ditampung dalam satu array. Setiap objek tetap independen, memiliki ukuran yang berbeda-beda, dan datanya tidak saling memengaruhi.

<p class="footnote">Objek yang tidak lagi ditunjuk oleh referensi mana pun akan otomatis dibersihkan dari heap oleh garbage collector.</p>

---

<!-- _class: divider -->

# Bagian 4
## Membaca Diagram Kelas UML

---

## Anatomi Kotak Kelas UML

![h:320 Diagram kelas Rectangle](../assets/uml/p02-rectangle.png)

<div class="term-box">
Tanda <b>-</b> menunjukkan atribut atau method bersifat privat (hanya dapat diakses dari dalam kelas itu sendiri), sedangkan tanda <b>+</b> menunjukkan sifat publik (dapat diakses dari luar kelas). Konsep enkapsulasi dibahas secara lengkap pada Pertemuan 3.
</div>

---

## Latihan Membaca: Kelas Account

![h:300 Diagram kelas Account](../assets/uml/p02-account.png)

**Latihan:** berapa jumlah atribut pada kelas ini? Apakah atribut-atributnya bersifat privat atau publik? Method apa saja yang disediakan, dan input apa yang dibutuhkan oleh masing-masing method?

<div class="tip-box">
<code>Account</code> adalah kelas pertama dari studi kasus <b>Bank Mini</b> yang akan dibangun sepanjang semester ini. Penerjemahan diagram ini menjadi kode Java dilakukan sebagai latihan praktik pada jobsheet Praktikum Pertemuan 2 (RTI253008).
</div>

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program* - Bab Classes and Objects

Oracle Java Tutorials: "Classes and Objects"

Latihan praktik untuk materi ini tersedia pada jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 2

---

## Diskusi

Pilih satu kelas dari dunia nyata yang belum dibahas di kelas ini (bukan `Rectangle`, `Student`, atau `Circle`). Sebutkan minimal tiga atribut dan dua method yang menurutmu wajar dimiliki kelas tersebut, lalu sketsakan diagram UML sederhananya (nama kelas, atribut, method, tanpa perlu digambar rapi, cukup ditulis di kertas).
