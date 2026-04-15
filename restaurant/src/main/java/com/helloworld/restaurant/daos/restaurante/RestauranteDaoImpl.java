package com.helloworld.restaurant.daos.restaurante;

import com.helloworld.restaurant.daos.model.Plato;
import com.helloworld.restaurant.daos.model.Restaurante;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        Map<String, Object> params = Map.of("cif", cif);

        String query = "SELECT cif, nombre, direccion, telefono FROM restaurante WHERE cif = :cif";

        try {
            return Optional.of(jdbcTemplate.queryForObject(query, params, restauranteRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Plato> getPlatosByRestaurante(String cif) {
        Map<String, Object> params = Map.of("cif", cif);

        String query = """
                SELECT p.id, p.nombre, p.precio, p.categoria, p.calorias, p.vegano
                FROM plato p
                JOIN restaurante_plato rp ON p.id = rp.id_plato
                WHERE rp.cif_restaurante = :cif
                """;

        return jdbcTemplate.query(query, params, platoRowMapper);
    }

    @Override
    @Transactional
    public Boolean addPlato(com.helloworld.restaurant.model.Plato plato, String cifRestaurante) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("nombre", plato.getNombre())
                .addValue("precio", plato.getPrecio())
                .addValue("categoria", plato.getCategoria())
                .addValue("calorias", plato.getCalorias());

        String insertPlato = """
                INSERT INTO plato (nombre, precio, categoria, calorias)
                VALUES (:nombre, :precio, :categoria, :calorias)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(insertPlato, params, keyHolder);

        Integer platoId = Objects.requireNonNull(keyHolder.getKey()).intValue();
    
        String insertRelacion = """
                INSERT INTO restaurante_plato (id_plato, cif_restaurante)
                VALUES (:platoId, :cifRestaurante)
                """;

        Map<String, Object> relParams = Map.of(
                "platoId", platoId,
                "cifRestaurante", cifRestaurante
        );

        int rows = jdbcTemplate.update(insertRelacion, relParams);

        return rows > 0;
    }

    @Override
    public Boolean deletePlato(int platoId, String restauranteCif) {

        Map<String, Object> params = Map.of(
                "platoId", platoId,
                "restauranteCif", restauranteCif
        );

        String sql = """
                DELETE FROM restaurante_plato
                WHERE id_plato = :platoId
                AND cif_restaurante = :restauranteCif
                """;

        int rows = jdbcTemplate.update(sql, params);

        return rows > 0;
    }
}