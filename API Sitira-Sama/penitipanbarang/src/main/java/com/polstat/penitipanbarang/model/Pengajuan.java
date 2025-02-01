package com.polstat.penitipanbarang.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pengajuan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Atau AUTO/SEQUENCE tergantung kebutuhan
    private Long id;

    private String username;
    private String barang;
    private String deskripsi;

    private String status;


    // Getter dan Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBarang() {
        return barang;
    }

    public void setBarang(String barang) {
        this.barang = barang;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}
}
