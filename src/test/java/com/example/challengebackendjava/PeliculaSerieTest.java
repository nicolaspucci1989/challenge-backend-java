package com.example.challengebackendjava;


import com.example.challengebackendjava.dto.PeliculaSerieDetalleDto;
import com.example.challengebackendjava.dto.PeliculaSerieListDto;
import com.example.challengebackendjava.service.PeliculaSerieService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de peliculas-series")
public class PeliculaSerieTest {
  @Autowired
  MockMvc mockMvc;
  @Autowired
  PeliculaSerieService peliculaSerieService;

  @Transactional
  @Test
  @DisplayName("podemos obtener un listado de peliculas-series")
  public void listadoPeliculasSeries() throws Exception {
    MockHttpServletResponse response = mockMvc.perform(
            get("/movies")
        )
        .andReturn().getResponse();

    Assertions.assertEquals(200, response.getStatus());

    Set<PeliculaSerieListDto> peliculaSerieListDtos = getMapper().readValue(response.getContentAsString(), new TypeReference<>() {
    });
    peliculaSerieListDtos.forEach(peliculaSerieListDto -> {
      assertNotNull(peliculaSerieListDto.getId());
      assertNotNull(peliculaSerieListDto.getImagen());
      assertNotNull(peliculaSerieListDto.getTitulo());
      assertNotNull(peliculaSerieListDto.getFechaDeCreacion());
    });
  }

  @Transactional
  @Test
  @DisplayName("podemos obtener la informacion de una pelicula-serie")
  public void infoDePersonaje() throws Exception {
    Long id = peliculaSerieService.all().get(0).getId();

    mockMvc.perform(
            get("/movies/" + id)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.titulo").exists())
        .andExpect(jsonPath("$.imagen").exists())
        .andExpect(jsonPath("$.fechaDeCreacion").exists())
        .andExpect(jsonPath("$.calificacion").exists())
        .andExpect(jsonPath("$.personajes").exists());
  }

  @Transactional
  @Test
  @DisplayName("al intentar crear una pelicula-serie no valida devuelve 400")
  public void crearPeliculaSerieNoValida() throws Exception {
    PeliculaSerieDetalleDto peliculaSerieDetalleDto = new PeliculaSerieDetalleDto(null,
        "/img/imagen.jpg",
        "",
        LocalDate.now(),
        5,
        new HashSet<>());

    mockMvc.perform(
        post("/movies")
            .contentType(APPLICATION_JSON)
            .content(getMapper().writeValueAsString(peliculaSerieDetalleDto ))
    )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.titulo", Is.is("El titulo es obligatorio")));
  }

  private ObjectMapper getMapper() {
    var mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    mapper.registerModule(new JavaTimeModule());
    return mapper;
  }
}
