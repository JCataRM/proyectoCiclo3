package co.utp.misiontic.proyecto.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioDto implements Serializable{

    @Id 
    private Integer Id;
    private String nombre;
    private String contrasena;
    private String telefono;
    private String correo;
    private String tipoUsuario;
    
}
