package com.example.challengebackendjava.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CriterioDeBusqueda {
  private String clave;
  private String operacion;
  private Object valor;
}
