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
  section.lead a {
    color: #bfdbfe;
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

Pertemuan 14: **GUI dengan NetBeans Matisse (Bagian 2)**

Menerima input pengguna dengan aman

---

## Yang Akan Kamu Pelajari

- Cara memvalidasi input di titik masuk (boundary): input dari pengguna tidak pernah bisa dipercaya begitu saja
- Cara menampilkan kegagalan sebagai dialog yang bisa dibaca pengguna, bukan mencetaknya ke konsol atau membiarkan program berhenti paksa
- Cara membaca baris yang sedang dipilih pada `JTable`, lalu memakainya untuk MENCEGAH aksi tidak valid (menonaktifkan tombol), bukan sekadar menangkapnya setelah terjadi
- Penerapan pada Bank Mini: dialog tambah rekening dengan nomor otomatis, tombol setor dan tarik saldo pada `BankMiniFrame`

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 14.
</div>

---

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Validasi input di titik masuk
- **Sesi 2 (50')**: Baris terpilih pada JTable
- **Sesi 3 (50')**: Menerapkan dialog tambah rekening ke Bank Mini
- **Sesi 4 (50')**: Menerapkan setor dan tarik saldo ke Bank Mini

---

<!-- _class: divider -->

# Bagian 1
## Validasi Input di Titik Masuk

Sesi 1 dari 4

---

## Pengguna Bisa Mengetik Apa Saja

Kolom input teks pada GUI tidak membatasi apa yang bisa diketik pengguna: kolom yang dimaksudkan untuk angka tetap saja bisa diisi huruf, dikosongkan, atau diisi format yang tidak terduga.

<div class="warn-box">
Kode yang langsung memakai isi kolom input tanpa memeriksanya lebih dulu akan berhenti paksa (exception) begitu isiannya tidak sesuai harapan.
</div>

---

## Mengapa Ini Penting?

Sebagian besar bug yang dilaporkan pengguna aplikasi nyata bukan berasal dari algoritma yang salah, melainkan dari input tak terduga yang tidak pernah divalidasi: kolom dikosongkan, format tanggal berbeda dari yang diharapkan, angka desimal ditulis dengan pemisah yang berbeda. Validasi input, terutama di titik masuk data (boundary) sebuah aplikasi, adalah salah satu praktik paling mendasar untuk mencegah kelas bug ini.

<div class="term-box">
Aplikasi yang gagal memvalidasi input pada titik masuknya juga membuka celah keamanan: banyak kerentanan perangkat lunak, dari yang sekadar mengganggu sampai yang serius, berakar dari data yang dipercaya begitu saja tanpa diperiksa lebih dulu.
</div>

---

## Validasi, Bukan Sekadar Berharap

![h:280 Perbandingan input yang tidak divalidasi (program berhenti paksa) dengan input yang divalidasi (dialog pesan, program tetap berjalan)](../assets/illustrations/input-validation-boundary.svg)

<div class="term-box">
Pola yang dipakai sama seperti exception handling di Pertemuan 10: bungkus operasi yang berisiko gagal (mis. <code>Double.parseDouble(...)</code>) dalam <code>try</code>, tangani kegagalannya di <code>catch</code>. Bedanya, di GUI, penanganannya berupa dialog yang bisa langsung dibaca pengguna, bukan pesan di konsol.
</div>

---

## Contoh Kode: Menangkap NumberFormatException

```java
double initialBalance;
try {
    initialBalance = Double.parseDouble(initialBalanceField.getText().trim());
} catch (NumberFormatException e) {
    // tampilkan dialog, lalu return
}
```

`Double.parseDouble(...)` melempar `NumberFormatException` (unchecked, turunan `RuntimeException`) begitu isiannya bukan angka yang sah; `try`/`catch` tetap dipakai agar programnya tidak berhenti paksa, walau compiler sendiri tidak mewajibkannya.

---

## JOptionPane: Menampilkan Dialog

<div class="term-box">
<code>JOptionPane.showMessageDialog(parent, pesan, judul, tipe)</code> menampilkan sebuah jendela dialog kecil berisi pesan, lalu menghentikan sementara interaksi dengan jendela lain sampai pengguna menutupnya. Parameter <i>tipe</i> (mis. <code>ERROR_MESSAGE</code>, <code>WARNING_MESSAGE</code>) menentukan ikon yang ditampilkan.
</div>

---

## Contoh Kode: Menampilkan Dialog Galat

```java
JOptionPane.showMessageDialog(this,
        "Amount must be a number.",
        "Invalid input", JOptionPane.ERROR_MESSAGE);
return;
```

Dialog inilah yang dibaca pengguna, menggantikan pesan galat yang sebelumnya hanya tercetak ke konsol atau menghentikan program secara paksa.

---

## Kesalahan Umum: Lupa return Setelah Menampilkan Dialog

<div class="warn-box">
<b>Salah:</b> menampilkan dialog galat lewat <code>JOptionPane.showMessageDialog(...)</code>, lalu tetap melanjutkan kode berikutnya tanpa <code>return;</code>, seolah validasi sudah dianggap "selesai" begitu dialognya tampil.
</div>

**Benar:** dialog HANYA menampilkan pesan, ia tidak menghentikan alur program dengan sendirinya. Tanpa `return;` setelahnya, kode berikutnya tetap berjalan memakai nilai yang belum tentu valid (mis. variabel `initialBalance` yang gagal di-assign), berisiko menyebabkan exception lain atau data yang salah.

---

## Latihan

```java
try {
    initialBalance = Double.parseDouble(initialBalanceField.getText().trim());
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this, "Initial balance must be a number.",
            "Invalid input", JOptionPane.ERROR_MESSAGE);
}
Account account = new SavingsAccount(accountNumber, owner, initialBalance, DEFAULT_INTEREST_RATE);
```

Kode ini tidak menulis `return;` di dalam blok `catch`. Jelaskan apa yang bisa salah, lalu sebutkan perbaikannya.

---

## Jawaban Latihan

**Masalah:** jika `Double.parseDouble(...)` gagal, `initialBalance` tidak pernah ter-assign (galat kompilasi "variable might not have been initialized"), atau bila sudah punya nilai default sebelumnya, `new SavingsAccount(...)` tetap dipanggil dengan nilai lama yang tidak dimaksudkan pengguna. **Perbaikan:** tambahkan `return;` di baris terakhir blok `catch`, supaya rekening baru tidak pernah dibuat memakai `initialBalance` yang tidak valid.

---

## Rangkuman Bagian 1

- Input dari pengguna tidak pernah bisa dipercaya begitu saja; validasi di titik masuk mencegah exception sekaligus celah keamanan.
- Pola `try`/`catch` yang sama seperti Pertemuan 10 dipakai lagi, hanya penanganannya berupa dialog (`JOptionPane`), bukan pesan konsol.
- Setiap cabang validasi yang gagal wajib diakhiri `return;`, dialog saja tidak menghentikan alur program.

Selanjutnya: Bagian 2 membahas cara mengetahui baris mana yang sedang dipilih pengguna pada sebuah tabel.

---

<!-- _class: divider -->

# Bagian 2
## Baris Terpilih pada JTable

Sesi 2 dari 4

---

## Satu Tabel, Banyak Baris, Satu yang Dipilih

Sebuah `JTable` bisa menampilkan puluhan baris sekaligus. Ketika pengguna mengklik sebuah tombol aksi (mis. "Delete" atau "Edit"), kode itu sendiri tidak tahu baris data mana yang dimaksud, kecuali diberi tahu baris mana yang sedang disorot pengguna.

---

## Mengapa Ini Penting?

Pola "cari indeks terpilih, lalu ambil datanya" ini dipakai berulang di hampir semua aplikasi yang menampilkan daftar dan tombol aksi: e-mail client (memilih pesan mana yang dihapus), pengelola berkas (memilih berkas mana yang disalin), bahkan aplikasi spreadsheet (memilih sel mana yang diformat). Tanpa cara mengetahui baris terpilih, tombol aksi tidak akan pernah tahu objek mana yang harus diproses.

<div class="term-box">
Menguasai pola ini sekali berarti tahu cara kerja hampir seluruh antarmuka berbasis daftar/tabel, jauh melampaui satu aplikasi Bank Mini ini saja.
</div>

---

## getSelectedRow() dan getValueAt()

![h:260 Baris yang sedang dipilih pada tabel menentukan objek mana yang diproses](../assets/illustrations/table-selection.svg)

<div class="term-box">
<code>JTable.getSelectedRow()</code> mengembalikan indeks baris yang sedang disorot pengguna (atau <code>-1</code> bila belum ada yang dipilih). <code>getValueAt(baris, kolom)</code> mengambil nilai pada sel tertentu di baris itu.
</div>

---

## Contoh Kode: Membaca Baris Terpilih

```java
int row = table.getSelectedRow();
if (row < 0) {
    return; // belum ada baris yang dipilih
}
String name = (String) table.getValueAt(row, 0);
```

Memeriksa `row < 0` SEBELUM memanggil `getValueAt(...)` mencegah kode mencoba membaca baris yang sebenarnya tidak ada.

---

## Kesalahan Umum: Lupa Memeriksa -1

<div class="warn-box">
<b>Salah:</b> langsung memanggil <code>table.getValueAt(table.getSelectedRow(), 0)</code> tanpa memeriksa dulu apakah <code>getSelectedRow()</code> mengembalikan <code>-1</code>.
</div>

**Benar:** ketika belum ada baris yang dipilih, `getSelectedRow()` mengembalikan `-1`, dan `getValueAt(-1, 0)` melempar `ArrayIndexOutOfBoundsException`. Indeks yang dikembalikan wajib diperiksa dulu (`if (row < 0) return;`) sebelum dipakai untuk mengambil data.

---

## Latihan

Sebuah tabel pelanggan belum ada satu baris pun yang dipilih pengguna. Kode berikut dijalankan:

```java
int row = table.getSelectedRow();
String name = (String) table.getValueAt(row, 0);
```

Apa yang terjadi? Jelaskan, lalu sebutkan perbaikannya.

---

## Jawaban Latihan

**`ArrayIndexOutOfBoundsException` dilempar.** `getSelectedRow()` mengembalikan `-1` karena belum ada baris yang dipilih, lalu `getValueAt(-1, 0)` mencoba mengakses baris ke -1 yang tidak pernah ada. Perbaikan: tambahkan pemeriksaan `if (row < 0) { return; }` (atau tampilkan dialog peringatan) sebelum memanggil `getValueAt(...)`.

---

## Rangkuman Bagian 2

- `getSelectedRow()` mengembalikan indeks baris yang disorot pengguna, atau `-1` bila belum ada yang dipilih.
- `getValueAt(baris, kolom)` mengambil nilai sel tertentu; indeksnya wajib diperiksa dulu sebelum dipakai.
- Pola ini berlaku umum di hampir seluruh antarmuka berbasis daftar/tabel, bukan hanya `JTable` pada Java.

Selanjutnya: Bagian 3 menerapkan validasi input ke form tambah rekening Bank Mini.

---

<!-- _class: divider -->

# Bagian 3
## Menerapkan Dialog Tambah Rekening ke Bank Mini

Sesi 3 dari 4

---

## Dialog Tambah Rekening

![h:280 Dialog Add Account dengan nomor rekening dibangkitkan otomatis](../assets/illustrations/bank-mini-add-account-dialog.svg)

`BankMiniFrame` kini punya sebuah `JDialog` terpisah, `AddAccountDialog`, untuk menambah rekening baru: nomor rekening (dibangkitkan otomatis, tidak diketik), nama pemilik, telepon, jenis rekening, dan saldo awal. Sebelum objek `Account` benar-benar dibuat, seluruh isian yang MASIH bisa diketik diperiksa lebih dulu.

---

## `JDialog`: Jendela Modal

<div class="term-box">
<code>JFrame</code> cocok untuk jendela utama aplikasi, tetapi kurang tepat untuk form sekali-pakai seperti "tambah rekening": begitu selesai atau dibatalkan, jendelanya semestinya langsung tertutup dan mengembalikan kendali ke jendela pemanggilnya. <code>JDialog</code> dirancang untuk kebutuhan ini. Dialog modal (<code>true</code>) MEMBLOKIR interaksi dengan jendela pemanggilnya selama dialog masih terbuka, persis seperti <code>JOptionPane.showMessageDialog(...)</code> yang sudah dipakai sejak Bagian 1, hanya kali ini tampilannya dirancang sendiri lewat Matisse.
</div>

---

## Mencegah Duplikat Lewat Desain: Nomor Rekening Otomatis

Versi sebelumnya membiarkan pengguna mengetik nomor rekening sendiri, lalu MEMERIKSA apakah nomornya sudah dipakai. Cara itu tetap membuka peluang galat: pengguna bisa saja tetap mengetik nomor yang bentrok.

<div class="tip-box">
Pendekatan yang lebih baik: jangan biarkan pengguna mengetik nomor rekening sama sekali. <code>accountNumberValueLabel</code> pada dialog ini adalah sebuah <code>JLabel</code> (bukan <code>JTextField</code>), diisi otomatis dari <code>Bank.nextAccountNumber()</code>. Kelas galat "nomor rekening duplikat" DICEGAH lewat desain, bukan ditangkap setelah terlanjur diketik.
</div>

---

## Contoh Kode: Bank.nextAccountNumber()

```java
public String nextAccountNumber() {
    int max = 0;
    for (Account acc : repository.findAll()) {
        String number = acc.getAccountNumber();
        if (number.length() == 4 && number.charAt(0) == 'A') {
            max = Math.max(max, Integer.parseInt(number.substring(1)));
        }
    }
    return String.format("A%03d", max + 1);
}
```

Nomor rekening tertinggi yang sudah ada dicari lebih dulu, lalu nomor berikutnya dibangkitkan dari situ, mis. `A001`, `A002` menghasilkan `A003`.

---

## Contoh Kode: Memeriksa Kolom Wajib

```java
String ownerName = ownerField.getText().trim();

if (ownerName.isEmpty()) {
    JOptionPane.showMessageDialog(this,
            "Owner name is required.",
            "Invalid input", JOptionPane.ERROR_MESSAGE);
    return;
}
```

Hanya `ownerField` yang wajib diperiksa kosong-tidaknya; `accountNumberValueLabel` tidak pernah kosong maupun salah format, sebab isinya bukan ketikan pengguna.

---

## Contoh Kode: Mem-parse Saldo Awal

```java
double initialBalance;
try {
    initialBalance = Double.parseDouble(initialBalanceField.getText().trim());
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this,
            "Initial balance must be a number.",
            "Invalid input", JOptionPane.ERROR_MESSAGE);
    return;
}
```

Pola dari Bagian 1 dipakai persis sama: bungkus `Double.parseDouble(...)` dalam `try`, tampilkan dialog dan `return;` di `catch`.

---

## Contoh Kode: Membuat dan Menyimpan Account

```java
String accountNumber = accountNumberValueLabel.getText();
Account account;
if (accountTypeCombo.getSelectedItem().equals("Savings")) {
    account = new SavingsAccount(accountNumber, owner, initialBalance, DEFAULT_INTEREST_RATE);
} else {
    account = new CheckingAccount(accountNumber, owner, initialBalance, DEFAULT_OVERDRAFT_LIMIT);
}

bank.addAccount(account);
dispose();
```

Jenis rekening yang dipilih pada `accountTypeCombo` menentukan subclass `Account` mana yang dibuat, tidak ada yang berbeda dari cara kedua kelas ini dipakai sejak Pertemuan 6. `bank.addAccount(...)` tidak perlu lagi diperiksa nilai kembaliannya seperti versi lama, sebab `nextAccountNumber()` menjamin nomornya selalu baru; `dispose()` menutup dialog, `BankMiniFrame` memuat ulang tabelnya setelahnya.

---

## Latihan

Misalkan `AddAccountDialog` dibuat TIDAK modal (`super(parent, false)`). Pengguna mengklik tombol **Add Account...** dua kali berturut-turut sebelum dialog pertama sempat ditutup, mengisi keduanya, lalu menekan **Save** pada kedua dialog secara cepat.

Apa yang bisa salah? Kaitkan jawabanmu dengan `Bank.nextAccountNumber()`.

---

## Jawaban Latihan

Karena tidak modal, `BankMiniFrame` tetap responsif selagi dialog pertama terbuka, sehingga tombol **Add Account...** bisa diklik lagi dan membuka dialog KEDUA. Bila kedua dialog sempat terbuka sebelum salah satunya menyimpan, KEDUANYA memanggil `bank.nextAccountNumber()` saat rekening tertinggi masih sama, menghasilkan nomor rekening yang SAMA persis untuk dua rekening berbeda. Dialog modal (`true`) mencegah ini dengan memblokir interaksi dengan jendela lain, termasuk mengklik tombol **Add Account...** lagi, selama dialog pertama masih terbuka.

---

## Rangkuman Bagian 3

- `AddAccountDialog` adalah jendela modal terpisah; nomor rekening dibangkitkan otomatis lewat `Bank.nextAccountNumber()`, bukan diketik pengguna, mencegah duplikat lewat desain.
- Validasi kolom wajib dan parsing saldo awal memakai pola yang sama seperti Bagian 1.
- Sifat modal `JDialog` juga mencegah dua dialog tambah rekening terbuka bersamaan, yang bisa merusak asumsi nomor rekening selalu unik.

Selanjutnya: Bagian 4 menerapkan pola baris-terpilih untuk MENCEGAH tombol setor dan tarik saldo diklik tanpa rekening yang dipilih.

---

<!-- _class: divider -->

# Bagian 4
## Menerapkan Setor dan Tarik Saldo ke Bank Mini

Sesi 4 dari 4

---

## Setor dan Tarik Saldo

![h:220 Tombol Deposit dan Withdraw aktif setelah satu baris dipilih](../assets/illustrations/bank-mini-window-selected.svg)

Tombol **Deposit...** dan **Withdraw...** mulai NONAKTIF (abu-abu). Keduanya baru menyala setelah pengguna memilih satu baris di tabel, lalu memakai `getSelectedAccount()` untuk mencari objek `Account`-nya lewat `Bank.findAccount()`.

<div class="tip-box">
Tidak satu pun kelas <code>Account</code>, <code>Bank</code>, atau <code>InsufficientBalanceException</code> yang diubah untuk mendukung GUI ini. GUI hanya memanggil method yang sudah ada sejak beberapa pertemuan lalu, lewat cara yang berbeda.
</div>

---

## Mencegah, Bukan Hanya Menangani: Nonaktifkan Tombol

Versi yang hanya memeriksa `row < 0` lalu menampilkan dialog peringatan ("Select an account first") tetap MEMBIARKAN pengguna mengklik tombol yang belum boleh diklik, baru menanganinya setelah kejadian.

<div class="term-box">
Pendekatan yang lebih baik: sambungkan status pilihan tabel ke status AKTIF/NONAKTIF tombolnya sendiri lewat <code>ListSelectionListener</code>. Begitu tidak ada baris terpilih, tombol Deposit dan Withdraw dinonaktifkan (<code>setEnabled(false)</code>); pengguna secara FISIK tidak bisa mengkliknya. Galat "belum memilih rekening" dicegah lewat desain antarmuka, bukan ditangkap lewat dialog peringatan setelah tombol terlanjur diklik.
</div>

---

## Contoh Kode: configureSelectionListener()

```java
private void configureSelectionListener() {
    accountTable.getSelectionModel().addListSelectionListener(evt -> {
        boolean rowSelected = accountTable.getSelectedRow() >= 0;
        depositButton.setEnabled(rowSelected);
        withdrawButton.setEnabled(rowSelected);
    });
}
```

Dipanggil sekali di constructor, SETELAH `loadAccounts()`. Satu listener ini mengatur KEDUA tombol sekaligus, setiap kali baris terpilih berubah.

---

## Contoh Kode: getSelectedAccount()

```java
private Account getSelectedAccount() {
    int row = accountTable.getSelectedRow();
    String accountNumber = (String) accountTable.getValueAt(row, 0);
    return bank.findAccount(accountNumber);
}
```

Berbeda dari Bagian 2, method ini TIDAK LAGI memeriksa `row < 0`: sebab `depositButton`/`withdrawButton` hanya bisa diklik ketika sebuah baris benar-benar terpilih, `getSelectedRow()` di sini dijamin valid oleh `configureSelectionListener()`.

---

## Contoh Kode: depositButtonActionPerformed

```java
Account account = getSelectedAccount();
String input = JOptionPane.showInputDialog(this,
        "Deposit amount for " + account.getAccountNumber()
                + " (" + account.getOwner().getName() + "):");
if (input == null) {
    return;
}
```

`JOptionPane.showInputDialog(...)` menampilkan dialog berisi satu kolom teks, mengembalikan isiannya sebagai `String`, atau `null` bila pengguna menekan Cancel.

---

## Kesalahan Umum: Menyamakan Cancel dengan Isian Kosong

<div class="warn-box">
<b>Salah:</b> langsung memanggil <code>Double.parseDouble(input.trim())</code> tanpa memeriksa <code>input == null</code> lebih dulu, mengira Cancel akan menghasilkan galat <code>NumberFormatException</code> yang sama seperti isian kosong.
</div>

**Benar:** menekan Cancel pada `showInputDialog(...)` mengembalikan `null`, BUKAN string kosong `""`. Memanggil `.trim()` pada `null` melempar `NullPointerException`, bukan `NumberFormatException`. Pengecekan `if (input == null) return;` wajib dilakukan SEBELUM blok `try`/`catch` `NumberFormatException`, sebab keduanya adalah kegagalan yang berbeda.

---

## Contoh Kode: withdrawButtonActionPerformed

```java
try {
    account.withdraw(amount);
    loadAccounts();
} catch (InsufficientBalanceException e) {
    JOptionPane.showMessageDialog(this,
            e.getMessage(),
            "Withdrawal failed", JOptionPane.ERROR_MESSAGE);
}
```

Persis pola Pertemuan 10: `withdraw()` melempar `InsufficientBalanceException`, kali ini pesannya ditampilkan lewat dialog alih-alih dicetak ke konsol.

---

## Latihan

`SavingsAccount` dengan saldo Rp 100.000 dipilih di tabel, lalu pengguna mengklik **Withdraw...**, mengetik `500000` pada dialog input, dan menekan OK.

Apa yang terjadi pada dialog input dan pada tabelnya? Jelaskan alurnya baris demi baris.

---

## Jawaban Latihan

Dialog input SELALU tertutup begitu OK ditekan, sebab `showInputDialog` bukan kolom teks yang menetap di jendela utama. `input` berisi `"500000"`, `Double.parseDouble(...)` berhasil. `account.withdraw(500000)` dipanggil, melempar `InsufficientBalanceException` sebab saldo Rp 100.000 tidak cukup menyisakan `MINIMUM_BALANCE` `SavingsAccount`. Baris `loadAccounts()` TIDAK PERNAH tercapai (dilompati oleh exception), dialog error "Withdrawal failed" muncul menampilkan pesannya, dan tabel tetap menampilkan saldo LAMA, sebab tidak ada perubahan yang berhasil disimpan.

---

## Rangkuman Bagian 4

- `configureSelectionListener()` menyambungkan baris terpilih ke status aktif/nonaktif `depositButton`/`withdrawButton`, mencegah klik tanpa rekening terpilih lewat desain, bukan lewat dialog peringatan.
- `JOptionPane.showInputDialog(...)` mengembalikan `null` (bukan string kosong) bila Cancel ditekan; ini WAJIB diperiksa sebelum `Double.parseDouble(...)`.
- `withdrawButtonActionPerformed` memakai pola `try`/`catch` yang sama sejak Pertemuan 10 untuk `InsufficientBalanceException`.

---

## Rangkuman Pertemuan 14

- Input pengguna divalidasi di titik masuk lewat `try`/`catch`, kegagalannya ditampilkan lewat `JOptionPane`, bukan konsol.
- Baris terpilih pada `JTable` dipakai untuk MENCEGAH aksi tidak valid lewat `ListSelectionListener` dan `setEnabled(...)`, bukan sekadar menangkapnya setelah tombol terlanjur diklik.
- Bank Mini menerapkan keduanya: dialog tambah rekening dengan nomor otomatis, dan tombol setor/tarik saldo yang nonaktif sampai sebuah rekening dipilih.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab GUI Components, Exception Handling

Oracle Java Tutorials: ["How to Use Tables"](https://docs.oracle.com/javase/tutorial/uiswing/components/table.html), "Validating Input"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 14

---

## Diskusi

`configureSelectionListener()` mengatur status aktif/nonaktif KEDUA tombol (`depositButton` dan `withdrawButton`) sekaligus dari SATU listener, bukan dua listener terpisah di masing-masing tombol. Jelaskan dengan kata-katamu sendiri mengapa pendekatan ini lebih baik dibanding memeriksa `getSelectedRow()` secara terpisah di dalam setiap `depositButtonActionPerformed`/`withdrawButtonActionPerformed` (kaitkan jawabanmu dengan Single Responsibility Principle dari Pertemuan 11).
