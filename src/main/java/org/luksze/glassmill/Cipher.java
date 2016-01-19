package org.luksze.glassmill;

import java.util.Arrays;

public class Cipher {
    private byte[] bytes;

    public void encrypt(byte[] bytes, String secretPassword) {
        this.bytes = bytes;
    }

    byte[] bytesToEncrypt() {
        return Arrays.copyOf(bytes, bytes.length);
    }
}
