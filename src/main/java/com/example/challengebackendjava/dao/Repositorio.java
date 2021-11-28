package com.example.challengebackendjava.dao;

import com.example.challengebackendjava.error.BusinessException;
import com.example.challengebackendjava.model.Entidad;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Repositorio <T extends Entidad> {
  Long id = 1L;
  List<T> elementos = new ArrayList<>();

  public List<T> all() {
    return elementos;
  }

  public T findById(Long id) {
    return elementos
        .stream()
        .filter(t -> Objects.equals(t.getId(), id))
        .findFirst()
        .orElse(null);
  }

  public void crear(T elemento) {
    validarElemento(elemento);
    agregarElemento(elemento);
  }

  private void validarElemento(T elemento) {
    if (!elemento.esValido()){
      throw new BusinessException("El elemento no es valido");
    }
  }

  public void update(T elementoEnRepo, T elementoActualizado) {
    validarElemento(elementoActualizado);
    elementoEnRepo.update(elementoActualizado);
  }

  public void eliminar(T elemento) {
    elementos.remove(elemento);
  }

  private void agregarElemento(T elemento) {
    elemento.setId(incrementar());
    elementos.add(elemento);
  }

  private Long incrementar() {
    return id ++;
  }

  public boolean elementoExiste(T elemento) {
    return elementos.contains(elemento);
  }
}
