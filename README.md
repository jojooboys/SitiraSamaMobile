
# 📦 Sistem Penitipan Barang di Satpam Mahasiswa (Sitira-Sama)

Proyek ini merupakan sistem penitipan barang berbasis API yang dibangun menggunakan **Spring Boot (Java)** untuk backend dan **Android Studio (Kotlin)** untuk frontend dalam rangka memenuhi **Ujian Akhir Semester (UAS)** mata kuliah **Pemrogaman Platform Khusus (PPK)** di **Politeknik Statistika STIS**. Sistem ini menyediakan fitur autentikasi, pengajuan penitipan barang, serta pengelolaan barang yang diterima atau ditolak oleh satpam.

## 📌 Fitur Utama
- **Autentikasi dengan JWT** (Login, Register, Ganti Password)
- **Pengajuan penitipan barang** oleh mahasiswa
- **Persetujuan atau penolakan** barang oleh satpam
- **Manajemen penitipan barang** dengan status yang dapat diperbarui
- **Integrasi Android Studio dengan API Backend**

---

## ⚙️ Instalasi dan Konfigurasi

### 1️⃣ Import Database MySQL
Sebelum menjalankan sistem, import database **`penitipanbarangsatpam.sql`** ke MySQL:
```sql
mysql -u root -p penitipanbarangsatpam < penitipanbarangsatpam.sql
```
Atau melalui **phpMyAdmin**:
1. Masuk ke **phpMyAdmin**
2. Buat database baru bernama **`penitipanbarangsatpam`**
3. Masuk ke tab **Import** dan pilih file **penitipanbarangsatpam.sql**, lalu klik **Go**.

### 2️⃣ Jalankan API Backend
Untuk menjalankan API menggunakan **Spring Boot**, gunakan perintah berikut di terminal:
```bash
mvn spring-boot:run
```
Atau jika menggunakan **Gradle**:
```bash
./gradlew bootRun
```
Pastikan server berjalan di `http://localhost:8080/` atau sesuaikan dengan konfigurasi yang diinginkan.

### 3️⃣ Jalankan Aplikasi Android
Setelah API aktif, jalankan aplikasi Android dengan **Android Studio**:
1. **Buka Android Studio**
2. **Import proyek Android**
3. **Perbarui URL API** di `ApiClient.kt` (gantilah IP jika perlu):
   ```kotlin
   private const val BASE_URL = "http://192.168.1.100:8080/"
   ```
4. **Jalankan aplikasi** di emulator atau perangkat fisik.

---

## 🚀 API Endpoints Lengkap

| **Endpoint**                      | **HTTP Method** | **Deskripsi**                                |
|:----------------------------------|:----------------|:---------------------------------------------|
| **🔐 Autentikasi**                |                 |                                              |
| `/api/auth/register`             | `POST`          | Register akun baru                           |
| `/api/auth/login`                | `POST`          | Login akun                                   |
| `/api/auth/profil`               | `GET`           | Lihat profil pengguna                        |
| `/api/auth/profil`               | `PATCH`         | Update profil pengguna                       |
| `/api/auth/gantiPassword`        | `PATCH`         | Ganti password akun                          |
| `/api/auth/akun/{email}`         | `DELETE`        | Hapus akun berdasarkan email                 |
|                                    |                 |                                              |
| **📦 Pengajuan Penitipan Barang** |                 |                                              |
| `/api/auth/barang`               | `POST`          | Tambah pengajuan penitipan barang            |
| `/api/auth/pengajuan`            | `GET`           | Lihat daftar pengajuan barang                |
| `/api/auth/pengajuan`            | `PATCH`         | Update data pengajuan barang                 |
| `/api/auth/pengajuan/{id}`       | `DELETE`        | Hapus pengajuan barang berdasarkan ID        |
|                                    |                 |                                              |
| **📋 Manajemen Penitipan Barang** |                 |                                              |
| `/api/auth/barang`               | `GET`           | Lihat daftar barang yang dititipkan          |
| `/api/auth/barang/{id}`          | `PATCH`         | Update status barang penitipan               |
| `/api/auth/barang/{id}`          | `DELETE`        | Hapus barang penitipan berdasarkan ID        |
|                                    |                 |                                              |
| **❌ Barang Ditolak**              |                 |                                              |
| `/api/auth/barang/tolak`         | `GET`           | Lihat daftar barang yang ditolak             |
| `/api/auth/barang/tolak`         | `PATCH`         | Update data barang yang ditolak              |
| `/api/auth/barang/tolak/{id}`    | `DELETE`        | Hapus barang yang ditolak berdasarkan ID     |

---

## 🔧 Teknologi yang Digunakan
- **Backend**: Spring Boot, JWT Authentication, JPA, MySQL
- **Frontend**: Android Studio, Kotlin, Retrofit untuk komunikasi API
- **Database**: MySQL

---

## ⚠️ **Catatan Penting**
- Semua request API yang membutuhkan autentikasi harus menyertakan **Bearer Token** di header:
  ```http
  Authorization: Bearer <token_anda>
  ```
- Gunakan **JSON** sebagai format request body, contohnya:
  ```json
  {
    "nama": "Budi",
    "email": "budi@example.com",
    "password": "password123"
  }
  ```

---

## 📞 Kontak
Jika mengalami kendala atau butuh bantuan, silakan hubungi **Jordan Dwi Febriyanto** melalui **jordandwifebri@gmail.com**.

---
