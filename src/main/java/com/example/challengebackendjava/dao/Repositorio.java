package com.example.challengebackendjava.dao;

import com.example.challengebackendjava.model.Entidad;

import java.util.ArrayList;
import java.util.List;

public class Repositorio <T extends Entidad> {
    Integer id = 1;
    List<T> elementos = new ArrayList<>();

    public void crear(T elemento) {
        agregarElemento(elemento);
    }

    public List<T> all() {
        return elementos;
    }

    private void agregarElemento(T elemento) {
        elemento.setId(incrementar());
        elementos.add(elemento);
    }

    private Integer incrementar() {
        return id ++;
    }
}
