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
  Personaje jamesSullivan;
  Personaje mikeWazowski;
  Personaje tiaCass;
  Personaje rayoMcQueen;
  Personaje fred;

  PeliculaSerie steamboatWillie; // Peli de Mickey Mouse
  PeliculaSerie bigHeroSix; // Peli de Tia Cass
  PeliculaSerie monstersInc; // Peli de Boo
  PeliculaSerie monstersUniversity; // Peli de Boo
  PeliculaSerie enredados; // Peli de Rapunzel
  PeliculaSerie cars; // Peli de Rayo McQueen


  public void initPersonajes() {
    mickeyMouse = new Personaje("/img/mickey.jpg",
            "Mickey Mouse",
            40,
            "El raton de Disney...",
            90f);

    rapunzel = new Personaje("/img/rapunzel.jpg",
            "Rapunzel",
            30,
            "La princesa de pelo largo...",
            100f);

    boo = new Personaje("/img/boo.jpg",
            "Boo",
            50,
            "Boo...",
            120f);

    jamesSullivan = new Personaje("/img/jamessullivan.jpg",
            "James Sullivan",
            30,
            "James Sullivan...",
            85f);

    mikeWazowski = new Personaje("/img/mikewazowski.jpg",
            "Mike Wazowski",
            30,
            "Mike Wazowski...",
            85f);

    tiaCass = new Personaje("/img/tiacass.jpg",
            "Tia Cass",
            50,
            "Tia Cass...",
            120f);

    rayoMcQueen = new Personaje("/img/rayomcqueen.jpg",
            "Rayo McQueen",
            50,
            "Rayo McQueen...",
            120f);

    fred = new Personaje("/img/fred.jpg",
            "Fred",
            14,
            "Fred ...",
            80f);

    personajeRepo.crear(mickeyMouse);
    personajeRepo.crear(rapunzel);
    personajeRepo.crear(boo);
    personajeRepo.crear(jamesSullivan);
    personajeRepo.crear(tiaCass);
    personajeRepo.crear(rayoMcQueen);
    personajeRepo.crear(fred);
  }

  public void initPeliculasSeries() {
    steamboatWillie = new PeliculaSerie("/img/steamboat-willie.jpg",
            "Steamboat Willie",
            LocalDate.parse("1928-11-01"),
            5);

    enredados = new PeliculaSerie("/img/enredados.jpg",
            "Enredados",
            LocalDate.parse("2010-11-24"),
            10);

    bigHeroSix = new PeliculaSerie("/img/big-hero-six.jpg",
            "Big Hero Six",
            LocalDate.parse("2014-11-07"),
            7);

    monstersInc = new PeliculaSerie("/img/monsters-inc.jpg",
            "Monsters Inc",
            LocalDate.parse("2001-11-02"),
            13);

    monstersUniversity = new PeliculaSerie("/img/monsters-university.jpg",
            "Monsters University",
            LocalDate.parse("2013-06-21"),
            11);


    cars = new PeliculaSerie("/img/cars.jpg",
            "Cars",
            LocalDate.parse("2001-11-02"),
            13);



    peliculaSerieRepository.crear(steamboatWillie);
    peliculaSerieRepository.crear(enredados);
    peliculaSerieRepository.crear(bigHeroSix);
    peliculaSerieRepository.crear(monstersInc);
    peliculaSerieRepository.crear(monstersUniversity);
    peliculaSerieRepository.crear(cars);
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

    enredados.agregarPersonaje(rapunzel);
    rapunzel.agregarPelicualaSerie(enredados);

    bigHeroSix.agregarPersonaje(tiaCass);
    bigHeroSix.agregarPersonaje(fred);
    tiaCass.agregarPelicualaSerie(bigHeroSix);
    fred.agregarPelicualaSerie(bigHeroSix);

    monstersInc.agregarPersonaje(boo);
    monstersInc.agregarPersonaje(jamesSullivan);
    monstersInc.agregarPersonaje(mikeWazowski);
    boo.agregarPelicualaSerie(monstersInc);
    jamesSullivan.agregarPelicualaSerie(monstersInc);
    mikeWazowski.agregarPelicualaSerie(monstersInc);

    monstersUniversity.agregarPersonaje(mikeWazowski);
    monstersUniversity.agregarPersonaje(jamesSullivan);
    monstersUniversity.agregarPersonaje(boo);
    mikeWazowski.agregarPelicualaSerie(monstersUniversity);
    jamesSullivan.agregarPelicualaSerie(monstersUniversity);
    boo.agregarPelicualaSerie(monstersUniversity);

    cars.agregarPersonaje(rayoMcQueen);
    rayoMcQueen.agregarPelicualaSerie(cars);
  }

}
