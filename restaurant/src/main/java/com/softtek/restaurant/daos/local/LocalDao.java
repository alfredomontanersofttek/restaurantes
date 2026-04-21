package com.softtek.restaurant.daos.local;

import com.softtek.restaurant.daos.model.Local;

public interface LocalDao {

    void insert(Local local);

    void update(String id, Local local);

    void delete(String id);
}
