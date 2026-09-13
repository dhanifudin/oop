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

Pertemuan 9: **Kelas Abstrak dan Interface**

Mendeklarasikan kontrak yang wajib dipenuhi subclass

---

## Yang Akan Kamu Pelajari

- Cara mencegah sebuah kelas diinstansiasi langsung ketika ia hanya masuk akal sebagai superclass
- Cara mewajibkan setiap subclass menyediakan perilakunya sendiri, dideteksi compiler, bukan ditemukan belakangan
- Cara mendeklarasikan kontrak kemampuan yang berlaku lintas hierarki kelas yang tidak berkerabat
- Kapan memilih kelas abstrak dan kapan memilih interface untuk kebutuhan yang sama
- Penerapan pada Bank Mini: `Account` menjadi abstract, interface `InterestBearing` untuk rekening berbunga

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 9.
</div>

---

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Kelas abstrak, superclass yang tidak boleh diinstansiasi
- **Sesi 2 (50')**: Interface, kontrak lintas hierarki kelas
- **Sesi 3 (50')**: Menerapkan kelas abstrak ke Bank Mini
- **Sesi 4 (50')**: Menerapkan interface ke Bank Mini

---

<!-- _class: divider -->

# Bagian 1
## Kelas Abstrak

Sesi 1 dari 4

---

## Superclass yang Tidak Boleh Diinstansiasi

Bayangkan kelas `Shape` sebagai superclass umum untuk `Circle` dan `Square`. Setiap bentuk pasti punya luas, tetapi rumus luasnya berbeda-beda tergantung jenis bentuknya. Tidak ada satu "bentuk generik" yang masuk akal untuk diinstansiasi langsung, `Shape` hanya masuk akal sebagai superclass.

---

## Mengapa Ini Penting?

Bayangkan sebuah framework GUI dipakai oleh ratusan aplikasi berbeda: setiap komponen wajib tahu cara menggambar dirinya sendiri, tetapi "komponen generik" yang belum tahu cara menggambar apa pun tidak pernah boleh benar-benar dibuat. Tanpa cara memastikan hal ini, sebuah objek yang belum lengkap bisa saja lolos dibuat, dan galatnya baru muncul jauh kemudian, saat method yang belum diimplementasikan itu benar-benar dipanggil pengguna.

<div class="term-box">
Kelas abstrak memindahkan kesalahan ini dari saat program berjalan ke saat program dikompilasi: subclass yang belum mengimplementasikan seluruh method abstrak warisannya tidak akan pernah bisa diinstansiasi sama sekali, compiler yang menolaknya, bukan pengguna aplikasi yang menemukannya belakangan. Inilah sebabnya kelas abstrak menjadi fondasi banyak framework dan library besar, dari GUI toolkit sampai driver database.
</div>

---

## Kelas Abstrak

<div class="term-box">
<b>Kelas abstrak</b> (<code>abstract class</code>) tidak boleh diinstansiasi langsung lewat <code>new</code>, ia hanya boleh menjadi superclass. Dideklarasikan dengan kata kunci <code>abstract</code> pada kelasnya.
</div>

---

## Contoh Kode: Mendeklarasikan `abstract class Shape`

```java
public abstract class Shape {
    private String label;

    public Shape(String label) { this.label = label; }

    public abstract double area();
}
```

`new Shape("bentuk")` akan ditolak compiler, `Shape` hanya boleh dijadikan superclass.

---

## Method Abstrak

![h:320 Shape sebagai kelas abstrak, Circle dan Square mengimplementasikan area()](../assets/uml/p09-shape-abstract.png)

<div class="term-box">
<b>Method abstrak</b> hanya dideklarasikan signature-nya (nama, parameter, tipe kembalian), tanpa isi sama sekali. Setiap subclass konkret (yang bisa diinstansiasi) wajib menyediakan isinya sendiri, atau compiler akan menampilkan galat.
</div>

---

## Contoh Kode: `Circle` Mengimplementasikan `area()`

```java
public class Circle extends Shape {
    private double radius;

    public Circle(String label, double radius) {
        super(label);
        this.radius = radius;
    }

    @Override
    public double area() { return Math.PI * radius * radius; }
}
```

---

## Subclass Konkret vs Kelas Abstrak

<div class="warn-box">
Sebuah subclass dari kelas abstrak tetap ikut menjadi abstrak (dan tidak bisa diinstansiasi) apabila ia belum mengimplementasikan seluruh method abstrak warisannya. Hanya subclass yang sudah mengimplementasikan semuanya yang menjadi kelas konkret.
</div>

<div class="tip-box">
Kelas abstrak boleh tetap memiliki method biasa (dengan isi lengkap) selain method abstraknya, persis seperti superclass pada umumnya. Subclass mewarisi method biasa itu apa adanya, sama seperti inheritance yang sudah dipelajari sebelumnya.
</div>

---

## Kesalahan Umum: Lupa Mengimplementasikan Method Abstrak

<div class="warn-box">
<b>Salah:</b> menulis <code>class Square extends Shape</code> tanpa meng-override <code>area()</code>, mengira ini sudah cukup karena <code>Square</code> "kan jelas punya luas".
</div>

**Benar:** compiler menampilkan galat `Square is not abstract and does not override abstract method area()`. `Square` ikut menjadi abstrak (tidak bisa diinstansiasi) sampai `area()` benar-benar diimplementasikan.

---

## Latihan

Diberi `abstract class Shape` dengan method abstrak `area()`. Kelas `Triangle extends Shape` tidak meng-override `area()` sama sekali.

Apakah `new Triangle("segitiga", 3, 4)` bisa dikompilasi? Jelaskan.

---

## Jawaban Latihan

**Tidak bisa.** `Triangle` belum mengimplementasikan method abstrak `area()` yang diwarisi dari `Shape`, sehingga `Triangle` ikut menjadi abstrak secara otomatis. Compiler menolak instansiasi kelas abstrak mana pun, termasuk `Triangle`, sampai `area()` diimplementasikan.

---

## Rangkuman Bagian 1

- Kelas abstrak tidak boleh diinstansiasi langsung, hanya boleh menjadi superclass.
- Method abstrak hanya mendeklarasikan signature-nya; subclass konkret wajib mengimplementasikan isinya.
- Subclass yang belum mengimplementasikan seluruh method abstrak warisannya ikut menjadi abstrak.

Selanjutnya: Bagian 2 membahas interface, kontrak serupa yang berlaku lintas hierarki kelas yang sama sekali tidak berkerabat.

---

<!-- _class: divider -->

# Bagian 2
## Interface

Sesi 2 dari 4

---

## Kontrak Lintas Hierarki Kelas

Bayangkan `Phone` dan `ElectricCar`, dua kelas yang sama sekali tidak berkerabat (satu alat komunikasi, satu kendaraan), tetapi keduanya sama-sama "bisa diisi daya". Tidak ada satu superclass masuk akal yang bisa mewadahi kemampuan ini lewat inheritance biasa.

<div class="term-box">
<b>Interface</b> mendeklarasikan kontrak method (signature tanpa isi) yang wajib dipenuhi kelas mana pun yang menyatakan <code>implements</code> terhadapnya, tanpa mewajibkan hubungan <code>extends</code> sama sekali.
</div>

---

## Mengapa Ini Penting?

Bayangkan sebuah tim besar membangun sistem pembayaran: satu tim menulis kode yang memproses pembayaran, tim lain menulis implementasi untuk kartu kredit, tim lain lagi untuk e-wallet, dan ada pula tim yang menulis pengujian otomatis. Tanpa kontrak yang jelas, ketiga tim itu harus terus-menerus berkoordinasi setiap kali ada perubahan kecil pada salah satu bagian.

<div class="term-box">
Interface memungkinkan tim yang memproses pembayaran bergantung hanya pada kontrak (method apa saja yang tersedia), bukan pada implementasi konkretnya. Implementasi boleh berubah, ditambah, atau bahkan diganti dengan versi tiruan untuk pengujian (disebut mock), tanpa mengubah kode yang memakainya. Prinsip inilah yang mendasari salah satu prinsip SOLID, Dependency Inversion Principle, dibahas lebih lanjut pada Pertemuan 11.
</div>

---

## Contoh Kode: `Phone` Meng-implement `Chargeable`

```java
public interface Chargeable {
    void charge();
}

public class Phone implements Chargeable {
    private String model;

    public Phone(String model) { this.model = model; }

    @Override
    public void charge() { System.out.println(model + " is charging"); }
}
```

---

## Dua Kelas Tak Berkerabat, Satu Kontrak

![h:300 Chargeable diimplementasikan Phone dan ElectricCar, dua hierarki yang terpisah](../assets/uml/p09-chargeable.png)

`Phone` dan `ElectricCar` tidak berbagi superclass apa pun selain `Object`, tetapi keduanya sama-sama wajib menyediakan `charge()` karena sama-sama menyatakan `implements Chargeable`.

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

## Kesalahan Umum: Memakai `extends` untuk Interface

<div class="warn-box">
<b>Salah:</b> menulis <code>class Phone extends Chargeable</code>, mengira interface diperlakukan sama seperti superclass biasa.
</div>

**Benar:** kelas menyatakan hubungan ke interface lewat `implements`, bukan `extends`. `extends Chargeable` menyebabkan galat compile, sebab `Chargeable` bukan kelas yang bisa diwarisi lewat inheritance.

---

## Latihan

Untuk tiap kebutuhan berikut, tentukan **kelas abstrak** atau **interface** yang lebih cocok:

1. `Vehicle` sebagai superclass umum `Car`, `Motorcycle`, dan `Truck`, dengan atribut `speed` yang dipakai bersama.
2. Kemampuan "bisa dibandingkan" (`compareTo()`), diterapkan pada `Student`, `Product`, dan `Invoice`, tiga kelas yang tidak berkerabat.

---

## Jawaban Latihan

1. **Kelas abstrak.** `Car`, `Motorcycle`, dan `Truck` memang berkerabat lewat `Vehicle`, dan butuh atribut bersama (`speed`) yang tidak bisa dideklarasikan interface.
2. **Interface.** `Student`, `Product`, dan `Invoice` sama sekali tidak berkerabat; masing-masing tetap butuh superclass-nya sendiri, `implements` interface tidak membatasi itu.

---

## Rangkuman Bagian 2

- Interface mendeklarasikan kontrak method tanpa isi, wajib dipenuhi lewat `implements`.
- Sebuah kelas boleh meng-implements banyak interface, berbeda dari kelas abstrak yang hanya boleh di-extends satu.
- Interface cocok untuk kemampuan lintas hierarki kelas yang tidak berkerabat; kelas abstrak cocok untuk superclass yang masuk akal bagi seluruh subclass.

Selanjutnya: Bagian 3 menerapkan kelas abstrak ke `Account` Bank Mini.

---

<!-- _class: divider -->

# Bagian 3
## Menerapkan Kelas Abstrak ke Bank Mini

Sesi 3 dari 4

---

## Account Menjadi Kelas Abstrak

Tidak ada satu pun `Account` polos yang pernah dibuat langsung di Bank Mini, semua instansiasi selalu berupa `SavingsAccount` atau `CheckingAccount`. Ini pertanda bahwa `Account` sebaiknya menjadi kelas abstrak, dengan method abstrak `monthlyFee()` yang wajib diimplementasikan setiap jenis rekening dengan besaran biayanya masing-masing.

![h:280 Account abstract dengan method abstrak monthlyFee, SavingsAccount dan CheckingAccount mengimplementasikannya masing-masing](../assets/uml/p09-account-monthlyfee.png)

---

## Contoh Kode: `monthlyFee()` Berbeda Tiap Jenis Rekening

```java
public class SavingsAccount extends Account {
    @Override
    public double monthlyFee() { return 0; }
}

public class CheckingAccount extends Account {
    @Override
    public double monthlyFee() { return MONTHLY_FEE; }
}
```

`SavingsAccount` bebas biaya bulanan, `CheckingAccount` menanggung biaya tetap, keduanya wajib menyediakan `monthlyFee()` sendiri karena `Account` mendeklarasikannya sebagai method abstrak.

---

## Kesalahan Umum: Kode Lama Membuat `Account` Langsung

<div class="warn-box">
<b>Salah:</b> kode dari pertemuan sebelumnya yang masih menulis <code>new Account("A1", owner, 0)</code> langsung, tanpa lewat <code>SavingsAccount</code> atau <code>CheckingAccount</code>.
</div>

**Benar:** setelah `Account` menjadi abstract, baris kode itu gagal dikompilasi (`Account is abstract; cannot be instantiated`). Ini perubahan yang disengaja: `Account` polos memang tidak pernah seharusnya ada di Bank Mini.

---

## Latihan

`BusinessAccount` (tugas mandiri Pertemuan 6) juga `extends Account`, tetapi belum pernah mengimplementasikan `monthlyFee()`.

Apa yang terjadi kalau seseorang mencoba `new BusinessAccount(...)` sekarang, setelah `Account` menjadi abstract? Jelaskan.

---

## Jawaban Latihan

**Gagal dikompilasi.** `BusinessAccount` mewarisi method abstrak `monthlyFee()` dari `Account` tetapi belum mengimplementasikannya, sehingga `BusinessAccount` ikut menjadi abstrak. Perbaikannya: tambahkan `@Override public double monthlyFee()` di `BusinessAccount` dengan besaran biaya yang sesuai.

---

## Rangkuman Bagian 3

- `Account` menjadi abstract class dengan method abstrak `monthlyFee()`, mencegah instansiasi `Account` polos sama sekali.
- `SavingsAccount` dan `CheckingAccount` wajib mengimplementasikan `monthlyFee()` masing-masing sesuai aturan biayanya.
- Subclass lama yang belum mengimplementasikan `monthlyFee()` (mis. `BusinessAccount`) ikut menjadi abstrak sampai diperbaiki.

Selanjutnya: Bagian 4 menerapkan interface untuk kemampuan yang hanya dimiliki sebagian jenis rekening.

---

<!-- _class: divider -->

# Bagian 4
## Menerapkan Interface ke Bank Mini

Sesi 4 dari 4

---

## InterestBearing, Interface untuk Rekening Berbunga

![h:300 Account sebagai kelas abstrak, SavingsAccount meng-implement interface InterestBearing](../assets/uml/p09-account-abstract.png)

Hanya rekening yang menghasilkan bunga yang membutuhkan `applyInterest()`, `CheckingAccount` tidak membutuhkannya sama sekali. Daripada menambah method itu ke `Account` (yang berarti seluruh subclass mewarisinya, termasuk yang tidak relevan), method ini dideklarasikan sebagai interface `InterestBearing` tersendiri, hanya diterapkan pada `SavingsAccount`.

---

## Contoh Kode: `SavingsAccount` Meng-implement `InterestBearing`

```java
public interface InterestBearing {
    void applyInterest();
}

public class SavingsAccount extends Account implements InterestBearing {
    @Override
    public void applyInterest() {
        double interest = getBalance() * interestRate;
        deposit(interest);
    }
}
```

---

## Kesalahan Umum: Menaruh `applyInterest()` di `Account`

<div class="warn-box">
<b>Salah:</b> menambahkan <code>abstract void applyInterest()</code> langsung ke <code>Account</code>, mengira ini lebih sederhana daripada membuat interface baru.
</div>

**Benar:** `CheckingAccount` sama sekali tidak berbunga, memaksanya mengimplementasikan `applyInterest()` (walau isinya kosong atau melempar galat) hanya karena mewarisi `Account`. Interface `InterestBearing` yang terpisah menghindari ini, kemampuan yang tidak relevan tidak perlu dipaksakan ke seluruh subclass.

---

## Latihan

`BusinessAccount` (tugas mandiri Pertemuan 6) adalah rekening bisnis tanpa bunga sama sekali.

Perlukah `BusinessAccount` meng-implement `InterestBearing`? Jelaskan alasanmu.

---

## Jawaban Latihan

**Tidak perlu.** `InterestBearing` hanya relevan untuk rekening yang benar-benar menghasilkan bunga. `BusinessAccount` tidak berbunga, memaksanya meng-implement `InterestBearing` berarti menyediakan `applyInterest()` yang tidak pernah punya makna nyata, persis kesalahan umum yang baru dibahas.

---

## Rangkuman Bagian 4

- Interface `InterestBearing` mendeklarasikan `applyInterest()`, hanya diterapkan pada rekening yang benar-benar berbunga.
- `SavingsAccount` mewarisi `Account` lewat `extends` sekaligus meng-implement `InterestBearing` lewat `implements`, dua jenis kontrak berbeda sekaligus.
- Kemampuan yang tidak relevan bagi seluruh subclass lebih cocok jadi interface terpisah daripada dipaksakan lewat superclass.

---

## Rangkuman Pertemuan 9

- Kelas abstrak mencegah instansiasi langsung dan mewajibkan subclass konkret mengimplementasikan method abstraknya.
- Interface mendeklarasikan kontrak method tanpa isi, berlaku lintas hierarki kelas yang tidak berkerabat, dan sebuah kelas boleh meng-implements banyak sekaligus.
- Bank Mini memakai keduanya: `Account` menjadi abstract lewat `monthlyFee()`, dan `InterestBearing` sebagai interface tambahan untuk rekening berbunga.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Object-Oriented Programming: Creating Abstract Superclasses and Concrete Subclasses, Interfaces

Oracle Java Tutorials: "Abstract Methods and Classes", "Interfaces"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 9

---

## Diskusi

`SavingsAccount` sekarang punya dua "kontrak" sekaligus: mewarisi `Account` (kelas abstrak) lewat `extends`, dan meng-implement `InterestBearing` (interface) lewat `implements`. Jelaskan dengan kata-katamu sendiri apa perbedaan mendasar antara kedua jenis kontrak ini, lalu berikan satu contoh kemampuan baru (selain bunga) yang menurutmu lebih cocok dideklarasikan sebagai interface baru dibandingkan ditambahkan langsung ke `Account`.
