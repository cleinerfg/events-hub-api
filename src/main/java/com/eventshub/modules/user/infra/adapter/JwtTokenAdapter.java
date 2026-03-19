package com.eventshub.modules.user.infra.adapter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.eventshub.modules.user.core.model.User;
import com.eventshub.modules.user.core.port.TokenPort;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtTokenAdapter implements TokenPort {

    @Value("${app.security.jwt.secret}")
    private String secret;

    @Value("${app.security.jwt.issuer}")
    private String issuer;

    @Value("${app.security.jwt.expiration-seconds}")
    private long expirationSeconds;

    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(secret);
    }

    @Override
    public String generate(User user) {
        Instant now = Instant.now();
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getExternalId().toString())
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(expirationSeconds))
                .sign(algorithm);
    }
}
