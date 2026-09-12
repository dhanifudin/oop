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

- Cara satu pemanggilan method yang sama menjalankan perilaku berbeda tergantung objek penerimanya, ditentukan saat program berjalan
- Cara memeriksa sekaligus melakukan downcasting yang aman ketika kode tetap butuh tahu tipe konkret suatu objek
- Cara membuat kegagalan yang mustahil diabaikan begitu saja, lewat `throw`, `try`/`catch`, dan exception kustom
- Penerapan pada Bank Mini: `withdraw()` melempar `InsufficientBalanceException`, `Bank.processMonthEnd()` memproses rekening secara polimorfik

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 10.
</div>

---

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Polimorfisme, satu pemanggilan method, banyak perilaku
- **Sesi 2 (50')**: Exception handling, kegagalan yang tidak bisa diabaikan
- **Sesi 3 (50')**: Menerapkan exception handling ke Bank Mini
- **Sesi 4 (50')**: Menerapkan polimorfisme ke Bank Mini

---

<!-- _class: divider -->

# Bagian 1
## Polimorfisme

Sesi 1 dari 4

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

![h:280 Satu titik pemanggilan area() yang diselesaikan secara berbeda-beda saat program berjalan](../assets/illustrations/polymorphic-dispatch.svg)

<div class="term-box">
<b>Polimorfisme</b> adalah kemampuan satu pemanggilan method yang sama, dipanggil lewat tipe superclass atau interface, untuk menjalankan versi milik objek yang sebenarnya saat program berjalan (<i>dynamic dispatch</i>). Mekanisme ini sebenarnya sudah bekerja sejak method overriding dipelajari di Pertemuan 7, di sini diberi nama formalnya.
</div>

---

## Contoh Kode: Satu Pemanggilan, Perilaku Berbeda

```java
Shape[] shapes = { new Circle("c1", 3), new Square("s1", 4) };

for (Shape s : shapes) {
    System.out.println(s.area());
}
```

`s.area()` yang sama persis mencetak `28.27` untuk elemen pertama dan `16.0` untuk elemen kedua, `Shape[]` tidak pernah perlu tahu jenis konkret tiap elemennya.

---

## Kapan Tetap Butuh Tahu Tipe Konkret

Kadang kode tetap perlu memeriksa tipe konkret suatu objek, misalnya untuk memanggil kemampuan yang hanya dimiliki sebagian subclass (bukan seluruh superclass). Bayangkan `Circle` punya method tambahan `getRadius()` yang tidak dimiliki `Shape` maupun `Square`.

<div class="term-box">
<code>instanceof</code> dengan <i>pattern matching</i> (<code>if (obj instanceof TipeTertentu variabel)</code>) memeriksa tipe objek sekaligus langsung menyediakan variabel bertipe spesifik itu, menggantikan cara lama yang memerlukan casting manual terpisah setelah pengecekan.
</div>

<div class="warn-box">
Terlalu banyak <code>instanceof</code> yang memeriksa tipe konkret satu per satu adalah tanda polimorfisme belum dimanfaatkan sepenuhnya. Gunakan <code>instanceof</code> secukupnya, terutama untuk memeriksa <i>interface</i> yang hanya diterapkan sebagian subclass, seperti dicontohkan pada Bagian 4.
</div>

---

## Contoh Kode: Pattern Matching dengan `instanceof`

```java
for (Shape s : shapes) {
    if (s instanceof Circle c) {
        System.out.println("radius: " + c.getRadius());
    }
}
```

`c` langsung bertipe `Circle`, tanpa casting manual terpisah; blok ini hanya berjalan untuk elemen yang benar-benar `Circle`.

---

## Kesalahan Umum: if/else Padahal Bisa Polimorfik

<div class="warn-box">
<b>Salah:</b> menulis <code>if (s instanceof Circle) area = 3.14 * r * r; else if (s instanceof Square) area = side * side;</code> secara manual di dalam loop, padahal <code>Circle</code> dan <code>Square</code> sudah sama-sama meng-override <code>area()</code>.
</div>

**Benar:** cukup panggil `s.area()`. `Shape[]` otomatis memanggil versi milik objek yang sebenarnya lewat dynamic dispatch, kode pemanggil tidak perlu tahu jenis konkretnya sama sekali.

---

## Latihan

```java
Shape[] shapes = { new Circle("c1", 2), new Square("s1", 5) };
for (Shape s : shapes) {
    System.out.println(s.area());
}
```

Prediksi output dua baris yang dicetak kode ini (`Circle.area()` menghitung `Math.PI * radius * radius`, `Square.area()` menghitung `side * side`).

---

## Jawaban Latihan

Baris pertama mencetak **`12.566370614359172`** (`Math.PI * 2 * 2`), baris kedua mencetak **`25.0`** (`5 * 5`). `s.area()` yang sama persis dipanggil untuk kedua elemen, tetapi masing-masing menjalankan versi `area()` milik tipe konkretnya sendiri.

---

## Rangkuman Bagian 1

- Polimorfisme membuat satu pemanggilan method yang sama menjalankan versi milik objek yang sebenarnya saat program berjalan (dynamic dispatch).
- `instanceof` dengan pattern matching memeriksa tipe sekaligus menyediakan variabel bertipe spesifik, dipakai secukupnya saat kode memang butuh tahu tipe konkret.
- Kode yang bercabang `if`/`else` berdasarkan tipe padahal method-nya sudah polimorfik adalah tanda polimorfisme belum dimanfaatkan sepenuhnya.

Selanjutnya: Bagian 2 membahas exception handling, cara membuat kegagalan mustahil diabaikan begitu saja.

---

<!-- _class: divider -->

# Bagian 2
## Exception Handling

Sesi 2 dari 4

---

## Ketika Kegagalan Didiamkan Begitu Saja

Pertemuan 3 menunjukkan `Grade.setScore()` yang diam-diam membatasi (<i>clamp</i>) nilai di luar jangkauan 0-100, alih-alih menolaknya. Cara ini praktis, tetapi pemanggil tidak pernah tahu bahwa nilai yang dikirimnya sebenarnya diubah secara diam-diam.

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

![h:280 Sebuah exception menghentikan method yang melemparnya dan diteruskan ke atas hingga tertangkap](../assets/illustrations/exception-throw-catch.svg)

<div class="term-box">
Sebuah method melempar (<code>throw</code>) objek exception ketika menemui kondisi yang tidak bisa ditangani secara wajar, menghentikan eksekusinya saat itu juga. Kode pemanggil membungkus pemanggilan dalam blok <code>try</code>, lalu menangani exception yang mungkin dilempar lewat blok <code>catch</code>.
</div>

---

## Contoh Kode: Menangani Exception dengan `try`/`catch`

```java
try {
    grade.setScore(150);
} catch (InvalidScoreException e) {
    System.out.println("Gagal: " + e.getMessage());
}
```

Bila `setScore(150)` melempar `InvalidScoreException`, sisa blok `try` langsung dilewati dan eksekusi lompat ke `catch`, program tetap berjalan setelahnya.

---

## Membuat Exception Kustom

![h:280 Exception, InvalidScoreException, dan Grade yang melemparnya](../assets/uml/p10-invalidscore-exception.png)

<div class="term-box">
Exception kustom dibuat dengan mendeklarasikan kelas yang meng-<code>extends</code> <code>Exception</code>, biasanya hanya berisi konstruktor yang meneruskan pesan galat ke konstruktor superclass-nya lewat <code>super(pesan)</code>. Nama kelasnya sendiri sudah menjelaskan jenis kegagalan yang terjadi, jauh lebih jelas dibandingkan sekadar nilai <code>boolean</code> atau <code>null</code>.
</div>

---

## Contoh Kode: Mendeklarasikan Exception Kustom

```java
public class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}
```

Cukup satu konstruktor yang meneruskan pesan ke `super(...)`; `Exception` sudah menyediakan seluruh perilaku dasarnya.

---

## Kesalahan Umum: Lupa `try`/`catch` untuk Checked Exception

<div class="warn-box">
<b>Salah:</b> memanggil <code>grade.setScore(150);</code> langsung tanpa membungkusnya dalam <code>try</code>/<code>catch</code>, mengira exception hanya perlu ditangani kalau benar-benar terjadi.
</div>

**Benar:** compiler menampilkan galat `unreported exception InvalidScoreException; must be caught or declared to be thrown`. `InvalidScoreException` adalah checked exception, wajib ditangani lewat `try`/`catch` atau dideklarasikan lewat `throws` pada method pemanggil, sebelum kode bisa dikompilasi sama sekali.

---

## Latihan

```java
public void updateGrade(Grade grade, int newScore) {
    grade.setScore(newScore);
}
```

`setScore(int)` dideklarasikan `throws InvalidScoreException`. Apakah kode `updateGrade` ini bisa dikompilasi? Jelaskan, lalu sebutkan satu cara memperbaikinya.

---

## Jawaban Latihan

**Tidak bisa.** `setScore(int)` adalah checked exception, `updateGrade` memanggilnya tanpa `try`/`catch` maupun `throws`, sehingga compiler menampilkan galat `unreported exception`. Perbaikan: tambahkan `throws InvalidScoreException` pada signature `updateGrade`, atau bungkus pemanggilan `grade.setScore(newScore)` dalam blok `try`/`catch`.

---

## Rangkuman Bagian 2

- Exception membuat kegagalan mustahil diabaikan begitu saja, checked exception dipaksa compiler untuk ditangani secara eksplisit.
- `throw` melempar objek exception dan langsung menghentikan method, `try`/`catch` menangkap dan menanganinya di kode pemanggil.
- Exception kustom dibuat dengan `extends Exception`, nama kelasnya sendiri menjelaskan jenis kegagalan yang terjadi.

Selanjutnya: Bagian 3 menerapkan exception handling ke `withdraw()` Bank Mini.

---

<!-- _class: divider -->

# Bagian 3
## Menerapkan Exception Handling ke Bank Mini

Sesi 3 dari 4

---

## withdraw() Melempar InsufficientBalanceException

![h:280 Exception, InsufficientBalanceException, dan Account yang melemparnya](../assets/uml/p10-insufficientbalance-exception.png)

Sejauh ini, `withdraw()` diam-diam mengembalikan `false` ketika saldo tidak mencukupi, persis risiko yang dibahas pada Bagian 2. `withdraw()` kini melempar `InsufficientBalanceException`, kode pemanggil wajib menanganinya lewat `try`/`catch`, tidak bisa lagi lupa memeriksa hasilnya.

---

## Contoh Kode: `withdraw()` Melempar Exception

```java
public void withdraw(double amount) throws InsufficientBalanceException {
    if (!canWithdraw(amount)) {
        throw new InsufficientBalanceException(
                accountNumber + ": insufficient balance");
    }
    balance -= amount;
}
```

`canWithdraw()` (hook dari Pertemuan 7) tetap dipakai apa adanya, hanya cara menangani kegagalannya yang berubah.

---

## Kesalahan Umum: Catch Block Kosong

<div class="warn-box">
<b>Salah:</b> menulis <code>try { account.withdraw(500000); } catch (InsufficientBalanceException e) {}</code>, blok <code>catch</code> dibiarkan kosong tanpa penanganan apa pun.
</div>

**Benar:** blok `catch` kosong menciptakan ulang persis masalah yang ingin dipecahkan Bagian 2, kegagalan didiamkan begitu saja, hanya sekarang dibungkus `try`/`catch` supaya compiler tidak lagi mengeluh. Setidaknya cetak atau catat `e.getMessage()`, atau tampilkan pemberitahuan ke pengguna.

---

## Latihan

`SavingsAccount` dengan `balance` Rp 100.000 dan saldo minimum Rp 50.000 memanggil `withdraw(70000)`.

Apa yang terjadi? Jelaskan lewat `canWithdraw()`, lalu sebutkan apakah `balance` berubah.

---

## Jawaban Latihan

**`InsufficientBalanceException` dilempar.** `canWithdraw(70000)` mengecek `balance - amount >= 50000`, dengan `balance` 100000 hasilnya 30000, kurang dari 50000, sehingga `canWithdraw()` mengembalikan `false`. `withdraw()` melempar exception SEBELUM baris `balance -= amount` sempat dijalankan, `balance` tetap 100000, tidak berubah sama sekali.

---

## Rangkuman Bagian 3

- `withdraw()` melempar `InsufficientBalanceException` alih-alih diam-diam mengembalikan `false`, kode pemanggil wajib menanganinya.
- Exception dilempar SEBELUM `balance` diubah, kegagalan penarikan tidak pernah meninggalkan `Account` dalam keadaan tidak konsisten.
- Blok `catch` kosong menciptakan ulang masalah kegagalan yang didiamkan, exception tetap wajib ditangani secara berarti, bukan hanya supaya compiler diam.

Selanjutnya: Bagian 4 menerapkan polimorfisme ke `Bank.processMonthEnd()`.

---

<!-- _class: divider -->

# Bagian 4
## Menerapkan Polimorfisme ke Bank Mini

Sesi 4 dari 4

---

## processMonthEnd(): Polimorfisme pada Bank Mini

![h:280 Account sebagai kelas abstrak, SavingsAccount meng-implement interface InterestBearing](../assets/uml/p09-account-abstract.png)

`Bank.processMonthEnd()` memproses seluruh rekening secara polimorfik lewat `monthlyFee()`. Hanya rekening yang meng-implement `InterestBearing` yang mendapat `applyInterest()`, diperiksa lewat `instanceof InterestBearing`, bukan `instanceof SavingsAccount`, sehingga rekening berbunga jenis baru pun otomatis ikut terproses tanpa mengubah kode ini sama sekali.

---

## Contoh Kode: `processMonthEnd()` Memproses Tiap Rekening

```java
public void processMonthEnd() {
    for (int i = 0; i < count; i++) {
        Account acc = accounts[i];
        if (acc instanceof InterestBearing bearing) {
            bearing.applyInterest();
        }
        System.out.println(acc.monthlyFee());
    }
}
```

`acc.monthlyFee()` dipanggil polimorfik untuk SEMUA rekening; `applyInterest()` hanya untuk yang meng-implement `InterestBearing`.

---

## Kesalahan Umum: Memeriksa Kelas Konkret, Bukan Interface

<div class="warn-box">
<b>Salah:</b> menulis <code>if (acc instanceof SavingsAccount)</code> untuk memutuskan kapan memanggil <code>applyInterest()</code>, mengira ini sama saja dengan memeriksa <code>InterestBearing</code>.
</div>

**Benar:** memeriksa `instanceof InterestBearing` (bukan `instanceof SavingsAccount`) berarti rekening berbunga jenis BARU otomatis ikut terproses tanpa mengubah `processMonthEnd()` sama sekali. Memeriksa kelas konkret memaksa method ini diubah lagi setiap kali ada jenis rekening berbunga baru.

---

## Latihan

Bank Mini menambahkan `BusinessAccount` (tugas mandiri Pertemuan 6), rekening bisnis yang sama sekali tidak berbunga, jadi tidak meng-implement `InterestBearing`.

Apakah `processMonthEnd()` memanggil `applyInterest()` untuk `BusinessAccount`? Jelaskan lewat pengecekan `instanceof`-nya.

---

## Jawaban Latihan

**Tidak.** `processMonthEnd()` memeriksa `acc instanceof InterestBearing`, dan `BusinessAccount` tidak meng-implement `InterestBearing`. Pengecekan ini bernilai `false` untuk `BusinessAccount`, sehingga `applyInterest()` dilewati, hanya `acc.monthlyFee()` yang tetap dipanggil untuk seluruh rekening termasuk `BusinessAccount`.

---

## Rangkuman Bagian 4

- `processMonthEnd()` memanggil `monthlyFee()` secara polimorfik untuk seluruh rekening, tanpa perlu tahu jenis konkretnya.
- `instanceof InterestBearing` memutuskan kapan `applyInterest()` dipanggil, bukan `instanceof SavingsAccount`, supaya rekening berbunga baru otomatis ikut terproses.
- Polimorfisme dan exception handling sama-sama membuat `Bank` bisa tumbuh (jenis rekening baru, kegagalan baru) tanpa mengubah kode yang sudah ada.

---

## Rangkuman Pertemuan 10

- Polimorfisme membuat satu pemanggilan method yang sama menjalankan versi milik objek yang sebenarnya saat program berjalan; `instanceof` dengan pattern matching dipakai secukupnya saat kode tetap butuh tahu tipe konkret.
- Exception membuat kegagalan mustahil diabaikan begitu saja; checked exception dipaksa compiler untuk ditangani lewat `try`/`catch`, exception kustom dibuat lewat `extends Exception`.
- Bank Mini memakai keduanya: `withdraw()` melempar `InsufficientBalanceException`, `processMonthEnd()` memproses seluruh rekening secara polimorfik lewat `instanceof InterestBearing`.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Exception Handling, Polymorphism, Interfaces

Oracle Java Tutorials: "Polymorphism", "Exceptions"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 10

---

## Diskusi

`processMonthEnd()` memeriksa `instanceof InterestBearing`, bukan `instanceof SavingsAccount`, supaya jenis rekening berbunga baru otomatis ikut terproses tanpa mengubah method ini. Jelaskan dengan kata-katamu sendiri: apa yang akan terjadi (dan kode apa yang harus diubah) apabila pengecekan itu ditulis sebagai `instanceof SavingsAccount`, lalu Bank Mini menambahkan jenis rekening berbunga baru bernama `DepositAccount`?
