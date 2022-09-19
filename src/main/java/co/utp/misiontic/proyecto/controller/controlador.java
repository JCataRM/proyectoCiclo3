package co.utp.misiontic.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import co.utp.misiontic.proyecto.model.entity.*;
import co.utp.misiontic.proyecto.service.*;

@Controller
public class controlador {

    private final EntradaServicio entradaServicio;
    private final BebidaServicio bebidaServicio;
    private final PostreServicio postreServicio;
    private final PlatoFuerteServicio platoFuerteServicio;
    private final UsuarioServicio usuarioServicio;
    private final PedidoServicio pedidoServicio;

    private Pedido pedidoActual;
    private Usuario usuarioActual;

    public controlador(EntradaServicio entradaServicio, BebidaServicio bebidaServicio, PostreServicio postreServicio,
            PlatoFuerteServicio platoFuerteServicio, UsuarioServicio usuarioServicio, PedidoServicio pedidoServicio
            ) {
        this.entradaServicio = entradaServicio;
        this.bebidaServicio = bebidaServicio;
        this.postreServicio = postreServicio;
        this.platoFuerteServicio = platoFuerteServicio;
        this.usuarioServicio = usuarioServicio;
        this.pedidoServicio = pedidoServicio;
        this.pedidoActual = new Pedido();
        this.usuarioActual = new Usuario();

    }
    
    
//------------------Conexión de páginas web------------------

    @GetMapping("/")
    public String inicio(Model modelo){
        
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

    
//---------------------Cuenta del usuario---------------------

    @GetMapping("/registro")
    public String registroUsuario(@RequestParam("cedula") Integer cedula,
        @RequestParam("nombre") String nombre, 
        @RequestParam("telefono") String telefono,
        @RequestParam("correo") String correo,
        @RequestParam("contrasena") String contrasena,
        Model modelo
        ){
        
        //Verifico si el usuario existe
        var usuarioop = usuarioServicio.obtenerUsuario(cedula);
        
        //Si el usuario no existe, se procede a crearlo en la base de datos
        if(usuarioop.isEmpty()){
            usuarioServicio.crearUsuario(cedula, nombre, telefono, correo, contrasena);

        // Si el usuario existe se le muestra un mensaje de que ya tiene una cuenta
        }else {
            modelo.addAttribute("mensaje", "El usuario ya tiene una cuenta vinculada con ese número de cédula");
            return "form_registration";
        }  

        return "index";
    }

    @GetMapping("/inicio_sesion")
    public String ingresoSesion(@RequestParam("cedula") Integer cedula,
        @RequestParam("contrasena") String contrasena,
        Model modelo
        ){
        
        //Verifico si el usuario existe
        var usuario = usuarioServicio.obtenerUsuario(cedula);
        
        //Si el usuario no existe, se procede a crearlo en la base de datos
        if (usuario.isEmpty()) {
            modelo.addAttribute("mensaje", "El usuario no tiene una cuenta vinculada a este número de cédula. Lo invitamos a crearla.");
            return "form_registration";
        } 
        // Si el usuario existe se valida la contraseña
        if (!usuario.get().getContrasena().equals(contrasena)) {
            modelo.addAttribute("mensaje", "El usuario o la contraseña son incorrectas");
        }

        return "index";
    }
    @GetMapping("/cerrar_sesion")
    public String cerrarSesion(){
        return "index";
    }

//---------------------Carrito de compras---------------------

    @GetMapping("/entradas/{id}")
    public String agregarAlCarritoEntrada(@PathVariable("id") Integer id,  Model modelo){
        
        var entrada = this.entradaServicio.obtenerEntrada(id);
        if (!entrada.isEmpty()) {
            this.pedidoActual.getEntradas().add(entrada.get());
            modelo.addAttribute("mensaje", "La entrada se agregó exitosamente al carrito");
        } 
        menuEntradas(modelo);
        
        return "entradas";
    }   

}


