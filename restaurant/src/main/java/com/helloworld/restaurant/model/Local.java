package com.helloworld.restaurant.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Schema(description = "Modelo de un restaurante")
public class Local {
    @NotBlank
    @Schema(description = "CIF del restaurante", example = "A12345678")
    private String cif;
    @NotBlank
    @Schema(description = "Nombre del restaurante", example = "La Mar Salada")
    private String nombre;
    @NotBlank
    @Schema(description = "Dirección", example = "Calle Mayor 1")
    private String direccion;
    @NotBlank
    @Schema(description = "Teléfono", example = "123456789")
    private String telefono;

    private List<Plato> platos = new ArrayList<>();

    public Local(String cif, String nombre, String direccion, String telefono) {
        this.cif = cif;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }


}