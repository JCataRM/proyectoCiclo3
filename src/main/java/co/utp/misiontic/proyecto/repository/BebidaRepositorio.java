package co.utp.misiontic.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.utp.misiontic.proyecto.model.entity.OpcionBebida;

public interface BebidaRepositorio extends JpaRepository<OpcionBebida, Integer>{
    
}
