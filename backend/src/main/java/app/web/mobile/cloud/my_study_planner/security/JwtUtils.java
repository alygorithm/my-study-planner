package app.web.mobile.cloud.my_study_planner.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * Utility per la generazione, validazione e gestione dei token JWT.
 *
 * Funzioni principali:
 * - Generare token JWT firmati con chiave segreta
 * - Estrarre lo username dai token
 * - Validare la correttezza e scadenza del token
 *
 * Si integra con JwtAuthenticationFilter e JwtAuthenticationEntryPoint
 * per gestire la sicurezza basata su JWT nell'applicazione.
 */

@Component
public class JwtUtils {

    private final SecretKey key;
    private final long jwtExpirationMs;

    public JwtUtils(@Value("${jwt.secret}") String jwtSecret,
                    @Value("${jwt.expiration}") long jwtExpiration) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
        this.jwtExpirationMs = jwtExpiration;
    }

    /**
     * Genera un token JWT firmato contenente lo username.
     *
     * @param username username dell'utente
     * @return token JWT come stringa
     *
     * Funzionamento:
     * 1. Imposta lo username come subject del token
     * 2. Imposta issuedAt alla data corrente
     * 3. Imposta expiration aggiungendo jwtExpirationMs alla data corrente
     * 4. Firma il token con la chiave HMAC e algoritmo HS512
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // estrae l'username da un token JWT valido
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // veifica se un token JWT è valido e non scaduto
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Invalid JWT token: " + e.getMessage());
            return false;
        }
    }
}