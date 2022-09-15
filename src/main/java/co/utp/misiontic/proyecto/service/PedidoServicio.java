package co.utp.misiontic.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.utp.misiontic.proyecto.model.entity.*;
import co.utp.misiontic.proyecto.repository.PedidoRepositorio;

@Service
public class PedidoServicio {

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    public void guardarEntradaEnPedido(OpcionEntrada entrada){
        
    }
    
}
