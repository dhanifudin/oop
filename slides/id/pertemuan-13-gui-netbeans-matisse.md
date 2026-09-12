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

- Cara program GUI bereaksi terhadap aksi pengguna, bukan berjalan urut dari atas ke bawah seperti program konsol
- Cara menyusun tampilan lewat GUI Builder (Matisse) dengan drag-and-drop, tanpa menulis kode layout manual
- Cara memisahkan kode antarmuka (GUI) dari kode logika bisnis yang sudah dibangun sejak pertemuan-pertemuan sebelumnya
- Penerapan pada Bank Mini: reorganisasi paket `model`/`repository`/`ui`, dan `BankMiniFrame` sebagai jendela pertama Bank Mini

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 13.
</div>

---

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Pemrograman berbasis event
- **Sesi 2 (50')**: GUI Builder (Matisse)
- **Sesi 3 (50')**: Menerapkan reorganisasi paket ke Bank Mini
- **Sesi 4 (50')**: Menerapkan BankMiniFrame ke Bank Mini

---

<!-- _class: divider -->

# Bagian 1
## Pemrograman Berbasis Event

Sesi 1 dari 4

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

## Contoh Kode: Mendaftarkan Sebuah Listener

```java
JButton plusButton = new JButton("+");
plusButton.addActionListener(evt -> {
    int result = a + b;
    display.setText(String.valueOf(result));
});
```

`addActionListener` mendaftarkan kode di dalam lambda sebagai handler; kode ini menunggu, tidak langsung dijalankan saat baris ini dieksekusi.

---

## Contoh Kode: Bentuk Asli Sebuah Listener (Anonymous Class)

```java
plusButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
        int result = a + b;
        display.setText(String.valueOf(result));
    }
});
```

Lambda pada slide sebelumnya hanyalah cara ringkas menulis pola ini; NetBeans sendiri tetap menghasilkan bentuk anonymous class ini, seperti akan terlihat pada Bagian 2.

---

## Kesalahan Umum: Mengira Handler Berjalan Seperti main()

<div class="warn-box">
<b>Salah:</b> mengira kode di dalam <code>plusButtonActionPerformed()</code> otomatis berjalan satu kali saat program dimulai, sama seperti baris-baris di dalam <code>main()</code>.
</div>

**Benar:** kode di dalam handler hanya berjalan ketika event yang sesuai benar-benar terjadi (tombol itu diklik). Ia bisa berjalan nol kali (tombol tidak pernah diklik), satu kali, atau berkali-kali, tidak pernah dipanggil otomatis oleh urutan program.

---

## Latihan

Sebuah form punya dua tombol, `saveButton` dan `deleteButton`, masing-masing dengan handler-nya sendiri.

Pengguna mengklik `saveButton` tiga kali lalu `deleteButton` sekali. Berapa kali `saveButtonActionPerformed()` dan `deleteButtonActionPerformed()` masing-masing berjalan? Jelaskan.

---

## Jawaban Latihan

`saveButtonActionPerformed()` berjalan **tiga kali**, `deleteButtonActionPerformed()` berjalan **satu kali**, persis sebanyak tombol terkait diklik. Tidak ada urutan tetap yang menentukan ini sebelumnya, program hanya bereaksi setiap kali event klik benar-benar terjadi pada tombol yang bersangkutan.

---

## Rangkuman Bagian 1

- Program GUI tidak berjalan urut; ia menunggu dan bereaksi terhadap event yang dipicu aksi pengguna.
- Event ditangkap listener yang menjalankan method handler; handler bisa berjalan nol kali, sekali, atau berkali-kali.
- Handler tidak pernah dipanggil otomatis oleh urutan program, hanya oleh event yang benar-benar terjadi.

Selanjutnya: Bagian 2 membahas GUI Builder (Matisse), cara menyusun tampilan tanpa menulis kode layout manual.

---

<!-- _class: divider -->

# Bagian 2
## GUI Builder (Matisse)

Sesi 2 dari 4

---

## Menulis Layout Secara Manual Itu Merepotkan

Tata letak (layout) sebuah GUI, posisi dan ukuran tiap komponen, bisa ditulis lewat kode Java murni. Namun untuk form dengan banyak komponen, kode layout manual menjadi panjang, sulit dibaca, dan sulit disesuaikan setiap kali tampilan berubah sedikit saja.

<div class="term-box">
NetBeans menyediakan GUI Builder, dikenal sebagai <b>Matisse</b>, yang memungkinkan komponen disusun dengan cara diseret (drag-and-drop) di editor visual. NetBeans sendiri yang menuliskan kode layout-nya (<code>GroupLayout</code>) di balik layar, di dalam blok kode yang ditandai "Generated Code".
</div>

---

## Mengapa Ini Penting?

Menyusun antarmuka lewat alat visual, bukan kode manual, adalah praktik industri yang luas dipakai: Android Studio punya Layout Editor, Xcode punya Interface Builder, banyak perangkat pengembangan web punya page builder, semuanya memakai gagasan yang sama, memisahkan "bagaimana bentuknya" (disusun visual) dari "bagaimana perilakunya" (ditulis sebagai kode). Menguasai satu GUI Builder, seperti Matisse, mempermudah beradaptasi dengan alat serupa di ekosistem lain.

---

## JFrame, Content Pane, dan Komponen

<div class="term-box">
Sebuah <code>JFrame</code> (jendela) tidak menampung komponennya secara langsung, melainkan lewat satu wadah di dalamnya bernama <i>content pane</i>. Setiap komponen (tombol, tabel, dan sebagainya) ditambahkan ke content pane itu, lalu diatur posisinya oleh satu objek layout (mis. <code>GroupLayout</code>) yang dipasang pada content pane yang sama.
</div>

Matisse menyembunyikan detail ini di balik editor visual, tetapi kode yang dihasilkannya tetap memanggil `getContentPane()` dan `setLayout(...)` seperti kode manual biasa.

---

## Mengatur Komponen Lewat Panel Properties

Selain menyusun posisi lewat drag-and-drop, Matisse punya panel **Properties** untuk mengatur nilai awal komponen (teks tombol, nama variabel) tanpa menulis kode sama sekali.

<div class="term-box">
Mengubah properti <b>text</b> tombol menjadi "Refresh" lewat panel Properties menghasilkan baris <code>refreshButton.setText("Refresh");</code> di blok Generated Code, persis seperti menuliskannya manual, hanya saja lewat editor visual.
</div>

---

## Blok "Generated Code"

<div class="term-box">
Kode yang dihasilkan Matisse selalu dibungkus penanda <code>// &lt;editor-fold desc="Generated Code"&gt;</code>. Kode yang kamu tulis sendiri (constructor, method bantu, event handler) selalu berada DI LUAR blok ini.
</div>

```java
// <editor-fold desc="Generated Code">
private void initComponents() {
    accountTable = new javax.swing.JTable();
    // ...GroupLayout dibuat otomatis di sini...
}
// </editor-fold>
```

---

## Kesalahan Umum: Mengedit Langsung Blok Generated Code

<div class="warn-box">
<b>Salah:</b> menambahkan baris kode sendiri (mis. mengubah warna komponen) langsung di dalam blok <code>initComponents()</code> yang dijaga NetBeans.
</div>

**Benar:** NetBeans menimpa ULANG seluruh isi blok itu setiap kali desain visual diubah di tab Design, tanpa peringatan apa pun; perubahan manual di dalamnya akan hilang begitu saja. Kode tambahan selalu ditulis di luar blok, misalnya di constructor setelah pemanggilan `initComponents()`.

---

## Latihan

Seorang mahasiswa menambahkan baris `accountTable.setBackground(Color.YELLOW);` langsung di dalam blok `initComponents()`, lalu kembali ke tab Design dan menggeser posisi tombol Refresh sedikit.

Apa yang terjadi pada baris yang baru ditambahkan itu? Jelaskan, lalu sebutkan cara yang benar.

---

## Jawaban Latihan

**Baris itu hilang.** Berpindah ke tab Design dan mengubah apa pun membuat NetBeans menulis ulang seluruh isi blok `initComponents()` dari awal, menimpa baris yang ditambahkan manual di dalamnya. Cara yang benar: taruh `accountTable.setBackground(Color.YELLOW);` di constructor, setelah pemanggilan `initComponents()`, di luar blok yang dijaga NetBeans.

---

## Rangkuman Bagian 2

- Matisse menyusun layout lewat drag-and-drop, menuliskan kode `GroupLayout`-nya secara otomatis.
- Kode hasil generate dibungkus blok "Generated Code" dan ditimpa ulang setiap kali desain visual diubah.
- Kode yang ditulis sendiri (constructor, handler, method bantu) selalu diletakkan di luar blok itu.

Selanjutnya: Bagian 3 menerapkan reorganisasi paket ke seluruh kelas Bank Mini yang sudah dibangun.

---

<!-- _class: divider -->

# Bagian 3
## Menerapkan Reorganisasi Paket ke Bank Mini

Sesi 3 dari 4

---

## Reorganisasi Paket model/repository/ui

![h:280 Bank bergantung pada AccountRepository (repository) dan Account (model); BankMiniFrame (ui) bergantung pada Bank](../assets/uml/p13-layered-packages.png)

Kelas Bank Mini yang sudah dibangun sejak Pertemuan 2 kini dikelompokkan menurut perannya: `model` untuk data dan aturan bisnis inti, `repository` untuk penyimpanan data, `ui` untuk antarmuka GUI yang mulai dibangun pertemuan ini. `Bank` sendiri tetap di paket induk, sebagai penghubung antar lapisan.

---

## Mengapa Ini Penting?

Bayangkan sebuah proyek dengan ratusan kelas yang seluruhnya diletakkan langsung di satu paket tanpa pengelompokan. Menemukan satu kelas tertentu berarti menelusuri daftar panjang nama yang tidak terorganisasi, dan tidak ada cara sekilas untuk tahu kelas mana yang boleh saling bergantung.

<div class="term-box">
Mengelompokkan kelas menurut perannya (data, penyimpanan, antarmuka, dan seterusnya) adalah praktik standar hampir semua proyek nyata, bukan sekadar rapi-rapi berkas. Nama paket sendiri sudah menjelaskan peran kelas di dalamnya, dan arah ketergantungan antar lapisan (<code>ui</code> bergantung pada lapisan bisnis, bukan sebaliknya) menjadi terlihat jelas dari strukturnya.
</div>

---

## Struktur Folder Proyek Maven

```
src/main/java/id/ac/polinema/
├── Bank.java
├── Main.java
├── model/       (Account, Customer, Transaction, ...)
├── repository/  (AccountRepository, InMemoryAccountRepository)
└── ui/          (BankMiniFrame)
```

Struktur folder fisik mengikuti persis nama paketnya; memindahkan sebuah kelas ke paket baru berarti memindahkan berkasnya ke folder yang sesuai.

---

## Contoh Kode: Deklarasi Paket dan Import Lintas Paket

```java
// Account.java
package id.ac.polinema.model;

// Bank.java
package id.ac.polinema;
import id.ac.polinema.model.Account;
import id.ac.polinema.repository.AccountRepository;
```

Memindahkan sebuah kelas ke paket baru mengharuskan baris `package` di kelas itu SENDIRI diperbarui, dan kelas lain yang memakainya lintas paket diberi `import`.

---

## Bank Mendapat getAllAccounts()

`Bank` sejauh ini hanya bisa mencetak rekening langsung ke konsol lewat `printAllAccounts()`. GUI pada Bagian 4 butuh data mentahnya, bukan teks tercetak, sehingga `Bank` mendapat method baru:

```java
public Collection<Account> getAllAccounts() {
    return repository.findAll();
}
```

`getAllAccounts()` mengembalikan data mentahnya; kode pemanggil yang memutuskan mau dicetak ke konsol, ditampilkan di tabel GUI, atau dipakai dengan cara lain.

---

## Kesalahan Umum: Lupa Memperbarui Package atau Import

<div class="warn-box">
<b>Salah:</b> memindahkan berkas <code>Account.java</code> ke folder <code>model</code>, tetapi lupa mengubah baris <code>package id.ac.polinema;</code> di baris pertamanya menjadi <code>package id.ac.polinema.model;</code>.
</div>

**Benar:** compiler menampilkan galat `package id.ac.polinema does not exist` atau `cannot find symbol` pada kelas lain yang memakainya. Setiap berkas yang dipindah wajib memperbarui deklarasi `package`-nya SENDIRI, dan kelas lain yang memakainya lintas paket wajib diberi `import`.

---

## Latihan

`Customer.java` dipindahkan ke paket `id.ac.polinema.model`, tetapi baris `package` di dalamnya masih `package id.ac.polinema;`.

Apa yang terjadi saat proyek dikompilasi? Jelaskan, lalu sebutkan perbaikannya.

---

## Jawaban Latihan

**Gagal dikompilasi.** Lokasi fisik berkas (folder `model`) tidak cocok dengan deklarasi `package`-nya (`id.ac.polinema`, tanpa `.model`), menyebabkan compiler menampilkan galat ketidakcocokan paket. Perbaikan: ubah baris pertama `Customer.java` menjadi `package id.ac.polinema.model;`, lalu tambahkan `import id.ac.polinema.model.Customer;` di setiap kelas lain yang memakainya lintas paket.

---

## Rangkuman Bagian 3

- Kelas Bank Mini dikelompokkan menurut perannya: `model`, `repository`, dan `ui`; `Bank` tetap di paket induk sebagai penghubung.
- Memindahkan kelas mengharuskan deklarasi `package`-nya sendiri diperbarui, dan pemakaian lintas paket diberi `import`.
- `Bank` mendapat `getAllAccounts()`, mengembalikan data mentah alih-alih mencetaknya, supaya bisa dipakai GUI.

Selanjutnya: Bagian 4 menerapkan `BankMiniFrame`, jendela pertama Bank Mini yang memakai `getAllAccounts()` ini.

---

<!-- _class: divider -->

# Bagian 4
## Menerapkan BankMiniFrame ke Bank Mini

Sesi 4 dari 4

---

## BankMiniFrame, Jendela Pertama Bank Mini

![Jendela BankMiniFrame menampilkan tabel rekening](../assets/screenshots/pertemuan-13/p13-account-list.png)

`BankMiniFrame` menampilkan seluruh rekening lewat `JTable`, diisi dari `Bank.getAllAccounts()`. `Bank` dan `AccountRepository` yang dipakai `BankMiniFrame` adalah persis kelas yang sama yang sudah dibangun sejak Pertemuan 11, tidak ada satu baris pun kode bisnisnya yang berubah untuk mendukung GUI ini.

---

## EventQueue.invokeLater(): Menjalankan GUI di Thread yang Tepat

<div class="term-box">
Komponen Swing wajib dibuat dan diubah dari satu thread khusus, disebut <i>Event Dispatch Thread</i> (EDT), tempat seluruh event (klik, ketikan) sebenarnya diproses satu per satu. <code>EventQueue.invokeLater(...)</code> menjadwalkan kode pembuatan GUI supaya berjalan di EDT, bukan langsung di thread <code>main()</code> biasa.
</div>

```java
public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(() -> new BankMiniFrame().setVisible(true));
}
```

Inilah sebabnya `main()` program GUI hampir selalu diakhiri dengan pola `invokeLater(...)`, bukan langsung memanggil constructor jendelanya.

---

## TableModel dan JTable: Tampilan Terpisah dari Data

<div class="term-box">
<code>JTable</code> sendiri tidak menyimpan data selnya; ia hanya menampilkan apa pun yang disediakan objek <code>TableModel</code> yang dipasang padanya. <code>DefaultTableModel</code> adalah implementasi siap pakai yang menyimpan data sebagai baris-baris biasa, dengan method tambahan seperti <code>addRow(...)</code> untuk mengubah isinya.
</div>

Memisahkan "apa yang ditampilkan" (`JTable`) dari "data apa yang ada" (`TableModel`) berarti sumber datanya bisa diganti tanpa mengubah cara tabel itu ditampilkan.

---

## Contoh Kode: Constructor Menyiapkan Data Contoh

```java
public BankMiniFrame() {
    initComponents();
    bank = new Bank(new InMemoryAccountRepository());
    seedSampleAccounts();
    loadAccounts();
}
```

`initComponents()` (blok Generated Code) dipanggil lebih dulu, baru kode sendiri: menyiapkan `Bank`, mengisi data contoh, lalu memuatnya ke tabel.

---

## Contoh Kode: Mengisi JTable dari Bank

```java
private void loadAccounts() {
    DefaultTableModel model = (DefaultTableModel) accountTable.getModel();
    model.setRowCount(0);
    for (Account acc : bank.getAllAccounts()) {
        model.addRow(new Object[]{acc.getAccountNumber(), acc.getOwner().getName(), acc.getBalance()});
    }
}
```

`getAllAccounts()` dari Bagian 3 diiterasi langsung, tiap rekening menjadi satu baris tabel lewat `model.addRow(...)`.

---

## Kesalahan Umum: Cast ke TableModel, Bukan DefaultTableModel

<div class="warn-box">
<b>Salah:</b> menulis <code>TableModel model = accountTable.getModel();</code> lalu memanggil <code>model.addRow(...)</code>.
</div>

**Benar:** compiler menampilkan galat, sebab interface `TableModel` biasa tidak memiliki method `addRow()`/`setRowCount()`, hanya `DefaultTableModel` (implementasi konkretnya) yang punya. Baris itu wajib di-cast eksplisit: `(DefaultTableModel) accountTable.getModel()`.

---

## Latihan

`loadAccounts()` dipanggil, tetapi tabel `BankMiniFrame` tetap tampil kosong setelah program dijalankan.

Sebutkan dua kemungkinan penyebab yang berkaitan dengan URUTAN pemanggilan method di constructor, lalu jelaskan masing-masing.

---

## Jawaban Latihan

**Kemungkinan 1:** `loadAccounts()` dipanggil SEBELUM `seedSampleAccounts()`, sehingga `bank.getAllAccounts()` masih kosong saat tabel dimuat. **Kemungkinan 2:** `loadAccounts()` dipanggil SEBELUM `initComponents()`, sehingga `accountTable` belum diinisialisasi (`null`) saat `getModel()` dipanggil, menyebabkan `NullPointerException`. Urutan yang benar: `initComponents()`, lalu `seedSampleAccounts()`, baru `loadAccounts()`.

---

## Rangkuman Bagian 4

- `BankMiniFrame` menampilkan rekening lewat `JTable`, diisi dari `Bank.getAllAccounts()` tanpa mengubah kode bisnis yang sudah ada.
- Mengisi `JTable` butuh cast eksplisit ke `DefaultTableModel`, sebab `TableModel` biasa tidak punya `addRow()`/`setRowCount()`.
- Urutan pemanggilan di constructor penting: `initComponents()`, lalu penyiapan data, baru pemuatan ke tabel.

---

## Rangkuman Pertemuan 13

- Program GUI berbasis event: kode handler menunggu dan bereaksi terhadap aksi pengguna, bukan berjalan urut.
- Matisse menyusun layout lewat drag-and-drop; kode hasil generate ditimpa ulang, kode sendiri selalu ditulis di luar blok itu.
- Bank Mini direorganisasi ke paket `model`/`repository`/`ui`, dan `BankMiniFrame` menampilkan datanya lewat `getAllAccounts()` tanpa mengubah kode bisnis yang sudah ada sejak Pertemuan 11.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab GUI Components, Event Handling

Oracle Java Tutorials: "Creating a GUI With Swing", "Writing Event Listeners"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 13

---

## Diskusi

`BankMiniFrame` memanggil `bank.getAllAccounts()` untuk mengisi tabel, bukan `bank.printAllAccounts()` yang sudah ada sejak Pertemuan 9. Jelaskan dengan kata-katamu sendiri mengapa method yang mencetak langsung ke konsol (`System.out.println`) tidak bisa dipakai ulang untuk mengisi komponen GUI seperti `JTable`, dan mengapa mengembalikan data mentah (`Collection<Account>`) jauh lebih fleksibel untuk dipakai di berbagai konteks (konsol, GUI, atau bahkan format lain di masa depan).
