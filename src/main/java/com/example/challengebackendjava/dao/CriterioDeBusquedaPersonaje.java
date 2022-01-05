package com.example.challengebackendjava.dao;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Builder
@Data
public class CriterioDeBusquedaPersonaje {
  private Optional<String> name;
  private Optional<Integer> edad;
  private List<Long> idPelis;
  private Optional<Float> peso;
}
