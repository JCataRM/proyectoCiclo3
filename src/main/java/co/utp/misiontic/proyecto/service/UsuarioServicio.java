package co.utp.misiontic.proyecto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.utp.misiontic.proyecto.dto.UsuarioDto;
import co.utp.misiontic.proyecto.repository.UsuarioRepositorio;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public void guardarUsuario(UsuarioDto usuario){
        usuarioRepositorio.save(usuario);
    }

    public Optional<UsuarioDto> encontrarUsuario(Integer id){
        return usuarioRepositorio.findById(id);
    }
}
