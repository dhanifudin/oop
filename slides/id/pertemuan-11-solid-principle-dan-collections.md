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

Pertemuan 11: **SOLID Principle dan Collections**

Lima prinsip desain kelas yang baik

---

## Yang Akan Kamu Pelajari

- Lima prinsip SOLID agar kelas mudah dipahami, diperluas, dan diuji
- Cara mengenali kelas yang melanggar salah satu prinsip itu
- Cara menyimpan data yang jumlahnya berubah-ubah (`ArrayList` dan `Map`)
- Penerapan kelima prinsip dalam satu sistem pemrosesan pesanan

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 11.
</div>

---

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Single Responsibility dan Open/Closed
- **Sesi 2 (50')**: Liskov Substitution dan Interface Segregation
- **Sesi 3 (50')**: Dependency Inversion, lalu `ArrayList` dan `Map`
- **Sesi 4 (50')**: Studi kasus, sistem pemrosesan pesanan

---

<!-- _class: divider -->

# Bagian 1
## SOLID: Single Responsibility dan Open/Closed

Sesi 1 dari 4

---

## Lima Prinsip Desain Kelas yang Baik

<div class="term-box">
<b>SOLID</b> adalah lima prinsip agar kelas mudah dipahami, diperluas, dan diuji. Tiap huruf mewakili satu prinsip: <b>S</b>ingle Responsibility, <b>O</b>pen/Closed, <b>L</b>iskov Substitution, <b>I</b>nterface Segregation, <b>D</b>ependency Inversion.
</div>

Beberapa prinsip sudah kamu pakai tanpa sadar. Pertemuan ini memberi nama resminya.

---

## Single Responsibility Principle (SRP)

Di restoran, koki, kasir, dan pelayan punya tugas masing-masing. Kesalahan di dapur tidak ikut mengacaukan pembayaran.

![h:270 Satu kelas dengan tiga tanggung jawab, dipisah menjadi tiga kelas masing-masing satu tanggung jawab](../assets/illustrations/srp-split.svg)

<div class="term-box">
Satu kelas sebaiknya punya satu tanggung jawab, yaitu satu alasan untuk berubah. <b>Satu kelas, satu tugas.</b>
</div>

---

## Mengapa Ini Penting?

Bayangkan kelas `Report` yang menghitung total, memformat tampilan, dan mengirim email sekaligus. Tim yang mengubah format tampilan bisa tanpa sengaja merusak baris perhitungan. Semuanya ada di satu file.

<div class="term-box">
Kelas dengan satu tugas lebih aman diubah. Mengganti cara kirim email tidak menyentuh perhitungan.
</div>

---

## Contoh Kode: `Report` Dipisah Jadi Tiga Kelas

```java
class TotalCalculator { double calculate(Report r) { /* ... */ return 0; } }
class ReportFormatter { String format(Report r) { /* ... */ return ""; } }
class ReportMailer    { void send(Report r) { /* ... */ } }
```

1. Menghitung total ada di `TotalCalculator`.
2. Memformat tampilan ada di `ReportFormatter`.
3. Mengirim email ada di `ReportMailer`. Tiap kelas hanya punya satu alasan untuk berubah.

---

## Kesalahan Umum: Kelas Serba Bisa

<div class="warn-box">
<b>Salah:</b> <code>class UserManager</code> berisi <code>validate()</code>, <code>saveToDatabase()</code>, dan <code>sendEmail()</code> sekaligus. Satu kelas mengerjakan tiga tugas.
</div>

**Benar:** tiga kelas kecil, `UserValidator`, `UserStore`, dan `EmailSender`. Tiap kelas satu tugas. Mengganti cara kirim email kini tidak menyentuh kode validasi.

---

## Open/Closed Principle (OCP)

Stop kontak menerima alat apa pun. Alat baru tinggal dicolokkan, instalasi rumah tidak dibongkar.

![h:270 PaymentMethod.pay() dengan if/else per tipe, dibandingkan subclass baru GoPay yang ditambahkan tanpa mengubah kode lama](../assets/illustrations/ocp-extend-not-modify.svg)

<div class="term-box">
<b>Open/Closed Principle</b>: kelas terbuka untuk diperluas, tertutup untuk diubah. Fitur baru ditambah lewat kelas baru. <b>Kelas baru, bukan ubah kode lama.</b>
</div>

---

## Mengapa Ini Penting?

Bayangkan sistem pembayaran toko daring dengan satu method besar berisi `if`/`else` untuk tiap jenis pembayaran. Menambah GoPay berarti menyisipkan cabang baru ke method itu. Salah tempat sedikit, cabang kartu kredit yang sudah benar ikut rusak.

<div class="term-box">
Makin banyak jenis pembayaran, makin besar method itu dan makin besar risikonya. Dengan kelas baru, kode lama yang sudah teruji tidak disentuh.
</div>

---

## Contoh Kode: Menambah GoPay Tanpa Mengubah Kode Lama

```java
// Sebelum: tiap jenis baru mengubah method ini
if (type.equals("CREDIT_CARD")) { chargeCard(amount); }
else if (type.equals("BANK_TRANSFER")) { transferBank(amount); }

// Sesudah: cukup kelas baru
class GoPay extends PaymentMethod {
    void pay(double amount) { /* logika GoPay */ }
}
```

`PaymentMethod`, `CreditCard`, dan `BankTransfer` sudah ada. Menambah `GoPay` tidak mengubah satu baris pun kode mereka.

---

## Kesalahan Umum: Menambah Cabang `if` untuk Tiap Jenis Baru

<div class="warn-box">
<b>Salah:</b> tiap jenis diskon baru menambah satu cabang di <code>calculateDiscount()</code>.
</div>

```java
if (type.equals("REGULAR")) { return amount * 5 / 100; }
else if (type.equals("WHOLESALE")) { return amount * 20 / 100; }
else if (type.equals("STUDENT")) { return amount * 10 / 100; }   // cabang baru lagi
```

**Benar:** satu interface `DiscountPolicy` dengan method `calculate(amount)`. Tiap jenis diskon menjadi kelas sendiri, misalnya `StudentDiscountPolicy`. Jenis baru tidak menyentuh method lama.

---

## Latihan

Prinsip mana yang dilanggar: Single Responsibility atau Open/Closed?

1. `UserManager` memvalidasi input, menyimpan ke database, dan mengirim email.
2. Jenis pembayaran baru mengharuskan menambah cabang `if` di method `pay()`.
3. `InvoicePrinter` mencetak faktur sekaligus menghitung pajaknya.

---

## Jawaban Latihan

1. **Single Responsibility.** Tiga tugas tercampur dalam satu kelas.
2. **Open/Closed.** Method lama harus diubah tiap ada jenis pembayaran baru.
3. **Single Responsibility.** Mencetak dan menghitung pajak adalah dua tugas. Aturan pajak yang berubah akan menyentuh kode cetak.

---

## Rangkuman Bagian 1

- Single Responsibility: satu kelas, satu tugas.
- Open/Closed: kelas baru, bukan ubah kode lama.
- Keduanya membuat perubahan kecil tetap kecil. Hanya satu kelas yang disentuh.

Selanjutnya: Bagian 2 membahas dua prinsip tentang subclass dan interface, Liskov Substitution dan Interface Segregation.

---

<!-- _class: divider -->

# Bagian 2
## SOLID: Liskov Substitution dan Interface Segregation

Sesi 2 dari 4

---

## Liskov Substitution Principle (LSP)

Kamu memesan ojek, dan yang datang motor mana pun. Penggantinya harus tetap bisa mengantarmu.

![h:270 List<Bird> memanggil fly() untuk tiap elemen, Sparrow dan Duck berhasil, Penguin melempar exception](../assets/illustrations/lsp-substitution.svg)

<div class="term-box">
<b>Liskov Substitution Principle</b>: subclass harus bisa menggantikan superclass-nya di mana pun tanpa membuat program salah. <code>Sparrow</code> dan <code>Duck</code> di gambar memenuhinya, <code>Penguin</code> tidak. <b>Pengganti harus tetap bisa.</b>
</div>

---

## Mengapa Ini Penting?

Kode yang memanggil method lewat tipe superclass percaya pada janji superclass itu. Bila satu subclass diam-diam mengingkari janji, program gagal di tempat yang jauh dari subclass itu. Penyebabnya sulit dilacak.

<div class="term-box">
Error seperti ini tidak ditolak compiler. Ia baru muncul saat program berjalan, mungkin di tangan pengguna.
</div>

---

## Kesalahan Umum: Subclass Mengingkari Janji

<div class="warn-box">
<b>Salah:</b> <code>Penguin extends Bird</code> meng-override <code>fly()</code> untuk melempar <code>UnsupportedOperationException</code>, karena penguin tidak bisa terbang.
</div>

```java
for (Bird b : birds) {
    b.fly();   // error begitu b ternyata Penguin
}
```

**Benar:** `Penguin` tidak punya `fly()` sama sekali. Kemampuan terbang dipindah ke interface `Flyable` (slide berikut). Alasannya: kode di atas mengira semua `Bird` bisa `fly()`. `Penguin` tidak bisa menggantikan `Bird`.

---

## Perbaikan: Pisahkan Kemampuan Terbang

<div class="term-box">
Pindahkan <code>fly()</code> ke interface kecil, misalnya <code>Flyable</code>. Hanya <code>Sparrow</code> dan <code>Duck</code> yang <code>implements Flyable</code>. <code>Penguin</code> tidak.
</div>

```java
interface Flyable { void fly(); }
class Sparrow extends Bird implements Flyable { ... }
class Penguin extends Bird { /* tidak implements Flyable */ }
```

Pemanggil kini memeriksa kemampuannya: `if (b instanceof Flyable f) f.fly();`. Bentuk `instanceof Flyable f` memeriksa tipe sekaligus memberi variabel `f` bertipe `Flyable`, tanpa cast. Pola interface kecil ini muncul lagi di Bagian 4 (`Shippable`).

---

## Interface Segregation Principle (ISP)

Remote TV yang hanya perlu menyalakan dan mematikan tidak butuh 50 tombol channel.

![h:230 Chargeable diimplementasikan Phone dan ElectricCar, dua hierarki yang terpisah](../assets/uml/p09-chargeable.png)

<div class="term-box">
<b>Interface Segregation Principle</b>: interface sebaiknya kecil dan fokus, sehingga kelas tidak dipaksa mengisi method yang tidak relevan. Contohnya <code>Chargeable</code> di atas: satu method <code>charge()</code>, diisi <code>Phone</code> dan <code>ElectricCar</code> yang tidak berkerabat. <b>Interface kecil, satu kemampuan.</b>
</div>

---

## Mengapa Ini Penting?

Bayangkan `charge()` dan `call()` digabung dalam satu interface besar, `Device`. `ElectricCar` ikut wajib menulis `call()`, padahal mobil listrik tidak bisa menelepon. Pilihannya dua dan keduanya buruk: dibiarkan kosong, atau melempar exception.

<div class="term-box">
Kode yang memegang <code>Device</code> mengira <code>call()</code> berfungsi. Pada <code>ElectricCar</code>, method itu tidak berbuat apa-apa atau menghentikan program. Interface yang gemuk ikut melanggar Liskov.
</div>

---

## Contoh Kode: Memecah Interface yang Gemuk

```java
// Sebelum: satu interface gemuk
interface Device { void charge(); void call(); }

// Sesudah: dua interface kecil
interface Chargeable { void charge(); }
interface Caller { void call(); }
class ElectricCar implements Chargeable { /* hanya charge() */ }
```

1. Sebelum: `ElectricCar` terpaksa menulis `call()` yang tidak masuk akal.
2. Sesudah: dua interface kecil, satu kemampuan masing-masing.
3. `ElectricCar` hanya `implements Chargeable`.

---

## Latihan

Prinsip mana yang dilanggar: Liskov Substitution atau Interface Segregation?

1. `Penguin extends Bird`, tetapi `fly()`-nya melempar exception.
2. `interface Printer` punya `print()`, `scan()`, dan `fax()`. `SimplePrinter implements Printer`, padahal hanya bisa `print()`.
3. `ReadOnlyList extends ArrayList`, tetapi `add()`-nya melempar exception.

---

## Jawaban Latihan

1. **Liskov Substitution.** `Penguin` mengingkari janji `Bird`, jadi tidak bisa menggantikannya.
2. **Interface Segregation.** `SimplePrinter` dipaksa mengisi `scan()` dan `fax()`. Pecah menjadi interface kecil.
3. **Liskov Substitution.** Kode yang memegang `ArrayList` mengira `add()` selalu bisa. `ReadOnlyList` mengingkarinya.

---

## Rangkuman Bagian 2

- Liskov Substitution: pengganti harus tetap bisa.
- Interface Segregation: interface kecil, satu kemampuan.
- Interface yang gemuk memaksa method palsu. Method palsu melanggar Liskov.

Selanjutnya: Bagian 3 membahas prinsip terakhir, Dependency Inversion, lalu dua struktur data untuk menyimpan banyak objek.

---

<!-- _class: divider -->

# Bagian 3
## SOLID: Dependency Inversion, dan Collections

Sesi 3 dari 4

---

## Dependency Inversion Principle (DIP)

Kasir hanya mengenal mesin kartu, bukan bank penerbitnya. Kartu dari bank mana pun bisa dipakai.

![h:270 OrderProcessor bergantung langsung pada MySqlDatabase, dibandingkan bergantung pada interface Repository yang diimplementasikan MySqlDatabase dan MockRepository](../assets/illustrations/dip-invert-dependency.svg)

<div class="term-box">
<b>Dependency Inversion Principle</b>: kelas yang mengatur alur, misalnya <code>OrderProcessor</code>, bergantung pada interface, bukan pada kelas sungguhan seperti <code>MySqlDatabase</code>. <b>Bergantung pada interface, bukan kelas sungguhan.</b>
</div>

---

## Mengapa Ini Penting?

Bayangkan `OrderProcessor` yang langsung memakai kelas `MySqlDatabase`. Pindah ke database lain menjadi sulit. Menguji dengan database tiruan juga sulit. `OrderProcessor` terikat ke MySQL.

<div class="term-box">
Dengan interface <code>Repository</code>, penyimpanan bisa berganti (MySQL, memori, atau tiruan untuk pengujian) tanpa mengubah <code>OrderProcessor</code>.
</div>

---

## Contoh Kode: `OrderProcessor` Bergantung pada Interface

```java
interface Repository { void save(Order order, int total); }

class OrderProcessor {
    private Repository repository;
    OrderProcessor(Repository repository) { this.repository = repository; }
}
```

1. `Repository` hanya menyatakan kemampuan `save(...)`.
2. `OrderProcessor` menerima `Repository` lewat constructor.
3. Kode yang membuat `OrderProcessor` memilih implementasi mana yang dipakai.

---

## Kesalahan Umum: Membuat Sendiri Kelas Sungguhan

<div class="warn-box">
<b>Salah:</b> <code>class OrderProcessor { private MySqlDatabase db = new MySqlDatabase(); }</code>. Kelas ini membuat sendiri objek sungguhannya.
</div>

**Benar:** `OrderProcessor` menerima `Repository` (interface) lewat constructor. Kode yang membuat `OrderProcessor` yang memutuskan implementasi mana yang dipakai.

---

## Masalah: Array Itu Kaku

`InMemoryRepository` harus menyimpan banyak `Order`. Array biasa punya dua masalah.

```java
Order[] orders = new Order[2];                      // ukuran dikunci: 2
orders[0] = new Order("Laptop", 5000000);
orders[1] = new Order("Mouse", 150000);
orders[2] = new Order("Cable", 50000);              // error saat dijalankan
```

1. Ukuran ditentukan di awal. Elemen ketiga menghasilkan `ArrayIndexOutOfBoundsException`.
2. Mencari satu elemen harus memeriksa dari awal, satu per satu.

---

## `ArrayList` dan `Map`

| | Array | `ArrayList` | `Map` |
|---|---|---|---|
| Ukuran | tetap sejak dibuat | bertambah otomatis | bertambah otomatis |
| Cara ambil | lewat indeks | lewat indeks, `get(1)` | lewat kunci, `get("WHOLESALE")` |
| Cocok untuk | jumlah pasti | daftar berurutan | cari langsung lewat kunci |

<div class="term-box">
<b>ArrayList</b> adalah daftar yang ukurannya menyesuaikan otomatis. <b>Map</b> menyimpan pasangan kunci dan nilai, lalu mencari nilai langsung lewat kuncinya. <code>HashMap</code> tidak menjamin urutan; <code>LinkedHashMap</code> menjaga urutan penambahan.
</div>

---

## Contoh Kode: `InMemoryRepository` Memakai `ArrayList`

```java
class InMemoryRepository implements Repository {
    private List<Order> orders = new ArrayList<>();   // daftar yang bisa bertambah

    public void save(Order order, int total) {
        orders.add(order);                            // tidak ada batas ukuran
    }
}
```

1. `List<Order>` berarti daftar ini hanya berisi `Order`.
2. `add(...)` menambah di belakang. Tidak perlu tahu kapasitas lebih dulu.
3. `OrderProcessor` hanya mengenal `Repository`. Ia tidak tahu ada `ArrayList` di dalamnya.

---

## Contoh Kode: `Map` untuk Memilih Policy

```java
Map<String, DiscountPolicy> policies = new HashMap<>();
policies.put("REGULAR", new RegularDiscountPolicy());
policies.put("WHOLESALE", new WholesaleDiscountPolicy());
DiscountPolicy policy = policies.get("WHOLESALE");   // langsung, tanpa looping
```

1. `<String, DiscountPolicy>`: kuncinya jenis pelanggan, nilainya policy (dari Bagian 1).
2. `put(...)` menyimpan satu pasangan kunci dan nilai.
3. `get("WHOLESALE")` langsung mengambil `WholesaleDiscountPolicy`, tanpa memeriksa `"REGULAR"`. Bila kuncinya tidak ada, hasilnya `null`.

---

## Latihan

1. `OrderProcessor` berisi `private MySqlDatabase db = new MySqlDatabase();`. Prinsip mana yang dilanggar?
2. Pilih `ArrayList` atau `Map`: daftar pesanan yang hanya dicetak berurutan dari awal sampai akhir.
3. Pilih `ArrayList` atau `Map`: data pelanggan yang sering dicari lewat nomor pelanggan.

---

## Jawaban Latihan

1. **Dependency Inversion.** Buat interface `Repository`, lalu `OrderProcessor` menerimanya lewat constructor.
2. **`ArrayList`.** Data hanya dibaca berurutan, tidak perlu pencarian lewat kunci.
3. **`Map`.** Nomor pelanggan menjadi kunci, sehingga pencariannya langsung.

---

## Rangkuman Bagian 3

- Dependency Inversion: bergantung pada interface, bukan kelas sungguhan.
- Membuat objek sungguhan dengan `new` di dalam kelas tetap melanggarnya, walau interface-nya ada.
- `ArrayList` untuk daftar yang bertambah. `Map` untuk mencari langsung lewat kunci.

Selanjutnya: Bagian 4 menyatukan kelima prinsip dalam satu studi kasus.

---

<!-- _class: divider -->

# Bagian 4
## Studi Kasus: Kelima Prinsip dalam Satu Sistem

Sesi 4 dari 4

---

## Satu Sistem Pemrosesan Pesanan

Sebuah toko daring punya pesanan fisik (perlu dikirim) dan pesanan digital (tidak perlu dikirim). Diskonnya berbeda tiap jenis pelanggan, dan penyimpanannya suatu hari mungkin pindah dari memori ke berkas.

Pemainnya:

- `Order`, dengan subclass `PhysicalOrder` dan `DigitalOrder`
- `Customer`, punya jenis pelanggan (reguler, grosir)
- `OrderProcessor`, koordinator alur pesanan
- `DiscountCalculator` dan `DiscountPolicy`, menghitung diskon
- `Repository`, menyimpan pesanan; `ReceiptPrinter`, mencetak struk

Sistem kecil ini cukup untuk menunjukkan kelima prinsip bekerja bersama.

---

## `Shippable`: LSP dan ISP Sekaligus

![h:230 Order, PhysicalOrder yang mengimplementasikan Shippable, dan DigitalOrder yang sengaja tidak](../assets/uml/p11-order-shippable.png)

`Shippable` kecil dan fokus, hanya `ship()` (Interface Segregation). `PhysicalOrder` mengimplementasikannya. `DigitalOrder` sengaja tidak, karena pesanan digital tidak pernah dikirim, sehingga tidak dipaksa punya `ship()` palsu (Liskov Substitution).

---

## Contoh Kode: `PhysicalOrder` dan `DigitalOrder`

```java
public class PhysicalOrder extends Order implements Shippable {
    public String ship() {
        return "Shipping " + describe() + "...";
    }
}

public class DigitalOrder extends Order {
    // sengaja tidak mengimplementasikan Shippable
}
```

Hanya `PhysicalOrder` yang bisa dikirim.

---

## `OrderProcessor`: Single Responsibility

![h:270 OrderProcessor mendelegasikan ke DiscountCalculator, Repository, dan ReceiptPrinter](../assets/illustrations/orderprocessor-srp.svg)

`OrderProcessor` tidak menghitung diskon, tidak menyimpan data, dan tidak mencetak apa pun. Tiga tugas itu diserahkan ke `DiscountCalculator`, `Repository`, dan `ReceiptPrinter`. Mengubah cara cetak struk tidak berisiko merusak perhitungan diskon.

---

## Contoh Kode: `OrderProcessor` Hanya Mengoordinasikan

```java
public void processOrder(Customer customer, Order order) {
    int discount = discountCalculator.calculate(customer, order);
    int total = order.getAmount() - discount;
    repository.save(order, total);
    receiptPrinter.print(customer, order, discount, total);
}
```

Method ini hanya memanggil tiga kelas lain secara berurutan: hitung diskon, simpan, cetak struk.

---

## `DiscountPolicy` dan `Repository`: OCP dan DIP

![h:270 DiscountPolicy diimplementasikan RegularDiscount dan WholesaleDiscount, Repository diimplementasikan InMemoryRepository dan FileRepository](../assets/illustrations/ocp-dip-pluggable.svg)

Keduanya memakai pola yang sama: satu interface, banyak implementasi kecil. Jenis diskon baru cukup menjadi kelas baru (Open/Closed). `OrderProcessor` hanya mengenal interface `Repository`, jadi penyimpanan bebas ditukar (Dependency Inversion).

---

## Contoh Kode: Menambah `DiscountPolicy` Baru

```java
public class WholesaleDiscountPolicy implements DiscountPolicy {
    public int calculate(int amount) {
        return amount * 20 / 100;
    }
}
```

Menambah kelas ini tidak mengubah satu baris pun `DiscountCalculator` atau `OrderProcessor`. `DiscountCalculator` menyimpan policy dalam `Map<String, DiscountPolicy>` dengan jenis pelanggan sebagai kunci, persis contoh `Map` di Bagian 3.

---

## Kesalahan Umum: Memaksakan Kontrak yang Tidak Relevan

<div class="warn-box">
<b>Salah:</b> <code>class DigitalOrder extends Order implements Shippable { public String ship() { return "N/A"; } }</code>, supaya "konsisten" dengan <code>PhysicalOrder</code>.
</div>

**Benar:** `class DigitalOrder extends Order { }` tanpa `Shippable`. Alasannya: versi salah melanggar Interface Segregation, karena `DigitalOrder` dipaksa mengisi method yang tidak relevan, dan melanggar Liskov, karena `"N/A"` adalah janji palsu.

---

## Latihan

Toko ingin menambah `StudentDiscountPolicy` (potongan 10% untuk mahasiswa).

1. Berapa kelas baru yang perlu ditambah?
2. Perlukah `DiscountCalculator` atau `OrderProcessor` diubah?
3. Prinsip SOLID mana yang membuat hal itu mungkin?

---

## Jawaban Latihan

1. **Satu kelas baru:** `StudentDiscountPolicy implements DiscountPolicy`.
2. **Tidak.** Kelas baru itu didaftarkan saat sistem disusun, misalnya di `main`, bukan di dalam `DiscountCalculator` atau `OrderProcessor`.
3. **Open/Closed.** Sistem terbuka untuk diperluas, tetapi kode yang sudah ada tidak diubah.

---

## Rangkuman Bagian 4

- `Shippable` yang kecil menerapkan Interface Segregation dan menjaga Liskov Substitution.
- `OrderProcessor` menyerahkan diskon, penyimpanan, dan pencetakan ke kelas lain (Single Responsibility).
- `DiscountPolicy` menerapkan Open/Closed. `Repository` menerapkan Dependency Inversion.

---

## Rangkuman Pertemuan 11

- SOLID adalah lima prinsip desain kelas: satu kelas satu tugas; kelas baru bukan ubah kode lama; pengganti harus tetap bisa; interface kecil; bergantung pada interface.
- `ArrayList` dan `Map` menggantikan array untuk data yang bertambah atau dicari lewat kunci.
- Sistem pemrosesan pesanan menunjukkan kelima prinsip bekerja bersama dalam satu desain.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Collections; Martin, *Agile Software Development* (SOLID Principles)

Oracle Java Tutorials: "The Collections Framework"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 11

---

## Diskusi

`OrderProcessor` menerima `Repository` lewat constructor, sehingga penyimpanan bisa pindah dari `InMemoryRepository` ke `FileRepository`. Bayangkan `OrderProcessor` sejak awal memakai `InMemoryRepository` secara langsung.

1. Kode apa yang harus diubah saat penyimpanan pindah ke berkas?
2. Di berapa tempat perubahan itu mungkin muncul?
