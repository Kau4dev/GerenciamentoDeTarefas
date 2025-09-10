package com.kau4dev.GerenciamentoDeTarefas.config.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.kau4dev.GerenciamentoDeTarefas.exception.tokenException.ErroAoGerarTokenException;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("API Gerenciamento de Tarefas")
                    .withSubject(usuario.getEmail())
                    .withClaim("nome", usuario.getNome())
                    .withClaim("login", usuario.getEmail())
                    .withIssuedAt(Instant.now())
                    .withJWTId(UUID.randomUUID().toString())
                    .withExpiresAt(geraDataDeExpiracao())
                    .sign(algorithm);

            return token;

        }catch (JWTCreationException e){

            throw new ErroAoGerarTokenException("Erro ao gerar token JWT");
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("API Gerenciamento de Tarefas")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTCreationException e) {
            return null;
        }
    }

    private Instant geraDataDeExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
