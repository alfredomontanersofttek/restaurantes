package com.softtek.restaurant.services.menu;

import com.softtek.restaurant.model.Menu;
import com.softtek.restaurant.model.MenuFilter;

import java.util.List;

public interface MenuService
{
    List<Menu> getMenusByRestaurante(String cif);

    public List<Menu> getMenusByFilter(String cif, MenuFilter filter);
}
