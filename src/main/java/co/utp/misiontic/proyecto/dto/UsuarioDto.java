package co.utp.misiontic.proyecto.dto;

import java.io.Serializable;
import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class UsuarioDto implements Serializable{

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String nombre;
    private String contrasena;
    private Integer telefono;
    private String correo;
    private String tipoUsuario;

}
