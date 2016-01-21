package org.luksze.glassmill;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CipherTest {

    @Test
    public void checkCipherBasicFunctionality() throws Exception {
        //given
        Cipher cipher = new Cipher();

        //when
        byte[] encrypted = cipher.encrypt("Test".getBytes(), "password");

        //then
        assertTrue(encrypted.length > 0);
        assertNotEquals("Test", Arrays.toString(encrypted));

        //when
        byte[] decrypted = cipher.decrypt(encrypted, "password");
        assertEquals("Test", new String(decrypted));

    }
}