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

Pertemuan 13: **GUI dengan NetBeans Matisse (Bagian 1)**

Dari konsol ke jendela aplikasi

---

## Yang Akan Kamu Pelajari

- Pemrograman berbasis event (*event-driven programming*): alur program GUI tidak lagi berjalan urut dari atas ke bawah seperti program konsol
- GUI Builder (Matisse): menyusun tampilan lewat drag-and-drop, bukan menulis kode layout secara manual
- Memisahkan kode antarmuka (GUI) dari kode logika bisnis yang sudah dibangun sejak pertemuan-pertemuan sebelumnya
- Penerapan pada Bank Mini: reorganisasi paket `model`/`repository`/`ui`, dan `BankMiniFrame` sebagai jendela pertama Bank Mini

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 13.
</div>

---

<!-- _class: divider -->

# Bagian 1
## Pemrograman Berbasis Event

---

## Program Konsol Berjalan Urut, Program GUI Tidak

Program konsol yang sudah kamu tulis sejak Pertemuan 2 selalu berjalan urut: baris pertama dieksekusi, lalu baris kedua, dan seterusnya, berhenti sejenak hanya ketika membaca input lewat `Scanner`. Program GUI berbeda sama sekali: pengguna bisa mengklik tombol mana pun, kapan pun, dalam urutan apa pun, program tidak bisa lagi sekadar "membaca input secara berurutan".

<div class="warn-box">
Tidak ada satu urutan tetap yang bisa ditulis untuk program GUI, sebab pengguna sendiri yang menentukan urutan aksinya.
</div>

---

## Mengapa Ini Penting?

Hampir seluruh aplikasi yang kamu pakai sehari-hari, aplikasi desktop, aplikasi mobile, bahkan halaman web, dibangun dengan pola yang sama: program menunggu, lalu bereaksi ketika sesuatu terjadi (tombol diklik, halaman digeser, notifikasi masuk). Pola pikir "tunggu dan bereaksi" ini disebut pemrograman berbasis event, dan hampir seluruh interaksi manusia-komputer modern dibangun di atasnya.

<div class="term-box">
Programmer yang hanya terbiasa berpikir "urut dari atas ke bawah" akan kesulitan memahami mengapa kode di dalam satu method GUI bisa terpanggil berkali-kali, atau tidak terpanggil sama sekali, tergantung aksi pengguna. Memahami pemrograman berbasis event sejak awal jauh lebih murah dibandingkan membongkar kebiasaan berpikir prosedural nanti.
</div>

---

## Event, Listener, dan Handler

![h:260 Satu event dari klik tombol memicu satu method handler](../assets/illustrations/event-callback-flow.svg)

<div class="term-box">
Ketika pengguna melakukan sesuatu pada komponen GUI (mengklik tombol, misalnya), komponen itu memancarkan sebuah <b>event</b>. Sebuah <i>listener</i> yang didaftarkan pada komponen tsb akan menangkap event itu, lalu menjalankan method <i>handler</i>-nya. Method handler inilah satu-satunya bagian kode yang benar-benar kamu tulis; kapan ia dipanggil sepenuhnya ditentukan oleh aksi pengguna, bukan oleh urutan baris kode.
</div>

---

<!-- _class: divider -->

# Bagian 2
## GUI Builder (Matisse)

---

## Menulis Layout Secara Manual Itu Merepotkan

Tata letak (layout) sebuah GUI, posisi dan ukuran tiap komponen, bisa ditulis lewat kode Java murni. Namun untuk form dengan banyak komponen, kode layout manual menjadi panjang, sulit dibaca, dan sulit disesuaikan setiap kali tampilan berubah sedikit saja.

<div class="term-box">
NetBeans menyediakan GUI Builder, dikenal sebagai <b>Matisse</b>, yang memungkinkan komponen disusun dengan cara diseret (drag-and-drop) di editor visual. NetBeans sendiri yang menuliskan kode layout-nya (<code>GroupLayout</code>) di balik layar, di dalam blok kode yang ditandai "Generated Code".
</div>

---

## Mengapa Ini Penting?

Menyusun antarmuka lewat alat visual, bukan kode manual, adalah praktik industri yang luas dipakai: Android Studio punya Layout Editor, Xcode punya Interface Builder, banyak perangkat pengembangan web punya page builder, semuanya memakai gagasan yang sama, memisahkan "bagaimana bentuknya" (disusun visual) dari "bagaimana perilakunya" (ditulis sebagai kode). Menguasai satu GUI Builder, seperti Matisse, mempermudah beradaptasi dengan alat serupa di ekosistem lain.

<div class="term-box">
Kode yang dihasilkan Matisse (blok "Generated Code") sebaiknya tidak diedit manual, sebab NetBeans akan menimpanya kembali setiap kali desain visual diubah. Kode yang kamu tulis sendiri (constructor, method bantu, event handler) selalu berada DI LUAR blok itu.
</div>

---

<!-- _class: divider -->

# Bagian 3
## Menerapkan ke Bank Mini

---

## Reorganisasi Paket model/repository/ui

![h:280 Bank bergantung pada AccountRepository (repository) dan Account (model); BankMiniFrame (ui) bergantung pada Bank](../assets/uml/p13-layered-packages.png)

Kelas Bank Mini yang sudah dibangun sejak Pertemuan 2 kini dikelompokkan menurut perannya: `model` untuk data dan aturan bisnis inti, `repository` untuk penyimpanan data, `ui` untuk antarmuka GUI yang mulai dibangun pertemuan ini. `Bank` sendiri tetap di paket induk, sebagai penghubung antar lapisan.

---

## BankMiniFrame, Jendela Pertama Bank Mini

![Jendela BankMiniFrame menampilkan tabel rekening](../assets/screenshots/pertemuan-13/p13-account-list.png)

`BankMiniFrame` menampilkan seluruh rekening lewat `JTable`, diisi dari `Bank.getAllAccounts()`, method baru yang mengembalikan data mentah alih-alih mencetaknya ke konsol. `Bank` dan `AccountRepository` yang dipakai `BankMiniFrame` adalah persis kelas yang sama yang sudah dibangun sejak Pertemuan 11, tidak ada satu baris pun kode bisnisnya yang berubah untuk mendukung GUI ini.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab GUI Components, Event Handling

Oracle Java Tutorials: "Creating a GUI With Swing", "Writing Event Listeners"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 13

---

## Diskusi

`BankMiniFrame` memanggil `bank.getAllAccounts()` untuk mengisi tabel, bukan `bank.printAllAccounts()` yang sudah ada sejak Pertemuan 9. Jelaskan dengan kata-katamu sendiri mengapa method yang mencetak langsung ke konsol (`System.out.println`) tidak bisa dipakai ulang untuk mengisi komponen GUI seperti `JTable`, dan mengapa mengembalikan data mentah (`Collection<Account>`) jauh lebih fleksibel untuk dipakai di berbagai konteks (konsol, GUI, atau bahkan format lain di masa depan).
