package com.softtek.restaurant.services.local;

import com.softtek.restaurant.model.Local;

public interface LocalService {



    void createLocal(Local local);

    void updateLocal(String cif, Local local);

    void deleteLocal(String cif);
}