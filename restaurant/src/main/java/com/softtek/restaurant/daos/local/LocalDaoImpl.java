package com.softtek.restaurant.daos.local;

import com.softtek.restaurant.daos.model.Local;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;


@Repository
public class LocalDaoImpl implements LocalDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public LocalDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Local> rowMapper = (rs, rowNum) -> {

        String cif = rs.getString("cif");
        String nombre = rs.getString("nombre");
        String direccion = rs.getString("direccion");
        String telefono = rs.getString("telefono");

        return new Local(cif, nombre, direccion, telefono);
    };



    @Override
    public void insert(Local local) {

        String sql = "INSERT INTO restaurante(cif, nombre, direccion, telefono) VALUES(:cif, :nombre, :direccion, :telefono)";

        Map<String, Object> params = new HashMap<>();
        params.put("cif", local.cif());
        params.put("nombre", local.nombre());
        params.put("direccion", local.direccion());
        params.put("telefono", local.telefono());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public void update(String cif, Local local) {

        String sql = "UPDATE restaurante SET nombre=:nombre, direccion=:direccion, telefono=:telefono WHERE cif=:cif";

        Map<String, Object> params = new HashMap<>();
        params.put("cif", cif);
        params.put("nombre", local.nombre());
        params.put("direccion", local.direccion());
        params.put("telefono", local.telefono());

        jdbcTemplate.update(sql, params);


    }

    @Override
    public void delete(String cif) {

        String sql = "DELETE FROM restaurante WHERE cif=:cif";

        Map<String, Object> params = new HashMap<>();
        params.put("cif", cif);

        jdbcTemplate.update(sql, params);
    }
}