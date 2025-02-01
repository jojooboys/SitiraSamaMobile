package com.polstat.penitipanbarang.repository;

import com.polstat.penitipanbarang.model.Pengajuan;
import com.polstat.penitipanbarang.model.Penitipan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PenitipanRepository extends JpaRepository<Penitipan, Long> {
    List<Penitipan> findByUsername(String username);

    // Ambil semua Penitipan
    List<Penitipan> findAll();
}
