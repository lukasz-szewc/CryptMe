package org.luksze.cryptme;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class AppCipherTest {

    @Test
    public void checkCipherBasicFunctionality() throws Exception {
        //given
        AppCipher appCipher = new AppCipher();

        //when
        byte[] encrypted = appCipher.encrypt("Test".getBytes(), "password");

        //then
        assertTrue(encrypted.length > 0);
        assertNotEquals("Test", Arrays.toString(encrypted));

        //when
        byte[] decrypted = appCipher.decrypt(encrypted, "password");
        assertEquals("Test", new String(decrypted));

    }
}