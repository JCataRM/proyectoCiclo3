package co.utp.misiontic.proyecto.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.utp.misiontic.proyecto.model.entity.OpcionBebida;
import co.utp.misiontic.proyecto.model.entity.OpcionEntrada;
import co.utp.misiontic.proyecto.model.entity.OpcionPlatoFuerte;
import co.utp.misiontic.proyecto.model.entity.OpcionPostre;
import co.utp.misiontic.proyecto.model.entity.Pedido;
import co.utp.misiontic.proyecto.repository.PedidoRepositorio;

@Service
public class PedidoServicio {

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    public Pedido crearPedido() {
        var pedido = pedidoRepositorio.save(new Pedido());
        return pedido;
    }

    public Optional<Pedido> obtenerPedido(Integer id) {
        return pedidoRepositorio.findById(id);
    }

    public void guardarPedido(Pedido pedido,
            List<OpcionEntrada> entradasBd,
            List<OpcionPlatoFuerte> platosBd,
            List<OpcionPostre> postresBd,
            List<OpcionBebida> bebidasBd) {

        // Se modifica la lista de cada producto para cargar todos los productos
        pedido.setEntradas(entradasBd);
        pedido.setPlatosFuertes(platosBd);
        pedido.setPostres(postresBd);
        pedido.setBebidas(bebidasBd);

        pedidoRepositorio.save(pedido);
    }

    public Integer calcularValorPedido(Pedido pedido) {
        var valorTotal = 0;

        var entradas = pedido.getEntradas().stream().map(m -> m.getPrecio() * m.getCantidad())
                .collect(Collectors.toList());
        valorTotal += entradas.stream().reduce(0, (a, b) -> a + b);

        var platos = pedido.getPlatosFuertes().stream().map(m -> m.getPrecio() * m.getCantidad())
                .collect(Collectors.toList());
        valorTotal += platos.stream().reduce(0, (a, b) -> a + b);

        var postres = pedido.getPostres().stream().map(m -> m.getPrecio() * m.getCantidad())
                .collect(Collectors.toList());
        valorTotal += postres.stream().reduce(0, (a, b) -> a + b);

        var bebidas = pedido.getBebidas().stream().map(m -> m.getPrecio() * m.getCantidad())
                .collect(Collectors.toList());
                
        valorTotal += bebidas.stream().reduce(0, (a, b) -> a + b);

        return valorTotal;
    }

    public List<Pedido> listarPedidosPorEntregar() {
        var pedidos = pedidoRepositorio.findAll();

        var pedidosPendientes = pedidos.stream()
                .filter(m -> m.getEstadoPedido().equals("Pendiente por entregar")).collect(Collectors.toList());

        return pedidosPendientes;
    }

    public List<Pedido> listarPedidosPorCliente(Integer cedula) {
        var pedidos = pedidoRepositorio.findAll();

        pedidos.stream().map(m -> organizarPedido(m)).collect(Collectors.toList());
        
        var pedidosPendientesPorCliente = pedidos.stream()
                .filter(m -> m.getUsuario().getId().equals(cedula))
                .collect(Collectors.toList());

        return pedidosPendientesPorCliente;
    }

    public void actualizarEstadoPedido(Integer id) {
        var pedido = pedidoRepositorio.findById(id).get();

        pedido.setEstadoPedido("Entregado");
        pedidoRepositorio.save(pedido);
    }

    public Pedido organizarPedido(Pedido pedido) {

        //Organizar entradas
        var entradas = pedido.getEntradas();
        if (entradas != null) {
            for (OpcionEntrada entrada : entradas) {
                entrada.setCantidad(Collections.frequency(entradas, entrada));
            }

            var respuesta = new ArrayList<>(new HashSet<>(entradas));
            pedido.setEntradas(respuesta);
        }
        //Organizar platos fuertes
        var platos = pedido.getPlatosFuertes();
        if (platos != null) {
            for (OpcionPlatoFuerte plato : platos) {
                plato.setCantidad(Collections.frequency(platos, plato));
            }

            var respuesta = new ArrayList<>(new HashSet<>(entradas));
            pedido.setEntradas(respuesta);
        } 
        //Organizar postres
        var postres = pedido.getPostres();
        if (postres != null) {
            for (OpcionPostre postre : postres) {
                postre.setCantidad(Collections.frequency(postres, postre));
            }

            var respuesta = new ArrayList<>(new HashSet<>(postres));
            pedido.setPostres(respuesta);
        } 
        //Organizar bebidas
        var bebidas = pedido.getBebidas();
        if (bebidas != null) {
            for (OpcionBebida bebida : bebidas) {
                bebida.setCantidad(Collections.frequency(bebidas, bebida));
            }

            var respuesta = new ArrayList<>(new HashSet<>(bebidas));
            pedido.setBebidas(respuesta);
        } 
        
        return pedido;
    }

}
