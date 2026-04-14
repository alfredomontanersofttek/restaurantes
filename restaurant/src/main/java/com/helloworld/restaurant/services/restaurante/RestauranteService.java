package com.helloworld.restaurant.services.restaurante;

import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.model.Restaurante;

import java.util.List;
import java.util.Optional;

public interface RestauranteService {
    List<Restaurante> getRestaurantes();
    Optional<Restaurante> getRestauranteByCif(String cif);
    List<Plato> getPlatosByRestaurante(String cif);
}
