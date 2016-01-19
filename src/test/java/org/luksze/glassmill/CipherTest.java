package org.luksze.glassmill;

import org.junit.Assert;
import org.junit.Test;

public class CipherTest {
    @Test
    public void canCreateTest() throws Exception {
        Cipher cipher = new Cipher();
        Assert.assertNotNull(cipher);
    }
}
