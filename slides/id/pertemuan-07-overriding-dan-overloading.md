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

Pertemuan 7: **Overriding dan Overloading**

Mengganti perilaku warisan, dan memberi satu nama untuk beberapa bentuk pemanggilan

---

## Yang Akan Kamu Pelajari

- Cara subclass mengganti perilaku method yang diwarisinya (overriding)
- Cara memakai kembali perilaku lama lewat `super`, dan menguncinya lewat `final`
- Cara membuat output `println(objek)` mudah dibaca lewat `toString()`
- Cara memberi satu nama method untuk beberapa bentuk pemanggilan (overloading)
- Perbedaan overriding dan overloading, diterapkan pada satu studi kasus

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 7.
</div>

---

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Method overriding
- **Sesi 2 (50')**: `super`, `final`, dan `toString()`
- **Sesi 3 (50')**: Method overloading
- **Sesi 4 (50')**: Studi kasus, koleksi perpustakaan

---

<!-- _class: divider -->

# Bagian 1
## Method Overriding

Sesi 1 dari 4

---

## Masalah: Semua Kendaraan Berbunyi Sama

```java
class Vehicle {
    public String honk() { return "Beep!"; }
}

class Sedan extends Vehicle { }
class Truck extends Vehicle { }
```

`Sedan` dan `Truck` mewarisi `honk()` apa adanya. Keduanya berbunyi "Beep!", padahal klakson truk seharusnya berbeda.

---

## Apa Itu Overriding?

Bayangkan resep keluarga. Seorang anak memasak hidangan dengan nama yang sama, tetapi memakai resepnya sendiri.

<div class="term-box">
<b>Overriding</b> adalah menulis ulang method warisan di dalam subclass. Nama dan parameternya tetap sama, hanya isinya yang berbeda.
</div>

---

## Mengapa Ini Penting?

Sebuah aplikasi pembayaran punya banyak jenis metode: kartu kredit, transfer bank, e-wallet. Jenis baru terus bertambah. Tanpa overriding, kode pemroses pembayaran harus diubah setiap kali ada jenis baru, dan perubahan itu bisa merusak jenis lain yang sudah berjalan.

<div class="term-box">
Dengan overriding, setiap subclass membawa perilakunya sendiri. Kode lama tidak perlu disentuh. Prinsip ini bernama Open/Closed Principle, dibahas pada Pertemuan 11.
</div>

---

## Nama Lengkap Method: Signature

![h:200 Anatomi signature: honk(int times), nama method dan daftar parameter, terpisah dari visibility dan return type](../assets/illustrations/method-signature-anatomy.svg)

<div class="term-box">
<b>Signature</b> adalah nama method ditambah daftar parameternya. Dua method dianggap sama bila signature-nya sama.
</div>

---

## Overriding pada Diagram Kelas

![h:260 Diagram kelas Vehicle, Sedan, dan Truck, dengan honk() muncul lagi di kedua subclass](../assets/uml/p06-vehicle.png)

Method yang muncul lagi di kotak subclass berarti di-override. `honk()` tertulis di `Vehicle`, lalu tertulis lagi di `Sedan` dan `Truck`.

---

## Contoh Kode: Truck Meng-override `honk()`

```java
class Vehicle {
    public String honk() { return "Beep!"; }
}

class Truck extends Vehicle {
    @Override
    public String honk() { return "Tin tin!"; }   // isi baru
}
```

---

## Telusuri Langkah demi Langkah

```java
Truck truck = new Truck();
System.out.println(truck.honk());
```

1. Java melihat objeknya: sebuah `Truck`.
2. Java mencari `honk()` di kelas `Truck`, dan menemukannya.
3. Versi milik `Truck` yang dijalankan.

Output: `Tin tin!`

---

## Anotasi `@Override`: Pengaman dari Salah Ketik

```java
class Truck extends Vehicle {
    @Override
    public String hunk() { return "Tin tin!"; }   // salah ketik
}
```

<div class="tip-box">
Dengan <code>@Override</code>, compiler langsung menampilkan error, sebab <code>Vehicle</code> tidak punya method <code>hunk()</code>. Tanpa <code>@Override</code>, kesalahan ini lolos dan truk tetap berbunyi "Beep!".
</div>

---

## Objek yang Menentukan, Bukan Tipe Variabel

```java
Vehicle v = new Truck();
System.out.println(v.honk());   // Tin tin!
```

Variabel `v` bertipe `Vehicle`, tetapi objek di dalamnya adalah `Truck`. Java menjalankan versi milik objeknya.

<div class="tip-box">
Sifat ini menjadi dasar topik Polimorfisme pada Pertemuan 10.
</div>

---

## Kesalahan Umum: Parameter Berbeda Bukan Override

<div class="warn-box">
<b>Salah:</b> menulis <code>public String honk(String mode)</code> di <code>Truck</code>, lalu mengira <code>honk()</code> milik <code>Vehicle</code> sudah diganti.
</div>

**Benar:** parameternya berbeda, jadi signature-nya berbeda. Itu method baru, bukan override. Pasang `@Override` supaya compiler menangkap kesalahan ini.

---

## Latihan

`Vehicle.honk()` mengembalikan `"Beep!"`. `Truck` meng-override-nya menjadi `"Tin tin!"`. Tebak output program berikut:

```java
Vehicle a = new Vehicle();
Vehicle b = new Truck();
System.out.println(a.honk());
System.out.println(b.honk());
```

---

## Jawaban Latihan

```
Beep!
Tin tin!
```

Objek `a` adalah `Vehicle`, jadi versi `Vehicle` yang dijalankan. Objek `b` adalah `Truck`, jadi versi `Truck` yang dijalankan, walaupun variabelnya bertipe `Vehicle`.

---

## Rangkuman Bagian 1

- Overriding menulis ulang method warisan di subclass, dengan signature yang sama.
- `@Override` membuat compiler memeriksa bahwa method itu benar-benar ada di superclass.
- Versi yang dijalankan ditentukan oleh objeknya, bukan oleh tipe variabelnya.

Selanjutnya: Bagian 2 membahas cara memakai kembali perilaku lama, dan cara menguncinya.

---

<!-- _class: divider -->

# Bagian 2
## super, final, dan toString()

Sesi 2 dari 4

---

## `super.method(...)`: Resep Lama Ditambah Satu Bahan

Kadang subclass tidak ingin mengganti seluruh perilaku lama. Subclass hanya ingin menambah sedikit, seperti memakai resep orang tua lalu menambah satu bahan.

<div class="term-box">
<code>super.namaMethod(...)</code> memanggil versi milik superclass dari dalam method yang meng-override-nya.
</div>

---

## Contoh Kode: Memanggil `super.honk()`

```java
class Truck extends Vehicle {
    @Override
    public String honk() {
        return super.honk() + " (loud horn)";   // hasil lama + tambahan
    }
}
```

Output `new Truck().honk()`: `Beep! (loud horn)`

---

## Tiga Aturan Overriding

| Aturan | Benar | Salah |
|---|---|---|
| Signature harus sama persis | `honk()` menjadi `honk()` | `honk()` menjadi `honk(int times)` |
| Access modifier tidak boleh lebih sempit | `protected` menjadi `public` | `public` menjadi `private` |
| Method `private` dan `final` tidak bisa di-override | method biasa | method bertanda `final` |

Bila satu aturan dilanggar, compiler menampilkan error.

---

## `final`: Resep yang Tidak Boleh Diubah

```java
class Vehicle {
    public final String plateFormat() { return "N 1234 AB"; }
}
```

<div class="term-box">
Method bertanda <code>final</code> tidak bisa di-override. Pakai hanya bila perilakunya memang harus sama di semua subclass.
</div>

---

## Masalah: Output `println(objek)` Sulit Dibaca

```java
Sedan civic = new Sedan("Civic");
System.out.println(civic);   // Sedan@1b6d3586
```

`println` memanggil `toString()`, method yang diwarisi setiap kelas dari `Object`. Versi bawaannya hanya mencetak nama kelas dan hash code.

<div class="tip-box">
Karena <code>toString()</code> adalah method warisan, kita boleh meng-override-nya.
</div>

---

## Contoh Kode: Meng-override `toString()`

```java
class Vehicle {
    private String name;
    public Vehicle(String name) { this.name = name; }

    @Override
    public String toString() { return "Vehicle: " + name; }
}
```

Output `System.out.println(new Vehicle("Civic"))`: `Vehicle: Civic`

---

## Kesalahan Umum: Mempersempit Access Modifier

<div class="warn-box">
<b>Salah:</b> <code>Vehicle</code> punya <code>public String honk()</code>, lalu <code>Truck</code> menulis <code>private String honk()</code>.
</div>

**Benar:** override harus sama terbukanya, atau lebih terbuka. `public` tidak boleh menjadi `protected` atau `private`. Kode di atas gagal dikompilasi.

---

## Latihan

Tentukan **valid** atau **error** untuk tiap override berikut:

1. `Vehicle`: `public String honk()`. `Truck`: `protected String honk()`.
2. `Vehicle`: `public final String plateFormat()`. `Truck` menulis ulang `plateFormat()`.
3. `Truck`: `@Override public String honk() { return super.honk() + "!"; }`

---

## Jawaban Latihan

1. **Error.** `protected` lebih sempit daripada `public`.
2. **Error.** Method `final` tidak bisa di-override.
3. **Valid.** Output-nya `Beep!!`, yaitu hasil `super.honk()` ditambah satu tanda seru.

---

## Rangkuman Bagian 2

- `super.method(...)` memakai kembali perilaku superclass, lalu subclass menambah bagiannya sendiri.
- Tiga aturan override: signature sama, access modifier tidak lebih sempit, `private` dan `final` tidak bisa di-override.
- Meng-override `toString()` membuat output `println(objek)` mudah dibaca.

Selanjutnya: Bagian 3 membahas overloading, nama yang sama dengan parameter yang berbeda.

---

<!-- _class: divider -->

# Bagian 3
## Method Overloading

Sesi 3 dari 4

---

## Masalah: Nama Method Terus Bertambah

```java
class Vehicle {
    public String honkOnce() { return "Beep!"; }
    public String honkTimes(int times) { return "Beep!".repeat(times); }
    public String honkLoud(boolean loud) { return loud ? "BEEP!" : "Beep!"; }
}
```

Ketiganya melakukan hal yang sama, yaitu membunyikan klakson. Pemakai kelas ini harus menghafal tiga nama yang berbeda.

---

## Apa Itu Overloading?

Di kasir, satu kata "bayar" berlaku untuk tunai, kartu, dan QR. Kasir memilih caranya dari apa yang kamu serahkan.

<div class="term-box">
<b>Overloading</b> adalah beberapa method dengan nama yang sama, tetapi daftar parameter yang berbeda. Compiler memilih versinya dari argumen yang diberikan.
</div>

---

## Contoh Kode: `honk()` dan `honk(int times)`

```java
class Vehicle {
    public String honk() { return "Beep!"; }

    public String honk(int times) {      // nama sama, parameter berbeda
        return honk().repeat(times);
    }
}
```

---

## Telusuri: Versi Mana yang Dipilih?

```java
Vehicle v = new Vehicle();
System.out.println(v.honk());    // tanpa argumen
System.out.println(v.honk(3));   // satu argumen int
```

1. `v.honk()` tidak membawa argumen, compiler memilih `honk()`.
2. `v.honk(3)` membawa satu `int`, compiler memilih `honk(int times)`.

Output: `Beep!` lalu `Beep!Beep!Beep!`

---

## Mengapa Ini Penting?

Tanpa overloading, mencetak ke layar butuh `printString()`, `printInt()`, `printDouble()`, dan seterusnya. Programmer harus mengingat nama yang berbeda untuk tiap tipe data.

<div class="term-box">
Berkat overloading, satu nama <code>println(...)</code> cukup untuk semua tipe data. Kelas yang kita tulis pun lebih mudah dipakai orang lain bila mengikuti cara yang sama.
</div>

---

## Constructor Juga Bisa Di-overload

```java
class Vehicle {
    private String name;
    private int wheels;

    public Vehicle(String name) { this(name, 4); }   // memanggil constructor di bawah
    public Vehicle(String name, int wheels) { this.name = name; this.wheels = wheels; }
}
```

`new Vehicle("Civic")` dan `new Vehicle("Hino", 6)` sama-sama valid. `this(...)` memanggil constructor lain di kelas yang sama.

---

## Overriding vs Overloading

![h:300 Perbandingan overriding dan overloading](../assets/illustrations/override-vs-overload.svg)

Overriding mengganti isi method warisan. Overloading menambah versi baru dengan parameter yang berbeda.

---

## Kesalahan Umum: Hanya Tipe Kembalian yang Berbeda

<div class="warn-box">
<b>Salah:</b> menulis <code>public String honk()</code> dan <code>public int honk()</code> di kelas yang sama, lalu mengira keduanya overload yang valid.
</div>

**Benar:** tipe kembalian tidak termasuk signature. Kedua method itu punya signature yang sama, sehingga compiler menampilkan error. Yang harus berbeda adalah daftar parameternya.

---

## Latihan

Di kelas yang sama, tentukan **overload valid** atau **error**:

1. `honk()` dan `honk(int times)`
2. `String getName()` dan `int getName()`
3. `setScore(int score)` dan `setScore(double score)`

---

## Jawaban Latihan

1. **Valid.** Jumlah parameternya berbeda.
2. **Error.** Parameternya sama (kosong), hanya tipe kembaliannya yang berbeda.
3. **Valid.** Tipe parameternya berbeda, `int` dan `double`.

---

## Rangkuman Bagian 3

- Overloading: nama method sama, daftar parameter berbeda.
- Compiler memilih versinya dari jumlah dan tipe argumen saat pemanggilan.
- Constructor juga bisa di-overload; `this(...)` memanggil constructor lain di kelas yang sama.

Selanjutnya: Bagian 4 memakai overriding dan overloading pada koleksi perpustakaan.

---

<!-- _class: divider -->

# Bagian 4
## Studi Kasus: Koleksi Perpustakaan

Sesi 4 dari 4

---

## Kembali ke Perpustakaan

Pada Pertemuan 6, `Book`, `Dvd`, dan `Magazine` mewarisi `LibraryItem`. Ada tiga hal yang belum beres:

1. Semua koleksi dipinjam 14 hari, padahal DVD seharusnya 7 hari.
2. `describe()` hanya mencetak judul dan tahun, belum menyebut halaman atau durasi.
3. Perpanjangan pinjaman butuh dua cara: bawaan 7 hari, atau jumlah hari tertentu.

Nomor 1 dan 2 diselesaikan dengan overriding. Nomor 3 diselesaikan dengan overloading.

---

## Diagram Kelas: Siapa Meng-override Apa

![h:300 LibraryItem dengan loanDays, describe, dan dua versi extendLoan; Book, Dvd, dan Magazine menulis ulang sebagian method](../assets/uml/p07-libraryitem-override.png)

`loanDays()` muncul lagi di `Dvd`. `describe()` muncul lagi di ketiga subclass. `extendLoan` tertulis dua kali di `LibraryItem` dengan parameter berbeda.

---

## Contoh Kode: `Dvd` Meng-override `loanDays()`

```java
class LibraryItem {
    public int loanDays() { return 14; }
}

class Dvd extends LibraryItem {
    @Override
    public int loanDays() { return 7; }   // khusus DVD
}
```

---

## Contoh Kode: `describe()` dengan `super`

```java
class LibraryItem {
    public String describe() { return title + " (" + year + ")"; }
}

class Book extends LibraryItem {
    @Override
    public String describe() { return super.describe() + ", " + pages + " pages"; }
}
```

Output untuk buku Dune: `Dune (1965), 412 pages`

---

## Contoh Kode: Dua Versi `extendLoan`

```java
class LibraryItem {
    private int dueInDays = 14;

    public void extendLoan() { extendLoan(7); }            // tanpa angka: tambah 7 hari
    public void extendLoan(int days) { dueInDays += days; }
}
```

`item.extendLoan()` menambah 7 hari. `item.extendLoan(3)` menambah 3 hari.

---

## Telusuri: Satu Koleksi, Output Berbeda

```java
LibraryItem[] items = { new Book("Dune", 1965, 412), new Dvd("Inception", 2010, 148) };
for (LibraryItem item : items) {
    System.out.println(item.describe() + ": " + item.loanDays() + " days");
}
```

```
Dune (1965), 412 pages: 14 days
Inception (2010), 148 min: 7 days
```

Kode perulangan tidak berubah dari Pertemuan 6. Tiap objek menjalankan versinya sendiri.

---

## Kesalahan Umum: Overload yang Dikira Override

<div class="warn-box">
<b>Salah:</b> <code>Dvd</code> menulis <code>public int loanDays(int extra) { return 7; }</code> tanpa <code>@Override</code>, lalu heran karena DVD masih dipinjam 14 hari.
</div>

**Benar:** parameternya berbeda, jadi itu overload, bukan override. Perulangan memanggil `loanDays()` tanpa argumen, dan versi itu masih milik `LibraryItem`. `@Override` akan menangkap kesalahan ini.

---

## Latihan

1. Lengkapi supaya majalah dipinjam 3 hari:

```java
class Magazine extends LibraryItem {
    ________
    public int loanDays() { return ___; }
}
```

2. Versi `extendLoan` mana yang dipilih untuk `item.extendLoan()` dan `item.extendLoan(5)`?

---

## Jawaban Latihan

```java
class Magazine extends LibraryItem {
    @Override
    public int loanDays() { return 3; }
}
```

`item.extendLoan()` memilih versi tanpa parameter. `item.extendLoan(5)` memilih versi `extendLoan(int days)`.

---

## Rangkuman Bagian 4

- `Dvd` meng-override `loanDays()`, sehingga DVD dipinjam 7 hari tanpa mengubah `LibraryItem`.
- `describe()` di subclass memakai `super.describe()`, lalu menambah datanya sendiri.
- `extendLoan()` dan `extendLoan(int days)` adalah overload: satu nama, dua bentuk pemanggilan.

---

## Rangkuman Pertemuan 7

| | Overriding | Overloading |
|---|---|---|
| Ditulis di | subclass | kelas yang sama |
| Signature | sama persis | nama sama, parameter berbeda |
| Yang menentukan versi | objek yang sebenarnya | argumen saat pemanggilan |
| Kapan ditentukan | saat program berjalan | saat kompilasi |

`@Override` menjaga dari salah ketik, `super.method(...)` memakai kembali perilaku lama, `final` mengunci sebuah method.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Object-Oriented Programming: Inheritance dan Polymorphism

Oracle Java Tutorials: "Overriding and Hiding Methods", "Defining Methods" (overloading)

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 7

---

## Tugas: Koleksi Perpustakaan

Perpustakaan menambah `AudioBook`, subclass dari `LibraryItem`, dengan atribut `durationMinutes` dan `narrator`. Buku audio dipinjam 10 hari.

1. Method mana yang perlu di-override di `AudioBook`? Tuliskan signature-nya.
2. Tambahkan satu overload yang menurutmu berguna, lalu jelaskan kapan versi itu dipakai.
3. Gambarkan diagram kelas `AudioBook` di kertas, lengkap dengan panah ke `LibraryItem`.

---

## Tugas: Studi Kasusmu Sendiri

Pakai kembali hierarki kelas dari tugas Pertemuan 6 (aplikasi pilihanmu sendiri).

1. Pilih satu method di superclass, lalu tulis override-nya di salah satu subclass. Pakai `super.method(...)` di dalamnya.
2. Tambahkan satu pasang overload pada salah satu kelas.
3. Perbarui diagram kelasmu di kertas, lalu tuliskan output yang kamu harapkan dari satu pemanggilan tiap method.
