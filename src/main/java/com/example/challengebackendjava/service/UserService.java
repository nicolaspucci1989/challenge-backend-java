package com.example.challengebackendjava.service;

import com.example.challengebackendjava.dao.UserRepository;
import com.example.challengebackendjava.error.BusinessException;
import com.example.challengebackendjava.error.NotFoundException;
import com.example.challengebackendjava.event.UserCreatedEvent;
import com.example.challengebackendjava.event.UserCreatedPublisher;
import com.example.challengebackendjava.helper.SecurityHelper;
import com.example.challengebackendjava.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserCreatedPublisher userCreatedPublisher;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      log.error("No se encontro el usuario");
      throw new UsernameNotFoundException("No se encontro el usuario");
    } else {
      log.info("Se encontro el usuairo: {}", username);
    }

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        SecurityHelper.getSimpleGrantedAuthorities(user));
  }

  public void crear(User user) {
    log.info("Creando un nuevo usuario {}", user.getUsername());

    if (emailExiste(user.getEmail())) {
      String error = "Ya existe un usuario con el email: " + user.getEmail();
      log.error(error);
      throw new BusinessException(error);
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    userCreatedPublisher.publishUserCreatedEvent("usuario creado", user.getEmail());
  }

  public User findByUsername(String username) {
    log.info("Buscado usuario {}", username);
    return userRepository.findByUsername(username);
  }

  private boolean emailExiste(String email) {
    return userRepository.findByEmail(email) != null;
  }
}
