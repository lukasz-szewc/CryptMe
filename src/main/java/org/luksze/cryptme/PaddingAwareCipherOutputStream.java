package org.luksze.cryptme;

import javax.crypto.Cipher;
import java.io.FileOutputStream;
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

    }
}
