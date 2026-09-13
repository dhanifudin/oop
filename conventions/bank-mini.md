# Studi kasus tunggal: Bank Mini

Satu studi kasus yang sama, **Bank Mini** (sistem rekening bank sederhana),
dipakai berkesinambungan dari Pertemuan 2 sampai 16, supaya mahasiswa
membangun satu aplikasi yang sama dari kelas tunggal sampai aplikasi GUI +
database, bukan potongan-potongan lepas.

## Pemetaan topik resmi per minggu

Sumber "POKOK MATERI PBO 2026" (spreadsheet dari Dosen koordinator,
menggantikan urutan lama yang diturunkan dari PDF RPS di `docs/` untuk
minggu 6-11 karena keduanya sempat berbeda): 1 Pengantar Konsep Dasar OOP;
2 Class dan Object; 3 Enkapsulasi; 4 Relasi Class; 5 Kuis 1; 6 Inheritance;
7 Overriding dan Overloading; 8 UTS; 9 Abstract Class dan Interface;
10 Polimorfisme; 11 SOLID Principle; 12 Kuis 2; 13-14 GUI + Database (satu
proyek NetBeans dibangun berkesinambungan lintas kedua pertemuan ini, lihat
catatan Matisse di bawah); 15-16 Project (PBL, mahasiswa memilih ekstensi
sendiri); 17 UAS. `archive/pertemuan-11-solid/` (SOLID principles, domain
pemrosesan pesanan) TIDAK LAGI dianggap "diarsipkan permanen": topik SOLID
kini resmi ada di minggu 11, tetapi jobsheet harus ditulis ulang
Bank-Mini-only (bukan dipakai ulang domain lama), slide konsep boleh
merujuk materi lama itu sebagai toy ilustrasi.

**Exception handling dan Collections** (dulu direncanakan sebagai materi
tersendiri minggu 10-11) tidak punya slot minggu sendiri di pemetaan resmi
ini. Keduanya dianyam sebagai sub-topik: exception handling masuk ke minggu
10 (Polimorfisme, bersama `InsufficientBalanceException`), Collections
(`ArrayList`/`HashMap`) masuk ke minggu 11 (SOLID, sebagai bagian refactor
`Bank` yang sekaligus mengajarkan prinsip SOLID).

## Pola "konsep dulu, baru studi kasus"

Tiap pertemuan memperkenalkan konsep baru lewat contoh generik kecil (toy
example, satu kelas atau beberapa kelas kecil, TIDAK terkait Bank Mini)
untuk paparan pertama, baru menerapkannya ke Bank Mini. Toy generik ini
HANYA muncul di slide konsep (ilustrasi utama, ditutup dengan
diagram/contoh Bank Mini); **jobsheet praktikum sepenuhnya Bank-Mini-only,
tidak lagi mengetik kode toy generik apa pun ke dalam proyek**. Sebelumnya
jobsheet Pertemuan 2 sempat mengetik kelas `Rectangle` (dan tugas `Circle`)
langsung ke proyek `bank-mini`, sampai-sampai jobsheet Pertemuan 3 harus
membuka dengan langkah menghapus `Rectangle.java`; ini sudah diperbaiki
(jobsheet Pertemuan 2 kini membangun `Account` langsung dari Langkah 2,
`Rectangle` tetap jadi contoh pengantar slide saja). Bila sebuah langkah
jobsheet butuh eksposur konsep yang belum tuntas dibahas di kelas konsep,
pakai kotak "Konsep Singkat" (lihat aturan urutan di [[jobsheets]]), bukan
menghidupkan kembali toy generik di kode.

Gambar memori stack/heap Pertemuan 2 (`p02-memory-new.png`,
`p02-memory-alias.png` di `jobsheets/assets/uml/`) adalah varian berlabel
`Account`, dirender dari `stack-heap-single-account.svg`/
`stack-heap-alias-account.svg`, terpisah dari SVG `Rectangle` generik
(`stack-heap-single.svg`/`stack-heap-alias.svg`) yang tetap dipakai slide.

Aturan generik-dulu di atas berlaku untuk SELURUH bagian sebelum
"Menerapkan ke Bank Mini", bukan cuma ilustrasi pertama: pernah lolos tanpa
disadari pada Pertemuan 3 dan 4, yang narasi pembukanya sudah langsung
menyebut `Account`/`Bank`/`Customer` padahal ilustrasinya sendiri masih
generik (mis. `direct-access-bug.svg` menampilkan `Thermostat`, bukan
`Account`), dan bahkan satu ilustrasi (`relation-strengths.svg`) sempat
mencampur label generik dengan `Bank`/`Account` di diagram yang sama.
Periksa toy generik dan ilustrasinya benar-benar konsisten (nama kelas
yang disebut di narasi sama dengan yang tampil di gambar) sebelum
menganggap sebuah "Bagian" selesai.

**Larangan ini juga berlaku untuk referensi recap ke penerapan Bank Mini
pertemuan-pertemuan sebelumnya**, bukan cuma perkenalan pertama sebuah
konsep: pernah lolos tanpa disadari di slide recap OCP/LSP/ISP pada
Pertemuan 11 (menjelaskan prinsip lewat
`Account`/`canWithdraw()`/`SavingsAccount`/`InterestBearing`, alih-alih
memakai ulang contoh generik yang sama persis dipakai saat konsep itu
pertama diajarkan) dan pada slide "Mengapa Ini Penting?" overloading
Pertemuan 7 (memakai nama method `deposit()`/`depositAmount()` Bank Mini).
Perbaikannya: recap tetap memakai ulang contoh generik dari pertemuan
asalnya (mis. `PaymentMethod` untuk OCP, `Animal`/`Dog`/`Cat` untuk LSP),
forward-reference ke pertemuan lain yang menyebut nama kelas Bank Mini juga
masuk kategori ini (mis. kalimat penutup slide DIP yang menyebut
`AccountRepository`/`Bank.java` untuk menunjuk ke Pertemuan 15, seharusnya
tanpa identifier).

## Slide "Mengapa Ini Penting?"

**Setiap konsep butuh slide "Mengapa Ini Penting?" yang berdiri sendiri**,
ditempatkan setelah slide masalah/motivasi awal dan sebelum definisi formal
(term-box). Slide ini HARUS menjelaskan dampak nyata di rekayasa perangkat
lunak sungguhan, bukan sekadar mengulang mekanisme toy yang baru
dijelaskan: apa yang benar-benar rusak tanpa konsep ini pada skala besar
(bug yang sulit dilacak, biaya perawatan, risiko tim), dan mengapa industri
menganggapnya fondasional. "Penting karena ini konsep dasar OOP" tanpa
penjelasan konkret TIDAK cukup; gunakan skenario spesifik (aplikasi
berskala besar, tim yang berbeda-beda, bug nyata yang pernah terjadi).

## Cakupan kelas Bank Mini

Jangan tambah di luar ini kecuali RPS berubah: `Account`, `Customer`,
`SavingsAccount`, `CheckingAccount`, `BusinessAccount` (tugas mandiri),
`Bank`, `Transaction`, `InsufficientBalanceException`, interface
`InterestBearing`, `Auditable`, dan `AccountRepository`, kelas GUI/JDBC
`BankMiniFrame` dan `JdbcAccountRepository`.

Pertemuan 15 menambah cakupan ini dengan mekanisme autentikasi sederhana
(permintaan eksplisit pengguna, bukan penambahan tak terarah): `User`
(model, `username` + `passwordHash`), interface `UserRepository`,
`InMemoryUserRepository` (preview, mengikuti pola `AccountRepository` dari
Pertemuan 11), `JdbcUserRepository`, kelas utilitas `PasswordHasher`
(SHA-256 lewat `java.security.MessageDigest`, TANPA dependency eksternal;
jobsheet/slide WAJIB memberi warn-box eksplisit bahwa sistem produksi
memakai hashing bergaram dan berulang seperti bcrypt/Argon2/PBKDF2, SHA-256
polos di sini murni penyederhanaan pengajaran, bukan contoh siap
produksi), dan `LoginFrame` (GUI, memakai `JPasswordField` bukan
`JTextField` biasa untuk kolom sandi). Alasan penempatan di Pertemuan 15,
bukan 13-14: autentikasi sungguhan butuh kredensial yang tersimpan dan
diperiksa dari data persisten, bukan `if` yang di-hardcode di kode Java,
sehingga baru masuk akal setelah database (JDBC) diperkenalkan; Pertemuan
14 menutup dengan catatan singkat yang secara eksplisit menyebut celah ini
dan menunjuk ke Pertemuan 15
(`jobsheets/id/pertemuan-14-gui-netbeans-matisse.md`, bagian D).

`PasswordHasher` diletakkan di paket induk `id.ac.polinema` (bukan
subpaket baru), mengikuti pola `Bank`/`Main`: kelas yang tidak cocok masuk
`model`/`repository`/`ui` tetap di paket induk, bukan dalih untuk menambah
subpaket keempat. Paket `id.ac.polinema`; subpaket `model`/`repository`/`ui`
baru dipakai mulai Pertemuan 13 (fase Maven/GUI).

## Evolusi kelas per pertemuan praktikum

State SETELAH pertemuan tsb, mengikuti pemetaan topik resmi di atas:

- Pertemuan 2: `Account` v1 (atribut publik, tanpa validasi).
- 3: enkapsulasi + konstruktor + validasi.
- 4: `Customer` + `Bank` dengan array `Account[]`.
- 6: `SavingsAccount`/`CheckingAccount` murni inheritance (atribut dan
  method BARU saja, belum ada override, `withdraw()` masih satu aturan
  generik warisan, sengaja dibiarkan belum optimal untuk memotivasi
  pertemuan berikutnya).
- 7: `Account.canWithdraw()` jadi hook `protected` yang di-override tiap
  subclass, plus overload `deposit(double, String)`.
- 9: `Account` jadi abstract class (method abstrak `monthlyFee()`) +
  interface `InterestBearing`.
- 10: polymorphism di `Bank` (iterasi polimorfik, `instanceof`) + exception
  handling (`InsufficientBalanceException`).
- 11: SOLID Principle diajarkan LEWAT refactor `Bank` dari `Account[]` ke
  `Map<String, Account>` (`LinkedHashMap`, dipilih di atas `HashMap` polos
  supaya urutan iterasi tetap deterministik) + kelas `Transaction`
  disimpan per `Account` lewat `List<Transaction>` (`ArrayList`) (SRP
  lewat `Transaction` terpisah, OCP lewat hook `canWithdraw()` yang sudah
  ada, LSP lewat kontrak subclass yang konsisten, ISP lewat interface
  kecil `InterestBearing`/`Auditable`, DIP lewat pengenalan
  `AccountRepository` + `InMemoryAccountRepository` sebagai preview).
- 13-14: SATU proyek Maven + GUI dibangun berkesinambungan (lihat catatan
  Matisse di bawah). `BankMiniFrame` HANYA menampilkan `JTable` + satu baris
  tombol (`Add Account...`, `Deposit...`, `Withdraw...`, `Refresh`,
  `Process Month End` di tugas); TIDAK ADA form input yang menetap di
  jendela utama. Tambah rekening lewat `AddAccountDialog` (Pertemuan 14,
  `JDialog` modal terpisah): nomor rekening dibangkitkan otomatis lewat
  `Bank.nextAccountNumber()` (`JLabel`, bukan `JTextField`, mencegah
  duplikat lewat desain, bukan diperiksa-lalu-ditolak setelah diketik).
  Setor/tarik saldo lewat `JOptionPane.showInputDialog(...)` per aksi
  (bukan kolom Amount yang menetap); `depositButton`/`withdrawButton`
  mulai `enabled=false`, disambungkan ke `ListSelectionListener` pada
  `accountTable` lewat method `configureSelectionListener()` supaya
  keduanya hanya aktif saat sebuah baris benar-benar terpilih (mencegah
  klik tanpa rekening lewat desain, bukan lewat dialog peringatan
  "No account selected" setelah tombol terlanjur diklik). Jangan regresi
  ke form inline (`formPanel`/`actionsPanel`/`amountField`) atau ke
  validasi nomor-rekening-duplikat manual saat merevisi deck/jobsheet ini;
  keduanya sengaja diganti atas permintaan eksplisit pengguna supaya Bank
  Mini terasa seperti aplikasi nyata yang MENCEGAH galat, bukan hanya
  melaporkannya.
- 15 Langkah 1: `JdbcAccountRepository` + SQLite menggantikan
  `InMemoryAccountRepository` (menyambung interface Pertemuan 11, tanpa
  mengubah `Bank.java`), plus `Bank.saveAccount()` (baru) dipanggil ulang
  setelah `deposit()`/`withdraw()`/`processMonthEnd()` supaya perubahan
  saldo ikut tersimpan (payoff konkret: penyimpanan in-memory otomatis
  "tersimpan" lewat referensi objek yang sama, penyimpanan database TIDAK,
  harus disimpan ulang secara eksplisit setiap perubahan, perbedaan nyata
  yang jadi materi "Mengapa Ini Penting?"). Langkah 2:
  `User`/`UserRepository`/`InMemoryUserRepository` (preview singkat) lalu
  `JdbcUserRepository`/`PasswordHasher`/`LoginFrame`, `Main.java`
  menjalankan `LoginFrame` lebih dulu, bukan `BankMiniFrame` langsung.
  `JdbcAccountRepository`/`JdbcUserRepository` memakai Apache Commons
  DbUtils (`QueryRunner` dibuat dari `SQLiteDataSource`, plus satu
  `ResultSetHandler` kecil per kelas untuk pemetaan baris polimorfik/flat)
  alih-alih `Connection`/`PreparedStatement`/`ResultSet` manual; jangan
  regresi ke JDBC mentah saat merevisi deck atau jobsheet ini.
  Tugas: `BankMiniFrame` menampilkan "Logged in as: &lt;username&gt;" di
  judul jendela (constructor menerima parameter username), plus satu
  pengguna tambahan.
- 16: PBL (mahasiswa memilih ekstensi sendiri).

Rencana lengkap ada di
`/home/dhs/.claude/plans/you-re-top-oop-lecturer-elegant-wind.md` (riwayat
plan mode, bukan bagian repo ini, tapi jadi rujukan desain).

## Konvensi teaching Pertemuan 6 vs 7

Pertemuan 6 sengaja TIDAK memperkenalkan overriding sama sekali, subclass
hanya menambah atribut dan method baru; ini membuat keterbatasan warisan
(mis. `overdraftLimit` belum memengaruhi apa pun karena `withdraw()` yang
diwarisi masih pakai aturan generik) terlihat konkret sebagai motivasi
overriding di Pertemuan 7, bukan sekadar diceritakan.

## GUI Pertemuan 13-14: NetBeans Matisse

Pakai NetBeans Matisse (drag-and-drop), bukan Swing manual: atas
permintaan pengguna, untuk menghindari kompleksitas kode layout. Langkah
desainer di jobsheet ditulis sebagai instruksi tekstual bernomor (komponen
palette, nilai properti, layout), BUKAN gambar kode; hanya isi event
handler dan kelas repository yang ditampilkan sebagai gambar kode. Berkas
`.form` ikut disertakan di code-src/checkpoint supaya checkpoint terbuka
benar di NetBeans; mahasiswa tanpa NetBeans mengompilasi langsung `.java`
hasil generate (sudah lengkap tanpa `.form`). Paket dipisah
`model`/`repository`/`ui` sejak Pertemuan 13 (`Bank` dan `Main` tetap di
paket induk `id.ac.polinema` sebagai penghubung antar lapisan, bukan
bagian dari salah satu dari ketiga subpaket itu).

**Jebakan tata letak Matisse**: `FlowLayout` di dalam panel yang dikelola
`GroupLayout` bisa menghasilkan komponen yang terpotong (tombol/field
tidak tampil sama sekali), sebab `FlowLayout` membungkus barisnya dan
`getPreferredSize()`-nya bergantung pada lebar container saat itu, situasi
ayam-telur yang tidak selalu terselesaikan benar oleh proses resize
`GroupLayout`. Ditemukan lewat verifikasi visual sungguhan (lihat bagian
berikut), bukan dari membaca kode. Perbaikan: pakai `GridLayout(baris,
kolom, hgap, vgap)` untuk sub-panel form Matisse (ukuran preferensinya
independen dari lebar container), jauh lebih stabil untuk form berisi
banyak label/field/tombol sekaligus.

## Verifikasi visual GUI tanpa NetBeans/X server

Teknik ditemukan dan terbukti bekerja di lingkungan ini:
`Robot.createScreenCapture()` menghasilkan gambar HITAM POLOS di sandbox
ini (kemungkinan pembatasan keamanan pada screen capture), padahal
`JFrame` tetap bisa dibuat dan ditampilkan (`setVisible(true)` tidak
melempar `HeadlessException`). Teknik yang TERBUKTI bekerja: panggil
`frame.setVisible(true)`, tunggu sebentar, lalu `frame.printAll(g2)` ke
sebuah `BufferedImage` kosong (bukan capture layar sungguhan, melainkan
meminta komponen menggambar dirinya sendiri ke Graphics manapun) lalu
simpan lewat `ImageIO.write`. Dengan begini, hasil GUI (termasuk setelah
simulasi klik tombol lewat `actionListener.actionPerformed(...)` manual,
atau pemilihan baris `JTable.setRowSelectionInterval(...)`) bisa
benar-benar dilihat lewat tool Read, bukan sekadar dipercaya dari membaca
kode.

Satu jebakan: `JOptionPane.showMessageDialog(...)` itu modal dan
MEMBLOKIR thread EDT sampai dialog ditutup; memicu tombol yang menampilkan
dialog lalu menunggu lewat `invokeAndWait` akan hang selamanya dalam
pengujian headless semacam ini. Uji jalur SUKSES (tidak memicu dialog)
secara langsung; untuk jalur galat (dialog muncul), cukup percaya pada
kesamaan strukturnya dengan pola try/catch yang sudah diverifikasi di
jalur konsol pertemuan sebelumnya, atau jalankan di thread terpisah dengan
auto-dismiss.

**Teknik ini butuh sebuah X server (nyata atau virtual/Xvfb) berjalan.**
Sandbox sesi ini TIDAK memilikinya (`No X11 DISPLAY variable was set`,
tanpa `Xvfb`/`xvfb-run` terpasang, dan menginstal paket sistem baru berada
di luar wewenang sesi tanpa izin eksplisit), jadi baik `printAll` maupun
`Robot` sama sekali tidak bisa dipakai di sini, berbeda dari kondisi yang
terdokumentasi di atas. Pengganti sementara untuk screenshot BankMiniFrame
pasca revamp dialog Pertemuan 14 (`p14-add-account.png`,
`p14-deposit-withdraw.png`, `p15-bankmini-after-login.png`,
`p15-bankmini-tugas-login.png`): mockup SVG tangan
(`assets/illustrations/src/bank-mini-*.svg`, dirender ke
`jobsheets/assets/uml/p14-*.png`/`p15-*.png` lewat
`scripts/render-illustrations.sh`, BUKAN foto asli aplikasi). Bila sesi
mendatang punya akses X/Xvfb, prioritaskan mengambil screenshot ASLI
lewat teknik `printAll` di atas dan pensiunkan mockup-mockup ini.

## Kalau menambah materi di luar cakupan RPS

Jangan perluas cakupan kelas Bank Mini tanpa alasan kuat; kalau memang
perlu domain tambahan yang sama sekali berbeda, ikuti pola lama (generik,
lokal untuk pertemuan itu saja, tidak menyambung ke pertemuan lain).

Lihat juga [[slides]] (pola generik-dulu di slide, "Mengapa Ini Penting?")
dan [[jobsheets]] (kotak "Konsep Singkat", larangan path checkpoint).
