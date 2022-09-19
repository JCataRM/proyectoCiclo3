package co.utp.misiontic.proyecto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.utp.misiontic.proyecto.model.entity.Usuario;
import co.utp.misiontic.proyecto.repository.UsuarioRepositorio;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public void guardarUsuario(Usuario usuario){
        usuarioRepositorio.save(usuario);
    }

    public Optional<Usuario> obtenerUsuario(Integer id){
        return usuarioRepositorio.findById(id);
    }

    public void crearUsuario(Integer cedula, String nombre, String telefono, String correo, String contrasena) {
        var usuario = new Usuario();
        usuario.setId(cedula);
        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        usuario.setCorreo(correo);
        usuario.setContrasena(contrasena);
        usuario.setTipoUsuario("cliente");

        guardarUsuario(usuario);
    }
}
