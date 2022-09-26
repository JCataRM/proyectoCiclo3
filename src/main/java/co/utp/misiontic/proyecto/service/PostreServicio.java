package co.utp.misiontic.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.utp.misiontic.proyecto.model.entity.OpcionPostre;
import org.springframework.data.domain.Sort;
import co.utp.misiontic.proyecto.repository.PostreRepositorio;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostreServicio {

    @Autowired
    private PostreRepositorio postreRepositorio;

    public List<OpcionPostre> listarPostres() {
        return postreRepositorio.findAll(Sort.by("nombre"));
    }

    public Optional<OpcionPostre> obtenerPostre(Integer id){
        return postreRepositorio.findById(id);
    }

    public Boolean validarCantidadPostres(List<OpcionPostre> postres){
        var respuesta = postres.stream().map(m -> m.getCantidad()).collect(Collectors.toList());
        
        if(respuesta.stream().reduce(0, (a, b) -> a + b) >= 5){
            return true;
        }
        return false;
    }
}
