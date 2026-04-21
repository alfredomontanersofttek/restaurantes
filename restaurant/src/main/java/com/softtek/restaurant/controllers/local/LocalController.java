package com.softtek.restaurant.controllers.local;

import com.softtek.restaurant.model.Local;
import org.springframework.http.ResponseEntity;

public interface LocalController {


    ResponseEntity<Void> createLocal(Local local);

    void updateLocal(String cif, Local local);

    void deleteLocal(String cif);
}