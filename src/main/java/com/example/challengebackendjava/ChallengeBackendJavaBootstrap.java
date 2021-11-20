package com.example.challengebackendjava;

import com.example.challengebackendjava.model.PeliculaSerie;
import com.example.challengebackendjava.model.Personaje;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ChallengeBackendJavaBootstrap implements InitializingBean {

    Personaje mickeyMouse;
    Personaje rapunzel;
    Personaje boo;
    Personaje tiaCass;
    Personaje rayoMcQueen;

    PeliculaSerie steamboatWillie;

    public void initPersonajes() {
        mickeyMouse = new Personaje("/img/mickey.jpg",
                "Mickey Mouse",
                40,
                "El raton de Disney...");
    }

    public void initPeliculasSeries() {
        steamboatWillie = new PeliculaSerie("/img/steamboatwillie.jpg",
                "Steamboat Willie",
                LocalDate.parse("1928-11-01"),
                5);
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("************************************************************************");
        System.out.println("Inicializando...");
        System.out.println("************************************************************************");

        initPeliculasSeries();
        initPersonajes();

        steamboatWillie.agregarPersonaje(mickeyMouse);
        mickeyMouse.agregarPelicualaSerie(steamboatWillie);
    }

}
