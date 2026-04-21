package com.softtek.restaurant.services.restaurante;

import com.softtek.restaurant.model.Plato;
import com.softtek.restaurant.model.Restaurante;

import java.util.List;
import java.util.Optional;

public interface RestauranteService {
    List<Restaurante> getRestaurantes();

    Optional<Restaurante> getRestauranteByCif(String cif);

    List<Plato> getPlatosByRestaurante(String cif);

    Boolean addPlato(Plato plato, String restauranteId);

    Boolean deletePlato(int idPlato, String restauranteId);
}
