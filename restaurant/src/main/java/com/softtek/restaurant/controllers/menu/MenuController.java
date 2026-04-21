package com.softtek.restaurant.controllers.menu;

import com.softtek.restaurant.model.Menu;
import com.softtek.restaurant.model.MenuFilter;

import java.util.List;

public interface MenuController
{

    List<Menu> getMenus(String cif, MenuFilter filter);

    //Menu getMenuRandom();
}
