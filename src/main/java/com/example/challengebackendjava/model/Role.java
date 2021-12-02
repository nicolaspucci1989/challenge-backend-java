package com.example.challengebackendjava.model;

import com.example.challengebackendjava.error.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data @NoArgsConstructor @AllArgsConstructor
public class Role extends Entidad{
  private String name;

  @Override
  public void update(Entidad entidad) {
    throw new BusinessException("No se puede actualizar un rol");
  }

  @Override
  public boolean esValido() {
    return name.length() > 3;
  }
}
