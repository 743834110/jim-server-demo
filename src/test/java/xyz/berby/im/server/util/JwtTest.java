package xyz.berby.im.server.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import io.jsonwebtoken.*;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

public class JwtTest {

    @Test
    public void test() {
        Date createDate = new Date();

        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.DATE, 10);
        Date expiresDate = nowTime.getTime();

        Claims claims = Jwts.claims();
        claims.put("name", "cy");
        RSA rsa = SecureUtil.rsa();
        long a = System.currentTimeMillis();
        String token = Jwts.builder()
                .setClaims(claims)
                .setId("11")
                .setExpiration(expiresDate)
                .setIssuedAt(createDate)
                .signWith(SignatureAlgorithm.RS256, "bb".getBytes())
                .compact();
        System.out.println(token);
        System.out.println("用时:" + (System.currentTimeMillis() - a) + "ms");

        Jws<Claims> jws = Jwts.parser().setSigningKey("bb".getBytes()).parseClaimsJws(token);
        System.out.println(jws.getBody().get("name"));

    }
}
