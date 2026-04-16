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

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Plato> platoRowMapper = (rs, rowNum) -> new Plato(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getDouble("precio"),
            rs.getInt("categoria"),
            rs.getInt("calorias"),
            rs.getBoolean("vegano") // El 6º campo para que no de error de "length"
    );

    public PlatoDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Plato> getPlatos() {
        return jdbcTemplate.query("SELECT id, nombre, precio, categoria, calorias, vegano FROM plato", platoRowMapper);
    }

    @Override
    public Optional<Plato> getPlatosById(int platoId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", platoId);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT id, nombre, precio, categoria, calorias, vegano FROM plato WHERE id = :id", params, platoRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Plato> getPlatosByCalories(int calories) {
        Map<String, Object> params = new HashMap<>();
        params.put("calories", calories);
        return jdbcTemplate.query("SELECT id, nombre, precio, categoria, calorias, vegano FROM plato WHERE calorias < :calories", params, platoRowMapper);
    }

    @Override
    public Boolean addPlato(Plato plato) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", plato.id());
        params.put("nombre", plato.nombre());
        params.put("precio", plato.precio());
        params.put("categoria", plato.categoria());
        params.put("calorias", plato.calorias());
        params.put("vegano", plato.vegano());

        String query = "INSERT INTO plato (id, nombre, precio, categoria, calorias, vegano) VALUES (:id, :nombre, :precio, :categoria, :calorias, :vegano)";
        return jdbcTemplate.update(query, params) > 0;
    }

    @Override
    public Boolean deletePlato(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbcTemplate.update("DELETE FROM plato WHERE id = :id", params) > 0;
    }

    @Override
    public Boolean updatePlato(Plato plato) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", plato.id());
        params.put("nombre", plato.nombre());
        params.put("precio", plato.precio());
        params.put("categoria", plato.categoria());
        params.put("calorias", plato.calorias());
        params.put("vegano", plato.vegano());

        String query = "UPDATE plato SET nombre = :nombre, precio = :precio, categoria = :categoria, calorias = :calorias, vegano = :vegano WHERE id = :id";
        return jdbcTemplate.update(query, params) > 0;
    }
}