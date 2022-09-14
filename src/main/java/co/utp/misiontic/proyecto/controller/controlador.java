package co.utp.misiontic.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.utp.misiontic.proyecto.dto.UsuarioDto;
import co.utp.misiontic.proyecto.service.*;


@Controller
public class controlador {

    private EntradaServicio entradaServicio;
    private BebidaServicio bebidaServicio;
    private PostreServicio postreServicio;
    private PlatoFuerteServicio platoFuerteServicio;
    private UsuarioServicio usuarioServicio;
    private PedidoServicio pedidoServicio;

    public controlador(EntradaServicio entradaServicio, BebidaServicio bebidaServicio, PostreServicio postreServicio,
        PlatoFuerteServicio platoFuerteServicio, UsuarioServicio usuarioServicio, PedidoServicio pedidoServicio) {
        this.entradaServicio = entradaServicio;
        this.bebidaServicio = bebidaServicio;
        this.postreServicio = postreServicio;
        this.platoFuerteServicio = platoFuerteServicio;
        this.usuarioServicio = usuarioServicio;
        this.pedidoServicio = pedidoServicio;
    }

    //------------------Conexión de páginas web------------------

    @GetMapping("/")
    public String inicio(){
        return "index";
    }
    @GetMapping("/entradas")
    public String menuEntradas(Model modelo){
        var entradas = this.entradaServicio.listarEntradas();
        modelo.addAttribute("entradas", entradas);

        return "entradas";
    }
    @GetMapping("/platos_fuertes")
    public String menuPlatosFuertes(Model modelo){
        var platosFuertes = this.platoFuerteServicio.listarPlatosFuertes();
        modelo.addAttribute("platosFuertes", platosFuertes);
        return "platos_fuertes";
    }
    @GetMapping("/postres")
    public String menuPostres(Model modelo){
        var postres = this.postreServicio.listarPostres();
        modelo.addAttribute("postres", postres);
        return "postres";
    }
    @GetMapping("/bebidas")
    public String menuBebidas(Model modelo){
        var bebidas = this.bebidaServicio.listarBebidas();
        modelo.addAttribute("bebidas", bebidas);

        return "bebidas";
    }
    @GetMapping("/#section3")
    public String menu(){
        return "/#section3";
    }

    @GetMapping("/form_registration")
    public String formularioRegistro(){
        return "/form_registration";
    }

    //---------------------Registro de un usuario---------------------

    @GetMapping("/registro")
    public String registroUsuario(@RequestParam("cedula") Integer cedula,
        @RequestParam("nombre") String nombre, 
        @RequestParam("telefono") String telefono,
        @RequestParam("correo") String correo,
        @RequestParam("contrasena") String contrasena,
        Model modelo
        ){
        
        //Verifico si el usuario existe
        var usuarioop = usuarioServicio.encontrarUsuario(cedula);
        
        //Si el usuario no existe, se procede a crearlo en la base de datos
        if (usuarioop.isEmpty()) {
            var usuario = new UsuarioDto();
            usuario.setId(cedula);
            usuario.setNombre(nombre);
            usuario.setTelefono(telefono);
            usuario.setCorreo(correo);
            usuario.setContrasena(contrasena);
            usuario.setTipoUsuario("cliente");
        
            usuarioServicio.guardarUsuario(usuario);
        } 
        // Si el usuario existe se le muestra un mensaje de que ya está logeado
        else {
            modelo.addAttribute("mensaje", "El usuario ya tiene una cuenta vinculada");
            return "form_registration";
        }  

        return "index";
    }
}
