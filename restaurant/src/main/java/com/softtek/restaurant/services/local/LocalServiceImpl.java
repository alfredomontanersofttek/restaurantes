package com.softtek.restaurant.services.local;

import com.softtek.restaurant.daos.local.LocalDao;
import com.softtek.restaurant.model.Local;
import org.springframework.stereotype.Service;

@Service
public class LocalServiceImpl implements LocalService {

    private final LocalDao localDao;

    public LocalServiceImpl(LocalDao localDao) {
        this.localDao = localDao;
    }



    @Override
    public void createLocal(Local local) {
        localDao.insert(new com.softtek.restaurant.daos.model.Local(
                local.getCif(),
                local.getNombre(),
                local.getDireccion(),
                local.getTelefono()
        ));
    }

    @Override
    public void updateLocal(String cif, Local local) {

        localDao.update(cif, new com.softtek.restaurant.daos.model.Local(
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