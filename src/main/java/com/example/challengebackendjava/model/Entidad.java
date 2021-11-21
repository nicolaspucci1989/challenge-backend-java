package com.example.challengebackendjava.model;

abstract public class Entidad {
    Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    abstract public void update(Entidad entidad);
}
