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
- Penerapan pada Bank Mini: `Bank` beralih ke `Map`, `Transaction` memisahkan tanggung jawab pencatatan, `AccountRepository` memisahkan `Bank` dari cara penyimpanan data

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 11.
</div>

---

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Collections, `ArrayList` dan `Map`
- **Sesi 2 (50')**: SOLID bagian 1, Single Responsibility, Open/Closed, Liskov Substitution
- **Sesi 3 (50')**: SOLID bagian 2, Interface Segregation, Dependency Inversion
- **Sesi 4 (50')**: Menerapkan SOLID ke Bank Mini

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
Satu kelas sebaiknya memiliki satu tanggung jawab, satu alasan untuk berubah. Kelas yang mencampur banyak tanggung jawab menjadi sulit dipahami, dan perubahan pada satu tanggung jawab berisiko memengaruhi tanggung jawab lain yang sebenarnya tidak berhubungan.
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

## Open/Closed dan Liskov Substitution (Recap)

<div class="term-box">
<b>Open/Closed Principle</b>: kelas sebaiknya terbuka untuk diperluas, tertutup untuk diubah. Kamu sudah mempraktikkan ini sejak Pertemuan 7: menambah subclass <code>PaymentMethod</code> baru tidak pernah mengubah kode superclass yang sudah ada, subclass baru cukup meng-override method miliknya sendiri.
</div>

<div class="term-box">
<b>Liskov Substitution Principle</b>: subclass harus bisa menggantikan superclass-nya di mana pun tanpa mengubah kebenaran program. <code>Sedan</code> dan <code>Truck</code> selalu bisa dipakai di mana pun kode mengharapkan <code>Vehicle</code>, sejak Pertemuan 6-7, tanpa membuat kode itu berperilaku salah.
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

<div class="term-box">
<b>Interface Segregation Principle</b>: interface sebaiknya kecil dan fokus, kelas tidak dipaksa mengimplementasikan method yang tidak relevan baginya. Kamu sudah mempraktikkan ini di Pertemuan 9: kemampuan seperti "bisa diisi daya" dideklarasikan sebagai interface kecil tersendiri (<code>Chargeable</code>), bukan digabung ke satu interface besar yang memaksa kelas mengimplementasikan method yang tidak relevan baginya.
</div>

---

## Dependency Inversion Principle

<div class="term-box">
<b>Dependency Inversion Principle</b>: kelas tingkat tinggi sebaiknya bergantung pada interface (abstraksi), bukan pada implementasi konkret. Ini baru diterapkan secara eksplisit pada Bank Mini di pertemuan ini, dibahas pada Bagian 4.
</div>

---

## Mengapa Ini Penting?

Bayangkan kelas `OrderProcessor` yang bergantung langsung pada kelas konkret `MySqlDatabase`. Migrasi ke database lain, atau menambahkan pengujian otomatis (yang butuh basis data tiruan agar tidak menyentuh data sungguhan), sama-sama menjadi sulit tanpa mengubah `OrderProcessor` itu sendiri, karena ia "tahu" secara langsung bahwa penyimpanannya pasti MySQL.

<div class="term-box">
Dependency Inversion Principle membalik arah ketergantungan ini: <code>OrderProcessor</code> cukup bergantung pada interface <code>Repository</code>, implementasi konkretnya (MySQL, penyimpanan sementara, atau versi tiruan untuk pengujian) bebas berganti tanpa <code>OrderProcessor</code> pernah tahu atau peduli. Prinsip yang sama ini diterapkan langsung pada Bank Mini di Bagian 4, dan dipakai lagi saat cara penyimpanan datanya diganti ke database pada Pertemuan 15.
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

Selanjutnya: Bagian 4 menerapkan seluruh prinsip ini ke `Bank` Bank Mini.

---

<!-- _class: divider -->

# Bagian 4
## Menerapkan SOLID ke Bank Mini

Sesi 4 dari 4

---

## Bank Beralih dari Array ke Map

`Bank` sejauh ini menyimpan rekening di `Account[] accounts` berukuran tetap, `findAccount()` memeriksa elemen satu per satu. `Bank` kini menyimpan rekening lewat `Map<String, Account>`, memakai nomor rekening sebagai kunci, pencarian menjadi langsung.

---

## Contoh Kode: Pencarian Rekening Lewat Map

```java
public void save(Account account) {
    accounts.put(account.getAccountNumber(), account);
}

public Account findByNumber(String accountNumber) {
    return accounts.get(accountNumber);
}
```

`findByNumber(...)` langsung mengembalikan rekeningnya lewat `accounts.get(...)`, tidak ada lagi perulangan memeriksa elemen satu per satu.

---

## Transaction: Single Responsibility Principle pada Bank Mini

![h:280 Account dan Transaction, satu Account memiliki banyak Transaction](../assets/uml/p11-transaction.png)

`Account` sejauh ini tidak mencatat riwayat transaksinya sama sekali. Kelas `Transaction` kini menjadi satu-satunya yang bertanggung jawab merepresentasikan satu transaksi, dipisah dari `Account` yang bertanggung jawab menjaga saldo dan aturan bisnis.

---

## Contoh Kode: `deposit()` Mencatat ke `Transaction`

```java
public boolean deposit(double amount) {
    if (amount <= 0) return false;
    balance += amount;
    history.add(new Transaction("DEPOSIT", amount));
    return true;
}
```

`Account` tetap menjaga `balance`, tetapi mendelegasikan representasi tiap transaksi ke kelas `Transaction`, bukan mengurusnya sendiri.

---

## AccountRepository: Dependency Inversion Principle pada Bank Mini

![h:280 Bank bergantung pada interface AccountRepository, diimplementasikan InMemoryAccountRepository](../assets/uml/p11-accountrepository.png)

`Bank` kini bergantung pada interface `AccountRepository`, bukan pada `Map` secara langsung. `InMemoryAccountRepository` adalah implementasi hari ini; Pertemuan 15 mengganti cara penyimpanan menjadi database, tanpa mengubah `Bank` satu baris pun.

---

## Contoh Kode: `Bank` Menerima `AccountRepository` Lewat Constructor

```java
public class Bank {
    private AccountRepository repository;

    public Bank(AccountRepository repository) {
        this.repository = repository;
    }
}
```

`Bank` tidak pernah menulis `new InMemoryAccountRepository()` di dalam dirinya sendiri, implementasi konkretnya diteruskan dari luar.

---

## Kesalahan Umum: Bank Membuat Sendiri Implementasinya

<div class="warn-box">
<b>Salah:</b> menulis <code>public Bank() { this.repository = new InMemoryAccountRepository(); }</code> di dalam <code>Bank</code>, membuat sendiri implementasi konkretnya.
</div>

**Benar:** `Bank` menerima `AccountRepository` lewat constructor, seperti kode sebenarnya, tidak pernah membuat instance konkretnya sendiri. Kode yang membuat objek `Bank`-lah yang memutuskan implementasi mana dipakai, persis prinsip yang baru dibahas di Bagian 3.

---

## Latihan

Pertemuan 15 mengganti `InMemoryAccountRepository` dengan `JdbcAccountRepository` (implementasi baru yang menyimpan ke database).

Sebutkan kode apa saja yang perlu diubah di `Bank`, dan jelaskan mengapa.

---

## Jawaban Latihan

**Tidak ada satu baris pun kode `Bank` yang perlu diubah.** `Bank` hanya bergantung pada interface `AccountRepository`, bukan implementasi konkretnya. Cukup ganti objek yang diteruskan ke constructor `Bank` saat aplikasi dijalankan, dari `new InMemoryAccountRepository()` menjadi `new JdbcAccountRepository(...)`.

---

## Rangkuman Bagian 4

- `Bank` beralih ke `Map` (lewat `AccountRepository`), pencarian rekening menjadi langsung lewat nomor rekening.
- `Transaction` memisahkan tanggung jawab mencatat riwayat dari `Account`, menerapkan Single Responsibility Principle.
- `AccountRepository` memisahkan `Bank` dari cara penyimpanan data, menerapkan Dependency Inversion Principle, sehingga Pertemuan 15 bisa mengganti penyimpanan ke database tanpa mengubah `Bank`.

---

## Rangkuman Pertemuan 11

- `ArrayList` dan `Map` menggantikan array biasa untuk data yang ukurannya berubah-ubah atau sering dicari berdasarkan kunci.
- SOLID adalah lima prinsip desain kelas: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion.
- Bank Mini menerapkan ketiganya: `Map` untuk penyimpanan, `Transaction` untuk SRP, `AccountRepository` untuk Dependency Inversion.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab Collections; Martin, *Agile Software Development* (SOLID Principles)

Oracle Java Tutorials: "The Collections Framework"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 11

---

## Diskusi

`AccountRepository` disebut memungkinkan Pertemuan 15 mengganti `InMemoryAccountRepository` dengan `JdbcAccountRepository` tanpa mengubah `Bank.java` sama sekali. Jelaskan dengan kata-katamu sendiri: apa yang akan terjadi (kode apa yang harus diubah, dan di berapa banyak tempat) apabila `Bank` sejak awal bergantung langsung pada `Map<String, Account>` tanpa lewat interface `AccountRepository`, lalu suatu hari cara penyimpanannya harus diganti ke database?
