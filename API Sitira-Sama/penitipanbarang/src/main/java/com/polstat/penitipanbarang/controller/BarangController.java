package com.polstat.penitipanbarang.controller;

import com.polstat.penitipanbarang.model.Penitipan;
import com.polstat.penitipanbarang.model.Pengajuan;
import com.polstat.penitipanbarang.service.PenitipanService;
import com.polstat.penitipanbarang.service.PengajuanService;
import com.polstat.penitipanbarang.util.BarcodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/barang")
public class BarangController {

    @Autowired
    private PengajuanService pengajuanService;

    @Autowired
    private PenitipanService penitipanService;

    @PostMapping
    public String addPengajuan(@RequestBody Pengajuan pengajuan) {
        pengajuanService.savePengajuan(pengajuan);
        return "Pengajuan penitipan barang berhasil ditambahkan.";
    }

    @GetMapping
    public List<Penitipan> getBarang(@RequestParam String username) {
        return penitipanService.getPenitipanByUsername(username);
    }
}
