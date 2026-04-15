package com.helloworld.restaurant.services.plato;

import com.helloworld.restaurant.daos.plato.PlatoDao;
import com.helloworld.restaurant.model.Plato;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlatoServiceImpl implements PlatoService {

	private final PlatoDao platoDao;

	public PlatoServiceImpl(PlatoDao platoDao) {
		this.platoDao = platoDao;
	}

	@Override
	public List<Plato> getPlatos() {
		return platoDao.getPlatos().stream().map(Plato::fromPlatoDAO).toList();
	}


	@Override
	public Optional<Plato> getPlatosById(int id) {
		return platoDao.getPlatosById(id).map(Plato::fromPlatoDAO);
	}

	@Override
	public List<Plato> getPlatosByCalories(int kcal)
	{
		return platoDao.getPlatos().stream().filter(p -> p.calorias() < kcal).map(Plato::fromPlatoDAO).toList();
	}

	@Override
	public Boolean addPlato(Plato plato) {
		return platoDao.addPlato(plato.toPlatoDAO());
	}

	@Override
	public Boolean deletePlato(int id) {
		return platoDao.deletePlato(id);
	}

	@Override
	public Boolean updatePlato(Plato plato) {
		return platoDao.updatePlato(plato.toPlatoDAO());
	}
}
