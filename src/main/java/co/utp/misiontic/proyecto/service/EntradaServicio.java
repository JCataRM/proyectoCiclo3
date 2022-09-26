package co.utp.misiontic.proyecto.service;

import java.util.*;
import java.util.stream.Collectors;

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

    public Boolean validarCantidadEntradas(List<OpcionEntrada> entradas){
        var respuesta = entradas.stream().map(m -> m.getCantidad()).collect(Collectors.toList());
        
        if(respuesta.stream().reduce(0, (a, b) -> a + b) >= 5){
            return true;
        }
        return false;
    }


}
