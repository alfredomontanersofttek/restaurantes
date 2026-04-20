package com.helloworld.restaurant.controllers.local;

import com.helloworld.restaurant.model.Local;
import com.helloworld.restaurant.services.local.LocalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Locales", description = "Gestión de restaurantes")
@RestController
@RequestMapping("restaurante/locales")
public class LocalControllerImpl implements LocalController {

    private final LocalService localService;

    public LocalControllerImpl(LocalService localService) {
        this.localService = localService;
    }


    @Override
    @Operation(summary = "Crear un nuevo local")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Local creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("")
    public ResponseEntity<Void> createLocal(@RequestBody Local local) {
        localService.createLocal(local);
        return ResponseEntity
                .created(URI.create("/restaurante/locales/" + local.getCif()))
                .build();
    }

    @Override
    @Operation(summary = "Actualizar un local")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Local actualizado"),
            @ApiResponse(responseCode = "404", description = "Local no encontrado")
    })
    @PutMapping("/{cif}")
    public void updateLocal(@PathVariable String cif, @RequestBody Local local) {
        localService.updateLocal(cif, local);
    }

    @Override
    @Operation(summary = "Eliminar un local")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Local eliminado"),
            @ApiResponse(responseCode = "404", description = "Local no encontrado")
    })
    @DeleteMapping("/{cif}")
    public void deleteLocal(@PathVariable String cif) {
        localService.deleteLocal(cif);
    }
}