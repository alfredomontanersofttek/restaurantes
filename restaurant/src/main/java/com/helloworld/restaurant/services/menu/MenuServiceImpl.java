package com.helloworld.restaurant.services.menu;

import com.helloworld.restaurant.model.Menu;
import com.helloworld.restaurant.model.MenuFilter;
import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.services.plato.PlatoService;
import com.helloworld.restaurant.services.restaurante.RestauranteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService
{

    private final RestauranteService restauranteService;

    public MenuServiceImpl(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @Override
    public List<Menu> getMenusByRestaurante(String cif)
    {
        List<Plato> platos = restauranteService.getPlatosByRestaurante(cif);
        List<Plato> entrantes = platos.stream().filter(plato -> plato.getCategoria().equals(Plato.Categoria.PRIMER_PLATO)).toList();
        List<Plato> principales = platos.stream().filter(plato -> plato.getCategoria().equals(Plato.Categoria.SEGUNDO_PLATO)).toList();
        List<Plato> postres = platos.stream().filter(plato -> plato.getCategoria().equals(Plato.Categoria.POSTRE)).toList();

        List<Menu> menus = new ArrayList<>();

        for (Plato entrante : entrantes)
        {
            for (Plato principal : principales)
            {
                for (Plato postre : postres)
                {
                    menus.add(new Menu(entrante, principal, postre));
                }
            }
        }
        return menus;
    }

    /*@Override
    public Menu getMenuRandom() {
        List<Menu> menus = generarMenus();

        if (menus.isEmpty()) {
            throw new RuntimeException("No hay menús disponibles");
        }

        int index = random.nextInt(menus.size());
        return menus.get(index);
    }*/

    @Override
    public List<Menu> getMenusByFilter(String cif, MenuFilter filter)
    {
        List<Menu> menus = getMenusByRestaurante(cif);

        switch (filter)
        {
            case LOWCOST:
                double media = menus.stream().mapToDouble(Menu::getPrecioTotal).average().orElse(0);
                return menus.stream().filter(m -> m.getPrecioTotal() <= media).toList();
            case HEALTHY:
                double mediaCalorias = menus.stream().mapToDouble(Menu::getCaloriasTotales).average().orElse(0);
                return menus.stream().filter(m -> m.getCaloriasTotales() < mediaCalorias).toList();
            case VEGAN:
                return menus.stream().filter(m->m.getPrimerPlato().isVegano() &&
                        m.getSegundoPlato().isVegano() && m.getPostre().isVegano()).toList();
            case ALL:
            default:
                return menus;
        }
    }
}