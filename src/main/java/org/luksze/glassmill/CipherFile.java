package org.luksze.glassmill;

import java.nio.file.Files;
import java.nio.file.Path;

public class CipherFile {

    public void encrypt(Path source, Path destination, String password) {
        validateInput(source, destination, password);
        try {
            Cipher cipher = new Cipher();
            byte[] inputFileBytes = Files.readAllBytes(source);
            byte[] encryptedBytes = cipher.encrypt(inputFileBytes, password);
            Files.write(destination, encryptedBytes);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void decrypt(Path source, Path destination, String password) {
        validateInput(source, destination, password);
        try {
            Cipher cipher = new Cipher();
            byte[] inputFileBytes = Files.readAllBytes(source);
            byte[] decryptedBytes = cipher.decrypt(inputFileBytes, password);
            Files.write(destination, decryptedBytes);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private void validateInput(@SuppressWarnings("unused") Path source,
                               @SuppressWarnings("unused") Path destination,
                               @SuppressWarnings("unused") String password) {

    }
}
