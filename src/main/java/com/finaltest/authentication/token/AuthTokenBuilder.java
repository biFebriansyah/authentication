package com.finaltest.authentication.token;


import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.finaltest.authentication.dto.AuthDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class AuthTokenBuilder {

//    private static final String JWTKEY = "secreetkey0852";
    private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    String jwtKey;

    public String GenerateToken(AuthDto auth) {
        try {
            LocalDateTime time = LocalDateTime.now();
            Algorithm algorithm = Algorithm.HMAC256(jwtKey);
            String token = JWT.create()
                    .withSubject(auth.getFullname())
                    .withClaim("email", auth.getEmail())
                    .withClaim("name", auth.getFullname())
                    .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                    .withIssuer("finaltest")
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException err) {
            return null;
        }
    }
}
