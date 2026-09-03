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

Pertemuan 4: **Relasi Kelas**

Bagaimana objek-objek saling terhubung membentuk sebuah sistem

---

## Yang Akan Kamu Pelajari

- Bahwa sebuah objek dapat memiliki objek lain sebagai atributnya
- Tiga kekuatan relasi antar kelas: association, aggregation, composition
- Perbedaan umur objek pada masing-masing jenis relasi
- Penerapan relasi kelas pada studi kasus Bank Mini: `Customer`, `Account`, `Bank`

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 4.
</div>

---

<!-- _class: divider -->

# Bagian 1
## Objek Bisa Memiliki Objek Lain

---

## Dari Atribut Sederhana ke Atribut Berupa Objek

Sejauh ini, atribut sebuah kelas selalu bertipe sederhana: `String`, `double`, `int`. Padahal, atribut sebuah kelas juga bisa bertipe kelas lain. Sebuah `Library` bisa memiliki banyak `Book` sebagai atributnya, sebuah `Car` bisa memiliki `Engine` sebagai atributnya.

<div class="term-box">
Ketika sebuah kelas memiliki kelas lain sebagai atributnya, kedua kelas tersebut dikatakan memiliki <b>relasi</b> ("has-a"). Relasi ini berbeda dari inheritance ("is-a"), yang baru dibahas pada Pertemuan 6.
</div>

---

## Tiga Kekuatan Relasi

![Tiga tingkat kekuatan relasi has-a: association, aggregation, composition](../assets/illustrations/relation-strengths.svg)

Ketiganya sama-sama berarti "memiliki", tetapi berbeda dalam seberapa erat umur kedua objek saling terikat.

---

## Mengapa Ini Penting?

Hampir seluruh sistem perangkat lunak nyata memodelkan jaringan objek yang saling terhubung, bukan potongan data yang berdiri sendiri-sendiri. Memilih kekuatan relasi yang keliru bisa menimbulkan bug nyata: menghapus sebuah `Car` yang secara tidak sengaja ikut menghapus `Driver`-nya, padahal `Driver` masih dipakai objek lain, atau sebaliknya, `Engine` yang tetap "hidup" di memori padahal `Car` pemiliknya sudah lama dihapus, membuang-buang sumber daya program.

<div class="term-box">
Memodelkan relasi dengan kekuatan yang tepat, association, aggregation, atau composition, membuat umur setiap objek berperilaku sesuai harapan, sehingga program tidak diam-diam kehilangan data atau menyimpan data yang seharusnya sudah tidak diperlukan.
</div>

---

<!-- _class: divider -->

# Bagian 2
## Contoh Pengantar: Car, Engine, Driver

---

## Diagram Kelas: Association dan Composition

![Car berelasi composition dengan Engine, dan association dengan Driver](../assets/uml/p04-car-engine-driver.png)

`Car` membuat `Engine`-nya sendiri di dalam constructor (composition), sedangkan `Driver` menerima `Car` yang sudah ada dari luar (association).

---

## Perbedaan Umur Objek

![Composition: bagian ikut hilang bersama keseluruhan. Association: bagian tetap hidup meski keseluruhan bubar](../assets/illustrations/whole-part-lifecycle.svg)

<div class="term-box">
Pada <b>composition</b>, objek bagian (<code>Engine</code>) dibuat di dalam objek pemilik dan tidak pernah diberikan ke pihak luar; ketika objek pemilik dibuang, objek bagian ikut hilang. Pada <b>association</b>, kedua objek dapat tetap hidup secara independen satu sama lain.
</div>

---

<!-- _class: divider -->

# Bagian 3
## Menerapkan Relasi Kelas ke Bank Mini

---

## Account Berelasi dengan Customer

![Bank ber-aggregation dengan Account, dan Account ber-association dengan Customer](../assets/uml/p04-bank-customer-account.png)

`Account` kini menyimpan referensi ke sebuah objek `Customer` (association) sebagai ganti sekadar nama pemilik berupa teks. `Bank` menyimpan banyak `Account` dalam sebuah array (aggregation).

---

## Graf Objek di Heap

![Satu Bank mereferensikan array, yang mereferensikan objek Account, yang mereferensikan objek Customer](../assets/illustrations/object-graph-references.svg)

<div class="tip-box">
Objek-objek yang saling berelasi membentuk sebuah <b>graf objek</b> di heap: satu objek menunjuk ke objek lain lewat referensi, bukan menyalin datanya. Mengubah data <code>Customer</code> lewat satu <code>Account</code> akan terlihat oleh siapa pun yang memegang referensi <code>Customer</code> yang sama, persis seperti aliasing yang dibahas pada Pertemuan 2.
</div>

---

## Bank Mengelola Banyak Account

`Bank` menyimpan referensi ke banyak `Account` dalam sebuah array, mirip dengan array `Account[]` yang dibuat pada Pertemuan 2, namun kali ini array tersebut menjadi atribut sebuah kelas, bukan variabel lokal di `main`.

<div class="term-box">
Method <code>addAccount()</code> menambah anggota array, <code>findAccount()</code> mencari berdasarkan nomor rekening dan mengembalikan <code>null</code> bila tidak ditemukan, <code>printAllAccounts()</code> mencetak seluruh anggotanya satu per satu.
</div>

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Classes and Objects: Object References

Oracle Java Tutorials: "Creating Objects" dan "Using Objects"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 4

---

## Diskusi

Bayangkan objek `Bank` dihapus dari memori. Menurutmu, apakah objek `Account` yang sudah ditambahkan ke dalamnya ikut terhapus, atau tetap bisa berdiri sendiri secara independen? Jelaskan jawabanmu, lalu simpulkan: apa artinya jawaban tersebut terhadap jenis relasi `Bank`-`Account`, aggregation atau composition?
