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

Satu perintah, banyak perilaku; kegagalan yang tidak boleh didiamkan

---

## Yang Akan Kamu Pelajari

- Cara satu pemanggilan method menghasilkan perilaku berbeda (polimorfisme)
- Cara memeriksa jenis objek dengan aman lewat `instanceof`
- Cara menangani masalah saat program berjalan lewat `try` dan `catch`
- Cara membuat dan melempar exception sendiri lewat `throw`
- Penerapan semuanya pada satu studi kasus

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 10.
</div>

---

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Polimorfisme dan `instanceof`
- **Sesi 2 (50')**: Exception handling dengan `try` dan `catch`
- **Sesi 3 (50')**: Melempar exception sendiri
- **Sesi 4 (50')**: Studi kasus, koleksi perpustakaan

---

<!-- _class: divider -->

# Bagian 1
## Polimorfisme

Sesi 1 dari 4

---

## Masalah: Satu Cabang untuk Tiap Jenis

```java
for (Shape s : shapes) {
    if (s instanceof Circle) { /* rumus lingkaran */ }
    else if (s instanceof Square) { /* rumus persegi */ }
    // ada jenis baru? tambah cabang lagi
}
```

Setiap ada jenis bentuk baru, cabang `if` harus ditambah. Kode seperti ini bisa tersebar di banyak tempat.

---

## Apa Itu Polimorfisme?

Seorang pelatih berteriak "mulai!". Perenang mulai berenang, pelari mulai berlari. Perintahnya satu, tiap atlet menjalankannya dengan caranya sendiri.

<div class="term-box">
<b>Polimorfisme</b> berarti satu pemanggilan method yang sama menjalankan versi milik objek yang menerimanya. Versi itu dipilih saat program berjalan.
</div>

---

## Mengapa Ini Penting?

Sebuah toko online punya puluhan jenis produk. Tiap jenis menghitung ongkos kirim dengan caranya sendiri. Tanpa polimorfisme, setiap jenis baru berarti mencari dan mengubah semua cabang `if` di seluruh aplikasi. Satu saja terlewat, ongkos kirim pelanggan salah.

<div class="term-box">
Dengan polimorfisme, kode pemanggil cukup menulis <code>product.shippingCost()</code>. Objeknya sendiri yang tahu cara menghitungnya. Menambah jenis baru tidak mengubah kode lama.
</div>

---

## Satu Pemanggilan, Dua Hasil

![h:300 Satu titik pemanggilan area() yang diselesaikan secara berbeda-beda saat program berjalan](../assets/illustrations/polymorphic-dispatch.svg)

Pemanggilan `s.area()` hanya ditulis satu kali. Untuk `Circle`, Java menjalankan rumus lingkaran. Untuk `Square`, Java menjalankan rumus persegi.

---

## Contoh Kode: Tanpa `if` Sama Sekali

```java
Shape[] shapes = { new Circle("c1", 2), new Square("s1", 4) };

for (Shape s : shapes) {
    System.out.println(s.area());   // satu baris untuk semua jenis
}
```

---

## Telusuri Langkah demi Langkah

1. Putaran pertama: `s` memegang objek `Circle`. Java menjalankan `area()` milik `Circle`: 3.14 x 2 x 2.
2. Putaran kedua: `s` memegang objek `Square`. Java menjalankan `area()` milik `Square`: 4 x 4.

```
12.56
16.0
```

Tipe variabelnya selalu `Shape`. Yang menentukan hasil adalah objeknya.

---

## Tiga Bahan Polimorfisme

1. Ada hubungan keluarga: `extends` atau `implements`.
2. Subclass mengisi atau meng-override method yang sama.
3. Objek dipegang lewat variabel bertipe superclass atau interface.

<div class="tip-box">
Ketiga bahan ini sudah kamu pelajari pada topik Inheritance, Overriding, serta Kelas Abstrak dan Interface. Polimorfisme adalah hasil gabungannya.
</div>

---

## Memeriksa Jenis Objek: `instanceof`

Kadang kode perlu tahu jenis objeknya, misalnya untuk memanggil method yang hanya dimiliki `Circle`. Ibarat petugas yang memeriksa kartu anggota sebelum memberi layanan khusus.

```java
if (s instanceof Circle) {
    Circle c = (Circle) s;   // downcasting
    System.out.println(c.getRadius());
}
```

---

## Contoh Kode: `instanceof` yang Lebih Ringkas

```java
for (Shape s : shapes) {
    if (s instanceof Circle c) {              // periksa, lalu langsung dapat variabel c
        System.out.println("radius: " + c.getRadius());
    }
}
```

Bentuk ini disebut pattern matching. Output-nya `radius: 2.0`, hanya untuk elemen yang benar-benar `Circle`.

---

## Kesalahan Umum: `if` Padahal Bisa Polimorfik

<div class="warn-box">
<b>Salah:</b> memeriksa <code>instanceof Circle</code> lalu <code>instanceof Square</code> untuk menghitung luas, padahal keduanya sudah punya <code>area()</code>.
</div>

**Benar:** cukup panggil `s.area()`. Pakai `instanceof` hanya untuk kemampuan yang tidak dimiliki semua subclass.

---

## Latihan

`Circle.area()` menghitung 3.14 x radius x radius. `Square.area()` menghitung side x side. Tebak output program berikut:

```java
Shape[] shapes = { new Square("s1", 5), new Circle("c1", 1) };
for (Shape s : shapes) {
    System.out.println(s.area());
}
```

---

## Jawaban Latihan

```
25.0
3.14
```

Elemen pertama adalah `Square`, jadi 5 x 5. Elemen kedua adalah `Circle`, jadi 3.14 x 1 x 1. Urutan output mengikuti urutan objek di dalam array.

---

## Rangkuman Bagian 1

- Polimorfisme: satu pemanggilan method, versi yang dijalankan mengikuti objeknya.
- Kode pemanggil tidak perlu cabang `if` untuk tiap jenis objek.
- `instanceof` dipakai seperlunya, untuk kemampuan yang tidak dimiliki semua subclass.

Selanjutnya: Bagian 2 membahas apa yang terjadi ketika program menemui masalah saat berjalan.

---

<!-- _class: divider -->

# Bagian 2
## Exception Handling

Sesi 2 dari 4

---

## Masalah: Program Berhenti Mendadak

```java
int[] scores = { 80, 90 };
System.out.println(scores[5]);   // indeks 5 tidak ada
System.out.println("done");      // tidak pernah dijalankan
```

Program berhenti di baris kedua dengan pesan `ArrayIndexOutOfBoundsException`. Baris sesudahnya tidak dijalankan.

---

## Apa Itu Exception?

Bayangkan alarm kebakaran. Saat ada masalah, alarm berbunyi dan semua kegiatan berhenti sampai ada yang menanganinya.

<div class="term-box">
<b>Exception</b> adalah objek yang menandakan ada masalah saat program berjalan. Bila tidak ada yang menanganinya, program berhenti.
</div>

---

## Mengapa Ini Penting?

Sebuah sistem keuangan diam-diam mengubah jumlah transfer yang salah menjadi nol, tanpa pemberitahuan. Beberapa minggu kemudian laporan keuangan tidak seimbang. Penyebabnya sudah tenggelam di antara ribuan transaksi dan sangat sulit dilacak.

<div class="term-box">
Exception membuat masalah muncul tepat di tempat ia terjadi. Masalah tidak bisa lagi lewat diam-diam.
</div>

---

## `try` dan `catch`: Coba, dan Siapkan Rencana Cadangan

<div class="term-box">
Kode yang mungkin bermasalah ditaruh di dalam blok <code>try</code>. Bila exception terjadi, sisa blok <code>try</code> dilewati dan blok <code>catch</code> yang dijalankan. Setelah itu program lanjut seperti biasa.
</div>

---

## Contoh Kode: Menangkap Exception

```java
try {
    System.out.println(scores[5]);
    System.out.println("this line is skipped");
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Failed: " + e.getMessage());
}
System.out.println("done");
```

---

## Telusuri Langkah demi Langkah

1. `scores[5]` gagal. Java membuat objek exception.
2. Sisa blok `try` dilewati.
3. Blok `catch` dijalankan. `e.getMessage()` berisi penjelasan masalahnya.
4. Program lanjut ke baris setelah `try`/`catch`.

```
Failed: Index 5 out of bounds for length 2
done
```

---

## Kesalahan Umum: Blok `catch` Kosong

<div class="warn-box">
<b>Salah:</b> menulis <code>catch (Exception e) { }</code> tanpa isi apa pun, supaya program tidak berhenti.
</div>

**Benar:** blok `catch` kosong menyembunyikan masalah. Setidaknya cetak `e.getMessage()`, supaya ada yang tahu bahwa sesuatu gagal.

---

## Latihan

Tebak output program berikut:

```java
int[] data = { 1, 2, 3 };
try {
    System.out.println(data[0]);
    System.out.println(data[9]);
    System.out.println("end of try");
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("caught");
}
```

---

## Jawaban Latihan

```
1
caught
```

`data[0]` berhasil dan mencetak 1. `data[9]` gagal, jadi `"end of try"` dilewati dan blok `catch` dijalankan.

---

## Rangkuman Bagian 2

- Exception adalah objek yang menandakan masalah saat program berjalan.
- Kode berisiko ditaruh di `try`; rencana cadangannya di `catch`.
- Jangan biarkan blok `catch` kosong.

Selanjutnya: Bagian 3 membahas cara melempar exception dari method yang kita tulis sendiri.

---

<!-- _class: divider -->

# Bagian 3
## Melempar Exception Sendiri

Sesi 3 dari 4

---

## Masalah: Kegagalan yang Didiamkan

```java
class Grade {
    private int score;
    public void setScore(int score) {
        if (score > 100) { score = 100; }   // diam-diam diubah
        this.score = score;
    }
}
```

`setScore(150)` tidak menolak nilai yang salah. Nilai itu diubah menjadi 100, dan pemanggilnya tidak pernah tahu.

---

## `throw`: Mengangkat Tangan dan Melapor

Seorang petugas yang menemukan formulir salah tidak memperbaikinya diam-diam. Ia mengangkat tangan dan melapor, lalu pekerjaannya berhenti di situ.

<div class="term-box">
<code>throw</code> melempar sebuah objek exception. Method berhenti saat itu juga, dan masalahnya diserahkan kepada kode pemanggil.
</div>

---

## Perjalanan Sebuah Exception

![h:300 Sebuah exception menghentikan method yang melemparnya dan diteruskan ke atas hingga tertangkap](../assets/illustrations/exception-throw-catch.svg)

Exception dilempar di dalam `setScore(150)`, lalu naik ke kode pemanggil sampai ada blok `catch` yang menangkapnya.

---

## Contoh Kode: Exception Buatan Sendiri

```java
class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);   // pesan disimpan oleh Exception
    }
}
```

Cukup `extends Exception` dan satu constructor. Nama kelasnya sudah menjelaskan jenis masalahnya.

---

## Exception pada Diagram Kelas

![h:260 Exception, InvalidScoreException, dan Grade yang melemparnya](../assets/uml/p10-invalidscore-exception.png)

`InvalidScoreException` adalah subclass dari `Exception`. Panah putus-putus berlabel "throws" berarti `Grade` bisa melempar exception itu.

---

## Contoh Kode: `setScore()` Melempar Exception

```java
public void setScore(int score) throws InvalidScoreException {
    if (score < 0 || score > 100) {
        throw new InvalidScoreException("Score must be 0-100: " + score);
    }
    this.score = score;
}
```

Kata `throws` pada signature memberi tahu pemanggil bahwa method ini bisa gagal.

---

## Telusuri Langkah demi Langkah

```java
try {
    grade.setScore(150);
    System.out.println("saved");
} catch (InvalidScoreException e) {
    System.out.println("Failed: " + e.getMessage());
}
```

1. `setScore(150)` menemukan nilai di luar 0-100, lalu melempar exception.
2. Baris `this.score = score` tidak dijalankan. Nilai lama tetap aman.
3. `"saved"` dilewati, blok `catch` dijalankan.

Output: `Failed: Score must be 0-100: 150`

---

## Exception yang Wajib Ditangani

<div class="term-box">
Exception yang <code>extends Exception</code> wajib ditangani. Pemanggil punya dua pilihan: membungkus pemanggilan dengan <code>try</code>/<code>catch</code>, atau meneruskannya dengan menulis <code>throws</code> pada method-nya sendiri.
</div>

Bila keduanya tidak dilakukan, compiler menolak kode itu.

---

## Kesalahan Umum: Lupa `try`/`catch`

<div class="warn-box">
<b>Salah:</b> memanggil <code>grade.setScore(150);</code> begitu saja, tanpa <code>try</code>/<code>catch</code> dan tanpa <code>throws</code>.
</div>

**Benar:** compiler menampilkan error `unreported exception InvalidScoreException; must be caught or declared to be thrown`. Bungkus pemanggilan itu dengan `try`/`catch`.

---

## Latihan

`setScore(int)` dideklarasikan `throws InvalidScoreException`. Tentukan **valid** atau **error**:

1. `grade.setScore(90);` tanpa `try`/`catch`, di dalam `main` biasa.
2. `try { grade.setScore(90); } catch (InvalidScoreException e) { System.out.println(e.getMessage()); }`
3. `void update(Grade g) throws InvalidScoreException { g.setScore(90); }`

---

## Jawaban Latihan

1. **Error.** Walaupun 90 nilai yang benar, compiler tetap mewajibkan penanganan.
2. **Valid.** Pemanggilan dibungkus `try`/`catch`.
3. **Valid.** Method `update` meneruskan exception lewat `throws`.

---

## Rangkuman Bagian 3

- `throw` melempar exception dan menghentikan method saat itu juga.
- Exception buatan sendiri cukup `extends Exception` dengan satu constructor.
- `throws` pada signature mewajibkan pemanggil memakai `try`/`catch` atau meneruskannya.

Selanjutnya: Bagian 4 memakai polimorfisme dan exception pada koleksi perpustakaan.

---

<!-- _class: divider -->

# Bagian 4
## Studi Kasus: Koleksi Perpustakaan

Sesi 4 dari 4

---

## Kembali ke Perpustakaan

`LibraryItem` sudah abstrak, dan `Dvd` sudah `Playable`. Ada tiga kebutuhan baru:

1. Mencetak denda semua koleksi tanpa cabang `if` untuk tiap jenis.
2. Memutar hanya koleksi yang bisa diputar.
3. Menolak peminjaman koleksi yang sedang dipinjam, dengan pesan yang jelas.

Nomor 1 memakai polimorfisme. Nomor 2 memakai `instanceof`. Nomor 3 memakai exception.

---

## Diagram Kelas: Koleksi dan Exception-nya

![h:300 LibraryItem abstrak dengan checkOut yang melempar ItemNotAvailableException, Book, Dvd, dan Playable](../assets/uml/p10-libraryitem-exception.png)

`ItemNotAvailableException` adalah subclass dari `Exception`. Panah "throws" menunjukkan `checkOut()` di `LibraryItem` bisa melemparnya.

---

## Telusuri: Denda dan Pemutaran

```java
for (LibraryItem item : items) {      // items berisi Book "Dune" dan Dvd "Inception"
    System.out.println(item.title + ": " + item.lateFeePerDay());
    if (item instanceof Playable p) { p.play(); }
}
```

```
Dune: 1000
Inception: 5000
Playing Inception
```

`lateFeePerDay()` dipanggil untuk semua koleksi. `play()` hanya untuk yang `Playable`.

---

## Contoh Kode: `checkOut()` Melempar Exception

```java
public void checkOut() throws ItemNotAvailableException {
    if (!available) {
        throw new ItemNotAvailableException(title + " is already on loan");
    }
    available = false;
}
```

---

## Telusuri: Meminjam Dua Kali

```java
Book dune = new Book("Dune");
try {
    dune.checkOut();
    dune.checkOut();
    System.out.println("borrowed twice");
} catch (ItemNotAvailableException e) {
    System.out.println("Failed: " + e.getMessage());
}
```

Pemanggilan pertama berhasil. Pemanggilan kedua melempar exception, jadi `"borrowed twice"` dilewati. Output: `Failed: Dune is already on loan`

---

## Kesalahan Umum: Memeriksa Kelas, Bukan Kemampuan

<div class="warn-box">
<b>Salah:</b> menulis <code>if (item instanceof Dvd)</code> untuk memutuskan kapan memanggil <code>play()</code>.
</div>

**Benar:** periksa `instanceof Playable`. Bila nanti ada `AudioBook` yang juga `Playable`, koleksi itu langsung ikut diputar tanpa mengubah kode perulangan.

---

## Latihan

1. `items` berisi `Magazine` "Tempo" (denda 500) dan `Dvd` "Inception" (denda 5000). Tebak output perulangan pada slide "Telusuri: Denda dan Pemutaran".
2. Apakah `dune.checkOut();` tanpa `try`/`catch` bisa dikompilasi? Ya atau tidak?

---

## Jawaban Latihan

```
Tempo: 500
Inception: 5000
Playing Inception
```

**Tidak.** `checkOut()` dideklarasikan `throws ItemNotAvailableException`, jadi pemanggilnya wajib memakai `try`/`catch` atau `throws`.

---

## Rangkuman Bagian 4

- Satu perulangan mencetak denda semua koleksi, tanpa cabang `if` per jenis.
- `instanceof Playable` memeriksa kemampuan, bukan nama kelas.
- `checkOut()` melempar exception, sehingga peminjaman ganda tidak bisa lewat diam-diam.

---

## Rangkuman Pertemuan 10

| | Polimorfisme | Exception Handling |
|---|---|---|
| Masalah yang diselesaikan | cabang `if` untuk tiap jenis objek | kegagalan yang didiamkan |
| Kata kunci | `@Override`, `instanceof` | `try`, `catch`, `throw`, `throws` |
| Yang menentukan | objek yang sebenarnya | method yang menemukan masalah |
| Contoh hari ini | `s.area()`, `item.lateFeePerDay()` | `setScore()`, `checkOut()` |

Keduanya membuat program bisa bertambah besar tanpa mengubah kode lama.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Polymorphism and Interfaces, Exception Handling

Oracle Java Tutorials: "Polymorphism", "Exceptions"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 10

---

## Tugas: Koleksi Perpustakaan

Perpustakaan menambah aturan: seorang anggota (`Member`) hanya boleh meminjam paling banyak 3 koleksi.

1. Buat exception `LoanLimitExceededException`. Tuliskan deklarasi kelasnya.
2. Tuliskan signature method `borrow(LibraryItem item)` di `Member` yang bisa melempar exception itu.
3. Tuliskan potongan `try`/`catch` yang memanggil `borrow(...)` dan mencetak pesan bila gagal.
4. Gambarkan diagram kelasnya di kertas, lengkap dengan panah "throws".

---

## Tugas: Studi Kasusmu Sendiri

Pakai kembali hierarki kelas dari tugas pertemuan sebelumnya (aplikasi pilihanmu sendiri).

1. Tulis satu perulangan yang memanggil method yang sama pada beberapa jenis objek, tanpa cabang `if`. Tuliskan output yang kamu harapkan.
2. Pilih satu method yang bisa gagal. Buat exception sendiri untuknya, lalu tuliskan `try`/`catch` pemanggilnya.
3. Perbarui diagram kelasmu di kertas.
