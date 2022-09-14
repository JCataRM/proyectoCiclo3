package co.utp.misiontic.proyecto.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import co.utp.misiontic.proyecto.model.EstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PedidoDto implements Serializable{

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String fecha_reserva;
    private String hora_reserva;
    private EstadoPedido EstadoPedido;
    private Integer precio_total;
    private UsuarioDto usuario;

    private List<OpcionEntradaDto> entradas;
    private List<OpcionPlatoFuerteDto> platosFuertes;
    private List<OpcionPostreDto> postres;
    private List<OpcionBebidaDto> bebidas;
    

}
