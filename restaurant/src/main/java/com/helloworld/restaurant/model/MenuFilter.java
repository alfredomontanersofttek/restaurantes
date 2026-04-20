package com.helloworld.restaurant.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Filtro para los menús")
public enum MenuFilter
{
    ALL,
    LOWCOST,
    HEALTHY,
    GOURMET,
    VEGAN
}
