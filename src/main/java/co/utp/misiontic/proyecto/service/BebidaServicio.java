package co.utp.misiontic.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.utp.misiontic.proyecto.dto.OpcionBebidaDto;
import co.utp.misiontic.proyecto.repository.BebidaRepositorio;

@Service
public class BebidaServicio{
    
    @Autowired
    private BebidaRepositorio bebidaRepositorio;
    
    public List<OpcionBebidaDto> listarBebidas() {
        return bebidaRepositorio.findAll();
    }
}
