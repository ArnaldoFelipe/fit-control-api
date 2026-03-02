package Evolua.application.config;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Component;

import Evolua.application.entities.Usuario;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class TokenConfig {
    
    private String secret = "secret";

    public String generateToken(Usuario users){

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withClaim("usuarioId", users.getId())
                .withSubject(users.getNome())
                .withExpiresAt(Instant.now().plusSeconds(845454))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JWTUserData> validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decode = JWT.require(algorithm).build().verify(token);

            return Optional.of(JWTUserData.builder()
                    .usuarioId(decode.getClaim("usuarioId").asLong())
                    .nome(decode.getSubject())
                    .build());
        }
        catch (JWTVerificationException ex){
            return Optional.empty();
        }
    }
}
