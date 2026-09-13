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

- Cara menyimpan dan mencari data tanpa harus menebak ukuran di awal atau memeriksa elemen satu per satu
- Lima prinsip yang membuat kelas tetap mudah dipahami, diperluas, dan diuji seiring aplikasi bertambah besar
- Cara mengenali kapan sebuah kelas melanggar salah satu dari prinsip-prinsip itu
- Studi kasus sintesis: sebuah sistem pemrosesan pesanan yang menerapkan kelima prinsip SOLID sekaligus dalam satu desain

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 11.
</div>

---

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Collections, `ArrayList` dan `Map`
- **Sesi 2 (50')**: SOLID bagian 1, Single Responsibility, Open/Closed, Liskov Substitution
- **Sesi 3 (50')**: SOLID bagian 2, Interface Segregation, Dependency Inversion
- **Sesi 4 (50')**: Sintesis, kelima prinsip dalam satu sistem pemrosesan pesanan

---

<!-- _class: divider -->

# Bagian 1
## Collections

Sesi 1 dari 4

---

## Batasan Array Biasa

Array biasa harus ditentukan ukurannya sejak awal dibuat, dan mencari satu elemen di dalamnya berarti memeriksa elemen satu per satu sampai ditemukan.

<div class="warn-box">
Menentukan ukuran array di awal berarti menebak: terlalu kecil berarti kehabisan tempat, terlalu besar berarti memori terbuang percuma. Mencari data lewat pemeriksaan satu per satu juga semakin lambat seiring bertambahnya data.
</div>

---

## Mengapa Ini Penting?

Bayangkan aplikasi dengan jutaan data pelanggan disimpan di array. Mencari satu pelanggan berarti, dalam kasus terburuk, memeriksa jutaan elemen satu per satu sebelum ditemukan (atau dipastikan tidak ada). Semakin besar aplikasi berkembang, semakin terasa lambat setiap operasi pencarian, sementara batas ukuran array yang ditentukan di awal cepat atau lambat pasti terlampaui.

<div class="term-box">
Inilah sebabnya hampir semua aplikasi nyata memakai struktur data dari Java Collections Framework, bukan array biasa: ukurannya menyesuaikan otomatis, dan pencarian lewat kunci bisa dilakukan langsung tanpa memeriksa data lain sama sekali.
</div>

---

## ArrayList dan Map

![h:300 Array berukuran tetap dengan pencarian satu per satu, dibandingkan Map dengan pencarian langsung lewat kunci](../assets/illustrations/collections-motivation.svg)

<div class="term-box">
<b>ArrayList</b> adalah daftar yang ukurannya menyesuaikan otomatis. <b>Map</b> (paling umum <code>HashMap</code> atau <code>LinkedHashMap</code>) menyimpan pasangan kunci-nilai, pencarian berdasarkan kunci dilakukan langsung tanpa memeriksa elemen lain.
</div>

---

## Contoh Kode: `ArrayList` Menyesuaikan Ukuran Otomatis

```java
List<Employee> employees = new ArrayList<>();
employees.add(new Employee("E001", "Nadia"));
employees.add(new Employee("E002", "Sari"));
System.out.println(employees.get(1).getName());
```

Tidak ada ukuran yang perlu ditentukan di awal, `employees.add(...)` menambah elemen tanpa batas yang ditebak sebelumnya.

---

## Contoh Kode: `Map` Mencari Langsung Lewat Kunci

```java
Map<String, Employee> employees = new HashMap<>();
employees.put("E001", new Employee("E001", "Nadia"));
employees.put("E002", new Employee("E002", "Sari"));
Employee e = employees.get("E002");
```

`employees.get("E002")` langsung mengembalikan objeknya, tanpa memeriksa `"E001"` terlebih dahulu.

---

## Kesalahan Umum: Mencari di Map dengan Perulangan Manual

<div class="warn-box">
<b>Salah:</b> menulis <code>for (Employee e : employees.values()) { if (e.getId().equals("E003")) return e; }</code>, padahal <code>employees</code> sudah berupa <code>Map</code>.
</div>

**Benar:** `employees.get("E003")` langsung mengembalikan objeknya (atau `null` bila tidak ada), tanpa perlu memeriksa elemen lain satu per satu. Inilah keunggulan utama `Map` dibanding array atau `ArrayList` untuk pencarian berdasarkan kunci.

---

## Latihan

Aplikasi A menyimpan daftar pesanan yang HANYA pernah diproses berurutan dari awal sampai akhir, tidak pernah dicari berdasarkan ID tertentu. Aplikasi B menyimpan data karyawan yang SERING dicari berdasarkan NIP karyawan.

Struktur data mana (`ArrayList` atau `Map`) yang lebih cocok untuk masing-masing? Jelaskan.

---

## Jawaban Latihan

**Aplikasi A: `ArrayList`.** Data hanya diakses berurutan, tidak pernah butuh pencarian cepat berdasarkan kunci tertentu, `ArrayList` sudah cukup.

**Aplikasi B: `Map<String, Employee>`.** Pencarian sering dilakukan berdasarkan NIP, `Map` memakai NIP sebagai kunci sehingga pencariannya langsung, jauh lebih cepat dibanding memeriksa satu per satu.

---

## Rangkuman Bagian 1

- Array biasa harus ditentukan ukurannya di awal, dan pencariannya memeriksa elemen satu per satu.
- `ArrayList` menyesuaikan ukuran otomatis; `Map` menyimpan pasangan kunci-nilai dan mencari langsung lewat kunci.
- Memakai perulangan manual untuk mencari di `Map` menghilangkan keunggulan utamanya.

Selanjutnya: Bagian 2 membahas tiga dari lima prinsip SOLID, cara merancang kelas yang tetap mudah diperluas.

---

<!-- _class: divider -->

# Bagian 2
## SOLID: Single Responsibility, Open/Closed, Liskov Substitution

Sesi 2 dari 4

---

## Lima Prinsip Desain Kelas yang Baik

<div class="term-box">
<b>SOLID</b> adalah lima prinsip yang membantu kelas tetap mudah dipahami, diperluas, dan diuji seiring aplikasi bertambah besar: <b>S</b>ingle Responsibility, <b>O</b>pen/Closed, <b>L</b>iskov Substitution, <b>I</b>nterface Segregation, <b>D</b>ependency Inversion.
</div>

Beberapa di antaranya sudah kamu praktikkan tanpa disadari sejak beberapa pertemuan lalu. Bagian ini dan Bagian 3 memberi nama formalnya, sekaligus melengkapi dua yang belum pernah dibahas.

---

## Single Responsibility Principle (SRP)

![h:280 Satu kelas dengan tiga tanggung jawab, dipisah menjadi tiga kelas masing-masing satu tanggung jawab](../assets/illustrations/srp-split.svg)

<div class="term-box">
Satu kelas sebaiknya memiliki satu tanggung jawab, satu alasan untuk berubah. Kelas yang mencampur banyak tanggung jawab menjadi sulit dipahami, dan perubahan pada satu tanggung jawab berisiko memengaruhi tanggung jawab lain yang sebenarnya tidak berhubungan. Ibarat restoran: satu orang tidak merangkap koki, kasir, dan pelayan sekaligus, supaya kesalahan di dapur tidak ikut mengacaukan pencatatan pembayaran.
</div>

---

## Mengapa Ini Penting?

Bayangkan kelas `Report` yang sekaligus menghitung total, memformat tampilan, dan mengirim email, semuanya bercampur dalam satu kelas. Tim yang mengerjakan perubahan format tampilan (misalnya dari teks ke PDF) bisa saja tanpa sengaja mengubah baris yang memengaruhi perhitungan total, karena keduanya berada di file yang sama, meskipun keduanya sebenarnya sama sekali tidak berhubungan.

<div class="term-box">
Kelas dengan satu tanggung jawab jauh lebih aman diubah: mengganti cara pengiriman email tidak pernah menyentuh logika perhitungan sama sekali, karena keduanya sudah berada di kelas yang berbeda. Semakin besar aplikasi, semakin mahal harga yang dibayar ketika prinsip ini diabaikan sejak awal.
</div>

---

## Contoh Kode: `Report` Dipisah Jadi Tiga Kelas

```java
class TotalCalculator {
    double calculate(Report report) { /* ... */ return 0; }
}

class ReportFormatter {
    String format(Report report) { /* ... */ return ""; }
}
```

`ReportMailer` (tidak ditampilkan) melengkapi tanggung jawab ketiga; masing-masing kelas hanya punya satu alasan untuk berubah.

---

## Open/Closed Principle (Recap)

![h:260 PaymentMethod.pay() dengan if/else per tipe, dibandingkan subclass baru GoPay yang ditambahkan tanpa mengubah kode lama](../assets/illustrations/ocp-extend-not-modify.svg)

<div class="term-box">
<b>Open/Closed Principle</b>: kelas sebaiknya terbuka untuk diperluas, tertutup untuk diubah. Kamu sudah mempraktikkan ini sejak Pertemuan 7: menambah subclass <code>PaymentMethod</code> baru tidak pernah mengubah kode superclass yang sudah ada, subclass baru cukup meng-override method miliknya sendiri. Bandingkan dengan cabang <code>if</code>/<code>else</code> per tipe: satu bug pada cabang salah satu tipe pembayaran berisiko merusak cabang tipe lain, sebab semuanya bercampur di method yang sama. Ibarat stop kontak listrik di dinding: alat baru apa pun tinggal dicolokkan, tidak perlu membongkar instalasi rumah untuk menambahkannya.
</div>

---

## Mengapa Ini Penting?

Bayangkan sebuah sistem pembayaran e-commerce yang menangani semua jenis pembayaran lewat satu method besar berisi cabang `if`/`else`: `if (type.equals("CREDIT_CARD")) {...} else if (type.equals("BANK_TRANSFER")) {...}`. Ketika toko ingin menambah GoPay, satu-satunya cara adalah menyisipkan cabang `else if` baru ke DALAM method yang sama. Programmer yang terburu-buru bisa saja salah menempatkan logika baru sehingga tanpa sengaja mengubah alur cabang Credit Card yang sebelumnya sudah berjalan benar, padahal keduanya sama sekali tidak berhubungan.

<div class="term-box">
Semakin banyak jenis pembayaran ditambahkan ke method yang sama, semakin besar method itu, dan semakin besar pula risiko satu perubahan kecil merembet ke cabang lain yang seharusnya tidak tersentuh. Inilah alasan Open/Closed Principle penting: menambah kemampuan baru seharusnya tidak pernah mengharuskan mengubah kode lama yang sudah teruji dan sudah berjalan benar.
</div>

---

## Contoh Kode: Menambah GoPay Tanpa Mengubah Kode Lama

```java
// Sebelum: satu method besar, harus diubah tiap ada jenis baru
if (type.equals("CREDIT_CARD")) { chargeCard(amount); }
else if (type.equals("BANK_TRANSFER")) { transferBank(amount); }
// menambah GoPay berarti menyisipkan cabang baru di sini

// Sesudah: subclass baru, kode lama tidak tersentuh
class GoPayMethod extends PaymentMethod {
    void pay(double amount) { /* logika GoPay */ }
}
```

`PaymentMethod`, `CreditCardMethod`, dan `BankTransferMethod` yang sudah ada sejak Pertemuan 7 tidak perlu diubah satu baris pun; `GoPayMethod` cukup ditambahkan sebagai kelas baru.

---

## Kesalahan Umum: Menyisipkan Cabang Baru ke Method Lama

<div class="warn-box">
<b>Salah:</b> menambah dukungan GoPay dengan menyisipkan <code>else if (type.equals("GOPAY")) {...}</code> baru ke dalam method <code>pay()</code> yang sudah ada, alih-alih membuat subclass baru.
</div>

**Benar:** buat kelas `GoPayMethod extends PaymentMethod`, lalu override method `pay()` miliknya sendiri. Method lama tempat `CreditCardMethod` dan `BankTransferMethod` sudah berjalan sama sekali tidak disentuh, sehingga tidak ada risiko regresi pada jenis pembayaran yang sudah ada dan sudah teruji.

---

## Liskov Substitution Principle (Recap)

![h:260 List<Bird> memanggil fly() untuk tiap elemen, Sparrow dan Duck berhasil, Penguin melempar exception](../assets/illustrations/lsp-substitution.svg)

<div class="term-box">
<b>Liskov Substitution Principle</b>: subclass harus bisa menggantikan superclass-nya di mana pun tanpa mengubah kebenaran program. <code>Sedan</code> dan <code>Truck</code> selalu bisa dipakai di mana pun kode mengharapkan <code>Vehicle</code>, sejak Pertemuan 6-7, tanpa membuat kode itu berperilaku salah. Kode yang memanggil method lewat tipe superclass-nya percaya penuh pada kontrak itu; begitu ada satu subclass yang diam-diam melanggarnya, program bisa gagal di lokasi yang jauh dari subclass bermasalah, jauh lebih sulit dilacak dibanding galat kompilasi biasa. Slide berikutnya menunjukkan subclass yang GAGAL memenuhi janji ini.
</div>

---

## Kesalahan Umum: Subclass Menolak Kontrak Superclass-nya

<div class="warn-box">
<b>Salah:</b> <code>Penguin extends Bird</code> meng-override <code>fly()</code> untuk melempar <code>UnsupportedOperationException</code>, karena penguin tidak bisa terbang.
</div>

```java
for (Bird b : birds) {
    b.fly();  // meledak begitu b ternyata Penguin
}
```

**Benar:** kode di atas mengasumsikan SEMUA `Bird` bisa `fly()` tanpa masalah, itulah kontrak tipe `Bird`. `Penguin` menolak kontrak itu (melempar exception, bukan terbang), sehingga `b.fly()` tiba-tiba gagal begitu `birds` berisi seekor `Penguin`. Inilah pelanggaran Liskov Substitution Principle: `Penguin` gagal menggantikan `Bird` justru di tempat yang mengharapkan `Bird`.

---

## Perbaikan: Pisahkan Kemampuan Terbang ke Interface

<div class="term-box">
Solusi yang benar: <code>fly()</code> dikeluarkan dari <code>Bird</code>, dideklarasikan sebagai interface kecil tersendiri, misalnya <code>Flyable</code>. Hanya subclass yang benar-benar bisa terbang (<code>Sparrow</code>, <code>Duck</code>) yang meng-implement <code>Flyable</code>; <code>Penguin</code> tidak mengimplementasikannya sama sekali, bukan mengimplementasikan lalu menolaknya.
</div>

```java
interface Flyable { void fly(); }
class Sparrow extends Bird implements Flyable { ... }
class Penguin extends Bird { /* tidak implements Flyable */ }
```

Kode pemanggil berubah menjadi `if (b instanceof Flyable f) f.fly();`, memeriksa kemampuan lewat interface alih-alih mengasumsikan seluruh `Bird` pasti bisa terbang. Pola memisahkan kemampuan lewat interface kecil ini muncul lagi pada Bagian 4 (`Shippable`).

---

## Latihan

Untuk tiap skenario berikut, tentukan prinsip mana (Single Responsibility, Open/Closed, atau Liskov Substitution) yang dilanggar:

1. Kelas `UserManager` sekaligus memvalidasi input, menyimpan ke database, dan mengirim email selamat datang.
2. Menambah jenis diskon baru mengharuskan mengubah method `calculateDiscount()` yang sudah ada, menambah cabang `if` baru di dalamnya.

---

## Jawaban Latihan

1. **Single Responsibility Principle dilanggar.** Tiga tanggung jawab (validasi, penyimpanan, pengiriman email) tercampur dalam satu kelas, satu perubahan berisiko memengaruhi yang lain.
2. **Open/Closed Principle dilanggar.** Method yang sudah ada harus diubah setiap kali ada jenis diskon baru, padahal seharusnya cukup menambah subclass atau implementasi baru tanpa mengubah kode lama.

---

## Rangkuman Bagian 2

- Single Responsibility Principle: satu kelas, satu tanggung jawab, satu alasan untuk berubah.
- Open/Closed Principle: terbuka untuk diperluas (subclass baru), tertutup untuk diubah (kode lama tidak disentuh).
- Liskov Substitution Principle: subclass harus bisa menggantikan superclass-nya tanpa mengubah kebenaran program, tidak boleh menolak kontrak yang diwarisi.

Selanjutnya: Bagian 3 membahas dua prinsip SOLID yang tersisa, Interface Segregation dan Dependency Inversion.

---

<!-- _class: divider -->

# Bagian 3
## SOLID: Interface Segregation, Dependency Inversion

Sesi 3 dari 4

---

## Interface Segregation Principle (Recap)

![h:260 Chargeable diimplementasikan Phone dan ElectricCar, dua hierarki yang terpisah](../assets/uml/p09-chargeable.png)

<div class="term-box">
<b>Interface Segregation Principle</b>: interface sebaiknya kecil dan fokus, kelas tidak dipaksa mengimplementasikan method yang tidak relevan baginya. Kamu sudah mempraktikkan ini di Pertemuan 9: kemampuan seperti "bisa diisi daya" dideklarasikan sebagai interface kecil tersendiri (<code>Chargeable</code>), bukan digabung ke satu interface besar yang memaksa kelas lain mengimplementasikan method yang tidak relevan baginya. Ibarat remote TV: alat yang cuma perlu menyalakan dan mematikan tidak seharusnya dipaksa punya 50 tombol channel yang tidak akan pernah dipakainya.
</div>

---

## Mengapa Ini Penting?

Bayangkan `charge()` dan `call()` digabung menjadi satu interface besar, sebut saja `Device`. `ElectricCar` yang mengimplementasikan `Device` TERPAKSA ikut menuliskan method `call()`, walau mobil listrik tidak pernah bisa dipakai menelepon. Pilihan yang tersisa hanya dua, sama-sama bermasalah: method `call()` dibiarkan kosong (tidak melakukan apa-apa), atau melempar exception seperti `UnsupportedOperationException`.

<div class="term-box">
Kedua pilihan itu sama-sama merusak kepercayaan pada interface-nya: kode pemanggil yang memegang referensi <code>Device</code> dan memanggil <code>call()</code> berasumsi method itu benar-benar berfungsi, padahal pada <code>ElectricCar</code> method itu diam-diam tidak melakukan apa pun, atau malah membuat program berhenti. Interface yang gemuk seperti ini diam-diam menyeret pelanggaran Liskov Substitution Principle juga, sebab subclass tidak benar-benar memenuhi kontrak yang dijanjikan interface-nya.
</div>

---

## Contoh Kode: Memecah Interface yang Gemuk

```java
// Sebelum: satu interface gemuk memaksa method yang tidak relevan
interface Device { void charge(); void call(); }
class ElectricCar implements Device {
    public void charge() { /* isi daya baterai */ }
    public void call() { throw new UnsupportedOperationException(); }
}

// Sesudah: dua interface kecil dan fokus
interface Chargeable { void charge(); }
interface Callable { void call(); }
class ElectricCar implements Chargeable { /* hanya charge() */ }
```

`ElectricCar` kini hanya perlu mengimplementasikan kemampuan yang benar-benar relevan baginya.

---

## Dependency Inversion Principle

<div class="term-box">
<b>Dependency Inversion Principle</b>: kelas tingkat tinggi sebaiknya bergantung pada interface (abstraksi), bukan pada implementasi konkret. Ibarat charger USB-C: kabelnya sama untuk mengisi daya laptop, ponsel, atau earphone merek apa pun, asalkan perangkatnya mengikuti standar port USB-C, charger itu tidak perlu tahu merek spesifik perangkat yang akan diisi dayanya. Bagian 4 menunjukkan prinsip ini bekerja berdampingan dengan keempat prinsip lainnya dalam satu sistem.
</div>

---

## Membalik Arah Ketergantungan

![h:280 OrderProcessor bergantung langsung pada MySqlDatabase, dibandingkan bergantung pada interface Repository yang diimplementasikan MySqlDatabase dan MockRepository](../assets/illustrations/dip-invert-dependency.svg)

Slide berikutnya menjelaskan mengapa pembalikan arah ketergantungan ini penting, bukan sekadar tambahan tingkat abstraksi.

---

## Mengapa Ini Penting?

Bayangkan kelas `OrderProcessor` yang bergantung langsung pada kelas konkret `MySqlDatabase`. Migrasi ke database lain, atau menambahkan pengujian otomatis (yang butuh basis data tiruan agar tidak menyentuh data sungguhan), sama-sama menjadi sulit tanpa mengubah `OrderProcessor` itu sendiri, karena ia "tahu" secara langsung bahwa penyimpanannya pasti MySQL.

<div class="term-box">
Dependency Inversion Principle membalik arah ketergantungan ini: <code>OrderProcessor</code> cukup bergantung pada interface <code>Repository</code>, implementasi konkretnya (MySQL, penyimpanan sementara, atau versi tiruan untuk pengujian) bebas berganti tanpa <code>OrderProcessor</code> pernah tahu atau peduli. Bagian 4 menerapkan prinsip yang sama ini sebagai bagian dari sistem yang lebih besar.
</div>

---

## Contoh Kode: `OrderProcessor` Bergantung pada Interface

```java
interface Repository {
    void save(Order order);
}

class OrderProcessor {
    private Repository repository;
    OrderProcessor(Repository repository) {
        this.repository = repository;
    }
}
```

---

## Kesalahan Umum: Membuat Sendiri Implementasi Konkret

<div class="warn-box">
<b>Salah:</b> menulis <code>class OrderProcessor { private MySqlDatabase db = new MySqlDatabase(); ... }</code>, <code>OrderProcessor</code> membuat sendiri instance konkretnya di dalam kelasnya sendiri.
</div>

**Benar:** `OrderProcessor` menerima `Repository` (interface) lewat constructor, tidak pernah tahu atau membuat implementasi konkretnya sendiri. Kode yang membuat objek `OrderProcessor`-lah yang memutuskan implementasi mana yang dipakai.

---

## Latihan

`OrderProcessor` saat ini menulis `private MySqlDatabase db = new MySqlDatabase();` langsung di dalam kelasnya.

Jelaskan langkah untuk memperbaikinya agar mengikuti Dependency Inversion Principle, lalu sebutkan satu keuntungan konkret dari perbaikan itu.

---

## Jawaban Latihan

Buat interface `Repository` dengan method yang dibutuhkan (mis. `save(...)`), buat `MySqlDatabase` mengimplementasikan interface itu, lalu `OrderProcessor` menerima `Repository` lewat constructor alih-alih membuat `MySqlDatabase` sendiri. **Keuntungan:** `OrderProcessor` bisa diuji dengan implementasi tiruan (mock) tanpa menyentuh database sungguhan, dan implementasi penyimpanan bisa diganti tanpa mengubah `OrderProcessor` sama sekali.

---

## Rangkuman Bagian 3

- Interface Segregation Principle: interface kecil dan fokus, tidak memaksa kelas mengimplementasikan method yang tidak relevan.
- Dependency Inversion Principle: kelas tingkat tinggi bergantung pada interface, bukan implementasi konkret.
- Kelas yang membuat sendiri instance konkret dependensinya (`new MySqlDatabase()` di dalam dirinya sendiri) melanggar Dependency Inversion Principle, walau interface-nya sudah ada.

Selanjutnya: Bagian 4 menyatukan seluruh prinsip ini dalam satu studi kasus sintesis.

---

<!-- _class: divider -->

# Bagian 4
## Sintesis: Kelima Prinsip dalam Satu Sistem

Sesi 4 dari 4

---

## Satu Sistem Pemrosesan Pesanan

Bayangkan sebuah sistem pemrosesan pesanan toko daring: ada pesanan fisik (perlu dikirim) dan pesanan digital (tidak perlu dikirim), diskon yang besarnya berbeda tiap jenis pelanggan, dan penyimpanan data yang suatu hari mungkin harus berpindah dari memori ke berkas. Satu sistem kecil ini ternyata cukup untuk menunjukkan kelima prinsip SOLID bekerja bersama sekaligus, bukan satu per satu secara terpisah.

---

## Shippable: LSP dan ISP Sekaligus

![h:230 Order, PhysicalOrder yang mengimplementasikan Shippable, dan DigitalOrder yang sengaja tidak](../assets/uml/p11-order-shippable.png)

`Shippable` sengaja kecil dan fokus, hanya `ship()` (Interface Segregation). `PhysicalOrder` mengimplementasikannya; `DigitalOrder` sengaja TIDAK, sebab pesanan digital tidak pernah bisa dikirim. Ini menghormati Liskov Substitution Principle: `DigitalOrder` tidak dipaksa berpura-pura punya `ship()` yang tidak masuk akal baginya.

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

---

## OrderProcessor: Single Responsibility Principle

![h:280 OrderProcessor mendelegasikan ke DiscountCalculator, Repository, dan ReceiptPrinter](../assets/illustrations/orderprocessor-srp.svg)

`OrderProcessor` sendiri tidak menghitung diskon, tidak menyimpan data, dan tidak mencetak apa pun. Ketiga tanggung jawab itu didelegasikan masing-masing ke `DiscountCalculator`, `Repository`, dan `ReceiptPrinter`, sehingga mengubah cara struk dicetak, misalnya, tidak pernah berisiko merusak perhitungan diskon.

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

---

## DiscountPolicy dan Repository: OCP dan DIP Berdampingan

![h:280 DiscountPolicy diimplementasikan RegularDiscount dan WholesaleDiscount, Repository diimplementasikan InMemoryRepository dan FileRepository](../assets/illustrations/ocp-dip-pluggable.svg)

Kedua sisi memakai pola yang sama: satu interface, banyak implementasi kecil yang bisa ditambah atau ditukar. `DiscountPolicy` (Open/Closed): jenis diskon baru cukup jadi kelas baru, `DiscountCalculator` tidak pernah diubah. `Repository` (Dependency Inversion): `OrderProcessor` bergantung pada interface-nya saja, implementasi penyimpanan bebas ditukar.

---

## Contoh Kode: Menambah `DiscountPolicy` Baru

```java
public class WholesaleDiscountPolicy implements DiscountPolicy {
    public int calculate(int amount) {
        return amount * 20 / 100;
    }
}
```

Menambah kelas ini tidak mengubah satu baris pun `DiscountCalculator` atau `OrderProcessor` yang sudah ada.

---

## Kesalahan Umum: Memaksakan Kontrak yang Tidak Relevan

<div class="warn-box">
<b>Salah:</b> menulis <code>class DigitalOrder extends Order implements Shippable { public String ship() { return "N/A"; } }</code>, memaksa <code>DigitalOrder</code> ikut mengimplementasikan <code>Shippable</code> supaya "konsisten" dengan <code>PhysicalOrder</code>.
</div>

**Benar:** ini melanggar Interface Segregation Principle (`DigitalOrder` dipaksa mengimplementasikan method yang tidak relevan baginya) sekaligus Liskov Substitution Principle (`ship()` yang mengembalikan `"N/A"` adalah kontrak palsu, bukan perilaku `Shippable` yang sesungguhnya). Kelas yang memang tidak punya kemampuan tertentu sebaiknya tidak mengimplementasikan interface-nya sama sekali.

---

## Latihan

Toko ingin menambahkan `StudentDiscountPolicy` (potongan 10% untuk pelanggan berstatus mahasiswa).

Kelas apa saja yang perlu ditambah atau diubah? Sebutkan prinsip SOLID yang membuat perubahan ini tidak perlu menyentuh kode `DiscountCalculator` maupun `OrderProcessor` yang sudah ada.

---

## Jawaban Latihan

**Cukup menambah satu kelas baru**, `StudentDiscountPolicy implements DiscountPolicy`, lalu mendaftarkannya ke `DiscountCalculator` untuk tipe pelanggan yang sesuai. Tidak ada kode `DiscountCalculator` maupun `OrderProcessor` yang perlu diubah. Ini adalah **Open/Closed Principle**: sistem terbuka untuk diperluas (kelas kebijakan diskon baru) tetapi tertutup untuk diubah (kode yang sudah ada tidak disentuh).

---

## Rangkuman Bagian 4

- `Shippable` yang kecil dan fokus, hanya diimplementasikan kelas yang benar-benar relevan, menerapkan Interface Segregation sekaligus menjaga Liskov Substitution.
- `OrderProcessor` mendelegasikan diskon, penyimpanan, dan pencetakan ke kelas terpisah, menerapkan Single Responsibility.
- `DiscountPolicy` dan `Repository` sama-sama pola satu-interface-banyak-implementasi: yang pertama menerapkan Open/Closed, yang kedua menerapkan Dependency Inversion.

---

## Rangkuman Pertemuan 11

- `ArrayList` dan `Map` menggantikan array biasa untuk data yang ukurannya berubah-ubah atau sering dicari berdasarkan kunci.
- SOLID adalah lima prinsip desain kelas: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion.
- Sistem pemrosesan pesanan pada Bagian 4 menunjukkan kelima prinsip ini bekerja bersama dalam satu desain, bukan lima aturan lepas yang berdiri sendiri-sendiri.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Collections; Martin, *Agile Software Development* (SOLID Principles)

Oracle Java Tutorials: "The Collections Framework"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 11

---

## Diskusi

`OrderProcessor` menerima `Repository` lewat constructor, sehingga bisa berpindah dari `InMemoryRepository` ke `FileRepository` tanpa mengubah `OrderProcessor` sama sekali. Jelaskan dengan kata-katamu sendiri: apa yang akan terjadi (kode apa yang harus diubah, dan di berapa banyak tempat) apabila `OrderProcessor` sejak awal bergantung langsung pada `InMemoryRepository` tanpa lewat interface `Repository`, lalu suatu hari penyimpanannya harus diganti ke berkas?
