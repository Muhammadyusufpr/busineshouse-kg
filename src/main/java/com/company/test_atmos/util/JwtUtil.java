package com.company.test_atmos.util;

import com.company.test_atmos.enums.ProfileRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final int jwtExpiration = 24;
    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final String encodedKey = "qwertyasdfgh65432wsxegvnpierugneirgheirgveoigjh3409ut509347t5092763yr203uroifj8owjeifjsodifj8efhoiwefh8wehf8wefhwpef";

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean hasClaim(String token, String claimName) {
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName) != null;
    }

    public boolean hasEqual(String token, String claimName, String claimValue) {
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName) != null && claims.get(claimName).equals(claimValue);
    }

    public String getSubject(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(encodedKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username, ProfileRole role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role.name());
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String username) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(jwtExpiration)))
                .signWith(SignatureAlgorithm.HS256, encodedKey)
                .compact();
    }

    public Boolean isTokenValid(String token, String username) {
        final String extractUserName = extractUserName(token);
        return (extractUserName.equals(username) && !isTokenExpired(token));
    }


}
