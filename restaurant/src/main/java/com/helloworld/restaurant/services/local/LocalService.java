package com.helloworld.restaurant.services.local;

import com.helloworld.restaurant.model.Local;

import java.util.List;
import java.util.Optional;

public interface LocalService {



    void createLocal(Local local);

    void updateLocal(String cif, Local local);

    void deleteLocal(String cif);
}