package org.luksze.glassmill;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import java.security.NoSuchAlgorithmException;

import static java.util.Arrays.copyOf;

public class GlassMill {
    private byte[] bytes;
    private final Cipher cipher;

    public GlassMill() {
        cipher = createCipher();
    }

    private Cipher createCipher() {
        try {
            return Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalStateException("Error creating cipher", e);
        }
    }

    public void encrypt(byte[] bytes, String secretPassword) {
        this.bytes = bytes;
    }

    byte[] bytesToEncrypt() {
        return copyOf(bytes, bytes.length);
    }

    public byte[] bytesAfterEncryption() {
        return new byte[1];
    }

    Cipher cipher() {
        return cipher;
    }
}
