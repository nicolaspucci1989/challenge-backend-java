package com.example.challengebackendjava.filter;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.challengebackendjava.helper.SecurityHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final AuthenticationManager authenticationManager;

  public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    log.info("User: {}, Passwordk: {}", username, password);
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(username, password);
    return authenticationManager.authenticate(authenticationToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
    User user = (User) authentication.getPrincipal();
    response.setContentType(APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(
        response.getOutputStream(),
        getTokens(user, request)
    );
  }

  private Map<String, String> getTokens(User user, HttpServletRequest request) {
    final Algorithm algorithm = SecurityHelper.getAlgorithm();
    Map<String, String> tokens = new HashMap<>();
    tokens.put(
        "access_token",
        SecurityHelper.getJWT(request, user.getUsername(), 10 * 60 * 1000)
            .withClaim("roles", getAuthorities(user))
            .sign(algorithm)
    );

    tokens.put(
        "refresh_token",
        SecurityHelper.getJWT(request, user.getUsername(), 30 * 60 * 1000)
            .sign(algorithm)
    );

    return tokens;
  }

  private List<String> getAuthorities(User user) {
    return user.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
  }
}
