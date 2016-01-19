package org.luksze.glassmill;

import org.junit.Assert;
import org.junit.Test;

public class CipherTest {

    public static final byte[] BYTES = "PlainText".getBytes();

    @Test
    public void canCreateTest() throws Exception {
        Cipher cipher = new Cipher();
        Assert.assertNotNull(cipher);
    }

    @Test
    public void canTakeByteArrayForEncryption() throws Exception {
        Cipher cipher = new Cipher();
        cipher.encrypt(BYTES, "secretPassword");
        Assert.assertArrayEquals(cipher.bytesToEncrypt(), BYTES);
    }
}
