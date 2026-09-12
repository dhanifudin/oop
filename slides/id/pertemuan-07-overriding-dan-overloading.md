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

Menulis ulang perilaku warisan, dan menambah versi baru sebuah method

---

## Yang Akan Kamu Pelajari

- Cara subclass menulis ulang perilaku yang diwarisi supaya cocok dengan kebutuhannya sendiri
- Cara tetap memanfaatkan perilaku lama sambil menambah sesuatu yang baru di atasnya
- Cara mencegah sebuah perilaku ditulis ulang sama sekali
- Menambahkan beberapa cara memanggil operasi yang sama, dan bagaimana ini berbeda dari menulis ulang perilaku warisan
- Penerapan pada Bank Mini: aturan penarikan yang berbeda tiap jenis rekening, dan setoran dengan atau tanpa catatan

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 7.
</div>

---

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Method overriding, menulis ulang perilaku warisan
- **Sesi 2 (50')**: Method overloading, menambah versi baru sebuah method
- **Sesi 3 (50')**: Menerapkan overriding ke Bank Mini
- **Sesi 4 (50')**: Menerapkan overloading ke Bank Mini

---

<!-- _class: divider -->

# Bagian 1
## Method Overriding

Sesi 1 dari 4

---

## Menulis Ulang Method Warisan

Pertemuan 6 menunjukkan bahwa subclass mewarisi method superclass apa adanya. Kadang perilaku yang diwarisi tidak cocok untuk subclass tertentu: `Sedan` dan `Truck` sama-sama mewarisi `honk()` dari `Vehicle`, tetapi tentu saja bunyi klaksonnya seharusnya berbeda.

<div class="term-box">
<b>Overriding</b> adalah menulis ulang method superclass di dalam subclass, dengan nama dan daftar parameter (tanda tangan) yang sama persis. Java memanggil versi milik objek yang sebenarnya saat program berjalan, bukan versi yang dideklarasikan di tipe variabelnya.
</div>

---

## Mengapa Ini Penting?

Bayangkan sebuah sistem pembayaran dengan puluhan jenis metode (kartu kredit, transfer bank, e-wallet), dengan superclass `PaymentMethod` yang subclass-nya terus bertambah seiring waktu. Tanpa overriding, setiap kali ditambahkan jenis pembayaran baru, kode yang memproses pembayaran juga harus diubah untuk menangani kasus baru itu, berisiko merusak jenis pembayaran lain yang sudah berjalan baik.

<div class="term-box">
Overriding memungkinkan setiap subclass menyediakan perilakunya sendiri tanpa mengubah satu baris pun kode superclass atau subclass lain yang sudah ada. Prinsip "boleh diperluas, tetapi tidak boleh diubah" ini adalah salah satu dari lima prinsip SOLID, disebut Open/Closed Principle, yang dibahas lebih lanjut pada Pertemuan 11.
</div>

---

## Contoh Kode: Meng-override `honk()`

```java
class Vehicle {
    public String honk() { return "Beep!"; }
}

class Truck extends Vehicle {
    @Override
    public String honk() { return "Tin tin!"; }
}
```

---

## Anotasi `@Override`

<div class="tip-box">
Anotasi <code>@Override</code> memberi tahu compiler untuk memeriksa bahwa method benar-benar menulis ulang method superclass dengan tanda tangan yang sama persis. Bila ada kesalahan ketik pada nama method, compiler menampilkan galat alih-alih diam-diam membuat method baru yang tidak pernah terpanggil.
</div>

<div class="warn-box">
<code>@Override</code> sendiri tidak wajib secara sintaks, tetapi selalu disertakan sebagai kebiasaan baik: galat yang terdeteksi lebih awal jauh lebih murah diperbaiki daripada bug yang baru ketahuan saat program berjalan.
</div>

---

## Memanggil Versi Superclass: `super.method(...)`

<div class="term-box">
Sebuah method yang di-override boleh tetap memanggil versi superclass-nya lewat <code>super.namaMethod(...)</code>, biasanya untuk menambahkan perilaku baru tanpa menulis ulang seluruh isi method dari awal.
</div>

Pola ini sering dipakai ketika subclass hanya ingin menambahkan sedikit informasi pada perilaku yang sudah ada, misalnya mencetak baris tambahan setelah baris yang sudah dicetak superclass.

---

## Contoh Kode: Memanggil `super.honk()`

```java
class Truck extends Vehicle {
    @Override
    public String honk() {
        return super.honk() + " (klakson besar)";
    }
}
```

---

## Mencegah Method Di-override: `final`

<div class="term-box">
Method yang ditandai <code>final</code> tidak dapat di-override oleh subclass mana pun. Java akan menampilkan galat compile bila ada subclass yang mencoba menulis ulang method tersebut.
</div>

<div class="warn-box">
Gunakan <code>final</code> secukupnya: hanya ketika ada alasan kuat suatu perilaku harus selalu sama di seluruh subclass. Menandai semua method sebagai <code>final</code> justru menghilangkan manfaat utama inheritance, yaitu kemampuan subclass menyesuaikan perilaku.
</div>

---

## Kesalahan Umum: Mengira Parameter Boleh Berbeda

<div class="warn-box">
<b>Salah:</b> menulis <code>public String honk(String mode)</code> di <code>Truck</code>, mengira ini meng-override <code>honk()</code> milik <code>Vehicle</code>, padahal daftar parameternya berbeda.
</div>

**Benar:** `honk(String mode)` bukan override, melainkan method BARU yang kebetulan bernama sama. Tanda tangan (nama dan parameter) harus identik persis; `@Override` akan menampilkan galat compile kalau tidak cocok, justru mengungkap kesalahan ini lebih awal.

---

## Latihan

Kelas `Truck` menulis method `public String honk(int times)`, sedangkan `Vehicle` punya `public String honk()`.

Apakah ini overriding? Jelaskan, lalu prediksi apa yang terjadi kalau `Truck` menandai method ini dengan `@Override`.

---

## Jawaban Latihan

**Bukan overriding**, sebab parameternya berbeda (`honk()` vs `honk(int times)`). Kalau ditandai `@Override`, compiler akan menampilkan galat, sebab tidak ada method `honk(int times)` di `Vehicle` yang bisa ditulis ulang.

---

## Rangkuman Bagian 1

- Overriding menulis ulang method superclass di subclass, dengan tanda tangan yang harus sama persis.
- `@Override` membuat compiler memeriksa tanda tangannya benar-benar cocok, menangkap kesalahan lebih awal.
- `super.method(...)` memanggil versi superclass; `final` mencegah method di-override sama sekali.

Selanjutnya: Bagian 2 membahas method overloading, situasi ketika daftar parameter yang berbeda ternyata bukan overriding sama sekali.

---

<!-- _class: divider -->

# Bagian 2
## Method Overloading

Sesi 2 dari 4

---

## Nama Sama, Parameter Berbeda

<div class="term-box">
<b>Overloading</b> adalah menambahkan method dengan nama yang sama tetapi daftar parameter (jumlah atau tipe) yang berbeda. Compiler memilih versi mana yang dipanggil berdasarkan argumen yang diberikan saat pemanggilan, ditentukan sejak program dikompilasi, bukan saat program berjalan.
</div>

Contoh umum: `println()` pada `System.out` sebenarnya adalah puluhan method overload, masing-masing menerima tipe argumen yang berbeda (`String`, `int`, `double`, `boolean`, dan seterusnya), tetapi semuanya dipanggil dengan nama yang sama.

---

## Contoh Kode: Meng-overload `honk()`

```java
class Vehicle {
    public String honk() { return "Beep!"; }
    public String honk(int times) {
        return honk().repeat(times);
    }
}
```

---

## Mengapa Ini Penting?

Tanpa overloading, setiap variasi cara memanggil sebuah operasi butuh nama method yang berbeda, misalnya `printString()`, `printInt()`, `printDouble()`, `printBoolean()`. Semakin banyak variasi tipe data, semakin sulit programmer lain mengingat nama mana yang harus dipakai untuk kebutuhan tertentu.

<div class="term-box">
Overloading membuat API sebuah kelas terasa alami untuk dipakai: satu nama method yang sama, <code>println(...)</code>, cukup untuk seluruh variasi tipe data, dan compiler yang menentukan versi mana yang cocok berdasarkan argumen yang diberikan.
</div>

---

## Overriding vs Overloading

![h:300 Perbandingan overriding dan overloading](../assets/illustrations/override-vs-overload.svg)

Keduanya terdengar mirip namanya, tetapi mekanismenya sangat berbeda: overriding mengganti perilaku method warisan di subclass (diputuskan saat program berjalan), sementara overloading menambah versi baru sebuah method di kelas yang sama (diputuskan saat program dikompilasi).

---

## Kesalahan Umum: Mengira Tipe Kembalian Saja Sudah Cukup

<div class="warn-box">
<b>Salah:</b> menulis <code>public String honk()</code> dan <code>public int honk()</code> di kelas yang sama, mengira keduanya overload yang sah karena tipe kembaliannya berbeda.
</div>

**Benar:** tipe kembalian saja tidak cukup untuk overloading. Java membedakan overload lewat daftar parameter; dua method dengan parameter identik tapi tipe kembalian berbeda menyebabkan galat compile "duplicate method".

---

## Latihan

Untuk tiap pasangan method berikut, di kelas yang sama, tentukan **overloading yang sah** atau **galat compile**:

1. `honk()` dan `honk(int times)`
2. `String getName()` dan `int getName()`
3. `setScore(int score)` dan `setScore(double score)`

---

## Jawaban Latihan

1. **Overloading sah**, parameternya berbeda (jumlah).
2. **Galat compile**, parameternya identik (kosong); hanya tipe kembalian yang berbeda, itu tidak cukup.
3. **Overloading sah**, tipe parameternya berbeda (`int` vs `double`).

---

## Rangkuman Bagian 2

- Overloading menambah versi baru sebuah method dengan parameter berbeda, dipilih compiler berdasarkan argumen pemanggilan.
- Tipe kembalian saja tidak pernah cukup untuk membedakan overload; parameternya yang harus berbeda.
- Overriding mengganti perilaku warisan (runtime); overloading menambah versi baru (compile-time).

Selanjutnya: Bagian 3 menerapkan overriding ke aturan penarikan Bank Mini.

---

<!-- _class: divider -->

# Bagian 3
## Menerapkan Overriding ke Bank Mini

Sesi 3 dari 4

---

## canWithdraw() yang Di-override Tiap Jenis Rekening

![h:280 Account dengan canWithdraw sebagai titik override, SavingsAccount dan CheckingAccount menulis ulang aturannya masing-masing](../assets/uml/p07-account-hierarchy.png)

Pertemuan 6 menunjukkan bahwa `overdraftLimit` milik `CheckingAccount` belum memengaruhi apa pun, karena `withdraw()` yang diwarisi hanya tahu satu aturan generik. Dengan `canWithdraw()` yang di-override, `SavingsAccount` kini menjaga saldo minimum dan `CheckingAccount` kini benar-benar bisa ditarik melebihi saldo hingga batas overdraft-nya.

---

## Contoh Kode: `canWithdraw()` yang Berbeda Tiap Rekening

```java
class SavingsAccount extends Account {
    @Override
    protected boolean canWithdraw(double amount) {
        return balance - amount >= 50000;  // saldo minimum
    }
}

class CheckingAccount extends Account {
    @Override
    protected boolean canWithdraw(double amount) {
        return balance - amount >= -overdraftLimit;
    }
}
```

---

## Kesalahan Umum: Mengurangi Visibility Saat Override

<div class="warn-box">
<b>Salah:</b> menulis <code>private boolean canWithdraw(double amount)</code> di <code>SavingsAccount</code>, mengira ini meng-override method <code>protected</code> milik <code>Account</code>.
</div>

**Benar:** Java tidak mengizinkan override yang mengurangi visibility. Method override harus sama atau lebih terbuka daripada superclass-nya (`protected` boleh jadi `public`, tapi tidak boleh jadi `private`); kode ini gagal dikompilasi.

---

## Latihan

`SavingsAccount` menjaga saldo minimum Rp 50.000. Diberi `balance` saat ini Rp 100.000, tentukan hasil `withdraw(60000)`: berhasil atau ditolak? Jelaskan lewat `canWithdraw()`.

---

## Jawaban Latihan

**Ditolak.** `canWithdraw()` milik `SavingsAccount` mengecek `balance - amount >= 50000`. Dengan `balance` 100000 dan `amount` 60000, hasilnya 40000, kurang dari 50000, sehingga `canWithdraw()` mengembalikan `false` dan `withdraw()` ditolak.

---

## Rangkuman Bagian 3

- `canWithdraw()` di-override tiap subclass `Account`, memberi aturan penarikan yang berbeda tanpa mengubah `withdraw()` itu sendiri.
- Method override tidak boleh mengurangi visibility dibanding superclass-nya.
- Aturan overriding dari Bagian 1 (tanda tangan sama persis, `@Override`) berlaku persis sama di sini.

Selanjutnya: Bagian 4 menerapkan overloading ke method setoran Bank Mini.

---

<!-- _class: divider -->

# Bagian 4
## Menerapkan Overloading ke Bank Mini

Sesi 4 dari 4

---

## deposit() yang Di-overload

<div class="term-box">
<code>Account</code> mendapat versi kedua dari <code>deposit(double amount)</code>, yaitu <code>deposit(double amount, String note)</code>, yang menerima catatan tambahan lalu memanggil versi pertama untuk logika penyimpanannya. Keduanya adalah method yang berbeda di kelas yang sama, dipilih Java berdasarkan jumlah argumen yang diberikan saat pemanggilan.
</div>

---

## Contoh Kode: `deposit()` dengan Dua Versi

```java
class Account {
    public boolean deposit(double amount) {
        if (amount <= 0) return false;
        balance += amount;
        return true;
    }

    public boolean deposit(double amount, String note) {
        System.out.println("Catatan: " + note);
        return deposit(amount);
    }
}
```

---

## Latihan

Panggilan `account.deposit(50000)` dan `account.deposit(50000, "gaji bulanan")` sama-sama valid.

Method mana yang dipanggil Java untuk masing-masing, dan berdasarkan apa Java memilihnya?

---

## Jawaban Latihan

`account.deposit(50000)` memanggil versi satu parameter; `account.deposit(50000, "gaji bulanan")` memanggil versi dua parameter. Java memilih berdasarkan jumlah dan tipe argumen yang diberikan saat pemanggilan, ditentukan sejak kompilasi, bukan saat program berjalan.

---

## Rangkuman Bagian 4

- `deposit()` di-overload: versi dua parameter menerima catatan tambahan, lalu memanggil versi satu parameter untuk logika intinya.
- Java memilih versi overload berdasarkan jumlah dan tipe argumen saat pemanggilan, bukan saat program berjalan.

---

## Rangkuman Pertemuan 7

- Overriding menulis ulang perilaku warisan dengan tanda tangan yang sama persis; overloading menambah versi baru dengan parameter berbeda.
- `@Override` menangkap kesalahan tanda tangan lebih awal; `super.method(...)` tetap memanfaatkan perilaku lama; `final` mencegah override sama sekali.
- Bank Mini memakai overriding untuk aturan penarikan tiap jenis rekening, dan overloading untuk setoran dengan atau tanpa catatan.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Object-Oriented Programming: Overriding, Overloading

Oracle Java Tutorials: "Overriding and Hiding Methods", "Defining Methods" (overloading)

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 7

---

## Diskusi

`Employee.describe()` (Pertemuan 6) ditandai `final` karena formatnya harus selalu konsisten untuk seluruh jenis pegawai. Perhatikan kembali method-method `Account` yang baru saja kamu buat (`printInfo()`, `canWithdraw()`, `deposit()`, dan lain-lain): apakah ada salah satu di antaranya yang menurutmu juga layak ditandai `final`? Jelaskan alasanmu, atau jelaskan mengapa tidak ada yang membutuhkannya.
