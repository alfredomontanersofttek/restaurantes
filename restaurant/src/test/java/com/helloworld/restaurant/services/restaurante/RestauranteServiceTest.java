package com.helloworld.restaurant.services.restaurante;

import com.helloworld.restaurant.daos.model.Restaurante;
import com.helloworld.restaurant.daos.restaurante.RestauranteDao;
import com.helloworld.restaurant.model.Plato;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteServiceTest {

    @Mock
    private RestauranteDao restauranteDao;

    @InjectMocks
    private RestauranteServiceImpl restauranteService;

    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante("A12345678", "La Mar Salada", "Calle Mayor, 1", "123456789");
    }

    @Test
    void getRestaurantes_debeRetornarListaDeRestaurantes() {
        when(restauranteDao.getRestaurantes()).thenReturn(List.of(restaurante));

        List<com.helloworld.restaurant.model.Restaurante> result = restauranteService.getRestaurantes();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCif()).isEqualTo("A12345678");
        assertThat(result.get(0).getNombre()).isEqualTo("La Mar Salada");
    }

    @Test
    void getRestauranteByCif_cuandoExiste_debeRetornarRestaurante() {
        when(restauranteDao.getRestauranteByCif("A12345678"))
                .thenReturn(Optional.of(restaurante));

        Optional<com.helloworld.restaurant.model.Restaurante> result =
                restauranteService.getRestauranteByCif("A12345678");

        assertThat(result).isPresent();
        assertThat(result.get().getCif()).isEqualTo("A12345678");
    }

    @Test
    void getRestauranteByCif_cuandoNoExiste_debeRetornarVacio() {
        when(restauranteDao.getRestauranteByCif("Z99999999"))
                .thenReturn(Optional.empty());

        Optional<com.helloworld.restaurant.model.Restaurante> result =
                restauranteService.getRestauranteByCif("Z99999999");

        assertThat(result).isEmpty();
    }

    @Test
    void getPlatosByRestaurante_debeRetornarPlatosDelLocal() {
        com.helloworld.restaurant.daos.model.Plato platoDAO =
                new com.helloworld.restaurant.daos.model.Plato(1, "Ensalada", 6.00, 1, 150, true);

        when(restauranteDao.getPlatosByRestaurante("A12345678"))
                .thenReturn(List.of(platoDAO));

        List<Plato> result = restauranteService.getPlatosByRestaurante("A12345678");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNombre()).isEqualTo("Ensalada");
        assertThat(result.get(0).getPrecio()).isEqualTo(6.00);
    }

    // ✅ ADD PLATO

    @Test
    void addPlato_cuandoSeAnade_debeRetornarTrue() {
        Plato plato = new Plato(1, "Ensalada", 6.00, Plato.Categoria.PRIMER_PLATO, 150, true);

        when(restauranteDao.addPlato(plato, "A12345678"))
                .thenReturn(true);

        boolean result = restauranteService.addPlato(plato, "A12345678");

        assertThat(result).isTrue();
    }

    @Test
    void addPlato_cuandoFalla_debeRetornarFalse() {
        Plato plato = new Plato(1, "Ensalada", 6.00, Plato.Categoria.PRIMER_PLATO, 150, true);

        when(restauranteDao.addPlato(plato, "A12345678"))
                .thenReturn(false);

        boolean result = restauranteService.addPlato(plato, "A12345678");

        assertThat(result).isFalse();
    }

    @Test
    void deletePlato_cuandoExiste_debeRetornarTrue() {
        when(restauranteDao.deletePlato(1, "A12345678"))
                .thenReturn(true);

        boolean result = restauranteService.deletePlato(1, "A12345678");

        assertThat(result).isTrue();
    }

    @Test
    void deletePlato_cuandoNoExiste_debeRetornarFalse() {
        when(restauranteDao.deletePlato(999, "A12345678"))
                .thenReturn(false);

        boolean result = restauranteService.deletePlato(999, "A12345678");

        assertThat(result).isFalse();
    }
}