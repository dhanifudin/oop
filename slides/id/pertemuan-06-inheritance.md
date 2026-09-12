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

Pertemuan 6: **Inheritance**

Menurunkan sifat sebuah kelas ke kelas lain

---

## Yang Akan Kamu Pelajari

- Motivasi inheritance: menghindari duplikasi antar kelas yang mirip
- Cara mendeklarasikan subclass dengan `extends` dan memanggil constructor induk lewat `super(...)`, termasuk urutan eksekusi ketika beberapa `super(...)` berantai
- Kata kunci `protected`, dan inheritance bertingkat (multilevel) hingga kelas `Object` sebagai akar semua kelas di Java
- Kapan sebaiknya memilih inheritance ("is-a"), dan kapan memilih relasi ("has-a")
- Penerapan inheritance pada Bank Mini: `SavingsAccount` dan `CheckingAccount`

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 6.
</div>

---

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Konsep inheritance, superclass dan subclass
- **Sesi 2 (50')**: Constructor, `super(...)`, `protected`, dan inheritance bertingkat
- **Sesi 3 (50')**: Kapan sebaiknya memakai inheritance
- **Sesi 4 (50')**: Menerapkan inheritance ke Bank Mini

---

<!-- _class: divider -->

# Bagian 1
## Konsep Inheritance

Sesi 1 dari 4

---

## Dari Kelas yang Mirip ke Superclass

Bayangkan kelas `Dog` dan `Cat` ditulis terpisah, padahal keduanya sama-sama punya atribut nama dan method untuk mendapatkan nama tersebut. Menyalin kode yang sama ke kedua kelas membuat program sulit dirawat: perubahan pada satu kelas harus diulang secara manual di kelas lainnya.

<div class="term-box">
<b>Inheritance</b> memungkinkan sebuah kelas (subclass) mewarisi atribut dan method dari kelas lain (superclass), sehingga kode yang sama cukup ditulis satu kali di superclass.
</div>

---

## Mengapa Ini Penting?

Bayangkan `Dog` dan `Cat` ditulis terpisah selama bertahun-tahun, lalu ditemukan bug pada method `getName()`-nya. Programmer memperbaiki bug itu di `Dog`, tetapi lupa melakukan hal yang sama di `Cat`, karena keduanya adalah salinan kode yang terpisah. Kode yang seharusnya identik tetapi perlahan "berbeda" karena hanya sebagian salinan yang diperbarui adalah salah satu sumber bug paling umum di proyek nyata.

<div class="term-box">
Inheritance menghilangkan sumber bug ini dengan memastikan kode yang sama hanya ada di satu tempat, yaitu superclass. Namun inheritance adalah alat yang kuat sekaligus mudah disalahgunakan: memaksakan hubungan "is-a" yang sebenarnya tidak alami justru menciptakan ketergantungan yang kaku antar kelas. Pertemuan 11 (SOLID) membahas disiplin lebih lanjut soal kapan inheritance sebaiknya dihindari.
</div>

---

## Struktur Inheritance

![h:280 Dog dan Cat masing-masing mewarisi dari Animal](../assets/illustrations/inheritance-tree.svg)

Kata kunci `extends` menyatakan hubungan ini dalam Java: `class Dog extends Animal` berarti `Dog` adalah subclass dari `Animal`, superclass-nya.

---

## Contoh Kode: Superclass dan Subclass

```java
class Animal {
    private String name;
    public String getName() { return name; }
}

class Dog extends Animal {
    // otomatis punya getName(), tanpa menulis ulang
}
```

---

## Apa yang Diwariskan?

![h:280 Subclass Dog mewarisi seluruh anggota Animal, ditambah anggotanya sendiri](../assets/illustrations/inherited-members.svg)

Subclass otomatis memiliki seluruh atribut dan method (yang tidak bersifat `private`) milik superclass-nya, ditambah atribut dan method baru yang ditulis di subclass itu sendiri.

---

## Kesalahan Umum: Mengira Semua Anggota Harus Ditulis Ulang

<div class="warn-box">
<b>Salah:</b> menulis ulang <code>getName()</code> di dalam <code>Dog</code> padahal isinya persis sama dengan milik <code>Animal</code>, karena mengira subclass "belum benar-benar punya" method itu sebelum dituliskan sendiri.
</div>

**Benar:** `Dog` otomatis mewarisi `getName()` apa adanya begitu `extends Animal` dituliskan. Menulis ulang tanpa perubahan apa pun hanya menciptakan duplikasi yang seharusnya dihindari, persis masalah yang coba diselesaikan inheritance.

---

## Latihan

Kelas `Bird` mewarisi `Animal` (dengan atribut `name` dan method `getName()`). `Bird` menambahkan atribut baru `wingspan` dan method baru `fly()`.

Sebutkan apa saja yang otomatis dimiliki `Bird` tanpa perlu ditulis ulang, dan apa saja yang harus ditulis sendiri di dalam `Bird`.

---

## Jawaban Latihan

Otomatis dimiliki (dari `Animal`): atribut `name` dan method `getName()`. Harus ditulis sendiri di `Bird`: atribut `wingspan` dan method `fly()`, sebab keduanya baru dan tidak ada di `Animal`.

---

## Rangkuman Bagian 1

- Inheritance membuat subclass mewarisi atribut dan method superclass, menghindari duplikasi kode antar kelas yang mirip.
- Kata kunci `extends` menyatakan hubungan subclass-superclass di Java.
- Anggota yang diwarisi otomatis tersedia di subclass; hanya anggota baru atau yang sengaja diubah yang perlu ditulis.

Selanjutnya: Bagian 2 masuk ke bagaimana constructor bekerja saat sebuah subclass dibuat.

---

<!-- _class: divider -->

# Bagian 2
## Constructor, super(...), dan Visibilitas

Sesi 2 dari 4

---

## Constructor Superclass: `super(...)`

![h:260 Diagram kelas Animal, Dog, dan Cat](../assets/uml/p06-animal.png)

<div class="term-box">
Constructor subclass wajib memanggil constructor superclass, baik secara eksplisit lewat <code>super(...)</code> di baris pertama, maupun secara implisit (Java memanggil constructor tanpa parameter milik superclass apabila <code>super(...)</code> tidak dituliskan).
</div>

<div class="tip-box">
<code>Dog</code> dan <code>Cat</code> meng-override <code>makeSound()</code> agar setiap subclass punya bunyinya sendiri, ditandai anotasi <code>@Override</code>. Detail aturan overriding dibahas tuntas Pertemuan 7.
</div>

---

## Contoh Kode: Memanggil `super(...)`

```java
class Animal {
    public Animal(String name) { this.name = name; }
}

class Dog extends Animal {
    public Dog(String name) {
        super(name);  // wajib jadi baris pertama
    }
}
```

---

## Urutan Eksekusi Ketika super(...) Berantai

![h:260 Urutan pemanggilan super(...) dan urutan constructor body benar-benar dijalankan](../assets/illustrations/constructor-chain.svg)

Ketika `new Director(...)` dipanggil, `super(...)` merambat ke atas terlebih dahulu hingga mencapai `Employee`. Baru setelah itu, isi constructor benar-benar dijalankan, dimulai dari `Employee`, kemudian `Manager`, dan terakhir `Director`.

---

## Mengapa Urutan Ini Penting?

<div class="term-box">
Urutan ini menjamin bagian milik superclass sudah lengkap terbentuk sebelum subclass menambahkan bagiannya sendiri. Constructor subclass tidak pernah perlu khawatir mengakses bagian superclass yang belum siap.
</div>

<div class="warn-box">
Pemanggilan <code>super(...)</code>, bila dituliskan, wajib menjadi pernyataan pertama di dalam constructor. Java akan menampilkan galat compile bila <code>super(...)</code> diletakkan setelah pernyataan lain.
</div>

---

## Kata Kunci `protected` dan Inheritance Bertingkat

![h:320 Empat tingkat visibilitas di Java](../assets/illustrations/protected-visibility.svg)

<div class="term-box">
<code>protected</code> berada di antara default (hanya satu package) dan <code>public</code>: anggota bertanda <code>protected</code> dapat diakses subclass, bahkan bila berada di package berbeda.
</div>

---

## Contoh Kode: Mengakses Anggota `protected`

```java
class Employee {
    protected String name;
}

class Manager extends Employee {
    public String greet() {
        return "Halo, " + name;  // langsung akses name, protected
    }
}
```

---

## Inheritance Bertingkat (Multilevel)

![h:280 Object sebagai akar semua kelas, dengan Employee, Manager, dan Director bertingkat di bawahnya](../assets/illustrations/multilevel-ladder.svg)

Sebuah subclass boleh diturunkan lagi menjadi superclass bagi subclass yang lain. Setiap kelas di Java, tanpa terkecuali, pada akhirnya diturunkan dari kelas `Object`, meskipun kata `extends Object` tidak pernah dituliskan secara eksplisit.

---

## Diagram Kelas: Employee, Manager, Director

![h:280 Employee sebagai superclass, Manager dan Director bertingkat di bawahnya](../assets/uml/p06-employee-multilevel.png)

`name` bertanda `#` (protected) sehingga `Manager` dan `Director` dapat mengaksesnya secara langsung. `describe()` bertanda `{final}`: method ini sengaja tidak boleh di-override, supaya format keluarannya konsisten untuk seluruh jenis pegawai.

---

## Kesalahan Umum: Lupa super(...) Wajib Baris Pertama

<div class="warn-box">
<b>Salah:</b> menulis pernyataan lain (misalnya mengisi atribut sendiri) sebelum memanggil <code>super(...)</code> di dalam constructor subclass.
</div>

**Benar:** `super(...)`, bila dituliskan, harus selalu jadi baris pertama, tanpa terkecuali. Java menampilkan galat compile begitu aturan ini dilanggar, bukan sekadar peringatan.

---

## Latihan

`Employee` hanya punya satu constructor: `Employee(String name, double baseSalary)`, tanpa constructor tanpa parameter. `Manager` menulis constructornya tanpa memanggil `super(...)` sama sekali.

Apa yang terjadi ketika kode ini dikompilasi? Jelaskan alasannya.

---

## Jawaban Latihan

**Gagal dikompilasi.** Tanpa `super(...)` eksplisit, Java otomatis mencoba memanggil constructor tanpa parameter milik `Employee`. Karena `Employee` tidak punya constructor semacam itu, kompilasi gagal. `Manager` wajib memanggil `super(name, baseSalary)` secara eksplisit.

---

## Rangkuman Bagian 2

- Constructor subclass selalu memanggil constructor superclass lebih dulu lewat `super(...)`, eksplisit atau implisit.
- `super(...)`, bila dituliskan, wajib jadi baris pertama; superclass yang tidak punya constructor tanpa parameter memaksanya menjadi wajib eksplisit.
- `protected` membuka akses ke subclass lintas package; inheritance bisa bertingkat, berakar pada `Object`.

Selanjutnya: Bagian 3 membahas kapan inheritance sebaiknya dipakai, dan kapan sebaiknya dihindari.

---

<!-- _class: divider -->

# Bagian 3
## Kapan Memakai Inheritance?

Sesi 3 dari 4

---

## IS-A vs HAS-A

![h:280 Uji cepat: baca relasinya, apakah lebih cocok is-a atau has-a](../assets/illustrations/is-a-vs-has-a.svg)

<div class="warn-box">
Inheritance sering dipakai secara keliru hanya karena dua kelas kebetulan punya beberapa atribut yang sama. Selalu uji dulu apakah relasinya benar-benar "is-a"; bila tidak terdengar wajar, relasi ("has-a", dibahas Pertemuan 4) biasanya pilihan yang lebih tepat.
</div>

---

## Mengapa Ini Penting?

Memaksakan inheritance pada relasi yang sebenarnya "has-a" menciptakan ketergantungan yang kaku. Subclass mewarisi SELURUH anggota superclass, termasuk yang tidak relevan atau bahkan membingungkan, dan setiap perubahan pada superclass otomatis merambat ke semua subclass-nya, termasuk yang tidak seharusnya terpengaruh.

<div class="term-box">
Pada aplikasi besar, inheritance yang salah tempat membuat hierarki kelas menjadi kaku dan sulit diubah: menambah satu method baru di superclass bisa diam-diam memengaruhi puluhan subclass yang sebenarnya tidak membutuhkannya.
</div>

---

## Contoh Kode: IS-A yang Dipaksakan

```java
// Salah: Car bukan jenis Engine, dipaksakan jadi inheritance
class Car extends Engine { ... }

// Benar: relasi (composition), seperti dibahas Pertemuan 4
class Car {
    private Engine engine;
}
```

---

## Kesalahan Umum: Inheritance Hanya untuk Menghindari Duplikasi

<div class="warn-box">
<b>Salah:</b> membuat <code>Cat extends Dog</code> semata-mata karena keduanya kebetulan punya method yang mirip, padahal seekor Cat bukan jenis Dog.
</div>

**Benar:** kesamaan kode saja tidak cukup untuk memilih inheritance. Kalau relasinya tidak benar-benar "is-a", kesamaan kode sebaiknya diselesaikan dengan cara lain (misalnya kelas pembantu yang dipakai bersama), bukan dengan memaksakan hierarki subclass-superclass.

---

## Latihan

Untuk tiap pasangan berikut, tentukan **is-a** atau **has-a**:

1. `Sedan` dan `Mobil`
2. `Mobil` dan `GPS`
3. `Manager` dan `Employee`
4. `Restoran` dan `Menu`

---

## Jawaban Latihan

1. **is-a**, `Sedan` adalah jenis khusus dari `Mobil`.
2. **has-a**, `Mobil` memiliki `GPS`, bukan jenis dari `GPS`.
3. **is-a**, `Manager` adalah jenis khusus dari `Employee` (seperti dibahas Bagian 2).
4. **has-a**, `Restoran` memiliki `Menu`, bukan jenis dari `Menu`.

---

## Rangkuman Bagian 3

- Sebelum memakai inheritance, uji dulu apakah relasinya benar-benar "is-a"; kalau tidak, "has-a" biasanya lebih tepat.
- Inheritance yang dipaksakan menciptakan ketergantungan kaku: subclass mewarisi seluruh anggota superclass, relevan maupun tidak.
- Kesamaan kode semata bukan alasan cukup untuk memilih inheritance.

Selanjutnya: Bagian 4 menerapkan inheritance ke `Account` di Bank Mini.

---

<!-- _class: divider -->

# Bagian 4
## Menerapkan Inheritance ke Bank Mini

Sesi 4 dari 4

---

## SavingsAccount dan CheckingAccount

![h:280 Account sebagai superclass, SavingsAccount dan CheckingAccount sebagai subclass](../assets/uml/p06-account-hierarchy.png)

Kedua subclass ini menambahkan atributnya sendiri (`interestRate` dan `overdraftLimit`) serta method barunya sendiri (`printAccountType()`), sambil tetap mewarisi `deposit()`, `withdraw()`, dan `printInfo()` dari `Account` apa adanya, belum ada satu pun yang ditulis ulang.

---

## Contoh Kode: SavingsAccount Menambah Atribut

```java
class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, Customer owner,
            double balance, double interestRate) {
        super(accountNumber, owner, balance);
        this.interestRate = interestRate;
    }
}
```

---

## Method Warisan Belum Tentu Cocok untuk Semua Subclass

<div class="term-box">
<code>CheckingAccount</code> mewarisi <code>withdraw()</code> yang hanya membolehkan penarikan sebesar saldo yang tersedia, padahal <code>overdraftLimit</code> seharusnya membuat rekening ini bisa ditarik melebihi saldo. Atribut baru saja tidak cukup: subclass juga perlu cara untuk menulis ulang perilaku yang diwarisi.
</div>

<div class="tip-box">
Inilah yang akan diselesaikan Pertemuan 7 lewat overriding: subclass menulis ulang method superclass untuk memberi perilaku yang berbeda, tanpa mengubah kode <code>Account</code> maupun <code>Bank</code> sama sekali.
</div>

---

## Kesalahan Umum: Lupa Meneruskan Data lewat super(...)

<div class="warn-box">
<b>Salah:</b> menulis constructor <code>SavingsAccount</code> yang hanya mengisi <code>interestRate</code>, tanpa memanggil <code>super(accountNumber, owner, balance)</code>, berharap ketiga atribut warisan itu tetap terisi dengan benar.
</div>

**Benar:** tanpa `super(...)` yang meneruskan nilai sesungguhnya, `accountNumber`, `owner`, dan `balance` diam-diam tetap kosong (nilai bawaan), bukan galat yang langsung terlihat. `SavingsAccount` wajib meneruskan ketiganya lewat `super(...)`.

---

## Latihan

`CheckingAccount` menambahkan atribut `overdraftLimit`, mengikuti pola yang sama seperti `SavingsAccount`.

Tuliskan signature constructor `CheckingAccount` yang tepat, lengkap dengan pemanggilan `super(...)`-nya.

---

## Jawaban Latihan

```java
public CheckingAccount(String accountNumber, Customer owner,
        double balance, double overdraftLimit) {
    super(accountNumber, owner, balance);
    this.overdraftLimit = overdraftLimit;
}
```

---

## Rangkuman Bagian 4

- `SavingsAccount` dan `CheckingAccount` menambahkan atributnya sendiri, sambil tetap mewarisi seluruh method `Account` apa adanya.
- Constructor subclass wajib meneruskan data milik superclass lewat `super(...)`, bukan mengisinya sendiri secara terpisah.
- Method warisan belum tentu cocok untuk semua subclass; menulis ulang perilakunya adalah topik Pertemuan 7 (overriding).

---

## Rangkuman Pertemuan 6

- Inheritance membuat subclass mewarisi atribut dan method superclass lewat `extends`, menghindari duplikasi kode.
- Constructor subclass selalu memanggil constructor superclass lebih dulu lewat `super(...)`, wajib jadi baris pertama.
- `protected` membuka akses ke subclass lintas package; inheritance bisa bertingkat, berakar pada `Object`.
- Pilih inheritance hanya untuk relasi "is-a" yang benar-benar alami; Bank Mini menerapkannya lewat `SavingsAccount` dan `CheckingAccount`.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Object-Oriented Programming: Inheritance

Oracle Java Tutorials: "Inheritance", "The Object Class", "Using the Keyword super"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 6

---

## Diskusi

Perhatikan kembali `SavingsAccount` dan `CheckingAccount` yang baru saja kamu bangun: apakah keduanya sebaiknya juga punya subclass masing-masing (misalnya `SavingsAccount` dipecah lagi menjadi jenis dengan bunga tetap dan bunga berjenjang)? Beri satu contoh subclass yang menurutmu masuk akal beserta atribut barunya, atau jelaskan mengapa pemecahan lebih lanjut tidak diperlukan untuk Bank Mini.
