package com.example.challengebackendjava.service;

import com.example.challengebackendjava.dao.RoleRepository;
import com.example.challengebackendjava.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
  private final RoleRepository roleRepository;

  public void crear(Role role) {
    roleRepository.save(role);
  }

  Role getByName(String roleName) {
    return roleRepository.findByName(roleName);
  }
}
