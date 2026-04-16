package com.helloworld.restaurant.controllers.plato;

import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.services.plato.PlatoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
        return platoService.getPlatos();
    }

    @Override
    @GetMapping("/{id}")
    public Plato getPlatosById(@PathVariable String id) {
        Optional<Plato> plato = platoService.getPlatosById(Integer.parseInt(id));
        return plato.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato no encontrado"));
    }

    @Override
    @GetMapping("/kcal/{calories}")
    public List<Plato> getPlatosByCalories(@PathVariable int calories) {
        return platoService.getPlatosByCalories(calories);
    }

    @PostMapping("/")
    @Override
    public ResponseEntity<Void> addPlato(@RequestBody Plato plato) {
        boolean created = platoService.addPlato(plato);
        if (created) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("{id}")
                    .buildAndExpand(plato.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/{id}")
    @Override
    public Boolean deletePlato(@PathVariable int id) {
        if (!platoService.deletePlato(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return true;
    }

    @PutMapping("/")
    @Override
    public Boolean updatePlato(@RequestBody Plato plato) {
        return platoService.updatePlato(plato);
    }
}