package br.atos.cemig.Jwt;

import br.atos.cemig.User.UserEntity;
import br.atos.cemig.User.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    // Injeta a chave de assinatura JWT da configuração da aplicação.
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    // Referência ao repositório de usuários
    private final UserRepository userRepository; // Altere para UserRepository

    // Construtor
    public JwtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Este método extrai o nome de usuário do token.
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getId);
    }

    // Gera um token para um usuário específico (login).
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Verifica se um token é válido para um usuário específico.
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Extrai uma reivindicação do token aplicando uma função.
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    // Gera um token JWT para um usuário com reivindicações adicionais.
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        UserEntity user = (UserEntity) userDetails; // Altere para UserEntity
        extraClaims.put("role", user.getRole()); // Supondo que o UserEntity tenha um método getRole()
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setId(user.getId().toString()) // Altere para o método correto de obtenção do ID
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 dia
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Verifica se um token expirou.
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extrai a data de expiração de um token.
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extrai todas as reivindicações de um token.
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Decodifica a chave de assinatura de base64 e retorna um objeto Key.
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
