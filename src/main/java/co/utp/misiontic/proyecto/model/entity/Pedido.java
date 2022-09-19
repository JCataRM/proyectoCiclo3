package co.utp.misiontic.proyecto.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import co.utp.misiontic.proyecto.model.EstadoPedido;
import lombok.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable{

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String fecha_reserva;
    private String hora_reserva;
    private EstadoPedido estadoPedido;
    private Integer precio_total;

    @ManyToOne
    private Usuario usuario;

    @ManyToMany
    @JoinTable(name="seleccion_entrada",
        joinColumns = @JoinColumn(name="id_pedido"),
        inverseJoinColumns = @JoinColumn(name="id_entrada"))
    private List<OpcionEntrada> entradas;

    @ManyToMany
    @JoinTable(name="seleccion_plato_fuerte",
        joinColumns = @JoinColumn(name="id_pedido"),
        inverseJoinColumns = @JoinColumn(name="id_platos_fuertes"))
    private List<OpcionPlatoFuerte> platosFuertes;

    @ManyToMany
    @JoinTable(name="seleccion_postre",
        joinColumns = @JoinColumn(name="id_pedido"),
        inverseJoinColumns = @JoinColumn(name="id_postre"))
    private List<OpcionPostre> postres;

    @ManyToMany
    @JoinTable(name="seleccion_bebida",
        joinColumns = @JoinColumn(name="id_pedido"),
        inverseJoinColumns = @JoinColumn(name="id_bebidas"))
    private List<OpcionBebida> bebidas;

    
    public Pedido() {
        this.estadoPedido = EstadoPedido.PENDIENTE_POR_ENTREGAR;
        this.entradas = new ArrayList<>();
        this.platosFuertes = new ArrayList<>();
        this.postres = new ArrayList<>();
        this.bebidas = new ArrayList<>();
    }

    

}
