package com.polstat.penitipanbarang.controller;

import com.polstat.penitipanbarang.model.BarangDitolak;
import com.polstat.penitipanbarang.service.BarangDitolakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/satpam/barang/tolak")
public class BarangDitolakController {

    @Autowired
    private BarangDitolakService barangDitolakService;

    // Menambahkan barang yang ditolak
    @PostMapping
    public BarangDitolak addBarangDitolak(@RequestBody BarangDitolak barangDitolak) {
        return barangDitolakService.saveBarangDitolak(barangDitolak);
    }

    // Melihat barang yang ditolak berdasarkan username
    @GetMapping("/{username}")
    public List<BarangDitolak> getBarangDitolakByUsername(@PathVariable String username) {
        return barangDitolakService.getBarangDitolakByUsername(username);
    }

    // Menghapus barang yang ditolak berdasarkan ID
    @DeleteMapping("/{id}")
    public void deleteBarangDitolak(@PathVariable Long id) {
        barangDitolakService.deleteBarangDitolak(id);
    }
}
