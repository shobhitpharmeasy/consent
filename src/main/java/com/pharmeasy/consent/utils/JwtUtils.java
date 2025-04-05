package com.pharmeasy.consent.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

    private static final String SECRET_KEY
        = "00dc0f84f0cb815cb312cb202d74b90bb6d6aa4766c1877c711dc1116402c8ec3bbda3711e9545700639b8099418b272e3e79dcf463220e481391b221bed3818";

    public String generateToken(String username, String role) {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder().claims().subject(username).issuedAt(new Date()).expiration(
                       new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                   .and().claim("role", role).signWith(key).compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);

        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();

        return claimsResolver.apply(claims);
    }
}
