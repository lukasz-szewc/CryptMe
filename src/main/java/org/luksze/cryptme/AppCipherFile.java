package org.luksze.cryptme;

import java.nio.file.Files;
import java.nio.file.Path;

public class AppCipherFile {

    public void encrypt(Path source, Path destination, String password) {
        try {
            AppCipher appCipher = new AppCipher();
            byte[] inputFileBytes = Files.readAllBytes(source);
            byte[] encryptedBytes = appCipher.encrypt(inputFileBytes, password);
            Files.write(destination, encryptedBytes);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void decrypt(Path source, Path destination, String password) {
        try {
            AppCipher appCipher = new AppCipher();
            byte[] inputFileBytes = Files.readAllBytes(source);
            byte[] decryptedBytes = appCipher.decrypt(inputFileBytes, password);
            Files.write(destination, decryptedBytes);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
