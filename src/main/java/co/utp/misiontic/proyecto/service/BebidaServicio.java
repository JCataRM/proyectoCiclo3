package co.utp.misiontic.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import co.utp.misiontic.proyecto.model.entity.OpcionBebida;
import co.utp.misiontic.proyecto.repository.BebidaRepositorio;

@Service
public class BebidaServicio{
    
    @Autowired
    private BebidaRepositorio bebidaRepositorio;
    
    public List<OpcionBebida> listarBebidas() {
        return bebidaRepositorio.findAll(Sort.by("nombre"));
    }
}
