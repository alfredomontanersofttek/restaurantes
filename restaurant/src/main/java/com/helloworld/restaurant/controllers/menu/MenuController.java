package com.helloworld.restaurant.controllers.menu;

import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.model.MenuFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MenuController
{

    List<Menu> getMenus(String cif, MenuFilter filter);

    //Menu getMenuRandom();
}
