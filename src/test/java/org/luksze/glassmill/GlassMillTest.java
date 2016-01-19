package org.luksze.glassmill;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

public class GlassMillTest {

    public static final byte[] BYTES = "PlainText".getBytes();

    @Test
    public void canTakeByteArrayForEncryption() throws Exception {
        GlassMill glassMill = new GlassMill();
        glassMill.encrypt(BYTES, "secretPassword");
        assertArrayEquals(glassMill.bytesToEncrypt(), BYTES);
    }

    @Test
    public void canExpectEncryptedByteArray() throws Exception {
        GlassMill glassMill = new GlassMill();
        glassMill.encrypt(BYTES, "secretPassword");
        assertNotNull(glassMill.bytesAfterEncryption());
    }
}
