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

Pertemuan 9: **Kelas Abstrak dan Interface**

Mendeklarasikan kontrak yang wajib dipenuhi subclass

---

## Yang Akan Kamu Pelajari

- Kelas abstrak (`abstract class`): kelas yang tidak boleh diinstansiasi langsung, hanya boleh menjadi superclass
- Method abstrak: dideklarasikan tanpa isi, wajib diimplementasikan tiap subclass konkret
- Interface: kontrak method yang bisa diterapkan lintas hierarki kelas lewat `implements`
- Perbedaan mendasar antara kelas abstrak dan interface, dan kapan memilih yang mana
- Penerapan pada Bank Mini: `Account` menjadi abstract, interface `InterestBearing` untuk rekening berbunga

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 9.
</div>

---

<!-- _class: divider -->

# Bagian 1
## Kelas Abstrak

---

## Superclass yang Tidak Boleh Diinstansiasi

Bayangkan kelas `Shape` sebagai superclass umum untuk `Circle` dan `Square`. Setiap bentuk pasti punya luas, tetapi rumus luasnya berbeda-beda tergantung jenis bentuknya. Tidak ada satu "bentuk generik" yang masuk akal untuk diinstansiasi langsung, `Shape` hanya masuk akal sebagai superclass.

<div class="term-box">
<b>Kelas abstrak</b> (<code>abstract class</code>) tidak boleh diinstansiasi langsung lewat <code>new</code>, ia hanya boleh menjadi superclass. Dideklarasikan dengan kata kunci <code>abstract</code> pada kelasnya.
</div>

---

## Mengapa Ini Penting?

Bayangkan sebuah framework GUI dipakai oleh ratusan aplikasi berbeda: setiap komponen wajib tahu cara menggambar dirinya sendiri, tetapi "komponen generik" yang belum tahu cara menggambar apa pun tidak pernah boleh benar-benar dibuat. Tanpa cara memastikan hal ini, sebuah objek yang belum lengkap bisa saja lolos dibuat, dan galatnya baru muncul jauh kemudian, saat method yang belum diimplementasikan itu benar-benar dipanggil pengguna.

<div class="term-box">
Kelas abstrak memindahkan kesalahan ini dari saat program berjalan ke saat program dikompilasi: subclass yang belum mengimplementasikan seluruh method abstrak warisannya tidak akan pernah bisa diinstansiasi sama sekali, compiler yang menolaknya, bukan pengguna aplikasi yang menemukannya belakangan. Inilah sebabnya kelas abstrak menjadi fondasi banyak framework dan library besar, dari GUI toolkit sampai driver database.
</div>

---

## Method Abstrak

![Shape sebagai kelas abstrak, Circle dan Square mengimplementasikan area()](../assets/uml/p09-shape-abstract.png)

<div class="term-box">
<b>Method abstrak</b> hanya dideklarasikan tanda tangannya (nama, parameter, tipe kembalian), tanpa isi sama sekali. Setiap subclass konkret (yang bisa diinstansiasi) wajib menyediakan isinya sendiri, atau compiler akan menampilkan galat.
</div>

---

## Subclass Konkret vs Kelas Abstrak

<div class="warn-box">
Sebuah subclass dari kelas abstrak tetap ikut menjadi abstrak (dan tidak bisa diinstansiasi) apabila ia belum mengimplementasikan seluruh method abstrak warisannya. Hanya subclass yang sudah mengimplementasikan semuanya yang menjadi kelas konkret.
</div>

<div class="tip-box">
Kelas abstrak boleh tetap memiliki method biasa (dengan isi lengkap) selain method abstraknya, persis seperti superclass pada umumnya. Subclass mewarisi method biasa itu apa adanya, sama seperti inheritance yang sudah dipelajari sebelumnya.
</div>

---

<!-- _class: divider -->

# Bagian 2
## Interface

---

## Kontrak Lintas Hierarki Kelas

Kelas abstrak cocok ketika beberapa kelas memang berbagi satu superclass yang masuk akal. Namun terkadang beberapa kelas yang sama sekali tidak berkerabat butuh kemampuan yang sama, misalnya "bisa dibandingkan" atau "bisa disimpan ke berkas", tanpa harus berbagi satu superclass.

<div class="term-box">
<b>Interface</b> mendeklarasikan kontrak method (tanda tangan tanpa isi) yang wajib dipenuhi kelas mana pun yang menyatakan <code>implements</code> terhadapnya, tanpa mewajibkan hubungan <code>extends</code> sama sekali.
</div>

---

## Mengapa Ini Penting?

Bayangkan sebuah tim besar membangun sistem pembayaran: satu tim menulis kode yang memproses pembayaran, tim lain menulis implementasi untuk kartu kredit, tim lain lagi untuk e-wallet, dan ada pula tim yang menulis pengujian otomatis. Tanpa kontrak yang jelas, ketiga tim itu harus terus-menerus berkoordinasi setiap kali ada perubahan kecil pada salah satu bagian.

<div class="term-box">
Interface memungkinkan tim yang memproses pembayaran bergantung hanya pada kontrak (method apa saja yang tersedia), bukan pada implementasi konkretnya. Implementasi boleh berubah, ditambah, atau bahkan diganti dengan versi tiruan untuk pengujian (disebut mock), tanpa mengubah kode yang memakainya. Prinsip inilah yang mendasari salah satu prinsip SOLID, Dependency Inversion Principle, dibahas lebih lanjut pada Pertemuan 11.
</div>

---

## Satu Kelas, Banyak Interface

<div class="term-box">
Berbeda dari kelas abstrak (sebuah kelas hanya boleh <code>extends</code> satu superclass), sebuah kelas boleh meng-<code>implements</code> banyak interface sekaligus. Interface cocok dipakai untuk kemampuan tambahan yang berlaku lintas hierarki kelas yang berbeda-beda.
</div>

<div class="warn-box">
Kelas yang menyatakan <code>implements</code> terhadap sebuah interface wajib mengimplementasikan seluruh method di dalamnya. Melewatkan satu saja akan membuat compiler menampilkan galat.
</div>

---

## Kelas Abstrak vs Interface

| | Kelas Abstrak | Interface |
|---|---|---|
| Kata kunci | `extends` | `implements` |
| Jumlah per kelas | Hanya satu | Boleh banyak sekaligus |
| Atribut dan method biasa | Boleh punya | Tidak (hanya kontrak method) |
| Cocok dipakai untuk | Superclass yang masuk akal bagi seluruh subclass | Kemampuan lintas hierarki kelas yang berbeda-beda |

---

<!-- _class: divider -->

# Bagian 3
## Menerapkan ke Bank Mini

---

## Account Menjadi Kelas Abstrak

Tidak ada satu pun `Account` polos yang pernah dibuat langsung di Bank Mini, semua instansiasi selalu berupa `SavingsAccount` atau `CheckingAccount`. Ini pertanda bahwa `Account` sebaiknya menjadi kelas abstrak, dengan method abstrak `monthlyFee()` yang wajib diimplementasikan setiap jenis rekening dengan besaran biayanya masing-masing.

---

## InterestBearing, Interface untuk Rekening Berbunga

![h:300 Account sebagai kelas abstrak, SavingsAccount meng-implement interface InterestBearing](../assets/uml/p09-account-abstract.png)

Hanya rekening yang menghasilkan bunga yang membutuhkan `applyInterest()`, `CheckingAccount` tidak membutuhkannya sama sekali. Daripada menambah method itu ke `Account` (yang berarti seluruh subclass mewarisinya, termasuk yang tidak relevan), method ini dideklarasikan sebagai interface `InterestBearing` tersendiri, hanya diterapkan pada `SavingsAccount`.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Object-Oriented Programming: Creating Abstract Superclasses and Concrete Subclasses, Interfaces

Oracle Java Tutorials: "Abstract Methods and Classes", "Interfaces"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 9

---

## Diskusi

`SavingsAccount` sekarang punya dua "kontrak" sekaligus: mewarisi `Account` (kelas abstrak) lewat `extends`, dan meng-implement `InterestBearing` (interface) lewat `implements`. Jelaskan dengan kata-katamu sendiri apa perbedaan mendasar antara kedua jenis kontrak ini, lalu berikan satu contoh kemampuan baru (selain bunga) yang menurutmu lebih cocok dideklarasikan sebagai interface baru dibandingkan ditambahkan langsung ke `Account`.
