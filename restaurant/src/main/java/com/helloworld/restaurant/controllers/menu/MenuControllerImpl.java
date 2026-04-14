package com.helloworld.restaurant.controllers.menu;

import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.services.menu.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurante/menus")
public class MenuControllerImpl implements MenuController
{

    private final MenuService menuService;


    public MenuControllerImpl(MenuService menuService)
    {
        this.menuService = menuService;
    }

    @Override
    @GetMapping("")
    public List<Menu> getMenus(@RequestParam(required = false) Boolean lowcost)
    {
        List<Menu> result;

        if (Boolean.TRUE.equals(lowcost))
        {
            result = menuService.getMenusLowCost();
        } else
        {
            result = menuService.generarMenus();
        }

        return result;
    }

    @Override
    @GetMapping("/random")
    public Menu getMenuRandom() {
        return menuService.getMenuRandom();
    }
}
