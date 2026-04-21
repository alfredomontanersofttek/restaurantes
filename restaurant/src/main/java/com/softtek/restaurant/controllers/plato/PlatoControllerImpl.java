package com.softtek.restaurant.controllers.plato;

import com.softtek.restaurant.model.Plato;
import com.softtek.restaurant.services.plato.PlatoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("restaurante/platos")
public class PlatoControllerImpl implements PlatoController {

    private final PlatoService platoService;

    public PlatoControllerImpl(PlatoService platoService) {
        this.platoService = platoService;
    }


    @Override
    @GetMapping("")
    public List<Plato> getPlatos() {
        List<Plato> platos = platoService.getPlatos();
        return platos;
    }

    @Override
    @GetMapping("/{id}")
    public Plato getPlatosById(@PathVariable String id) {
        Optional<Plato> plato = platoService.getPlatosById(Integer.parseInt(id));
        if (plato.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato no encontrado");
        } else {
            return plato.get();
        }
    }

    @Override
    @GetMapping("/{kcal}")
    public List<Plato> getPlatosByCalories(@PathVariable int calories) {
        List<Plato> platosByCalories = platoService.getPlatosByCalories(calories);
        return platosByCalories;
    }

    @PutMapping("/")
    @Override
    public Boolean addPlato(@RequestBody Plato plato) {
        return platoService.addPlato(plato);
    }

    @DeleteMapping("/{id}")
    @Override
    public Boolean deletePlato(@PathVariable int id) {
        boolean deleted = platoService.deletePlato(id);

        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato no encontrado");
        }

        return true;
    }

    @PostMapping("/")
    @Override
    public Boolean updatePlato(@RequestBody Plato plato) {
        return platoService.updatePlato(plato);
    }


}
