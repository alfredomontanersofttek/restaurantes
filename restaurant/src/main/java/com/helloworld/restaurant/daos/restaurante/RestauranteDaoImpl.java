package com.helloworld.restaurant.daos.restaurante;

import com.helloworld.restaurant.daos.model.Plato;
import com.helloworld.restaurant.daos.model.Restaurante;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class RestauranteDaoImpl implements RestauranteDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Restaurante> restauranteRowMapper = (rs, rowNum) ->
            new Restaurante(
                    rs.getString("cif"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getString("telefono")
            );

    private final RowMapper<Plato> platoRowMapper = (rs, rowNum) ->
            new Plato(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("categoria"),
                    rs.getInt("calorias"),
                    rs.getBoolean("vegano")
            );

    public RestauranteDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Restaurante> getRestaurantes() {
        String query = "SELECT cif, nombre, direccion, telefono FROM restaurante";
        return jdbcTemplate.query(query, restauranteRowMapper);
    }

    @Override
    public Optional<Restaurante> getRestauranteByCif(String cif) {
        Map<String, Object> params = new HashMap<>();
        params.put("cif", cif);
        String query = "SELECT cif, nombre, direccion, telefono FROM restaurante WHERE cif = :cif";
        try {
            return Optional.of(jdbcTemplate.queryForObject(query, params, restauranteRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Plato> getPlatosByRestaurante(String cif) {
        Map<String, Object> params = new HashMap<>();
        params.put("cif", cif);
        String query = """
                SELECT p.id, p.nombre, p.precio, p.categoria, p.calorias, p.vegano
                FROM plato p
                JOIN restaurante_plato rp ON p.id = rp.id_plato
                WHERE rp.cif_restaurante = :cif
                """;
        return jdbcTemplate.query(query, params, platoRowMapper);
    }

    @Override
    public Boolean addPlato(com.helloworld.restaurant.model.Plato plato, int restauranteId) {
        Map<String, Object> params = new HashMap<>();

        params.put("id", plato.getId());
        params.put("nombre", plato.getNombre());
        params.put("precio", plato.getPrecio());
        params.put("categoria", plato.getCategoria());
        params.put("calorias", plato.getCalorias());
        params.put("restauranteId", restauranteId);

        String query = "INSERT INTO plato (id, nombre, precio, categoria, calorias, restaurante_id) " +
                "VALUES (:id, :nombre, :precio, :categoria, :calorias, :restauranteId)";

        int rows = jdbcTemplate.update(query, params);

        return rows > 0;
    }


    @Override
    public Boolean deletePlato(int platoId, int restauranteId) {
        Map<String, Object> params = new HashMap<>();
        params.put("platoId", platoId);
        params.put("restauranteId", restauranteId);

        String sql = """
                    DELETE FROM restaurante_plato
                    WHERE plato_id = :platoId
                    AND restaurante_id = :restauranteId
                """;

        int rows = jdbcTemplate.update(sql, params);

        return rows > 0;
    }
}
