-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Feb 01, 2025 at 01:25 AM
-- Server version: 8.0.30
-- PHP Version: 8.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `penitipanbarangsatpam`
--
DROP DATABASE IF EXISTS `penitipanbarangsatpam`;
CREATE DATABASE IF NOT EXISTS `penitipanbarangsatpam` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `penitipanbarangsatpam`;

-- --------------------------------------------------------

--
-- Table structure for table `akun`
--

CREATE TABLE `akun` (
  `id` bigint NOT NULL,
  `email` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `status` enum('mahasiswa','satpam') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `akun`
--

INSERT INTO `akun` (`id`, `email`, `username`, `password`, `status`) VALUES
(3, 'satpam@gmail.com', 'satpam', '$2a$10$kLHslwwXCabJ/5YJ/VVfsecDeRBz0PQzA2PcVymoGPMxJsjOkmFZm', 'satpam'),
(4, '212413731@stis.ac.id', 'orizastephanie', '$2a$10$wdnGsNr8UE4rXtLxo8NVceE9YJZxizVLra6MxfXWEjZizbaJwPcpS', 'mahasiswa'),
(5, '222212683@stis.ac.id', 'jordandwi', '$2a$10$EJIHm/25XRzHEjWVYHVqEeYi6HPzhT9R/wCCdXFJyBaSDhodKze.C', 'mahasiswa');

-- --------------------------------------------------------

--
-- Table structure for table `barang_ditolak`
--

CREATE TABLE `barang_ditolak` (
  `id` bigint NOT NULL,
  `username` varchar(255) NOT NULL,
  `barang` varchar(255) NOT NULL,
  `deskripsi` text,
  `alasan` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `barang_ditolak`
--

INSERT INTO `barang_ditolak` (`id`, `username`, `barang`, `deskripsi`, `alasan`) VALUES
(2, 'jordandwi', 'Kamera Canon', 'Kamera DSLR', 'Tidak sesuai ketentuan'),
(3, 'jordandwi', 'Headset Razer', 'Headset gaming', 'Terlalu besar untuk dititipkan'),
(4, 'jordandwi', 'Kaca Mata Oakley', 'Kaca mata hitam', 'Barang pribadi tidak dititipkan'),
(5, 'jordandwi', 'SSD Eksternal Samsung', 'SSD 1TB', 'Barang elektronik tidak boleh dititipkan'),
(6, 'jordandwi', 'Tas Ransel Nike', 'Tas untuk kerja', 'Bukan barang yang perlu penitipan'),
(7, 'orizastephanie', 'Buku Kuliah', 'Buku referensi akademik', 'Tidak diizinkan dititipkan'),
(8, 'orizastephanie', 'Parfum Dior', 'Parfum koleksi', 'Mengandung cairan, tidak diperbolehkan'),
(9, 'orizastephanie', 'Speaker JBL', 'Speaker bluetooth', 'Barang elektronik tidak boleh dititipkan'),
(10, 'orizastephanie', 'Harddisk WD', 'HDD eksternal 2TB', 'Rentan terhadap kerusakan, tidak boleh dititipkan'),
(11, 'orizastephanie', 'Power Bank Aukey', 'Power bank 20.000mAh', 'Baterai lithium tidak boleh dititipkan');

-- --------------------------------------------------------

--
-- Table structure for table `pengajuan`
--

CREATE TABLE `pengajuan` (
  `id` bigint NOT NULL,
  `username` varchar(255) NOT NULL,
  `barang` varchar(255) NOT NULL,
  `deskripsi` text,
  `status` enum('menunggu','terima','tolak') DEFAULT 'menunggu'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `pengajuan`
--

INSERT INTO `pengajuan` (`id`, `username`, `barang`, `deskripsi`, `status`) VALUES
(2, 'jordandwi', 'Laptop Asus', 'Laptop kerja', 'terima'),
(3, 'jordandwi', 'Kamera Canon', 'Kamera DSLR', 'tolak'),
(4, 'jordandwi', 'Mouse Logitech', 'Mouse wireless', 'terima'),
(5, 'jordandwi', 'Headset Razer', 'Headset gaming', 'tolak'),
(6, 'jordandwi', 'Jam Tangan Fossil', 'Jam tangan elegan', 'terima'),
(7, 'jordandwi', 'Kaca Mata Oakley', 'Kaca mata hitam', 'tolak'),
(8, 'jordandwi', 'Power Bank Anker', 'Power bank 10.000mAh', 'terima'),
(9, 'jordandwi', 'SSD Eksternal Samsung', 'SSD 1TB', 'tolak'),
(10, 'jordandwi', 'Smartphone Xiaomi', 'HP Android', 'terima'),
(11, 'jordandwi', 'Tas Ransel Nike', 'Tas untuk kerja', 'tolak'),
(12, 'orizastephanie', 'Tablet Samsung', 'Tablet untuk presentasi', 'terima'),
(13, 'orizastephanie', 'Buku Kuliah', 'Buku referensi akademik', 'tolak'),
(14, 'orizastephanie', 'Sepatu Adidas', 'Sepatu olahraga', 'terima'),
(15, 'orizastephanie', 'Parfum Dior', 'Parfum koleksi', 'tolak'),
(16, 'orizastephanie', 'Smartwatch Apple', 'Jam tangan pintar', 'terima'),
(17, 'orizastephanie', 'Speaker JBL', 'Speaker bluetooth', 'tolak'),
(18, 'orizastephanie', 'Kamera Sony', 'Kamera mirrorless', 'terima'),
(19, 'orizastephanie', 'Harddisk WD', 'HDD eksternal 2TB', 'tolak'),
(20, 'orizastephanie', 'Dompet Fossil', 'Dompet kulit premium', 'terima'),
(21, 'orizastephanie', 'Power Bank Aukey', 'Power bank 20.000mAh', 'tolak');

-- --------------------------------------------------------

--
-- Table structure for table `penitipan`
--

CREATE TABLE `penitipan` (
  `id` bigint NOT NULL,
  `username` varchar(255) NOT NULL,
  `barang` varchar(255) NOT NULL,
  `deskripsi` text,
  `barcode` varchar(255) NOT NULL,
  `statuspenitipan` enum('Dalam Penitipan','Sudah Dikembalikan') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `penitipan`
--

INSERT INTO `penitipan` (`id`, `username`, `barang`, `deskripsi`, `barcode`, `statuspenitipan`) VALUES
(2, 'jordandwi', 'Laptop Asus', 'Laptop kerja', 'BC-001', 'Dalam Penitipan'),
(3, 'jordandwi', 'Mouse Logitech', 'Mouse wireless', 'BC-002', 'Dalam Penitipan'),
(4, 'jordandwi', 'Jam Tangan Fossil', 'Jam tangan elegan', 'BC-003', 'Dalam Penitipan'),
(5, 'jordandwi', 'Power Bank Anker', 'Power bank 10.000mAh', 'BC-004', 'Dalam Penitipan'),
(6, 'jordandwi', 'Smartphone Xiaomi', 'HP Android', 'BC-005', 'Sudah Dikembalikan'),
(7, 'orizastephanie', 'Tablet Samsung', 'Tablet untuk presentasi', 'BC-006', 'Dalam Penitipan'),
(8, 'orizastephanie', 'Sepatu Adidas', 'Sepatu olahraga', 'BC-007', 'Dalam Penitipan'),
(9, 'orizastephanie', 'Smartwatch Apple', 'Jam tangan pintar', 'BC-008', 'Sudah Dikembalikan'),
(10, 'orizastephanie', 'Kamera Sony', 'Kamera mirrorless', 'BC-009', 'Dalam Penitipan'),
(11, 'orizastephanie', 'Dompet Fossil', 'Dompet kulit premium', 'BC-010', 'Dalam Penitipan');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `akun`
--
ALTER TABLE `akun`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `idx_akun_username` (`username`);

--
-- Indexes for table `barang_ditolak`
--
ALTER TABLE `barang_ditolak`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_barang_ditolak_username` (`username`);

--
-- Indexes for table `pengajuan`
--
ALTER TABLE `pengajuan`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_pengajuan_username` (`username`);

--
-- Indexes for table `penitipan`
--
ALTER TABLE `penitipan`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `barcode` (`barcode`),
  ADD KEY `idx_penitipan_username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `akun`
--
ALTER TABLE `akun`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `barang_ditolak`
--
ALTER TABLE `barang_ditolak`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `pengajuan`
--
ALTER TABLE `pengajuan`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `penitipan`
--
ALTER TABLE `penitipan`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `barang_ditolak`
--
ALTER TABLE `barang_ditolak`
  ADD CONSTRAINT `barang_ditolak_ibfk_1` FOREIGN KEY (`username`) REFERENCES `akun` (`username`) ON DELETE CASCADE;

--
-- Constraints for table `pengajuan`
--
ALTER TABLE `pengajuan`
  ADD CONSTRAINT `pengajuan_ibfk_1` FOREIGN KEY (`username`) REFERENCES `akun` (`username`) ON DELETE CASCADE;

--
-- Constraints for table `penitipan`
--
ALTER TABLE `penitipan`
  ADD CONSTRAINT `penitipan_ibfk_1` FOREIGN KEY (`username`) REFERENCES `akun` (`username`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
