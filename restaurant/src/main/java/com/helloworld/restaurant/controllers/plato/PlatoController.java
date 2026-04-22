package com.helloworld.restaurant.controllers.plato;

import com.helloworld.restaurant.model.Plato;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public interface PlatoController {


    List<Plato> getPlatos(HttpServletRequest request, @RequestParam(required = false) Integer calories, HttpServletResponse response);

    // List<Plato> getPlatosByCalories(@PathVariable int kcal, HttpServletResponse response);

    void clearCalorieFilter(HttpServletResponse response);

    Plato getPlatosById(String id);

    Boolean addPlato(Plato plato);

    Boolean deletePlato(int id);

    Boolean updatePlato(Plato plato);

    Optional<Integer> getCalorieFilterFromCookie(HttpServletRequest request);
}
