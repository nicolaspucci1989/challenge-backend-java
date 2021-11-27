package com.example.challengebackendjava.model;

import com.example.challengebackendjava.serializer.View;
import com.fasterxml.jackson.annotation.JsonView;

abstract public class Entidad {
    Integer id;

    @JsonView({
            View.Personaje.Lista.class,
            View.PeliculaSerie.Lista.class,
            View.PeliculaSerie.Detalle.class,
            View.Personaje.Detalle.class
    })
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    abstract public void update(Entidad entidad);

    abstract public boolean esValido();
}
