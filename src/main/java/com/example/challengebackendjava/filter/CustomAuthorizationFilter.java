package com.example.challengebackendjava.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.challengebackendjava.helper.SecurityHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain)
      throws ServletException, IOException {
    if (isLoginOrRefresh(request)) {
      filterChain.doFilter(request, response);
    } else {

      String authorizationHeader = request.getHeader(AUTHORIZATION);
      if (SecurityHelper.authorizationIsValid(authorizationHeader)) {
        try {
          DecodedJWT decodedJWT = SecurityHelper.getDecodedJWT(authorizationHeader);
          UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(
                  decodedJWT.getSubject(), //get username
                  null,
                  SecurityHelper.getSimpleGrantedAuthorities(decodedJWT)
              );
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);

          filterChain.doFilter(request, response);
        } catch (Exception exception) {
          log.error("Error login in: {}", exception.getMessage());
          response.setHeader("error", exception.getMessage());
          response.setStatus(FORBIDDEN.value());
          Map<String, String> error = new HashMap<>();
          error.put("error_message", exception.getMessage());
          response.setContentType(APPLICATION_JSON_VALUE);
          new ObjectMapper().writeValue(response.getOutputStream(), error);
        }

      } else {
        filterChain.doFilter(request, response);
      }
    }

  }

  private boolean isLoginOrRefresh(HttpServletRequest request) {
    return request.getServletPath().equals("/login") ||
        request.getServletPath().equals("/auth/refreshtoken");
  }

}