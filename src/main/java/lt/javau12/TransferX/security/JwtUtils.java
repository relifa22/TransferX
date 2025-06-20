package lt.javau12.TransferX.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtils {

    private final SecretKey secretKey;
    private final long expirationMillis;

    public JwtUtils(
            @Value("${jwt.secret}") String secret, // ← čia slaptažodis iš failo
            @Value("${jwt.expiration}") long expirationMillis) { // ← galiojimo laikas

        //  Iš to slapto rakto (teksto) padarom SecretKey objektą, su kuriuo pasirašysim tokenus
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        // Išsaugom galiojimo laiką, kad vėliau galėtume naudoti kuriant token
        this.expirationMillis = expirationMillis;
    }

    public String generateToken(String email){
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(email)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMillis)))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token){
        return Jwts.parser()             // konvertuojam
                .verifyWith(secretKey)   // patikrinam secret key
                .build()//
                .parseSignedClaims(token)      //issifruojam tokena ir
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token, String email){
        try {
            String extractedUsername = extractUsername(token);
            return extractedUsername.equals(email) && !isTokenExpired(token);
        }catch (Exception e){
            return false;
        }
    }

    private boolean isTokenExpired(String token){
        Date expiration = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expiration.before(new Date());

    }


}
