package com.helloworld.restaurant.controllers;

import com.helloworld.restaurant.controllers.restaurante.RestauranteControllerImpl;
import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.model.Restaurante;
import com.helloworld.restaurant.services.restaurante.RestauranteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteControllerTest {

    @Mock
    private RestauranteService restauranteService;

    @InjectMocks
    private RestauranteControllerImpl restauranteController;

    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante("A12345678", "La Mar Salada", "Calle Mayor, 1", "123456789");
    }

    @Test
    void getRestaurantes_debeRetornarListaCompleta() {
        when(restauranteService.getRestaurantes()).thenReturn(List.of(restaurante));

        List<Restaurante> result = restauranteController.getRestaurantes();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNombre()).isEqualTo("La Mar Salada");
    }

    @Test
    void getRestauranteByCif_cuandoExiste_debeRetornarRestaurante() {
        when(restauranteService.getRestauranteByCif("A12345678")).thenReturn(Optional.of(restaurante));

        Restaurante result = restauranteController.getRestauranteByCif("A12345678");

        assertThat(result.getCif()).isEqualTo("A12345678");
        assertThat(result.getDireccion()).isEqualTo("Calle Mayor, 1");
    }

    @Test
    void getRestauranteByCif_cuandoNoExiste_debeLanzar404() {
        when(restauranteService.getRestauranteByCif("Z99999999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> restauranteController.getRestauranteByCif("Z99999999"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Restaurante no encontrado");
    }

    @Test
    void getPlatosByRestaurante_cuandoExiste_debeRetornarPlatos() {
        Plato plato = new Plato(1, "Ensalada", 6.00, Plato.Categoria.PRIMER_PLATO, 150,true);
        when(restauranteService.getRestauranteByCif("A12345678")).thenReturn(Optional.of(restaurante));
        when(restauranteService.getPlatosByRestaurante("A12345678")).thenReturn(List.of(plato));

        List<Plato> result = restauranteController.getPlatosByRestaurante("A12345678");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNombre()).isEqualTo("Ensalada");
    }

    @Test
    void getPlatosByRestaurante_cuandoRestauranteNoExiste_debeLanzar404() {
        when(restauranteService.getRestauranteByCif("Z99999999")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> restauranteController.getPlatosByRestaurante("Z99999999"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Restaurante no encontrado");
    }
}
