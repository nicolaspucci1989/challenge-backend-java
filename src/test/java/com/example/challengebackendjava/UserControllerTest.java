package com.example.challengebackendjava;

import com.example.challengebackendjava.model.User;
import com.example.challengebackendjava.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.ArrayList;

import static com.example.challengebackendjava.TestHelper.getMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de usuarios")
public class UserControllerTest {
  @Autowired
  MockMvc mockMvc;
  @Autowired
  UserService userService;
  User user;

  @Transactional
  @Test
  @DisplayName("al intentar crear un usuario no valido devuelve error")
  public void crearUsuarioNoValido() throws Exception {

    user.setName("");
    user.setUsername("");
    user.setPassword("");
    user.setEmail("");

    mockMvc.perform(
        post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(getMapper().writeValueAsString(user))
    )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.name", Is.is("El nombre es obligatorio")))
        .andExpect(jsonPath("$.username", Is.is("El username es obligatorio")))
        .andExpect(jsonPath("$.email", Is.is("El email es obligatorio")))
        .andExpect(jsonPath("$.password", Is.is("El password es obligatorio")));
  }

  @BeforeEach
  void init() {
    user = new User(null,
        "nombre",
        "username",
        "password",
        "email",
        new ArrayList<>());
  }
}
