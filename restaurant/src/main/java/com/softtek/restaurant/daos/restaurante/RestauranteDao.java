package com.softtek.restaurant.daos.restaurante;

import com.softtek.restaurant.model.Plato;
import com.softtek.restaurant.daos.model.Restaurante;

import java.util.List;
import java.util.Optional;

public interface RestauranteDao {
    List<Restaurante> getRestaurantes();

    Optional<Restaurante> getRestauranteByCif(String cif);

    List<com.softtek.restaurant.daos.model.Plato> getPlatosByRestaurante(String cif);

    Boolean addPlato(Plato plato, String restauranteId);

    Boolean deletePlato(int idPlato, String restauranteId);
}
