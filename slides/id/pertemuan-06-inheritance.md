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

Pertemuan 6: **Inheritance**

Menurunkan sifat sebuah kelas ke kelas lain

---

## Yang Akan Kamu Pelajari

- Motivasi inheritance: menghindari duplikasi antar kelas yang mirip
- Cara mendeklarasikan subclass dengan `extends` dan memanggil constructor induk lewat `super(...)`, termasuk urutan eksekusi ketika beberapa `super(...)` berantai
- Kata kunci `protected`, dan inheritance bertingkat (multilevel) hingga kelas `Object` sebagai akar semua kelas di Java
- Kapan sebaiknya memilih inheritance ("is-a"), dan kapan memilih relasi ("has-a")
- Penerapan inheritance pada Bank Mini: `SavingsAccount` dan `CheckingAccount`

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 6.
</div>

---

<!-- _class: divider -->

# Bagian 1
## Konsep Inheritance

---

## Dari Kelas yang Mirip ke Superclass

Bayangkan kelas `Dog` dan `Cat` ditulis terpisah, padahal keduanya sama-sama punya atribut nama dan method untuk mendapatkan nama tersebut. Menyalin kode yang sama ke kedua kelas membuat program sulit dirawat: perubahan pada satu kelas harus diulang secara manual di kelas lainnya.

<div class="term-box">
<b>Inheritance</b> memungkinkan sebuah kelas (subclass) mewarisi atribut dan method dari kelas lain (superclass), sehingga kode yang sama cukup ditulis satu kali di superclass.
</div>

---

## Mengapa Ini Penting?

Bayangkan `Dog` dan `Cat` ditulis terpisah selama bertahun-tahun, lalu ditemukan bug pada method `getName()`-nya. Programmer memperbaiki bug itu di `Dog`, tetapi lupa melakukan hal yang sama di `Cat`, karena keduanya adalah salinan kode yang terpisah. Kode yang seharusnya identik tetapi perlahan "berbeda" karena hanya sebagian salinan yang diperbarui adalah salah satu sumber bug paling umum di proyek nyata.

<div class="term-box">
Inheritance menghilangkan sumber bug ini dengan memastikan kode yang sama hanya ada di satu tempat, yaitu superclass. Namun inheritance adalah alat yang kuat sekaligus mudah disalahgunakan: memaksakan hubungan "is-a" yang sebenarnya tidak alami justru menciptakan ketergantungan yang kaku antar kelas. Pertemuan 11 (SOLID) membahas disiplin lebih lanjut soal kapan inheritance sebaiknya dihindari.
</div>

---

## Struktur Inheritance

![Dog dan Cat masing-masing mewarisi dari Animal](../assets/illustrations/inheritance-tree.svg)

Kata kunci `extends` menyatakan hubungan ini dalam Java: `class Dog extends Animal` berarti `Dog` adalah subclass dari `Animal`, superclass-nya.

---

## Apa yang Diwariskan?

![Subclass Dog mewarisi seluruh anggota Animal, ditambah anggotanya sendiri](../assets/illustrations/inherited-members.svg)

Subclass otomatis memiliki seluruh atribut dan method (yang tidak bersifat `private`) milik superclass-nya, ditambah atribut dan method baru yang ditulis di subclass itu sendiri.

---

<!-- _class: divider -->

# Bagian 2
## Constructor, super(...), dan Visibilitas

---

## Constructor Superclass: `super(...)`

![h:280 Diagram kelas Animal, Dog, dan Cat](../assets/uml/p06-animal.png)

<div class="term-box">
Constructor subclass wajib memanggil constructor superclass, baik secara eksplisit lewat <code>super(...)</code> di baris pertama, maupun secara implisit (Java memanggil constructor tanpa parameter milik superclass apabila <code>super(...)</code> tidak dituliskan).
</div>

<div class="tip-box">
<code>Dog</code> dan <code>Cat</code> meng-override <code>makeSound()</code> agar setiap subclass punya bunyinya sendiri, ditandai anotasi <code>@Override</code>. Detail aturan overriding dibahas tuntas Pertemuan 7.
</div>

---

## Urutan Eksekusi Ketika super(...) Berantai

![Urutan pemanggilan super(...) dan urutan constructor body benar-benar dijalankan](../assets/illustrations/constructor-chain.svg)

Ketika `new Director(...)` dipanggil, `super(...)` merambat ke atas terlebih dahulu hingga mencapai `Employee`. Baru setelah itu, isi constructor benar-benar dijalankan, dimulai dari `Employee`, kemudian `Manager`, dan terakhir `Director`.

---

## Mengapa Urutan Ini Penting?

<div class="term-box">
Urutan ini menjamin bagian milik superclass sudah lengkap terbentuk sebelum subclass menambahkan bagiannya sendiri. Constructor subclass tidak pernah perlu khawatir mengakses bagian superclass yang belum siap.
</div>

<div class="warn-box">
Pemanggilan <code>super(...)</code>, bila dituliskan, wajib menjadi pernyataan pertama di dalam constructor. Java akan menampilkan galat compile bila <code>super(...)</code> diletakkan setelah pernyataan lain.
</div>

---

## Kata Kunci `protected` dan Inheritance Bertingkat

![h:380 Empat tingkat visibilitas di Java](../assets/illustrations/protected-visibility.svg)

<div class="term-box">
<code>protected</code> berada di antara default (hanya satu package) dan <code>public</code>: anggota bertanda <code>protected</code> dapat diakses subclass, bahkan bila berada di package berbeda.
</div>

---

## Inheritance Bertingkat (Multilevel)

![Object sebagai akar semua kelas, dengan Employee, Manager, dan Director bertingkat di bawahnya](../assets/illustrations/multilevel-ladder.svg)

Sebuah subclass boleh diturunkan lagi menjadi superclass bagi subclass yang lain. Setiap kelas di Java, tanpa terkecuali, pada akhirnya diturunkan dari kelas `Object`, meskipun kata `extends Object` tidak pernah dituliskan secara eksplisit.

---

## Diagram Kelas: Employee, Manager, Director

![Employee sebagai superclass, Manager dan Director bertingkat di bawahnya](../assets/uml/p06-employee-multilevel.png)

`name` bertanda `#` (protected) sehingga `Manager` dan `Director` dapat mengaksesnya secara langsung. `describe()` bertanda `{final}`: method ini sengaja tidak boleh di-override, supaya format keluarannya konsisten untuk seluruh jenis pegawai.

---

<!-- _class: divider -->

# Bagian 3
## Kapan Memakai Inheritance?

---

## IS-A vs HAS-A

![Uji cepat: baca relasinya, apakah lebih cocok is-a atau has-a](../assets/illustrations/is-a-vs-has-a.svg)

<div class="warn-box">
Inheritance sering dipakai secara keliru hanya karena dua kelas kebetulan punya beberapa atribut yang sama. Selalu uji dulu apakah relasinya benar-benar "is-a"; bila tidak terdengar wajar, relasi ("has-a", dibahas Pertemuan 4) biasanya pilihan yang lebih tepat.
</div>

---

<!-- _class: divider -->

# Bagian 4
## Menerapkan Inheritance ke Bank Mini

---

## SavingsAccount dan CheckingAccount

![Account sebagai superclass, SavingsAccount dan CheckingAccount sebagai subclass](../assets/uml/p06-account-hierarchy.png)

Kedua subclass ini menambahkan atributnya sendiri (`interestRate` dan `overdraftLimit`) serta method barunya sendiri (`printAccountType()`), sambil tetap mewarisi `deposit()`, `withdraw()`, dan `printInfo()` dari `Account` apa adanya, belum ada satu pun yang ditulis ulang.

---

## Method Warisan Belum Tentu Cocok untuk Semua Subclass

<div class="term-box">
<code>CheckingAccount</code> mewarisi <code>withdraw()</code> yang hanya membolehkan penarikan sebesar saldo yang tersedia, padahal <code>overdraftLimit</code> seharusnya membuat rekening ini bisa ditarik melebihi saldo. Atribut baru saja tidak cukup: subclass juga perlu cara untuk menulis ulang perilaku yang diwarisi.
</div>

<div class="tip-box">
Inilah yang akan diselesaikan Pertemuan 7 lewat overriding: subclass menulis ulang method superclass untuk memberi perilaku yang berbeda, tanpa mengubah kode <code>Account</code> maupun <code>Bank</code> sama sekali.
</div>

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Object-Oriented Programming: Inheritance

Oracle Java Tutorials: "Inheritance", "The Object Class", "Using the Keyword super"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 6

---

## Diskusi

Perhatikan kembali `SavingsAccount` dan `CheckingAccount` yang baru saja kamu bangun: apakah keduanya sebaiknya juga punya subclass masing-masing (misalnya `SavingsAccount` dipecah lagi menjadi jenis dengan bunga tetap dan bunga berjenjang)? Beri satu contoh subclass yang menurutmu masuk akal beserta atribut barunya, atau jelaskan mengapa pemecahan lebih lanjut tidak diperlukan untuk Bank Mini.
