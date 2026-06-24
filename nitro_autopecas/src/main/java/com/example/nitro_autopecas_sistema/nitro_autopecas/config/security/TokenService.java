package com.example.nitro_autopecas_sistema.nitro_autopecas.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.time.ZoneOffset;


@Service
public class TokenService {

    @Value("${api.security.token.secret}") // Pega a senha do application.properties
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            String perfilString = (usuario.getPerfil() != null) ? usuario.getPerfil().toString() : "CLIENTE";
            return JWT.create()
                    .withIssuer("API NitroAutopecas")
                    .withSubject(usuario.getLogin())
                    .withClaim("id",usuario.getId())
                    .withClaim("role",perfilString)
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token jwt", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API NitroAutopecas")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new IllegalArgumentException("Token JWT inválido ou expirado!");
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(5).toInstant(ZoneOffset.of("-03:00"));
    }
}
