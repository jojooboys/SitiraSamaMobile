package com.polstat.penitipanbarang.service;

import com.polstat.penitipanbarang.model.Pengajuan;
import com.polstat.penitipanbarang.repository.PengajuanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PengajuanService {

    @Autowired
    private PengajuanRepository pengajuanRepository;

    // Menghapus static pada metode savePengajuan
    public Pengajuan savePengajuan(Pengajuan pengajuan) {
        return pengajuanRepository.save(pengajuan);
    }

    // Mengambil semua pengajuan
    public List<Pengajuan> getAllPengajuan() {
        return pengajuanRepository.findAll();
    }

    // Mengambil pengajuan berdasarkan username
    public List<Pengajuan> getPengajuanByUsername(String username) {
        return pengajuanRepository.findByUsername(username);
    }

    // Mengambil pengajuan berdasarkan ID
    public Pengajuan getPengajuanById(Long id) {
        return pengajuanRepository.findById(id).orElse(null);
    }

    // Menghapus pengajuan berdasarkan ID
    public void deletePengajuan(Long id) {
        pengajuanRepository.deleteById(id);
    }
}
