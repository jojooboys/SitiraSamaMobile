
CREATE DATABASE IF NOT EXISTS penitipanbarangsatpam;
USE penitipanbarangsatpam;

-- Tabel Akun
CREATE TABLE IF NOT EXISTS akun (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    status ENUM('mahasiswa', 'satpam') NOT NULL
);

-- Tabel Pengajuan
CREATE TABLE IF NOT EXISTS pengajuan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    barang VARCHAR(255) NOT NULL,
    deskripsi TEXT,
    status ENUM('menunggu', 'terima', 'tolak') DEFAULT 'menunggu',
    FOREIGN KEY (username) REFERENCES akun(username) ON DELETE CASCADE
);

-- Tabel Penitipan
CREATE TABLE IF NOT EXISTS penitipan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    barang VARCHAR(255) NOT NULL,
    deskripsi TEXT,
    barcode VARCHAR(255) UNIQUE NOT NULL,
    statuspenitipan ENUM('Dalam Penitipan', 'Sudah Dikembalikan') NOT NULL,
    FOREIGN KEY (username) REFERENCES akun(username) ON DELETE CASCADE
);

-- Tabel Barang Ditolak
CREATE TABLE IF NOT EXISTS barang_ditolak (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    barang VARCHAR(255) NOT NULL,
    deskripsi TEXT,
    alasan TEXT,
    FOREIGN KEY (username) REFERENCES akun(username) ON DELETE CASCADE
);

-- Indeks untuk performa query
CREATE INDEX idx_akun_username ON akun(username);
CREATE INDEX idx_pengajuan_username ON pengajuan(username);
CREATE INDEX idx_penitipan_username ON penitipan(username);
CREATE INDEX idx_barang_ditolak_username ON barang_ditolak(username);

-- Data Dummy untuk testing (Opsional)
INSERT INTO akun (email, username, password, status) VALUES
('admin@polstat.ac.id', 'admin', '$2a$10$7jFg0zG/MUPFTBZ', 'satpam'),
('mahasiswa1@polstat.ac.id', 'mahasiswa1', '$2a$10$7jFg0zG/MUPFTBZ', 'mahasiswa');

INSERT INTO pengajuan (username, barang, deskripsi, status) VALUES
('mahasiswa1', 'Laptop', 'Macbook Pro 2022', 'menunggu');

INSERT INTO penitipan (username, barang, deskripsi, barcode, statuspenitipan) VALUES
('mahasiswa1', 'Tas', 'Tas Hitam Besar', 'ABC1234567', 'Dalam Penitipan');

INSERT INTO barang_ditolak (username, barang, deskripsi, alasan) VALUES
('mahasiswa1', 'Pisau Lipat', 'Pisau lipat kecil', 'Barang berbahaya');
