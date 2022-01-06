package com.example.challengebackendjava.controller;

import com.example.challengebackendjava.helper.SecurityHelper;
import com.example.challengebackendjava.model.User;
import com.example.challengebackendjava.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController extends BaseController{
  private final UserService userService;

  @GetMapping("/refreshtoken")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String authorizationHeader = request.getHeader(AUTHORIZATION);

    if (SecurityHelper.authorizationIsValid(authorizationHeader)) {
      try {
        User user = userService.findByUsername(getUsername(authorizationHeader));

        final String accessToken = SecurityHelper.createAccessToken(request, user);
        final String refreshToken = SecurityHelper.getTokenString(authorizationHeader);

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(
            response.getOutputStream(),
            getTokens(accessToken, refreshToken)
        );

      } catch (Exception exception) {
        response.setHeader("error", exception.getMessage());
        response.setStatus(FORBIDDEN.value());
        Map<String, String> error = new HashMap<>();
        error.put("error_message", exception.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
      }
    } else {
      throw new RuntimeException("Refresh token is missing");
    }
  }


  private Map<String, String> getTokens(String accessToken, String refreshToken) {
    Map<String, String> tokens = new HashMap<>();
    tokens.put("access_token", accessToken);
    tokens.put("refresh_token", refreshToken);
    return tokens;
  }

  private String getUsername(String authorizationHeader) {
    return SecurityHelper.getDecodedJWT(authorizationHeader).getSubject();
  }

  @PostMapping("/register")
  public void registerUser(@RequestBody @Valid User user) {
    userService.crear(user);
  }
}
