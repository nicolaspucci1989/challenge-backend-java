package com.example.challengebackendjava.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.challengebackendjava.model.Role;
import com.example.challengebackendjava.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

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

  public static String createAccessToken(HttpServletRequest request, User user) {
    return getJWT(request, user.getUsername(), 10 * 60 * 1000)
        .withClaim("roles", getUserRoles(user))
        .sign(getAlgorithm());
  }

  private static List<String> getUserRoles(User user) {
    return user
        .getRoles()
        .stream()
        .map(Role::getName)
        .collect(Collectors.toList());
  }

  public static String getTokenString(String authorizationHeader) {
    return authorizationHeader.substring("Bearer ".length());
  }

  public static Algorithm getAlgorithm() {
    final String secret = System.getenv("SECRET");
    return Algorithm.HMAC256(secret.getBytes());
  }

  public static boolean authorizationIsValid(String authorizationHeader) {
    return authorizationHeader != null && authorizationHeader.startsWith("Bearer ");
  }

  public static Collection<SimpleGrantedAuthority> getSimpleGrantedAuthorities(User user) {
    return user.getRoles()
        .stream().map(
            role -> new SimpleGrantedAuthority(role.getName())
        )
        .collect(Collectors.toList());
  }

  public static Collection<SimpleGrantedAuthority> getSimpleGrantedAuthorities(DecodedJWT decodedJWT) {
    return stream(getRoles(decodedJWT))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  private static String[] getRoles(DecodedJWT decodedJWT) {
    return decodedJWT.getClaim("roles").asArray(String.class);
  }

}
