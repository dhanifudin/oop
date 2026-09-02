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
  table.small {
    font-size: 0.75em;
  }
  th, td {
    padding: 4px 10px;
  }
  th {
    background: #1d4ed8;
    color: #fff;
  }
  code {
    background: #f1f5f9;
    color: #0f172a;
  }
  pre {
    font-size: 0.65em;
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
    gap: 24px;
  }
  .cols > div {
    flex: 1;
  }
  .flow {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 10px;
    margin-top: 30px;
    flex-wrap: wrap;
  }
  .flow .box {
    background: #1d4ed8;
    color: #fff;
    padding: 12px 18px;
    border-radius: 8px;
    font-weight: bold;
    font-size: 0.85em;
  }
  .flow .arrow {
    font-size: 1.4em;
    color: #1d4ed8;
  }
  .footnote {
    font-size: 0.55em;
    color: #64748b;
    position: absolute;
    bottom: 20px;
  }
  img {
    display: block;
    margin: 0 auto;
  }
---

<!-- _class: lead -->

# Pemrograman Berbasis Objek
## RTI253007 &nbsp;|&nbsp; D-IV Teknik Informatika

Pertemuan 11: **SOLID Principles**

Lima prinsip biar rancangan kelasmu tahan banting menghadapi perubahan

---

## Yang Akan Kamu Pelajari

- Kenapa rancangan kelas bisa "membusuk" seiring waktu
- Lima prinsip SOLID: SRP, OCP, LSP, ISP, DIP
- Satu studi kasus yang sama, direfaktor tahap demi tahap

<div class="term-box">
<b>Prasyarat:</b> materi hari ini memakai inheritance, kelas abstrak, interface, dan polymorfisme yang udah kamu pelajari di pertemuan-pertemuan sebelumnya. SOLID bukan konsep baru, ini cara memakai alat-alat itu dengan lebih disiplin.
</div>

---

<!-- _class: divider -->

# Bagian 1
## Mengapa Rancangan Bisa Membusuk

---

## Tiga Gejala Rancangan yang Buruk

<div class="warn-box">
<b>Rigidity (kaku):</b> satu perubahan kecil memaksa kamu mengedit banyak kelas yang sebenarnya nggak berhubungan.
</div>

<div class="warn-box">
<b>Fragility (rapuh):</b> ubah satu bagian kode, eh bagian lain yang kelihatannya nggak berkaitan malah ikut rusak.
</div>

<div class="warn-box">
<b>Immobility (susah dipakai ulang):</b> sebuah kelas terlalu terikat sama konteksnya, jadi susah dipakai di tempat lain.
</div>

---

## Studi Kasus: `OrderProcessor` yang Sudah Membengkak

Bayangkan sebuah aplikasi pemrosesan pesanan yang sudah berjalan cukup lama. Satu kelas, `OrderProcessor`, sekarang menangani semua hal terkait pesanan sekaligus.

![Diagram kelas OrderProcessor sebelum refactoring](../assets/uml/p11-before.png)

---

## Lima Prinsip SOLID

<table class="small">
<tr><th>Huruf</th><th>Nama</th><th>Inti</th></tr>
<tr><td>S</td><td>Single Responsibility</td><td>Satu kelas, satu alasan untuk berubah</td></tr>
<tr><td>O</td><td>Open/Closed</td><td>Terbuka untuk ekstensi, tertutup untuk modifikasi</td></tr>
<tr><td>L</td><td>Liskov Substitution</td><td>Subclass harus bisa menggantikan superclass-nya</td></tr>
<tr><td>I</td><td>Interface Segregation</td><td>Interface kecil dan spesifik, bukan satu interface besar</td></tr>
<tr><td>D</td><td>Dependency Inversion</td><td>Bergantung pada abstraksi, bukan detail konkret</td></tr>
</table>

Sepanjang slide ini, kita perbaiki `OrderProcessor` satu prinsip demi satu prinsip. Kode lengkapnya ada di jobsheet.

---

<!-- _class: divider -->

# Bagian 2
## SRP: Single Responsibility Principle

---

## "Satu Alasan untuk Berubah"

<div class="term-box">
Sebuah kelas sebaiknya cuma punya SATU alasan untuk berubah. Kalau kamu bisa membayangkan lebih dari satu kelompok kebutuhan yang beda-beda, yang masing-masing bisa memicu perubahan kelas ini, itu tandanya SRP dilanggar.
</div>

`OrderProcessor` versi awal berubah kalau aturan diskon berubah, atau kalau cara penyimpanan berubah, atau kalau format struknya berubah. Tiga alasan berbeda, tapi ditumpuk jadi satu kelas.

<div class="tip-box">
Heuristik cepat: coba deskripsikan tugas kelasnya dalam satu kalimat. Kalau butuh kata "dan" untuk menyambung dua tugas berbeda, curigai SRP.
</div>

---

## Sesudah: Dipecah per Tanggung Jawab

![Diagram kelas: OrderProcessor mengorkestrasi tiga kolaborator](../assets/uml/p11-srp.png)

`OrderProcessor` sekarang cuma mengorkestrasi `DiscountCalculator`, `OrderRepository`, dan `ReceiptPrinter`, dia sendiri nggak perlu lagi tahu detail perhitungan, penyimpanan, atau formatnya masing-masing.

---

<!-- _class: divider -->

# Bagian 3
## OCP: Open/Closed Principle

---

## "Terbuka untuk Ekstensi, Tertutup untuk Modifikasi"

<div class="term-box">
Kalau ada kebutuhan baru, idealnya kamu cukup MENAMBAH kode baru, bukan MENGEDIT kode lama yang udah teruji.
</div>

```java
if (customer.getType().equals("REGULAR")) { ... }
else if (customer.getType().equals("VIP")) { ... }
else { ... }
```

<div class="warn-box">
Tiap ada tipe customer baru, kamu terpaksa buka lagi method ini dan nambah cabang <code>else if</code>. Kode lama yang udah jalan ikut tersentuh, dan itu selalu berisiko bikin sesuatu yang lain jadi rusak.
</div>

---

## Sesudah: Strategi lewat Interface

![Diagram kelas: DiscountPolicy dan implementasinya](../assets/uml/p11-ocp.png)

<div class="tip-box">
Nambah tipe customer baru cuma butuh SATU kelas baru yang mengimplementasikan <code>DiscountPolicy</code>, plus satu baris pendaftaran. <code>DiscountCalculator</code> dan <code>OrderProcessor</code> sama sekali nggak perlu disentuh.
</div>

---

<!-- _class: divider -->

# Bagian 4
## LSP: Liskov Substitution Principle

---

## "Subclass Harus Bisa Menggantikan Superclass-nya"

<div class="term-box">
Kode yang memakai objek bertipe <code>Order</code> seharusnya tetap bekerja benar, walau objek yang sebenarnya diberikan adalah instance subclass <code>Order</code> apa pun.
</div>

```java
for (Order o : orders) {
    System.out.println(o.ship());   // meledak di DigitalOrder!
}
```

<div class="warn-box">
Kode yang manggil ini memperlakukan semua <code>Order</code> sama (langsung manggil <code>ship()</code>), tapi <code>DigitalOrder</code> bikin kaget dengan <code>UnsupportedOperationException</code>. Substitusinya gagal, ini pelanggaran LSP.
</div>

---

## Sesudah: Pisahkan Kemampuan lewat Interface

![Diagram kelas: Shippable memisahkan PhysicalOrder dari DigitalOrder](../assets/uml/p11-lsp.png)

<div class="tip-box">
Aturan praktis: kalau ada subclass yang meng-override method cuma buat melempar <code>UnsupportedOperationException</code>, curigai LSP.
</div>

---

<!-- _class: divider -->

# Bagian 5
## ISP: Interface Segregation Principle

---

## "Interface Kecil, Bukan Interface Gemuk"

![Diagram: interface gemuk vs tiga interface kecil](../assets/uml/p11-isp.png)

<div class="tip-box">
ISP bisa dibilang SRP-nya interface: satu interface, satu kemampuan. <code>InvoicePrinter</code> nggak lagi dipaksa mengimplementasikan method yang nggak relevan buatnya.
</div>

---

<!-- _class: divider -->

# Bagian 6
## DIP: Dependency Inversion Principle

---

## "Bergantung pada Abstraksi, Bukan Detail"

```java
private OrderRepository orderRepository = new OrderRepository();
```

<div class="warn-box">
<code>OrderProcessor</code> (kelas tingkat tinggi, isinya logika bisnis) langsung bergantung pada detail implementasi yang konkret (cara nulis ke berkas). Begitu cara penyimpanannya mau diganti, atau kamu mau menguji <code>OrderProcessor</code> tanpa nyentuh berkas sama sekali, kamu jadi terjebak.
</div>

---

## Sesudah: Bergantung pada Interface, Disuntik lewat Constructor

![Diagram kelas: OrderProcessor bergantung pada interface OrderRepository](../assets/uml/p11-dip.png)

<div class="tip-box">
Ganti <code>FileOrderRepository</code> jadi <code>InMemoryOrderRepository</code>? Cukup ubah SATU baris di <code>Main</code>. <code>OrderProcessor</code> nggak perlu tahu implementasi mana yang dipakai, dia cuma bergantung pada interface <code>OrderRepository</code> (constructor injection).
</div>

---

## Rekap: Satu Proyek, Lima Prinsip

![Diagram kelas lengkap setelah refactoring SOLID](../assets/uml/p11-final.png)

Satu studi kasus, lima prinsip, dan semuanya saling melengkapi.

---

<!-- _class: lead -->

# Referensi & Diskusi

Robert C. Martin, *Agile Software Development: Principles, Patterns, and Practices*

Robert C. Martin, *Clean Architecture*

Jobsheet Praktikum Pertemuan 11 tersedia di `jobsheets/id/pertemuan-11-solid-principles.md`

Diskusi: prinsip SOLID mana yang paling sering kamu langgar tanpa sadar di kode UTS-mu?
