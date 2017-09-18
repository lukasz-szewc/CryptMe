package org.luksze.cryptme;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Path;

import static java.nio.file.Files.exists;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.luksze.cryptme.AppCipherStreamTest.EXPECTED_MD5;
import static org.luksze.cryptme.MdFiveTest.md5;

public class AppCipherFileTest {

    private Path sourcePath;
    private Path encryptedFilePath;
    private Path decryptedFilePath;

    @Test
    public void testFileEncryption() throws Exception {
        //given
        AppCipherFile appCipherFile = new AppCipherFile();

        //when
        appCipherFile.encrypt(sourcePath, encryptedFilePath, "password");

        //then
        assertTrue(exists(encryptedFilePath));
        assertThat(md5(encryptedFilePath), is(EXPECTED_MD5));

        //when
        appCipherFile.decrypt(encryptedFilePath, decryptedFilePath, "password");

        //then
        assertTrue(exists(decryptedFilePath));
        assertArrayEquals(readAllBytes(decryptedFilePath), readAllBytes(sourcePath));
    }

    @Before
    public void setUp() throws Exception {
        URL resource = getClass().getClassLoader().getResource("loremIpsumOriginal.txt");
        if (resource != null) {
            sourcePath = get(resource.toURI());
        }
        URL destination = getClass().getClassLoader().getResource(".");
        if (destination != null) {
            encryptedFilePath = get(destination.toURI()).resolve("loremIpsumEncrypted.txt");
            decryptedFilePath = get(destination.toURI()).resolve("loremIpsumDecrypted.txt");
        }

        Assume.assumeNotNull(sourcePath);
        Assume.assumeNotNull(encryptedFilePath);
        Assume.assumeNotNull(decryptedFilePath);
    }
}