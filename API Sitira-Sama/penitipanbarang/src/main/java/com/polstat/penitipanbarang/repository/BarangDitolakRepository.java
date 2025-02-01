package com.polstat.penitipanbarang.repository;

import com.polstat.penitipanbarang.model.BarangDitolak;
import com.polstat.penitipanbarang.model.Penitipan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarangDitolakRepository extends JpaRepository<BarangDitolak, Long> {
    List<BarangDitolak> findByUsername(String username);

    // Ambil semua BarangDitolak
    List<BarangDitolak> findAll();
}
