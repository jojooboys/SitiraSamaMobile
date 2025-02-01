package com.polstat.penitipanbarang.repository;

import com.polstat.penitipanbarang.model.Pengajuan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PengajuanRepository extends JpaRepository<Pengajuan, Long> {

    // Cari Pengajuan berdasarkan Username
    List<Pengajuan> findByUsername(String username);

    // Ambil semua Pengajuan
    List<Pengajuan> findAll();
}