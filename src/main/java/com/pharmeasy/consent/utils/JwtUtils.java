package com.pharmeasy.consent.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtils {

    private static final String SECRET_KEY = Constants.SECRET_KEY;

    protected JwtUtils() {
        log.debug("JwtUtils loaded - constructor is protected");
    }

    /**
     * Sanitizes the raw token string (trims and removes 'Bearer ' prefix if present).
     */
    public static String sanitizeToken(final String tokenRaw) {
        if (tokenRaw == null || tokenRaw.trim().isEmpty()) {
            throw new IllegalArgumentException("JWT token is missing or empty");
        }

        String token = tokenRaw.trim();
        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
        }

        if (token.contains(" ")) {
            throw new IllegalArgumentException("Invalid JWT: contains whitespace");
        }

        return token;
    }

    /**
     * Generates a JWT token for the given employee email.
     */
    public String generateToken(final String employeeEmail) {
        log.info("Generating JWT for user: {}", employeeEmail);

        return Jwts.builder().claims().subject(employeeEmail).issuedAt(new Date()).expiration(
                       new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                   .and().signWith(getSigningKey()).compact();
    }

    /**
     * Returns the signing key for generating JWTs.
     */
    private Key getSigningKey() {
        return getSecretKey();
    }

    /**
     * Returns the signing key for JWT signature verification.
     */
    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Validates token against the provided email.
     */
    public boolean validateToken(final String tokenRaw, final String employeeEmail) {
        final String token = sanitizeToken(tokenRaw);
//        return true;

        final String tokenEmail = extractClaim(token, Claims::getSubject);
        if (tokenEmail == null || tokenEmail.isEmpty()) {
            log.warn("Token email is null or empty");
            return false;
        }
        final boolean isValid = tokenEmail.equals(employeeEmail) && isTokenExpired(token);

        log.info("Validating JWT for user: {} — Valid: {}", employeeEmail, isValid);
        return isValid;
    }

    /**
     * Extracts a claim using the provided claims resolver.
     */
    public <T> T extractClaim(final String tokenRaw, final Function<Claims, T> claimsResolver) {
        final String token = sanitizeToken(tokenRaw);
        final Claims claims = parseTokenClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parses the token and returns JWT claims.
     */
    private Claims parseTokenClaims(final String token) {
        return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
    }

    /**
     * Validates token against the provided UserDetails.
     */
    public boolean isTokenValid(final String tokenRaw, final UserDetails userDetails) {
        final String token = sanitizeToken(tokenRaw);
        final String username = extractUsername(token);
        final boolean isValid = username.equals(userDetails.getUsername()) && isTokenExpired(token);

        log.debug("Token valid for user {}: {}", username, isValid);
        return isValid;
    }

    /**
     * Extracts the username (email) from the token.
     */
    public String extractUsername(final String tokenRaw) {
        return extractClaim(tokenRaw, Claims::getSubject);
    }

    /**
     * Checks if the token is expired.
     */
    private boolean isTokenExpired(final String token) {
        final Date expiration = extractClaim(token, Claims::getExpiration);
        final boolean expired = expiration.before(new Date());
        log.debug("Token expired: {}", expired);
        return Boolean.FALSE.equals(expired);
    }
}
