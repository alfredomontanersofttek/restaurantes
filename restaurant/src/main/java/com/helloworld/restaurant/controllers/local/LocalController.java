package com.helloworld.restaurant.controllers.local;

import com.helloworld.restaurant.model.Local;

import java.util.List;

public interface LocalController {


    void createLocal(Local local);

    void updateLocal(String cif, Local local);

    void deleteLocal(String cif);
}