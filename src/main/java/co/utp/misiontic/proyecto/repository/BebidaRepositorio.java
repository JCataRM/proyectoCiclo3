package co.utp.misiontic.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.utp.misiontic.proyecto.dto.OpcionBebidaDto;

public interface BebidaRepositorio extends JpaRepository<OpcionBebidaDto, Integer>{
    
}
