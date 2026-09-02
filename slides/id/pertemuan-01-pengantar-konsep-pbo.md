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

Pertemuan 1: **Pengantar Konsep PBO**

Mengapa dunia perangkat lunak berpikir dalam objek

---

## Yang Akan Kamu Pelajari

- Perbedaan mendasar antara paradigma prosedural dan paradigma objek
- Empat pilar object-oriented programming (OOP): encapsulation, inheritance, polymorphism, abstraction
- Gambaran umum studi kasus yang akan dipakai sepanjang satu semester
- Bagaimana perkuliahan konsep (RTI253007) dan praktikum (RTI253008) saling melengkapi

<div class="tip-box">
Mata kuliah ini (RTI253007) berfokus pada konsep. Seluruh latihan pemrograman untuk materi hari ini tersedia di mata kuliah pendamping, <b>Praktikum Pemrograman Berbasis Objek (RTI253008)</b>, jobsheet Pertemuan 1.
</div>

---

<!-- _class: divider -->

# Bagian 1
## Dua Cara Berpikir tentang Program

---

## Ketika Program Semakin Besar

Program sederhana bisa ditulis sebagai kumpulan data dan fungsi yang terpisah. Selama program masih kecil, cara ini masih mudah diikuti. Namun ketika program bertambah besar, sebuah pertanyaan mulai muncul: fungsi mana saja yang boleh mengubah data yang mana, dan bagaimana memastikan semua bagian program tetap konsisten satu sama lain?

<div class="term-box">
<b>Paradigma pemrograman</b> adalah cara pandang atau gaya berpikir dalam menyusun program. Dua paradigma yang dibahas hari ini adalah <b>prosedural</b> (data dan fungsi terpisah) dan <b>berorientasi objek</b> (data dan fungsi dibundel menjadi satu kesatuan).
</div>

---

## Mengapa Ini Penting?

Bayangkan sebuah aplikasi perbankan dengan ratusan fungsi yang semuanya mengakses data saldo secara langsung. Ketika satu fungsi diubah, misalnya menambahkan aturan biaya administrasi baru, tidak ada satu titik pun yang menjamin seluruh fungsi lain yang juga mengakses saldo tetap berjalan benar. Bug semacam ini sulit dilacak karena penyebabnya bisa berada jauh dari tempat gejalanya muncul.

<div class="warn-box">
Semakin besar program, semakin banyak titik yang bisa mengubah data yang sama, dan semakin sulit memastikan seluruh titik tersebut tetap konsisten satu sama lain.
</div>

<div class="term-box">
Inilah salah satu alasan hampir seluruh sistem perangkat lunak skala besar di industri, mulai dari aplikasi perbankan, aplikasi mobile, hingga sistem enterprise, dibangun dengan bahasa berorientasi objek. Bukan sekadar preferensi gaya penulisan kode, melainkan cara mengendalikan kompleksitas yang terus tumbuh seiring ukuran program.
</div>

---

## Prosedural vs Berorientasi Objek

![h:300 Data bersama diakses bebas oleh banyak fungsi, dibandingkan dengan objek yang menjaga datanya sendiri](../assets/illustrations/paradigm-procedural-vs-oo.svg)

<div class="term-box">
Pada gaya <b>prosedural</b>, data disimpan terpisah dari fungsi yang mengolahnya, sehingga fungsi mana pun berpotensi mengubah data tersebut tanpa batasan yang jelas.
</div>

<div class="term-box">
Pada gaya <b>berorientasi objek</b>, data dan fungsi yang mengolahnya dibundel menjadi satu kesatuan bernama <b>objek</b>. Objek lain hanya dapat berinteraksi melalui method yang disediakan, bukan mengubah data secara langsung.
</div>

---

## Semua di Sekeliling Kita Adalah Objek

Contoh sederhana: sebuah lampu memiliki data (status menyala atau padam) dan perilaku (dinyalakan, dipadamkan). Mobil memiliki data (kecepatan, jumlah bahan bakar) dan perilaku (dipercepat, direm). Rekening bank memiliki data (saldo) dan perilaku (menyetor, menarik dana).

<div class="tip-box">
Pola yang sama selalu berulang: setiap benda punya <b>data yang melekat pada dirinya sendiri</b> dan <b>hal yang bisa dilakukannya dengan data tersebut</b>. Pemrograman berorientasi objek meniru pola ini langsung ke dalam kode.
</div>

<div class="warn-box">
Diskusi singkat: sebutkan satu benda lain di sekitarmu, lalu identifikasi data dan perilakunya.
</div>

---

<!-- _class: divider -->

# Bagian 2
## Empat Pilar OOP

---

## Empat Pilar OOP

![Encapsulation, inheritance, polymorphism, dan abstraction menopang satu atap bernama OOP](../assets/illustrations/oop-four-pillars.svg)

Keempat pilar ini akan dibahas satu per satu, satu pilar per beberapa pertemuan, sepanjang semester ini. Pertemuan hari ini hanya memperkenalkan namanya; penjelasan mendalam menyusul di pertemuan-pertemuan berikutnya.

---

## Sekilas Tentang Keempat Pilar

<div class="term-box">
<b>Encapsulation</b> (Pertemuan 3): data sebuah objek disembunyikan dan hanya bisa diakses melalui method yang disediakan objek itu sendiri.
</div>

<div class="term-box">
<b>Inheritance</b> (Pertemuan 6-7): sebuah kelas dapat mewarisi dan memperluas kelas lain, sehingga kode yang sudah ada bisa dipakai ulang.
</div>

<div class="term-box">
<b>Polymorphism</b> (Pertemuan 10): satu pesan yang sama dapat menghasilkan perilaku yang berbeda, tergantung objek mana yang menerimanya.
</div>

<div class="term-box">
<b>Abstraction</b> (Pertemuan 9): hanya detail yang penting bagi pemakai yang ditampilkan, detail pelaksanaannya disembunyikan.
</div>

---

<!-- _class: divider -->

# Bagian 3
## Studi Kasus Satu Semester: Bank Mini

---

## Satu Proyek, Tumbuh Sepanjang Semester

![Peta jalan Bank Mini: dari satu kelas Account hingga aplikasi GUI dengan database](../assets/illustrations/bank-mini-roadmap.svg)

<div class="term-box">
Sepanjang semester ini, jobsheet praktikum akan membangun satu aplikasi yang sama, <b>Bank Mini</b>, sedikit demi sedikit. Dimulai dari satu kelas <code>Account</code> yang sangat sederhana, aplikasi ini akan tumbuh hingga memiliki beberapa jenis rekening, riwayat transaksi, tampilan GUI, dan koneksi ke database.
</div>

---

## Mengapa Satu Studi Kasus?

<div class="tip-box">
Setiap konsep baru tetap diperkenalkan lebih dulu lewat contoh kecil yang berdiri sendiri (seperti lampu, termostat, atau hewan), supaya konsepnya jelas tanpa gangguan detail domain lain. Setelah itu, konsep yang sama diterapkan ke Bank Mini, sehingga kamu melihat bagaimana konsep-konsep itu benar-benar bekerja sama dalam satu aplikasi nyata, bukan potongan-potongan kode yang berdiri sendiri-sendiri.
</div>

Pada akhir semester, aplikasi Bank Mini yang kamu bangun akan mampu mencatat beberapa jenis rekening, memproses transaksi, menampilkan data lewat jendela aplikasi, dan menyimpan semuanya ke dalam database.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Pengantar dan Classes and Objects

Oracle Java Tutorials: "Object-Oriented Programming Concepts"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 1

---

## Diskusi

Pilih satu benda yang kamu pakai sehari-hari (misalnya sepeda, ponsel, atau termos). Sebutkan minimal tiga state (atribut) dan dua behavior (method) benda tersebut, lalu jelaskan argumenmu: apakah membungkus keduanya menjadi satu objek lebih masuk akal dibandingkan menyimpan datanya terpisah dari fungsi-fungsi yang mengolahnya? Berikan alasan konkret, bukan sekadar "karena OOP begitu".
