package com.example.challengebackendjava.service;

import com.example.challengebackendjava.dao.UserRepository;
import com.example.challengebackendjava.error.BusinessException;
import com.example.challengebackendjava.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class UserService {
  private final UserRepository userRepository;

  public void crear(User user) {
    log.info("Creando un nuevo usuario {}", user.getUsername());
    userRepository.crear(user);
  }

  public void actualizar(User user) {
    log.info("Actualizando usuario {}", user.getName());
    if(!user.esValido()) {
      log.info("El usuario no es valido");
      throw new BusinessException("El usuario no es valido");
    }

    User usuarioEnRepo = userRepository.findById(user.getId());

    userRepository.update(usuarioEnRepo, user);
  }

  public User findUserById(Long id) {
    log.info("Buscando usuario {}", id);
    return userRepository.findById(id);
  }

  public User findByUsername(String username) {
    log.info("Buscado usuario {}", username);
    return userRepository.findByUsername(username);
  }

  public List<User> all() {
    log.info("Buscando todos los usuarios");
    return userRepository.all();
  }
}
