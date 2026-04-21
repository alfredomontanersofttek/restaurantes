package com.softtek.restaurant.controllers.restaurante;

import com.softtek.restaurant.model.Plato;
import com.softtek.restaurant.model.Restaurante;

import java.util.List;

public interface RestauranteController {
    List<Restaurante> getRestaurantes();

    Restaurante getRestauranteByCif(String cif);

    List<Plato> getPlatosByRestaurante(String cif);

    Boolean addPlato(Plato plato, String restauranteId);

    Boolean deletePlato(int id, String restauranteId);
}
