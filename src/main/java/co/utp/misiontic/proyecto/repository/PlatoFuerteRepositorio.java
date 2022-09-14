package co.utp.misiontic.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.utp.misiontic.proyecto.dto.OpcionPlatoFuerteDto;

public interface PlatoFuerteRepositorio extends JpaRepository<OpcionPlatoFuerteDto, Integer>{
    
}
