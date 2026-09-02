# Jobsheet Praktikum: Pertemuan 2
## Kelas dan Objek

| | |
|---|---|
| **Mata Kuliah** | Praktikum Pemrograman Berbasis Objek (RTI253008) |
| **Pertemuan** | 2 (Minggu 2) |
| **Durasi** | 1 &times; 4 &times; 50' praktikum; 1 &times; 1 &times; 50' tugas/laporan mandiri |
| **Kode Awal** | `code/bank-mini/pertemuan-01/` (checkpoint Pertemuan 1) |
| **Kode Akhir** | proyek `bank-mini` setelah Langkah 7, disalin sebagai checkpoint `code/bank-mini/pertemuan-02/` |

## A. Capaian Praktikum

Setelah menyelesaikan jobsheet ini, mahasiswa mampu:

1. Mendefinisikan kelas Java dengan atribut, konstruktor, dan method.
2. Membuat dan memanipulasi objek menggunakan `new`, serta memahami perilaku referensi.
3. Menerjemahkan diagram kelas UML sederhana menjadi kode Java.

## B. Persiapan dan Prasyarat

- **Alat**: JDK 17 atau lebih baru, NetBeans (editor yang digunakan sepanjang praktikum ini).
- **Proyek**: pertemuan ini melanjutkan proyek `bank-mini` yang dibuat pada Pertemuan 1. Seluruh kelas tetap ditempatkan dalam package `id.ac.polinema` (konvensi umum penamaan package di Java: nama domain institusi dibalik urutannya, "polinema.ac.id" menjadi `id.ac.polinema`).
- **Verifikasi cepat** sebelum memulai:
  ```bash
  java -version
  javac -version
  ```
  Apabila keduanya menampilkan nomor versi tanpa galat, proses dapat dilanjutkan.

> **Tanpa NetBeans?** Jobsheet ini tetap dapat diikuti menggunakan editor teks biasa, lanjutkan folder `bank-mini/` dari Pertemuan 1:
> ```bash
> cd bank-mini
> javac -d out src/id/ac/polinema/*.java
> java -cp out id.ac.polinema.Main
> ```
> Checkpoint dan output yang dihasilkan tetap sama persis, apa pun editor yang digunakan.

## C. Langkah Kerja

### Langkah 1: Melanjutkan Proyek `bank-mini`

Proyek `bank-mini` yang dibuat pada Pertemuan 1 dilanjutkan pada pertemuan ini. Buka proyek tersebut di NetBeans (atau folder yang sama bila mengikuti alternatif "Tanpa NetBeans?" di atas). Pertemuan ini dimulai dengan contoh sederhana `Rectangle` untuk memperkenalkan konsep kelas, objek, dan konstruktor terlebih dahulu, sebelum konsep yang sama diterapkan kembali pada kelas `Account`, cikal bakal aplikasi Bank Mini.

> ✅ **Checkpoint:** panel Projects masih menampilkan proyek `bank-mini` dengan package `id.ac.polinema` berisi `Main.java` dari Pertemuan 1.

### Langkah 2: Kelas `Rectangle` Minimal dan Objek Pertama

**Kelas** merupakan cetakan yang menjelaskan atribut dan perilaku yang akan dimiliki oleh setiap **objek** yang dibuat darinya. Kelas `Rectangle` berikut baru memiliki atribut (state), belum memiliki method.

Tambahkan kelas baru `Rectangle` ke package `id.ac.polinema` (klik kanan package > New > Java Class):

![Rectangle.java: kelas dengan dua atribut](../assets/code/pertemuan-02/p02-02-rectangle.png){width=55%}

Ganti isi `Main.java` untuk membuat satu objek `Rectangle` dan mengisi atributnya satu per satu:

![Main.java: membuat objek Rectangle dan mengisi atribut secara manual](../assets/code/pertemuan-02/p02-02-main.png){width=75%}

Jalankan proyek (klik kanan proyek > Run, atau tekan tombol F6). Apabila menggunakan terminal: `javac -d out src/id/ac/polinema/*.java && java -cp out id.ac.polinema.Main`.

> ✅ **Checkpoint:** program menampilkan `Rectangle 6x4`.

> ⚠️ **Jika gagal:** apabila muncul `error: cannot find symbol`, periksa apakah nama berkas sama persis dengan nama kelasnya (`Rectangle.java` untuk `class Rectangle`). Java bersifat case-sensitive, sehingga huruf besar dan kecil harus sesuai.

### Langkah 3: Menambahkan Method (Perilaku)

Atribut hanya menyimpan data dan tidak memiliki perilaku. **Method** memberikan perilaku pada kelas, yaitu sesuatu yang dapat dilakukan objek dengan datanya sendiri. Tambahkan dua method ke `Rectangle` (baris hijau menandai kode yang baru ditambahkan):

![Rectangle.java dengan method area() dan perimeter() ditambahkan](../assets/code/pertemuan-02/p02-03-rectangle.png){width=55%}

Sederhanakan `Main.java` agar menggunakan kedua method tersebut:

![Main.java memakai area() dan perimeter()](../assets/code/pertemuan-02/p02-03-main.png){width=75%}

Jalankan ulang.

> ✅ **Checkpoint:** output menjadi dua baris, `Area: 24` kemudian `Perimeter: 20`.

> ⚠️ **Jika gagal:** apabila muncul galat `non-static method area() cannot be referenced from a static context`, method `area()` dipanggil tanpa melalui objek (misalnya `Rectangle.area()`). Method non-static wajib dipanggil melalui objeknya, yaitu `r.area()`.

### Langkah 4: Konstruktor dan `this`

Terdapat jeda waktu antara pembuatan objek dengan `new Rectangle()` dan selesainya pengisian atribut (lihat Langkah 2-3). Selama jeda tersebut, objek masih berada dalam kondisi "setengah jadi" dengan atribut yang masih kosong (bernilai `0` untuk tipe `int`). **Konstruktor** menutup celah ini dengan mewajibkan data lengkap pada saat objek dibuat.

![Rectangle.java dengan konstruktor ditambahkan](../assets/code/pertemuan-02/p02-04-rectangle.png){width=55%}

`this.width` merujuk pada atribut milik objek, sedangkan `width` di sisi kanan merupakan parameter konstruktor. Karena kedua nama tersebut sengaja dibuat sama, kata kunci `this` diperlukan agar Java dapat membedakan keduanya.

Sederhanakan `Main.java`:

![Main.java memakai konstruktor Rectangle](../assets/code/pertemuan-02/p02-04-main.png){width=75%}

> ✅ **Checkpoint:** output sama persis dengan Langkah 3, dengan kode `Main.java` yang jauh lebih ringkas.

Bagian berikut sering menimbulkan kebingungan: pada saat pernyataan `new Rectangle(6, 4)` dijalankan, terdapat dua hal yang terjadi di memori. Variabel `r` hanyalah tempat kecil di **stack** yang berisi alamat. Objek `Rectangle` yang sesungguhnya, lengkap dengan `width` dan `height`-nya, berada secara terpisah di **heap**. Ilustrasinya adalah sebagai berikut:

![Variabel di stack menunjuk ke objek Rectangle di heap](../assets/uml/p02-memory-new.png){width=45%}

> ⚠️ **Jika gagal:** galat `constructor Rectangle in class Rectangle cannot be applied to given types` umumnya menunjukkan bahwa jumlah atau urutan argumen pada `new Rectangle(...)` tidak sesuai dengan parameter konstruktornya. Periksa kembali jumlah dan urutan argumen tersebut.

### Langkah 5: Referensi, Aliasing, dan `null`

Variabel objek di Java bukan merupakan objeknya sendiri, melainkan **referensi** yang menunjuk ke objek di memori. Oleh karena itu, dua variabel dapat menunjuk ke objek yang persis sama. Tambahkan kode berikut ke akhir `main` (baris hijau):

![Main.java dengan blok aliasing dan uji null ditambahkan](../assets/code/pertemuan-02/p02-05-bug-main.png){width=75%}

Jalankan.

> ✅ **Checkpoint:** kedua baris pertama (`Via original:` dan `Via copy:`) sama-sama menampilkan nilai `40`, meskipun hanya `copy.width` yang diubah. Hal ini bukan merupakan galat, melainkan cara kerja referensi: `original` dan `copy` menunjuk ke objek yang sama persis, sehingga perubahan melalui salah satu variabel otomatis terlihat pada variabel lainnya. Baris ketiga akan menghasilkan `NullPointerException`, dan hal ini memang disengaja.

Berikut ilustrasinya pada stack dan heap:

![Dua variabel di stack menunjuk ke satu objek Rectangle yang sama di heap](../assets/uml/p02-memory-alias.png){width=55%}

Perbaiki dengan menghapus dua baris terakhir (`Rectangle empty = null;` beserta pemanggilan `area()` di atasnya) agar program dapat berjalan kembali tanpa galat:

![Main.java setelah dua baris uji null dihapus](../assets/code/pertemuan-02/p02-05-fix-main.png){width=75%}

> ⚠️ **Jika gagal:** `NullPointerException` selalu muncul apabila method dipanggil pada referensi yang belum menunjuk ke objek mana pun (`null`). Solusinya selalu sama, yaitu memastikan objek telah benar-benar dibuat dengan `new` sebelum method-nya dipanggil.

### Langkah 6: Kelas `Account`, Cikal Bakal Bank Mini

Diagram kelas UML berikut menggambarkan kelas `Account` yang perlu dibuat, kelas pertama dari aplikasi Bank Mini yang akan dibangun sepanjang semester:

![Diagram kelas UML untuk Account](../assets/uml/p02-account.png){width=45%}

Tanda `-` di depan atribut atau method menunjukkan sifat privat (hanya dapat diakses dari dalam kelas itu sendiri), sedangkan tanda `+` menunjukkan sifat publik. Diagram `Account` di atas seluruhnya bertanda `+`, artinya seluruh atributnya masih dapat diakses langsung dari luar kelas. Ini disengaja: risiko dari desain seperti ini (misalnya saldo dapat diubah langsung tanpa lewat `deposit`/`withdraw`) menjadi alasan munculnya encapsulation, yang dibahas tuntas pada Pertemuan 3. Buat kelas baru `Account`:

![Account.java sesuai diagram UML](../assets/code/pertemuan-02/p02-06-account.png){width=55%}

Tambahkan pengujian berikut di akhir `main` pada `Main.java`:

![Main.java dengan pengujian Account ditambahkan](../assets/code/pertemuan-02/p02-06-main.png){width=75%}

> ✅ **Checkpoint:** program berhasil dikompilasi ulang dan baris terakhir menampilkan `Nadia - balance: 350000.0`.

### Langkah 7: Array of Objects, Banyak Objek dari Satu Kelas

Satu kelas dapat menghasilkan banyak objek sekaligus. Ganti bagian pengujian `Rectangle` (Langkah 4-5) dengan array `Rectangle[]`, dengan tetap mempertahankan bagian `Account` dari Langkah 6:

![Main.java final: array Rectangle[] dan pengujian Account](../assets/code/pertemuan-02/p02-07-main.png){width=75%}

> ✅ **Checkpoint:** program mencetak tiga baris area/perimeter (satu per elemen array, dengan nilai yang berbeda-beda karena setiap `Rectangle` memiliki ukurannya sendiri), diikuti baris `Nadia - balance: 350000.0`.

> ⚠️ **Jika gagal:** `ArrayIndexOutOfBoundsException` menunjukkan bahwa indeks yang diisi tidak tersedia, misalnya `shapes[3]` padahal array tersebut hanya berukuran 3 (indeks yang valid: 0, 1, 2).

## D. Tugas dan Deliverable

Kumpulkan hal berikut sesuai format yang diminta Dosen:

- Screenshot output program setelah Langkah 7.
- **Tugas mandiri:**
  1. Buat kelas `Circle` sesuai diagram UML berikut:

     ![Diagram kelas UML untuk Circle](../assets/uml/p02-circle.png){width=45%}

     Method `area()` dan `circumference()` mengembalikan nilai bertipe `double`, dihitung menggunakan rumus lingkaran (`Math.PI * radius * radius` untuk luas, `2 * Math.PI * radius` untuk keliling). Buktikan dengan membuat satu objek `Circle` di `Main` (radius 5) dan mencetak kedua hasilnya.
  2. Jawab secara singkat (2-3 kalimat untuk masing-masing pertanyaan): (a) apa perbedaan antara objek dengan referensi ke objek? (b) kapan tepatnya konstruktor sebuah kelas dijalankan?

## E. Kriteria Penilaian

| Komponen | Bobot | Kriteria Lengkap (100%) | Kriteria Minimum |
|---|---:|---|---|
| Langkah kerja tuntas | 40% | Seluruh langkah dijalankan dan berfungsi | Sebagian besar langkah selesai, hasil akhir berjalan |
| Checkpoint terverifikasi | 35% | Semua checkpoint tercapai dan dibuktikan (screenshot/output) | Sebagian checkpoint terbukti |
| Tugas mandiri | 25% | Kelas `Circle` benar dan jawaban konsep tepat | Kelas `Circle` ada meski jawaban belum lengkap |
