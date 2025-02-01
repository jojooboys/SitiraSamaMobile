package com.polstat.penitipanbarang.service;

import com.polstat.penitipanbarang.exception.InvalidStatusException;
import com.polstat.penitipanbarang.model.Penitipan;
import com.polstat.penitipanbarang.repository.PenitipanRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PenitipanService {

    @Autowired
    private PenitipanRepository penitipanRepository;

    // Menyimpan data penitipan
    public Penitipan savePenitipan(Penitipan penitipan) {
        return penitipanRepository.save(penitipan);
    }

    // Mengambil semua data penitipan
    public List<Penitipan> getAllPenitipan() {
        return penitipanRepository.findAll();
    }

    // Mengambil penitipan berdasarkan username
    public List<Penitipan> getPenitipanByUsername(String username) {
        return penitipanRepository.findByUsername(username);
    }

    // Mengambil penitipan berdasarkan ID
    public Penitipan getPenitipanById(Long id) {
        return penitipanRepository.findById(id).orElse(null); // Mengembalikan null jika barang tidak ditemukan
    }

    // Menghapus penitipan berdasarkan ID
    public void deletePenitipan(Long id) {
        penitipanRepository.deleteById(id);
    }

    // Metode untuk update status penitipan
    public Penitipan updateStatus(Long id, String newStatus) {
        // Mencari penitipan berdasarkan ID
        Penitipan penitipan = penitipanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Barang dengan ID " + id + " tidak ditemukan"));

        // Validasi status
        if (!"Dalam Penitipan".equals(newStatus) && !"Sudah Dikembalikan".equals(newStatus)) {
            throw new InvalidStatusException("Status harus 'Dalam Penitipan' atau 'Sudah Dikembalikan'");
        }

        // Mengubah status penitipan
        penitipan.setStatuspenitipan(newStatus);

        // Menyimpan perubahan
        return penitipanRepository.save(penitipan);
    }

    public Penitipan findPenitipanById(Long id) {
        return penitipanRepository.findById(id).orElse(null);
    }

    public Penitipan updatePenitipan(Penitipan penitipan) {
        return penitipanRepository.save(penitipan);
    }

}
