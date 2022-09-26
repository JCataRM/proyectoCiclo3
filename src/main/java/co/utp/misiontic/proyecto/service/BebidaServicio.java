package co.utp.misiontic.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<OpcionBebida> obtenerBebida(Integer id){
        return bebidaRepositorio.findById(id);
    }

    public Boolean validarCantidadBebidas(List<OpcionBebida> bebidas){
        var respuesta = bebidas.stream().map(m -> m.getCantidad()).collect(Collectors.toList());
        
        if(respuesta.stream().reduce(0, (a, b) -> a + b) >= 5){
            return true;
        }
        return false;
    }
}
