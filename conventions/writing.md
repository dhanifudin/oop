# Aturan penulisan

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
  override, exception, getter/setter, repository, signature, dst. Narasi di
  sekitarnya tetap bahasa Indonesia. Khusus **signature**: JANGAN
  diterjemahkan jadi "tanda tangan" (arti harfiahnya beda total, hanya
  berarti tanda tangan tulisan tangan dalam bahasa Indonesia sehari-hari,
  membingungkan di konteks method).
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
