package com.example.challengebackendjava.service;

import com.example.challengebackendjava.dao.UserRepository;
import com.example.challengebackendjava.error.BusinessException;
import com.example.challengebackendjava.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      log.error("No se encontro el usuario");
      throw new UsernameNotFoundException("No se encontro el usuario");
    } else {
      log.info("Se encontro el usuairo: {}", username);
    }

    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
  }

  public void crear(User user) {
    log.info("Creando un nuevo usuario {}", user.getUsername());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.crear(user);
  }

  public void actualizar(User user) {
    log.info("Actualizando usuario {}", user.getName());
    if(!user.esValido()) {
      log.info("El usuario no es valido");
      throw new BusinessException("El usuario no es valido");
    }

    User usuarioEnRepo = userRepository.findById(user.getId());
    usuarioEnRepo.setPassword(passwordEncoder.encode(user.getPassword()));
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
