package com.helloworld.restaurant.controllers.menu;

import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.model.MenuFilter;
import com.helloworld.restaurant.services.menu.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{cif}/menus")
public class MenuControllerImpl {

    private final MenuService menuService;

    public MenuControllerImpl(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public List<Menu> getMenus(@PathVariable String cif, MenuFilter filter)
    {
        return menuService.getMenusByFilter(cif, filter);
    }
}