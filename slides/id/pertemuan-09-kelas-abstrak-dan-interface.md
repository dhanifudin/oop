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

Kelas yang belum lengkap, dan kontrak kemampuan

---

## Yang Akan Kamu Pelajari

- Cara membuat superclass yang tidak boleh dibuat objeknya (kelas abstrak)
- Cara mewajibkan tiap subclass mengisi sebuah method (method abstrak)
- Cara memberi satu kemampuan yang sama ke kelas yang tidak berkerabat (interface)
- Cara memilih antara kelas abstrak dan interface
- Penerapan keduanya pada satu studi kasus

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 9.
</div>

---

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Kelas abstrak dan method abstrak
- **Sesi 2 (50')**: Interface
- **Sesi 3 (50')**: Kelas abstrak atau interface?
- **Sesi 4 (50')**: Studi kasus, koleksi perpustakaan

---

<!-- _class: divider -->

# Bagian 1
## Kelas Abstrak

Sesi 1 dari 4

---

## Masalah: Objek yang Tidak Masuk Akal

```java
class Shape {
    public double area() { return 0; }   // luas bentuk apa?
}

Shape s = new Shape();
System.out.println(s.area());   // 0.0
```

Lingkaran punya luas. Persegi punya luas. "Bentuk umum" tidak punya rumus luas, tetapi Java tetap mengizinkan objeknya dibuat.

---

## Apa Itu Kelas Abstrak?

Bayangkan resep dasar kue yang satu langkahnya masih kosong: "isi sesuai selera". Resep itu belum bisa dimasak sebelum langkah kosongnya diisi.

<div class="term-box">
<b>Kelas abstrak</b> adalah kelas yang belum lengkap. Objeknya tidak boleh dibuat lewat <code>new</code>. Kelas ini hanya dipakai sebagai superclass.
</div>

---

## Mengapa Ini Penting?

Sebuah library tampilan dipakai ratusan aplikasi. Setiap komponen wajib tahu cara menggambar dirinya. Tanpa kelas abstrak, komponen yang belum tahu cara menggambar bisa lolos dibuat, dan kesalahannya baru terlihat saat aplikasi dipakai pengguna.

<div class="term-box">
Kelas abstrak memindahkan kesalahan itu ke saat kompilasi. Compiler yang menolaknya, bukan pengguna yang menemukannya.
</div>

---

## Method Abstrak: Langkah Kosong yang Wajib Diisi

<div class="term-box">
<b>Method abstrak</b> hanya punya signature, tanpa isi. Setiap subclass wajib mengisinya lewat overriding. Bila belum diisi, subclass itu tidak bisa dikompilasi.
</div>

Method abstrak hanya boleh ditulis di dalam kelas abstrak.

---

## Contoh Kode: `abstract class Shape`

```java
abstract class Shape {
    private String label;
    public Shape(String label) { this.label = label; }

    public String getLabel() { return label; }   // method biasa, diwarisi
    public abstract double area();               // method abstrak, tanpa isi
}
```

Sekarang `new Shape("x")` ditolak compiler: `Shape is abstract; cannot be instantiated`.

---

## Kelas Abstrak pada Diagram Kelas

![h:260 Shape sebagai kelas abstrak, Circle dan Square mengisi area()](../assets/uml/p09-shape-abstract.png)

Nama kelas abstrak dan method abstrak ditulis miring. `area()` muncul lagi di `Circle` dan `Square`, artinya kedua subclass mengisinya.

---

## Contoh Kode: `Circle` Mengisi `area()`

```java
class Circle extends Shape {
    private double radius;
    public Circle(String label, double radius) { super(label); this.radius = radius; }

    @Override
    public double area() { return 3.14 * radius * radius; }   // langkah kosong diisi
}
```

---

## Telusuri Langkah demi Langkah

```java
Shape s = new Circle("small circle", 2);
System.out.println(s.getLabel() + ": " + s.area());
```

1. Objeknya adalah `Circle`. Variabelnya boleh bertipe `Shape`.
2. `getLabel()` tidak ditulis di `Circle`, jadi versi warisan dari `Shape` yang dijalankan.
3. `area()` ditulis di `Circle`, jadi versi `Circle` yang dijalankan: 3.14 x 2 x 2.

Output: `small circle: 12.56`

---

## Kesalahan Umum: Lupa Mengisi Method Abstrak

<div class="warn-box">
<b>Salah:</b> menulis <code>class Square extends Shape { }</code> tanpa mengisi <code>area()</code>.
</div>

**Benar:** compiler menampilkan error `Square is not abstract and does not override abstract method area()`. Isi `area()` di `Square`, lalu kelas itu bisa dipakai.

---

## Latihan

`Shape` adalah kelas abstrak dengan method abstrak `area()`. Tentukan **valid** atau **error**:

1. `Shape s = new Shape("x");`
2. `class Square extends Shape { }` (tanpa `area()`)
3. `Shape s = new Circle("c", 1);`

---

## Jawaban Latihan

1. **Error.** Objek kelas abstrak tidak boleh dibuat.
2. **Error.** `Square` belum mengisi method abstrak `area()`.
3. **Valid.** Objeknya `Circle`, kelas yang sudah lengkap. Variabelnya boleh bertipe `Shape`.

---

## Rangkuman Bagian 1

- Kelas abstrak belum lengkap: objeknya tidak boleh dibuat, hanya dipakai sebagai superclass.
- Method abstrak tidak punya isi; setiap subclass wajib mengisinya.
- Kelas abstrak tetap boleh punya atribut, constructor, dan method biasa.

Selanjutnya: Bagian 2 membahas interface, kontrak untuk kelas yang tidak berkerabat.

---

<!-- _class: divider -->

# Bagian 2
## Interface

Sesi 2 dari 4

---

## Masalah: Tidak Berkerabat, Tetapi Punya Kemampuan Sama

```java
class Phone { }         // alat komunikasi
class ElectricCar { }   // kendaraan
```

Ponsel dan mobil listrik sama-sama bisa diisi daya. Keduanya tidak punya superclass yang masuk akal: ponsel bukan kendaraan, mobil bukan alat komunikasi.

---

## Apa Itu Interface?

Bayangkan port USB-C. Benda apa pun yang punya port itu bisa diisi daya dengan charger yang sama, entah ponsel, laptop, atau lampu.

<div class="term-box">
<b>Interface</b> adalah daftar method tanpa isi. Kelas yang menyatakan <code>implements</code> berjanji mengisi semua method itu.
</div>

---

## Mengapa Ini Penting?

Satu tim menulis kode pemroses pembayaran. Tim lain menulis kartu kredit, tim lain menulis e-wallet. Tanpa kontrak yang jelas, setiap perubahan kecil memaksa semua tim berkoordinasi ulang.

<div class="term-box">
Dengan interface, kode pemroses cukup mengenal kontraknya. Implementasi boleh ditambah atau diganti tanpa mengubah kode yang memakainya. Prinsip ini dibahas lagi pada Pertemuan 11.
</div>

---

## Contoh Kode: `Chargeable` dan `Phone`

```java
interface Chargeable {
    void charge();   // tanpa isi
}

class Phone implements Chargeable {
    @Override
    public void charge() { System.out.println("Phone is charging"); }
}
```

---

## Interface pada Diagram Kelas

![h:280 Chargeable diimplementasikan Phone dan ElectricCar](../assets/uml/p09-chargeable.png)

Nama interface juga ditulis miring. Bedanya ada pada panahnya: bergaris putus-putus, menunjuk dari kelas ke interface yang diimplementasikan.

---

## Telusuri: Satu Kontrak, Dua Kelas

```java
Chargeable[] devices = { new Phone(), new ElectricCar() };
for (Chargeable d : devices) {
    d.charge();
}
```

```
Phone is charging
Car is charging
```

Perulangan hanya mengenal `Chargeable`. Tiap objek menjalankan `charge()` miliknya sendiri.

---

## Satu Kelas, Banyak Interface

Seseorang hanya punya satu ibu kandung, tetapi boleh punya banyak sertifikat keahlian.

```java
class Phone implements Chargeable, Connectable {
    @Override public void charge() { System.out.println("Phone is charging"); }
    @Override public void connect() { System.out.println("Phone is online"); }
}
```

Sebuah kelas hanya boleh `extends` satu superclass, tetapi boleh `implements` banyak interface.

---

## Kesalahan Umum: Memakai `extends` untuk Interface

<div class="warn-box">
<b>Salah:</b> menulis <code>class Phone extends Chargeable</code>.
</div>

**Benar:** kelas memakai `implements` untuk interface, dan `extends` untuk superclass. Kode di atas gagal dikompilasi.

---

## Latihan

`Chargeable` punya method `charge()`. `Connectable` punya method `connect()`. Tentukan **valid** atau **error**:

1. `class Laptop extends Chargeable { ... }`
2. `class Laptop implements Chargeable { }` (tanpa `charge()`)
3. `class Laptop implements Chargeable, Connectable` dengan `charge()` dan `connect()` terisi

---

## Jawaban Latihan

1. **Error.** Interface dipakai dengan `implements`, bukan `extends`.
2. **Error.** `Laptop` berjanji mengisi `charge()`, tetapi belum mengisinya.
3. **Valid.** Satu kelas boleh `implements` banyak interface, asal semua method terisi.

---

## Rangkuman Bagian 2

- Interface adalah daftar method tanpa isi, dipakai lewat `implements`.
- Kelas yang tidak berkerabat bisa memakai interface yang sama.
- Satu kelas boleh `implements` banyak interface.

Selanjutnya: Bagian 3 membahas cara memilih antara kelas abstrak dan interface.

---

<!-- _class: divider -->

# Bagian 3
## Kelas Abstrak atau Interface?

Sesi 3 dari 4

---

## Dua Pertanyaan Sederhana

1. Apakah kelas-kelas ini satu keluarga, dan berbagi atribut atau method yang sama? Pakai **kelas abstrak**.
2. Apakah ini kemampuan tambahan yang bisa dimiliki kelas apa pun? Pakai **interface**.

<div class="tip-box">
Kelas abstrak menjawab "ini jenis apa?". Interface menjawab "ini bisa melakukan apa?".
</div>

---

## Kelas Abstrak vs Interface

| | Kelas Abstrak | Interface |
|---|---|---|
| Kata kunci di subclass | `extends` | `implements` |
| Jumlah per kelas | hanya satu | boleh banyak |
| Isinya | atribut, method biasa, method abstrak | daftar method tanpa isi |
| Maknanya | "adalah jenis dari" | "bisa melakukan" |

---

## Contoh Kode: Keduanya Dipakai Bersama

```java
abstract class Vehicle {
    public abstract String honk();
}

class ElectricCar extends Vehicle implements Chargeable {
    @Override public String honk() { return "Beep!"; }
    @Override public void charge() { System.out.println("Car is charging"); }
}
```

---

## Telusuri: Satu Objek, Dua Peran

```java
ElectricCar car = new ElectricCar();
Vehicle v = car;        // car adalah Vehicle
Chargeable c = car;     // car bisa diisi daya
System.out.println(v.honk());
c.charge();
```

Output: `Beep!` lalu `Car is charging`. Objeknya satu, tetapi bisa dipegang lewat dua tipe variabel.

---

## Kesalahan Umum: Kemampuan Khusus Ditaruh di Superclass

<div class="warn-box">
<b>Salah:</b> menambah <code>abstract void charge()</code> ke <code>Vehicle</code>. Akibatnya <code>Bicycle</code> ikut wajib mengisi <code>charge()</code>, padahal sepeda tidak punya baterai.
</div>

**Benar:** kemampuan yang hanya dimiliki sebagian subclass dijadikan interface. Hanya kelas yang butuh yang menyatakan `implements Chargeable`.

---

## Latihan

Pilih **kelas abstrak** atau **interface**:

1. `Animal` sebagai induk `Cat` dan `Dog`, dengan atribut `name` yang dipakai bersama.
2. Kemampuan "bisa dicetak" (`print()`) untuk `Invoice`, `Photo`, dan `Ticket`.
3. `Employee` sebagai induk `Manager` dan `Staff`, dengan atribut `baseSalary` yang dipakai bersama.

---

## Jawaban Latihan

1. **Kelas abstrak.** `Cat` dan `Dog` satu keluarga dan berbagi atribut `name`.
2. **Interface.** `Invoice`, `Photo`, dan `Ticket` tidak berkerabat; "bisa dicetak" adalah kemampuan tambahan.
3. **Kelas abstrak.** `Manager` dan `Staff` satu keluarga dan berbagi atribut `baseSalary`.

---

## Rangkuman Bagian 3

- Satu keluarga dengan atribut bersama: kelas abstrak.
- Kemampuan tambahan untuk kelas apa pun: interface.
- Satu kelas boleh `extends` satu kelas abstrak sekaligus `implements` beberapa interface.

Selanjutnya: Bagian 4 memakai keduanya pada koleksi perpustakaan.

---

<!-- _class: divider -->

# Bagian 4
## Studi Kasus: Koleksi Perpustakaan

Sesi 4 dari 4

---

## Kembali ke Perpustakaan

Pada pertemuan sebelumnya, `Book`, `Dvd`, dan `Magazine` mewarisi `LibraryItem`. Ada tiga hal yang belum beres:

1. `new LibraryItem("?")` masih bisa dibuat, padahal tidak ada "koleksi umum" di rak.
2. Tiap jenis koleksi punya denda keterlambatan sendiri, tetapi tidak ada yang mewajibkannya.
3. Hanya DVD yang bisa diputar. Buku dan majalah tidak.

Nomor 1 dan 2 diselesaikan dengan kelas abstrak. Nomor 3 diselesaikan dengan interface.

---

## Diagram Kelas: Kelas Abstrak dan Interface Bersama

![h:300 LibraryItem abstrak dengan lateFeePerDay, tiga subclass mengisinya, Dvd juga mengimplementasikan Playable](../assets/uml/p09-libraryitem-abstract.png)

`lateFeePerDay()` abstrak di `LibraryItem`, lalu diisi ketiga subclass. Hanya `Dvd` yang punya panah putus-putus ke `Playable`.

---

## Contoh Kode: `LibraryItem` Menjadi Abstrak

```java
abstract class LibraryItem {
    protected String title;
    public LibraryItem(String title) { this.title = title; }

    public abstract int lateFeePerDay();   // wajib diisi tiap jenis koleksi
}
```

---

## Contoh Kode: `Book` Mengisi `lateFeePerDay()`

```java
class Book extends LibraryItem {
    public Book(String title) { super(title); }

    @Override
    public int lateFeePerDay() { return 1000; }
}
```

`Dvd` mengikuti pola yang sama, dengan denda 5000 per hari.

---

## Contoh Kode: `Dvd` Juga `Playable`

```java
interface Playable {
    void play();
}

class Dvd extends LibraryItem implements Playable {
    public Dvd(String title) { super(title); }
    @Override public int lateFeePerDay() { return 5000; }
    @Override public void play() { System.out.println("Playing " + title); }
}
```

---

## Telusuri: Denda Tiap Koleksi

```java
LibraryItem[] items = { new Book("Dune"), new Dvd("Inception") };
for (LibraryItem item : items) {
    System.out.println(item.title + ": " + item.lateFeePerDay());
}
```

```
Dune: 1000
Inception: 5000
```

Tiap objek menjalankan `lateFeePerDay()` miliknya. `new LibraryItem("?")` kini ditolak compiler.

---

## Kesalahan Umum: `play()` Ditaruh di `LibraryItem`

<div class="warn-box">
<b>Salah:</b> menambah <code>abstract void play()</code> ke <code>LibraryItem</code>. Akibatnya <code>Book</code> dan <code>Magazine</code> wajib mengisi <code>play()</code>, padahal keduanya tidak bisa diputar.
</div>

**Benar:** `play()` ditaruh di interface `Playable`. Hanya `Dvd` yang menyatakan `implements Playable`.

---

## Latihan

1. Lengkapi supaya denda majalah 500 per hari:

```java
class Magazine ________ LibraryItem {
    public Magazine(String title) { super(title); }
    @Override
    public int lateFeePerDay() { return ___; }
}
```

2. Perlukah `Magazine` menyatakan `implements Playable`? Ya atau tidak?

---

## Jawaban Latihan

```java
class Magazine extends LibraryItem {
    public Magazine(String title) { super(title); }
    @Override
    public int lateFeePerDay() { return 500; }
}
```

**Tidak.** Majalah tidak bisa diputar, jadi tidak perlu berjanji mengisi `play()`.

---

## Rangkuman Bagian 4

- `LibraryItem` menjadi abstrak: objek "koleksi umum" tidak bisa lagi dibuat.
- `lateFeePerDay()` abstrak, sehingga tiap jenis koleksi wajib menentukan dendanya.
- `Playable` adalah interface: hanya `Dvd` yang memakainya.

---

## Rangkuman Pertemuan 9

| | Kelas Abstrak | Interface |
|---|---|---|
| Dipakai untuk | satu keluarga kelas | kemampuan tambahan |
| Kata kunci | `extends` (hanya satu) | `implements` (boleh banyak) |
| Isinya | atribut, method biasa, method abstrak | daftar method tanpa isi |
| Contoh hari ini | `Shape`, `LibraryItem` | `Chargeable`, `Playable` |

Keduanya memindahkan kesalahan ke saat kompilasi: method yang belum diisi langsung ditolak compiler.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Object-Oriented Programming: Polymorphism and Interfaces

Oracle Java Tutorials: "Abstract Methods and Classes", "Interfaces"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 9

---

## Tugas: Koleksi Perpustakaan

Perpustakaan menambah `AudioBook` (buku audio) dan aturan baru: hanya buku dan majalah yang pinjamannya bisa diperpanjang.

1. `AudioBook` adalah jenis koleksi yang bisa diputar. Tuliskan baris deklarasi kelasnya (`class AudioBook ...`).
2. Buat interface `Renewable` dengan satu method. Kelas mana saja yang menyatakan `implements Renewable`?
3. Gambarkan diagram kelas lengkapnya di kertas, dengan panah yang tepat untuk `extends` dan `implements`.

---

## Tugas: Studi Kasusmu Sendiri

Pakai kembali hierarki kelas dari tugas pertemuan sebelumnya (aplikasi pilihanmu sendiri).

1. Jadikan superclass-nya kelas abstrak, dengan satu method abstrak yang wajib diisi tiap subclass.
2. Tambahkan satu interface untuk kemampuan yang hanya dimiliki sebagian subclass.
3. Perbarui diagram kelasmu di kertas, lalu jelaskan dalam satu kalimat mengapa kemampuan itu dijadikan interface.
