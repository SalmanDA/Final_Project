# PBO-F-Project

Nama Anggota:
1. Salman Damai Alfariq           - 05111940000159
2. Muhammad Fikri Sandi Pratama   - 05111940000195
3. Muhammad Rizky Widodo          - 05111940000216

### Snake Game
<p>Game yang sudah populer di kalangan masyarakat. Game ini memiliki beberapa objek seperti ular dan apel. Cara memainkannya adalah dengan mengendalikan Gerakan ular yang di gerakkan lewat keyboard up, down, left and right dalam permainan. Tujuannya adalah memakan sebanyak mungkin apel yang muncul tanpa menabrak tembok ataupun badan dari ular itu sendiri serta untuk mendapatkan score tertinggi dan mencapai panjang maksimal dari ular. Karena ketika ular sudah memiliki panjang yang sudah di maksimalkan kemungkinan ular tidak bisa berputar-putar di map lagi, ada beberapa fitur yang terdapat di game ini yaitu: </p>

### Fitur
1.	Terdapat pilihan level
Semakin tinggi level yang dipilih, semakin cepat Gerakan ular dan akan semakin sulit dikendalikan yang terdiri dari Easy, Hard, Expert, Dan Impossible. 
2.	Adanya racun/ranjau
Jika yang dimakan adalah racun, maka akan mengurangi 5 point dan jumlah poison ada 5 buah namun mempunyai batas waktu. 
3. Adanya AppleMagic
Jika memakan AppleMagic highscore akan ditambahkan 10 point dari biasanya namun mempunyai batas waktu.
4. Credit
Untuk menambahkan credit yang isinya menampilkan Nama kelompok, Dosen pebimbing, dan Refrensi.

### Class
1.	Apple
Dibuat untuk membuat apel sebagai makanan ular.
2.	Body
Untuk membuat bagian-bagian tubuh ular. Jika tubuh ular semakin Panjang, maka akan dibuat objek body lagi untuk menambah konponen tubuh.
3.	Head
Objek dari class Head ini yang akan kita kendalikan, dan objek bagian-bagian tubuhnya akan mengikuti gerakan dari objek  Head ini.
4.	Sprite
Sebagai super class dari kelas Apple, Body, head, Magic Apple, dan Poison.
5.	Game Panel
Class yang berfungsi sebagai pengatur jalannya permainan dan di dalamnya ada untuk mengatur tingkatan level, Title, dan Credit.
6.	Snake Game dan Game Frame
Main class untuk menjalankan program.
7.	Magic Apple
Berfungsi untuk memunculkan Magic Apple yang jika berhasil dimakan, akan mendapat skor 10 point. Kemunculan Magic Apple ini memiliki Batasan waktu tertentu untuk muncul dan mempunyai kemungkinan muncul sekitar 50%.
8.	Poison
Membuat objek racun yang jika dimakan akan menyebabkan turunnya skor sebesar 5 point, namun panjang tubuh ular akan tetap. Objek ini juga memiliki Batasan waktu untuk muncul dan hanya muncul dengan persentase tertentu saja, serta kemunculan racun ada 5 buah racun.
9. Score
Untuk menambahkan score baru ke class highscore
10. HighScore
Untuk menampilkan 10 score yang sudah di simpan dari setiap tingkatan level.

Class Diagram Image
![]()

### Refrensi
1. http://zetcode.com/javagames/snake/
2. http://forum.codecall.net/topic/50071-making-a-simple-high-score-system/
