package com.polstat.penitipanbarang.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private static final Logger LOGGER = Logger.getLogger(JwtUtil.class.getName());

    public String generateToken(String email, String username) {
        return Jwts.builder()
                .setSubject(email) // Email sebagai subject utama
                .claim("username", username) // Tambahkan username sebagai klaim tambahan
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOGGER.severe("Failed to extract claims from token: " + e.getMessage());
            throw e; // Re-throw or handle as needed
        }
    }

    public String extractUsername(String token) {
        try {
            return extractClaims(token).get("username", String.class); // Ekstrak username dari klaim
        } catch (Exception e) {
            LOGGER.severe("Failed to extract username from token: " + e.getMessage());
            return null;
        }
    }

    public String extractEmail(String token) {
        try {
            return extractClaims(token).getSubject(); // Ekstrak email dari subject
        } catch (Exception e) {
            LOGGER.severe("Failed to extract email from token: " + e.getMessage());
            return null;
        }
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    // Mengambil role dari token
    public boolean hasRole(String username, String role) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(username)
                .getBody();

        // Pastikan claims menyimpan role pengguna
        return claims.get("role").equals(role);
    }
}
