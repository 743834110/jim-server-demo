package xyz.berby.im.server.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.AsymmetricCrypto;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import com.jfinal.kit.AesKit;
import com.jfinal.kit.Base64Kit;
import com.jfinal.kit.HashKit;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.rsa.RSAPrivateCrtKeyImpl;
import sun.security.rsa.RSAPrivateKeyImpl;
import sun.security.util.DerValue;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;

/**
 * Created by Administrator on 2018/10/27.
 */
public class CryptoUtilTest {


    @Test
    public void testCrypto() throws InvalidKeyException, IOException {
        RSA rsa = SecureUtil.rsa();
        System.out.println(rsa.getPrivateKeyBase64());
        System.out.println(rsa.getPublicKeyBase64());
    }
}
