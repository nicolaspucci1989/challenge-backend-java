package com.example.challengebackendjava;

import com.example.challengebackendjava.dto.PersonajeDetalleDto;
import com.example.challengebackendjava.model.Personaje;
import com.example.challengebackendjava.service.PersonajeService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de personaje")
public class PersonajeControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  PersonajeService personajeService;

  @Transactional
  @Test
  @DisplayName("podemos actualizar la informacion de un personaje")
  public void actualizarPersonaje() throws Exception {
    String nombre = "nuevo nombre";
    String imagen = "/img/nueva_imagen.jpg";
    int edad = 40;
    float peso = 90f;

    Long id = personajeService.all().get(0).getId();
    Personaje personaje = personajeService.findById(id);

    personaje.setNombre(nombre);
    personaje.setImagen(imagen);
    personaje.setEdad(edad);
    personaje.setPeso(peso);


    MockHttpServletResponse response = mockMvc.perform(
            MockMvcRequestBuilders.put("/characters/" + personaje.getId())
                .contentType(APPLICATION_JSON)
                .content(getMapper().writeValueAsString(PersonajeDetalleDto.fromPersonaje(personaje)))
        )
        .andReturn().getResponse();

    assertEquals(200, response.getStatus());

    var personajeActualizado = personajeService.findById(id);

    assertEquals(nombre, personajeActualizado.getNombre());
    assertEquals(imagen, personajeActualizado.getImagen());
    assertEquals(edad, personajeActualizado.getEdad());
    assertEquals(peso, personajeActualizado.getPeso());
  }

  @Transactional
  @Test
  @DisplayName("al intentar crear un personaje no valido devuelve 400")
  public void crearPersonajeNoValido() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/characters")
                .contentType(APPLICATION_JSON)
                .content("{\"nombre\": \"Nuevo personaje\"}")
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.edad", Is.is("La edad es obligatoria")))
        .andExpect(jsonPath("$.peso", Is.is("El peso es obligatorio")))
        .andExpect(jsonPath("$.historia", Is.is("La historia es obligatoria")))
        .andExpect(jsonPath("$.imagen", Is.is("La imagen es obligatoria")));
  }

  private ObjectMapper getMapper() {
    var mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    mapper.registerModule(new JavaTimeModule());
    return mapper;
  }

}
