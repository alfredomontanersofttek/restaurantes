package com.helloworld.restaurant.controllers.menu;

import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.model.MenuFilter;
import com.helloworld.restaurant.services.menu.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurante/locales/{cif}/menus")
public class MenuControllerImpl {

    private final MenuService menuService;

    public MenuControllerImpl(MenuService menuService) {
        this.menuService = menuService;
    }

    @Operation(summary="Listado de menús, con o sin filtro")
    @GetMapping
    public List<Menu> getMenus(@Parameter(description = "cif de identificación del local",required = true) @PathVariable String cif, @Parameter(description = "Filtro de menús",required = false)@RequestParam(defaultValue = "ALL") MenuFilter filter)
    {
        return menuService.getMenusByFilter(cif, filter);
    }
}