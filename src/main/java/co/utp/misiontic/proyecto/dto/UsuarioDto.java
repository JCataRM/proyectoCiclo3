package co.utp.misiontic.proyecto.dto;

import java.io.Serializable;
import javax.persistence.*;
import javax.print.DocFlavor.STRING;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class UsuarioDto implements Serializable{

    @Id 
    private Integer Id;
    private String nombre;
    private String contrasena;
    private String telefono;
    private String correo;
    private String tipoUsuario;

}
