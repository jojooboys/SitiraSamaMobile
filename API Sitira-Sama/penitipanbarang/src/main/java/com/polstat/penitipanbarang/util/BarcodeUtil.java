package com.polstat.penitipanbarang.util;

import java.util.UUID;

public class BarcodeUtil {

    public static String generateBarcode() {
        return UUID.randomUUID().toString();
    }

    public static String generateIdBarang() {
        return "ID-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
