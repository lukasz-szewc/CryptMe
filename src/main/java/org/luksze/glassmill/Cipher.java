package org.luksze.glassmill;

import static java.util.Arrays.copyOf;

public class Cipher {
    private byte[] bytes;

    public void encrypt(byte[] bytes, String secretPassword) {
        this.bytes = bytes;
    }

    byte[] bytesToEncrypt() {
        return copyOf(bytes, bytes.length);
    }

    public byte[] bytesAfterEncryption() {
        return new byte[1];
    }
}
