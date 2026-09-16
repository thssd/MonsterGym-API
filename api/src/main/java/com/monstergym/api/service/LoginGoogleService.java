package com.monstergym.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class LoginGoogleService {

    @Value("${google.oauth.client.id}")
    private String clientId;

    @Value("${google.oauth.client.secret}")
    private String clientSecret;

    private final String REDIRECT_URI = "http://localhost:8080/login/google/autorizado";

    private final RestClient restClient;

    public LoginGoogleService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public String gerarUrl() {
        return "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + clientId +
                "&redirect_uri=" + REDIRECT_URI +
                "&scope=https://www.googleapis.com/auth/userinfo.email" +
                "&response_type=code";
    }

    private String obterToken(String code) {
        var resposta = restClient.post()
                .uri("https://oauth.googleapis.com/token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Map.of("code", code,
                        "client_id", clientId,
                        "client_secret", clientSecret,
                        "redirect_uri", REDIRECT_URI,
                        "grant_type", "authorization_code"))
                .retrieve()
                .body(Map.class);

        return resposta.get("id_token").toString();
    }

    public String obterEmail(String code) {
        var token = obterToken(code);
        var jwtDecodificado = JWT.decode(token);

        return jwtDecodificado.getClaim("email").asString();
    }
}
