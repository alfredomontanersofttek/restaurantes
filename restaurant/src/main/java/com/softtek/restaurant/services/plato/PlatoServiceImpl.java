package com.softtek.restaurant.services.plato;

import com.softtek.restaurant.clients.LabServiceClient;
import com.softtek.restaurant.daos.plato.PlatoDao;
import com.softtek.restaurant.model.Plato;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlatoServiceImpl implements PlatoService {

    private final PlatoDao platoDao;
    private final LabServiceClient soapClient;

    public PlatoServiceImpl(PlatoDao platoDao, LabServiceClient soapClient) {
        this.platoDao = platoDao;
        this.soapClient = soapClient;
    }

    @Override
    public List<Plato> getPlatos() {
        return platoDao.getPlatos().stream().map(Plato::fromPlatoDAO).toList();
    }

    @Override
    public Optional<Plato> getPlatosById(int id) {
        return platoDao.getPlatosById(id).map(Plato::fromPlatoDAO);
    }

    @Override
    public List<Plato> getPlatosByCalories(int kcal) {
        return platoDao.getPlatosByCalories(kcal).stream().map(Plato::fromPlatoDAO).toList();
    }

    @Override
    public Boolean addPlato(Plato plato) {
        return platoDao.addPlato(plato.toPlatoDAO());
    }

    @Override
    public Boolean deletePlato(int id) {
        return platoDao.deletePlato(id);
    }

    @Override
    public Boolean updatePlato(Plato plato) {
        return platoDao.updatePlato(plato.toPlatoDAO());
    }

    @Override
    public Optional<Plato> syncPlatoRandomFromSoap() {
        var request = new com.helloworld.restaurant.clients.GetPlatoRdnReq();
        var response = (com.helloworld.restaurant.clients.GetPlatoRdnRes) soapClient.enviarPeticion(request);

        if (response == null || response.getNombre() == null) {
            return Optional.empty();
        }

        String nombreSoap = response.getNombre();

        return platoDao.getPlatoByNombre(nombreSoap)
                .map(Plato::fromPlatoDAO)
                .or(() -> {
                    Plato nuevo = new Plato(
                            null,
                            nombreSoap,
                            response.getPrecio(),
                            Plato.Categoria.fromDescripcion(response.getCategoria()),
                            response.getCalorias()
                    );

                    return platoDao.addPlato(nuevo.toPlatoDAO())
                            ? platoDao.getPlatoByNombre(nombreSoap).map(Plato::fromPlatoDAO)
                            : Optional.empty();
                });
    }
}