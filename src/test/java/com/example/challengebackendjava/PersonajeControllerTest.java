package com.example.challengebackendjava;

import com.example.challengebackendjava.dto.PersonajeDetalleDto;
import com.example.challengebackendjava.model.Personaje;
import com.example.challengebackendjava.service.PersonajeService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.HashSet;

import static com.example.challengebackendjava.TestHelper.getMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Dado un controller de personaje")
@WithMockUser
public class PersonajeControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  PersonajeService personajeService;

  @Transactional
  @Test
  @DisplayName("podemos crear un personaje con un payload valido")
  public void crearPersonaje() throws Exception {
    String nombre = "Nuevo Personaje";
    String imagen = "/img/personaje.jpg";
    int edad = 40;
    String historia = "Historia del personaje";
    float peso = 90F;

    Personaje personaje = new Personaje(null,
        imagen,
        nombre,
        edad,
        historia,
        peso,
        new HashSet<>()
    );

    mockMvc.perform(
            post("/characters")
                .contentType(APPLICATION_JSON)
                .content(getMapper().writeValueAsString(PersonajeDetalleDto.fromPersonaje(personaje)))
        )
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.nombre").value(nombre))
        .andExpect(jsonPath("$.imagen").value(imagen))
        .andExpect(jsonPath("$.edad").value(edad))
        .andExpect(jsonPath("$.historia").value(historia))
        .andExpect(jsonPath("$.peso").value(peso))
        .andExpect(jsonPath("$.peliculasSeries").isEmpty());
  }

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
            put("/characters/" + personaje.getId())
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
            post("/characters")
                .contentType(APPLICATION_JSON)
                .content("{\"nombre\": \"Nuevo personaje\"}")
        )
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.edad", Is.is("La edad es obligatoria")))
        .andExpect(jsonPath("$.peso", Is.is("El peso es obligatorio")))
        .andExpect(jsonPath("$.historia", Is.is("La historia es obligatoria")))
        .andExpect(jsonPath("$.imagen", Is.is("La imagen es obligatoria")));
  }

  @Test
  @DisplayName("podemos filtrar con una query")
  public void filtrarConParametros() throws Exception {
    mockMvc
        .perform(
            get("/characters?name=Mickey Mouse&age=40")
                .contentType(APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains("Mickey Mouse")))
        .andExpect(result -> assertFalse(result.getResponse().getContentAsString().contains("Minnie Mouse")));
  }
}
