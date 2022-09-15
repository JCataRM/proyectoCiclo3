package co.utp.misiontic.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import co.utp.misiontic.proyecto.model.entity.OpcionEntrada;
import co.utp.misiontic.proyecto.repository.EntradaRepositorio;

@Service
public class EntradaServicio {

    @Autowired
    private EntradaRepositorio entradaRepositorio;

    public List<OpcionEntrada> listarEntradas() {
        return entradaRepositorio.findAll(Sort.by("nombre"));
    }

    public Optional<OpcionEntrada> obtenerEntrada(Integer id){
        return entradaRepositorio.findById(id);
    }

}
