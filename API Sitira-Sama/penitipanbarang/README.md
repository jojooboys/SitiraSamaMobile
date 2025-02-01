# Sitira Sama: Layanan Web Services Penitipan Barang di Satpam untuk Mahasiswa Politeknik Statistika STIS

Selamat datang di **Sitira Sama**, layanan web services untuk mengelola penitipan barang mahasiswa di Politeknik Statistika STIS. Sistem ini dirancang untuk mempermudah proses pengajuan, pengelolaan, dan pengambilan barang yang dititipkan di satpam dengan fitur autentikasi berbasis **JSON Web Token (JWT)** serta kontrol akses berbasis peran (role-based access).

---

## 🚀 **Fitur Utama**
1. **Autentikasi Pengguna**:
   - Pendaftaran akun untuk mahasiswa dan satpam.
   - Login dengan email dan password untuk mendapatkan token akses.
   - Proteksi endpoint menggunakan JWT.

2. **Pengelolaan Barang oleh Mahasiswa**:
   - Pengajuan barang untuk dititipkan.
   - Melihat daftar pengajuan, barang yang dititipkan, dan barang yang ditolak.

3. **Pengelolaan Barang oleh Satpam**:
   - Melihat semua pengajuan barang.
   - Menyetujui atau menolak pengajuan barang.
   - Mengubah status barang yang dititipkan.
   - Menghapus barang dari daftar penitipan atau barang yang ditolak.

4. **Manajemen Profil**:
   - Mengubah profil pengguna (email, username).
   - Mengganti password pengguna.

5. **Keamanan Data**:
   - Enkripsi password menggunakan **BCrypt**.
   - Validasi token JWT untuk setiap request.

---

## 🛠️ **Teknologi yang Digunakan**
- **Framework**: Spring Boot
- **Database**: PostgreSQL (JPA/Hibernate)
- **Autentikasi**: JSON Web Token (JWT)
- **Bahasa Pemrograman**: Java
- **Logging**: SLF4J, Logback
- **Build Tool**: Maven

---

## 📄 **Struktur Endpoint**

### **Autentikasi dan Profil**
| HTTP Method | Endpoint                  | Deskripsi                                                |
|-------------|---------------------------|----------------------------------------------------------|
| `POST`      | `/api/auth/register`      | Mendaftarkan akun baru (mahasiswa/satpam).               |
| `POST`      | `/api/auth/login`         | Login untuk mendapatkan access token.                    |
| `DELETE`    | `/api/auth/akun/email`    | Menghapus akun pengguna yang sedang login.               |
| `GET`       | `/api/auth/profil`        | Melihat profil pengguna yang sedang login.               |
| `PATCH`     | `/api/auth/profil`        | Mengubah profil (email, username) pengguna.              |
| `PATCH`     | `/api/auth/gantiPassword` | Mengubah password pengguna yang sedang login.            |

### **Mahasiswa**
| HTTP Method | Endpoint                | Deskripsi                                                |
|-------------|-------------------------|----------------------------------------------------------|
| `POST`      | `/api/auth/barang`      | Menambahkan pengajuan penitipan barang.                  |
| `GET`       | `/api/auth/pengajuan`   | Melihat daftar pengajuan barang.                         |
| `GET`       | `/api/auth/barang`      | Melihat daftar barang yang dititipkan.                   |
| `GET`       | `/api/auth/barang/tolak`| Melihat daftar barang yang ditolak beserta alasannya.    |
| `DELETE`    | `/api/auth/pengajuan/id`| Menghapus pengajuan barang.                              |

### **Satpam**
| HTTP Method | Endpoint                    | Deskripsi                                                |
|-------------|-----------------------------|----------------------------------------------------------|
| `GET`       | `/api/auth/pengajuan`       | Melihat semua pengajuan barang.                          |
| `GET`       | `/api/auth/barang`          | Melihat semua barang yang berhasil dititipkan.           |
| `GET`       | `/api/auth/barang/tolak`    | Melihat semua barang yang ditolak beserta alasannya.     |
| `PATCH`     | `/api/auth/pengajuan`       | Menerima atau menolak pengajuan barang.                  |
| `PATCH`     | `/api/auth/barang/id`       | Mengubah status barang yang dititipkan.                  |
| `PATCH`     | `/api/auth/barang/tolak`    | Mengubah alasan barang yang ditolak.                     |
| `DELETE`    | `/api/auth/barang/id`       | Menghapus barang dari daftar penitipan.                  |
| `DELETE`    | `/api/auth/barang/tolak/id` | Menghapus barang dari daftar barang yang ditolak.        |
| `DELETE`    | `/api/auth/pengajuan/id`    | Menghapus barang dari daftar pengajuan.                  |

---

## 📖 **Dokumentasi API**

### **Swagger UI**
Swagger UI menyediakan antarmuka visual untuk eksplorasi API. Anda dapat mengakses Swagger UI melalui URL berikut setelah aplikasi dijalankan:
(http://localhost:8080/docs/swagger-ui/index.html#/)


### **File OpenAPI JSON**
Jika Anda menggunakan alat seperti Postman atau SwaggerHub, Anda dapat menggunakan file OpenAPI berikut:
https://raw.githubusercontent.com/jordandwifebriyanto/SitiraSama/main/openapi.json

---

## 🔧 **Cara Menggunakan**

### **1. Setup Proyek**
1. Clone repositori:
   ```bash
   git clone https://github.com/jojooboys/SitiraSama.git
   cd SitiraSama
2. Pastikan Anda memiliki Java 21 atau lebih baru, Maven, dan PostgreSQL terinstall.
3. Buat database baru di PostgreSQL:
   CREATE DATABASE PenitipanBarangSatpam;
4. Konfigurasikan application.properties:
   spring.application.name=penitipanbarang
   spring.datasource.url=jdbc:mysql://localhost:3306/PenitipanBarangSatpam
   spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
   spring.datasource.username=your_database_username
   spring.datasource.password=your_database_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

   jwt.secret=mysecretkey
   jwt.expiration=3600000  # 1 hour
5. Jalankan aplikasi:
   mvn spring-boot:run

---

## 🧪 **Pengujian**
### **1. Swagger UI**
Buka Swagger UI di http://localhost:8080/docs/swagger-ui/index.html#/ untuk mencoba endpoint secara langsung.

### **2. Postman**
Import file OpenAPI JSON untuk menguji endpoint secara manual.

### **3. Unit Testing**
Jalankan unit testing menggunakan Maven:
mvn test

---

## 🛡️ **Keamanan**
### **1. Enkripsi Password**
Semua password dienkripsi menggunakan BCrypt.
### **2. Token JWT**
Setiap request ke endpoint yang dilindungi harus menyertakan token JWT yang valid.
### **3. Role-Based Access**
Hanya pengguna dengan role satpam yang dapat mengakses endpoint tertentu.

---

## 🌟 **Kontribusi**
Kontribusi untuk proyek ini sangat disambut baik. Silakan buat pull request atau ajukan issue jika menemukan bug atau memiliki saran untuk pengembangan.

---

## 📄 **Lisensi**
Proyek ini dilisensikan di bawah MIT License.

---

## ✉️ **Kontak**
**Nama**: Jordan Dwi Febriyanto
**Email**: jordandwifebri@gmail.com
**Proyek GitHub**: Sitira Sama

Semoga layanan ini bermanfaat dan mempermudah pengelolaan penitipan barang di Politeknik Statistika STIS! 😊
