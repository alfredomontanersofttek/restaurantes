package com.helloworld.restaurant.daos.plato;

import com.helloworld.restaurant.daos.model.Plato;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PlatoDaoImpl implements PlatoDao {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private RowMapper<Plato> platoRowMapper = (rs, rowNum) ->
	{
		int id = rs.getInt("id");
		String nombre = rs.getString("nombre");
		double precio = rs.getDouble("precio");
		int categoria = rs.getInt("categoria");
		int calorias = rs.getInt("calorias");

		return new Plato(id, nombre, precio, categoria, calorias);
	};

	public PlatoDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Plato> getPlatos() {
		String query = "SELECT id, nombre, precio, categoria, calorias FROM plato";
		return jdbcTemplate.query(query, platoRowMapper);
	}

	@Override
	public Optional<Plato> getPlatosById(int platoId) {

		Map<String, Object> params = new HashMap<>();
		params.put("id", platoId);
		String query = "SELECT id, nombre, precio, categoria, calorias FROM plato WHERE id = :id";
		try {
			return Optional.of(jdbcTemplate.queryForObject(query, params, platoRowMapper));
		}catch(EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public List<Plato> getPlatosByCalories(int calories)
	{
		Map<String, Object> params = new HashMap<>();
		params.put("calorias",calories);
		String query = "SELECT id, nombre, precio, categoria, calorias FROM plato WHERE calorias < :calories";
		return jdbcTemplate.query(query, params, platoRowMapper);
	}
	
	@Override
	public void deletePlato(int id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		String query = "DELETE FROM plato WHERE id = :id";
		jdbcTemplate.update(query, params);
	}

	@Override
	public void createPlato(Plato plato) {
		Map<String, Object> params = new HashMap<>();
		params.put("nombre", plato.nombre());
		params.put("precio", plato.precio());
		params.put("categoria", plato.categoria());
		params.put("calorias", plato.calorias());
		String query = "INSERT INTO plato (nombre, precio, categoria, calorias) VALUES (:nombre, :precio, :categoria, :calorias)";
		jdbcTemplate.update(query, params);
	}

	@Override
	public void updatePlato(Plato plato) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", plato.id());
		params.put("nombre", plato.nombre());
		params.put("precio", plato.precio());
		params.put("categoria", plato.categoria());
		params.put("calorias", plato.calorias());
		String query = "UPDATE plato SET nombre = :nombre, precio = :precio, categoria = :categoria, calorias = :calorias WHERE id = :id";
		jdbcTemplate.update(query, params);
	}

}
