package co.utp.misiontic.proyecto.model.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable{

    @Id 
    private Integer Id;
    private String nombre;
    private String contrasena;
    private String telefono;
    private String correo;
    private String tipoUsuario;
    
    public Usuario() {
    }

}
