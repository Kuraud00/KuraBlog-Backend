package kura.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String SECRET_STRING;

    @Value("${jwt.access-token-expire}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expire}")
    private long refreshTokenExpiration;

    /**
     * 生成Access令牌
     * @param claims
     * @return
     */
    public String generateAccessToken(Map<String,Object> claims){
        SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());
        return Jwts.builder()
                .issuer("KuraBlog")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(accessTokenExpiration, ChronoUnit.MINUTES)))
                .claims(claims)
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * 生成Refresh令牌
     * @param claims
     * @return
     */
    public String generateRefreshToken(Map<String,Object> claims){
        SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());
        return Jwts.builder()
                .issuer("KuraBlog")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(refreshTokenExpiration, ChronoUnit.HOURS)))
                .claims(claims)
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * 解析AccessJWT令牌
     * @param token
     * @return
     */
    public Claims parseJWT(String token){
        SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * JWT令牌是否依然有效
     * @param token
     * @return
     */
    public boolean isJWTAvailable(String token){
        try{
            parseJWT(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public Integer getIdFromToken(String token){
        return (Integer) parseJWT(token).get("id");
    }

}
