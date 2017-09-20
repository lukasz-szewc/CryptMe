package org.luksze.cryptme;

import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import static java.lang.Boolean.TRUE;
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
        assertThat(MdFiveTest.md5(pathToDestinationFile(loremPath)), is(EXPECTED_MD5));
    }

    @Test
    public void fileEncryptionCanBeDoneUsingCipherAndTailoredInputStream() throws Exception {
        //given
        Cipher cipher = new AppCipher().constructEncryptCipher("password");
        Path loremPath = fileToBeEncrypted();

        //when
        cipherAndTailoredStreamIsUsedToEncryptFile(cipher, loremPath);

        //then
        assertThat(fileHasBeenCreated(loremPath.getParent()), is(TRUE));
        assertThat(MdFiveTest.md5(pathToDestinationFile(loremPath)), is(EXPECTED_MD5));
    }

    private Path pathToDestinationFile(Path loremPath) {
        return loremPath.getParent().resolve(DESTINATION_FILE);
    }

    private boolean fileHasBeenCreated(Path parent) {
        return parent.resolve(DESTINATION_FILE).toFile().exists();
    }

    private void cipherAndTailoredStreamIsUsedToEncryptFile(Cipher cipher, Path loremPath) throws IOException {
        PaddingAwareCipherOutputStream outputSteam = new PaddingAwareCipherOutputStream(constructFileOutputStream(loremPath), cipher);
        FileInputStream inputStream = constructInputStream(loremPath);
        copy(inputStream, outputSteam);
        inputStream.close();
        outputSteam.close();
    }

    private void cipherIsUsedToEncryptFile(Cipher cipher, Path loremPath) throws IOException {
        FileOutputStream out = constructFileOutputStream(loremPath);
        CipherInputStream inputStream = new CipherInputStream(constructInputStream(loremPath), cipher);
        copy(inputStream, out);
        inputStream.close();
        out.close();
    }

    private FileInputStream constructInputStream(Path loremPath) throws FileNotFoundException {
        return new FileInputStream(loremPath.toFile());
    }

    private FileOutputStream constructFileOutputStream(Path loremPath) throws FileNotFoundException {
        return new FileOutputStream(new File(loremPath.getParent().toFile(), DESTINATION_FILE));
    }

    private void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bytes = new byte[512];
        int readInteger;
        while ((readInteger = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, readInteger);
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
