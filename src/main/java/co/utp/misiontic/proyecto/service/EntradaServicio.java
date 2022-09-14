package co.utp.misiontic.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.utp.misiontic.proyecto.dto.OpcionEntradaDto;
import co.utp.misiontic.proyecto.repository.EntradaRepositorio;

@Service
public class EntradaServicio {

    @Autowired
    private EntradaRepositorio bebidaRepositorio;

    public List<OpcionEntradaDto> listarEntradas() {
        return bebidaRepositorio.findAll();
    }
}
