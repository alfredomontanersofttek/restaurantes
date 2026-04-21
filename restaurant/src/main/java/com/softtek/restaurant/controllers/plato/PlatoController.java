package com.softtek.restaurant.controllers.plato;

import com.softtek.restaurant.model.Plato;

import java.util.List;

public interface PlatoController {
    List<Plato> getPlatos();

    Plato getPlatosById(String id);

    List<Plato> getPlatosByCalories(int calories);

    Boolean addPlato(Plato plato);

    Boolean deletePlato(int id);

    Boolean updatePlato(Plato plato);
}
