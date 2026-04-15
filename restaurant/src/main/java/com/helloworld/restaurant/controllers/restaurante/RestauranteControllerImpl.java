package com.helloworld.restaurant.controllers.restaurante;

import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.model.Restaurante;
import com.helloworld.restaurant.services.restaurante.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("restaurante/locales")
public class RestauranteControllerImpl implements RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteControllerImpl(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @Override
    @GetMapping("")
    public List<Restaurante> getRestaurantes() {
        return restauranteService.getRestaurantes();
    }

    @Override
    @GetMapping("/{cif}")
    public Restaurante getRestauranteByCif(@PathVariable String cif) {
        return restauranteService.getRestauranteByCif(cif)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante no encontrado"));
    }

    @Override
    @GetMapping("/{cif}/platos")
    public List<Plato> getPlatosByRestaurante(@PathVariable String cif) {
        restauranteService.getRestauranteByCif(cif)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante no encontrado"));
        return restauranteService.getPlatosByRestaurante(cif);
    }

    @PutMapping("/{restauranteId}/platos")
    @Override
    public Boolean addPlato(@RequestBody Plato plato, @PathVariable int restauranteId) {
        return restauranteService.addPlato(plato, restauranteId);
    }

    @DeleteMapping("/{restauranteId}/platos/{platoId}")
    @Override
    public Boolean deletePlato(@PathVariable int platoId, @PathVariable int restauranteId) {
        boolean deleted = restauranteService.deletePlato(platoId, restauranteId);

        if (!deleted)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato no encontrado");
        return true;
    }
}
