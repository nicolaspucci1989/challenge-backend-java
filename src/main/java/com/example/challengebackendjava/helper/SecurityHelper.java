package com.example.challengebackendjava.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class SecurityHelper {
  public static DecodedJWT getDecodedJWT(String authorizationHeader) {
    return JWT
        .require(Algorithm.HMAC256("secret".getBytes()))
        .build()
        .verify(authorizationHeader.substring("Bearer ".length()));
  }

  public static Builder getJWT(HttpServletRequest request, String username, Integer expiration) {
    return JWT.create()
        .withSubject(username)
        .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
        .withIssuer(request.getRequestURL().toString());
  }
}
