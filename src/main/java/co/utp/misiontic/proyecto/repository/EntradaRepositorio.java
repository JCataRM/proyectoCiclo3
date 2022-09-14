package co.utp.misiontic.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.utp.misiontic.proyecto.dto.OpcionEntradaDto;

public interface EntradaRepositorio extends JpaRepository<OpcionEntradaDto, Integer>{

}
