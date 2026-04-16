package com.helloworld.restaurant.controllers.local;

import com.helloworld.restaurant.model.Local;
import org.springframework.http.ResponseEntity;

public interface LocalController {


    ResponseEntity<Void> createLocal(Local local);

    void updateLocal(String cif, Local local);

    void deleteLocal(String cif);
}