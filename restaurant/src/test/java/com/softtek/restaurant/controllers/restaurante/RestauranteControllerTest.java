package com.softtek.restaurant.controllers.restaurante;

import com.softtek.restaurant.model.Plato;
import com.softtek.restaurant.model.Restaurante;
import com.softtek.restaurant.services.restaurante.RestauranteService;
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

    private Plato plato = new Plato(123, "Cocido", 30.0, Plato.Categoria.SEGUNDO_PLATO, 540, false);

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
        when(restauranteService.getRestauranteByCif("A12345678"))
                .thenReturn(Optional.of(restaurante));

        Restaurante result = restauranteController.getRestauranteByCif("A12345678");

        assertThat(result.getCif()).isEqualTo("A12345678");
    }

    @Test
    void getRestauranteByCif_cuandoNoExiste_debeLanzar404() {
        when(restauranteService.getRestauranteByCif("Z99999999"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> restauranteController.getRestauranteByCif("Z99999999"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Restaurante no encontrado");
    }

    @Test
    void getPlatosByRestaurante_cuandoExiste_debeRetornarPlatos() {
        Plato plato = new Plato(1, "Ensalada", 6.00, Plato.Categoria.PRIMER_PLATO, 150, true);

        when(restauranteService.getRestauranteByCif("A12345678"))
                .thenReturn(Optional.of(restaurante));
        when(restauranteService.getPlatosByRestaurante("A12345678"))
                .thenReturn(List.of(plato));

        List<Plato> result = restauranteController.getPlatosByRestaurante("A12345678");

        assertThat(result).hasSize(1);
    }

    @Test
    void getPlatosByRestaurante_cuandoRestauranteNoExiste_debeLanzar404() {
        when(restauranteService.getRestauranteByCif("Z99999999"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> restauranteController.getPlatosByRestaurante("Z99999999"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Restaurante no encontrado");
    }

    @Test
    void addPlato_cuandoSeAnade_debeRetornarTrue() {
        when(restauranteService.addPlato(plato, restaurante.getCif()))
                .thenReturn(true);

        boolean result = restauranteController.addPlato(plato, restaurante.getCif());

        assertThat(result).isTrue();
    }

    @Test
    void addPlato_cuandoFalla_debeLanzar404() {
        when(restauranteService.addPlato(plato, restaurante.getCif()))
                .thenReturn(false);

        assertThatThrownBy(() -> restauranteController.addPlato(plato, restaurante.getCif()))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("No se pudo añadir el plato.");
    }

    @Test
    void deletePlato_cuandoSeElimina_debeDevolverTrue() {
        when(restauranteService.deletePlato(1, restaurante.getCif()))
                .thenReturn(true);

        boolean result = restauranteController.deletePlato(1, restaurante.getCif());

        assertThat(result).isTrue();
    }

    @Test
    void deletePlato_cuandoNoExiste_debeLanzar404() {
        when(restauranteService.deletePlato(999, restaurante.getCif()))
                .thenReturn(false);

        assertThatThrownBy(() -> restauranteController.deletePlato(999, restaurante.getCif()))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Plato no encontrado.");
    }
}