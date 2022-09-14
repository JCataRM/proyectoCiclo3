package co.utp.misiontic.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.utp.misiontic.proyecto.dto.OpcionPlatoFuerteDto;
import co.utp.misiontic.proyecto.repository.PlatoFuerteRepositorio;

@Service
public class PlatoFuerteServicio {

    @Autowired
    private PlatoFuerteRepositorio platoFuerteRepositorio;

    public List<OpcionPlatoFuerteDto> listarPlatosFuertes() {
        return platoFuerteRepositorio.findAll();
    }
}
