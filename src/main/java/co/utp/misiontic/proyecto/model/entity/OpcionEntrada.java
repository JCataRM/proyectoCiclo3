package co.utp.misiontic.proyecto.model.entity;

import java.io.Serializable;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "opcion_entrada")
public class OpcionEntrada implements Serializable{
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String descripcion;
    private String imagen;
    private Integer precio;
    private Integer cantidad;
    
    @Override
    public String toString() {
        return  nombre + " -> cantidad: " + cantidad + ". ";
    }

    
}
