package com.polstat.penitipanbarang.service;

import com.polstat.penitipanbarang.model.BarangDitolak;
import com.polstat.penitipanbarang.repository.BarangDitolakRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BarangDitolakService {

    @Autowired
    private BarangDitolakRepository barangDitolakRepository;

    // Menyimpan barang yang ditolak
    public BarangDitolak saveBarangDitolak(BarangDitolak barangDitolak) {
        return barangDitolakRepository.save(barangDitolak);
    }

    // Mengambil daftar barang yang ditolak oleh mahasiswa (berdasarkan username)
    public List<BarangDitolak> getBarangDitolakByUsername(String username) {
        return barangDitolakRepository.findByUsername(username);
    }

    // Mengambil barang yang ditolak berdasarkan ID
    public BarangDitolak getBarangDitolakById(Long id) {
        Optional<BarangDitolak> barangDitolak = barangDitolakRepository.findById(id);
        return barangDitolak.orElse(null); // Kembalikan null jika tidak ditemukan
    }

    // Menghapus barang yang ditolak berdasarkan ID
    public void deleteBarangDitolak(Long id) {
        barangDitolakRepository.deleteById(id);
    }

    // Menghapus semua barang yang ditolak berdasarkan username
    public void deleteBarangDitolakByUsername(String username) {
        List<BarangDitolak> barangDitolakList = getBarangDitolakByUsername(username);
        for (BarangDitolak barang : barangDitolakList) {
            deleteBarangDitolak(barang.getId());
        }
    }

    // Mendapatkan semua barang ditolak (untuk satpam)
    public List<BarangDitolak> getAllBarangDitolak() {
        return barangDitolakRepository.findAll();  // Mengambil semua barang yang ditolak
    }
}
