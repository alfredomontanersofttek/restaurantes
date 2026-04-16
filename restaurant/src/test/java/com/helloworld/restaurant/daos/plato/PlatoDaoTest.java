package com.helloworld.restaurant.daos.plato;

import com.helloworld.restaurant.daos.model.Plato;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class PlatoDaoTest {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @InjectMocks
    private PlatoDaoImpl daoImpl;

     @BeforeEach
    void setup(){
         MockitoAnnotations.openMocks(this);
     }

     @Test
    void shouldGetPlatos(){
         List<Plato> mockPlatos = List.of(
                 new Plato(1, "Ensalada César", 8.99, 1, 250, false),
                 new Plato(2, "Sopa de Tomate", 6.50, 1, 150, true)
         );

         when(jdbcTemplate.query(anyString(), any(RowMapper.class))).
                 thenReturn(mockPlatos);

            List<Plato> result = daoImpl.getPlatos();

            assertThat(result).hasSize(2);
            verify(jdbcTemplate).query(anyString(), any(RowMapper.class));
     }

    @Test
    void shouldGetPlatoById() {
        Plato mockPlato = new Plato(1, "Ensalada", 6.0, 1, 150, true);

        when(jdbcTemplate.queryForObject(anyString(), any(Map.class), any(RowMapper.class)))
                .thenReturn(mockPlato);

        Optional<Plato> result = daoImpl.getPlatosById(1);

        assertThat(result).isPresent();
        assertThat(result.get().nombre()).isEqualTo("Ensalada");
    }

    @Test
    void shouldReturnEmptyWhenPlatoNotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(Map.class), any(RowMapper.class)))
                .thenThrow(new org.springframework.dao.EmptyResultDataAccessException(1));

        Optional<Plato> result = daoImpl.getPlatosById(99);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldGetPlatosByCalories() {
        List<Plato> mockPlatos = List.of(
                new Plato(1, "Ensalada", 6.0, 1, 150, true)
        );

        when(jdbcTemplate.query(anyString(), any(Map.class), any(RowMapper.class)))
                .thenReturn(mockPlatos);

        List<Plato> result = daoImpl.getPlatosByCalories(200);

        assertThat(result).hasSize(1);
    }

    @Test
    void shouldAddPlato() {
        when(jdbcTemplate.update(anyString(), any(Map.class)))
                .thenReturn(1);

        Plato plato = new Plato(1, "Test", 10.0, 1, 100, true);

        Boolean result = daoImpl.addPlato(plato);

        assertThat(result).isTrue();
        verify(jdbcTemplate).update(anyString(), any(Map.class));
    }

    @Test
    void shouldDeletePlato() {
        when(jdbcTemplate.update(anyString(), any(Map.class)))
                .thenReturn(1);

        Boolean result = daoImpl.deletePlato(1);

        assertThat(result).isTrue();
    }

    @Test
    void shouldUpdatePlato() {
        when(jdbcTemplate.update(anyString(), any(Map.class)))
                .thenReturn(1);

        Plato plato = new Plato(1, "Updated", 7.0, 1, 120, true);

        Boolean result = daoImpl.updatePlato(plato);

        assertThat(result).isTrue();
    }
}
}
