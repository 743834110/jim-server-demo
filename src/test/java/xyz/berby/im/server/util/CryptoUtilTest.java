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

    private String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMIBQU0Ka6tFUJ+DpOSgxYT41kYyx6Nua2VSJ/XJLYIqzs3hzr8B1H6w77WHMmHL0CX6LmUDiAjpl1UQ+36PB86oDGsSirf/1jTA0cLpEGVy9xXq1VgtCRuX4i71fd6AvzIcBhrBI+haFqjJGhwaqGXeEBAjEw8eSt0sy4TJscLtAgMBAAECgYAF0d/RndRpCOVIQ7y4lxIV0Rksde2DO5bBLVvBhc11sC8QAgwfssBbJJesTGf4gxK1cPHfWeHE0q0VgjLpMwU64mww0N6UCG1pC4+x/2wfHbSU9pgRyzrsAb1/+3uijlCn/WsaEwzNqR5oLjBb96txKHLnZR1LFGS2X/XTAo6syQJBAO8qWopjyKDk0cnIhgX+AYGohhsqL1+Qi+2JYq5spNV9VyTB8KHHtg3ifFER1TUgXcottC4B3pEejkXq7pSqudcCQQDPqSFSUpG/HPJVeYoWnvN151hyj8OWbXZRWFAvrbyrnsnEmelBn0tPD0jHIcdIkhSXpwCLgSXqLDVK2v3OuHjbAkEA5YNTl/LiGeT8nhPc6es0LuU2rOLyy1a/sZhiJtkD5gx+kDu9XMN2piRvAK2IWY9R8i+h9XS9za3xAo/NnMWpyQJAR5qkGMTL6MBMd1ivWtl4TaF2CBq2cHpSH/55t9cKhYr9O8NugOgyiZJVoARvuQBWBWzabUAdGMAhqCwW9F77nQJBAMf7WSFTWHyYAz8Z/ZcVa2z/dsT+uR/d40O0vJfvFtPCnd5/w9o4gt2aZ76FRQ4ZibO0GwFgt5UxHXda42hYaLU=";


    @Test
    public void testCrypto() throws InvalidKeyException, IOException {
        AsymmetricCrypto crypto = SecureUtil.rsa(this.privateKey, null);
        System.out.println(crypto.encryptBase64("a", KeyType.PrivateKey));

        long a = System.currentTimeMillis();

        RSA rsa = SecureUtil.rsa();
        rsa.encryptStr("{system:ddd}", KeyType.PublicKey);
        System.out.println(System.currentTimeMillis() - a);
    }
}
