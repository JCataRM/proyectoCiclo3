package co.utp.misiontic.proyecto.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import co.utp.misiontic.proyecto.model.entity.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PedidoDto implements Serializable{

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String fecha_reserva;
    private String hora_reserva;
    private EstadoPedido EstadoPedido;
    private Integer precio_total;
    private Usuario usuario;
    private List<OpcionEntrada> entradas;
    private List<OpcionPlatoFuerte> platosFuertes;
    private List<OpcionPostre> postres;
    private List<OpcionBebida> bebidas;

}
