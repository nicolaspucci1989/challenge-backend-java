package com.example.challengebackendjava;


import com.example.challengebackendjava.dto.PeliculaSerieListDto;
import com.example.challengebackendjava.service.PeliculaSerieService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    Set<PeliculaSerieListDto> peliculaSerieListDtos = getMapper().readValue(response.getContentAsString(), new TypeReference<Set<PeliculaSerieListDto>>(){});
    PeliculaSerieListDto peliculaSerieListDto = peliculaSerieListDtos.stream().findFirst().get();
    assertNotNull(peliculaSerieListDto.getId());
    assertNotNull(peliculaSerieListDto.getImagen());
    assertNotNull(peliculaSerieListDto.getTitulo());
    assertNotNull(peliculaSerieListDto.getFechaDeCreacion());
  }

  @Transactional
  @Test
  @DisplayName("podemos obtener la informacion de un personaje")
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


  private ObjectMapper getMapper() {
    var mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    mapper.registerModule(new JavaTimeModule());
    return mapper;
  }
}
