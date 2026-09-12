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

Pertemuan 1: **Pengantar Konsep PBO**

Mengapa dunia perangkat lunak berpikir dalam objek

---

## Yang Akan Kamu Pelajari

- Perbedaan mendasar antara paradigma prosedural dan paradigma objek
- Bagaimana benda di dunia nyata punya data dan perilaku sekaligus
- Empat pilar object-oriented programming (OOP): encapsulation, inheritance, polymorphism, abstraction
- Gambaran umum studi kasus satu semester, dan bagaimana perkuliahan konsep (RTI253007) dan praktikum (RTI253008) saling melengkapi

<div class="tip-box">
Mata kuliah ini (RTI253007) berfokus pada konsep. Seluruh latihan pemrograman untuk materi hari ini tersedia di mata kuliah pendamping, <b>Praktikum Pemrograman Berbasis Objek (RTI253008)</b>, jobsheet Pertemuan 1.
</div>

---

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Dari pemrograman prosedural ke berorientasi objek
- **Sesi 2 (50')**: Objek di dunia nyata, data dan perilaku
- **Sesi 3 (50')**: Empat pilar OOP
- **Sesi 4 (50')**: Studi kasus Bank Mini dan struktur perkuliahan

---

<!-- _class: divider -->

# Bagian 1
## Dari Prosedural ke Berorientasi Objek

Sesi 1 dari 4

---

## Ketika Program Semakin Besar

Program sederhana bisa ditulis sebagai kumpulan data dan fungsi yang terpisah. Selama program masih kecil, cara ini masih mudah diikuti. Namun ketika program bertambah besar, sebuah pertanyaan mulai muncul: fungsi mana saja yang boleh mengubah data yang mana, dan bagaimana memastikan semua bagian program tetap konsisten satu sama lain?

<div class="term-box">
<b>Paradigma pemrograman</b> adalah cara pandang atau gaya berpikir dalam menyusun program. Dua paradigma yang dibahas hari ini adalah <b>prosedural</b> (data dan fungsi terpisah) dan <b>berorientasi objek</b> (data dan fungsi dibundel menjadi satu kesatuan).
</div>

---

## Mengapa Ini Penting?

Bayangkan sebuah aplikasi perbankan dengan ratusan fungsi yang semuanya bisa mengubah data saldo secara langsung. Kalau satu fungsi diubah, misalnya menambah aturan biaya administrasi baru, tidak ada yang menjamin fungsi-fungsi lain yang juga memakai saldo tetap berjalan benar. Bug seperti ini sulit dilacak, sebab penyebabnya bisa jauh dari tempat gejalanya muncul.

<div class="term-box">
Inilah salah satu alasan hampir semua sistem skala besar di industri, dari aplikasi perbankan sampai aplikasi mobile, dibangun dengan gaya berorientasi objek. Bukan sekadar gaya penulisan kode, tapi cara mengendalikan kompleksitas yang terus bertambah seiring program membesar.
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

## Contoh Konkret: Menyalakan Lampu

Gaya prosedural: fungsi bebas "nyalakan lampu" mengubah status lampu langsung dari luar, siapa pun boleh memanggilnya kapan saja.

Gaya berorientasi objek: kamu memanggil perintah "nyalakan" milik lampu itu sendiri; lampu itu sendiri yang mengubah statusnya, bukan kode luar yang mengubahnya langsung.

<div class="tip-box">
Bedanya bukan pada APA yang terjadi (lampu tetap menyala), melainkan SIAPA yang bertanggung jawab mengubah datanya.
</div>

---

## Tabel Perbandingan Singkat

| | Prosedural | Berorientasi Objek |
|---|---|---|
| Data disimpan di | variabel terpisah, bebas diakses | dalam objek, dilindungi objeknya |
| Yang mengubah data | fungsi mana pun, dari mana pun | objeknya sendiri, lewat method |
| Risiko saat program membesar | sulit melacak siapa mengubah apa | perubahan data lebih terkendali |

---

## Kesalahan Umum: Mengira OOP Hanya Soal Gaya Penulisan

<div class="warn-box">
<b>Salah:</b> mengira berorientasi objek hanya berarti "menyebut-nyebut kata objek" di kode, sementara data tetap bebas diakses dan diubah dari mana saja.
</div>

**Benar:** berorientasi objek berarti data benar-benar dibundel bersama fungsi yang mengolahnya, dan hanya bisa diubah lewat fungsi tersebut. Kalau data masih bebas diubah dari luar, itu tetap gaya prosedural, walau dibungkus istilah objek.

---

## Latihan

Sebuah program pendataan mahasiswa menyimpan nama, NIM, dan IPK sebagai variabel-variabel terpisah. Program itu juga punya beberapa fungsi bebas seperti "cetak data" dan "hitung predikat" yang bisa dipanggil dan mengubah variabel-variabel itu dari mana saja.

Apakah program ini bergaya prosedural atau berorientasi objek? Jelaskan alasanmu.

---

## Jawaban Latihan

**Prosedural.** Data (nama, NIM, IPK) disimpan terpisah dari fungsi yang mengolahnya, dan fungsi mana pun bebas mengaksesnya tanpa batasan. Ciri gaya berorientasi objek adalah data dan fungsi dibundel jadi satu kesatuan, dengan akses yang dikendalikan lewat method, bukan variabel bebas yang bisa diubah dari mana saja.

---

## Rangkuman Bagian 1

- Program prosedural memisahkan data dan fungsi; program berorientasi objek membundel keduanya jadi satu kesatuan bernama objek.
- Semakin besar program, semakin penting mengendalikan siapa yang boleh mengubah data yang mana.
- Sekadar memakai istilah "objek" tidak cukup; data harus benar-benar dilindungi dan hanya diubah lewat method.

Selanjutnya: Bagian 2 melihat lebih dekat bagaimana objek meniru cara benda-benda di dunia nyata bekerja.

---

<!-- _class: divider -->

# Bagian 2
## Objek di Dunia Nyata: Data dan Perilaku

Sesi 2 dari 4

---

## Semua di Sekeliling Kita Adalah Objek

Contoh sederhana: sebuah lampu memiliki data (status menyala atau padam) dan perilaku (dinyalakan, dipadamkan). Mobil memiliki data (kecepatan, jumlah bahan bakar) dan perilaku (dipercepat, direm). Rekening bank memiliki data (saldo) dan perilaku (menyetor, menarik dana).

<div class="tip-box">
Pola yang sama selalu berulang: setiap benda punya <b>data yang melekat pada dirinya sendiri</b> dan <b>hal yang bisa dilakukannya dengan data tersebut</b>. Pemrograman berorientasi objek meniru pola ini langsung ke dalam kode.
</div>

---

## Contoh Lain, Dirangkum dalam Tabel

| Benda | State (data) | Behavior (perilaku) |
|---|---|---|
| Lampu | menyala atau padam | dinyalakan, dipadamkan |
| Mobil | kecepatan, jumlah bahan bakar | dipercepat, direm |
| Rekening bank | saldo | menyetor, menarik dana |

<div class="term-box">
Istilah <b>state</b> untuk data yang dimiliki objek, dan <b>behavior</b> untuk hal yang bisa dilakukan objek, akan sering dipakai sepanjang mata kuliah ini.
</div>

---

## Mengapa Ini Penting?

Bayangkan kamu menjelaskan desain sebuah aplikasi ke rekan satu tim, tapi data dan perilakunya dibicarakan sebagai dua hal terpisah yang tidak jelas keterkaitannya. Diskusi jadi berlarut-larut karena tidak ada satu "unit" yang jelas untuk dibicarakan bersama.

<div class="term-box">
Dengan membundel data dan perilaku jadi satu objek, tim jadi punya bahasa yang sama untuk membicarakan desain sistem: cukup sebut nama objeknya, semua orang tahu data dan perilaku apa yang dimaksud. Ini yang membuat sistem besar, yang dikerjakan banyak orang, tetap bisa dipahami bersama.
</div>

---

## Kesalahan Umum: Mengira Objek Harus Benda Fisik

<div class="warn-box">
<b>Salah:</b> mengira "objek" dalam pemrograman harus berupa benda fisik seperti lampu atau mobil.
</div>

**Benar:** objek juga bisa mewakili konsep yang tidak berwujud fisik, misalnya sebuah transaksi, sebuah pesanan, atau sebuah jadwal. Selama sesuatu itu punya data dan perilaku yang melekat padanya, ia bisa dimodelkan sebagai objek.

---

## Latihan

Pilih satu benda yang kamu pakai sehari-hari (misalnya sepeda, ponsel, atau termos). Sebutkan minimal tiga state (data) dan dua behavior (perilaku) benda tersebut.

---

## Jawaban Latihan

Tidak ada satu jawaban benar, sebab bendanya kamu pilih sendiri. Contoh untuk sepeda: state-nya bisa berupa kecepatan, tekanan ban, dan posisi gigi; behavior-nya bisa berupa dikayuh dan direm. Periksa jawabanmu: apakah setiap state benar-benar data yang melekat pada benda itu, dan setiap behavior benar-benar sesuatu yang bisa dilakukan benda itu sendiri?

---

## Rangkuman Bagian 2

- Setiap benda, fisik maupun konsep abstrak, punya state (data) dan behavior (perilaku) yang melekat padanya.
- Objek dalam pemrograman meniru pola ini: membundel state dan behavior jadi satu kesatuan.
- Membundel state dan behavior memberi tim satu bahasa yang sama untuk membicarakan desain sistem.

Selanjutnya: Bagian 3 mengenalkan empat pilar OOP, empat ide yang membuat gaya berorientasi objek benar-benar bekerja.

---

<!-- _class: divider -->

# Bagian 3
## Empat Pilar OOP

Sesi 3 dari 4

---

## Empat Pilar OOP

![Encapsulation, inheritance, polymorphism, dan abstraction menopang satu atap bernama OOP](../assets/illustrations/oop-four-pillars.svg)

Keempat pilar ini akan dibahas satu per satu, satu pilar per beberapa pertemuan, sepanjang semester ini. Pertemuan hari ini hanya memperkenalkan namanya secara singkat; penjelasan mendalam menyusul di pertemuan-pertemuan berikutnya.

---

## Encapsulation: Melindungi Data

![h:280 Data privat sebuah objek hanya bisa dicapai lewat method, tidak ada jalan pintas](../assets/illustrations/capsule-shield.svg)

<div class="term-box">
<b>Encapsulation</b> (dibahas mendalam Pertemuan 3): data sebuah objek disembunyikan, dan hanya bisa diakses lewat method yang disediakan objek itu sendiri. Ibarat brankas, isinya hanya bisa diambil lewat pintu resminya, bukan dibongkar paksa dari luar.
</div>

---

## Inheritance: Mewarisi dan Memperluas

![Dog dan Cat sama-sama mewarisi Animal](../assets/illustrations/inheritance-tree.svg)

<div class="term-box">
<b>Inheritance</b> (dibahas mendalam Pertemuan 6-7): sebuah kelas dapat mewarisi dan memperluas kelas lain, sehingga kode yang sudah ada bisa dipakai ulang. Ibarat resep masakan turunan, resep baru cukup menambahkan bahan tambahan tanpa menulis ulang seluruh resep dasarnya.
</div>

---

## Polymorphism: Satu Pesan, Banyak Respons

![Satu pemanggilan area() menghasilkan hasil berbeda tergantung objeknya Circle atau Square](../assets/illustrations/polymorphic-dispatch.svg)

<div class="term-box">
<b>Polymorphism</b> (dibahas mendalam Pertemuan 10): satu pesan yang sama dapat menghasilkan perilaku berbeda, tergantung objek mana yang menerimanya. Ibarat perintah "bersuara" ke seekor kucing dan seekor anjing, hasilnya berbeda meski perintahnya sama.
</div>

---

## Abstraction: Menyembunyikan Detail

<div class="term-box">
<b>Abstraction</b> (dibahas mendalam Pertemuan 9): hanya detail yang penting bagi pemakai yang ditampilkan, detail pelaksanaannya disembunyikan. Ibarat remote televisi, kamu cukup menekan tombol daya, tanpa perlu tahu rangkaian elektronik di baliknya.
</div>

---

## Keempatnya Bekerja Bersama, Bukan Sendiri-sendiri

Dalam satu aplikasi nyata, keempat pilar ini biasanya muncul sekaligus, saling melengkapi. Sebuah aplikasi kasir, misalnya, bisa saja melindungi data harga lewat encapsulation, mewariskan sifat umum antar jenis produk lewat inheritance, menghitung diskon berbeda-beda lewat polymorphism, dan menyembunyikan rumus pajak yang rumit lewat abstraction, semuanya dalam satu desain yang sama.

---

## Kesalahan Umum: Mengira Keempat Pilar Berdiri Sendiri-sendiri

<div class="warn-box">
<b>Salah:</b> mempelajari keempat pilar sebagai empat fitur terpisah yang tidak berkaitan, lalu bingung kapan harus memakai yang mana.
</div>

**Benar:** keempat pilar saling melengkapi dalam satu desain. Semakin banyak latihan membangun aplikasi nyata, semakin terlihat bagaimana keempatnya muncul bersamaan, bukan dipilih satu-satu secara terpisah.

---

## Latihan

Cocokkan tiap pernyataan berikut dengan salah satu dari empat pilar OOP:

1. Sebuah aplikasi menyembunyikan rumus pajak yang rumit di balik satu perintah "hitung total", pemakainya tidak perlu tahu detail rumusnya.
2. Sebuah rekening bank tidak mengizinkan saldonya diubah langsung dari luar; satu-satunya cara mengubah saldo adalah lewat operasi setor dan tarik yang memvalidasi jumlahnya.
3. Mobil listrik dan mobil bensin sama-sama mewarisi sifat umum "mobil", sehingga keduanya otomatis bisa "jalan" dan "berhenti" tanpa didefinisikan ulang dari nol.
4. Perintah "hitung ongkos kirim" menghasilkan angka berbeda tergantung jenis pengirimannya, reguler atau ekspres, meski perintahnya sama persis.

---

## Jawaban Latihan

1. **Abstraction**, sebab yang ditonjolkan hanya hasil akhirnya, detail rumusnya disembunyikan.
2. **Encapsulation**, sebab datanya (saldo) dilindungi, hanya bisa diubah lewat operasi resmi yang memvalidasi.
3. **Inheritance**, sebab sifat umum diwariskan, tidak perlu ditulis ulang di tiap jenis.
4. **Polymorphism**, sebab satu perintah yang sama menghasilkan perilaku berbeda tergantung jenis objeknya.

---

## Rangkuman Bagian 3

- Empat pilar OOP: encapsulation (melindungi data), inheritance (mewarisi), polymorphism (satu pesan, banyak respons), abstraction (menyembunyikan detail).
- Keempatnya biasanya muncul bersamaan dalam satu aplikasi nyata, saling melengkapi, bukan dipilih satu-satu.
- Hari ini baru perkenalan nama dan analogi; masing-masing dibahas mendalam di pertemuan-pertemuan mendatang.

Selanjutnya: Bagian 4 memperkenalkan proyek yang akan kamu bangun sepanjang semester untuk mempraktikkan semua ini.

---

<!-- _class: divider -->

# Bagian 4
## Bank Mini dan Struktur Perkuliahan

Sesi 4 dari 4

---

## Satu Proyek, Tumbuh Sepanjang Semester

![Peta jalan Bank Mini: dari satu kelas Account hingga aplikasi GUI dengan database](../assets/illustrations/bank-mini-roadmap.svg)

<div class="term-box">
Sepanjang semester ini, jobsheet praktikum akan membangun satu aplikasi yang sama, <b>Bank Mini</b>, sedikit demi sedikit. Dimulai dari satu kelas <code>Account</code> yang sangat sederhana, aplikasi ini akan tumbuh hingga memiliki beberapa jenis rekening, riwayat transaksi, tampilan GUI, dan koneksi ke database.
</div>

---

## Mengapa Satu Studi Kasus?

Kalau tiap pertemuan memakai contoh yang berbeda-beda, kamu tidak pernah melihat bagaimana konsep-konsep itu bekerja sama dalam satu aplikasi nyata, hanya potongan kode yang berdiri sendiri-sendiri.

<div class="tip-box">
Setiap konsep baru tetap diperkenalkan lebih dulu lewat contoh kecil yang berdiri sendiri, seperti pada Bagian 1 sampai 3 tadi, supaya konsepnya jelas tanpa gangguan detail lain. Setelah itu, konsep yang sama diterapkan ke Bank Mini, satu aplikasi yang terus tumbuh dari pertemuan ke pertemuan.
</div>

---

## Bagaimana Konsep dan Praktikum Saling Melengkapi

Mata kuliah ini terbagi dua: **RTI253007** (kelas konsep, tempat kamu sedang duduk sekarang) membahas ide dan alasan di baliknya lewat slide seperti ini. **RTI253008** (praktikum) adalah tempat kamu benar-benar menuliskan kodenya sendiri, lewat jobsheet, biasanya membangun konsep yang sama ke proyek Bank Mini.

<div class="term-box">
Jobsheet Praktikum Pertemuan 1 memulai proyek Bank Mini dari nol: menyiapkan lingkungan kerja, lalu menulis program pertama. Konsep hari ini menjadi dasar untuk memahami APA yang sedang kamu bangun di sana, bukan sekadar mengikuti langkah demi langkah.
</div>

---

## Latihan

Pada akhir semester, aplikasi Bank Mini yang kamu bangun akan mampu mencatat beberapa jenis rekening, memproses transaksi, menampilkan data lewat jendela aplikasi, dan menyimpan semuanya ke database.

Menurutmu, kelas pertama apa yang paling masuk akal dibangun lebih dulu di Pertemuan 2, sebelum semua kemampuan itu ada? Jelaskan alasanmu.

---

## Jawaban Latihan

Tidak ada jawaban tunggal yang mutlak, tapi arah yang diharapkan: sebuah kelas rekening sederhana, yang nanti kamu kenal dengan nama Account, sebab hampir semua kemampuan lain (jenis rekening, transaksi, tampilan, database) pada akhirnya berputar di sekitar data satu rekening. Membangun bagian paling inti lebih dulu, baru menambah kemampuan di sekelilingnya, adalah pola yang akan berulang sepanjang semester ini.

---

## Rangkuman Pertemuan 1

- Berorientasi objek membundel data dan fungsi jadi satu kesatuan, berbeda dari gaya prosedural yang memisahkan keduanya.
- Setiap benda, fisik maupun konsep abstrak, punya state (data) dan behavior (perilaku) yang melekat padanya.
- Empat pilar OOP (encapsulation, inheritance, polymorphism, abstraction) biasanya muncul bersamaan dalam satu desain, dibahas mendalam satu per satu di pertemuan-pertemuan mendatang.
- Satu studi kasus, Bank Mini, akan menemani praktikum sepanjang semester, dari kelas tunggal hingga aplikasi GUI dan database.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Pengantar dan Classes and Objects

Oracle Java Tutorials: "Object-Oriented Programming Concepts"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 1

---

## Diskusi

Pilih satu benda sehari-hari yang berbeda dari yang kamu pilih pada Latihan Bagian 2. Menurutmu, apakah membungkus data dan perilaku benda itu menjadi satu objek lebih masuk akal dibandingkan menyimpan datanya terpisah dari fungsi-fungsi yang mengolahnya, seperti gaya prosedural pada Bagian 1? Berikan alasan konkret, bukan sekadar "karena OOP begitu".
