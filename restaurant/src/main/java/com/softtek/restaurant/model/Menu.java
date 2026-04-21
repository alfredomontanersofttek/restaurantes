package com.softtek.restaurant.model;

import lombok.Data;

@Data
public class Menu
{

    private final Plato primerPlato;
    private final Plato segundoPlato;
    private final Plato postre;

    public double getPrecioTotal() {
        return primerPlato.getPrecio() +
                segundoPlato.getPrecio() +
                postre.getPrecio();
    }

    public int getCaloriasTotales() {
        return primerPlato.getCalorias() +
                segundoPlato.getCalorias() +
                postre.getCalorias();
    }
}