package com.helloworld.restaurant.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Local {

    private String cif;
    private String nombre;
    private String direccion;
    private String telefono;

    private List<Plato> platos = new ArrayList<>();

    public Local(String cif, String nombre, String direccion, String telefono) {
        this.cif = cif;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }


}