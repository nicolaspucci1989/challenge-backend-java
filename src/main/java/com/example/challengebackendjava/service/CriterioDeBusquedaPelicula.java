package com.example.challengebackendjava.service;

import com.example.challengebackendjava.controller.OrderEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@Builder
public class CriterioDeBusquedaPelicula {
  private Optional<String> name;
  private List<Long> idGenre;
  private Optional<OrderEnum> order;
}
