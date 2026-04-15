package com.helloworld.restaurant.services.local;

import com.helloworld.restaurant.daos.local.LocalDao;
import com.helloworld.restaurant.model.Local;
import org.springframework.stereotype.Service;

@Service
public class LocalServiceImpl implements LocalService {

    private final LocalDao localDao;

    public LocalServiceImpl(LocalDao localDao) {
        this.localDao = localDao;
    }



    @Override
    public void createLocal(Local local) {
        localDao.insert(new com.helloworld.restaurant.daos.model.Local(
                local.getCif(),
                local.getNombre(),
                local.getDireccion(),
                local.getTelefono()
        ));
    }

    @Override
    public void updateLocal(String cif, Local local) {

        localDao.update(cif, new com.helloworld.restaurant.daos.model.Local(
                local.getCif(),
                local.getNombre(),
                local.getDireccion(),
                local.getTelefono()
        ));
    }

    @Override
    public void deleteLocal(String cif) {

        localDao.delete(cif);
    }
}