package org.luksze.cryptme;

import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import java.io.*;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.lang.Boolean.TRUE;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AppCipherStreamTest {

    private static final String DESTINATION_FILE = "crypted-by-stream-test";
    static final String EXPECTED_MD5 = "684fba1d1a12de5f03fdea034fdeb468";

    @Test
    public void fileEncryptionCanBeDoneUsingCipherInputStream() throws Exception {
        //given
        Cipher cipher = new AppCipher().constructEncryptCipher("password");
        Path loremPath = fileToBeEncrypted();

        //when
        cipherIsUsedToEncryptFile(cipher, loremPath);

        //then
        assertThat(fileHasBeenCreated(loremPath.getParent()), is(TRUE));
        assertThat(md5(pathToDestinationFile(loremPath)), is(EXPECTED_MD5));
    }

    private Path pathToDestinationFile(Path loremPath) {
        return loremPath.getParent().resolve(DESTINATION_FILE);
    }

    static String md5(Path resolve) throws NoSuchAlgorithmException, IOException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return new BigInteger(1, md5.digest(readAllBytes(resolve))).toString(16);
    }

    private boolean fileHasBeenCreated(Path parent) {
        return parent.resolve(DESTINATION_FILE).toFile().exists();
    }

    private void cipherIsUsedToEncryptFile(Cipher cipher, Path loremPath) throws IOException {
        FileOutputStream out = new FileOutputStream(new File(loremPath.getParent().toFile(), DESTINATION_FILE));
        CipherInputStream inputStream = new CipherInputStream(new FileInputStream(loremPath.toFile()), cipher);
        copy(out, inputStream);
        inputStream.close();
        out.close();
    }

    private void copy(FileOutputStream out, CipherInputStream cipherInputStream) throws IOException {
        byte[] bytes = new byte[512];
        int readInteger;
        while ((readInteger = cipherInputStream.read(bytes)) != -1) {
            out.write(bytes, 0, readInteger);
        }
    }

    private Path fileToBeEncrypted() throws URISyntaxException {
        URL resource = AppCipherStreamTest.class.getClassLoader().getResource("loremIpsumOriginal.txt");
        if (resource != null) {
            return get(resource.toURI());
        } else {
            throw new IllegalStateException();
        }
    }
}
