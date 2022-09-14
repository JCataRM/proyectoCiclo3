package co.utp.misiontic.proyecto.dto;

import java.io.Serializable;
import javax.persistence.*;

import co.utp.misiontic.proyecto.model.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDto implements Serializable{

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String nombre;
    private String contrasena;
    private Integer telefono;
    private String correo;
    private TipoUsuario tipoUsuario;

}
