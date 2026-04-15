package com.helloworld.restaurant.daos.model;



public record Local(
        String cif,
        String nombre,
        String direccion,
        String telefono
) {}