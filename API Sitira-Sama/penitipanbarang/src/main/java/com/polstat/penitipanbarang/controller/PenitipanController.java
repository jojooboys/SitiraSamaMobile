package com.polstat.penitipanbarang.controller;

import com.polstat.penitipanbarang.model.Penitipan;
import com.polstat.penitipanbarang.service.PenitipanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/satpam/barang")
public class PenitipanController {

    @Autowired
    private PenitipanService penitipanService;

    // Mendapatkan semua data penitipan
    @GetMapping
    public List<Penitipan> getAllPenitipan() {
        return penitipanService.getAllPenitipan();
    }

    // Mendapatkan penitipan berdasarkan username
    @GetMapping("/{username}")
    public List<Penitipan> getPenitipanByUsername(@PathVariable String username) {
        return penitipanService.getPenitipanByUsername(username);
    }

    @PreAuthorize("hasRole('satpam')")  // Membatasi akses hanya untuk role SATPAM
    // Menambahkan penitipan barang
    @PostMapping
    public Penitipan addPenitipan(@RequestBody Penitipan penitipan) {
        return penitipanService.savePenitipan(penitipan);
    }

    @PreAuthorize("hasRole('satpam')")  // Membatasi akses hanya untuk role SATPAM
    // Menghapus penitipan barang berdasarkan ID
    @DeleteMapping("/{id}")
    public void deletePenitipan(@PathVariable Long id) {
        penitipanService.deletePenitipan(id);
    }

    @PreAuthorize("hasRole('satpam')")  // Membatasi akses hanya untuk role SATPAM
    @PatchMapping("/barang/{id}")
    public ResponseEntity<Penitipan> updateStatusPenitipan(@PathVariable Long id, @RequestBody String newStatus) {
        Penitipan updatedPenitipan = penitipanService.updateStatus(id, newStatus);
        return ResponseEntity.ok(updatedPenitipan);
    }
}
