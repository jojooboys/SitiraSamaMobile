package com.polstat.penitipanbarang.repository;

import com.polstat.penitipanbarang.model.Akun;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AkunRepository extends JpaRepository<Akun, String> {
    Akun findByEmail(String email);
    Akun findByUsername(String username);
}
