package com.example.challengebackendjava.service;

import com.example.challengebackendjava.dao.RoleRepository;
import com.example.challengebackendjava.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
  @Autowired
  RoleRepository roleRepository;

  public void crear(Role role) {
    roleRepository.crear(role);
  }

  Role getByName(String roleName) {
    return roleRepository.findByName(roleName);
  }
}
