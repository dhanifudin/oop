# Gaya slide (Marp)

Ikuti pola dari `advance-web-programming/slides/id/bab01-arsitektur-web-modern.md`:
frontmatter `marp: true`, `size: 16:9`, `paginate: true`, palet biru, kelas
`lead` (judul), `divider` (pembatas bagian), dan util `.cols`, `.term-box`,
`.tip-box`, `.warn-box`, `.flow`, `.stack`, `.footnote`. **Slide fokus pada
konsep**: cuplikan kode di slide singkat (maksimal ~8 baris, teks biasa
dengan highlight native Marp), bukan gambar; listing lengkap ada di
jobsheet. Diagram struktur/UML di slide memakai gambar PlantUML dari
`slides/assets/uml/`.

Lihat [[bank-mini]] untuk pola "konsep dulu, baru studi kasus" (toy generik
di setiap Bagian sebelum "Menerapkan ke Bank Mini") dan wajibnya slide
"Mengapa Ini Penting?" per konsep; kedua aturan itu berlaku untuk struktur
di bawah.

## SOP dek: sesi konsep 4x50 menit

Satu pertemuan konsep berdurasi 4x50 menit (200 menit), seluruhnya berbasis
slide (praktikum ada di sesi/jobsheet terpisah). Untuk durasi ini, dek
menyasar **~48-55 slide isi** (di luar frontmatter), dengan asumsi kasar
~4 menit per slide termasuk waktu untuk latihan dan diskusi singkat di
tempat. Dek yang jauh lebih pendek dari ini (mis. 15-25 slide) tidak
mengisi 200 menit dan harus direvisi mengikuti SOP ini.

Struktur:

1. **Pembuka (3 slide)**: lead judul -> "Yang Akan Kamu Pelajari" -> slide
   peta sesi yang memetakan keempat Bagian ke blok 50 menit (mis. "Sesi 1
   (50'): ...", "Sesi 2 (50'): ...", dst.), supaya baik Dosen maupun
   mahasiswa punya patokan tempo.
2. **Empat Bagian, satu Bagian per blok 50 menit.** Slide divider tiap
   Bagian menyertakan penanda sesi di subjudul atau body (mis. "Sesi 2 dari
   4") supaya patokan tempo di slide peta sesi konsisten dengan isi.
   Setiap Bagian, sejauh relevan dengan materinya, diakhiri urutan elemen
   berikut (selain slide konsep inti seperti biasa):
   - **Kesalahan Umum** (opsional, dipakai kalau ada miskonsepsi yang
     benar-benar sering terjadi): satu slide `warn-box` yang menampilkan
     kesalahan mahasiswa yang lazim (mis. kode/pemahaman yang salah)
     berdampingan dengan versi yang benar, plus penjelasan singkat
     mengapa yang salah itu salah.
   - **Latihan**: satu slide berisi HANYA soal (klasifikasi, prediksi
     output, identifikasi pola, dst.), tanpa jawaban di slide yang sama.
   - **Jawaban Latihan**: slide terpisah segera setelahnya, berisi jawaban
     dan penjelasan singkat kenapa.
   - **Rangkuman dan Jembatan**: satu slide yang merangkum inti Bagian
     tsb dalam beberapa poin, ditutup satu kalimat yang menjembatani ke
     Bagian berikutnya (apa yang akan dibahas selanjutnya dan kenapa itu
     relevan setelah apa yang baru dipelajari). Bagian terakhir cukup
     rangkuman tanpa jembatan (sudah masuk penutup dek).
   - **Contoh Kode** (dipakai di titik yang butuh penguatan konkret,
     bukan di setiap Bagian): satu slide berjudul "Contoh Kode: ..."
     berisi cuplikan kode singkat (maksimal ~8 baris, sesuai aturan
     umum cuplikan kode di slide) yang mengonkretkan konsep yang baru
     dijelaskan, diikuti satu kalimat takeaway (tip-box). Tidak
     mengasumsikan Dosen mengetik ulang kode ini secara langsung; kalau
     Dosen ingin live-coding di kelas, cuplikan ini tetap jadi acuan
     yang aman untuk ditampilkan bila demo langsung gagal atau
     kehabisan waktu.
3. **Penutup (3 slide, tidak berubah dari pola lama)**: satu slide
   rangkuman akhir dek, lalu dua slide TERPISAH: lead "Referensi" (hanya
   sitasi + pointer jobsheet) lalu slide biasa "Diskusi" (satu pertanyaan
   diskusi yang konkret dan terjawab, dengan skenario/tugas spesifik,
   bukan pertanyaan terbuka tanpa jangkar). Jangan gabungkan keduanya jadi
   satu slide "Referensi dan Diskusi": pernah menyembunyikan tiga hal
   berbeda (sitasi, pointer jobsheet, pertanyaan diskusi) dalam satu blok
   tak terbedakan.

Kerangka kosong yang mengikuti SOP ini ada di `slides/TEMPLATE.md`; salin
dari situ saat membuat/merevisi dek, jangan menyusun struktur dari nol.

**Pengecualian sengaja di Pertemuan 3**: slide "Diskusi" penutup diganti
dengan "Tugas Mandiri: Mencari Encapsulation di Dunia Nyata", tugas luring
berdomain bebas (mahasiswa memilih sendiri sistem nyata apa pun di luar
Bank Mini untuk dianalisis), bukan pertanyaan diskusi tunggal yang
terjangkar seperti biasa. Ini permintaan eksplisit Dosen, bukan pola baku
untuk dek lain: jangan meniru pengecualian ini ke dek lain kecuali ada
permintaan eksplisit yang sama.

## Bahaya tersembunyi di kelas `divider`

Blok CSS `section.divider h1` mengatur warna putih, tapi aturan global
`h2 { color: #1d4ed8; }` (biru) tetap berlaku untuk subjudul `## ...` di
slide divider kecuali ditimpa eksplisit dengan `section.divider h2 {
color: #bfdbfe; }`. Tanpa override ini, subjudul jadi TIDAK TERLIHAT
karena warnanya sama persis dengan latar belakang biru slide divider (bug
nyata yang lolos dari verifikasi render-per-halaman sebelumnya, karena
teks yang hilang akibat warna sama dengan latar tidak terlihat seperti
kesalahan struktural saat sekilas dipindai). Pastikan blok CSS ini selalu
ikut disalin ke setiap deck baru.

## Bahaya tersembunyi sintaks ukuran gambar: Marp vs jobsheet berbeda

Marp mengatur ukuran gambar lewat prefix di dalam teks alt, `![h:300
keterangan](...)`, SEDANGKAN jobsheet (Pandoc) memakainya lewat suffix
setelah kurung, `![keterangan](...){width=70%}`. Menulis sintaks jobsheet
di file slide (mis. `![keterangan](...){h:300}`) tidak menghasilkan galat
apa pun, `{h:300}` hanya tercetak sebagai teks harfiah tepat di bawah
gambar (bug nyata yang lolos hingga verifikasi page-by-page). Selalu
double-check sintaks sesuai jenis file yang sedang diedit, terutama saat
menyalin satu gambar UML yang dipakai bersama slide dan jobsheet. Lihat
juga [[pipeline]] untuk gotcha ukuran gambar lainnya (rasio font-ke-viewBox,
overflow gambar penuh-lebar).

## Verifikasi

Setiap dek baru/revisi diverifikasi lewat render per-halaman sungguhan
(PDF -> gambar per halaman), bukan hanya membaca markdown-nya: berulang
kali bug (subjudul tak terlihat, sintaks ukuran gambar salah, gambar
meluber, teks terklip) baru ketahuan lewat pemeriksaan visual ini.
