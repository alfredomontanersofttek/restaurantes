package com.helloworld.restaurant.daos.plato;

import com.helloworld.restaurant.daos.model.Plato;

import java.util.List;
import java.util.Optional;

public interface PlatoDao {
<<<<<<< HEAD
    List<Plato> getPlatos();

    Optional<Plato> getPlatosById(int id);

    List<Plato> getPlatosByCalories(int calories);
=======
	List<Plato> getPlatos();
	Optional<Plato> getPlatosById(int id);
	List<Plato> getPlatosByCalories(int calories);

	void deletePlato(int id);
 	void createPlato(Plato plato);
	void updatePlato(Plato plato);
>>>>>>> fa8847f (Metodo delete , create y update de plato)
}
