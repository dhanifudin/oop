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
  .cols.wide > div:first-child {
    flex: 2;
  }
  .cols.wide > div:last-child {
    flex: 1;
  }
  .cols.wide img {
    max-height: 480px;
  }
  .cols.wide .term-box {
    margin: 0;
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
- Kelima prinsip dalam satu sistem pemrosesan pesanan

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
<b>SOLID</b> adalah lima prinsip agar kelas mudah dipahami, diperluas, dan diuji: <b>S</b>ingle Responsibility, <b>O</b>pen/Closed, <b>L</b>iskov Substitution, <b>I</b>nterface Segregation, <b>D</b>ependency Inversion.
</div>

Beberapa sudah kamu pakai tanpa sadar. Hari ini kita beri nama resminya.

---

## Single Responsibility Principle (SRP)

<div class="cols wide">
<div>

![Satu kelas dengan tiga tanggung jawab, dipisah menjadi tiga kelas masing-masing satu tanggung jawab](../assets/illustrations/srp-split.svg)

</div>
<div>

Di restoran, koki, kasir, dan pelayan punya tugas masing-masing. Salah di dapur tidak mengacaukan pembayaran.

<div class="term-box">
Satu kelas punya satu tanggung jawab, yaitu satu alasan untuk berubah. <b>Satu kelas, satu tugas.</b>
</div>

</div>
</div>

---

## Mengapa Ini Penting?

Kelas `Report` menghitung total, memformat tampilan, dan mengirim email sekaligus. Mengubah format tampilan bisa tanpa sengaja merusak perhitungan.

<div class="term-box">
Kelas dengan satu tugas aman diubah. Mengganti cara kirim email tidak menyentuh perhitungan.
</div>

---

## Contoh Kode: `Report` Dipisah Jadi Tiga Kelas

```java
class TotalCalculator { double calculate(Report r) { /* ... */ return 0; } }
class ReportFormatter { String format(Report r) { /* ... */ return ""; } }
class ReportMailer    { void send(Report r) { /* ... */ } }
```

1. Menghitung total: `TotalCalculator`.
2. Memformat tampilan: `ReportFormatter`.
3. Mengirim email: `ReportMailer`. Tiap kelas satu alasan untuk berubah.

---

## Open/Closed Principle (OCP)

<div class="cols wide">
<div>

![PaymentMethod.pay() dengan if/else per tipe, dibandingkan subclass baru GoPay yang ditambahkan tanpa mengubah kode lama](../assets/illustrations/ocp-extend-not-modify.svg)

</div>
<div>

Stop kontak menerima alat apa pun. Alat baru tinggal dicolokkan, instalasi rumah tidak dibongkar.

<div class="term-box">
<b>Open/Closed Principle</b>: terbuka untuk diperluas, tertutup untuk diubah. <b>Kelas baru, bukan ubah kode lama.</b>
</div>

</div>
</div>

---

## Mengapa Ini Penting?

Satu method besar berisi `if`/`else` untuk tiap jenis pembayaran. Menambah GoPay berarti menyisipkan cabang baru. Salah sedikit, cabang kartu kredit ikut rusak.

<div class="term-box">
Dengan kelas baru, kode lama yang sudah teruji tidak disentuh.
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

`PaymentMethod`, `CreditCard`, dan `BankTransfer` tidak berubah satu baris pun.

---

## Kesalahan Umum: Cabang `if` untuk Tiap Jenis Baru

<div class="warn-box">
<b>Salah:</b> tiap jenis diskon baru menambah satu cabang di <code>calculateDiscount()</code>.
</div>

```java
if (type.equals("REGULAR")) { return amount * 5 / 100; }
else if (type.equals("WHOLESALE")) { return amount * 20 / 100; }
else if (type.equals("STUDENT")) { return amount * 10 / 100; }   // cabang baru lagi
```

**Benar:** interface `DiscountPolicy` dengan `calculate(amount)`, satu kelas per jenis diskon. Jenis baru tidak menyentuh method lama.

---

## Latihan

Prinsip mana yang dilanggar: Single Responsibility atau Open/Closed?

1. `UserManager` memvalidasi input, menyimpan ke database, dan mengirim email.
2. Jenis pembayaran baru mengharuskan menambah cabang `if` di `pay()`.
3. `InvoicePrinter` mencetak faktur sekaligus menghitung pajaknya.

---

## Jawaban Latihan

1. **Single Responsibility.** Tiga tugas dalam satu kelas.
2. **Open/Closed.** Method lama diubah tiap ada jenis baru.
3. **Single Responsibility.** Mencetak dan menghitung pajak adalah dua tugas.

---

## Rangkuman Bagian 1

- Single Responsibility: satu kelas, satu tugas.
- Open/Closed: kelas baru, bukan ubah kode lama.
- Perubahan kecil tetap kecil: hanya satu kelas disentuh.

Selanjutnya: Liskov Substitution dan Interface Segregation.

---

<!-- _class: divider -->

# Bagian 2
## SOLID: Liskov Substitution dan Interface Segregation

Sesi 2 dari 4

---

## Liskov Substitution Principle (LSP)

<div class="cols wide">
<div>

![List<Bird> memanggil fly() untuk tiap elemen, Sparrow dan Duck berhasil, Penguin melempar exception](../assets/illustrations/lsp-substitution.svg)

</div>
<div>

Kamu memesan ojek, dan yang datang motor mana pun. Penggantinya harus tetap bisa mengantarmu.

<div class="term-box">
<b>Liskov Substitution Principle</b>: subclass harus bisa menggantikan superclass-nya tanpa membuat program salah. <code>Sparrow</code> dan <code>Duck</code> bisa, <code>Penguin</code> tidak. <b>Pengganti harus tetap bisa.</b>
</div>

</div>
</div>

---

## Mengapa Ini Penting?

Kode yang memanggil lewat tipe superclass percaya pada janji superclass itu. Bila satu subclass mengingkari janji, program gagal jauh dari subclass itu.

<div class="term-box">
Compiler tidak menolaknya. Error baru muncul saat program berjalan, mungkin di tangan pengguna.
</div>

---

## Kesalahan Umum: Subclass Mengingkari Janji

<div class="warn-box">
<b>Salah:</b> <code>Penguin extends Bird</code> meng-override <code>fly()</code> untuk melempar <code>UnsupportedOperationException</code>.
</div>

```java
for (Bird b : birds) {
    b.fly();   // error begitu b ternyata Penguin
}
```

**Benar:** `Penguin` tidak punya `fly()` sama sekali (slide berikut). Kode di atas mengira semua `Bird` bisa `fly()`.

---

## Perbaikan: Pisahkan Kemampuan Terbang

<div class="term-box">
Pindahkan <code>fly()</code> ke interface kecil <code>Flyable</code>. Hanya <code>Sparrow</code> dan <code>Duck</code> yang mengimplementasikannya.
</div>

```java
interface Flyable { void fly(); }
class Sparrow extends Bird implements Flyable { ... }
class Penguin extends Bird { /* tidak implements Flyable */ }
```

Pemanggil memeriksa dulu: `if (b instanceof Flyable f) f.fly();`. Bentuk ini memberi variabel `f` bertipe `Flyable` tanpa cast. Pola ini muncul lagi di Bagian 4 (`Shippable`).

---

## Interface Segregation Principle (ISP)

<div class="cols wide">
<div>

![Chargeable diimplementasikan Phone dan ElectricCar, dua hierarki yang terpisah](../assets/uml/p09-chargeable.png)

</div>
<div>

Remote TV yang hanya perlu menyalakan dan mematikan tidak butuh 50 tombol channel.

<div class="term-box">
<b>Interface Segregation Principle</b>: interface kecil dan fokus, supaya kelas tidak dipaksa mengisi method yang tidak relevan. <code>Chargeable</code> di atas: satu method, dua kelas yang tidak berkerabat. <b>Interface kecil, satu kemampuan.</b>
</div>

</div>
</div>

---

## Mengapa Ini Penting?

Bila `charge()` dan `call()` digabung dalam satu interface `Device`, `ElectricCar` wajib menulis `call()`. Pilihannya dua dan keduanya buruk: kosong, atau melempar exception.

<div class="term-box">
Kode yang memegang <code>Device</code> mengira <code>call()</code> berfungsi. Interface yang gemuk ikut melanggar Liskov.
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

1. Sebelum: `ElectricCar` terpaksa menulis `call()`.
2. Sesudah: dua interface, satu kemampuan masing-masing.
3. `ElectricCar` hanya `implements Chargeable`.

---

## Latihan

Prinsip mana yang dilanggar: Liskov Substitution atau Interface Segregation?

1. `Penguin extends Bird`, tetapi `fly()`-nya melempar exception.
2. `interface Printer` punya `print()`, `scan()`, `fax()`. `SimplePrinter` hanya bisa `print()`.
3. `ReadOnlyList extends ArrayList`, tetapi `add()`-nya melempar exception.

---

## Jawaban Latihan

1. **Liskov Substitution.** `Penguin` mengingkari janji `Bird`.
2. **Interface Segregation.** `SimplePrinter` dipaksa mengisi `scan()` dan `fax()`.
3. **Liskov Substitution.** Pemakai `ArrayList` mengira `add()` selalu bisa.

---

## Rangkuman Bagian 2

- Liskov Substitution: pengganti harus tetap bisa.
- Interface Segregation: interface kecil, satu kemampuan.
- Interface gemuk memaksa method palsu, dan itu melanggar Liskov.

Selanjutnya: Dependency Inversion, lalu `ArrayList` dan `Map`.

---

<!-- _class: divider -->

# Bagian 3
## SOLID: Dependency Inversion, dan Collections

Sesi 3 dari 4

---

## Dependency Inversion Principle (DIP)

<div class="cols wide">
<div>

![OrderProcessor bergantung langsung pada MySqlDatabase, dibandingkan bergantung pada interface Repository yang diimplementasikan MySqlDatabase dan MockRepository](../assets/illustrations/dip-invert-dependency.svg)

</div>
<div>

Kasir hanya mengenal mesin kartu, bukan bank penerbitnya. Kartu bank mana pun bisa dipakai.

<div class="term-box">
<b>Dependency Inversion Principle</b>: kelas yang mengatur alur (<code>OrderProcessor</code>) bergantung pada interface, bukan pada kelas sungguhan (<code>MySqlDatabase</code>). <b>Bergantung pada interface, bukan kelas sungguhan.</b>
</div>

</div>
</div>

---

## Mengapa Ini Penting?

`OrderProcessor` yang langsung memakai `MySqlDatabase` terikat ke MySQL. Pindah database atau menguji dengan tiruan menjadi sulit.

<div class="term-box">
Dengan interface <code>Repository</code>, penyimpanan bisa berganti tanpa mengubah <code>OrderProcessor</code>.
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
3. Kode yang membuat `OrderProcessor` memilih implementasinya.

---

## `ArrayList` dan `Map`

`InMemoryRepository` harus menyimpan banyak `Order`. Array biasa terlalu kaku.

| | Array | `ArrayList` | `Map` |
|---|---|---|---|
| Ukuran | tetap sejak dibuat | bertambah otomatis | bertambah otomatis |
| Cara ambil | lewat indeks | lewat indeks, `get(1)` | lewat kunci, `get("WHOLESALE")` |
| Cocok untuk | jumlah pasti | daftar berurutan | cari langsung lewat kunci |

<div class="term-box">
Elemen di luar ukuran array: <code>ArrayIndexOutOfBoundsException</code>. <b>ArrayList</b> bertambah otomatis. <b>Map</b> mencari langsung lewat kunci; <code>LinkedHashMap</code> menjaga urutan penambahan.
</div>

---

## Contoh Kode: `ArrayList` dan `Map`

```java
List<Order> orders = new ArrayList<>();
orders.add(order);                                   // tidak ada batas ukuran

Map<String, DiscountPolicy> policies = new HashMap<>();
policies.put("WHOLESALE", new WholesaleDiscountPolicy());
DiscountPolicy policy = policies.get("WHOLESALE");   // langsung, tanpa looping
```

1. `List<Order>`: daftar yang hanya berisi `Order`, bertambah lewat `add(...)`.
2. `Map<String, DiscountPolicy>`: kunci jenis pelanggan, nilai policy (Bagian 1).
3. `get("WHOLESALE")` langsung. Kunci tidak ada: hasilnya `null`.

---

## Latihan

1. `OrderProcessor` berisi `private MySqlDatabase db = new MySqlDatabase();`. Prinsip mana yang dilanggar?
2. Pilih `ArrayList` atau `Map`: daftar pesanan yang hanya dicetak berurutan.
3. Pilih `ArrayList` atau `Map`: data pelanggan yang sering dicari lewat nomor pelanggan.

---

## Jawaban Latihan

1. **Dependency Inversion.** Terima `Repository` lewat constructor.
2. **`ArrayList`.** Hanya dibaca berurutan.
3. **`Map`.** Nomor pelanggan menjadi kunci.

---

## Rangkuman Bagian 3

- Dependency Inversion: bergantung pada interface, bukan kelas sungguhan.
- `new` kelas sungguhan di dalam kelas tetap melanggarnya.
- `ArrayList` untuk daftar yang bertambah, `Map` untuk cari lewat kunci.

Selanjutnya: kelima prinsip dalam satu studi kasus.

---

<!-- _class: divider -->

# Bagian 4
## Studi Kasus: Kelima Prinsip dalam Satu Sistem

Sesi 4 dari 4

---

## Satu Sistem Pemrosesan Pesanan

Toko daring punya pesanan fisik (dikirim) dan digital (tidak dikirim). Diskon berbeda tiap jenis pelanggan.

- `Order`, dengan subclass `PhysicalOrder` dan `DigitalOrder`
- `Customer`, punya jenis pelanggan (reguler, grosir)
- `OrderProcessor`, koordinator alur pesanan
- `DiscountCalculator` dan `DiscountPolicy`, menghitung diskon
- `Repository`, menyimpan pesanan; `ReceiptPrinter`, mencetak struk

---

## `Shippable`: LSP dan ISP Sekaligus

<div class="cols wide">
<div>

![Order, PhysicalOrder yang mengimplementasikan Shippable, dan DigitalOrder yang sengaja tidak](../assets/uml/p11-order-shippable.png)

</div>
<div>

`Shippable` hanya punya `ship()` (Interface Segregation). `PhysicalOrder implements Shippable`; `DigitalOrder` sengaja tidak, sebab pesanan digital tidak pernah dikirim. Tidak ada `ship()` palsu (Liskov Substitution).

</div>
</div>

---

## `OrderProcessor`: Single Responsibility

<div class="cols wide">
<div>

![OrderProcessor mendelegasikan ke DiscountCalculator, Repository, dan ReceiptPrinter](../assets/illustrations/orderprocessor-srp.svg)

</div>
<div>

`OrderProcessor` tidak menghitung, tidak menyimpan, tidak mencetak. Tiga tugas itu diserahkan ke `DiscountCalculator`, `Repository`, dan `ReceiptPrinter`.

</div>
</div>

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

Hitung diskon, simpan, cetak struk. Masing-masing oleh kelas lain.

---

## `DiscountPolicy` dan `Repository`: OCP dan DIP

<div class="cols wide">
<div>

![DiscountPolicy diimplementasikan RegularDiscount dan WholesaleDiscount, Repository diimplementasikan InMemoryRepository dan FileRepository](../assets/illustrations/ocp-dip-pluggable.svg)

</div>
<div>

Pola yang sama: satu interface, banyak implementasi kecil. Jenis diskon baru cukup kelas baru (Open/Closed). `OrderProcessor` hanya mengenal `Repository`, jadi penyimpanan bebas ditukar (Dependency Inversion).

</div>
</div>

---

## Contoh Kode: Menambah `DiscountPolicy` Baru

```java
public class WholesaleDiscountPolicy implements DiscountPolicy {
    public int calculate(int amount) {
        return amount * 20 / 100;
    }
}
```

`DiscountCalculator` dan `OrderProcessor` tidak berubah. `DiscountCalculator` memegang `Map<String, DiscountPolicy>`, persis contoh di Bagian 3.

---

## Kesalahan Umum: Memaksakan Kontrak yang Tidak Relevan

<div class="warn-box">
<b>Salah:</b> <code>class DigitalOrder extends Order implements Shippable { public String ship() { return "N/A"; } }</code>, supaya "konsisten".
</div>

**Benar:** `class DigitalOrder extends Order { }` tanpa `Shippable`. `"N/A"` adalah janji palsu (Liskov), dan method yang tidak relevan dipaksakan (Interface Segregation).

---

## Latihan

Toko ingin menambah `StudentDiscountPolicy` (potongan 10% untuk mahasiswa).

1. Berapa kelas baru yang perlu ditambah?
2. Perlukah `DiscountCalculator` atau `OrderProcessor` diubah?
3. Prinsip SOLID mana yang membuat hal itu mungkin?

---

## Jawaban Latihan

1. **Satu kelas baru:** `StudentDiscountPolicy implements DiscountPolicy`.
2. **Tidak.** Kelas baru didaftarkan di `main`, bukan di dalam keduanya.
3. **Open/Closed.** Diperluas lewat kelas baru, kode lama tidak diubah.

---

## Rangkuman Bagian 4

- `Shippable` kecil: Interface Segregation, dan menjaga Liskov.
- `OrderProcessor` hanya mengoordinasikan: Single Responsibility.
- `DiscountPolicy`: Open/Closed. `Repository`: Dependency Inversion.

---

## Rangkuman Pertemuan 11

- SOLID: satu kelas satu tugas; kelas baru bukan ubah kode lama; pengganti harus tetap bisa; interface kecil; bergantung pada interface.
- `ArrayList` untuk data yang bertambah, `Map` untuk cari lewat kunci.
- Sistem pemrosesan pesanan menunjukkan kelima prinsip bekerja bersama.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Collections; Martin, *Agile Software Development* (SOLID Principles)

Oracle Java Tutorials: "The Collections Framework"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 11

---

## Tugas 1: Single Responsibility

Kode saat ini: satu kelas mengerjakan empat tugas.

```java
class OrderService {
    boolean validate(Order order) { /* ... */ return true; }
    int shippingCost(Order order) { /* ... */ return 0; }
    void saveToFile(Order order) { /* ... */ }
    void sendConfirmationEmail(Order order) { /* ... */ }
}
```

1. Pecah menjadi empat kelas, satu tugas tiap kelas. Tuliskan nama kelas dan method utamanya.
2. Aturan ongkos kirim berubah. Kelas mana yang diubah sesudah dipisah?

---

## Tugas 2: Open/Closed

Kode saat ini: tiap kurir baru menambah cabang `if`. Toko akan menambah `"SICEPAT"`.

```java
class ShippingCalculator {
    int cost(Order order, String courier) {
        if (courier.equals("JNE")) { return 20000; }
        else if (courier.equals("POS")) { return 15000; }
        else if (courier.equals("GOJEK")) { return 25000; }   // cabang baru lagi
        return 0;
    }
}
```

1. Tuliskan `interface ShippingMethod { int cost(Order order); }` dan kelas `SicepatShipping`.
2. Tuliskan ulang `ShippingCalculator` agar menerima `ShippingMethod`, plus baris di `main` yang memilih `SicepatShipping`.

---

## Tugas 3: Liskov Substitution

Kode saat ini: `Rectangle` punya `setWidth`, `setHeight`, dan `area()` = lebar x tinggi.

```java
class Square extends Rectangle {
    void setWidth(int w)  { width = w; height = w; }    // ikut mengubah tinggi
    void setHeight(int h) { width = h; height = h; }
}

Rectangle r = new Square();
r.setWidth(4); r.setHeight(5);
System.out.println(r.area());
```

1. Berapa yang diharapkan pemanggil, berapa yang tercetak?
2. Ubah: `Square` tidak lagi `extends Rectangle`. Keduanya `implements Shape { int area(); }`. Tuliskan kedua kelas, gambar diagramnya di kertas.

---

## Tugas 4: Interface Segregation

Kode saat ini: `RobotWorker` dipaksa mengisi method yang tidak masuk akal baginya.

```java
interface Worker { void work(); void eat(); void sleep(); }

class RobotWorker implements Worker {
    public void work()  { /* ... */ }
    // robot tidak makan dan tidak tidur:
    public void eat()   { throw new UnsupportedOperationException(); }
    public void sleep() { throw new UnsupportedOperationException(); }
}
```

1. Pecah `Worker` menjadi interface kecil. Tuliskan deklarasinya.
2. Tuliskan ulang baris `implements` untuk `RobotWorker` dan `HumanWorker`, lalu hapus method yang tidak perlu.

---

## Tugas 5: Dependency Inversion

Kode saat ini: terikat ke email. Toko ingin mengirim lewat WhatsApp juga.

```java
class NotificationService {
    private EmailSender sender = new EmailSender();   // kelas sungguhan dibuat sendiri

    void notifyCustomer(String to, String text) {
        sender.sendEmail(to, text);
    }
}
```

1. Tuliskan `interface MessageSender { void send(String to, String text); }`, lalu `EmailSender` dan `WhatsAppSender` yang mengimplementasikannya.
2. Tuliskan ulang `NotificationService` agar menerima `MessageSender` lewat constructor, plus baris di `main` yang memilih WhatsApp.

---

## Tugas 6: Collections

Kode saat ini: riwayat pesan disimpan di array berukuran 10.

```java
class NotificationService {
    private String[] sent = new String[10];
    private int count = 0;

    void log(String to, String text) {
        sent[count++] = to + ": " + text;   // pesan ke-11 gagal
    }
}
```

1. Ganti array dengan koleksi yang bertambah otomatis. Tuliskan deklarasi field dan `log(...)` yang baru.
2. Riwayat harus bisa dicari lewat nomor penerima. Pilih `ArrayList` atau `Map`, tuliskan deklarasi field dan satu baris pencariannya.
