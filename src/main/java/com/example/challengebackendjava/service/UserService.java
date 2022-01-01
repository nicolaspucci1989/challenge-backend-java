package com.example.challengebackendjava.service;

import com.example.challengebackendjava.dao.UserRepository;
import com.example.challengebackendjava.error.BusinessException;
import com.example.challengebackendjava.error.NotFoundException;
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

@Service
@RequiredArgsConstructor
@Slf4j
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

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        getSimpleGrantedAuthorities(user));
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
  }

  public void actualizar(User user) {
    log.info("Actualizando usuario {}", user.getName());
    if (!user.esValido()) {
      log.info("El usuario no es valido");
      throw new BusinessException("El usuario no es valido");
    }

    User usuarioEnRepo = userRepository.findById(user.getId())
        .orElseThrow(() -> new NotFoundException("No se encontro el usuario"));
    usuarioEnRepo.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  public User findById(Long id) {
    log.info("Buscando usuario {}", id);
    return userRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontro el usuario"));
  }

  public User findByUsername(String username) {
    log.info("Buscado usuario {}", username);
    return userRepository.findByUsername(username);
  }

  public List<User> all() {
    log.info("Buscando todos los usuarios");
    return userRepository.findAll();
  }

  private boolean emailExiste(String email) {
    return userRepository.findByEmail(email) != null;
  }

  private Collection<SimpleGrantedAuthority> getSimpleGrantedAuthorities(User user) {
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    user.getRoles().forEach(
        role -> authorities.add(new SimpleGrantedAuthority(role.getName()))
    );
    return authorities;
  }
}
