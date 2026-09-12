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

Pertemuan 4: **Relasi Kelas**

Bagaimana objek-objek saling terhubung membentuk sebuah sistem

---

## Yang Akan Kamu Pelajari

- Bahwa sebuah objek dapat memiliki objek lain sebagai atributnya
- Empat kekuatan relasi antar kelas: dependency, association, aggregation, composition
- Perbedaan umur objek pada masing-masing jenis relasi

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 4.
</div>

---

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Dependency dan association, relasi yang tidak memiliki objeknya
- **Sesi 2 (50')**: Aggregation dan composition, relasi kepemilikan dan notasi UML-nya
- **Sesi 3 (50')**: Referensi, null, dan array objek di memori
- **Sesi 4 (50')**: Menerapkan seluruh relasi ke Bank Mini

---

<!-- _class: divider -->

# Bagian 1
## Dependency dan Association

Sesi 1 dari 4

---

## Dari Atribut Sederhana ke Atribut Berupa Objek

Sejauh ini, atribut sebuah kelas selalu bertipe sederhana: `String`, `double`, `int`. Padahal, atribut sebuah kelas juga bisa bertipe kelas lain, dan sebuah kelas juga bisa memakai kelas lain sekadar sesaat tanpa pernah menyimpannya. Sebuah `Library` bisa memiliki banyak `Book` sebagai atributnya, sebuah `Car` bisa memiliki `Engine` sebagai atributnya, sedangkan sebuah `Printer` hanya memakai objek `File` sesaat ketika mencetak, tanpa pernah menyimpannya.

<div class="term-box">
Ketika sebuah kelas memakai atau memiliki kelas lain, kedua kelas tersebut dikatakan memiliki <b>relasi</b> ("has-a"). Relasi ini berbeda dari inheritance ("is-a"), yang baru dibahas pada Pertemuan 6.
</div>

---

## Mengapa Ini Penting?

Kalau `Printer` menyimpan `File` sebagai atributnya, padahal seharusnya cukup dipakai sesaat, `Printer` jadi terikat pada satu `File` itu saja. Padahal `Printer` seharusnya bisa mencetak file apa pun, kapan saja.

<div class="term-box">
Keterikatan antar kelas seperti ini disebut <b>coupling</b>. Semakin erat coupling-nya, semakin sulit satu kelas diubah tanpa ikut mengubah kelas lain. Pada program besar berisi ratusan kelas, ini salah satu penyebab utama kode yang sulit dirawat.
</div>

---

## Empat Kekuatan Relasi

![h:230 Empat tingkat kekuatan relasi antar kelas: dependency, association, aggregation, composition](../assets/illustrations/relation-strengths.svg)

Keempatnya menyatakan bagaimana satu kelas bergantung pada kelas lain, mulai dari sekadar dipakai sesaat (dependency) sampai dimiliki seumur hidup objeknya (composition). Bagian 1 membahas dua yang pertama; Bagian 2 membahas dua yang terkuat.

---

## Dependency dalam Detail

Ilustrasi `Printer`/`File` menunjukkan dependency: `Printer` memakai `File` hanya selama satu method sedang berjalan, misalnya sebagai parameter `print(File document)`. Begitu method selesai, `Printer` tidak menyimpan referensi apa pun ke `File` tersebut.

<div class="term-box">
<b>Dependency</b> adalah relasi terlemah: objek yang dipakai hanya muncul sebagai parameter method (atau variabel lokal), tidak pernah menjadi atribut kelas.
</div>

---

## Association dalam Detail

Berbeda dari dependency, association menyimpan referensi objek lain sebagai atribut, seperti `Driver` yang menyimpan referensi ke `Car` miliknya. Karena disimpan sebagai atribut, hubungan ini bertahan selama objek `Driver` itu sendiri masih hidup, bukan cuma selama satu pemanggilan method.

<div class="term-box">
Pada association, kedua objek tetap independen satu sama lain: <code>Driver</code> bisa berganti <code>Car</code>, dan <code>Car</code> yang sama bisa dipakai <code>Driver</code> lain, tanpa memengaruhi umur objek satu sama lain.
</div>

---

## Contoh Kode: Dependency vs Association

```java
class Printer {
    void print(File document) {
        // document dipakai sesaat: dependency
    }
}

class Driver {
    Car car;
    Driver(Car car) { this.car = car; }  // disimpan sebagai atribut: association
}
```

---

## Kesalahan Umum: Menyimpan yang Seharusnya Sesaat

<div class="warn-box">
<b>Salah:</b> parameter <code>File</code> pada method <code>print(File document)</code> ikut disimpan ke field <code>this.lastFile = document</code>, padahal <code>Printer</code> tidak pernah memakainya lagi setelah pencetakan selesai.
</div>

**Benar:** biarkan `File` hanya jadi parameter lokal, tidak disimpan sebagai field. Kecuali `Printer` memang butuh mengingatnya untuk dipakai lagi nanti, barulah itu pantas menjadi association yang disengaja.

---

## Latihan

Klasifikasikan tiap pasangan berikut sebagai **dependency** atau **association**, dan jelaskan alasannya:

1. `OrderProcessor` menerima `Logger` sebagai parameter method `process(Order order, Logger logger)`, dan hanya memakainya untuk mencatat satu baris log.
2. `Elevator` menyimpan referensi ke `ControlPanel` sebagai atributnya sepanjang umur objek `Elevator`.
3. `ReportGenerator` menerima `DateFormatter` sebagai parameter method `format(Date date, DateFormatter formatter)`, memakainya sekali, lalu tidak pernah lagi.
4. `MusicPlayer` menyimpan referensi ke `Playlist` yang sedang diputar sebagai atributnya.

---

## Jawaban Latihan

1. **Dependency**, `Logger` hanya dipakai sesaat di dalam satu pemanggilan method, tidak pernah disimpan.
2. **Association**, `ControlPanel` disimpan sebagai atribut, bertahan selama `Elevator` hidup.
3. **Dependency**, sama seperti soal 1: parameter yang dipakai sesaat, tidak disimpan.
4. **Association**, `Playlist` disimpan sebagai atribut `MusicPlayer`.

---

## Rangkuman Bagian 1

- Objek bisa dipakai sesaat lewat parameter method (dependency) atau disimpan sebagai atribut (association).
- Kekuatan relasi menentukan seberapa erat dua kelas saling terikat; dependency yang keliru dijadikan association membuat kelas terlalu terikat.
- Pada association, kedua objek tetap independen: masing-masing bisa hidup dan diganti tanpa memengaruhi yang lain.

Selanjutnya: Bagian 2 membahas dua relasi yang lebih erat lagi, relasi yang menyatakan satu objek benar-benar MEMILIKI objek lain sebagai bagian dirinya.

---

<!-- _class: divider -->

# Bagian 2
## Aggregation, Composition, dan Notasi UML

Sesi 2 dari 4

---

## Dari Sekadar Menyimpan ke Kepemilikan Sungguhan

Association menyimpan referensi objek lain, tetapi kedua objek tetap independen sepenuhnya, seperti `Driver` dan `Car` pada Bagian 1. Ada relasi has-a yang lebih erat lagi: satu objek benar-benar menjadi bagian dari objek lain, sehingga umur keduanya saling terkait. Sebuah `Library` memiliki banyak `Book` sebagai koleksinya; sebuah `Car` memiliki `Engine` sebagai komponennya. Keduanya sama-sama kepemilikan, tetapi berbeda seberapa erat.

---

## Aggregation: Kepemilikan yang Longgar

Pada aggregation, pemilik (`Library`) menyimpan referensi ke bagian (`Book`), tetapi bagian itu bisa dibuat sebelum jadi milik pemiliknya, dan tetap bisa hidup terpisah setelah pemiliknya dibuang. Sebuah `Book` bisa dipindahkan ke `Library` lain, atau tetap ada meski `Library` asalnya sudah tutup.

<div class="term-box">
<b>Aggregation</b>: pemilik memiliki koleksi bagian, tapi bagian tidak bergantung penuh pada pemiliknya untuk tetap hidup.
</div>

---

## Composition: Kepemilikan yang Erat

Pada composition, pemilik (`Car`) membuat bagiannya sendiri (`Engine`) di dalam constructor, dan tidak pernah membagikan referensinya ke pihak luar. Begitu `Car` dibuang, `Engine` yang jadi bagiannya ikut hilang; tidak ada `Engine` "yatim" yang tetap hidup sendirian.

<div class="term-box">
<b>Composition</b>: pemilik membuat dan sepenuhnya mengendalikan bagiannya; umur bagian terikat penuh pada umur pemiliknya.
</div>

---

## Mengapa Ini Penting?

Salah pilih composition atau aggregation bisa merepotkan. Kalau pakai composition padahal seharusnya aggregation, sesuatu yang semestinya dipakai bersama (misalnya satu `Book` yang sama di beberapa cabang `Library`) malah harus dibuat ulang terus-menerus.

Sebaliknya, kalau pakai aggregation padahal seharusnya composition, bagian yang seharusnya milik satu pemilik saja malah bisa dipegang dan diubah kode lain di tempat lain. Akibatnya data jadi tidak konsisten, nilainya beda-beda padahal seharusnya sama. Bug seperti ini sulit dilacak karena penyebabnya jauh dari gejalanya.

---

## Perbedaan Umur Objek

![h:280 Composition: bagian ikut hilang bersama pemiliknya. Association: bagian tetap hidup meski pemiliknya bubar](../assets/illustrations/whole-part-lifecycle.svg)

<div class="term-box">
Pada <b>composition</b>, bagian dibuat di dalam pemiliknya dan tidak pernah diberikan ke pihak luar; pemiliknya dibuang, bagian ikut hilang. Pada <b>association</b>, kedua objek independen sepenuhnya. <b>Aggregation</b> ada di antara keduanya, seperti sudah dijelaskan sebelumnya.
</div>

---

## Notasi UML: Membaca Diagram Relasi

| Relasi | Notasi garis | Contoh |
|---|---|---|
| Dependency | putus-putus, panah terbuka | `Printer` &#8674; `File` |
| Association | penuh, panah terbuka | `Driver` &#8594; `Car` |
| Aggregation | penuh, diamond KOSONG di sisi pemilik | `Library` &#9671;&#8212; `Book` |
| Composition | penuh, diamond PENUH di sisi pemilik | `Car` &#9670;&#8212; `Engine` |

<div class="term-box">
Diamond SELALU berada di sisi pemilik, bukan di sisi bagian.
</div>

---

## Multiplicity: Berapa Banyak di Tiap Sisi

![h:300 Multiplicity pada association Driver-Car dan aggregation Library-Book](../assets/uml/p04-multiplicity.png)

Multiplicity di ujung garis menyatakan berapa banyak objek yang boleh terlibat di sisi tersebut. Angka `1` berarti tepat satu; `0..*` berarti nol atau lebih. Pada `Driver` &#8594; `Car`, kedua sisi bertanda `1`: satu `Driver` menyetir satu `Car` pada satu waktu. Pada `Library` &#9671;&#8212; `Book`, sisi `Library` bertanda `1`, sisi `Book` bertanda `0..*`.

---

## Contoh Kode: Composition vs Aggregation

```java
class Car {
    Engine engine;
    Car() { engine = new Engine(150); }  // buat sendiri: composition
}

class Library {
    Book[] books;
    Library(Book[] books) { this.books = books; }  // dari luar: aggregation
}
```

<div class="tip-box">
Kelas yang memanggil <code>new</code> untuk membuat objek bagiannya sendiri, seperti <code>Car</code>, itu composition. Kelas yang menerima bagian sebagai parameter, seperti <code>Library</code>, itu aggregation.
</div>

---

## Kesalahan Umum: Diamond di Sisi yang Salah

<div class="warn-box">
<b>Salah:</b> menggambar diamond di sisi <code>Book</code> (bagian), seolah <code>Book</code> yang memiliki <code>Library</code>.
</div>

**Benar:** diamond selalu di sisi `Library` (pemilik), searah dengan siapa yang menyimpan koleksinya. Sebelum menggambar diamond, baca ulang kalimatnya: harus terbaca "`Library` MEMILIKI `Book`", bukan sebaliknya.

---

## Latihan

Untuk tiap pasangan berikut, tentukan **aggregation** atau **composition**, lalu tentukan di sisi mana diamond digambar:

1. `Playlist` menyimpan daftar `Song` yang bisa dipindahkan ke `Playlist` lain.
2. `House` membuat sendiri objek `Room`-nya di dalam constructor, dan tidak pernah membagikannya keluar.
3. `Team` menyimpan daftar `Player` yang bisa dipindahkan ke `Team` lain kapan saja.
4. `Computer` membuat sendiri objek `CPU`-nya di dalam constructor.

---

## Jawaban Latihan

1. **Aggregation**, diamond di sisi `Playlist`; `Song` bisa berpindah tanpa `Playlist` lamanya.
2. **Composition**, diamond di sisi `House`; `Room` dibuat sendiri, tidak dibagikan keluar.
3. **Aggregation**, diamond di sisi `Team`; `Player` bisa pindah `Team`, seperti pada ilustrasi umur objek sebelumnya.
4. **Composition**, diamond di sisi `Computer`; `CPU` dibuat sendiri di dalam constructor.

---

## Rangkuman Bagian 2

- Aggregation: pemilik menyimpan referensi ke bagian, tapi bagian bisa hidup terpisah (kepemilikan longgar).
- Composition: pemilik membuat bagiannya sendiri dan tidak membagikannya keluar; umur bagian terikat penuh pada pemiliknya.
- Notasi UML: diamond selalu di sisi pemilik, kosong untuk aggregation, penuh untuk composition; multiplicity di ujung garis menyatakan jumlah objek yang terlibat.

Selanjutnya: semua relasi ini bekerja lewat referensi, bukan salinan objek. Bagian 3 masuk ke bagaimana referensi ini sebenarnya bekerja di memori.

---

<!-- _class: divider -->

# Bagian 3
## Referensi, Null, dan Array Objek di Memori

Sesi 3 dari 4

---

## Referensi, Bukan Salinan

![Dua variabel menunjuk satu objek yang sama di heap; perubahan lewat salah satunya terlihat lewat keduanya](../assets/illustrations/stack-heap-alias.svg)

<div class="term-box">
Pada Pertemuan 2, sebuah variabel objek tidak menyimpan objeknya sendiri, hanya referensi (alamat) ke objek itu di heap. Aturan ini berlaku juga pada association, aggregation, dan composition. Ketika dua variabel menunjuk objek yang sama, mengubah data lewat salah satunya akan terlihat lewat yang lain.
</div>

---

## Contoh Kode: Satu Objek, Dua Variabel

```java
Rectangle a = new Rectangle(10, 4);
Rectangle b = a;

System.out.println(a.getWidth());  // 10
System.out.println(b.getWidth());  // 10
```

<div class="tip-box">
<code>b = a</code> tidak membuat objek baru. <code>a</code> dan <code>b</code> menunjuk objek yang sama persis di heap, seperti pada ilustrasi.
</div>

---

## Mengapa Ini Penting?

Dua variabel yang menunjuk objek yang sama disebut **aliasing**. Kalau tidak disadari, aliasing gampang menimbulkan bug. Contohnya, sebuah method menerima objek lewat parameter, lalu mengubahnya sekadar untuk mencoba. Padahal yang memanggil method itu masih memegang referensi yang sama, dan tidak menyangka objeknya ikut berubah. Bug seperti ini sulit dilacak, sebab tempat perubahannya jauh dari tempat gejalanya muncul.

---

## Referensi Kosong (null)

![Variabel yang belum menunjuk ke objek mana pun bernilai null; memanggil method di atasnya selalu gagal](../assets/illustrations/null-reference.svg)

<div class="term-box">
Sebuah variabel objek yang belum pernah diisi, atau sengaja dikosongkan, menyimpan nilai khusus <code>null</code>: belum menunjuk ke objek mana pun.
</div>

---

## Mengapa Null Berbahaya

Memanggil method pada variabel bernilai `null` selalu menghasilkan `NullPointerException`, sebab tidak ada objek sungguhan di ujung referensi tersebut.

<div class="term-box">
Method yang bisa mengembalikan <code>null</code> ketika data tidak ditemukan (misalnya method pencarian) HARUS diperiksa pemanggilnya sebelum hasilnya dipakai; melewatkan pemeriksaan ini adalah salah satu penyebab crash paling umum pada aplikasi produksi.
</div>

---

## Array Objek: Banyak Objek dari Satu Kelas

![Satu kelas melahirkan banyak objek independen, ditampung dalam satu array referensi](../assets/illustrations/multiple-objects-array.svg)

Satu kelas `Rectangle` bisa melahirkan banyak objek independen, masing-masing dengan datanya sendiri. Sebuah array menampung banyak REFERENSI ke objek-objek ini sekaligus, bukan objeknya secara langsung.

---

## Array Referensi: Slot yang Belum Terisi

Sebuah array objek yang baru dibuat (misalnya `new Account[10]`) berisi 10 slot, tetapi seluruhnya masih `null` sampai diisi satu per satu.

<div class="tip-box">
Kelas yang mengelola array semacam ini biasanya juga menyimpan sebuah pencacah (<code>count</code>) untuk tahu berapa slot yang sudah terisi, supaya tidak salah membaca slot <code>null</code> sebagai data sungguhan.
</div>

---

## Pratinjau: IS-A vs HAS-A

![h:260 Uji lisan sederhana untuk membedakan relasi IS-A (inheritance) dari HAS-A (relasi atribut)](../assets/illustrations/is-a-vs-has-a.svg)

Seluruh relasi pada pertemuan ini adalah HAS-A: satu kelas MEMILIKI kelas lain sebagai atribut. Ada satu kategori relasi lagi yang justru menyatakan satu kelas ADALAH jenis khusus dari kelas lain, disebut IS-A, dibahas mendalam pada Pertemuan 6.

---

## Kesalahan Umum: Mengira Assignment Menyalin Objek

<div class="warn-box">
<b>Salah:</b> menulis <code>Rectangle b = a;</code> lalu mengira <code>b</code> dan <code>a</code> adalah dua objek yang terpisah.
</div>

**Benar:** `b` dan `a` menunjuk objek YANG SAMA. Untuk benar-benar mendapat objek terpisah, harus dibuat objek baru secara eksplisit (misalnya `new Rectangle(a.getWidth(), a.getHeight())`), bukan sekadar assignment.

---

## Latihan

Diberi kode berikut (`Rectangle` punya method `setWidth` dan `getWidth`):

`Rectangle a = new Rectangle(5, 10);`
`Rectangle b = a;`
`b.setWidth(99);`
`System.out.println(a.getWidth());`

Berapa nilai yang tercetak, dan mengapa?

---

## Jawaban Latihan

Tercetak **99**. `b = a` hanya menyalin REFERENSI, bukan objeknya; `a` dan `b` menunjuk objek yang sama persis di heap, sehingga `b.setWidth(99)` juga terlihat lewat `a`.

---

## Rangkuman Bagian 3

- Semua relasi antar kelas bekerja lewat referensi, bukan salinan objek; assignment menyalin referensinya saja.
- Variabel bernilai `null` belum menunjuk ke objek mana pun; method pada `null` selalu melempar `NullPointerException`.
- Array objek menampung banyak referensi sekaligus, termasuk slot yang masih `null` sebelum diisi.

Selanjutnya: Bagian 4 menerapkan seluruh relasi, referensi, dan array ini langsung ke Bank Mini.

---

<!-- _class: divider -->

# Bagian 4
## Menerapkan Relasi Kelas ke Bank Mini

Sesi 4 dari 4

---

## Account Berelasi dengan Customer

![Bank ber-aggregation dengan Account, dan Account ber-association dengan Customer](../assets/uml/p04-bank-customer-account.png)

`Account` kini menyimpan referensi ke sebuah objek `Customer` (association) sebagai ganti sekadar nama pemilik berupa teks. `Bank` menyimpan banyak `Account` dalam sebuah array (aggregation).

---

## Mengapa Association, Bukan Aggregation atau Composition?

`Account` hanya menyimpan referensi ke satu `Customer` yang sudah ada sebelumnya, diterima dari luar. `Account` tidak membuat `Customer`-nya sendiri (jadi bukan composition), dan tidak mengelola banyak `Customer` sebagai koleksi (jadi bukan aggregation).

<div class="term-box">
Karena <code>Account</code> sekadar menyimpan referensi ke objek yang sudah ada, hubungan paling pas untuk pola ini adalah <b>association</b>.
</div>

---

## Bank Mengelola Banyak Account

`Bank` menyimpan referensi ke banyak `Account` dalam sebuah array, mirip array `Account[]` yang dibuat pada Pertemuan 2. Bedanya, array ini menjadi atribut sebuah kelas, bukan variabel lokal di `main`.

<div class="term-box">
<code>Account</code> dibuat terpisah, baru ditambahkan ke <code>Bank</code> lewat <code>addAccount(...)</code>. Bila dilepas dari <code>Bank</code>, <code>Account</code> tetap bisa berdiri sendiri, ciri khas <b>aggregation</b>.
</div>

---

## Graf Objek di Heap

![Satu Bank mereferensikan array, yang mereferensikan objek Account, yang mereferensikan objek Customer](../assets/illustrations/object-graph-references.svg)

<div class="tip-box">
Objek-objek yang saling berelasi membentuk sebuah <b>graf objek</b> di heap: satu objek menunjuk ke objek lain lewat referensi, bukan menyalin datanya. Mengubah data <code>Customer</code> lewat satu <code>Account</code> akan terlihat oleh siapa pun yang memegang referensi <code>Customer</code> yang sama, persis seperti aliasing yang dibahas pada Bagian 3.
</div>

---

## Method Bank: Menambah, Mencari, dan Menampilkan

Method `addAccount()` menambah anggota array. Method `findAccount()` mencari berdasarkan nomor rekening, dan mengembalikan `null` bila tidak ditemukan. Method `printAllAccounts()` mencetak seluruh anggotanya satu per satu.

---

## Contoh Kode: Memeriksa Hasil `findAccount`

```java
Account result = bank.findAccount("Z999");
if (result != null) {
    result.printInfo();
} else {
    System.out.println("Account not found");
}
```

<div class="tip-box">
Selalu periksa hasil <code>findAccount(...)</code> terhadap <code>null</code> sebelum dipakai, seperti dibahas pada Bagian 3.
</div>

---

## Latihan

Bila objek `Customer` (misalnya Nadia) dihapus dari memori sementara `Account` miliknya masih dipegang oleh `Bank`, apakah `Account` tersebut ikut terhapus? Kaitkan jawabanmu dengan arah panah association `Account` &#8594; `Customer` pada diagram kelas.

---

## Jawaban Latihan

**Tidak.** Association hanya menyatakan `Account` MENYIMPAN REFERENSI ke `Customer`, bukan MEMILIKI umurnya. Arah panah association menunjuk SATU ARAH: `Account` mengenal `Customer`, tetapi `Customer` tidak menyimpan balik referensi ke `Account` mana pun. Selama masih ada referensi yang menunjuk ke objek `Customer` tersebut (termasuk lewat `Account`), objek itu tidak akan dibuang.

---

## Rangkuman Bagian 4

- `Account`-`Customer`: association, referensi ke objek yang sudah ada, diterima dari luar.
- `Bank`-`Account`: aggregation, pemilik menyimpan koleksi bagian yang tetap bisa berdiri sendiri.
- Seluruh method `Bank` bekerja di atas array referensi, termasuk kemungkinan `null` yang harus diperiksa pemanggilnya.

---

## Rangkuman Pertemuan 4

- Empat kekuatan relasi antar kelas, dari terlemah ke terkuat: dependency, association, aggregation, composition.
- Notasi UML: dependency (panah putus-putus), association (panah penuh), aggregation (diamond kosong di sisi pemilik), composition (diamond penuh di sisi pemilik).
- Semua relasi ini bekerja lewat referensi, bukan salinan objek; array objek menampung banyak referensi sekaligus.
- Bank Mini: `Account` ber-association dengan `Customer`, dan `Bank` ber-aggregation dengan `Account`.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Classes and Objects: Object References

Oracle Java Tutorials: "Creating Objects" dan "Using Objects"

Fowler, *UML Distilled*, bab Class Diagrams: Association, Aggregation, Composition

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 4

---

## Diskusi

Bayangkan objek `Bank` dihapus dari memori. Menurutmu, apakah objek `Account` yang sudah ditambahkan ke dalamnya ikut terhapus, atau tetap bisa berdiri sendiri secara independen? Jelaskan jawabanmu, lalu simpulkan: apa artinya jawaban tersebut terhadap jenis relasi `Bank`-`Account`, aggregation atau composition?
