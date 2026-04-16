package com.helloworld.restaurant.daos.plato;

import com.helloworld.restaurant.daos.model.Plato;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.helloworld.restaurant.model.Plato.Categoria;

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
        boolean vegano=rs.getBoolean("vegano");

        return new Plato(id, nombre, precio, categoria, calorias,vegano);
    };

    public PlatoDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Plato> getPlatos() {
        String query = "SELECT id, nombre, precio, categoria, calorias, vegano FROM plato";
        return jdbcTemplate.query(query, platoRowMapper);
    }

    @Override
    public Optional<Plato> getPlatosById(int platoId) {

        Map<String, Object> params = new HashMap<>();
        params.put("id", platoId);
        String query = "SELECT id, nombre, precio, categoria, calorias, vegano FROM plato WHERE id = :id";
        try {
            return Optional.of(jdbcTemplate.queryForObject(query, params, platoRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Plato> getPlatosByCalories(int calories) {
        Map<String, Object> params = new HashMap<>();
        params.put("calorias", calories);
        String query = "SELECT id, nombre, precio, categoria, calorias, vegano FROM plato WHERE calorias < :calories";
        return jdbcTemplate.query(query, params, platoRowMapper);
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

        String query = "INSERT INTO plato (id, nombre, precio, categoria, calorias, vegano) " +
                "VALUES (:id, :nombre, :precio, :categoria, :calorias, :vegano)";

        int rows = jdbcTemplate.update(query, params);

        return rows > 0;
    }

    @Override
    public Boolean deletePlato(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        String query = "DELETE FROM plato WHERE id = :id";

        int rows = jdbcTemplate.update(query, params);

        return rows > 0;
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

        int rows = jdbcTemplate.update(query, params);

        return rows > 0;
    }
}
