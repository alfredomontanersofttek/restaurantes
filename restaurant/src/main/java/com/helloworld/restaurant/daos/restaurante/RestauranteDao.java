package com.helloworld.restaurant.daos.restaurante;

import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.daos.model.Restaurante;

import java.util.List;
import java.util.Optional;

public interface RestauranteDao {
    List<Restaurante> getRestaurantes();

    Optional<Restaurante> getRestauranteByCif(String cif);

    List<com.helloworld.restaurant.daos.model.Plato> getPlatosByRestaurante(String cif);

    Boolean addPlato(Plato plato, String restauranteId);

    Boolean deletePlato(int idPlato, String restauranteId);
}
