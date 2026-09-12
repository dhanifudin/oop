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

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Dari dunia nyata ke kelas dan objek
- **Sesi 2 (50')**: Anatomi sebuah kelas, atribut, method, konstruktor
- **Sesi 3 (50')**: Apa yang terjadi di memori saat objek dibuat
- **Sesi 4 (50')**: Membaca diagram kelas UML

---

<!-- _class: divider -->

# Bagian 1
## Dari Dunia Nyata ke Kelas

Sesi 1 dari 4

---

## Analogi Cetakan Kue

![h:300 Satu kelas menghasilkan banyak objek, masing-masing dengan datanya sendiri](../assets/illustrations/class-object-cutter.svg)

<div class="term-box">
<b>Kelas</b> merupakan cetakan atau blueprint, sedangkan <b>objek</b> adalah wujud konkret yang dihasilkan dari cetakan tersebut. Satu kelas dapat menghasilkan banyak objek, dan setiap objek memiliki datanya masing-masing.
</div>

---

## Objek Sebagai State dan Behavior

![h:280 Kontras antara data dan perilaku yang digabungkan menjadi satu (berorientasi objek) dibandingkan tersebar (prosedural)](../assets/illustrations/state-behavior-bundle.svg)

Setiap objek menggabungkan dua hal: **state** (data yang dimiliki objek itu sendiri) dan **behavior** (perilaku yang dapat dilakukan objek terhadap datanya). Berbeda dari pendekatan prosedural, di mana data dan fungsi yang mengolahnya biasanya tersebar di lokasi yang berbeda-beda.

---

## Mengapa Ini Penting?

Kelas dan objek bukan sekadar cara mengorganisasi kode. Keduanya adalah fondasi yang menopang seluruh konsep OOP lain yang akan dipelajari sepanjang semester ini. Tanpa objek sebagai satu-satuan, tidak ada "sesuatu" yang sifatnya bisa diwarisi, datanya bisa disembunyikan, atau perilakunya bisa diperlakukan berbeda-beda tergantung jenisnya.

<div class="term-box">
Membundel data dan perilaku ke dalam satu objek juga membuat tiap bagian program bisa dipahami sendiri-sendiri, tanpa harus menelusuri seluruh kode untuk tahu bagaimana suatu data dipakai. Inilah salah satu alasan aplikasi besar yang tersusun dari banyak objek kecil jauh lebih mudah dirawat dibandingkan satu program raksasa.
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

## Contoh Kode: Kelas dan Objek Pertama

```java
class Rectangle {
    int width;
    int height;
}

Rectangle r1 = new Rectangle();
r1.width = 6;
r1.height = 4;
```

<div class="tip-box">
Baris pertama mendefinisikan kelas (cetakan). Baris <code>new Rectangle()</code> membuat satu objek konkret dari cetakan itu; <code>r1</code> adalah nama untuk objek tersebut.
</div>

---

## Kesalahan Umum: Mengira Kelas Itu Sendiri Punya Data

<div class="warn-box">
<b>Salah:</b> menganggap <code>Rectangle</code> (kelasnya) sudah punya lebar dan tinggi tersendiri, sebelum ada objek yang dibuat darinya.
</div>

**Benar:** kelas hanya cetakan, tidak menyimpan data apa pun. Data (lebar, tinggi) baru benar-benar ada setelah sebuah objek dibuat lewat `new`. Dua objek dari kelas yang sama bisa punya lebar dan tinggi yang berbeda-beda.

---

## Latihan

Untuk tiap pernyataan berikut, tentukan apakah yang dimaksud adalah **kelas** atau **objek**:

1. Cetakan kue berbentuk bintang yang tergantung di dapur.
2. Kue bintang yang baru saja dikeluarkan dari oven, ukurannya sedikit lebih besar dari kue bintang sebelumnya.
3. Rancangan umum "Mobil" yang mendefinisikan bahwa setiap mobil punya kecepatan dan bahan bakar.
4. Mobil berwarna merah yang sedang terparkir di garasi rumahmu, dengan bahan bakar setengah penuh.

---

## Jawaban Latihan

1. **Kelas**, cetakannya sendiri, belum ada kuenya.
2. **Objek**, wujud konkret hasil cetakan, dengan datanya sendiri (ukurannya).
3. **Kelas**, rancangan umum, belum menunjuk satu mobil tertentu.
4. **Objek**, mobil konkret dengan datanya sendiri (warna, bahan bakar).

---

## Rangkuman Bagian 1

- Kelas adalah cetakan; objek adalah wujud konkret hasil cetakan itu, dengan datanya sendiri.
- Objek membundel state (data) dan behavior (perilaku) jadi satu kesatuan.
- Kelas sendiri tidak pernah menyimpan data; data baru ada setelah objek dibuat lewat `new`.

Selanjutnya: Bagian 2 membedah bagian-bagian penyusun sebuah kelas: atribut, method, dan konstruktor.

---

<!-- _class: divider -->

# Bagian 2
## Anatomi Sebuah Kelas

Sesi 2 dari 4

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

![h:360 Objek sebelum dan sesudah konstruktor mengisi atributnya](../assets/illustrations/constructor-before-after.svg)

**Konstruktor** adalah bagian kelas yang dijalankan secara otomatis pada saat objek baru dibuat. Tugasnya adalah memastikan seluruh atribut langsung terisi lengkap, sehingga objek tidak pernah berada dalam kondisi "setengah jadi".

---

## Mengapa Diperlukan `this`?

![h:320 this merujuk pada objek itu sendiri, berbeda dengan parameter yang berasal dari luar](../assets/illustrations/this-self-reference.svg)

Parameter konstruktor sering diberi nama yang sama persis dengan atributnya agar maksudnya jelas. Agar Java dapat membedakan keduanya, tersedia kata kunci `this` yang merujuk pada objek yang sedang dibuat, berbeda dari parameter yang hanya berupa nilai kiriman dari luar.

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

## Contoh Kode: Konstruktor dengan `this`

```java
class Rectangle {
    int width;
    int height;

    Rectangle(int width, int height) {
        this.width = width;    // this.width: atribut objek
        this.height = height;  // width: parameter dari luar
    }
}
```

<div class="tip-box">
Nama parameter dan atribut boleh sama persis. <code>this.width</code> selalu merujuk atribut objek, <code>width</code> saja merujuk parameter.
</div>

---

## Parameter dan Nilai Kembali

![h:280 Method sebagai mesin kecil: menerima input dan mengembalikan hasil](../assets/illustrations/function-io.svg)

Sebuah method dapat menerima input (**parameter**) dan mengembalikan hasil (**return value**), serupa dengan mesin kecil yang mengolah masukan menjadi keluaran.

<div class="tip-box">
Java juga mengizinkan beberapa method memiliki nama yang sama dengan parameter yang berbeda (overloading). Materi ini dibahas secara lengkap pada Pertemuan 7.
</div>

---

## Mengapa Ini Penting?

Bayangkan sebuah kelas dengan sepuluh atribut, tapi tanpa konstruktor yang mengisinya. Setiap kali objek baru dibuat, seseorang harus ingat mengisi kesepuluh atributnya satu per satu secara manual. Lupa satu saja, atribut itu diam-diam bernilai kosong, dan bug baru muncul jauh kemudian saat atribut itu dipakai.

<div class="term-box">
Konstruktor memindahkan tanggung jawab ini dari "siapa pun yang membuat objek" menjadi "kelasnya sendiri". Objek yang selesai dibuat dijamin selalu lengkap, tidak bergantung pada ingatan programmer yang memanggilnya.
</div>

---

## Kesalahan Umum: Lupa Memakai `this`

<div class="warn-box">
<b>Salah:</b> menulis <code>width = width;</code> di dalam konstruktor, berharap ini mengisi atribut, padahal parameter <code>width</code> hanya menyalin nilainya ke dirinya sendiri, atribut objeknya tidak pernah tersentuh.
</div>

**Benar:** tulis `this.width = width;`. Bagian kiri (`this.width`) merujuk atribut objek, bagian kanan (`width`) merujuk parameter.

---

## Latihan

Sebuah kelas `Rectangle` punya atribut `width` dan `height`, serta konstruktor berparameter `width` dan `height`. Di dalam konstruktornya, seorang mahasiswa menulis:

`height = height;`

Setelah objek dibuat lewat `new Rectangle(6, 4)`, berapa nilai atribut `height`-nya? Jelaskan alasanmu.

---

## Jawaban Latihan

Bukan **4**, melainkan nilai kosong bawaan (0). `height = height;` hanya menyalin nilai parameter `height` ke parameter itu sendiri, atribut objeknya (`this.height`) tidak pernah diisi. Seharusnya ditulis `this.height = height;`.

---

## Rangkuman Bagian 2

- Atribut menyimpan data objek, method menyediakan perilakunya.
- Konstruktor mengisi atribut secara otomatis begitu objek dibuat, mencegah objek "setengah jadi".
- `this` merujuk objek itu sendiri, dipakai untuk membedakan atribut dari parameter yang kebetulan bernama sama.

Selanjutnya: Bagian 3 melihat apa yang sebenarnya terjadi di memori komputer saat sebuah objek dibuat.

---

<!-- _class: divider -->

# Bagian 3
## Objek di Memori

Sesi 3 dari 4

---

## Tahapan Pembuatan Objek

![Empat tahap yang terjadi pada saat sebuah objek baru dibuat](../assets/illustrations/object-creation-flow.svg)

---

## Stack dan Heap: Variabel Bukan Objeknya Sendiri

![h:360 Variabel di stack menunjuk pada objek di heap](../assets/illustrations/stack-heap-single.svg)

Variabel yang berada di **stack** hanya menyimpan alamat (referensi), bukan objeknya secara langsung. Objek yang sesungguhnya, lengkap dengan seluruh datanya, disimpan secara terpisah di **heap**.

---

## Menyalin Variabel Berbeda dengan Menyalin Objek

Karena variabel hanya menyimpan alamat, dua variabel dapat menunjuk ke objek yang persis sama. Apabila hal ini terjadi, perubahan data melalui salah satu variabel akan otomatis terlihat melalui variabel lainnya, karena keduanya menunjuk ke objek yang sama persis di heap.

---

## Ilustrasi: Dua Variabel, Satu Objek

![h:380 Dua variabel di stack menunjuk pada satu objek yang sama di heap](../assets/illustrations/stack-heap-alias.svg)

Kondisi ini disebut **aliasing**, yaitu dua atau lebih variabel yang menunjuk ke objek yang persis sama di heap.

---

## Contoh Kode: Referensi, Bukan Salinan

```java
Rectangle a = new Rectangle(10, 4);
Rectangle b = a;

b.width = 99;
System.out.println(a.width);  // 99, bukan 10
```

<div class="tip-box">
<code>b = a</code> tidak membuat objek baru. <code>a</code> dan <code>b</code> menunjuk objek yang sama persis di heap.
</div>

---

## Referensi yang Belum Menunjuk ke Objek Mana Pun

![h:260 Referensi null menunjuk ke ruang kosong](../assets/illustrations/null-reference.svg)

<div class="warn-box">
Referensi yang belum menunjuk ke objek mana pun disebut bernilai kosong (null). Apabila method dipanggil pada referensi yang masih kosong, program akan langsung berhenti dengan galat. Solusinya selalu sama, yaitu memastikan objek telah benar-benar dibuat sebelum method-nya digunakan.
</div>

---

## Mengapa Ini Penting?

Aliasing dan referensi kosong terdengar seperti detail teknis kecil, tapi keduanya adalah penyebab bug yang sangat umum pada aplikasi nyata. Sebuah method yang mengira sedang memegang objeknya sendiri, padahal sebenarnya berbagi objek yang sama dengan bagian program lain, bisa diam-diam mengubah data yang tidak seharusnya ia ubah.

<div class="term-box">
Memahami bahwa variabel objek hanyalah referensi, bukan objeknya sendiri, adalah salah satu lompatan pemahaman terpenting di awal belajar OOP. Banyak bug membingungkan di kemudian hari sebenarnya berakar dari sini.
</div>

---

## Banyak Objek dari Satu Kelas

![h:300 Satu kelas menghasilkan beberapa objek independen dalam sebuah array](../assets/illustrations/multiple-objects-array.svg)

Satu kelas dapat menghasilkan banyak objek sekaligus, dan seluruh objek tersebut dapat ditampung dalam satu array. Setiap objek tetap independen, memiliki ukuran yang berbeda-beda, dan datanya tidak saling memengaruhi.

<p class="footnote">Objek yang tidak lagi ditunjuk oleh referensi mana pun akan otomatis dibersihkan dari heap oleh garbage collector.</p>

---

## Kesalahan Umum: Mengira Assignment Menyalin Objek

<div class="warn-box">
<b>Salah:</b> menulis <code>Rectangle b = a;</code> lalu mengira <code>b</code> dan <code>a</code> adalah dua objek yang terpisah.
</div>

**Benar:** `b` dan `a` menunjuk objek yang sama persis. Untuk benar-benar mendapat objek terpisah, harus dibuat objek baru secara eksplisit lewat `new`, bukan sekadar assignment.

---

## Latihan

Diberi kode berikut:

`Rectangle p = new Rectangle(5, 8);`
`Rectangle q = p;`
`q.height = 20;`
`System.out.println(p.height);`

Berapa nilai yang tercetak, dan mengapa?

---

## Jawaban Latihan

Tercetak **20**. `q = p` hanya menyalin referensi, bukan objeknya; `p` dan `q` menunjuk objek yang sama persis di heap, sehingga `q.height = 20` juga terlihat lewat `p`.

---

## Rangkuman Bagian 3

- Variabel objek menyimpan referensi (alamat) ke heap, bukan objeknya sendiri; assignment menyalin referensinya saja.
- Dua variabel bisa menunjuk objek yang sama (aliasing); mengubah lewat satu variabel terlihat lewat yang lain.
- Referensi bernilai null belum menunjuk ke objek mana pun; memanggil method di atasnya selalu gagal.

Selanjutnya: Bagian 4 menutup pertemuan ini dengan membaca kelas lewat diagram, bukan lewat kode.

---

<!-- _class: divider -->

# Bagian 4
## Membaca Diagram Kelas UML

Sesi 4 dari 4

---

## Anatomi Kotak Kelas UML

![h:300 Diagram kelas Rectangle](../assets/uml/p02-rectangle.png)

<div class="term-box">
Tanda <b>-</b> menunjukkan atribut atau method bersifat privat (hanya dapat diakses dari dalam kelas itu sendiri), sedangkan tanda <b>+</b> menunjukkan sifat publik (dapat diakses dari luar kelas). Konsep enkapsulasi dibahas secara lengkap pada Pertemuan 3.
</div>

---

## Contoh Kode: Dari Diagram ke Kode

```java
class Rectangle {
    private int width;
    private int height;

    public Rectangle(int width, int height) { ... }
    public int area() { ... }
    public int perimeter() { ... }
}
```

<div class="tip-box">
Setiap baris diagram punya padanan langsung di kode: atribut jadi field, method jadi deklarasi method, tanda -/+ jadi kata kunci <code>private</code>/<code>public</code>.
</div>

---

## Kesalahan Umum: Salah Membaca Tanda -/+

<div class="warn-box">
<b>Salah:</b> mengira tanda <code>-</code> dan <code>+</code> di depan atribut menunjukkan jenis data (negatif/positif), bukan hak akses.
</div>

**Benar:** tanda itu sama sekali tidak berkaitan dengan nilai datanya. Keduanya murni menyatakan siapa yang boleh mengakses: `-` berarti privat, `+` berarti publik.

---

## Latihan Membaca: Kelas Account

![h:280 Diagram kelas Account](../assets/uml/p02-account.png)

**Latihan:** berapa jumlah atribut pada kelas ini? Apakah atribut-atributnya bersifat privat atau publik? Method apa saja yang disediakan, dan input apa yang dibutuhkan oleh masing-masing method?

---

## Jawaban Latihan

Kelas ini punya dua atribut, `ownerName` dan `balance`, dan keduanya bersifat publik (tanda `+`): bisa diakses maupun diubah langsung dari luar kelas, tanpa lewat method apa pun. Method-nya juga publik: `deposit(amount)` dan `withdraw(amount)` masing-masing menerima satu parameter angka, sedangkan `printInfo()` tidak menerima parameter sama sekali. Atribut publik ini bukan kebetulan, melainkan celah yang sengaja dibahas tuntas pada Pertemuan 3.

<div class="tip-box">
<code>Account</code> adalah kelas pertama dari studi kasus <b>Bank Mini</b> yang akan dibangun sepanjang semester ini. Penerjemahan diagram ini menjadi kode Java dilakukan sebagai latihan praktik pada jobsheet Praktikum Pertemuan 2 (RTI253008).
</div>

---

## Rangkuman Bagian 4

- Diagram kelas UML punya tiga bagian: nama kelas, atribut, dan method.
- Tanda `-` berarti privat, tanda `+` berarti publik; keduanya soal hak akses, bukan jenis data.
- Setiap baris diagram punya padanan langsung di kode Java: atribut jadi field, method jadi deklarasi method.

---

## Rangkuman Pertemuan 2

- Kelas adalah cetakan; objek adalah wujud konkret hasil cetakan, dengan datanya sendiri.
- Konstruktor mengisi atribut objek secara otomatis; `this` membedakan atribut dari parameter bernama sama.
- Variabel objek menyimpan referensi ke heap, bukan objeknya sendiri; dua variabel bisa menunjuk objek yang sama (aliasing).
- Diagram kelas UML membaca kelas lewat nama, atribut, dan method, lengkap dengan tanda hak aksesnya.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program* - Bab Classes and Objects

Oracle Java Tutorials: "Classes and Objects"

Latihan praktik untuk materi ini tersedia pada jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 2

---

## Diskusi

Pilih satu kelas dari dunia nyata yang belum dibahas di kelas ini (bukan `Rectangle`, `Student`, atau `Circle`). Sebutkan minimal tiga atribut dan dua method yang menurutmu wajar dimiliki kelas tersebut, lalu sketsakan diagram UML sederhananya (nama kelas, atribut, method, tanpa perlu digambar rapi, cukup ditulis di kertas).
