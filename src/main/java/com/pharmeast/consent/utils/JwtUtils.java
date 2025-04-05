package com.pharmeast.consent.utils;

@org.springframework.stereotype.Component
public class JwtUtils {

    private static final String SECRET_KEY
        = "00dc0f84f0cb815cb312cb202d74b90bb6d6aa4766c1877c711dc1116402c8ec3bbda3711e9545700639b8099418b272e3e79dcf463220e481391b221bed3818";


    public String generateToken(String username, String role) {
        // Decode the base64-encoded secret key
        byte[] keyBytes = java.util.Base64.getDecoder().decode(SECRET_KEY);
        java.security.Key key = io.jsonwebtoken.security.Keys.hmacShaKeyFor(
            keyBytes);

        return io.jsonwebtoken.Jwts.builder().claims().subject(username)
                                   .issuedAt(new java.util.Date()).expiration(
                new java.util.Date(System.currentTimeMillis() +
                                   1000 * 60 * 60 * 10)) // 10 hours validity
                                   .and().claim("role", role).signWith(key)
                                   .compact();
    }

    public boolean validateToken(
        String token,
        org.springframework.security.core.userdetails.UserDetails userDetails
    ) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(
            token);
    }

    public String extractUsername(String token) {
        return extractClaim(token, io.jsonwebtoken.Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(
            token, io.jsonwebtoken.Claims::getExpiration).before(
            new java.util.Date());
    }

    public <T> T extractClaim(
        String token,
        java.util.function.Function<io.jsonwebtoken.Claims, T> claimsResolver
    ) {
        // Decode the base64-encoded secret key
        byte[] keyBytes = io.jsonwebtoken.io.Decoders.BASE64.decode(SECRET_KEY);
        javax.crypto.SecretKey secretKey
            = io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes);

        final io.jsonwebtoken.Claims claims = io.jsonwebtoken.Jwts.parser()
                                                                  .verifyWith(
                                                                      secretKey)
                                                                  .build()
                                                                  .parseSignedClaims(
                                                                      token)
                                                                  .getPayload();
        return claimsResolver.apply(claims);
    }
}
