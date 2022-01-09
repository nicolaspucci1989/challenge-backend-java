package com.example.challengebackendjava;

import com.example.challengebackendjava.model.Genero;
import com.example.challengebackendjava.model.PeliculaSerie;
import com.example.challengebackendjava.model.Personaje;
import com.example.challengebackendjava.model.User;
import com.example.challengebackendjava.service.GeneroService;
import com.example.challengebackendjava.service.PeliculaSerieService;
import com.example.challengebackendjava.service.PersonajeService;
import com.example.challengebackendjava.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class ChallengeBackendJavaBootstrap implements InitializingBean {
  final private PersonajeService personajeService;
  final private PeliculaSerieService peliculaSerieService;
  final private GeneroService generoService;
  final private UserService userService;

  Personaje mickeyMouse;
  Personaje minnieMouse;
  Personaje rapunzel;
  Personaje pascal;
  Personaje boo;
  Personaje jamesSullivan;
  Personaje mikeWazowski;
  Personaje tiaCass;
  Personaje rayoMcQueen;
  Personaje chickHicks;
  Personaje fred;

  PeliculaSerie steamboatWillie; // Peli de Mickey Mouse
  PeliculaSerie bigHeroSix; // Peli de Tia Cass
  PeliculaSerie monstersInc; // Peli de Boo
  PeliculaSerie monstersUniversity; // Peli de Boo
  PeliculaSerie enredados; // Peli de Rapunzel
  PeliculaSerie cars; // Peli de Rayo McQueen

  Genero carreras;
  Genero infantil;
  Genero fantasia;
  Genero terror;
  Genero cinenciaFiccion;

  public void initPersonajes() {
    mickeyMouse = new Personaje(null,
        "/img/mickey-mouse.jpg",
        "Mickey Mouse",
        40,
        "El raton de Disney...",
        90f,
        new HashSet<>());

    minnieMouse = new Personaje(null,
        "/img/minnie-mouse.jpg",
        "Minnie Mouse",
        43,
        "La ratona de Disney...",
        83f,
        new HashSet<>());

    rapunzel = new Personaje(null,
        "/img/rapunzel.jpg",
        "Rapunzel",
        30,
        "La princesa de pelo largo...",
        100f,
        new HashSet<>());

    pascal = new Personaje(null,
        "/img/pascal.jpg",
        "Pascal",
        30,
        "un Camaleón, es el mejor amigo de Rapunzel.",
        100f,
        new HashSet<>());

    boo = new Personaje(null,
        "/img/boo.jpg",
        "Boo",
        50,
        "Boo...",
        120f,
        new HashSet<>());

    jamesSullivan = new Personaje(null,
        "/img/jamessullivan.jpg",
        "James Sullivan",
        30,
        "James Sullivan...",
        85f,
        new HashSet<>());

    mikeWazowski = new Personaje(null,
        "/img/mikewazowski.jpg",
        "Mike Wazowski",
        30,
        "Mike Wazowski...",
        85f,
        new HashSet<>());

    tiaCass = new Personaje(null,
        "/img/tiacass.jpg",
        "Tia Cass",
        50,
        "Tia Cass...",
        120f,
        new HashSet<>());

    rayoMcQueen = new Personaje(null,
        "/img/rayomcqueen.jpg",
        "Rayo McQueen",
        50,
        "Rayo McQueen...",
        120f,
        new HashSet<>());

    chickHicks = new Personaje(null,
        "/img/chick-hicks.jpg",
        "Chick Hicks",
        50,
        "Chick Hicks...",
        120f,
        new HashSet<>());

    fred = new Personaje(null,
        "/img/fred.jpg",
        "Fred",
        14,
        "Fred ...",
        80f,
        new HashSet<>());

    savePersonajes();
  }

  public void initPeliculasSeries() {
    steamboatWillie = new PeliculaSerie("/img/steamboat-willie.jpg",
        "Steamboat Willie",
        LocalDate.parse("1928-11-01"),
        3);

    enredados = new PeliculaSerie("/img/enredados.jpg",
        "Enredados",
        LocalDate.parse("2010-11-24"),
        2);

    bigHeroSix = new PeliculaSerie("/img/big-hero-six.jpg",
        "Big Hero Six",
        LocalDate.parse("2014-11-07"),
        5);

    monstersInc = new PeliculaSerie("/img/monsters-inc.jpg",
        "Monsters Inc",
        LocalDate.parse("2001-11-02"),
        1);

    monstersUniversity = new PeliculaSerie("/img/monsters-university.jpg",
        "Monsters University",
        LocalDate.parse("2013-06-21"),
        2);


    cars = new PeliculaSerie("/img/cars.jpg",
        "Cars",
        LocalDate.parse("2002-11-02"),
        4);

    savePelicuasSeries();
  }

  public void initGeneros() {
    carreras = new Genero("Carreras", "/img/carreras.jpg");
    infantil = new Genero("Infantil", "/img/infantil.jpg");
    fantasia = new Genero("Fantasia", "/img/fantasia.jpg");
    terror = new Genero("Terror", "/img/terror.jpg");
    cinenciaFiccion = new Genero("Ciencia Ficcion", "/img/cinenciaFiccion.jpg");

    saveGeneros();
  }

  @Override
  public void afterPropertiesSet() {
    System.out.println("************************************************************************");
    System.out.println("Inicializando...");
    System.out.println("************************************************************************");

    initUsuarios();
    initPeliculasSeries();
    initPersonajes();
    initGeneros();

    steamboatWillie.agregarPersonaje(mickeyMouse);
    steamboatWillie.agregarPersonaje(minnieMouse);
    infantil.agregarPelicula(steamboatWillie);

    enredados.agregarPersonaje(rapunzel);
    enredados.agregarPersonaje(pascal);
    infantil.agregarPelicula(enredados);
    fantasia.agregarPelicula(enredados);

    bigHeroSix.agregarPersonaje(tiaCass);
    bigHeroSix.agregarPersonaje(fred);
    infantil.agregarPelicula(bigHeroSix);
    cinenciaFiccion.agregarPelicula(bigHeroSix);

    monstersInc.agregarPersonaje(boo);
    monstersInc.agregarPersonaje(jamesSullivan);
    monstersInc.agregarPersonaje(mikeWazowski);
    infantil.agregarPelicula(monstersInc);
    terror.agregarPelicula(monstersInc);

    monstersUniversity.agregarPersonaje(mikeWazowski);
    monstersUniversity.agregarPersonaje(jamesSullivan);
    monstersUniversity.agregarPersonaje(boo);
    infantil.agregarPelicula(monstersUniversity);
    terror.agregarPelicula(monstersUniversity);

    cars.agregarPersonaje(rayoMcQueen);
    cars.agregarPersonaje(chickHicks);
    infantil.agregarPelicula(cars);
    carreras.agregarPelicula(cars);

    savePersonajes();
    savePelicuasSeries();
    saveGeneros();
  }

  private void initUsuarios() {
    User user = new User(null,
        "John",
        "johndoe",
        "123",
        "johndoen@mail.com",
        new ArrayList<>());
    userService.crear(user);
  }

  private void savePersonajes() {
    personajeService.save(rapunzel);
    personajeService.save(pascal);
    personajeService.save(mickeyMouse);
    personajeService.save(minnieMouse);
    personajeService.save(boo);
    personajeService.save(jamesSullivan);
    personajeService.save(mikeWazowski);
    personajeService.save(tiaCass);
    personajeService.save(rayoMcQueen);
    personajeService.save(chickHicks);
    personajeService.save(fred);
  }

  private void savePelicuasSeries() {
    peliculaSerieService.save(steamboatWillie);
    peliculaSerieService.save(enredados);
    peliculaSerieService.save(bigHeroSix);
    peliculaSerieService.save(monstersInc);
    peliculaSerieService.save(monstersUniversity);
    peliculaSerieService.save(cars);
  }

  private void saveGeneros() {
    generoService.save(carreras);
    generoService.save(infantil);
    generoService.save(fantasia);
    generoService.save(terror);
    generoService.save(cinenciaFiccion);
  }

}
