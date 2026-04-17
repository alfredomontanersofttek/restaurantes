package com.helloworld.restaurant.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.*;

@Data
@Schema(description = "Modelo detallado que representa un plato dentro del sistema del restaurante")
public class Plato {

	@Schema(description = "Categoría del plato según el tiempo de servicio")
	public enum Categoria {
		PRIMER_PLATO ("Entrante"),
		SEGUNDO_PLATO ("Plato principal"),
		POSTRE ("Postre");

		private String descripcion;

		Categoria(String descripcion) {
			this.descripcion = descripcion;
		}

		@JsonValue
		public String getDescripcion() {
			return descripcion;
		}

		@JsonCreator
		public static Categoria fromDescripcion(String descripcion) {
			return switch (descripcion) {
				case "Entrante" -> PRIMER_PLATO;
				case "Plato principal" -> SEGUNDO_PLATO;
				case "Postre" -> POSTRE;
				default -> PRIMER_PLATO;
			};
		}
	}


	// Parte II: Agregamos accessMode para indicar que el ID es de solo lectura
	@Schema(description = "Identificador único asignado por el sistema", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
	private final Integer id;


	// Parte II: Agregamos validaciones de longitud (mínimo 3 caracteres)
	@NotBlank(message = "El nombre no puede estar vacío")
	@Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
	@Schema(description = "Nombre del plato", example = "Ceviche Mixto")
	private final String nombre;

	// Parte II: Agregamos validación de valor mínimo (no puede ser gratis o negativo)
	@Schema(description = "Precio de venta al público (PVP) en euros", example = "18.50", minimum = "0.5")
	private final double precio;

	@Schema(description = "Clasificación del plato (Entrante, Principal o Postre)")
	private final Categoria categoria;


	@Min(value = 0)
	@Max(value = 5000, message = "Ningún plato debería tener más de 5000 kcal")
	@Schema(description = "Calorías", example = "450")
	private int calorias;

	@Schema(description = "Indica si los ingredientes son 100% de origen vegetal", example = "false")
	private final boolean vegano;

	// Constructor
	public Plato(Integer id, String nombre, double precio, Categoria categoria, int calorias, boolean vegano) {
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.categoria = categoria;
		this.calorias = calorias;
		this.vegano = vegano;
	}

	// Métodos de conversión (DAO)
	public static Plato fromPlatoDAO(com.helloworld.restaurant.daos.model.Plato plato) {
		Categoria categoria = switch (plato.categoria()) {
			case 1 -> Categoria.PRIMER_PLATO;
			case 2 -> Categoria.SEGUNDO_PLATO;
			case 3 -> Categoria.POSTRE;
			default -> Categoria.PRIMER_PLATO;
		};

		return new Plato(
				plato.id(),
				plato.nombre(),
				plato.precio(),
				categoria,
				plato.calorias(),
				plato.vegano()
		);
	}

	public com.helloworld.restaurant.daos.model.Plato toPlatoDAO() {
		int cat = switch (this.getCategoria()) {
			case PRIMER_PLATO -> 1;
			case SEGUNDO_PLATO -> 2;
			case POSTRE -> 3;
		};

		return new com.helloworld.restaurant.daos.model.Plato(
				this.getId(),
				this.getNombre(),
				this.getPrecio(),
				cat,
				this.getCalorias(),
				this.isVegano()
		);
	}
}