package com.example.challengebackendjava.controller;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.challengebackendjava.helper.SecurityHelper;
import com.example.challengebackendjava.model.Role;
import com.example.challengebackendjava.model.User;
import com.example.challengebackendjava.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
  private final UserService userService;

  @GetMapping("/refreshtoken")
  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String authorizationHeader = request.getHeader(AUTHORIZATION);

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      try {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        User user = userService.findByUsername(getUsername(authorizationHeader));

        final String accessToken = createAccessToken(request, algorithm, user);
        final String refreshToken = getTokenString(authorizationHeader);

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

  private String createAccessToken(HttpServletRequest request, Algorithm algorithm, User user) {
    return SecurityHelper
        .getJWT(request, user.getUsername(), 10 * 60 * 1000)
        .withClaim("roles",getUserRoles(user))
        .sign(algorithm);
  }

  private String getTokenString(String authorizationHeader) {
    return authorizationHeader.substring("Bearer ".length());
  }

  private List<String> getUserRoles(User user) {
    return user
        .getRoles()
        .stream()
        .map(Role::getName)
        .collect(Collectors.toList());
  }

  private String getUsername(String authorizationHeader) {
    return SecurityHelper.getDecodedJWT(authorizationHeader).getSubject();
  }

  @PostMapping("/register")
  public void registerUser(@RequestBody User user) throws IOException {
    userService.crear(user);

    Email from = new Email("nicolaspucci1989@gmail.com");
    String subject = "Bienvendio";
    Email to = new Email(user.getEmail());
    Content content = new Content("text/plain", "La cuenta fue creada con exito");
    Mail mail = new Mail(from, subject, to, content);
    final String sendgrid_api_key = System.getenv("SENDGRID_API_KEY");
    log.info("Sendgrid API Key: {}", sendgrid_api_key);
    SendGrid sg = new SendGrid(sendgrid_api_key);
    Request request = new Request();

    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sg.api(request);
      log.info("Respuesta: {}", response.getStatusCode());
      log.info("Body: {}", response.getBody());
      log.info("Headers: {}", response.getHeaders());
    } catch (IOException ex) { // TODO: fix
      throw ex;
    }
  }
}
