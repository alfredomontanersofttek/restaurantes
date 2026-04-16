package com.helloworld.restaurant.controllers.plato;

import com.helloworld.restaurant.model.Plato;
import com.helloworld.restaurant.services.plato.PlatoService;
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
class PlatoControllerTest {

    @Mock
    private PlatoService platoService;

    @InjectMocks
    private PlatoControllerImpl platoController;

    @Test
    void getPlatos_shouldReturnList() {
        // Given
        List<Plato> platos = List.of(new Plato(1, "Plato1", 10.0, Plato.Categoria.PRIMER_PLATO, 200, true));
        when(platoService.getPlatos()).thenReturn(platos);

        // When
        List<Plato> result = platoController.getPlatos();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Plato1", result.get(0).getNombre());
        verify(platoService).getPlatos();
    }

    @Test
    void getPlatosById_shouldReturnPlato() {
        // Given
        Plato plato = new Plato(1, "Plato1", 10.0, Plato.Categoria.PRIMER_PLATO, 200, true);
        when(platoService.getPlatosById(1)).thenReturn(Optional.of(plato));

        // When
        Plato result = platoController.getPlatosById("1");

        // Then
        assertNotNull(result);
        assertEquals("Plato1", result.getNombre());
        verify(platoService).getPlatosById(1);
    }

    @Test
    void getPlatosById_shouldThrowNotFound() {
        // Given
        when(platoService.getPlatosById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> platoController.getPlatosById("1"));
    }

    @Test
    void addPlato_shouldReturnTrue() {
        // Given
        Plato plato = new Plato(1, "Plato1", 10.0, Plato.Categoria.PRIMER_PLATO, 200, true);
        when(platoService.addPlato(plato)).thenReturn(true);

        // When
        Boolean result = platoController.addPlato(plato);

        // Then
        assertTrue(result);
        verify(platoService).addPlato(plato);
    }

    @Test
    void deletePlato_shouldReturnTrue() {
        // Given
        when(platoService.deletePlato(1)).thenReturn(true);

        // When
        Boolean result = platoController.deletePlato(1);

        // Then
        assertTrue(result);
        verify(platoService).deletePlato(1);
    }

    @Test
    void deletePlato_shouldThrowNotFound() {
        // Given
        when(platoService.deletePlato(1)).thenReturn(false);

        // When & Then
        assertThrows(RuntimeException.class, () -> platoController.deletePlato(1));
    }

    @Test
    void updatePlato_shouldReturnTrue() {
        // Given
        Plato plato = new Plato(1, "Plato1", 10.0, Plato.Categoria.PRIMER_PLATO, 200, true);
        when(platoService.updatePlato(plato)).thenReturn(true);

        // When
        Boolean result = platoController.updatePlato(plato);

        // Then
        assertTrue(result);
        verify(platoService).updatePlato(plato);
    }
}
