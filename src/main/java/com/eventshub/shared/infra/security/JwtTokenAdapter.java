package com.eventshub.shared.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.eventshub.shared.core.exception.AppException;
import com.eventshub.shared.core.exception.support.CheckNotNull;
import com.eventshub.shared.core.security.TokenPayload;
import com.eventshub.shared.core.security.TokenPort;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

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
    private JWTVerifier verifier;

    @PostConstruct
    public void init() {
        CheckNotNull.forClass(JwtTokenAdapter.class.getName())
                .field("secret", secret)
                .field("issuer", issuer)
                .field("expirationSeconds", expirationSeconds);

        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .acceptLeeway(5) // Tolerance in seconds for clock drift between servers.
                .build();
    }

    @Override
    public String generate(String subject) {
        Instant now = Instant.now();
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(subject)
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(expirationSeconds))
                .sign(algorithm);
    }

    @Override
    public TokenPayload validate(String token) {
        try {
            DecodedJWT jwt = verifier.verify(token);
            return TokenPayload.builder()
                    .externalId(UUID.fromString(jwt.getSubject()))
                    .build();
        } catch (TokenExpiredException ex) {
            throw AppException.tokenExpired();
        } catch (JWTVerificationException ex) {
            throw AppException.tokenInvalid();
        }
    }
}
