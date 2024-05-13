package com.encrypt.hospital.util;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

import java.nio.charset.StandardCharsets;
import java.util.Formatter;

public class HMACSM3 {
    private static final byte[] key = "hospital".getBytes(StandardCharsets.UTF_8);

    public static String generateHmacSm3(String data) {
        HMac hMac = new HMac(new SM3Digest());
        hMac.init(new KeyParameter(key));
        byte[] result = new byte[hMac.getMacSize()];
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        hMac.update(bytes, 0, bytes.length);
        hMac.doFinal(result, 0);
        return toHexString(result);
    }

    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        formatter.close();
        return sb.toString();
    }

    public static boolean verifyHmacSm3(String data, String hmac) {
        String calculatedHmac = generateHmacSm3(data);
        return calculatedHmac.equalsIgnoreCase(hmac);
    }
}
