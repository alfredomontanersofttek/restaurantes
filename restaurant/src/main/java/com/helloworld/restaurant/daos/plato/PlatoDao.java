package com.helloworld.restaurant.daos.plato;

import com.helloworld.restaurant.daos.model.Plato;

import java.util.List;
import java.util.Optional;

public interface PlatoDao {
	List<Plato> getPlatos();
	Optional<Plato> getPlatosById(int id);
	List<Plato> getPlatosByCalories(int calories);

	void deletePlato(int id);
 	void createPlato(Plato plato);
	void updatePlato(Plato plato);
}
