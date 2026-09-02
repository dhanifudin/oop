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

Pertemuan 10: **Polimorfisme dan Exception Handling**

Satu titik kode, banyak perilaku; kegagalan yang tidak bisa diabaikan

---

## Yang Akan Kamu Pelajari

- Polimorfisme: satu pemanggilan method yang sama menjalankan perilaku berbeda tergantung objek penerimanya, ditentukan saat program berjalan
- `instanceof` dengan pattern matching, untuk memeriksa sekaligus melakukan downcasting yang aman
- Exception handling: `throw`, `try`/`catch`, dan membuat exception kustom lewat `extends Exception`
- Penerapan pada Bank Mini: `withdraw()` melempar `InsufficientBalanceException`, `Bank.processMonthEnd()` memproses rekening secara polimorfik

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 10.
</div>

---

<!-- _class: divider -->

# Bagian 1
## Polimorfisme

---

## Satu Array, Berbagai Jenis Objek

Bayangkan `Shape[] shapes` menyimpan campuran `Circle` dan `Square`. Tanpa polimorfisme, kode yang menghitung luas tiap elemen harus memeriksa jenisnya satu per satu: `if (s instanceof Circle) { ... } else if (s instanceof Square) { ... }`, masing-masing cabang memanggil rumus luas yang berbeda.

<div class="warn-box">
Setiap kali ditambahkan jenis bentuk baru, cabang <code>if</code>/<code>else</code> ini harus dicari dan ditambahi lagi, di setiap tempat kode semacam ini pernah ditulis.
</div>

---

## Mengapa Ini Penting?

Bayangkan aplikasi e-commerce dengan puluhan jenis produk (buku, elektronik, makanan), masing-masing punya cara sendiri menghitung ongkos kirim. Tanpa polimorfisme, method yang memproses pesanan berisi puluhan cabang `if (product instanceof Book) ... else if (product instanceof Electronics) ...`. Menambah satu jenis produk baru berarti mencari dan mengubah SETIAP method semacam ini di seluruh aplikasi, satu saja terlewat menjadi bug yang baru ketahuan saat pelanggan komplain ongkos kirimnya salah.

<div class="term-box">
Polimorfisme membalik tanggung jawab ini: kode pemanggil cukup memanggil <code>product.calculateShippingCost()</code>, objek itu sendiri yang tahu cara menghitungnya. Menambah jenis produk baru tidak pernah mengubah satu baris pun kode yang sudah ada, sejalan dengan Open/Closed Principle yang dibahas lebih lanjut pada Pertemuan 11.
</div>

---

## Dynamic Dispatch: Diputuskan Saat Program Berjalan

![Satu titik pemanggilan area() yang diselesaikan secara berbeda-beda saat program berjalan](../assets/illustrations/polymorphic-dispatch.svg)

<div class="term-box">
<b>Polimorfisme</b> adalah kemampuan satu pemanggilan method yang sama, dipanggil lewat tipe superclass atau interface, untuk menjalankan versi milik objek yang sebenarnya saat program berjalan (<i>dynamic dispatch</i>). Mekanisme ini sebenarnya sudah bekerja sejak method overriding dipelajari di Pertemuan 7, di sini diberi nama formalnya.
</div>

---

## Kapan Tetap Butuh Tahu Tipe Konkret

Kadang kode tetap perlu memeriksa tipe konkret suatu objek, misalnya untuk memanggil kemampuan yang hanya dimiliki sebagian subclass (bukan seluruh superclass).

<div class="term-box">
<code>instanceof</code> dengan <i>pattern matching</i> (<code>if (obj instanceof TipeTertentu variabel)</code>) memeriksa tipe objek sekaligus langsung menyediakan variabel bertipe spesifik itu, menggantikan cara lama yang memerlukan casting manual terpisah setelah pengecekan.
</div>

<div class="warn-box">
Terlalu banyak <code>instanceof</code> yang memeriksa tipe konkret satu per satu adalah tanda polimorfisme belum dimanfaatkan sepenuhnya. Gunakan <code>instanceof</code> secukupnya, terutama untuk memeriksa <i>interface</i> yang hanya diterapkan sebagian subclass, seperti dicontohkan pada Bagian 3.
</div>

---

<!-- _class: divider -->

# Bagian 2
## Exception Handling

---

## Ketika Kegagalan Didiamkan Begitu Saja

Pertemuan 3 menunjukkan `Thermostat.setTemperature()` yang diam-diam membatasi (<i>clamp</i>) nilai di luar jangkauan, alih-alih menolaknya. Cara ini praktis, tetapi pemanggil tidak pernah tahu bahwa nilai yang dikirimnya sebenarnya diubah secara diam-diam.

<div class="warn-box">
Kegagalan yang didiamkan begitu saja bisa menimbulkan bug yang baru terlihat jauh setelah penyebab sebenarnya terjadi, di tempat yang sama sekali berbeda dari sumbernya.
</div>

---

## Mengapa Ini Penting?

Bayangkan sebuah sistem yang diam-diam mengabaikan input tidak valid alih-alih menolaknya secara tegas, misalnya jumlah transfer negatif yang dibulatkan menjadi nol tanpa pemberitahuan. Beberapa minggu kemudian, tim menemukan laporan keuangan yang tidak seimbang, tetapi penyebabnya sudah lama tenggelam di antara ribuan transaksi lain, sangat sulit ditelusuri kembali ke baris kode yang sebenarnya bermasalah.

<div class="term-box">
Exception membuat kegagalan mustahil diabaikan begitu saja: untuk <i>checked exception</i>, compiler memaksa kode pemanggil menanganinya secara eksplisit. Masalah terungkap tepat di titik ia terjadi, bukan menyusup diam-diam ke bagian program yang jauh dan sulit dilacak.
</div>

---

## throw, try, catch

![Sebuah exception menghentikan method yang melemparnya dan diteruskan ke atas hingga tertangkap](../assets/illustrations/exception-throw-catch.svg)

<div class="term-box">
Sebuah method melempar (<code>throw</code>) objek exception ketika menemui kondisi yang tidak bisa ditangani secara wajar, menghentikan eksekusinya saat itu juga. Kode pemanggil membungkus pemanggilan dalam blok <code>try</code>, lalu menangani exception yang mungkin dilempar lewat blok <code>catch</code>.
</div>

---

## Membuat Exception Kustom

![Exception, InvalidTemperatureException, dan Thermostat yang melemparnya](../assets/uml/p10-invalidtemperature-exception.png)

<div class="term-box">
Exception kustom dibuat dengan mendeklarasikan kelas yang meng-<code>extends</code> <code>Exception</code>, biasanya hanya berisi konstruktor yang meneruskan pesan galat ke konstruktor superclass-nya lewat <code>super(pesan)</code>. Nama kelasnya sendiri sudah menjelaskan jenis kegagalan yang terjadi, jauh lebih jelas dibandingkan sekadar nilai <code>boolean</code> atau <code>null</code>.
</div>

---

<!-- _class: divider -->

# Bagian 3
## Menerapkan ke Bank Mini

---

## withdraw() Melempar InsufficientBalanceException

![Exception, InsufficientBalanceException, dan Account yang melemparnya](../assets/uml/p10-insufficientbalance-exception.png)

Sejauh ini, `withdraw()` diam-diam mengembalikan `false` ketika saldo tidak mencukupi, persis risiko yang dibahas pada Bagian 2. `withdraw()` kini melempar `InsufficientBalanceException`, kode pemanggil wajib menanganinya lewat `try`/`catch`, tidak bisa lagi lupa memeriksa hasilnya.

---

## processMonthEnd(): Polimorfisme pada Bank Mini

![h:300 Account sebagai kelas abstrak, SavingsAccount meng-implement interface InterestBearing](../assets/uml/p09-account-abstract.png)

`Bank.processMonthEnd()` memproses seluruh rekening secara polimorfik lewat `monthlyFee()`. Hanya rekening yang meng-implement `InterestBearing` yang mendapat `applyInterest()`, diperiksa lewat `instanceof InterestBearing`, bukan `instanceof SavingsAccount`, sehingga rekening berbunga jenis baru pun otomatis ikut terproses tanpa mengubah kode ini sama sekali.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Exception Handling, Polymorphism, Interfaces

Oracle Java Tutorials: "Polymorphism", "Exceptions"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 10

---

## Diskusi

`processMonthEnd()` memeriksa `instanceof InterestBearing`, bukan `instanceof SavingsAccount`, supaya jenis rekening berbunga baru otomatis ikut terproses tanpa mengubah method ini. Jelaskan dengan kata-katamu sendiri: apa yang akan terjadi (dan kode apa yang harus diubah) apabila pengecekan itu ditulis sebagai `instanceof SavingsAccount`, lalu Bank Mini menambahkan jenis rekening berbunga baru bernama `DepositAccount`?
