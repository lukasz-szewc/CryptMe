package org.luksze.glassmill;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

public class CipherTest {

    public static final byte[] BYTES = "PlainText".getBytes();

    @Test
    public void canCreateCipherInstance() throws Exception {
        Cipher cipher = new Cipher();
        assertNotNull(cipher);
    }

    @Test
    public void canTakeByteArrayForEncryption() throws Exception {
        Cipher cipher = new Cipher();
        cipher.encrypt(BYTES, "secretPassword");
        assertArrayEquals(cipher.bytesToEncrypt(), BYTES);
    }

    @Test
    public void canExpectEncryptedByteArray() throws Exception {
        Cipher cipher = new Cipher();
        cipher.encrypt(BYTES, "secretPassword");
        assertNotNull(cipher.bytesAfterEncryption());
    }
}
