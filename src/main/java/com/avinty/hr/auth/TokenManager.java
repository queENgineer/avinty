package com.avinty.hr.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenManager {
    private static final int validity=5 * 60 * 1000;
    Key key= Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username){
        Long nowLong=System.currentTimeMillis();

        return Jwts.builder().setSubject(username)
                .setIssuer("com.avinty.hr")
                .setIssuedAt(new Date(nowLong))
                .setExpiration(new Date(nowLong+validity))
                .signWith(key)
                .compact();
    }

   public boolean tokenValidate(String token){
        if(getUsernameToken(token)!=null && isNotExpired(token)){
            return true;
        }
        return false;
    }

    public String getUsernameToken(String token){
        Claims claims = getClaims(token);
        return  claims.getSubject()
            ;
    }

    public boolean isNotExpired(String token){
        Claims claims = getClaims(token);
        return  claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }


}
