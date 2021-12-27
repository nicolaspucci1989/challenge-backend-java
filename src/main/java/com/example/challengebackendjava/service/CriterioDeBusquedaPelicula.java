package com.example.challengebackendjava.service;

import com.example.challengebackendjava.controller.OrderEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@Builder
public class CriterioDeBusquedaPelicula {
  Optional<String> name;
  List<Long> idGenre;
  Optional<OrderEnum> order;
}
