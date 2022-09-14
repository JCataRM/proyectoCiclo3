package co.utp.misiontic.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.utp.misiontic.proyecto.dto.OpcionPostreDto;

public interface PostreRepositorio extends JpaRepository<OpcionPostreDto, Integer>{
    
}
