package org.luksze.cryptme;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MdFiveTest {

    private static final String EXPECTED_MD5_FOR_LOREM = "dcb4155e2beb85a1f17e4dcb4379d42f";

    @Test
    public void assertCorrectMd5Calculation() throws Exception {
        //given
        Path path = pathToLorem();

        //when
        String md5 = md5(path);

        //then
        assertThat(md5, is(EXPECTED_MD5_FOR_LOREM));
    }

    private Path pathToLorem() throws URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("loremIpsumOriginal.txt");
        if (resource != null) {
            return get(resource.toURI());
        } else {
            throw new IllegalStateException();
        }
    }

    static String md5(Path resolve) throws NoSuchAlgorithmException, IOException {
        return DigestUtils.md5Hex(readAllBytes(resolve));
    }
}
