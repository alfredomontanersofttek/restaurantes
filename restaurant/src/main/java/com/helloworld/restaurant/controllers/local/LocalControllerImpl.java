package com.helloworld.restaurant.controllers.local;

import com.helloworld.restaurant.model.Local;
import com.helloworld.restaurant.services.local.LocalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("restaurante/locales")
public class LocalControllerImpl implements LocalController {

    private final LocalService localService;

    public LocalControllerImpl(LocalService localService) {
        this.localService = localService;
    }


    @Override
    @PostMapping("")
    public ResponseEntity<Void> createLocal(@RequestBody Local local) {

        localService.createLocal(local);

        return ResponseEntity
                .created(URI.create("/restaurante/locales/" + local.getCif()))
                .build();
    }

    @Override
    @PutMapping("/{cif}")
    public void updateLocal(@PathVariable String cif, @RequestBody Local local) {
        localService.updateLocal(cif, local);
    }

    @Override
    @DeleteMapping("/{cif}")
    public void deleteLocal(@PathVariable String cif) {
        localService.deleteLocal(cif);
    }
}