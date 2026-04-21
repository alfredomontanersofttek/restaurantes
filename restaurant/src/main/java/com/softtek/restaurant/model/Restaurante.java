package com.softtek.restaurant.model;

import lombok.Data;

@Data
public class Restaurante {

    private final String cif;
    private final String nombre;
    private final String direccion;
    private final String telefono;

    public static Restaurante fromRestauranteDAO(com.softtek.restaurant.daos.model.Restaurante restaurante) {
        return new Restaurante(
                restaurante.cif(),
                restaurante.nombre(),
                restaurante.direccion(),
                restaurante.telefono()
        );
    }
}
