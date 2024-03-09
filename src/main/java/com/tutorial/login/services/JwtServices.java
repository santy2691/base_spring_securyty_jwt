package com.tutorial.login.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.tutorial.login.bean.UsuarioBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServices {

    @Value("${jwt.secret}")
    //private static String SECRET_KEY = "24432646294A404E635266546A576E5A7234753778214125442A472D4B615064";
    private String SECRET_KEY;


    
    
    /**
     * funcion para extraer el email del token
     * @param token String 
     * @return email de usuario del token
     */
    public String extractUserName(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }


    public String generateToken(UsuarioBean userDetails) {
        HashMap<String,Object> claim = new HashMap<>();
        claim.put("id_usuario", userDetails.getId());
        return generateToken(claim,(UserDetails)userDetails);
    }


    public String generateToken(Map<String, Object> extraClaim, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaim)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24)).
                signWith(getSignInKey(),SignatureAlgorithm.HS256)
                .compact();
    }
    

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
      }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Long extractIdUser(String token) {
        Claims claims =  this.extractAClaims(token);
        Long idUser = claims.get("id_usuario",Long.class);
        return idUser;
    }


    /**
     * funcion para extraer los elementos del token 
     * @param <T>
     * @param token
     * @param claimsResolver
     * @return
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAClaims(token);
        return claimsResolver.apply(claims);
      }

    private Claims extractAClaims(String token) {
        return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    }


    private Key getSignInKey() {
        byte[] keyByte = Decoders.BASE64.decode(this.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }
}

