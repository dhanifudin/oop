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
- Cara membaca baris yang sedang dipilih pada `JTable` untuk menentukan objek mana yang diproses
- Penerapan pada Bank Mini: form tambah rekening, tombol setor dan tarik saldo pada `BankMiniFrame`

<div class="tip-box">
Latihan pemrograman untuk materi hari ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 14.
</div>

---

## Peta Sesi Hari Ini

- **Sesi 1 (50')**: Validasi input di titik masuk
- **Sesi 2 (50')**: Baris terpilih pada JTable
- **Sesi 3 (50')**: Menerapkan form tambah rekening ke Bank Mini
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
double amount;
try {
    amount = Double.parseDouble(amountField.getText().trim());
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

**Benar:** dialog HANYA menampilkan pesan, ia tidak menghentikan alur program dengan sendirinya. Tanpa `return;` setelahnya, kode berikutnya tetap berjalan memakai nilai yang belum tentu valid (mis. variabel `amount` yang gagal di-assign), berisiko menyebabkan exception lain atau data yang salah.

---

## Latihan

```java
try {
    amount = Double.parseDouble(amountField.getText().trim());
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(this, "Amount must be a number.",
            "Invalid input", JOptionPane.ERROR_MESSAGE);
}
account.deposit(amount);
```

Kode ini tidak menulis `return;` di dalam blok `catch`. Jelaskan apa yang bisa salah, lalu sebutkan perbaikannya.

---

## Jawaban Latihan

**Masalah:** jika `Double.parseDouble(...)` gagal, `amount` tidak pernah ter-assign (galat kompilasi "variable might not have been initialized"), atau bila sudah punya nilai default sebelumnya, `account.deposit(amount)` tetap dipanggil dengan nilai lama yang tidak dimaksudkan pengguna. **Perbaikan:** tambahkan `return;` di baris terakhir blok `catch`, supaya `account.deposit(amount)` tidak pernah dipanggil ketika input tidak valid.

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
## Menerapkan Form Tambah Rekening ke Bank Mini

Sesi 3 dari 4

---

## Form Tambah Rekening

![h:260 Form tambah rekening pada BankMiniFrame](../assets/screenshots/pertemuan-14/p14-add-account.png)

`BankMiniFrame` kini punya form untuk menambah rekening baru: nomor rekening, nama pemilik, telepon, jenis rekening, dan saldo awal. Sebelum objek `Account` benar-benar dibuat, seluruh isian diperiksa lebih dulu.

---

## Contoh Kode: Memeriksa Kolom Wajib

```java
String accountNumber = accountNumberField.getText().trim();
String ownerName = ownerField.getText().trim();

if (accountNumber.isEmpty() || ownerName.isEmpty()) {
    JOptionPane.showMessageDialog(this,
            "Account number and owner name are required.",
            "Invalid input", JOptionPane.ERROR_MESSAGE);
    return;
}
```

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

## Contoh Kode: Membuat Account Sesuai Jenis yang Dipilih

```java
Account account;
if (accountTypeCombo.getSelectedItem().equals("Savings")) {
    account = new SavingsAccount(accountNumber, owner, initialBalance, DEFAULT_INTEREST_RATE);
} else {
    account = new CheckingAccount(accountNumber, owner, initialBalance, DEFAULT_OVERDRAFT_LIMIT);
}
```

Jenis rekening yang dipilih pada `accountTypeCombo` menentukan subclass `Account` mana yang dibuat, tidak ada yang berbeda dari cara kedua kelas ini dipakai sejak Pertemuan 6.

---

## Kesalahan Umum: Tidak Memeriksa Nomor Rekening Duplikat

<div class="warn-box">
<b>Salah:</b> memanggil <code>bank.addAccount(account)</code> lalu langsung memanggil <code>clearAddAccountFields()</code> dan <code>loadAccounts()</code>, tanpa memeriksa nilai kembalian <code>addAccount(...)</code>.
</div>

**Benar:** `Bank.addAccount(...)` mengembalikan `false` bila nomor rekening sudah dipakai rekening lain (sejak Pertemuan 11). Nilai kembalian ini wajib diperiksa; bila `false`, tampilkan dialog galat dan `return;`, jangan lanjut membersihkan form seolah rekening berhasil ditambahkan.

---

## Latihan

Pengguna menambahkan rekening dengan nomor `A001`, yang ternyata sudah dipakai rekening lain di tabel.

Apa yang seharusnya ditampilkan aplikasi, dan apa yang TIDAK boleh terjadi pada form maupun tabelnya?

---

## Jawaban Latihan

Aplikasi menampilkan dialog galat "Account number A001 already exists." lewat `JOptionPane.showMessageDialog(...)`. Yang TIDAK boleh terjadi: form tidak boleh dikosongkan (`clearAddAccountFields()` tidak dipanggil) dan tabel tidak boleh dimuat ulang (`loadAccounts()` tidak dipanggil), sebab tidak ada rekening baru yang benar-benar berhasil ditambahkan.

---

## Rangkuman Bagian 3

- Form tambah rekening memvalidasi kolom wajib dan mem-parse saldo awal, persis pola Bagian 1.
- Jenis rekening yang dipilih pengguna menentukan subclass `Account` yang dibuat.
- Nilai kembalian `Bank.addAccount(...)` wajib diperiksa; nomor rekening duplikat harus ditolak dengan dialog, bukan diteruskan seolah berhasil.

Selanjutnya: Bagian 4 menerapkan pola baris-terpilih ke tombol setor dan tarik saldo.

---

<!-- _class: divider -->

# Bagian 4
## Menerapkan Setor dan Tarik Saldo ke Bank Mini

Sesi 4 dari 4

---

## Setor dan Tarik Saldo

![h:240 Tombol Deposit dan Withdraw pada BankMiniFrame](../assets/screenshots/pertemuan-14/p14-deposit-withdraw.png)

Tombol **Deposit** dan **Withdraw** memakai `getSelectedAccount()`, method bantu yang membaca baris terpilih di tabel lalu mencari objek `Account`-nya lewat `Bank.findAccount()`.

<div class="tip-box">
Tidak satu pun kelas <code>Account</code>, <code>Bank</code>, atau <code>InsufficientBalanceException</code> yang diubah untuk mendukung GUI ini. GUI hanya memanggil method yang sudah ada sejak beberapa pertemuan lalu, lewat cara yang berbeda.
</div>

---

## Contoh Kode: getSelectedAccount()

```java
private Account getSelectedAccount() {
    int row = accountTable.getSelectedRow();
    if (row < 0) {
        return null;
    }
    String accountNumber = (String) accountTable.getValueAt(row, 0);
    return bank.findAccount(accountNumber);
}
```

Pola dari Bagian 2 (periksa `row < 0`) digabung dengan `Bank.findAccount(...)` yang sudah ada sejak Pertemuan 9.

---

## Contoh Kode: depositButtonActionPerformed

```java
Account account = getSelectedAccount();
if (account == null) {
    JOptionPane.showMessageDialog(this,
            "Select an account in the table first.",
            "No account selected", JOptionPane.WARNING_MESSAGE);
    return;
}
```

`getSelectedAccount()` mengembalikan `null` bila belum ada baris terpilih ATAU nomor rekeningnya tidak ditemukan; keduanya ditangani sekali lewat pengecekan `null` ini.

---

## Contoh Kode: withdrawButtonActionPerformed

```java
try {
    account.withdraw(amount);
    amountField.setText("");
    loadAccounts();
} catch (InsufficientBalanceException e) {
    JOptionPane.showMessageDialog(this,
            e.getMessage(),
            "Withdrawal failed", JOptionPane.ERROR_MESSAGE);
}
```

Persis pola Pertemuan 10: `withdraw()` melempar `InsufficientBalanceException`, kali ini pesannya ditampilkan lewat dialog alih-alih dicetak ke konsol.

---

## Kesalahan Umum: Membersihkan Input Sebelum Operasi Berhasil

<div class="warn-box">
<b>Salah:</b> menulis <code>amountField.setText(""); account.withdraw(amount);</code>, mengosongkan kolom SEBELUM memastikan <code>withdraw(...)</code> benar-benar berhasil.
</div>

**Benar:** `amountField.setText("")` dan `loadAccounts()` hanya dipanggil SETELAH `account.withdraw(amount)` selesai tanpa melempar exception, di dalam blok `try` yang sama. Bila `withdraw(...)` gagal, kolom `amountField` tetap berisi nilai yang tadi diketik, supaya pengguna tidak perlu mengetik ulang.

---

## Latihan

`SavingsAccount` dengan saldo Rp 100.000 dipilih di tabel, lalu pengguna mengetik `500000` di kolom Amount dan menekan tombol Withdraw.

Apa yang terjadi pada dialog, kolom Amount, dan tabelnya? Jelaskan.

---

## Jawaban Latihan

`account.withdraw(500000)` melempar `InsufficientBalanceException` (saldo tidak mencukupi). Dialog galat "Withdrawal failed" ditampilkan berisi pesan dari `e.getMessage()`. Kolom `amountField` TETAP berisi `500000`, TIDAK dikosongkan, sebab `setText("")` berada SETELAH `withdraw(...)` di dalam blok `try` yang sama, baris itu tidak pernah tercapai begitu `withdraw(...)` melempar exception. Tabel juga TIDAK dimuat ulang, sebab `loadAccounts()` ada di baris setelahnya, juga tidak pernah tercapai.

---

## Rangkuman Bagian 4

- `getSelectedAccount()` menggabungkan pola baris-terpilih (Bagian 2) dengan `Bank.findAccount()` yang sudah ada sejak Pertemuan 9.
- `depositButtonActionPerformed` dan `withdrawButtonActionPerformed` sama-sama menangani `account == null` sebelum melanjutkan.
- Kolom input dan tabel hanya dibersihkan/dimuat ulang SETELAH operasi benar-benar berhasil, bukan sebelum atau tanpa syarat.

---

## Rangkuman Pertemuan 14

- Input pengguna divalidasi di titik masuk lewat `try`/`catch`, kegagalannya ditampilkan lewat `JOptionPane`, bukan konsol.
- `JTable.getSelectedRow()` dan `getValueAt(...)` membaca baris yang sedang dipilih pengguna; indeks `-1` wajib diperiksa dulu.
- Bank Mini menerapkan keduanya: form tambah rekening tervalidasi, dan tombol setor/tarik saldo memakai baris terpilih lewat `getSelectedAccount()`.

---

<!-- _class: lead -->

# Referensi

Deitel, *Java How to Program*, bab GUI Components, Exception Handling

Oracle Java Tutorials: ["How to Use Tables"](https://docs.oracle.com/javase/tutorial/uiswing/components/table.html), "Validating Input"

Latihan pemrograman untuk materi ini tersedia di jobsheet Praktikum Pemrograman Berbasis Objek (RTI253008), Pertemuan 14

---

## Diskusi

`depositButtonActionPerformed` dan `withdrawButtonActionPerformed` sama-sama memanggil `getSelectedAccount()` dan menampilkan dialog peringatan bila belum ada baris yang dipilih. Jelaskan dengan kata-katamu sendiri: mengapa validasi "apakah ada baris yang dipilih" ini perlu dilakukan di KEDUA method secara terpisah, dan bagaimana caranya method itu bisa dipakai bersama tanpa menduplikasi logikanya (kaitkan jawabanmu dengan Single Responsibility Principle dari Pertemuan 11).
