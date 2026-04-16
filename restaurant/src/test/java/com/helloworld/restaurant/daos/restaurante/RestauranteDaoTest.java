package com.helloworld.restaurant.daos.restaurante;

import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.model.Restaurante;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteDaoTest {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @InjectMocks
    private RestauranteDaoImpl dao;

    private Restaurante restaurante;

    private final Plato plato = new Plato(123, "Cocido", 30.0, Plato.Categoria.SEGUNDO_PLATO, 540, false);

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante("A12345678", "La Mar Salada", "Calle Mayor, 1", "123456789");
    }

    @Test
    void addPlato_shouldReturnTrue_whenBothInsertsSucceed() {

        when(jdbcTemplate.update(
                anyString(),
                any(MapSqlParameterSource.class),
                any(KeyHolder.class)
        )).thenReturn(1);

        when(jdbcTemplate.update(
                anyString(),
                anyMap()
        )).thenReturn(1);

        Boolean result = dao.addPlato(plato, restaurante.getCif());

        assertTrue(result);

        verify(jdbcTemplate).update(anyString(), any(MapSqlParameterSource.class), any(KeyHolder.class));
        verify(jdbcTemplate).update(anyString(), anyMap());
    }

    @Test
    void deletePlato_shouldReturnTrue_whenDeleted() {
        when(jdbcTemplate.update(anyString(), anyMap())).thenReturn(1);

        Boolean result = dao.deletePlato(1, restaurante.getCif());

        assertTrue(result);
    }

    @Test
    void deletePlato_shouldReturnFalse_whenNothingDeleted() {
        when(jdbcTemplate.update(anyString(), anyMap())).thenReturn(0);

        Boolean result = dao.deletePlato(1, restaurante.getCif());

        assertFalse(result);
    }
}