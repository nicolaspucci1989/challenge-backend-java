package com.example.challengebackendjava.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class GeneroPeliculaSerie {
    @EmbeddedId
    private GeneroPeliculaSerieKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("generoId")
    private Genero genero;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("peliculaSerieId")
    private PeliculaSerie peliculaSerie;

    public GeneroPeliculaSerie(Genero genero, PeliculaSerie peliculaSerie) {
        this.genero = genero;
        this.peliculaSerie = peliculaSerie;
        this.id = new GeneroPeliculaSerieKey(genero.getId(), peliculaSerie.getId());
    }
}
