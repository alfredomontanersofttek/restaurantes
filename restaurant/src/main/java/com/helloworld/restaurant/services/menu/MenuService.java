package com.helloworld.restaurant.services.menu;

import com.helloworld.restaurant.model.Menu;

import java.util.List;

public interface MenuService
{
    List<Menu> generarMenus();

    Menu getMenuRandom();

    List<Menu> getMenusLowCost();
}
