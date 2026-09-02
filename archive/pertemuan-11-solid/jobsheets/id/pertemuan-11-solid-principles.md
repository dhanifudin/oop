# Jobsheet Praktikum: Pertemuan 11
## SOLID Principles

| | |
|---|---|
| **Mata Kuliah** | Pemrograman Berbasis Objek (RTI253007) |
| **Pertemuan** | 11 (Minggu 11) |
| **Durasi** | 1 &times; 2 &times; 50' tatap muka; 1 &times; 2 &times; 50' tugas/praktik mandiri |
| **Sub-CPMK** | SCPMK0704-02502: mengimplementasikan konsep OOP menggunakan Java dalam berbagai paradigma |
| **Kode Awal** | proyek Maven `pertemuan-11-starter` (dibagikan Dosen) |
| **Kode Akhir** | proyek `pertemuan-11-starter` milikmu, sudah direfaktor lengkap |

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, kamu mampu:

1. Mengidentifikasi pelanggaran prinsip SOLID dalam kode yang sudah berjalan (code smell).
2. Merefaktor kelas dengan menerapkan SRP, OCP, LSP, ISP, dan DIP menggunakan interface dan constructor injection.
3. Menjelaskan alasan di balik setiap langkah refactoring, dan membuktikan perilaku programnya tidak berubah.

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, Maven, NetBeans (editor yang kita pakai, sudah mendukung proyek Maven secara native).
- **Kode awal**: minta ke Dosen folder proyek `pertemuan-11-starter`, lalu salin ke komputermu. Di dalamnya ada kelas `OrderProcessor` yang sebenarnya sudah berjalan dengan benar, cuma semua tanggung jawabnya digabung jadi satu kelas saja (istilahnya **code smell**, tepatnya **God Class**). Nah, tugasmu sepanjang jobsheet ini adalah mengubah proyek ini langkah demi langkah, sampai rancangannya mengikuti kelima prinsip SOLID.
- **Verifikasi cepat**: buka folder proyek di NetBeans (File > Open Project, NetBeans langsung mendeteksi `pom.xml`-nya), lalu Run Project. Pastikan programnya jalan tanpa error dulu sebelum kamu mulai mengubah apa pun.

> **Tanpa NetBeans?** Jalankan proyeknya lewat terminal, dari dalam folder proyek:
> ```bash
> mvn -q compile exec:java
> ```
> Untuk menjalankan kelas selain `Main` (dipakai di Langkah 6 untuk `ShippingDemo`):
> ```bash
> mvn -q compile exec:java -Dexec.mainClass=id.ac.polinema.ShippingDemo
> ```
> Checkpoint dan output programnya sama persis, apa pun editor yang kamu pakai.

## C. Langkah Kerja

### Langkah 1: Jalankan kode awal, kenali "bau"-nya

Lihat diagram berikut: kelas `OrderProcessor` di proyek `pertemuan-11-starter` cuma satu kelas, tapi memegang empat tanggung jawab yang beda-beda sekaligus.

![Diagram kelas OrderProcessor sebelum refactoring: satu kelas, empat tanggung jawab](../assets/uml/p11-before.png){width=60%}

Ini isi keempat kelasnya:

![Order.java](../assets/code/pertemuan-11/p11-01-order.png){width=55%}

![Customer.java](../assets/code/pertemuan-11/p11-01-customer.png){width=55%}

![OrderProcessor.java: validasi, hitung diskon, simpan ke berkas, cetak struk, semuanya dalam satu kelas](../assets/code/pertemuan-11/p11-01-orderprocessor.png){width=80%}

![Main.java](../assets/code/pertemuan-11/p11-01-main.png){width=55%}

Jalankan proyeknya (Run Project di NetBeans, atau `mvn -q compile exec:java`).

> ✅ **Checkpoint:** program mencetak struk pesanan dengan `Discount : Rp5000` dan `Total    : Rp95000`, dan ada berkas `orders.txt` baru di root proyek berisi satu baris data.

Sebelum lanjut, coba tulis di komentar atau catatan terpisah: menurutmu, ada berapa tanggung jawab berbeda yang dipegang kelas `OrderProcessor` ini? (Petunjuk: hitung ada berapa method private di dalamnya, dan minimal ada 4.)

### Langkah 2: SRP, keluarkan `DiscountCalculator`

Salah satu ciri pelanggaran **Single Responsibility Principle (SRP)**: kelasnya punya lebih dari satu "alasan untuk berubah". Coba pikir, `OrderProcessor` bakal ikut berubah kalau aturan diskon berubah, tapi juga kalau cara penyimpanan berubah, dan juga kalau format struknya berubah. Tiga alasan berbeda, tapi ditumpuk jadi satu kelas. Yuk kita pisahkan satu per satu, mulai dari perhitungan diskon.

Buat kelas baru `DiscountCalculator`:

![DiscountCalculator.java: perhitungan diskon dikeluarkan dari OrderProcessor](../assets/code/pertemuan-11/p11-02-discountcalculator.png){width=55%}

Ubah `OrderProcessor.java` (baris hijau menandai kode yang berubah/ditambah):

![OrderProcessor.java memakai DiscountCalculator sebagai kolaborator](../assets/code/pertemuan-11/p11-02-orderprocessor.png){width=80%}

`Main.java` nggak perlu diubah sama sekali.

> ✅ **Checkpoint:** kompilasi ulang, jalankan lagi, outputnya harus **identik persis** dengan Langkah 1 (`Discount : Rp5000`, `Total    : Rp95000`). Ingat, refactoring itu artinya struktur kodenya berubah, tapi perilaku programnya nggak boleh ikut berubah.

### Langkah 3: SRP lanjut, keluarkan `OrderRepository` dan `ReceiptPrinter`

Lakukan hal yang sama untuk dua tanggung jawab lainnya: menyimpan data dan mencetak struk.

![OrderRepository.java](../assets/code/pertemuan-11/p11-03-orderrepository.png){width=55%}

![ReceiptPrinter.java](../assets/code/pertemuan-11/p11-03-receiptprinter.png){width=55%}

![OrderProcessor.java sekarang tinggal mengorkestrasi tiga kolaborator](../assets/code/pertemuan-11/p11-03-orderprocessor.png){width=80%}

![Diagram kelas: OrderProcessor mengorkestrasi tiga kolaborator terpisah](../assets/uml/p11-srp.png){width=70%}

> ✅ **Checkpoint:** output tetap identik. Sekarang isi `OrderProcessor` cuma orkestrasi (manggil tiga kolaborator lain) ditambah satu validasi kecil. Coba bandingkan, berapa baris kode `OrderProcessor` sekarang dibanding di Langkah 1?

> ⚠️ **Jika gagal:** kalau muncul `cannot find symbol` pada `discountCalculator`/`orderRepository`/`receiptPrinter`, cek apakah ketiga field itu dideklarasikan persis seperti di atas, SEBELUM method `processOrder`.

### Langkah 4: OCP, ganti if-else dengan `DiscountPolicy`

**Open/Closed Principle (OCP)** bilang: kelas sebaiknya terbuka untuk ekstensi, tapi tertutup untuk modifikasi. Masalahnya di `DiscountCalculator` sekarang: tiap ada tipe customer baru, kamu harus buka lagi method `calculate` dan nambah cabang `else if`. Kita ganti dengan strategi (interface) yang bisa didaftarkan dari luar, jadi nggak perlu buka-buka kode lama lagi.

![DiscountPolicy.java: interface strategi diskon](../assets/code/pertemuan-11/p11-04-discountpolicy.png){width=45%}

![RegularDiscountPolicy.java](../assets/code/pertemuan-11/p11-04-regulardiscountpolicy.png){width=45%}

![VipDiscountPolicy.java](../assets/code/pertemuan-11/p11-04-vipdiscountpolicy.png){width=45%}

Ubah `DiscountCalculator.java` supaya memakai registry `DiscountPolicy`, bukan if-else lagi:

![DiscountCalculator.java: if-else diganti dengan registry DiscountPolicy](../assets/code/pertemuan-11/p11-04-discountcalculator.png){width=70%}

Tambahkan method `getDiscountCalculator()` ke `OrderProcessor.java`:

![OrderProcessor.java dengan getter getDiscountCalculator()](../assets/code/pertemuan-11/p11-04-orderprocessor.png){width=80%}

Ubah `Main.java` supaya mendaftarkan kebijakan diskonnya:

![Main.java mendaftarkan RegularDiscountPolicy dan VipDiscountPolicy](../assets/code/pertemuan-11/p11-04-main.png){width=65%}

![Diagram kelas: DiscountPolicy dan implementasinya](../assets/uml/p11-ocp.png){width=75%}

> ✅ **Checkpoint:** output tetap `Discount : Rp5000` untuk Budi. Nggak ada lagi `if/else` bertingkat berdasarkan tipe customer di dalam `DiscountCalculator`.

### Langkah 5: Buktikan OCP, tambah `WholesaleDiscountPolicy` tanpa mengubah kode lama

Ini pembuktian OCP yang sesungguhnya: tambah tipe customer baru ("WHOLESALE") cukup dengan satu kelas baru dan satu baris pendaftaran, tanpa menyentuh `DiscountCalculator.java` atau `OrderProcessor.java` sama sekali.

![WholesaleDiscountPolicy.java: kelas baru, tanpa mengubah kode lama](../assets/code/pertemuan-11/p11-05-wholesalediscountpolicy.png){width=45%}

![Main.java mendaftarkan WholesaleDiscountPolicy dan memproses order customer wholesale](../assets/code/pertemuan-11/p11-05-main.png){width=70%}

> ✅ **Checkpoint:** program mencetak struk tambahan untuk Sari dengan `Discount : Rp20000` (20% dari Rp100000). Coba cek, berkas mana saja yang kamu ubah? Cuma `WholesaleDiscountPolicy.java` (baru) dan `Main.java`. `DiscountCalculator.java` dan `OrderProcessor.java` sama sekali nggak tersentuh, nah itulah OCP.

### Langkah 6: LSP, reproduksi bug-nya dulu baru perbaiki dengan `Shippable`

**Liskov Substitution Principle (LSP)** bilang: subclass harus bisa menggantikan superclass-nya tanpa bikin kaget kode yang memakainya. Tambahkan method `ship()` ke `Order.java`, lalu buat `DigitalOrder` yang mewakili pesanan barang digital yang memang nggak bisa dikirim lewat kurir:

![Order.java dengan method ship() ditambahkan](../assets/code/pertemuan-11/p11-06-bug-order.png){width=55%}

![DigitalOrder.java: override ship() untuk melempar exception](../assets/code/pertemuan-11/p11-06-bug-digitalorder.png){width=55%}

Buat kelas uji terpisah `ShippingDemo` untuk mereproduksi bug-nya:

![ShippingDemo.java: memanggil ship() pada tiap elemen katalog order](../assets/code/pertemuan-11/p11-06-bug-shippingdemo.png){width=55%}

Jalankan `ShippingDemo` (di NetBeans: klik kanan berkasnya > Run File; tanpa NetBeans: `mvn -q compile exec:java -Dexec.mainClass=id.ac.polinema.ShippingDemo`).

> ✅ **Checkpoint (reproduksi bug):** program CRASH dengan `UnsupportedOperationException` begitu sampai di `DigitalOrder`. Ini memang disengaja: `DigitalOrder` kelihatannya sama saja seperti `Order` biasa (lolos kompilasi tanpa keluhan), tapi begitu dipakai lewat loop yang memanggil `ship()` ke semua elemen, dia bikin kaget. Nah, itulah pelanggaran LSP.

Sekarang perbaiki. Hapus method `ship()` dari `Order.java`, buat interface `Shippable`, dan buat `PhysicalOrder` untuk pesanan yang memang bisa dikirim:

![Order.java: method ship() dihapus, field jadi protected](../assets/code/pertemuan-11/p11-06-fix-order.png){width=55%}

![Shippable.java: interface baru](../assets/code/pertemuan-11/p11-06-fix-shippable.png){width=45%}

![PhysicalOrder.java: subclass yang mengimplementasikan Shippable](../assets/code/pertemuan-11/p11-06-fix-physicalorder.png){width=55%}

![DigitalOrder.java: sekarang nggak implement apa pun selain Order](../assets/code/pertemuan-11/p11-06-fix-digitalorder.png){width=55%}

Ubah `ShippingDemo.java` supaya mengecek kemampuan lewat interface, bukan langsung mengasumsikan semua `Order` bisa dikirim:

![ShippingDemo.java: mengecek instanceof Shippable sebelum memanggil ship()](../assets/code/pertemuan-11/p11-06-fix-shippingdemo.png){width=55%}

![Diagram kelas: Shippable memisahkan PhysicalOrder dari DigitalOrder](../assets/uml/p11-lsp.png){width=60%}

> ✅ **Checkpoint (perbaikan):** kompilasi ulang, jalankan `ShippingDemo` lagi. Sekarang loop-nya kelar tanpa crash: baris pertama bilang lagi dikirim, baris kedua bilang cuma pengiriman digital.

> ⚠️ **Jika gagal:** kalau `Main.java` yang dipakai buat demo `OrderProcessor` di langkah-langkah sebelumnya ikut-ikutan error, tenang, itu wajar dan gampang diperbaiki, `Order` tetap punya constructor publik dan method `describe()`, jadi `Main.java` dari Langkah 5 tetap kompatibel tanpa perlu diubah.

> **Catatan:** ini aturan praktis yang lumayan berguna: kalau kamu nemu subclass yang meng-override method induknya cuma untuk melempar `UnsupportedOperationException`, itu tanda kuat ada pelanggaran LSP, subclass itu sebenarnya nggak benar-benar "sejenis" dengan induknya.

### Langkah 7: ISP, pecah interface gemuk `OrderNotifier`

Diberikan interface berikut yang menggambarkan "cara memberi tahu customer" secara umum, dan satu implementasinya untuk printer struk:

![OrderNotifier.java: interface gemuk dengan tiga method](../assets/code/pertemuan-11/p11-07-bug-ordernotifier.png){width=55%}

![InvoicePrinter.java: dipaksa mengimplementasikan method yang nggak relevan buatnya](../assets/code/pertemuan-11/p11-07-bug-invoiceprinter.png){width=55%}

![Diagram: interface gemuk memaksa method yang cuma melempar exception](../assets/uml/p11-isp.png){width=85%}

Lihat, `InvoicePrinter` dipaksa mengimplementasikan `sendEmailReceipt()` dan `printShippingLabel()`, padahal printer struk memang nggak pernah benar-benar melakukan itu. Ini pelanggaran **Interface Segregation Principle (ISP)**: interface-nya kegemukan, jadi class dipaksa mengimplementasikan method yang sama sekali nggak relevan buatnya.

Perbaiki dengan memecah `OrderNotifier` jadi tiga interface kecil. Hapus `OrderNotifier.java`, buat tiga interface berikut:

![InvoicePrintable.java](../assets/code/pertemuan-11/p11-07-fix-invoiceprintable.png){width=45%}

![EmailReceiptSendable.java](../assets/code/pertemuan-11/p11-07-fix-emailreceiptsendable.png){width=45%}

![ShippingLabelPrintable.java](../assets/code/pertemuan-11/p11-07-fix-shippinglabelprintable.png){width=45%}

Ubah `InvoicePrinter.java`:

![InvoicePrinter.java: sekarang cuma mengimplementasikan InvoicePrintable](../assets/code/pertemuan-11/p11-07-fix-invoiceprinter.png){width=55%}

> ✅ **Checkpoint:** `InvoicePrinter` sekarang cuma mengimplementasikan satu method, dan itu method yang memang benar-benar dipakainya. Nggak ada lagi method yang cuma melempar `UnsupportedOperationException` karena "kepaksa ikut interface".

> **Catatan:** ISP bisa dibilang SRP-nya interface, satu interface sebaiknya mewakili satu kemampuan saja, bukan sekumpulan kemampuan yang kebetulan sering dipakai bareng-bareng.

### Langkah 8: DIP, jadikan `OrderRepository` sebuah interface

**Dependency Inversion Principle (DIP)** bilang: kelas tingkat tinggi (`OrderProcessor`) sebaiknya bergantung pada abstraksi, bukan pada detail implementasi yang konkret. Sekarang `OrderProcessor` bikin `new OrderRepository()` sendiri di dalam field-nya, artinya dia terikat erat sama satu cara penyimpanan data (berkas teks). Kalau suatu saat cara penyimpanannya mau diganti, atau kamu mau menguji `OrderProcessor` tanpa nyentuh berkas sama sekali, kamu bakal kesulitan.

Ubah `OrderRepository.java` jadi interface:

![OrderRepository.java: dari kelas konkret jadi interface](../assets/code/pertemuan-11/p11-08-orderrepository.png){width=60%}

Buat `FileOrderRepository` (implementasi lama, dipindah ke sini) dan `InMemoryOrderRepository` (implementasi baru, berguna buat pengujian cepat tanpa menyentuh berkas sama sekali):

![FileOrderRepository.java](../assets/code/pertemuan-11/p11-08-fileorderrepository.png){width=70%}

![InMemoryOrderRepository.java](../assets/code/pertemuan-11/p11-08-inmemoryorderrepository.png){width=70%}

Ubah `OrderProcessor.java` supaya semua kolaboratornya diterima lewat constructor (**constructor injection**), bukan dibikin sendiri di dalam:

![OrderProcessor.java: constructor injection untuk ketiga kolaborator](../assets/code/pertemuan-11/p11-08-orderprocessor.png){width=80%}

Ubah `Main.java` supaya merakit dependency-nya lalu menyuntikkannya lewat constructor:

![Main.java: merakit dependency dan menyuntikkannya lewat constructor](../assets/code/pertemuan-11/p11-08-main.png){width=70%}

![Diagram kelas: OrderProcessor bergantung pada interface OrderRepository](../assets/uml/p11-dip.png){width=70%}

> ✅ **Checkpoint:** output tetap `Discount : Rp5000` untuk Budi, ditambah satu baris `Repository contents: [...]` yang menampilkan data tersimpan tanpa menyentuh berkas `orders.txt` sama sekali. Coba iseng ganti baris `new InMemoryOrderRepository()` jadi `new FileOrderRepository()`, kompilasi ulang, jalankan lagi: cuma SATU baris yang berubah di seluruh proyek untuk mengganti cara penyimpanan datanya.

> ⚠️ **Jika gagal:** kalau muncul `constructor OrderProcessor in class OrderProcessor cannot be applied to given types`, cek urutan argumen di `new OrderProcessor(...)`, harus persis `discountCalculator, orderRepository, receiptPrinter`.

Diagram berikut merangkum seluruh proyek setelah kelima prinsip diterapkan:

![Diagram kelas lengkap: proyek order-processing setelah refactoring SOLID](../assets/uml/p11-final.png){width=95%}

## D. Tugas dan Deliverable

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- Screenshot output `Main.java` setelah Langkah 8 (termasuk baris "Repository contents").
- Screenshot bukti crash di Langkah 6 sebelum diperbaiki (reproduksi bug LSP), dan output setelah diperbaiki.
- **Tugas mandiri:** kelas `OrderProcessor` di akhir Langkah 8 masih menyimpan satu tanggung jawab tambahan selain orkestrasi, yaitu method `validate(Customer, Order)`. Ini masih pelanggaran SRP kecil yang sengaja dibiarkan. Keluarkan method itu jadi kelas `OrderValidator` tersendiri (dengan method publik, misalnya `isValid(Customer, Order)`), sertakan lewat constructor injection seperti kolaborator lainnya, lalu tulis 3-5 kalimat yang menjelaskan prinsip SOLID mana yang kamu terapkan dan kenapa perubahan ini bikin `OrderProcessor` lebih gampang diuji secara terpisah.

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot/output), termasuk reproduksi bug LSP | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | `OrderValidator` benar dan justifikasi prinsip SOLID tepat | Refactoring ada meski penjelasan belum lengkap |
