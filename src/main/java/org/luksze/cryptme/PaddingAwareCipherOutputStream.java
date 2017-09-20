package org.luksze.cryptme;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PaddingAwareCipherOutputStream extends FilterOutputStream {
    private final Cipher cipher;

    public PaddingAwareCipherOutputStream(OutputStream out, Cipher cipher) {
        super(out);
        this.cipher = cipher;
    }

    @Override
    public void write(int b) throws IOException {
        byte[] bytes = new byte[1];
        bytes[0] = (byte) b;
        write(bytes, 0, 1);
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        byte[] update = cipher.update(b, off, len);
        out.write(update, 0, update.length);
        if (update.length != len) {
            byte[] bytes = doFinal();
            out.write(bytes);
        }
    }

    private byte[] doFinal() {
        try {
            return cipher.doFinal();
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalStateException(e);
        }
    }
}
