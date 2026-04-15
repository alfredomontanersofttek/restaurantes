package com.helloworld.restaurant.controllers.plato;

import com.helloworld.restaurant.model.Plato;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface PlatoController {
    List<Plato> getPlatos();

    Plato getPlatosById(String id);

    List<Plato> getPlatosByCalories(int calories);


}
