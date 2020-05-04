package cn.edu.buaa.se.bhac.Utils;

import cn.edu.buaa.se.bhac.Dao.entity.BhacUser;
import cn.edu.buaa.se.bhac.config.JWTConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.sql.Date;

public class JWTUtils {

    public static String createToken(BhacUser user) {
        String token = Jwts.builder().claim("uid", user.getId())
                .setExpiration(new Date(System.currentTimeMillis() + JWTConfig.expirationTime))
                .signWith(SignatureAlgorithm.HS256, JWTConfig.secretKey)
                .compact();
        return token;
    }

}
