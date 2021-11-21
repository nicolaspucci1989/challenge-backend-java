package com.example.challengebackendjava;

import com.example.challengebackendjava.dao.PeliculaSerieRepository;
import com.example.challengebackendjava.dao.PersonajeRepository;
import com.example.challengebackendjava.model.PeliculaSerie;
import com.example.challengebackendjava.model.Personaje;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ChallengeBackendJavaBootstrap implements InitializingBean {

  @Autowired
  PersonajeRepository personajeRepo;

  @Autowired
  PeliculaSerieRepository peliculaSerieRepository;

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

    rapunzel = new Personaje("/img/rapunzel.jpg",
            "Rapunzel",
            30,
            "La princesa de pelo largo...");

    personajeRepo.crear(mickeyMouse);
    personajeRepo.crear(rapunzel);
  }

  public void initPeliculasSeries() {
    steamboatWillie = new PeliculaSerie("/img/steamboatwillie.jpg",
            "Steamboat Willie",
            LocalDate.parse("1928-11-01"),
            5);

    peliculaSerieRepository.crear(steamboatWillie);
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
