package com.helloworld.restaurant.services.plato;

import com.helloworld.restaurant.model.Plato;

import java.util.List;
import java.util.Optional;

public interface PlatoService {
	List<Plato> getPlatos();
	Optional<Plato> getPlatosById(int id);
	List<Plato> getPlatosByCalories(int kcal);
	Boolean addPlato(Plato plato);
	Boolean deletePlato(int id);
	Boolean updatePlato(Plato plato);
}
