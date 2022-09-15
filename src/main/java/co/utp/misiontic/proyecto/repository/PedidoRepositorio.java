package co.utp.misiontic.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.utp.misiontic.proyecto.model.entity.Pedido;

public interface PedidoRepositorio extends JpaRepository<Pedido, Integer>{
    
}
