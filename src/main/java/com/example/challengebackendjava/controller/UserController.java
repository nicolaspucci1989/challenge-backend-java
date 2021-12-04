package com.example.challengebackendjava.controller;

import com.example.challengebackendjava.model.User;
import com.example.challengebackendjava.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/")
  public ResponseEntity<List<User>> getUser() {
    return ResponseEntity.ok().body(userService.all());
  }

}
