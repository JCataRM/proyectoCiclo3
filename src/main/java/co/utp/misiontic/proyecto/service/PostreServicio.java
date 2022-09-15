package co.utp.misiontic.proyecto.service;

import java.util.List;

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
}
