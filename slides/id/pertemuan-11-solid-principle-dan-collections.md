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

Lima prinsip desain kelas yang baik, dan struktur data siap pakai

---

## Yang Akan Kamu Pelajari

- Cara menyimpan data yang jumlahnya berubah-ubah (`ArrayList`)
- Cara mencari data langsung lewat kunci (`Map`)
- Lima prinsip SOLID agar kelas mudah dipahami, diperluas, dan diuji
- Cara mengenali kelas yang melanggar salah satu prinsip itu
- Penerapan kelima prinsip dalam satu sistem pemrosesan pesanan

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 11.
</div>

---

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Collections, `ArrayList` dan `Map`
- **Sesi 2 (50')**: SOLID bagian 1, Single Responsibility, Open/Closed, Liskov Substitution
- **Sesi 3 (50')**: SOLID bagian 2, Interface Segregation, Dependency Inversion
- **Sesi 4 (50')**: Studi kasus, sistem pemrosesan pesanan

---

<!-- _class: divider -->

# Bagian 1
## Collections

Sesi 1 dari 4

---

## Masalah: Array Itu Kaku

```java
Employee[] employees = new Employee[2];   // ukuran dikunci: 2
employees[0] = new Employee("E001", "Nadia");
employees[1] = new Employee("E002", "Sari");
employees[2] = new Employee("E003", "Rian");   // error saat dijalankan
```

Array hanya muat sebanyak ukuran yang ditentukan di awal. Elemen ketiga menghasilkan `ArrayIndexOutOfBoundsException`.

---

## Masalah: Mencari Harus Satu per Satu

```java
for (Employee e : employees) {
    if (e.getId().equals("E002")) { System.out.println(e.getName()); }
}
```

Untuk mencari satu karyawan, program memeriksa elemen dari awal sampai ketemu. Makin banyak data, makin lama pencariannya.

---

## Mengapa Ini Penting?

Bayangkan toko daring dengan jutaan data pelanggan di dalam array. Mencari satu pelanggan bisa berarti memeriksa jutaan elemen. Sementara itu, batas ukuran array cepat atau lambat terlampaui.

<div class="term-box">
Karena itu aplikasi nyata memakai Java Collections Framework. Ukurannya menyesuaikan otomatis, dan pencarian lewat kunci bisa langsung.
</div>

---

## `ArrayList` dan `Map`

`ArrayList` seperti daftar antrean yang boleh bertambah panjang. `Map` seperti buku telepon: cari nama, langsung dapat nomornya.

![h:270 Array berukuran tetap dengan pencarian satu per satu, dibandingkan Map dengan pencarian langsung lewat kunci](../assets/illustrations/collections-motivation.svg)

<div class="term-box">
<b>ArrayList</b> adalah daftar yang ukurannya menyesuaikan otomatis. <b>Map</b> menyimpan pasangan kunci dan nilai, lalu mencari nilai langsung lewat kuncinya.
</div>

---

## Contoh Kode: `ArrayList`

```java
List<Employee> employees = new ArrayList<>();
employees.add(new Employee("E001", "Nadia"));
employees.add(new Employee("E002", "Sari"));
System.out.println(employees.get(1).getName());
```

1. `<Employee>` berarti daftar ini hanya berisi `Employee`.
2. Dua `add(...)` menambah elemen di indeks 0 dan 1.
3. `get(1)` mengambil indeks 1, yaitu elemen kedua. Output: `Sari`

---

## Contoh Kode: `Map`

```java
Map<String, Employee> employees = new HashMap<>();
employees.put("E001", new Employee("E001", "Nadia"));
employees.put("E002", new Employee("E002", "Sari"));
Employee e = employees.get("E002");
System.out.println(e.getName());
```

1. `<String, Employee>`: kuncinya `String`, nilainya `Employee`.
2. `put(...)` menyimpan satu pasangan kunci dan nilai.
3. `get("E002")` langsung mengambil objeknya, tanpa memeriksa `"E001"`. Output: `Sari`

---

## Kesalahan Umum: Looping Manual di `Map`

<div class="warn-box">
<b>Salah:</b> mencari di <code>Map</code> dengan looping manual, padahal <code>employees</code> sudah berupa <code>Map</code>.
</div>

```java
for (Employee e : employees.values()) {
    if (e.getId().equals("E003")) { return e; }
}
```

**Benar:** cukup `employees.get("E003")`. Hasilnya `null` bila kuncinya tidak ada. Looping manual membuang keunggulan utama `Map`.

---

## Latihan

Pilih `ArrayList` atau `Map` untuk tiap aplikasi:

1. Daftar pesanan yang hanya dicetak berurutan dari awal sampai akhir.
2. Data karyawan yang sering dicari lewat NIP.
3. Kamus istilah: pengguna mengetik sebuah kata, lalu program menampilkan artinya.

---

## Jawaban Latihan

1. **`ArrayList`.** Data hanya dibaca berurutan, tidak perlu pencarian lewat kunci.
2. **`Map`.** NIP menjadi kunci, sehingga pencariannya langsung.
3. **`Map`.** Kata menjadi kunci, artinya menjadi nilai.

---

## Rangkuman Bagian 1

- Array berukuran tetap dan pencariannya satu per satu.
- `ArrayList` menyesuaikan ukuran otomatis. `Map` mencari langsung lewat kunci.
- Looping manual di `Map` menghilangkan keunggulannya.

Selanjutnya: Bagian 2 membahas tiga dari lima prinsip SOLID, cara merancang kelas yang tetap mudah diperluas.

---

<!-- _class: divider -->

# Bagian 2
## SOLID: Single Responsibility, Open/Closed, Liskov Substitution

Sesi 2 dari 4

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
Satu kelas sebaiknya punya satu tanggung jawab, yaitu satu alasan untuk berubah.
</div>

---

## Mengapa Ini Penting?

Bayangkan kelas `Report` yang menghitung total, memformat tampilan, dan mengirim email sekaligus. Tim yang mengubah format tampilan bisa tanpa sengaja merusak baris perhitungan, karena semuanya ada di satu file.

<div class="term-box">
Kelas dengan satu tanggung jawab lebih aman diubah. Mengganti cara kirim email tidak menyentuh logika perhitungan.
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

## Open/Closed Principle (OCP)

Stop kontak menerima alat apa pun. Alat baru tinggal dicolokkan, instalasi rumah tidak dibongkar.

![h:270 PaymentMethod.pay() dengan if/else per tipe, dibandingkan subclass baru GoPay yang ditambahkan tanpa mengubah kode lama](../assets/illustrations/ocp-extend-not-modify.svg)

<div class="term-box">
<b>Open/Closed Principle</b>: kelas terbuka untuk diperluas, tertutup untuk diubah. Fitur baru ditambah lewat kelas baru, bukan dengan mengubah kode lama.
</div>

---

## Mengapa Ini Penting?

Bayangkan sistem pembayaran toko daring dengan satu method besar berisi `if`/`else` untuk tiap jenis pembayaran. Menambah GoPay berarti menyisipkan cabang baru ke method itu. Salah tempat sedikit, cabang kartu kredit yang sudah benar ikut rusak.

<div class="term-box">
Makin banyak jenis pembayaran, makin besar method itu dan makin besar risikonya. Dengan OCP, fitur baru tidak mengubah kode lama yang sudah teruji.
</div>

---

## Contoh Kode: Menambah GoPay Tanpa Mengubah Kode Lama

```java
// Sebelum: tiap jenis baru mengubah method ini
if (type.equals("CREDIT_CARD")) { chargeCard(amount); }
else if (type.equals("BANK_TRANSFER")) { transferBank(amount); }

// Sesudah: cukup kelas baru
class GoPayMethod extends PaymentMethod {
    void pay(double amount) { /* logika GoPay */ }
}
```

`PaymentMethod`, `CreditCardMethod`, dan `BankTransferMethod` sudah ada. Menambah `GoPayMethod` tidak mengubah satu baris pun kode mereka.

---

## Liskov Substitution Principle (LSP)

Kamu memesan ojek, dan yang datang motor mana pun. Penggantinya harus tetap bisa mengantarmu.

![h:270 List<Bird> memanggil fly() untuk tiap elemen, Sparrow dan Duck berhasil, Penguin melempar exception](../assets/illustrations/lsp-substitution.svg)

<div class="term-box">
<b>Liskov Substitution Principle</b>: subclass harus bisa menggantikan superclass-nya di mana pun tanpa membuat program salah. <code>Sedan</code> dan <code>Truck</code> dari Pertemuan 6 dan 7 sudah memenuhinya.
</div>

---

## Mengapa Ini Penting?

Kode yang memanggil method lewat tipe superclass percaya pada kontrak tipe itu. Bila satu subclass diam-diam melanggar kontrak, program gagal di tempat yang jauh dari subclass bermasalah. Penyebabnya sulit dilacak.

<div class="term-box">
Error seperti ini tidak ditolak compiler. Ia baru muncul saat program berjalan, mungkin di tangan pengguna.
</div>

---

## Kesalahan Umum: Subclass Menolak Kontrak

<div class="warn-box">
<b>Salah:</b> <code>Penguin extends Bird</code> meng-override <code>fly()</code> untuk melempar <code>UnsupportedOperationException</code>, karena penguin tidak bisa terbang.
</div>

```java
for (Bird b : birds) {
    b.fly();   // error begitu b ternyata Penguin
}
```

**Benar:** kode di atas mengira semua `Bird` bisa `fly()`. Begitu `birds` berisi `Penguin`, `b.fly()` gagal. `Penguin` tidak bisa menggantikan `Bird`, jadi Liskov dilanggar.

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

Pemanggil kini memeriksa kemampuannya: `if (b instanceof Flyable f) f.fly();`. Pola interface kecil ini muncul lagi di Bagian 4 (`Shippable`).

---

## Latihan

Prinsip mana yang dilanggar: Single Responsibility, Open/Closed, atau Liskov Substitution?

1. `UserManager` memvalidasi input, menyimpan ke database, dan mengirim email.
2. Jenis diskon baru mengharuskan menambah cabang `if` di method `calculateDiscount()`.
3. `Penguin extends Bird`, tetapi `fly()`-nya melempar exception.

---

## Jawaban Latihan

1. **Single Responsibility.** Tiga tanggung jawab tercampur dalam satu kelas.
2. **Open/Closed.** Method lama harus diubah tiap ada jenis diskon baru.
3. **Liskov Substitution.** `Penguin` menolak kontrak `Bird`, sehingga tidak bisa menggantikannya.

---

## Rangkuman Bagian 2

- Single Responsibility: satu kelas, satu alasan untuk berubah.
- Open/Closed: terbuka untuk diperluas, tertutup untuk diubah.
- Liskov Substitution: subclass harus bisa menggantikan superclass-nya tanpa membuat program salah.

Selanjutnya: Bagian 3 membahas dua prinsip yang tersisa, Interface Segregation dan Dependency Inversion.

---

<!-- _class: divider -->

# Bagian 3
## SOLID: Interface Segregation, Dependency Inversion

Sesi 3 dari 4

---

## Interface Segregation Principle (ISP)

Remote TV yang hanya perlu menyalakan dan mematikan tidak butuh 50 tombol channel.

![h:230 Chargeable diimplementasikan Phone dan ElectricCar, dua hierarki yang terpisah](../assets/uml/p09-chargeable.png)

<div class="term-box">
<b>Interface Segregation Principle</b>: interface sebaiknya kecil dan fokus, sehingga kelas tidak dipaksa mengisi method yang tidak relevan. Contohnya <code>Chargeable</code> di Pertemuan 9.
</div>

---

## Mengapa Ini Penting?

Bayangkan `charge()` dan `dial()` digabung dalam satu interface besar, `Device`. `ElectricCar` ikut wajib menulis `dial()`, padahal mobil listrik tidak bisa menelepon. Pilihannya dua dan keduanya buruk: dibiarkan kosong, atau melempar exception.

<div class="term-box">
Kode yang memegang <code>Device</code> mengira <code>dial()</code> berfungsi. Pada <code>ElectricCar</code>, method itu tidak berbuat apa-apa atau menghentikan program. Interface yang gemuk ikut melanggar Liskov.
</div>

---

## Contoh Kode: Memecah Interface yang Gemuk

```java
// Sebelum: satu interface gemuk
interface Device { void charge(); void dial(); }

// Sesudah: dua interface kecil
interface Chargeable { void charge(); }
interface Dialable { void dial(); }
class ElectricCar implements Chargeable { /* hanya charge() */ }
```

1. Sebelum: `ElectricCar` terpaksa menulis `dial()` yang tidak masuk akal.
2. Sesudah: dua interface kecil, satu kemampuan masing-masing.
3. `ElectricCar` hanya `implements Chargeable`.

---

## Dependency Inversion Principle (DIP)

Kasir hanya mengenal mesin kartu, bukan bank penerbitnya. Kartu dari bank mana pun bisa dipakai.

![h:270 OrderProcessor bergantung langsung pada MySqlDatabase, dibandingkan bergantung pada interface Repository yang diimplementasikan MySqlDatabase dan MockRepository](../assets/illustrations/dip-invert-dependency.svg)

<div class="term-box">
<b>Dependency Inversion Principle</b>: kelas yang mengatur alur (kelas tingkat tinggi) bergantung pada interface, bukan pada kelas konkret.
</div>

---

## Mengapa Ini Penting?

Bayangkan `OrderProcessor` yang langsung memakai kelas `MySqlDatabase`. Pindah ke database lain, atau menguji dengan database tiruan, menjadi sulit karena `OrderProcessor` terikat ke MySQL.

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

## Kesalahan Umum: Membuat Sendiri Implementasi Konkret

<div class="warn-box">
<b>Salah:</b> <code>class OrderProcessor { private MySqlDatabase db = new MySqlDatabase(); }</code>. Kelas ini membuat sendiri objek konkretnya.
</div>

**Benar:** `OrderProcessor` menerima `Repository` (interface) lewat constructor. Kode yang membuat `OrderProcessor` yang memutuskan implementasi mana yang dipakai.

---

## Latihan

Prinsip mana yang dilanggar: Interface Segregation atau Dependency Inversion?

1. `interface Printer` punya `print()`, `scan()`, dan `fax()`. `SimplePrinter implements Printer`, padahal hanya bisa `print()`.
2. `OrderProcessor` berisi `private MySqlDatabase db = new MySqlDatabase();`. Satu perubahan apa yang memperbaikinya?

---

## Jawaban Latihan

1. **Interface Segregation.** `SimplePrinter` dipaksa mengisi `scan()` dan `fax()`. Pecah menjadi interface kecil.
2. **Dependency Inversion.** Buat interface `Repository`, lalu `OrderProcessor` menerimanya lewat constructor. Penyimpanan jadi mudah diganti dan mudah diuji dengan tiruan.

---

## Rangkuman Bagian 3

- Interface Segregation: interface kecil dan fokus.
- Dependency Inversion: bergantung pada interface, bukan kelas konkret.
- Membuat objek konkret dengan `new` di dalam kelas melanggar Dependency Inversion, walau interface-nya sudah ada.

Selanjutnya: Bagian 4 menyatukan kelima prinsip dalam satu studi kasus.

---

<!-- _class: divider -->

# Bagian 4
## Studi Kasus: Kelima Prinsip dalam Satu Sistem

Sesi 4 dari 4

---

## Satu Sistem Pemrosesan Pesanan

Sebuah toko daring punya pesanan fisik (perlu dikirim) dan pesanan digital (tidak perlu dikirim). Diskonnya berbeda tiap jenis pelanggan, dan penyimpanannya suatu hari mungkin pindah dari memori ke berkas.

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
        return "Shipping \"" + getDescription() + "\"...";
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

Menambah kelas ini tidak mengubah satu baris pun `DiscountCalculator` atau `OrderProcessor`.

---

## Kesalahan Umum: Memaksakan Kontrak yang Tidak Relevan

<div class="warn-box">
<b>Salah:</b> <code>class DigitalOrder extends Order implements Shippable { public String ship() { return "N/A"; } }</code>, supaya "konsisten" dengan <code>PhysicalOrder</code>.
</div>

**Benar:** ini melanggar Interface Segregation, karena `DigitalOrder` dipaksa mengisi method yang tidak relevan. Ini juga melanggar Liskov, karena `"N/A"` adalah kontrak palsu. Kelas yang tidak punya kemampuan itu sebaiknya tidak mengimplementasikan interface-nya.

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

- `ArrayList` dan `Map` menggantikan array untuk data yang berubah-ubah atau sering dicari lewat kunci.
- SOLID adalah lima prinsip desain kelas: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion.
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
