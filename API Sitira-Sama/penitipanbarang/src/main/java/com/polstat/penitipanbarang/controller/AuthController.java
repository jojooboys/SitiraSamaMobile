package com.polstat.penitipanbarang.controller;

import com.polstat.penitipanbarang.exception.InvalidStatusException;
import com.polstat.penitipanbarang.dto.*;
import com.polstat.penitipanbarang.dto.ChangePasswordRequest;
import com.polstat.penitipanbarang.model.*;
import com.polstat.penitipanbarang.service.AkunService;
import com.polstat.penitipanbarang.service.BarangDitolakService;
import com.polstat.penitipanbarang.service.PengajuanService;
import com.polstat.penitipanbarang.service.PenitipanService;
import com.polstat.penitipanbarang.util.BarcodeUtil;
import com.polstat.penitipanbarang.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AkunService akunService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PengajuanService pengajuanService;

    @Autowired
    private BarangDitolakService barangDitolakService;

    @Autowired
    private PenitipanService penitipanService;

    private Long generateUniqueId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Endpoint Register
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AkunRequest akunRequest) {
        // Cek jika email sudah terdaftar
        if (akunService.findAkunByEmail(akunRequest.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email sudah digunakan.");
        }

        // Buat akun baru
        Akun akun = new Akun();
        akun.setEmail(akunRequest.getEmail());
        akun.setUsername(akunRequest.getUsername());
        akun.setPassword(passwordEncoder.encode(akunRequest.getPassword())); // Enkripsi password dengan BCrypt
        akun.setStatus(akunRequest.getStatus());

        // Simpan akun ke database
        akunService.createAkun(akun);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Akun '" + akun.getEmail() + "' dengan status '" + akun.getStatus() + "' telah berhasil dibuat");
    }


    // Endpoint Login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Login attempt for: " + loginRequest.getEmail());

        Akun storedAkun = akunService.findAkunByEmail(loginRequest.getEmail());
        if (storedAkun != null) {
            System.out.println("Password dari database: " + storedAkun.getPassword());
            System.out.println("Password yang dimasukkan: " + loginRequest.getPassword());
        }

        // Memeriksa kecocokan password yang terenkripsi
        if (storedAkun != null && passwordEncoder.matches(loginRequest.getPassword(), storedAkun.getPassword())) {
            String token = jwtUtil.generateToken(storedAkun.getEmail(), storedAkun.getUsername());
            return ResponseEntity.ok(new LoginResponse("Selamat Datang " + storedAkun.getUsername(), token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Email atau password salah.", null));
        }
    }


    // Menghapus akun
    @DeleteMapping("/akun/{email}")
    public ResponseEntity<String> deleteAccount(@PathVariable("email") String email,
                                                @RequestHeader("Authorization") String token) {
        // Ambil username/email dari token
        String emailcheck = jwtUtil.extractEmail(token.substring(7)); // Bearer token

        // Verifikasi apakah email di path sesuai dengan yang terlogin
        if (emailcheck == null || !emailcheck.equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email tidak cocok dengan akun yang sedang terlogin.");
        }

        // Cari akun berdasarkan email
        Akun akun = akunService.findAkunByEmail(emailcheck);

        if (akun == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Akun tidak ditemukan.");
        }

        try {
            // Hapus akun
            akunService.deleteAkun(akun);

            // Kirimkan respons sukses
            return ResponseEntity.ok("Akun telah berhasil dihapus");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Terjadi kesalahan saat menghapus akun: " + e.getMessage());
        }
    }


    // Mendapatkan Profil Akun
    @GetMapping("/profil")
    public ResponseEntity<AkunResponse> getProfile(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractEmail(token.substring(7)); // Bearer token
        Akun akun = akunService.findAkunByEmail(email);
        if (akun == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        AkunResponse akunResponse = new AkunResponse(akun.getEmail(), akun.getUsername(), akun.getStatus());
        return ResponseEntity.ok(akunResponse);
    }

    // Update Profil Akun
    @PatchMapping("/profil")
    public ResponseEntity<String> updateProfile(@RequestBody AkunRequest akunRequest, @RequestHeader("Authorization") String token) {
        // Ambil email dari token JWT
        String email = jwtUtil.extractEmail(token.substring(7)); // Mengambil email dari token (Bearer token)

        // Cari akun berdasarkan email yang diambil dari token
        Akun akun = akunService.findAkunByEmail(email);
        if (akun == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Akun tidak ditemukan");
        }

        // Update email dan username
        akun.setEmail(akunRequest.getEmail());
        akun.setUsername(akunRequest.getUsername());

        // Simpan perubahan ke dalam database
        akunService.createAkun(akun); // Perbarui data akun dengan email dan username baru

        // Kirimkan response sukses
        return ResponseEntity.ok("Profil telah berhasil diubah");
    }


    // Endpoint untuk mengganti password
    @PatchMapping("/gantiPassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request, @RequestHeader("Authorization") String token) {
        // Extract email dari token yang ada (Bearer token)
        String email = jwtUtil.extractEmail(token.substring(7)); // Bearer token

        // Cari akun berdasarkan email
        Akun akun = akunService.findAkunByEmail(email);
        if (akun == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email salah");
        }

        // Memeriksa apakah oldPassword sesuai dengan yang ada di database
        if (!passwordEncoder.matches(request.getOldPassword(), akun.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password gagal diubah karena password salah");
        }

        // Update password dengan yang baru
        akun.setPassword(passwordEncoder.encode(request.getNewPassword())); // Enkripsi password baru
        akunService.createAkun(akun); // Simpan perubahan password ke database

        return ResponseEntity.ok("Password telah berhasil diubah");
    }


    // Mendapatkan Semua Pengajuan
    @GetMapping("/pengajuan")
    public ResponseEntity<List<Pengajuan>> getAllPengajuan(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7)); // Bearer token
        Akun akun = akunService.findAkunByUsername(username);

        if (akun == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<Pengajuan> pengajuanList;
        if ("satpam".equals(akun.getStatus())) {
            // Jika akun adalah satpam, tampilkan semua pengajuan
            pengajuanList = pengajuanService.getAllPengajuan();
        } else {
            // Jika akun adalah mahasiswa, hanya tampilkan pengajuan miliknya
            pengajuanList = pengajuanService.getPengajuanByUsername(username);
        }

        return ResponseEntity.ok(pengajuanList);
    }

    @GetMapping("/barang")
    public ResponseEntity<List<Penitipan>> getAllPenitipan(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7)); // Bearer token
        Akun akun = akunService.findAkunByUsername(username);

        if (akun == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<Penitipan> penitipanList;
        if ("satpam".equals(akun.getStatus())) {
            // Jika akun adalah satpam, tampilkan semua data penitipan
            penitipanList = penitipanService.getAllPenitipan();
        } else {
            // Jika akun bukan satpam (misalnya mahasiswa), hanya tampilkan penitipan miliknya
            penitipanList = penitipanService.getPenitipanByUsername(username);
        }

        return ResponseEntity.ok(penitipanList);
    }


    // Menambahkan Pengajuan Barang
    @PostMapping("/barang")
    public ResponseEntity<String> addPengajuan(@RequestBody Pengajuan pengajuan, @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7)); // Bearer token

        if (pengajuan.getId() == null) {
            pengajuan.setId(generateUniqueId()); // Ganti dengan metode yang menghasilkan ID unik
        }

        pengajuan.setUsername(username);
        pengajuanService.savePengajuan(pengajuan); // Save pengajuan

        return ResponseEntity.ok("Pengajuan penitipan barang berhasil ditambahkan.");
    }

    // Mendapatkan Barang Ditolak
    @GetMapping("/barang/tolak")
    public ResponseEntity<List<BarangDitolak>> getBarangDitolak(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7)); // Bearer token
        Akun akun = akunService.findAkunByUsername(username);

        if (akun == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<BarangDitolak> barangDitolakList;

        if ("satpam".equals(akun.getStatus())) {
            // Jika akun adalah satpam, tampilkan semua barang yang ditolak
            barangDitolakList = barangDitolakService.getAllBarangDitolak();
        } else if ("mahasiswa".equals(akun.getStatus())) {
            // Jika akun adalah mahasiswa, hanya tampilkan barang ditolak yang diajukan oleh mahasiswa tersebut
            barangDitolakList = barangDitolakService.getBarangDitolakByUsername(username);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return ResponseEntity.ok(barangDitolakList);
    }


    // Memperbarui Status Barang
    @PatchMapping("/pengajuan")
    public ResponseEntity<String> updateBarangStatus(@RequestBody Pengajuan pengajuan, @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7)); // Bearer token
        Akun akun = akunService.findAkunByUsername(username);
        if (akun == null || !"satpam".equals(akun.getStatus())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Hanya satpam yang dapat memperbarui status barang.");
        }

        Pengajuan existingPengajuan = pengajuanService.getPengajuanById(pengajuan.getId());
        if (existingPengajuan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pengajuan tidak ditemukan.");
        }

        if ("terima".equals(pengajuan.getStatus())) {
            // Barang diterima, dipindahkan ke penitipan
            Penitipan penitipan = new Penitipan();
            penitipan.setUsername(existingPengajuan.getUsername());
            penitipan.setBarang(existingPengajuan.getBarang());
            penitipan.setDeskripsi(existingPengajuan.getDeskripsi());
            penitipan.setBarcode(BarcodeUtil.generateBarcode()); // Menghasilkan barcode
            penitipan.setStatuspenitipan("Dalam Penitipan");
            penitipanService.savePenitipan(penitipan);

            pengajuanService.deletePengajuan(existingPengajuan.getId());
            return ResponseEntity.ok("Barang telah diterima dan dipindahkan ke tabel Penitipan.");
        } else if ("tolak".equals(pengajuan.getStatus())) {
            // Barang ditolak, dipindahkan ke BarangDitolak
            BarangDitolak barangDitolak = new BarangDitolak();
            barangDitolak.setUsername(existingPengajuan.getUsername());
            barangDitolak.setBarang(existingPengajuan.getBarang());
            barangDitolak.setDeskripsi(existingPengajuan.getDeskripsi());
            barangDitolakService.saveBarangDitolak(barangDitolak);

            pengajuanService.deletePengajuan(existingPengajuan.getId());
            return ResponseEntity.ok("Barang telah ditolak dan dipindahkan ke tabel BarangDitolak.");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status barang tidak valid.");
    }

    // Endpoint untuk mengubah status penitipan barang
    @PatchMapping("/barang/{id}")
    public ResponseEntity<String> updateStatusPenitipan(@PathVariable("id") Long id,
                                                        @RequestBody StatusRequest statusRequest,
                                                        @RequestHeader("Authorization") String token) {
        String statuspenitipan = statusRequest.getStatuspenitipan();

        // Extract username dari token
        String username = jwtUtil.extractUsername(token.substring(7)); // Bearer token
        Akun akun = akunService.findAkunByUsername(username);

        // Periksa apakah pengguna memiliki peran 'satpam'
        if (akun == null || !"satpam".equals(akun.getStatus())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Akses ditolak, hanya satpam yang dapat mengubah status.");
        }

        // Validasi status yang diberikan
        if (!"Dalam Penitipan".equals(statuspenitipan) && !"Sudah Dikembalikan".equals(statuspenitipan)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status tidak valid. Pilih antara 'Dalam Penitipan' atau 'Sudah Dikembalikan'.");
        }

        try {
            // Update status penitipan menggunakan service
            penitipanService.updateStatus(id, statuspenitipan);
            return ResponseEntity.ok("Status barang berhasil diubah menjadi " + statuspenitipan + ".");
        } catch (InvalidStatusException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Terjadi kesalahan: " + ex.getMessage());
        }
    }

    // Endpoint untuk menambahkan atau memperbarui alasan penolakan
    @PatchMapping("/barang/tolak")
    public ResponseEntity<String> updateAlasanPenolakan(@RequestBody UpdateAlasanRequest request) {
        BarangDitolak barangDitolak = barangDitolakService.getBarangDitolakById(request.getId());

        // Validasi apakah barang ditemukan
        if (barangDitolak == null) {
            return ResponseEntity.status(404).body("Barang tidak ditemukan.");
        }

        // Update alasan penolakan
        barangDitolak.setAlasan(request.getAlasan());
        barangDitolakService.saveBarangDitolak(barangDitolak); // Simpan perubahan

        return ResponseEntity.ok("Alasan penolakan berhasil diperbarui.");
    }

    // Menghapus Pengajuan Barang
    @DeleteMapping("/pengajuan/{id}")
    public ResponseEntity<String> deletePengajuan(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        // Mengambil username dari token
        String username = jwtUtil.extractUsername(token.substring(7)); // Bearer token

        // Cari akun berdasarkan username
        Akun akun = akunService.findAkunByUsername(username);
        if (akun == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Akun tidak ditemukan.");
        }

        // Jika akun memiliki status 'satpam', maka bisa menghapus pengajuan apapun
        if ("satpam".equals(akun.getStatus())) {
            Pengajuan pengajuan = pengajuanService.getPengajuanById(id);
            if (pengajuan == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pengajuan tidak ditemukan.");
            }

            pengajuanService.deletePengajuan(id);
            return ResponseEntity.ok("Pengajuan telah berhasil dihapus.");
        }

        // Jika akun adalah 'mahasiswa', hanya bisa menghapus pengajuan miliknya sendiri
        else if ("mahasiswa".equals(akun.getStatus())) {
            Pengajuan pengajuan = pengajuanService.getPengajuanById(id);

            // Jika pengajuan tidak ditemukan, beri respons yang sesuai
            if (pengajuan == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pengajuan tidak ditemukan.");
            }

            // Pastikan pengajuan milik pengguna yang sedang login
            if (!pengajuan.getUsername().equals(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Anda hanya dapat menghapus pengajuan Anda sendiri.");
            }

            // Hapus pengajuan
            pengajuanService.deletePengajuan(id);
            return ResponseEntity.ok("Pengajuan Anda telah berhasil dihapus.");
        }

        // Jika status akun tidak dikenali
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Akses ditolak.");
    }

    @DeleteMapping("/barang/{id}")
    public ResponseEntity<String> deletePenitipan(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        // Ambil username dari token JWT
        String username = jwtUtil.extractUsername(token.substring(7)); // Mengambil username dari token (Bearer token)
        Akun akun = akunService.findAkunByUsername(username);

        // Validasi apakah akun memiliki peran 'satpam'
        if (akun == null || !"satpam".equals(akun.getStatus())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Akses ditolak, hanya satpam yang dapat menghapus barang.");
        }

        // Cari barang penitipan berdasarkan ID
        Penitipan penitipan = penitipanService.getPenitipanById(id);
        if (penitipan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Barang penitipan tidak ditemukan.");
        }

        // Hapus barang penitipan
        penitipanService.deletePenitipan(id);

        // Kirimkan response sukses
        return ResponseEntity.ok("Barang penitipan telah berhasil dihapus.");
    }

    // Tambahkan endpoint DELETE baru untuk menghapus barang dari tabel barang_ditolak
    @DeleteMapping("/barang/tolak/{id}")
    public ResponseEntity<String> deleteBarangDitolak(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        // Extract username dari token
        String username = jwtUtil.extractUsername(token.substring(7)); // Bearer token
        Akun akun = akunService.findAkunByUsername(username);

        // Periksa apakah akun ada dan apakah akun tersebut adalah 'satpam'
        if (akun == null || !"satpam".equals(akun.getStatus())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Akses ditolak, hanya satpam yang dapat menghapus barang.");
        }

        // Cari barang yang ditolak berdasarkan ID
        BarangDitolak barangDitolak = barangDitolakService.getBarangDitolakById(id);
        if (barangDitolak == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Barang yang ditolak tidak ditemukan.");
        }

        try {
            // Hapus barang yang ditolak dari database
            barangDitolakService.deleteBarangDitolak(id);
            return ResponseEntity.ok("Barang ditolak telah berhasil dihapus.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Terjadi kesalahan saat menghapus barang ditolak: " + e.getMessage());
        }
    }
}