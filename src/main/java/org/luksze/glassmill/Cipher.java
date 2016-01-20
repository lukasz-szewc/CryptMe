package org.luksze.glassmill;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import static javax.crypto.Cipher.ENCRYPT_MODE;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.getInstance;

public class Cipher {
    private static final byte[] DEFAULT_SALT = {
            (byte) 0xA4, (byte) 0x0B, (byte) 0xC8, (byte) 0x34,
            (byte) 0xD6, (byte) 0x95, (byte) 0xF3, (byte) 0x13
    };

    private static final byte[] DEFAULT_INITIAL_VECTOR = {
            (byte) 0x21, (byte) 0x56, (byte) 0x8C, (byte) 0x84,
            (byte) 0xA2, (byte) 0xB3, (byte) 0xFD, (byte) 0x31
    };

    private static final int DEFAULT_SIZE = 128;
    private static final int DEFAULT_ITERATION_COUNT = 1024;

    private final int size;
    private final byte[] salt;
    private final byte[] initialVector;
    private int iterationCount;

    public Cipher() {
        initialVector = DEFAULT_INITIAL_VECTOR;
        salt = DEFAULT_SALT;
        size = DEFAULT_SIZE;
        iterationCount = DEFAULT_ITERATION_COUNT;
    }

    public byte[] encrypt(byte[] bytes, String password) {
        javax.crypto.Cipher aes = cipherInstance(ENCRYPT_MODE, secretKeySpec(password));
        return execute(bytes, aes);
    }

    public byte[] decrypt(byte[] bytes, String password){
        javax.crypto.Cipher cipher = cipherInstance(DECRYPT_MODE, secretKeySpec(password));
        return execute(bytes, cipher);
    }

    private SecretKeySpec secretKeySpec(String password) {
        SecretKeyFactory secretKeyFactory = secretKeyFactory();
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, size);
        byte[] encoded = generateSecretKey(secretKeyFactory, keySpec).getEncoded();
        return new SecretKeySpec(encoded, "AES");
    }

    private byte[] execute(byte[] bytes, javax.crypto.Cipher cipher) {
        try {
            return cipher.doFinal(bytes);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("General security exception", e);
        }
    }

    private SecretKey generateSecretKey(SecretKeyFactory secretKeyFactory, KeySpec keySpec) {
        try {
            return secretKeyFactory.generateSecret(keySpec);
        } catch (InvalidKeySpecException e) {
            throw new IllegalStateException("Invalid key passed into secret key factory", e);
        }
    }

    private SecretKeyFactory secretKeyFactory() {
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Cannot create secret key factory", e);
        }
    }

    private javax.crypto.Cipher cipherInstance(int mode, SecretKey secret) {
        try {
            javax.crypto.Cipher cipher = getInstance("AES/CBC/PKCS5Padding");
            cipher.init(mode, secret, new IvParameterSpec(initialVector));
            return cipher;
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("General security exception", e);
        }
    }
}
