package com.helloworld.restaurant.controllers.plato;

import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.services.plato.PlatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("restaurante/platos")

@Tag(name = "Platos", description = "Endpoints para la gestión completa del menú de platos del restaurante")
public class PlatoControllerImpl implements PlatoController {

    private final PlatoService platoService;

    public PlatoControllerImpl(PlatoService platoService) {
        this.platoService = platoService;
    }

    @Override
    @Operation(summary = "Obtener todos los platos", description = "Devuelve la lista completa de platos registrados")

    @ApiResponse(responseCode = "200", description = "Lista de platos obtenida correctamente")
    @GetMapping("")
    public List<Plato> getPlatos() {
        return platoService.getPlatos();
    }

    @Override
    @Operation(summary = "Buscar plato por ID", description = "Obtiene los detalles de un plato específico")
    // PARTE II: Documentamos las dos caras de la moneda (Éxito y Error)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato encontrado con éxito"),
            @ApiResponse(responseCode = "404", description = "No existe ningún plato con el ID proporcionado")
    })
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
    @Operation(summary = "Filtrar por calorías", description = "Retorna platos que coinciden con el nivel de calorías")

    @ApiResponse(responseCode = "200", description = "Búsqueda realizada (puede devolver lista vacía)")
    @GetMapping("/calorias/{calories}")
    public List<Plato> getPlatosByCalories(@PathVariable int calories) {
        return platoService.getPlatosByCalories(calories);
    }

    @Override
    @Operation(summary = "Añadir nuevo plato", description = "Registra un nuevo plato en el sistema")

    @ApiResponse(responseCode = "201", description = "Plato creado exitosamente")
    @PutMapping("/")
    public Boolean addPlato(@RequestBody Plato plato) {
        return platoService.addPlato(plato);
    }

    @Override
    @Operation(summary = "Eliminar plato", description = "Borra un plato permanentemente mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "El plato a eliminar no existe")
    })
    @DeleteMapping("/{id}")
    public Boolean deletePlato(@PathVariable int id) {
        boolean deleted = platoService.deletePlato(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato no encontrado");
        }
        return true;
    }

    @Override
    @Operation(summary = "Actualizar plato", description = "Modifica los datos de un plato existente")
    @ApiResponse(responseCode = "200", description = "Datos actualizados correctamente")

    @PostMapping("/")
    public Boolean updatePlato(@RequestBody Plato plato) {
        return platoService.updatePlato(plato);
    }
}