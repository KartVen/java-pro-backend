package pl.kartven.javaprobackend.infra.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.vavr.control.Option;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pl.kartven.javaprobackend.infra.model.user.User;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtUtil {
    public static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private final JwtProperties jwtProperties;

    private String buildToken(UserDetails userDetails, Map<String, Object> extraClaims, Integer expirationTime) {
        final Date createdDate = new Date(System.currentTimeMillis());
        final Date expirationDate = new Date(createdDate.getTime() + expirationTime * 1000);
        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .addClaims(extraClaims)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = Map.of(
                "role", ((User) userDetails).getRole().getAuthority()
        );
        return buildToken(userDetails, claims, jwtProperties.expirationTime);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(userDetails, null, jwtProperties.refreshTokenExpirationTime);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(this.getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(extractAllClaims(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails userDetails) {
        return (extractUsername(token).equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secretKey));
    }

    public String extractToken(String authHeader) {
        return Option
                .of(authHeader)
                .filter(header -> StringUtils.hasText(header) && header.startsWith(BEARER_TOKEN_PREFIX))
                .map(header -> header.substring(BEARER_TOKEN_PREFIX.length()))
                .getOrNull();
    }

    @Data
    @Configuration
    @ConfigurationProperties("app.security.jwt")
    private static class JwtProperties {
        private Integer expirationTime;
        private String secretKey;
        private Integer refreshTokenExpirationTime;
    }
}
