package com.polstat.penitipanbarang.dto;

public class AkunResponse {

    private String email;
    private String username;
    private String status;

    public AkunResponse(String email, String username, String status) {
        this.email = email;
        this.username = username;
        this.status = status;
    }

    // Getter dan Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
