package com.helloworld.restaurant.services.menu;

import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.model.MenuFilter;

import java.util.List;

public interface MenuService
{
    List<Menu> getMenusByRestaurante(String cif);

    public List<Menu> getMenusByFilter(String cif, MenuFilter filter);
}
