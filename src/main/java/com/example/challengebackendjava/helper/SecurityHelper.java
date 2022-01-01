package com.example.challengebackendjava.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class SecurityHelper {
  public static DecodedJWT getDecodedJWT(String authorizationHeader) {
    return JWT
        .require(Algorithm.HMAC256("secret".getBytes()))
        .build()
        .verify(authorizationHeader.substring("Bearer ".length()));
  }
}
