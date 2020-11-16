package com.luck.dataService.manager;

import com.luck.dataEntity.TokenApp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenManagerApp implements Serializable {
    private static final long serialVersionUID = -3301605591108950415L;

    static final String OPENID = "openid";
    static final String SESSION_KEY= "session_key";
    static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret:dfc438ec1d5c4cc50ea6796352edc7f5}")
    private String secret;

    private Long expiration;


    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
    public String generateToken(TokenApp TokenApp, Long expirationSeconds) {
        expiration = expirationSeconds;
        Map<String, Object> claims = new HashMap<>();
        claims.put(OPENID, TokenApp.getOpenId());
        claims.put(SESSION_KEY, TokenApp.getSessionkey());
        claims.put(CLAIM_KEY_CREATED, LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        return generateToken(claims);
    }

    String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    public TokenApp getTokenUserFromToken(String token) {
        final Claims claims = getClaimsFromToken(token);
        Object id = claims.get(OPENID);
        String openid = (String) id;
        TokenApp tokenApp = new TokenApp();
        tokenApp.setOpenId(openid);
        tokenApp.setSessionkey((String) claims.get(SESSION_KEY));
        tokenApp.setToken(token);
        return tokenApp;
    }
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
