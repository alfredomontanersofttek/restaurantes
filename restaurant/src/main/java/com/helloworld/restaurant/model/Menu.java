package com.helloworld.restaurant.model;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Schema(example =
        "{" +
        "\"primero\": {\"id\": 1, \"nombre\": \"Ensalada\", \"precio\": 6.00, \"categoria\": \"1\", \"calorias\": 150, \"vegano\": true}," +
        "\"segundo\": {\"id\": 4, \"nombre\": \"Hamburguesa\", \"precio\": 15.00, \"categoria\": \"2\", \"calorias\": 650, \"vegano\": false}," +
        "\"postre\": {\"id\": 8, \"nombre\": \"Fruta\", \"precio\": 6.00, \"categoria\": \"3\", \"calorias\": 80, \"vegano\": true}" +
        "}")
@Data
public class Menu
{
    @Schema(description = "Plato de categoría PRIMER_PLATO")
    @NotNull
    private final Plato primerPlato;
    @Schema(description = "Plato de categoría SEGUNDO_PLATO")
    @NotNull
    private final Plato segundoPlato;
    @Schema(description = "Plato de categoría POSTRE")
    @NotNull
    private final Plato postre;

    @Operation(summary = "suma total de los precios individuales de los tres platos del menú")
    @Positive
    public double getPrecioTotal() {
        return primerPlato.getPrecio() +
                segundoPlato.getPrecio() +
                postre.getPrecio();
    }

    @Operation(summary = "suma total de las calorías individuales de los tres platos del menú")
    @Positive
    public int getCaloriasTotales() {
        return primerPlato.getCalorias() +
                segundoPlato.getCalorias() +
                postre.getCalorias();
    }
}