package co.utp.misiontic.proyecto.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "opcion_postre")
public class OpcionPostre implements Serializable{
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String descripcion;
    private String imagen;
    private Integer precio;

}
