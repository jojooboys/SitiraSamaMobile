package com.polstat.penitipanbarang.service;

import com.polstat.penitipanbarang.model.Akun;
import com.polstat.penitipanbarang.repository.AkunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AkunService {

    @Autowired
    private AkunRepository akunRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Simpan akun dengan password terenkripsi
    public void createAkun(Akun akun) {
        akunRepository.save(akun);  // Simpan ke database tanpa perubahan lebih lanjut
    }

    public Akun findAkunByEmail(String email) {
        return akunRepository.findByEmail(email);
    }

    public Akun findAkunByUsername(String username) {
        return akunRepository.findByUsername(username);
    }

    public void deleteAkun(Akun akun) {
        // Menghapus akun dari database
        akunRepository.delete(akun);
    }

    // Metode untuk mengenkripsi password sebelum disimpan di database
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword); // Enkripsi password menggunakan BCrypt
    }

    // Memvalidasi password pengguna (digunakan saat login)
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);  // Memastikan password cocok
    }
}

