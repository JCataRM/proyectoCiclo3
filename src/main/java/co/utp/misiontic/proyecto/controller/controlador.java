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
    private final PedidoServicio pedidoServicio;
    private final UsuarioServicio usuarioServicio;
    private Pedido pedidoActual;

    public controlador(EntradaServicio entradaServicio, BebidaServicio bebidaServicio, PostreServicio postreServicio,
            PlatoFuerteServicio platoFuerteServicio, UsuarioServicio usuarioServicio, PedidoServicio pedidoServicio
            ) {
        this.entradaServicio = entradaServicio;
        this.bebidaServicio = bebidaServicio;
        this.postreServicio = postreServicio;
        this.platoFuerteServicio = platoFuerteServicio;
        this.pedidoServicio = pedidoServicio;
        this.usuarioServicio = usuarioServicio;
    }
    
    
//------------------------------------------Conexión de páginas web------------------------------------

    @GetMapping("/")
    public String inicio(Model modelo){
        if (pedidoActual == null) {
            this.pedidoActual = new Pedido();
        }
        if(this.pedidoActual.getEntradas() != null){
            var opEntradas = this.pedidoActual.getEntradas();         
            modelo.addAttribute("opEntradas", opEntradas);         
        }
        if(this.pedidoActual.getPlatosFuertes() != null){
            var opPlatos = this.pedidoActual.getPlatosFuertes();
            modelo.addAttribute("opPlatos", opPlatos);
        }
        if(this.pedidoActual.getPostres() != null){
            var opPostres = this.pedidoActual.getPostres();
            modelo.addAttribute("opPostres", opPostres);
        }
        if(this.pedidoActual.getBebidas() != null){
            var opBebidas = this.pedidoActual.getBebidas();
            modelo.addAttribute("opBebidas", opBebidas);
        }

        var total = pedidoServicio.calcularValorPedido(this.pedidoActual);
        modelo.addAttribute("total", total);

        System.out.println("Pedido index --------------------->" + this.pedidoActual);
        
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

    
//-----------------------------------Cuenta del usuario----------------------------------------------

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
            this.pedidoActual.setUsuario(usuarioServicio.crearUsuario(cedula, nombre, telefono, correo, contrasena));

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
            return "index";
        }
        this.pedidoActual.setUsuario(usuario.get());

        return "index";
    }
    @GetMapping("/cerrar_sesion")
    public String cerrarSesion(){
        return "index";
    }

//------------------------------------Carrito de compras-----------------------------------------------

    @GetMapping("/entradas/{id}")
    public String agregarEntradaAlCarrito(@PathVariable("id") Integer id,  Model modelo){
        
        var entrada = this.entradaServicio.obtenerEntrada(id);
        if (!entrada.isEmpty()) {
            System.out.println("entradaaaaaaaaaaaaaaaaaaaa ------------------->" + entrada);
            var entradas = this.pedidoActual.getEntradas();
            entradas.add(entrada.get());
            System.out.println("entradas --------------------->" + entradas);
            modelo.addAttribute("mensaje", "La entrada se agregó exitosamente al carrito");
        } 
        menuEntradas(modelo);
        
        return "entradas";
    } 
    
     @GetMapping("/platos_fuertes/{id}")
    public String agregarPlatoAlCarrito(@PathVariable("id") Integer id,  Model modelo){
        
        var plato = this.platoFuerteServicio.obtenerPlatoFuerte(id);
        if (!plato.isEmpty()) {
            var platos = this.pedidoActual.getPlatosFuertes();
            platos.add(plato.get());
            modelo.addAttribute("mensaje", "El plato fuerte se agregó exitosamente al carrito");
        } 
        menuPlatosFuertes(modelo);
        
        return "platos_fuertes";
    }   

    @GetMapping("/postres/{id}")
    public String agregarPostreAlCarrito(@PathVariable("id") Integer id,  Model modelo){
        
        var postre = this.postreServicio.obtenerPostre(id);
        if (!postre.isEmpty()) {
            var postres = this.pedidoActual.getPostres();
            postres.add(postre.get());
            modelo.addAttribute("mensaje", "El postre se agregó exitosamente al carrito");
        } 
        menuPostres(modelo);
        
        return "postres";
    }

    @GetMapping("/bebidas/{id}")
    public String agregarBebidaAlCarrito(@PathVariable("id") Integer id,  Model modelo){
        
        var bebida = this.bebidaServicio.obtenerBebida(id);
        if (!bebida.isEmpty()) {
            var bebidas = this.pedidoActual.getBebidas();
            bebidas.add(bebida.get());
            modelo.addAttribute("mensaje", "La bebida se agregó exitosamente al carrito");
        } 
        menuBebidas(modelo);
        
        return "bebidas";
    }

    @GetMapping("/eliminar/entrada/{id}")
    public String eliminarEntradaDeCarrito(@PathVariable("id") Integer id){
        
        var entrada = this.entradaServicio.obtenerEntrada(id);
        if (!entrada.isEmpty()) {
            var entradas = this.pedidoActual.getEntradas();
            entradas.remove(entrada.get());
        }
        return "index";
    }

    @GetMapping("/eliminar/plato_fuerte/{id}")
    public String eliminarPlatoDeCarrito(@PathVariable("id") Integer id){
        
        var plato = this.platoFuerteServicio.obtenerPlatoFuerte(id);
        if (!plato.isEmpty()) {
            var platos = this.pedidoActual.getPlatosFuertes();
            platos.remove(plato.get());
        }
        return "index";
    }

    @GetMapping("/eliminar/postre/{id}")
    public String eliminarPostreDeCarrito(@PathVariable("id") Integer id){
        
        var postre = this.postreServicio.obtenerPostre(id);
        if (!postre.isEmpty()) {
            var postres = this.pedidoActual.getPostres();
            postres.remove(postre.get());
        }
        return "index";
    }

    @GetMapping("/eliminar/bebida/{id}")
    public String eliminarBebidaDeCarrito(@PathVariable("id") Integer id){
        
        var bebida = this.bebidaServicio.obtenerBebida(id);
        if (!bebida.isEmpty()) {
            var bebidas = this.pedidoActual.getBebidas();
            bebidas.remove(bebida.get());
        }
        return "index";
    }

//------------------------------------Gestión de pedido-----------------------------------------------
    
    @GetMapping("/pedido")
    public String guardarPedido(){

        return "index";
    }

    

}


