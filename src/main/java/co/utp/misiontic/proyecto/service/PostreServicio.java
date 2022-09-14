package co.utp.misiontic.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.utp.misiontic.proyecto.dto.OpcionPostreDto;
import co.utp.misiontic.proyecto.repository.PostreRepositorio;

@Service
public class PostreServicio {

    @Autowired
    private PostreRepositorio postreRepositorio;

    public List<OpcionPostreDto> listarPostres() {
        return postreRepositorio.findAll();
    }
}
