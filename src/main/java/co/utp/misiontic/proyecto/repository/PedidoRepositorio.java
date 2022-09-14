package co.utp.misiontic.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.utp.misiontic.proyecto.dto.PedidoDto;

public interface PedidoRepositorio extends JpaRepository<PedidoDto, Integer>{
    
}
