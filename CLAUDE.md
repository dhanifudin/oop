# Pemrograman Berbasis Objek (RTI253007) - Konvensi Materi

Mata kuliah: Pemrograman Berbasis Objek (RTI253007), D-IV Teknik Informatika,
Politeknik Negeri Malang. Bahasa Java (JDK 17+). Editor utama: **NetBeans**
(sesuai RPS); semua jobsheet menyertakan alternatif baris perintah untuk
mahasiswa yang memakai editor teks biasa.

## Aturan penulisan

- Jangan pernah menggunakan em-dash (—) di konten yang dihasilkan. Gunakan
  koma, titik dua, tanda kurung, atau dua kalimat terpisah.
- **Tulis dengan bahasa Indonesia instruksional yang formal, bukan
  terjemahan kaku dan bukan pula gaya santai/gaul.** Narasi jobsheet/slide
  memakai bahasa baku, selayaknya dosen menulis handout resmi, tapi tidak
  boleh terbaca seperti terjemahan mekanis kata per kata dari bahasa
  Inggris. Dua kegagalan yang harus dihindari sekaligus: (1) prosa kaku
  hasil terjemahan textbook, dengan frasa transisi yang janggal
  ("Perhatikan bahwa...", "Ini juga jadi contoh sederhana bagaimana...");
  (2) gaya santai/gaul (kontraksi seperti "nggak", "kayak", "gimana",
  "kepikiran"; sapaan basa-basi seperti "Nah,", "Yuk,"; kata pengisi
  seperti "banget", "kok", "sih"). Hindari juga huruf kapital semua untuk
  penekanan di tengah kalimat (mis. "TAPI JUGA", "HANYA"); pakai pilihan
  kata atau penekanan markdown. Sapaan "kamu"/"mahasiswa" tetap dipakai
  dan itu bukan berarti gaya santai. Bacakan ulang tiap kalimat dan
  tanyakan dua hal: apakah ini terdengar seperti tulisan instruksional
  formal profesional (bukan terjemahan mekanis), dan apakah ini terdengar
  seperti handout kuliah (bukan pesan chat)? Berlaku juga untuk versi
  Inggris: bahasa akademik formal, sapaan langsung "you" tetap boleh,
  tapi hindari kontraksi berat ("it's", "you're", "here's", "let's") dan
  selingan yang kesannya mengobrol santai.
- Bahasa Indonesia adalah bahasa utama (`id/`); versi Inggris (`en/`) adalah
  cermin struktural, bukan terjemahan bebas: bagian, urutan, gambar, dan
  contoh kode harus identik, hanya narasi yang diterjemahkan.
- Istilah teknis bahasa Inggris TETAP dalam bahasa Inggris di materi
  berbahasa Indonesia, untuk menghindari makna ganda/salah: dependency
  injection, constructor injection, interface, refactoring, code smell,
  override, exception, getter/setter, repository, dst. Narasi di sekitarnya
  tetap bahasa Indonesia.
- Sebutan dosen memakai **"Dosen"** saja, tidak ada "asisten/Asisten".
- **Tidak ada git di jobsheet.** OOP adalah fokus mata kuliah ini, bukan
  version control. Jangan tambahkan langkah `git init`/branch/commit ke
  jobsheet mana pun.
- Semua kode Java memakai package `id.ac.polinema` (domain institusi
  dibalik: "polinema.ac.id" -> `id.ac.polinema`).
- Kode Java di slide dan jobsheet memakai identifier DAN string literal
  bahasa Inggris (`Rectangle`, `OrderProcessor`, `"REGULAR"`, `"Invalid
  order."`, dst.) di KEDUA bahasa materi, supaya kode di `id/` dan `en/`
  betul-betul identik dan hanya narasi yang diterjemahkan. Ini juga
  berlaku untuk teks yang di-`System.out.println(...)`, termasuk pesan
  "hello world" pertama di Pertemuan 1 dan output method seperti
  `printInfo()` (mis. `"balance"`, bukan `"saldo"`; `"Welcome to Bank
  Mini!"`, bukan versi Indonesia). Ini pernah lolos tanpa disadari: tiga
  pertemuan pertama sempat memakai literal Indonesia di dalam kode
  sebelum ditemukan dan diperbaiki, termasuk checkpoint di jobsheet yang
  mengutip output tsb persis (harus ikut diperbaiki juga). Setelah
  menulis kode baru, grep `System.out.println` di `code-src/` yang baru
  ditambahkan untuk memastikan tidak ada kata bahasa Indonesia yang
  lolos.
- **Studi kasus tunggal untuk satu semester: Bank Mini.** Satu studi kasus
  yang sama, **Bank Mini** (sistem rekening bank sederhana), dipakai
  berkesinambungan dari Pertemuan 2 sampai 16, supaya mahasiswa membangun
  satu aplikasi yang sama dari kelas tunggal sampai aplikasi GUI +
  database, bukan potongan-potongan lepas.
  - **Pemetaan topik resmi per minggu**, sumber "POKOK MATERI PBO 2026"
    (spreadsheet dari Dosen koordinator, menggantikan urutan lama yang
    diturunkan dari PDF RPS di `docs/` untuk minggu 6-11 karena keduanya
    sempat berbeda): 1 Pengantar Konsep Dasar OOP; 2 Class dan Object;
    3 Enkapsulasi; 4 Relasi Class; 5 Kuis 1; 6 Inheritance; 7 Overriding
    dan Overloading; 8 UTS; 9 Abstract Class dan Interface;
    10 Polimorfisme; 11 SOLID Principle; 12 Kuis 2; 13-14 GUI + Database
    (satu proyek NetBeans dibangun berkesinambungan lintas kedua
    pertemuan ini, lihat catatan Matisse di bawah); 15-16 Project (PBL,
    mahasiswa memilih ekstensi sendiri); 17 UAS. `archive/pertemuan-11-solid/`
    (SOLID principles, domain pemrosesan pesanan) TIDAK LAGI dianggap
    "diarsipkan permanen": topik SOLID kini resmi ada di minggu 11, tetapi
    jobsheet harus ditulis ulang Bank-Mini-only (bukan dipakai ulang
    domain lama), slide konsep boleh merujuk materi lama itu sebagai toy
    ilustrasi.
  - **Exception handling dan Collections** (dulu direncanakan sebagai
    materi tersendiri minggu 10-11) tidak punya slot minggu sendiri di
    pemetaan resmi ini. Keduanya dianyam sebagai sub-topik: exception
    handling masuk ke minggu 10 (Polimorfisme, bersama
    `InsufficientBalanceException`), Collections (`ArrayList`/`HashMap`)
    masuk ke minggu 11 (SOLID, sebagai bagian refactor `Bank` yang
    sekaligus mengajarkan prinsip SOLID).
  - **Pola "konsep dulu, baru studi kasus"**: tiap pertemuan memperkenalkan
    konsep baru lewat contoh generik kecil (toy example, satu kelas atau
    beberapa kelas kecil, TIDAK terkait Bank Mini) untuk paparan pertama,
    baru menerapkannya ke Bank Mini. Toy generik ini HANYA muncul di slide
    konsep (ilustrasi utama, ditutup dengan diagram/contoh Bank Mini);
    **jobsheet praktikum sepenuhnya Bank-Mini-only, tidak lagi mengetik
    kode toy generik apa pun ke dalam proyek**. Sebelumnya jobsheet
    Pertemuan 2 sempat mengetik kelas `Rectangle` (dan tugas `Circle`)
    langsung ke proyek `bank-mini`, sampai-sampai jobsheet Pertemuan 3
    harus membuka dengan langkah menghapus `Rectangle.java`; ini sudah
    diperbaiki (jobsheet Pertemuan 2 kini membangun `Account` langsung
    dari Langkah 2, `Rectangle` tetap jadi contoh pengantar slide saja).
    Bila sebuah langkah jobsheet butuh eksposur konsep yang belum tuntas
    dibahas di kelas konsep, pakai kotak "Konsep Singkat" (lihat aturan
    urutan di bawah), bukan menghidupkan kembali toy generik di kode.
    Gambar memori stack/heap Pertemuan 2 (`p02-memory-new.png`,
    `p02-memory-alias.png` di `jobsheets/assets/uml/`) adalah varian
    berlabel `Account`, dirender dari `stack-heap-single-account.svg`/
    `stack-heap-alias-account.svg`, terpisah dari SVG `Rectangle` generik
    (`stack-heap-single.svg`/`stack-heap-alias.svg`) yang tetap dipakai
    slide. Aturan generik-dulu di atas berlaku untuk SELURUH bagian sebelum
    "Menerapkan ke Bank Mini", bukan cuma ilustrasi pertama: pernah lolos
    tanpa disadari pada Pertemuan 3 dan 4, yang narasi pembukanya sudah
    langsung menyebut `Account`/`Bank`/`Customer` padahal ilustrasinya
    sendiri masih generik (mis. `direct-access-bug.svg` menampilkan
    `Thermostat`, bukan `Account`), dan bahkan satu ilustrasi
    (`relation-strengths.svg`) sempat mencampur label generik dengan
    `Bank`/`Account` di diagram yang sama. Periksa toy generik dan
    ilustrasinya benar-benar konsisten (nama kelas yang disebut di
    narasi sama dengan yang tampil di gambar) sebelum menganggap sebuah
    "Bagian" selesai. **Larangan ini juga berlaku untuk referensi recap ke
    penerapan Bank Mini pertemuan-pertemuan sebelumnya**, bukan cuma
    perkenalan pertama sebuah konsep: pernah lolos tanpa disadari di
    slide recap OCP/LSP/ISP pada Pertemuan 11 (menjelaskan prinsip lewat
    `Account`/`canWithdraw()`/`SavingsAccount`/`InterestBearing`, alih-alih
    memakai ulang contoh generik yang sama persis dipakai saat konsep itu
    pertama diajarkan) dan pada slide "Mengapa Ini Penting?" overloading
    Pertemuan 7 (memakai nama method `deposit()`/`depositAmount()` Bank
    Mini). Perbaikannya: recap tetap memakai ulang contoh generik dari
    pertemuan asalnya (mis. `PaymentMethod` untuk OCP, `Animal`/`Dog`/`Cat`
    untuk LSP), forward-reference ke pertemuan lain yang menyebut nama
    kelas Bank Mini juga masuk kategori ini (mis. kalimat penutup slide
    DIP yang menyebut `AccountRepository`/`Bank.java` untuk menunjuk ke
    Pertemuan 15, seharusnya tanpa identifier).
  - **Setiap konsep butuh slide "Mengapa Ini Penting?" yang berdiri
    sendiri**, ditempatkan setelah slide masalah/motivasi awal dan
    sebelum definisi formal (term-box). Slide ini HARUS menjelaskan
    dampak nyata di rekayasa perangkat lunak sungguhan, bukan sekadar
    mengulang mekanisme toy yang baru dijelaskan: apa yang benar-benar
    rusak tanpa konsep ini pada skala besar (bug yang sulit dilacak,
    biaya perawatan, risiko tim), dan mengapa industri menganggapnya
    fondasional. "Penting karena ini konsep dasar OOP" tanpa penjelasan
    konkret TIDAK cukup; gunakan skenario spesifik (aplikasi berskala
    besar, tim yang berbeda-beda, bug nyata yang pernah terjadi).
  - **Cakupan kelas Bank Mini** (jangan tambah di luar ini kecuali RPS
    berubah): `Account`, `Customer`, `SavingsAccount`, `CheckingAccount`,
    `BusinessAccount` (tugas mandiri), `Bank`, `Transaction`,
    `InsufficientBalanceException`, interface `InterestBearing`,
    `Auditable`, dan `AccountRepository`, kelas GUI/JDBC `BankMiniFrame`
    dan `JdbcAccountRepository`. Pertemuan 15 menambah cakupan ini dengan
    mekanisme autentikasi sederhana (permintaan eksplisit pengguna,
    bukan penambahan tak terarah): `User` (model, `username` +
    `passwordHash`), interface `UserRepository`,
    `InMemoryUserRepository` (preview, mengikuti pola
    `AccountRepository` dari Pertemuan 11), `JdbcUserRepository`, kelas
    utilitas `PasswordHasher` (SHA-256 lewat `java.security.MessageDigest`,
    TANPA dependency eksternal; jobsheet/slide WAJIB memberi warn-box
    eksplisit bahwa sistem produksi memakai hashing bergaram dan
    berulang seperti bcrypt/Argon2/PBKDF2, SHA-256 polos di sini murni
    penyederhanaan pengajaran, bukan contoh siap produksi), dan
    `LoginFrame` (GUI, memakai `JPasswordField` bukan `JTextField` biasa
    untuk kolom sandi). Alasan penempatan di Pertemuan 15, bukan 13-14:
    autentikasi sungguhan butuh kredensial yang tersimpan dan diperiksa
    dari data persisten, bukan `if` yang di-hardcode di kode Java,
    sehingga baru masuk akal setelah database (JDBC) diperkenalkan;
    Pertemuan 14 menutup dengan catatan singkat yang secara eksplisit
    menyebut celah ini dan menunjuk ke Pertemuan 15
    (`jobsheets/id/pertemuan-14-gui-netbeans-matisse.md`, bagian D).
    `PasswordHasher` diletakkan di paket induk `id.ac.polinema` (bukan
    subpaket baru), mengikuti pola `Bank`/`Main`: kelas yang tidak cocok
    masuk `model`/`repository`/`ui` tetap di paket induk, bukan dalih
    untuk menambah subpaket keempat. Paket `id.ac.polinema`; subpaket
    `model`/`repository`/`ui` baru dipakai mulai Pertemuan 13
    (fase Maven/GUI). Evolusi kelas per pertemuan praktikum (state SETELAH
    pertemuan tsb, mengikuti pemetaan topik resmi di atas): Pertemuan 2
    `Account` v1 (atribut publik, tanpa validasi); 3 enkapsulasi +
    konstruktor + validasi; 4 `Customer` + `Bank` dengan array
    `Account[]`; 6 `SavingsAccount`/`CheckingAccount` murni inheritance
    (atribut dan method BARU saja, belum ada override, `withdraw()` masih
    satu aturan generik warisan, sengaja dibiarkan belum optimal untuk
    memotivasi pertemuan berikutnya); 7 `Account.canWithdraw()` jadi hook
    `protected` yang di-override tiap subclass, plus overload
    `deposit(double, String)`; 9 `Account` jadi abstract class (method
    abstrak `monthlyFee()`) + interface `InterestBearing`; 10 polymorphism
    di `Bank` (iterasi polimorfik, `instanceof`) + exception handling
    (`InsufficientBalanceException`); 11 SOLID Principle diajarkan LEWAT
    refactor `Bank` dari `Account[]` ke `Map<String, Account>`
    (`LinkedHashMap`, dipilih di atas `HashMap` polos supaya urutan
    iterasi tetap deterministik) + kelas `Transaction` disimpan per
    `Account` lewat `List<Transaction>` (`ArrayList`) (SRP lewat
    `Transaction` terpisah, OCP lewat hook `canWithdraw()` yang sudah ada,
    LSP lewat kontrak subclass yang konsisten, ISP lewat interface kecil
    `InterestBearing`/`Auditable`, DIP lewat pengenalan
    `AccountRepository` + `InMemoryAccountRepository` sebagai preview);
    13-14 SATU
    proyek Maven + GUI dibangun berkesinambungan (lihat catatan Matisse di
    bawah); 15 Langkah 1: `JdbcAccountRepository` + SQLite menggantikan
    `InMemoryAccountRepository` (menyambung interface Pertemuan 11, tanpa
    mengubah `Bank.java`), plus `Bank.saveAccount()` (baru) dipanggil
    ulang setelah `deposit()`/`withdraw()`/`processMonthEnd()` supaya
    perubahan saldo ikut tersimpan (payoff konkret: penyimpanan in-memory
    otomatis "tersimpan" lewat referensi objek yang sama, penyimpanan
    database TIDAK, harus disimpan ulang secara eksplisit setiap
    perubahan, perbedaan nyata yang jadi materi "Mengapa Ini Penting?").
    Langkah 2: `User`/`UserRepository`/`InMemoryUserRepository` (preview
    singkat) lalu `JdbcUserRepository`/`PasswordHasher`/`LoginFrame`,
    `Main.java` menjalankan `LoginFrame` lebih dulu, bukan `BankMiniFrame`
    langsung. Tugas: `BankMiniFrame` menampilkan "Logged in as:
    &lt;username&gt;" di judul jendela (constructor menerima parameter
    username), plus satu pengguna tambahan; 16 PBL (mahasiswa memilih
    ekstensi sendiri). Rencana lengkap ada di
    `/home/dhs/.claude/plans/you-re-top-oop-lecturer-elegant-wind.md`
    (riwayat plan mode, bukan bagian repo ini, tapi jadi rujukan desain).
  - **Konvensi teaching Pertemuan 6 vs 7**: Pertemuan 6 sengaja TIDAK
    memperkenalkan overriding sama sekali, subclass hanya menambah
    atribut dan method baru; ini membuat keterbatasan warisan (mis.
    `overdraftLimit` belum memengaruhi apa pun karena `withdraw()` yang
    diwarisi masih pakai aturan generik) terlihat konkret sebagai
    motivasi overriding di Pertemuan 7, bukan sekadar diceritakan.
  - **GUI Pertemuan 13-14 pakai NetBeans Matisse (drag-and-drop), bukan
    Swing manual**: atas permintaan pengguna, untuk menghindari kompleksitas
    kode layout. Langkah desainer di jobsheet ditulis sebagai instruksi
    tekstual bernomor (komponen palette, nilai properti, layout), BUKAN
    gambar kode; hanya isi event handler dan kelas repository yang
    ditampilkan sebagai gambar kode. Berkas `.form` ikut disertakan di
    code-src/checkpoint supaya checkpoint terbuka benar di NetBeans;
    mahasiswa tanpa NetBeans mengompilasi langsung `.java` hasil generate
    (sudah lengkap tanpa `.form`). Paket dipisah `model`/`repository`/`ui`
    sejak Pertemuan 13 (`Bank` dan `Main` tetap di paket induk
    `id.ac.polinema` sebagai penghubung antar lapisan, bukan bagian dari
    salah satu dari ketiga subpaket itu).
  - **Verifikasi visual GUI TANPA NetBeans/X server sungguhan, ditemukan
    dan terbukti bekerja di lingkungan ini**: `Robot.createScreenCapture()`
    menghasilkan gambar HITAM POLOS di sandbox ini (kemungkinan
    pembatasan keamanan pada screen capture), padahal `JFrame` tetap bisa
    dibuat dan ditampilkan (`setVisible(true)` tidak melempar
    `HeadlessException`). Teknik yang TERBUKTI bekerja: panggil
    `frame.setVisible(true)`, tunggu sebentar, lalu `frame.printAll(g2)`
    ke sebuah `BufferedImage` kosong (bukan capture layar sungguhan,
    melainkan meminta komponen menggambar dirinya sendiri ke Graphics
    manapun) lalu simpan lewat `ImageIO.write`. Dengan begini, hasil GUI
    (termasuk setelah simulasi klik tombol lewat
    `actionListener.actionPerformed(...)` manual, atau pemilihan baris
    `JTable.setRowSelectionInterval(...)`) bisa benar-benar dilihat lewat
    tool Read, bukan sekadar dipercaya dari membaca kode. Satu jebakan:
    `JOptionPane.showMessageDialog(...)` itu modal dan MEMBLOKIR thread
    EDT sampai dialog ditutup; memicu tombol yang menampilkan dialog lalu
    menunggu lewat `invokeAndWait` akan hang selamanya dalam pengujian
    headless semacam ini. Uji jalur SUKSES (tidak memicu dialog) secara
    langsung; untuk jalur galat (dialog muncul), cukup percaya pada
    kesamaan strukturnya dengan pola try/catch yang sudah diverifikasi di
    jalur konsol pertemuan sebelumnya, atau jalankan di thread terpisah
    dengan auto-dismiss (lihat pola di riwayat sesi ini untuk contoh).
  - **Jebakan tata letak Matisse: `FlowLayout` di dalam panel yang
    dikelola `GroupLayout` bisa menghasilkan komponen yang terpotong
    (tombol/field tidak tampil sama sekali)**, sebab `FlowLayout`
    membungkus barisnya dan `getPreferredSize()`-nya bergantung pada
    lebar container saat itu, situasi ayam-telur yang tidak selalu
    terselesaikan benar oleh proses resize `GroupLayout`. Ditemukan lewat
    verifikasi visual sungguhan (lihat poin di atas), bukan dari membaca
    kode. Perbaikan: pakai `GridLayout(baris, kolom, hgap, vgap)` untuk
    sub-panel form Matisse (ukuran preferensinya independen dari lebar
    container), jauh lebih stabil untuk form berisi banyak
    label/field/tombol sekaligus.
  - **Aturan urutan (concept vs praktikum tidak selalu selaras minggu)**:
    jobsheet boleh mengasumsikan semua yang SUDAH dibahas kelas konsep
    sampai minggu kalender yang sama. Di titik-titik praktikum mendahului
    konsep, langkah jobsheet yang pertama kali butuh ide tsb membuka
    dengan kotak kutipan pendek "Konsep Singkat" (maksimal setengah
    halaman, narasi plus opsional satu gambar, tanpa kode baru) yang
    mandiri menjelaskan secukupnya. **Jangan taruh gambar DI DALAM
    blockquote "Konsep Singkat"**: `jobsheets/assets/header.tex`
    mendefinisikan ulang `quote` memakai environment `leftbar` (paket
    `framed`), dan LaTeX menolak float `figure` (yang otomatis dibuat
    pandoc dari gambar Markdown ber-alt-text) di dalam environment
    semacam ini dengan galat "Not in outer par mode". Taruh gambar sebagai
    paragraf biasa segera SETELAH blockquote, bukan di dalamnya. Slide
    konsep sebaliknya HANYA boleh merujuk kelas Bank Mini yang sudah
    dibangun praktikum sampai minggu itu.
  - **Hindari token kode panjang digabung slash di prosa/tabel sempit**
    (mis. `` `SavingsAccount`/`CheckingAccount` `` atau
    `` `dailyWithdrawalLimit` `` sendirian di sel tabel Kriteria
    Penilaian yang sempit): pernah menyebabkan teks benar-benar
    menempel/terklip di render PDF akhir (ditemukan lewat pemeriksaan
    per-halaman, bukan dari membaca markdown-nya). Tulis ulang jadi
    prosa dengan spasi/koma alami, atau di tabel sempit ganti istilah
    kode dengan frasa deskriptif singkat.
  - **Kelanjutan proyek tanpa git**: mahasiswa yang tertinggal melanjutkan
    dari checkpoint `code/bank-mini/pertemuan-<N-1>/` (snapshot lengkap
    yang bisa langsung dijalankan/dibuka NetBeans), bukan dari version
    control. Checkpoint dihasilkan otomatis oleh
    `scripts/build-checkpoints.py` dari `jobsheets/assets/code-src/`
    (lihat bagian Struktur direktori), jangan diedit manual.
  - Kalau menambah materi pertemuan baru di luar cakupan RPS ini: jangan
    perluas cakupan kelas Bank Mini tanpa alasan kuat; kalau memang perlu
    domain tambahan yang sama sekali berbeda, ikuti pola lama (generik,
    lokal untuk pertemuan itu saja, tidak menyambung ke pertemuan lain).
  - **Ukuran tampilan gambar di slide itu terpisah dari ukuran font di
    sumbernya, dan keduanya harus benar sebelum gambar terlihat besar di
    proyektor.** Rendered pixel size sebuah teks SVG = `font-size (unit
    SVG) x (lebar tampilan piksel / lebar viewBox)`. Menaikkan font-size
    SEKALIGUS ukuran seluruh elemen lain secara proporsional (termasuk
    viewBox) TIDAK mengubah apa pun secara visual, karena rasio
    font-ke-viewBox tetap sama; hanya menaikkan rasio itu (font lebih
    besar RELATIF terhadap viewBox yang sama, lewat kotak yang diperlebar
    atau margin yang dipangkas) yang benar-benar memperbesar tampilan
    akhir. Gambar dua-kolom (`.cols`) dibatasi lebar tampilan hanya
    ~554-560px oleh layout flex-nya sendiri, jauh lebih kecil dari
    anggaran tinggi (~420-460px) yang tersedia; mengonversi slide
    semacam itu jadi tumpukan (gambar penuh-lebar di atas, teks di
    bawah) jauh lebih murah dan rendah risiko daripada mendesain ulang
    SVG-nya, dan terbukti bekerja baik di seluruh dek. `img { max-height
    }` global dipakai 420px (naik dari 320px), `.cols img` (bila masih
    dipakai) 460px.
  - **PlantUML diam-diam MEMOTONG (bukan menskalakan atau memberi galat)
    diagram yang lebih lebar dari batas amannya (default 4096px).**
    Setelah `defaultFontSize` dinaikkan, beberapa diagram tiga-kelas
    melebihi batas ini dan terpotong tanpa peringatan (constructor
    signature ter-crop di tengah). `scripts/render-uml.sh` menaikkan
    batas ini lewat `JDK_JAVA_OPTIONS="-DPLANTUML_LIMIT_SIZE=8192"`;
    jangan hapus baris ini.
  - **Gambar penuh-lebar (bukan dua-kolom) yang diikuti paragraf/kotak
    teks panjang bisa meluber ke luar slide** setelah `max-height` global
    dinaikkan, karena gambar kini benar-benar lebih tinggi dari
    sebelumnya. Ini baru terlihat lewat rendering per-halaman yang
    sesungguhnya, tidak lewat membaca markdown-nya. Perbaikannya: beri
    directive `![h:NNN ...]` eksplisit pada gambar itu (bukan
    membiarkannya mengandalkan `max-height` global) untuk menyisakan
    ruang bagi teks di bawahnya.

## Struktur direktori

- `slides/id/`, `slides/en/`: slide Marp (`pertemuan-NN-<slug>.md`).
  `slides/assets/uml/`: diagram UML (PNG, hasil render PlantUML, dipakai
  bersama oleh `id/` dan `en/`). `slides/build.sh` merender ke PDF via
  `marp --pdf` (lihat `scripts/render-all.sh` untuk alur lengkap).
- `jobsheets/id/`, `jobsheets/en/`: jobsheet praktikum (`pertemuan-NN-<slug>.md`).
  `TEMPLATE.md` adalah kerangka baku. `jobsheets/assets/code/`: gambar
  cuplikan kode (PNG hasil render `scripts/render-code.py`, dipakai bersama
  `id/`/`en/`). `jobsheets/assets/code-src/`: sumber Java asli per langkah
  (satu sumber kebenaran; jangan edit gambar tanpa mengedit sumber ini dan
  merender ulang). `jobsheets/assets/uml/`: salinan diagram UML yang sama
  dengan `slides/assets/uml/`. `build.sh` merender ke PDF A4 (butuh `pandoc`
  + `lualatex`).
- `code/bank-mini/pertemuan-NN/`: checkpoint proyek Bank Mini, snapshot
  lengkap yang bisa dijalankan/dibuka NetBeans, DIHASILKAN OTOMATIS oleh
  `scripts/build-checkpoints.py` dari `jobsheets/assets/code-src/` (jangan
  diedit manual, edit sumbernya lalu jalankan `make checkpoints`).
  Pertemuan 2-11: struktur `src/id/ac/polinema/*.java` polos (javac/java).
  Pertemuan 13-16: proyek Maven (`pom.xml` dari `scripts/pom-template.xml`,
  `src/main/java/id/ac/polinema/{model,repository,ui}/`); Pertemuan 15
  menambah dependency `org.xerial:sqlite-jdbc`. `code/bank-mini-zips/
  pertemuan-NN.zip`: checkpoint yang sama, dikemas jadi satu berkas zip
  (folder teratas `bank-mini-pertemuan-NN/`) oleh
  `scripts/gen-checkpoint-zips.py`, ditautkan dari situs GitHub Pages
  (`scripts/gen-pages-index.py` + `.github/workflows/pages.yml`, lihat
  `https://dhanifudin.com/oop/`) supaya mahasiswa yang tertinggal bisa
  mengunduh langsung, tanpa git dan tanpa menunggu Dosen membagikan
  berkas secara manual.
  - **Gotcha nyata, ditemukan saat mengemas checkpoint 13-14 jadi zip
    (jauh lebih terlihat begitu file dikemas untuk diunduh langsung
    dibanding sekadar duduk di direktori lokal)**: reorganisasi paket
    `model`/`repository`/`ui` di Pertemuan 13 (lihat "Cakupan kelas Bank
    Mini" di atas) tidak pernah menghapus salinan LAMA di paket induk.
    `build-checkpoints.py` menumpuk file lintas pertemuan dan hanya
    membuang satu file kalau langkah yang menambahkannya menyertakan
    manifest `.delete` (satu path relatif per baris, relatif terhadap
    `id/ac/polinema/`; lihat docstring skrip itu) — langkah repackaging
    Pertemuan 13 tidak pernah membuat `.delete`-nya, sehingga checkpoint
    13 dan 14 diam-diam berisi SEMBILAN salinan basi (`Account.java`,
    `AccountRepository.java`, `CheckingAccount.java`, `Customer.java`,
    `InMemoryAccountRepository.java`, `InsufficientBalanceException.java`,
    `InterestBearing.java`, `SavingsAccount.java`, `Transaction.java`) di
    paket induk berdampingan dengan salinan benar di `model`/`repository`.
    (`Bank.java`/`Main.java` BUKAN basi, keduanya memang seharusnya tetap
    di paket induk.) Tetap berhasil dikompilasi (nama kelas berbeda paket,
    tidak bentrok), jadi baru ketahuan setelah benar-benar mendaftar isi
    checkpoint satu per satu, bukan dari galat compiler. Sudah diperbaiki
    lewat `jobsheets/assets/code-src/pertemuan-13/langkah-01/.delete`;
    kalau pertemuan mana pun ke depan memindahkan kelas antar paket,
    selalu tambahkan manifest `.delete` yang sesuai di langkah yang sama.
- `archive/pertemuan-11-solid/`: materi Pertemuan 11 versi lama (SOLID
  principles, domain pemrosesan pesanan), diarsipkan saat urutan lama
  (diturunkan dari PDF RPS) menempatkan Interface di minggu 11. Pemetaan
  topik resmi terbaru ("POKOK MATERI PBO 2026") mengembalikan SOLID ke
  minggu 11, tetapi jobsheet AKTIF untuk minggu itu tetap harus
  Bank-Mini-only (bukan memakai ulang domain pemrosesan pesanan lama);
  materi arsip ini hanya rujukan konsep/toy untuk slide, tidak ditautkan
  langsung dari jobsheet aktif mana pun.
- `assets/uml/src/*.puml`: sumber PlantUML (satu sumber kebenaran untuk
  semua diagram kelas; `_common.iuml` berisi skinparam bersama).
- `assets/screenshots/pertemuan-NN/*.png`: screenshot GUI asli (bukan hasil
  render dari sumber lain, lihat catatan verifikasi GUI di bawah),
  disalin ke `slides/assets/screenshots/` dan `jobsheets/assets/screenshots/`
  oleh `scripts/render-screenshots.sh`. Dipakai mulai Pertemuan 13 (fase GUI).
- `scripts/`: `setup.sh` (venv + pygments/pillow), `render-code.py` +
  `gen-manifest.py` (cuplikan kode -> gambar, dengan highlight baris
  baru/berubah, satu fungsi `rows_pNN()` per pertemuan), `render-uml.sh`
  (PlantUML -> PNG, disalin ke `slides/assets/uml/` dan
  `jobsheets/assets/uml/`), `render-screenshots.sh` (salin screenshot GUI
  ke `slides/`/`jobsheets/`), `build-checkpoints.py` (susun snapshot
  `code/bank-mini/pertemuan-NN/` dari `jobsheets/assets/code-src/`,
  termasuk berkas `.form` sejak Pertemuan 13),
  `gen-checkpoint-zips.py` (kemas tiap checkpoint jadi
  `code/bank-mini-zips/pertemuan-NN.zip`, hanya modul standar Python,
  tidak perlu venv; secara sengaja mengecualikan `target/` dan metadata
  IDE seperti `.classpath`/`.project`/`.settings` andai direktori
  checkpoint pernah ikut ter-compile manual saat pengujian lokal),
  `pom-template.xml` (kerangka `pom.xml` untuk checkpoint Maven),
  `gen-pages-index.py` (susun `docs-site/` berisi seluruh PDF slide/
  jobsheet dan zip checkpoint plus `index.html` yang menautkannya, untuk
  diunggah `.github/workflows/pages.yml` ke GitHub Pages),
  `render-all.sh` (jalankan semuanya lalu build seluruh PDF; TIDAK
  termasuk `gen-checkpoint-zips.py`/`gen-pages-index.py`, keduanya cuma
  dipanggil dari alur CI Pages, lihat `make zips` untuk menjalankannya
  manual).
- `docs/`: dokumen RPS resmi (PDF), tidak diubah oleh materi ini.
- Repo ini di-hosting di `github.com/dhanifudin/oop` (publik), dengan
  GitHub Actions (`.github/workflows/pages.yml`, runner `ubuntu-latest`)
  yang menjalankan seluruh pipeline lalu men-deploy ke GitHub Pages
  (`https://dhanifudin.com/oop/`) setiap push ke `main`. Semua yang
  di-gitignore di atas (checkpoint, gambar hasil render, PDF, zip) DIBUAT
  ULANG oleh CI itu sendiri, bukan disalin dari commit manapun.

## Gaya slide (Marp)

Ikuti pola dari `advance-web-programming/slides/id/bab01-arsitektur-web-modern.md`:
frontmatter `marp: true`, `size: 16:9`, `paginate: true`, palet biru, kelas
`lead` (judul), `divider` (pembatas bagian), dan util `.cols`, `.term-box`,
`.tip-box`, `.warn-box`, `.flow`, `.stack`, `.footnote`. Struktur: lead judul
-> "Yang Akan Kamu Pelajari" -> beberapa `Bagian N` (divider + slide isi) ->
dua slide penutup TERPISAH: lead "Referensi" (hanya sitasi + pointer
jobsheet) lalu slide biasa "Diskusi" (satu pertanyaan diskusi yang konkret
dan terjawab, dengan skenario/tugas spesifik, bukan pertanyaan terbuka
tanpa jangkar). Jangan gabungkan keduanya jadi satu slide "Referensi dan
Diskusi": pernah menyembunyikan tiga hal berbeda (sitasi, pointer jobsheet,
pertanyaan diskusi) dalam satu blok tak terbedakan. **Slide fokus pada
konsep**: cuplikan
kode di slide singkat (maksimal ~8 baris, teks biasa dengan highlight native
Marp), bukan gambar; listing lengkap ada di jobsheet. Diagram struktur/UML
di slide memakai gambar PlantUML dari `slides/assets/uml/`.

**Bahaya tersembunyi di kelas `divider`**: blok CSS `section.divider h1`
mengatur warna putih, tapi aturan global `h2 { color: #1d4ed8; }` (biru)
tetap berlaku untuk subjudul `## ...` di slide divider kecuali ditimpa
eksplisit dengan `section.divider h2 { color: #bfdbfe; }`. Tanpa override
ini, subjudul jadi TIDAK TERLIHAT karena warnanya sama persis dengan latar
belakang biru slide divider (bug nyata yang lolos dari verifikasi
render-per-halaman sebelumnya, karena teks yang hilang akibat warna sama
dengan latar tidak terlihat seperti kesalahan struktural saat sekilas
dipindai). Pastikan blok CSS ini selalu ikut disalin ke setiap deck baru.

**Bahaya tersembunyi sintaks ukuran gambar, Marp vs jobsheet berbeda**:
Marp mengatur ukuran gambar lewat prefix di dalam teks alt,
`![h:300 keterangan](...)`, SEDANGKAN jobsheet (Pandoc) memakainya lewat
suffix setelah kurung, `![keterangan](...){width=70%}`. Menulis sintaks
jobsheet di file slide (mis. `![keterangan](...){h:300}`) tidak
menghasilkan galat apa pun, `{h:300}` hanya tercetak sebagai teks harfiah
tepat di bawah gambar (bug nyata yang lolos hingga verifikasi
page-by-page). Selalu double-check sintaks sesuai jenis file yang sedang
diedit, terutama saat menyalin satu gambar UML yang dipakai bersama
slide dan jobsheet.

## Gaya jobsheet

Ikuti `jobsheets/TEMPLATE.md`: tabel metadata (tanpa baris git), bagian
A Capaian Praktikum, B Persiapan dan Prasyarat (NetBeans-first, dengan kotak
kutipan "Tanpa NetBeans?" berisi alternatif baris perintah), C Langkah Kerja
(narasi singkat -> gambar kode -> `> ✅ Checkpoint:` -> `> ⚠️ Jika gagal:`
bila relevan), D Tugas dan Deliverable, E Kriteria Penilaian (tabel bobot
40/35/25: Langkah kerja / Checkpoint / Tugas mandiri, tanpa baris commit).
Sapaan "kamu" dengan nada formal instruksional (lihat aturan penulisan di
atas). **Cuplikan kode di jobsheet adalah gambar** (bukan blok kode teks),
dirender dari `jobsheets/assets/code-src/` lewat `scripts/render-code.py`;
baris kode yang baru/berubah pada suatu langkah disorot hijau otomatis
(dihitung dari diff terhadap langkah sebelumnya, termasuk lintas pertemuan,
di `scripts/gen-manifest.py`). Diagram UML memakai gambar PlantUML, bukan
ASCII art. Pertemuan 2-11 memakai struktur `src/id/ac/polinema/*.java`
polos (javac/java). Pertemuan 13 dan seterusnya beralih ke proyek Maven
(checkpoint `code/bank-mini/pertemuan-NN/`), dijalankan dengan
`mvn -q compile exec:java` atau Run Project di NetBeans.
