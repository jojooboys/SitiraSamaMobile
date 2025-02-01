package com.polstat.penitipanbarang.dto;

public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;

    // Getter dan Setter
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

