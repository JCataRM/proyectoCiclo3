package co.utp.misiontic.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.utp.misiontic.proyecto.model.entity.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{
    
}
