package kakeibo_app_java.kakeibo_app_java.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKeyString;
    @Value("${jwt.expiration}")
    private long validityInMilliseconds;
    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    public String createToken(String username){
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean validateToken(String token){
        try{
            System.out.println(token);
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        }catch(JwtException | IllegalArgumentException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public String getUseremail(String token){
        Claims claims = Jwts.parserBuilder()
                        .setSigningKey(getSigningKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
        return claims.getSubject();
    }
}
