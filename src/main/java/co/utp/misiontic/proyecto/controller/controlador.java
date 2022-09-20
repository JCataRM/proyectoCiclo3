package co.utp.misiontic.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        this.pedidoActual = new Pedido();
    }
    
//------------------------------------------Conexión de páginas web------------------------------------

    @GetMapping("/")
    public String inicio(Model modelo){

    //Se listan todos los productos seleccionados por el usuario
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
    
    //Se calcula el valor total del pedido
        var total = pedidoServicio.calcularValorPedido(this.pedidoActual);
        modelo.addAttribute("total", total);
        this.pedidoActual.setPrecio_total(total);
       
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

    @PostMapping("/registro")
    public String registroUsuario(@RequestParam("cedula") Integer cedula,
        @RequestParam("nombre") String nombre, 
        @RequestParam("telefono") String telefono,
        @RequestParam("correo") String correo,
        @RequestParam("contrasena") String contrasena,
        @RequestParam("contrasena1") String contrasena1,
        Model modelo
        ){
        
        //Se verifica si el usuario existe
        var usuarioop = usuarioServicio.obtenerUsuario(cedula);
        
        //Si el usuario no existe, se procede a crearlo en la base de datos
        if(usuarioop.isEmpty()){
            //Se validan las contraseñas suministradas
            if(!contrasena.equals(contrasena1)){
                modelo.addAttribute("mensaje", "Las contraseñas suministradas no son iguales. Por favor intente nuevamente.");
            }else{
            //Si las contraseñas son iguales, se procede a crearlo en la base de datos
                var usuario = usuarioServicio.crearUsuario(cedula, nombre, telefono, correo, contrasena);
                this.pedidoActual.setUsuario(usuario);
                modelo.addAttribute("mensaje", "Su cuenta se ha creado exitosamente.");
            }
        // Si el usuario existe se le muestra un mensaje de que ya tiene una cuenta
        }else {
            modelo.addAttribute("mensaje", "El usuario ya tiene una cuenta vinculada con ese número de cédula");
        }  

        return "form_registration";
    }

    @PostMapping("/inicio_sesion")
    public String ingresoSesion(@RequestParam("cedula") Integer cedula,
        @RequestParam("contrasena") String contrasena,
        Model modelo
        ){
        
        //Se verifica si el usuario existe
        var usuarioOp = usuarioServicio.obtenerUsuario(cedula);
        
        //Si el usuario no existe, se le sugiere crear una cuenta
        if (usuarioOp.isEmpty()) {
            modelo.addAttribute("mensaje", "El usuario no tiene una cuenta vinculada a este número de cédula. Lo invitamos a crearla.");
            return "form_registration";
        } 
        // Si el usuario existe se valida la contraseña
        if (!usuarioOp.get().getContrasena().equals(contrasena)) {
            modelo.addAttribute("mensaje", "El usuario o la contraseña son incorrectas");

        // Se agrega el usuario al pedido que se encuentra en proceso
        }else{
            var usuario = usuarioOp.get();
            this.pedidoActual.setUsuario(usuario);
        }   
        inicio(modelo);
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
            var entradas = this.pedidoActual.getEntradas();
            entradas.add(entrada.get());
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

    @GetMapping("/eliminar_entrada/{id}")
    public String eliminarEntradaDeCarrito(@PathVariable("id") Integer id, Model modelo){
        
        var entrada = this.entradaServicio.obtenerEntrada(id);
        if (!entrada.isEmpty()) {
            var entradas = this.pedidoActual.getEntradas();
            entradas.remove(entrada.get());
            modelo.addAttribute("mensaje", "La entrada se eliminó del carrito. Aquí puedes escoger otras opciones de entrada.");
        } 
        menuEntradas(modelo);
        return "entradas";
    }

    @GetMapping("/eliminar_plato_fuerte/{id}")
    public String eliminarPlatoDeCarrito(@PathVariable("id") Integer id, Model modelo){
        
        var plato = this.platoFuerteServicio.obtenerPlatoFuerte(id);
        if (!plato.isEmpty()) {
            var platos = this.pedidoActual.getPlatosFuertes();
            platos.remove(plato.get());
            modelo.addAttribute("mensaje", "El plato se eliminó del carrito. Aquí puedes escoger otras opciones de plato fuerte.");
        }
        menuPlatosFuertes(modelo);
        return "platos_fuertes";
    }

    @GetMapping("/eliminar_postre/{id}")
    public String eliminarPostreDeCarrito(@PathVariable("id") Integer id, Model modelo){
        
        var postre = this.postreServicio.obtenerPostre(id);
        if (!postre.isEmpty()) {
            var postres = this.pedidoActual.getPostres();
            postres.remove(postre.get());
            modelo.addAttribute("mensaje", "El postre se eliminó del carrito. Aquí puedes escoger otras opciones de postre.");
        }
        menuPostres(modelo);
        return "postres";
    }

    @GetMapping("/eliminar_bebida/{id}")
    public String eliminarBebidaDeCarrito(@PathVariable("id") Integer id, Model modelo){
        
        var bebida = this.bebidaServicio.obtenerBebida(id);
        if (!bebida.isEmpty()) {
            var bebidas = this.pedidoActual.getBebidas();
            bebidas.remove(bebida.get());
            modelo.addAttribute("mensaje", "La bebida se eliminó del carrito. Aquí puedes escoger otras opciones de bebida.");
        }
        menuBebidas(modelo);
        return "bebidas";
    }
//------------------------------------Carrito favoritos-----------------------------------------------
    
    @GetMapping("/favoritos_entradas/{id}")
    public String agregarFavoritosEntradaAlCarrito(@PathVariable("id") Integer id,  Model modelo){
        
        var entrada = this.entradaServicio.obtenerEntrada(id);
        if (!entrada.isEmpty()) {
            var entradas = this.pedidoActual.getEntradas();
            entradas.add(entrada.get());
            modelo.addAttribute("mensaje", "La entrada se agregó exitosamente al carrito. Aquí puedes ver otras opciones de entrada.");
        } 
        menuEntradas(modelo);
        return "entradas";
    } 

    @GetMapping("/favoritos_platos_fuertes/{id}")
    public String agregarFavoritosPlatoAlCarrito(@PathVariable("id") Integer id,  Model modelo){
        
        var plato = this.platoFuerteServicio.obtenerPlatoFuerte(id);
        if (!plato.isEmpty()) {
            var platos = this.pedidoActual.getPlatosFuertes();
            platos.add(plato.get());
            modelo.addAttribute("mensaje", "El plato fuerte se agregó exitosamente al carrito. Aquí puedes ver otras opciones de plato fuerte.");
        } 
        
        menuPlatosFuertes(modelo);
        return "platos_fuertes";
    }   

    @GetMapping("/favoritos_postres/{id}")
    public String agregarFavoritosPostreAlCarrito(@PathVariable("id") Integer id,  Model modelo){
        
        var postre = this.postreServicio.obtenerPostre(id);
        if (!postre.isEmpty()) {
            var postres = this.pedidoActual.getPostres();
            postres.add(postre.get());
            modelo.addAttribute("mensaje", "El postre se agregó exitosamente al carrito. Aquí puedes ver otras opciones de postre.");
        } 
        
        menuPostres(modelo);
        return "postres";
    }

    @GetMapping("/favoritos_bebidas/{id}")
    public String agregarFavoritosBebidaAlCarrito(@PathVariable("id") Integer id,  Model modelo){
        
        var bebida = this.bebidaServicio.obtenerBebida(id);
        if (!bebida.isEmpty()) {
            var bebidas = this.pedidoActual.getBebidas();
            bebidas.add(bebida.get());
            modelo.addAttribute("mensaje", "La bebida se agregó exitosamente al carrito. Aquí puedes ver otras opciones de bebida.");
        } 
        
        menuBebidas(modelo);
        return "bebidas";
    }


//------------------------------------Gestión de pedido-----------------------------------------------
    
    @PostMapping("/pedido")
    public String guardarPedido(@RequestParam("fecha_reserva") String fecha,
        @RequestParam("hora_reserva") String hora,
        Model modelo){
            System.out.println("Horaaaaaaaaaaaaaaaa in" + hora);

            //Se valida si el usuario ya ingreso con su cuenta para hacer el pedido
            if (this.pedidoActual.getUsuario() == null) {
                modelo.addAttribute("mensaje", "Debe iniciar sesión para realizar un pedido");
                return "index";
            }

            //Se valida el horario de atención del restaurante
            var horaAtencion = Integer.parseInt(hora.substring(0, 1)+hora.substring(3, 4));
            if(!(Integer.parseInt(hora)>1029 && Integer.parseInt(hora)<2031)){
                System.out.println("Horaaaaaaaaaaaaaaaaaaaaaaa" + hora);
                modelo.addAttribute("mensaje", "No se puede hacer la reserva porque el horario de atención no corresponde a la hora fijada.");
            }
            
            //Se valida si el usuario tiene un producto en el carrito
            if (this.pedidoActual.getEntradas() != null || this.pedidoActual.getPlatosFuertes() != null ||
                this.pedidoActual.getPostres() != null || this.pedidoActual.getBebidas() != null ) {
                    
                    //Se agregan los datos de la reserva al pedido
                    this.pedidoActual.setFecha_reserva(fecha);
                    this.pedidoActual.setHora_reserva(hora);

                    //Se guarda el pedido en la base de datos
                    pedidoServicio.guardarPedido(this.pedidoActual);
                    modelo.addAttribute("mensaje", "El pedido se realizó exitosamente.");

                    //Se restablece el pedido
                    this.pedidoActual = new Pedido();
            }else{
                modelo.addAttribute("mensaje", "Debe agregar un producto al carrito de compras para realizar el pedido.");
            }
        
        inicio(modelo);
        return "index";
    }


}


