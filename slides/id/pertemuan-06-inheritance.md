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

- Mengapa kode mirip sebaiknya digabung di satu tempat, bukan disalin berulang
- Cara kelas baru mewarisi kelas lama, dan urutan pembuatannya tahap demi tahap
- Hak akses antar kelas, pewarisan bertingkat sampai `Object`, dan yang tidak diwariskan
- Upcasting dan downcasting: kapan objek subclass boleh dipegang sebagai superclass
- Kapan sebuah kelas sebaiknya menjadi jenis khusus dari kelas lain, dan kapan tidak
- Merancang satu hierarki kelas kecil dari awal sampai akhir

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 6.
</div>

---

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Konsep inheritance, superclass dan subclass
- **Sesi 2 (50')**: Constructor, `super(...)`, `protected`, dan inheritance bertingkat
- **Sesi 3 (50')**: Kapan sebaiknya memakai inheritance
- **Sesi 4 (50')**: Studi kasus sintesis, merancang hierarki koleksi perpustakaan

---

<!-- _class: divider -->

# Bagian 1
## Konsep Inheritance

Sesi 1 dari 4

---

## Dari Kelas yang Mirip

Bayangkan kelas `Sedan` dan `Truck` ditulis terpisah, padahal keduanya sama-sama punya atribut `name` dan method `getName()`. Menyalin kode yang sama ke kedua kelas membuat program sulit dipelihara: setiap perubahan harus diulang secara manual di kelas lainnya.

<div class="warn-box">
Kode yang sama, disalin ke banyak tempat, adalah salah satu tanda desain yang perlu diperbaiki.
</div>

---

## Superclass dan Subclass

<div class="term-box">
<b>Inheritance</b> memungkinkan sebuah kelas (disebut <b>subclass</b>) mewarisi atribut dan method dari kelas lain (disebut <b>superclass</b>), sehingga kode yang sama cukup ditulis satu kali di superclass, lalu dipakai bersama oleh subclass-subclassnya. Superclass juga disebut <b>parent class</b> atau <b>base class</b>; subclass disebut <b>child class</b> atau <b>derived class</b>.
</div>

---

## Mengapa Ini Penting?

Bayangkan `Sedan` dan `Truck` ditulis terpisah selama bertahun-tahun, lalu ditemukan bug pada method `getName()`-nya. Programmer memperbaiki bug itu di `Sedan`, tetapi lupa melakukan hal yang sama di `Truck`, karena keduanya adalah salinan kode yang terpisah. Kode yang seharusnya identik lambat laun berbeda karena hanya sebagian salinan yang diperbarui; ini salah satu sumber bug paling umum di proyek nyata.

<div class="term-box">
Inheritance menghilangkan sumber bug ini dengan memastikan kode yang sama hanya ada di satu tempat, yaitu superclass. Namun inheritance adalah alat yang kuat sekaligus mudah disalahgunakan: memaksakan hubungan "is-a" yang sebenarnya tidak alami justru menciptakan ketergantungan yang kaku antar kelas. Pertemuan 11 (SOLID) membahas aturan lebih lanjut tentang kapan inheritance sebaiknya dihindari.
</div>

---

## Struktur Inheritance

![h:280 Sedan dan Truck masing-masing mewarisi dari Vehicle](../assets/illustrations/inheritance-tree.svg)

Kata kunci `extends` menyatakan hubungan ini dalam Java: `class Sedan extends Vehicle` berarti `Sedan` adalah subclass dari `Vehicle`, superclass-nya. Pada diagram kelas UML, hubungan ini digambar sebagai panah berujung segitiga kosong (generalization) yang selalu menunjuk dari subclass ke superclass, dibaca "`Sedan` is-a `Vehicle`".

---

## Contoh Kode: Superclass dan Subclass

```java
class Vehicle {
    private String name;
    public String getName() { return name; }
}

class Sedan extends Vehicle {
    // otomatis punya getName(), tanpa menulis ulang
}
```

---

## Apa yang Diwariskan?

![h:280 Subclass Sedan mewarisi seluruh anggota Vehicle, ditambah anggotanya sendiri](../assets/illustrations/inherited-members.svg)

Subclass otomatis memiliki seluruh atribut dan method (yang tidak bersifat `private`) milik superclass-nya, ditambah atribut dan method baru yang ditulis di subclass itu sendiri.

---

## Satu Superclass Saja

<div class="term-box">
Java hanya mengizinkan <b>satu</b> <code>extends</code> per kelas (single inheritance). Satu superclass boleh punya banyak subclass (<code>Vehicle</code> diwarisi <code>Sedan</code>, <code>Truck</code>, <code>Bus</code>), tetapi satu subclass tidak boleh punya dua superclass sekaligus.
</div>

```java
class Sedan extends Vehicle, Truck { }   // error compile
```

<div class="tip-box">
Kebutuhan mewarisi perilaku dari beberapa sumber sekaligus dipenuhi lewat interface, topik Pertemuan 9.
</div>

---

## Kesalahan Umum: Mengira Semua Anggota Harus Ditulis Ulang

<div class="warn-box">
<b>Salah:</b> menulis ulang <code>getName()</code> di dalam <code>Sedan</code> padahal isinya persis sama dengan milik <code>Vehicle</code>, karena mengira subclass belum memiliki method itu sebelum dituliskan sendiri.
</div>

**Benar:** `Sedan` otomatis mewarisi `getName()` apa adanya begitu `extends Vehicle` dituliskan. Menulis ulang tanpa perubahan apa pun hanya menciptakan duplikasi, justru masalah yang ingin dihilangkan oleh inheritance.

---

## Latihan

Kelas `Bus` mewarisi `Vehicle` (dengan atribut `name` dan method `getName()`). `Bus` menambahkan atribut baru `passengerCapacity` dan method baru `boardPassenger()`.

Sebutkan apa saja yang otomatis dimiliki `Bus` tanpa perlu ditulis ulang, dan apa saja yang harus ditulis sendiri di dalam `Bus`.

---

## Jawaban Latihan

Otomatis dimiliki (dari `Vehicle`): atribut `name` dan method `getName()`. Harus ditulis sendiri di `Bus`: atribut `passengerCapacity` dan method `boardPassenger()`, sebab keduanya baru dan tidak ada di `Vehicle`.

---

## Rangkuman Bagian 1

- Inheritance membuat subclass mewarisi atribut dan method superclass, menghindari duplikasi kode antar kelas yang mirip.
- Kata kunci `extends` menyatakan hubungan subclass-superclass di Java.
- Anggota yang diwarisi otomatis tersedia di subclass; hanya anggota baru atau yang sengaja diubah yang perlu ditulis.
- Satu kelas hanya bisa `extends` satu superclass; satu superclass boleh diwarisi banyak subclass.

Selanjutnya: Bagian 2 masuk ke bagaimana constructor bekerja saat sebuah subclass dibuat.

---

<!-- _class: divider -->

# Bagian 2
## Constructor, super(...), dan Access Modifier

Sesi 2 dari 4

---

## Constructor Superclass: `super(...)`

![h:260 Diagram kelas Vehicle, Sedan, dan Truck](../assets/uml/p06-vehicle.png)

<div class="term-box">
Constructor subclass wajib memanggil constructor superclass, baik secara eksplisit lewat <code>super(...)</code> di baris pertama, maupun secara implisit (Java memanggil constructor tanpa parameter milik superclass apabila <code>super(...)</code> tidak dituliskan).
</div>

<div class="tip-box">
<code>Sedan</code> dan <code>Truck</code> meng-override <code>honk()</code> agar setiap subclass punya bunyi klaksonnya sendiri, ditandai anotasi <code>@Override</code>. Detail aturan overriding dibahas tuntas Pertemuan 7.
</div>

---

## Contoh Kode: Memanggil `super(...)`

```java
class Vehicle {
    public Vehicle(String name) { this.name = name; }
}

class Sedan extends Vehicle {
    public Sedan(String name) {
        super(name);  // wajib jadi baris pertama
    }
}
```

---

## Yang Tidak Ikut Diwariskan

<div class="term-box">
<b>Constructor tidak diwariskan.</b> Itulah sebabnya setiap subclass menulis constructornya sendiri, lalu meneruskan data milik superclass lewat <code>super(...)</code>, bukan mengandalkan constructor <code>Vehicle</code> diwariskan ke <code>Sedan</code>.
</div>

<div class="warn-box">
<b>Anggota <code>private</code> tidak bisa diakses langsung.</b> Atribut <code>name</code> milik <code>Vehicle</code> tetap ada di dalam setiap objek <code>Sedan</code>, tetapi kode di <code>Sedan</code> tidak boleh menyentuhnya langsung: pakai getter (<code>getName()</code>) atau ubah ke <code>protected</code>, dibahas berikutnya.
</div>

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
Pemanggilan <code>super(...)</code>, bila dituliskan, wajib menjadi pernyataan pertama di dalam constructor. Java menampilkan error compile bila <code>super(...)</code> diletakkan setelah pernyataan lain.
</div>

---

## Kata Kunci `protected`

![h:320 Empat tingkat access modifier di Java](../assets/illustrations/protected-visibility.svg)

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
        return "Hello, " + name;  // langsung akses name, protected
    }
}
```

---

## Inheritance Bertingkat (Multilevel)

![h:280 Object sebagai akar semua kelas, dengan Employee, Manager, dan Director bertingkat di bawahnya](../assets/illustrations/multilevel-ladder.svg)

Sebuah subclass boleh diturunkan lagi menjadi superclass bagi subclass yang lain. Setiap kelas di Java, tanpa terkecuali, pada akhirnya diturunkan dari kelas `Object`, meskipun kata `extends Object` tidak pernah dituliskan secara eksplisit.

---

## Warisan dari Object: toString() dan equals()

Karena semua kelas berakar pada `Object`, setiap objek otomatis punya `toString()`, `equals()`, dan `hashCode()` tanpa menuliskannya sama sekali:

```java
Sedan civic = new Sedan("Civic");
System.out.println(civic);           // Sedan@1b6d3586
System.out.println(civic.toString()); // sama, println memanggil toString()
```

Implementasi bawaan `toString()` hanya nama kelas ditambah hash code; itulah asal output di atas. Memberi bentuk yang lebih bermakna berarti menulis ulang method warisan ini, topik Pertemuan 7.

---

## Diagram Kelas: Employee, Manager, Director

![h:280 Employee sebagai superclass, Manager dan Director bertingkat di bawahnya](../assets/uml/p06-employee-multilevel.png)

`name` bertanda `#` (protected) sehingga `Manager` dan `Director` dapat mengaksesnya secara langsung. `describe()` bertanda `{final}`: method ini sengaja tidak boleh di-override, supaya format output-nya konsisten untuk seluruh jenis pegawai. Sebuah kelas juga bisa ditandai `final` (contoh: `String`) supaya tidak dapat diturunkan sama sekali.

---

## Kesalahan Umum: Lupa super(...) Wajib Baris Pertama

<div class="warn-box">
<b>Salah:</b> menulis pernyataan lain (misalnya mengisi atribut sendiri) sebelum memanggil <code>super(...)</code> di dalam constructor subclass.
</div>

**Benar:** `super(...)`, bila dituliskan, harus selalu jadi baris pertama, tanpa terkecuali. Java menampilkan error compile begitu aturan ini dilanggar, bukan sekadar peringatan.

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
- Setiap kelas mewarisi `toString()`/`equals()` dari `Object`; constructor tidak diwariskan.

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
Inheritance sering dipakai secara keliru hanya karena dua kelas kebetulan punya beberapa atribut yang sama. Selalu uji dulu apakah relasinya benar-benar "is-a"; bila tidak terdengar wajar, relasi has-a (Pertemuan 4) biasanya pilihan yang lebih tepat.
</div>

---

## Mengapa Ini Penting?

Memaksakan inheritance pada relasi yang sebenarnya "has-a" menciptakan ketergantungan yang kaku. Subclass mewarisi seluruh anggota superclass, termasuk yang tidak relevan atau bahkan membingungkan, dan setiap perubahan pada superclass otomatis merambat ke semua subclass-nya, termasuk yang tidak seharusnya terpengaruh.

<div class="term-box">
Pada aplikasi besar, inheritance yang salah tempat membuat hierarki kelas menjadi kaku dan sulit diubah: menambah satu method baru di superclass bisa diam-diam memengaruhi puluhan subclass yang sebenarnya tidak membutuhkannya.
</div>

---

## Contoh Kode: IS-A yang Dipaksakan

```java
// Salah: Car bukan jenis Engine, dipaksakan jadi inheritance
class Car extends Engine { ... }

// Benar: composition (Pertemuan 4)
class Car {
    private Engine engine;
}
```

---

## Kesalahan Umum: Inheritance Hanya untuk Menghindari Duplikasi

<div class="warn-box">
<b>Salah:</b> membuat <code>Truck extends Sedan</code> semata-mata karena keduanya kebetulan punya method yang mirip, padahal Truck bukan jenis Sedan.
</div>

**Benar:** kesamaan kode saja tidak cukup untuk memilih inheritance. Kalau relasinya tidak benar-benar "is-a", kesamaan kode sebaiknya diselesaikan dengan cara lain (misalnya kelas helper yang dipakai bersama), bukan dengan memaksakan hierarki subclass-superclass.

---

## Latihan

Untuk tiap pasangan berikut, tentukan **is-a** atau **has-a**:

1. `Sedan` dan `Car`
2. `Car` dan `GPS`
3. `Manager` dan `Employee`
4. `Restaurant` dan `Menu`

---

## Jawaban Latihan

1. **is-a**, `Sedan` adalah jenis khusus dari `Car`.
2. **has-a**, `Car` memiliki `GPS`, bukan jenis dari `GPS`.
3. **is-a**, `Manager` adalah jenis khusus dari `Employee` (seperti dibahas Bagian 2).
4. **has-a**, `Restaurant` memiliki `Menu`, bukan jenis dari `Menu`.

---

## Rangkuman Bagian 3

- Sebelum memakai inheritance, uji dulu apakah relasinya benar-benar "is-a"; kalau tidak, "has-a" biasanya lebih tepat.
- Inheritance yang dipaksakan menciptakan ketergantungan kaku: subclass mewarisi seluruh anggota superclass, relevan maupun tidak.
- Kesamaan kode semata bukan alasan cukup untuk memilih inheritance.

Selanjutnya: Bagian 4 menggabungkan seluruh konsep ini dalam satu studi kasus, merancang hierarki koleksi perpustakaan dari awal.

---

<!-- _class: divider -->

# Bagian 4
## Studi Kasus Sintesis: Hierarki Koleksi Perpustakaan

Sesi 4 dari 4

---

## Dari Kebutuhan ke Hierarki

Sebuah perpustakaan menyimpan buku, DVD, dan majalah. Semuanya punya judul, tahun terbit, status sedang dipinjam atau tidak, serta operasi pinjam dan kembali yang sama persis. Masing-masing juga punya data tambahannya sendiri: jumlah halaman untuk buku, durasi untuk DVD, nomor edisi untuk majalah.

Uji "is-a" dari Bagian 3: `Book` adalah jenis `LibraryItem` (ya), `Dvd` adalah jenis `LibraryItem` (ya). Sebaliknya, `Library` memiliki banyak `LibraryItem`, bukan jenis darinya, jadi relasinya "has-a", bukan inheritance.

<div class="warn-box">
Kesamaan atribut saja bukan alasan memakai <code>extends</code>. Baru setelah uji "is-a" terdengar wajar, kesamaan itu boleh diangkat ke superclass.
</div>

---

## Diagram Kelas: LibraryItem, Book, Dvd, Magazine

![h:280 LibraryItem sebagai superclass, dengan Book, Dvd, dan Magazine sebagai subclass](../assets/uml/p06-libraryitem-hierarchy.png)

`title` dan `year` bertanda `#` (protected) supaya subclass boleh memakainya langsung. `checkOut()`, `returnItem()`, `loanDays()`, dan `describe()` ditulis satu kali di `LibraryItem`, lalu diwarisi apa adanya oleh ketiga subclass. Tiap subclass juga menyediakan getter untuk atribut tambahannya (`getPages()`, `getDurationMinutes()`, `getIssueNumber()`).

---

## Contoh Kode: Superclass LibraryItem

```java
class LibraryItem {
    protected String title;
    protected int year;
    private boolean available = true;

    public LibraryItem(String title, int year) { this.title = title; this.year = year; }
    public int loanDays() { return 14; }
}
```

---

## Contoh Kode: Book Menambah Atribut

```java
class Book extends LibraryItem {
    private int pages;

    public Book(String title, int year, int pages) {
        super(title, year);  // bagian LibraryItem dibangun dulu
        this.pages = pages;
    }
}
```

<div class="tip-box">
<code>Dvd</code> dan <code>Magazine</code> mengikuti pola yang sama persis: <code>super(title, year)</code> di baris pertama, lalu mengisi atributnya sendiri.
</div>

---

## Upcasting: Objek Subclass sebagai Objek Superclass

<div class="term-box">
Karena <code>Book</code> is-a <code>LibraryItem</code>, pernyataan <code>LibraryItem item = new Book("Dune", 1965, 412);</code> valid. Menyimpan objek subclass ke variabel bertipe superclass disebut <b>upcasting</b>, terjadi otomatis tanpa sintaks tambahan karena selalu aman. Lewat variabel bertipe <code>LibraryItem</code> hanya anggota <code>LibraryItem</code> yang terlihat: <code>item.loanDays()</code> boleh, <code>item.getPages()</code> tidak.
</div>

Kebalikannya, `Book b = new LibraryItem("Dune", 1965);` error compile: tidak semua `LibraryItem` adalah `Book`.

<div class="tip-box">
Inilah yang membuat satu array atau koleksi bertipe <code>LibraryItem</code> bisa menampung semua jenis koleksi sekaligus. Apa yang terjadi saat method warisan ditulis ulang oleh subclass adalah topik Polimorfisme (Pertemuan 10).
</div>

---

## Downcasting: Kembali ke Tipe Subclass

<div class="term-box">
Arah sebaliknya, dari variabel bertipe superclass ke tipe subclass, disebut <b>downcasting</b>. Harus ditulis eksplisit dengan tanda kurung, karena tidak selalu aman: compiler tidak bisa memastikan objek yang sebenarnya dirujuk memang subclass itu.
</div>

```java
LibraryItem item = new Book("Dune", 1965, 412);
Book book = (Book) item;                 // downcasting, eksplisit
System.out.println(book.getPages());     // 412

LibraryItem other = new Dvd("Inception", 2010, 148);
Book wrong = (Book) other;               // lolos compile, gagal saat dijalankan
```

<div class="warn-box">
Baris terakhir melempar <code>ClassCastException</code> saat program berjalan, sebab objeknya sebenarnya <code>Dvd</code>. Selalu periksa dulu: <code>if (item instanceof Book) { Book b = (Book) item; }</code>. Bentuk yang lebih ringkas, pattern matching, dibahas pada topik Polimorfisme (Pertemuan 10).
</div>

---

## Contoh Kode: Satu Koleksi untuk Semua Jenis

```java
LibraryItem[] items = {
    new Book("Dune", 1965, 412),
    new Dvd("Inception", 2010, 148),
    new Magazine("Tempo", 2024, 12)
};
for (LibraryItem item : items) {
    System.out.println(item.title + ": " + item.loanDays() + " days");
}
```

Ketiga baris output berakhir dengan `14 days`, sebab semuanya memakai `loanDays()` warisan yang sama.

---

## Method Warisan Belum Tentu Cocok untuk Semua Subclass

<div class="term-box">
<code>Dvd</code> mewarisi <code>loanDays()</code> yang selalu mengembalikan 14 hari, padahal perpustakaan ingin DVD hanya boleh dipinjam 7 hari. Menambah atribut <code>durationMinutes</code> saja tidak mengubah apa pun. Atribut baru saja tidak cukup: subclass juga perlu cara untuk menulis ulang perilaku yang diwarisi.
</div>

<div class="tip-box">
Inilah yang akan diselesaikan Pertemuan 7 lewat overriding: subclass menulis ulang method superclass untuk memberi perilaku yang berbeda, tanpa mengubah kode <code>LibraryItem</code> sama sekali.
</div>

---

## Kesalahan Umum: Mengakses Atribut private Superclass dari Subclass

<div class="warn-box">
<b>Salah:</b> <code>LibraryItem</code> mendeklarasikan <code>private String title</code>, lalu <code>Book</code> menulis <code>return title + " (" + pages + " pages)"</code>. Kompilasi gagal: <code>title has private access in LibraryItem</code>.
</div>

**Benar:** anggota `private` memang diwarisi, tetapi tidak boleh diakses langsung dari subclass. Ubah ke `protected` (seperti pada diagram), atau sediakan getter `getTitle()` di `LibraryItem` dan panggil itu dari `Book`.

---

## Latihan

1. Tuliskan kelas `Magazine` lengkap: mewarisi `LibraryItem`, menambah atribut `issueNumber`, dengan constructor yang memanggil `super(...)`.
2. `Library` dan `LibraryItem`: relasinya **is-a** atau **has-a**? Jelaskan singkat.
3. `LibraryItem item = new Magazine("Tempo", 2024, 12);` Apakah `item.loanDays()` boleh dipanggil? Apakah `item.getIssueNumber()` boleh dipanggil? Bagaimana cara memanggilnya dengan aman?

---

## Jawaban Latihan

```java
class Magazine extends LibraryItem {
    private int issueNumber;

    public Magazine(String title, int year, int issueNumber) {
        super(title, year);
        this.issueNumber = issueNumber;
    }
}
```

**has-a**: `Library` memiliki banyak `LibraryItem`, bukan jenis khusus darinya. `item.loanDays()` boleh, sebab anggota `LibraryItem`; `item.getIssueNumber()` tidak, sebab anggota `Magazine` tidak terlihat lewat variabel bertipe `LibraryItem`. Cara aman: `if (item instanceof Magazine) { ((Magazine) item).getIssueNumber(); }`.

---

## Rangkuman Bagian 4

- Uji "is-a" dulu, baru `extends`: `Book`, `Dvd`, dan `Magazine` lolos, `Library` tidak.
- Anggota bersama ditulis satu kali di superclass; subclass hanya menambah miliknya sendiri lewat constructor yang memanggil `super(...)`.
- Objek subclass boleh disimpan dalam variabel atau koleksi bertipe superclass; hanya anggota superclass yang terlihat lewat variabel itu. Upcasting otomatis dan selalu aman; downcasting eksplisit dan wajib dijaga `instanceof`.
- Method warisan belum tentu cocok untuk semua subclass; menulis ulang perilakunya adalah topik Pertemuan 7 (overriding).

---

## Rangkuman Pertemuan 6

- Inheritance membuat subclass mewarisi atribut dan method superclass lewat `extends`, menghindari duplikasi kode.
- Constructor subclass selalu memanggil constructor superclass lebih dulu lewat `super(...)`, wajib jadi baris pertama.
- `protected` membuka akses ke subclass lintas package; inheritance bisa bertingkat, berakar pada `Object`.
- Java hanya mengizinkan satu superclass; constructor tidak diwariskan; upcasting otomatis, downcasting eksplisit dan dijaga `instanceof`.
- Pilih inheritance hanya untuk relasi "is-a" yang benar-benar alami; hierarki `LibraryItem` menunjukkan seluruh konsep ini bekerja bersama dalam satu desain.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Object-Oriented Programming: Inheritance

Oracle Java Tutorials: "Inheritance", "The Object Class", "Using the Keyword super"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 6

---

## Tugas: Hierarki Koleksi Perpustakaan

Perpustakaan ingin menambah koleksi buku audio, `AudioBook`, yang punya `durationMinutes` (seperti `Dvd`) sekaligus `narrator` (nama pembaca). Sebaiknya `AudioBook extends Book`, `AudioBook extends Dvd`, atau `AudioBook extends LibraryItem` langsung?

Uji dengan pertanyaan "is-a", tuliskan pilihanmu beserta alasannya, lalu gambarkan diagram kelasnya di kertas: sebutkan atribut dan method mana yang diwarisi, dan mana yang harus ditulis sendiri di `AudioBook`.

---

## Tugas: Cari Studi Kasusmu Sendiri

Pikirkan satu aplikasi atau sistem nyata yang pernah kamu pakai (bukan perpustakaan, bukan Bank Mini), misalnya aplikasi transportasi online, game, atau media sosial.

Temukan satu superclass dengan dua atau tiga subclass di dalamnya yang lolos uji "is-a". Gambarkan diagram kelasnya di kertas (anggota bersama di superclass, anggota tambahan di tiap subclass, panah generalization yang tepat), lalu tunjukkan satu method warisan yang perilakunya sebaiknya berbeda di salah satu subclass, sebagai bahan pembuka Pertemuan 7.
