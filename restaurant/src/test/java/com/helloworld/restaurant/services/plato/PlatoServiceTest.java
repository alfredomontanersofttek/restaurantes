package com.helloworld.restaurant.services.plato;

import com.helloworld.restaurant.daos.plato.PlatoDao;
import com.helloworld.restaurant.model.Plato;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlatoServiceTest {

    @Mock
    private PlatoDao platoDao;

    @InjectMocks
    private PlatoServiceImpl platoService;

    @Test
    void getPlatos_shouldReturnList() {
        // Given
        List<com.helloworld.restaurant.daos.model.Plato> daoPlatos = List.of(
                new com.helloworld.restaurant.daos.model.Plato(1, "Plato1", 10.0, 1, 200, true)
        );
        when(platoDao.getPlatos()).thenReturn(daoPlatos);

        // When
        List<Plato> result = platoService.getPlatos();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Plato1", result.get(0).getNombre());
    }

    @Test
    void getPlatosById_shouldReturnPlato() {
        // Given
        com.helloworld.restaurant.daos.model.Plato daoPlato = new com.helloworld.restaurant.daos.model.Plato(1, "Plato1", 10.0, 1, 200, true);
        when(platoDao.getPlatosById(1)).thenReturn(Optional.of(daoPlato));

        // When
        Optional<Plato> result = platoService.getPlatosById(1);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Plato1", result.get().getNombre());
    }

    @Test
    void addPlato_shouldReturnTrue() {
        // Given
        Plato plato = new Plato(1, "Plato1", 10.0, Plato.Categoria.PRIMER_PLATO, 200, true);
        when(platoDao.addPlato(any())).thenReturn(true);

        // When
        Boolean result = platoService.addPlato(plato);

        // Then
        assertTrue(result);
        verify(platoDao).addPlato(any());
    }

    @Test
    void deletePlato_shouldReturnTrue() {
        // Given
        when(platoDao.deletePlato(1)).thenReturn(true);

        // When
        Boolean result = platoService.deletePlato(1);

        // Then
        assertTrue(result);
        verify(platoDao).deletePlato(1);
    }

    @Test
    void updatePlato_shouldReturnTrue() {
        // Given
        Plato plato = new Plato(1, "Plato1", 10.0, Plato.Categoria.PRIMER_PLATO, 200, true);
        when(platoDao.updatePlato(any())).thenReturn(true);

        // When
        Boolean result = platoService.updatePlato(plato);

        // Then
        assertTrue(result);
        verify(platoDao).updatePlato(any());
    }
}
