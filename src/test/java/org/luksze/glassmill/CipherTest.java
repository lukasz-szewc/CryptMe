package org.luksze.glassmill;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CipherTest {

    @Test
    public void testName() throws Exception {
        Cipher cipher = new Cipher();

        for (int i = 0; i < 10; i++) {
            byte[] passwords = cipher.encrypt("Test".getBytes(), "password");
            System.out.println(Arrays.toString(passwords));

            byte[] passwords1 = cipher.decrypt(passwords, "password");
            System.out.println(new String(passwords1));
        }


    }
}