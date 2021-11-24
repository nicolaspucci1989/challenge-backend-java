package com.example.challengebackendjava.dao;

import com.example.challengebackendjava.model.Entidad;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Repositorio <T extends Entidad> {
    Integer id = 1;
    List<T> elementos = new ArrayList<>();

    public List<T> all() {
        return elementos;
    }

    public T findById(Integer id) {
        return elementos
                .stream()
                .filter(t -> Objects.equals(t.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public void crear(T elemento) {
        agregarElemento(elemento);
    }

    public void update(T elementoEnRepo, T elementoActualizado) {
        elementoEnRepo.update(elementoActualizado);
    }

    public void eliminar(T elemento) {
        elementos.remove(elemento);
    }

    private void agregarElemento(T elemento) {
        elemento.setId(incrementar());
        elementos.add(elemento);
    }

    private Integer incrementar() {
        return id ++;
    }


  public boolean elementoExiste(T elemento) {
        return elementos.contains(elemento);
  }
}
