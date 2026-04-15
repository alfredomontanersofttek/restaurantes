package com.helloworld.restaurant.daos.local;

import com.helloworld.restaurant.daos.model.Local;

import java.util.List;
import java.util.Optional;

public interface LocalDao {

    void insert(Local local);

    void update(String id, Local local);

    void delete(String id);
}
