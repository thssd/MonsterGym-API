package com.monstergym.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.monstergym.api.model.usuario.Usuario;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    public String gerarToken(Usuario usuario){
        try {
            var algorithm = Algorithm.HMAC256("12345678");
            return JWT.create()
                    .withIssuer("monstergym_api")
                    .withSubject(usuario.getUsername())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

//    public String getSubject(String tokenJWT){
//        try {
//            var algoritmo = Algorithm.HMAC256("12345678");
//            return JWT.require(algoritmo)
//                    .withIssuer("monstergym_api")
//                    .build()
//                    .verify(tokenJWT)
//                    .getSubject();
//        } catch (JWTVerificationException exception) {
//            throw new RuntimeException("Token JWT inválido ou expirado!");
//        }
//    }
}
