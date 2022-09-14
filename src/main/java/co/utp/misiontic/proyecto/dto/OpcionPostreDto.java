package co.utp.misiontic.proyecto.dto;

import java.io.Serializable;
import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "opcionpostre")
public class OpcionPostreDto implements Serializable{
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String nombre;
    private String descripcion;
    private String imagen;
    private Integer precio;

}