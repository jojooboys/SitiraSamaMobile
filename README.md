
# Penitipan Barang Satpam

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

## 🚀 API Endpoints
| Endpoint                     | HTTP Method | Deskripsi |
|------------------------------|-------------|-----------|
| `/api/auth/register`         | `POST`      | Register akun baru |
| `/api/auth/login`            | `POST`      | Login akun |
| `/api/auth/profil`           | `GET`       | Lihat profil akun |
| `/api/auth/gantiPassword`    | `PATCH`     | Ganti password |
| `/api/auth/barang`           | `POST`      | Tambah pengajuan penitipan barang |
| `/api/auth/pengajuan`        | `GET`       | Lihat daftar pengajuan barang |
| `/api/auth/barang/tolak`     | `GET`       | Lihat barang yang ditolak |
| `/api/auth/barang/{id}`      | `DELETE`    | Hapus barang penitipan |
| `/api/auth/pengajuan/{id}`   | `DELETE`    | Hapus pengajuan barang |

**Catatan:** Semua request API yang membutuhkan autentikasi harus menyertakan **Bearer Token**.

---

## 🔧 Teknologi yang Digunakan
- **Backend**: Spring Boot, JWT Authentication, JPA, MySQL
- **Frontend**: Android Studio, Kotlin, Retrofit untuk komunikasi API
- **Database**: MySQL

---

## 📞 Kontak
Jika mengalami kendala atau butuh bantuan, silakan hubungi **Jordan Dwi Febriyanto** melalui **jordandwifebri@gmail.com**.

---
