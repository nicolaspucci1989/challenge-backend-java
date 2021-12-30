package com.example.challengebackendjava.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeneroPeliculaSerieKey implements Serializable {
    @Column(name = "genero_id")
    private Long generoId;

    @Column(name = "pelicula_serie_id")
    private Long peliculaSerieId;
}
