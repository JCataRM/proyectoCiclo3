package co.utp.misiontic.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.utp.misiontic.proyecto.dto.UsuarioDto;

public interface UsuarioRepositorio extends JpaRepository<UsuarioDto, Integer>{
    
}
