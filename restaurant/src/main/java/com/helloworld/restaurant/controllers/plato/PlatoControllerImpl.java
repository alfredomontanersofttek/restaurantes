package com.helloworld.restaurant.controllers.plato;

import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.services.plato.PlatoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("restaurante/platos")
public class PlatoControllerImpl implements PlatoController
{

    private static final String COOKIE_NAME = "calorie_filter";
    private static final int COOKIE_MAX_AGE = 60;
    private final PlatoService platoService;

    public PlatoControllerImpl(PlatoService platoService) {
        this.platoService = platoService;
    }


    @GetMapping("")
    public List<Plato> getPlatos(HttpServletRequest request, @RequestParam(required = false) Integer calories, HttpServletResponse response)
    {
        if (calories != null)
        {
            Cookie cookie = new Cookie(COOKIE_NAME, String.valueOf(calories));
            cookie.setMaxAge(COOKIE_MAX_AGE);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }

        int activeFilter = Optional.ofNullable(calories).or(() -> getCalorieFilterFromCookie(request)).orElse(Integer.MAX_VALUE);

        return activeFilter == Integer.MAX_VALUE
                ? platoService.getPlatos()
                : platoService.getPlatosByCalories(activeFilter);
    }

    public Optional<Integer> getCalorieFilterFromCookie(HttpServletRequest request)
    {
        return Optional.ofNullable(request.getCookies())
                .map(Arrays::stream)
                .orElseGet(Stream::empty)
                .filter(c -> COOKIE_NAME.equals(c.getName()))
                .map(Cookie::getValue)
                .filter(v -> !v.isBlank())
                .map(Integer::parseInt)
                .findFirst();
    }

    @Override
    @DeleteMapping("/calories/filter")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCalorieFilter(HttpServletResponse response)
    {
        Cookie cookie = new Cookie(COOKIE_NAME, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    @GetMapping("/{id}")
    public Plato getPlatosById(@PathVariable String id) {
        Optional<Plato> plato = platoService.getPlatosById(Integer.parseInt(id));
        if (plato.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato no encontrado");
        } else {
            return plato.get();
        }
    }


    @PutMapping("/")
    @Override
    public Boolean addPlato(@RequestBody Plato plato) {
        return platoService.addPlato(plato);
    }

    @DeleteMapping("/{id}")
    @Override
    public Boolean deletePlato(@PathVariable int id) {
        boolean deleted = platoService.deletePlato(id);

        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato no encontrado");
        }

        return true;
    }

    @PostMapping("/")
    @Override
    public Boolean updatePlato(@RequestBody Plato plato) {
        return platoService.updatePlato(plato);
    }


}
