package com.softtek.restaurant.services.restaurante;

import com.softtek.restaurant.daos.restaurante.RestauranteDao;
import com.softtek.restaurant.model.Plato;
import com.softtek.restaurant.model.Restaurante;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteServiceImpl implements RestauranteService {

    private final RestauranteDao restauranteDao;

    public RestauranteServiceImpl(RestauranteDao restauranteDao) {
        this.restauranteDao = restauranteDao;
    }

    @Override
    public List<Restaurante> getRestaurantes() {
        return restauranteDao.getRestaurantes().stream()
                .map(Restaurante::fromRestauranteDAO)
                .toList();
    }

    @Override
    public Optional<Restaurante> getRestauranteByCif(String cif) {
        return restauranteDao.getRestauranteByCif(cif)
                .map(Restaurante::fromRestauranteDAO);
    }

    @Override
    public List<Plato> getPlatosByRestaurante(String cif) {
        return restauranteDao.getPlatosByRestaurante(cif).stream()
                .map(Plato::fromPlatoDAO)
                .toList();
    }

    @Override
    public Boolean addPlato(Plato plato, String restauranteId) {
        return restauranteDao.addPlato(plato, restauranteId);
    }

    @Override
    public Boolean deletePlato(int idPlato, String restauranteId) {
        return restauranteDao.deletePlato(idPlato, restauranteId);
    }
}
