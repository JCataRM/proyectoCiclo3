package co.utp.misiontic.proyecto.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    public Pedido crearPedido() {
        var pedido = pedidoRepositorio.save(new Pedido());
        return pedido;
    }

    public Optional<Pedido> obtenerPedido(Integer id){
        return pedidoRepositorio.findById(id);
    }

    public void guardarPedido(Pedido pedido){
        pedidoRepositorio.save(pedido);
    }

    public Integer calcularValorPedido(Pedido pedido){
        var valorTotal = 0;

        var entradas = pedido.getEntradas().stream().map(m -> m.getPrecio()).collect(Collectors.toList());
        valorTotal += entradas.stream().reduce(0, (a, b) -> a + b);

        var platos = pedido.getPlatosFuertes().stream().map(m -> m.getPrecio()).collect(Collectors.toList());
        valorTotal += platos.stream().reduce(0, (a, b) -> a + b);

        var postres = pedido.getPostres().stream().map(m -> m.getPrecio()).collect(Collectors.toList());
        valorTotal += postres.stream().reduce(0, (a, b) -> a + b);

        var bebidas = pedido.getBebidas().stream().map(m -> m.getPrecio()).collect(Collectors.toList());
        valorTotal += bebidas.stream().reduce(0, (a, b) -> a + b);


        return valorTotal;
    }
    
}
