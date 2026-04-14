package com.helloworld.restaurant.controllers.menu;

import com.helloworld.restaurant.model.Menu;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MenuController
{

    List<Menu> getMenus(@RequestParam(required = false) Boolean lowcost);

    Menu getMenuRandom();
}
