package co.utp.misiontic.proyecto.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private List<OpcionEntrada> entradasBd;
    private List<OpcionPlatoFuerte> platosBd;
    private List<OpcionPostre> postresBd;
    private List<OpcionBebida> bebidasBd;

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
        this.entradasBd = new ArrayList<>();
        this.platosBd = new ArrayList<>();
        this.postresBd = new ArrayList<>();
        this.bebidasBd= new ArrayList<>();
    }
    
//------------------------------------Conexión de páginas web--------------------------------------------------------------

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
    //Se asigna usuario
        if (this.pedidoActual.getUsuario() != null) {
            modelo.addAttribute("nombreUsuario", this.pedidoActual.getUsuario().getNombre());
        }      

    //Se calcula el valor total del pedido
        var total = pedidoServicio.calcularValorPedido(this.pedidoActual);
        modelo.addAttribute("total", total);
        this.pedidoActual.setPrecio_total(total);

    //Prueba
        System.out.println("Pedido -------------------> " + this.pedidoActual);
       
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
        return "form_registration";
    }

    
//------------------------------------Cuenta del usuario-------------------------------------------------------------------

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
                var nombreUsuario = usuario.getNombre();
                //Se agrega el usuario al pedido que está abierto.
                this.pedidoActual.setUsuario(usuario);
                modelo.addAttribute("mensaje", "Su cuenta se ha creado exitosamente.");
                modelo.addAttribute("nombreUsuario", nombreUsuario);
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
            modelo.addAttribute("mensaje", "El usuario o la contraseña son incorrectas. Por favor intente nuevamente.");

        // Se agrega el usuario al pedido que se encuentra abierto
        }else{
            var usuario = usuarioOp.get();
            var nombreUsuario = usuario.getNombre();
            this.pedidoActual.setUsuario(usuario);
            modelo.addAttribute("nombreUsuario", nombreUsuario);
            modelo.addAttribute("mensaje", null);
        }   
        inicio(modelo);
        return "index";
    }
    @GetMapping("/cerrar_sesion")
    public String cerrarSesion(Model modelo){
        
        this.pedidoActual = new Pedido();
        
        inicio(modelo);
        return "index";
    }

//------------------------------------Carrito de compras-------------------------------------------------------------------

    @GetMapping("/entradas/{id}")
    public String agregarEntradaAlCarrito(@PathVariable("id") Integer id,  Model modelo){
        //Se obtiene la lista de entradas que seleccionó el usuario y la entrada que entra como parámetro
        var entradas = this.pedidoActual.getEntradas();
        var entrada = this.entradaServicio.obtenerEntrada(id);

        //Se valida la restricción de cantidad máxima de entradas
        if(entradaServicio.validarCantidadEntradas(entradas)){
            modelo.addAttribute("mensajeLimite", "No puede seleccionar más de cinco entradas en un pedido.");
            
            menuEntradas(modelo);
            return "entradas";
        }
        
        //Se valida si existe un producto con ese id.
        if (!entrada.isEmpty()) {

        //Se consulta si el producto ya está en la lista
        var entradaLista = entradas.stream().filter(m -> m.getId() == id).findFirst();

            //Si el producto NO está, se adiciona a la lista.
            if (entradaLista.isEmpty()) {
                    entradas.add(entrada.get());
                    entradasBd.add(entrada.get());
                    modelo.addAttribute("mensaje", "La entrada '" + entrada.get().getNombre() + "' se agregó exitosamente al carrito.");
                }
            //Si el producto está en la lista, se le adiciona una cantidad.
            else{
                agregarEntrada(id, modelo);

            }
        } else{
            modelo.addAttribute("mensaje", "No existe un producto con esa referencia. Por favor intenta nuevamente");
        }
        
        menuEntradas(modelo);
        return "entradas";
    } 

    @GetMapping("/platos_fuertes/{id}")
    public String agregarPlatoAlCarrito(@PathVariable("id") Integer id,  Model modelo){
        //Se obtiene la lista de platos que seleccionó el usuario y el plato que entra como parámetro
        var platos = this.pedidoActual.getPlatosFuertes();
        var plato = this.platoFuerteServicio.obtenerPlatoFuerte(id);

        //Se valida la restricción de cantidad máxima de platos
        if(platoFuerteServicio.validarCantidadPlatos(platos)){
            modelo.addAttribute("mensajeLimite", "No puede seleccionar más de cinco platos fuertes en un pedido.");
            
            menuPlatosFuertes(modelo);
            return "platos_fuertes";
        }
        
        //Se valida si existe un producto con ese id.
        if (!plato.isEmpty()) {

        //Se consulta si el producto ya está en la lista
        var platoLista = platos.stream().filter(m -> m.getId() == id).findFirst();

            //Si el producto NO está, se adiciona a la lista.
            if (platoLista.isEmpty()) {
                    platos.add(plato.get());
                    platosBd.add(plato.get());
                    modelo.addAttribute("mensaje", "El plato fuerte '" + plato.get().getNombre() + "' se agregó exitosamente al carrito.");
                }
            //Si el producto está en la lista, se le adiciona una cantidad.
            else{
                agregarPlato(id, modelo);
            }
        } else{
            modelo.addAttribute("mensaje", "No existe un producto con esa referencia. Por favor intenta nuevamente");
        }        
        
        menuPlatosFuertes(modelo);
        return "platos_fuertes";
    } 

    @GetMapping("/postres/{id}")
    public String agregarPostresAlCarrito(@PathVariable("id") Integer id,  Model modelo){
        //Se obtiene la lista de postres que seleccionó el usuario y el postre que entra como parámetro
        var postres = this.pedidoActual.getPostres();
        var postre = this.postreServicio.obtenerPostre(id);

        //Se valida la restricción de cantidad máxima de postres
        if(postreServicio.validarCantidadPostres(postres)){
            modelo.addAttribute("mensajeLimite", "No puede seleccionar más de cinco postres en un pedido.");
            
            menuPostres(modelo);
            return "postres";
        }
        
        //Se valida si existe un producto con ese id.
        if (!postre.isEmpty()) {

        //Se consulta si el producto ya está en la lista
        var postreLista = postres.stream().filter(m -> m.getId() == id).findFirst();

            //Si el producto NO está, se adiciona a la lista.
            if (postreLista.isEmpty()) {
                    postres.add(postre.get());
                    postresBd.add(postre.get());
                    modelo.addAttribute("mensaje", "El postre '" + postre.get().getNombre() + "' se agregó exitosamente al carrito.");
                }
            //Si el producto está en la lista, se le adiciona una cantidad.
            else{
                agregarPostre(id, modelo);
            }
        } else{
            modelo.addAttribute("mensaje", "No existe un producto con esa referencia. Por favor intenta nuevamente");
        } 

        menuPostres(modelo);
        return "postres";
    } 

    @GetMapping("/bebidas/{id}")
    public String agregarBebidaAlCarrito(@PathVariable("id") Integer id,  Model modelo){
        //Se obtiene la lista de bebidas que seleccionó el usuario y la bebida que entra como parámetro
        var bebidas = this.pedidoActual.getBebidas();
        var bebida = this.bebidaServicio.obtenerBebida(id);

        //Se valida la restricción de cantidad máxima de bebidas
        if(bebidaServicio.validarCantidadBebidas(bebidas)){
            modelo.addAttribute("mensajeLimite", "No puede seleccionar más de cinco bebidas en un pedido.");
            
            menuBebidas(modelo);
            return "bebidas";
        }
        
        //Se valida si existe un producto con ese id.
        if (!bebida.isEmpty()) {

        //Se consulta si el producto ya está en la lista
        var bebidaLista = bebidas.stream().filter(m -> m.getId() == id).findFirst();

            //Si el producto NO está, se adiciona a la lista.
            if (bebidaLista.isEmpty()) {
                    bebidas.add(bebida.get());
                    bebidasBd.add(bebida.get());
                    modelo.addAttribute("mensaje", "La bebida '" + bebida.get().getNombre() + "' se agregó exitosamente al carrito.");
                } 
            //Si el producto está en la lista, se le adiciona una cantidad.
            else{
                agregarBebida(id, modelo);
            }
        } else{
            modelo.addAttribute("mensaje", "No existe un producto con esa referencia. Por favor intenta nuevamente");
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
            modelo.addAttribute("mensaje", "La entrada '" + entrada.get().getNombre() + "' se eliminó del carrito. Aquí puedes escoger otras opciones de entrada.");
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
            modelo.addAttribute("mensaje", "El plato '"+ plato.get().getNombre() + "' se eliminó del carrito. Aquí puedes escoger otras opciones de plato fuerte.");
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
            modelo.addAttribute("mensaje", "El postre '" + postre.get().getNombre() + "' se eliminó del carrito. Aquí puedes escoger otras opciones de postre.");
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
            modelo.addAttribute("mensaje", "La bebida '" + bebida.get().getNombre() + "' se eliminó del carrito. Aquí puedes escoger otras opciones de bebida.");
        }
        menuBebidas(modelo);
        return "bebidas";
    }

//------------------------------------Gestión de pedido en el carrito------------------------------------------------------
    
    @PostMapping("/carrito")
    public String guardarPedido(@RequestParam("fecha_reserva") String fecha,
        @RequestParam("hora_reserva") String hora,
        Model modelo){

            final var hoy = LocalDate.now().toString();

            var fechaHoy = Integer.parseInt(hoy.substring(5, 7) + hoy.substring(8, 10));
            var fechaAtencion = Integer.parseInt(fecha.substring(5, 7) + fecha.substring(8, 10));
            var horaAtencion = Integer.parseInt(hora.substring(0, 2) + hora.substring(3, 5));

            //Se valida si el usuario ya ingreso con su cuenta para hacer el pedido
            if (this.pedidoActual.getUsuario() == null) {
                
                modelo.addAttribute("mensaje", "No puede realizar una reserva sin iniciar sesión.");
                inicio(modelo);
                return "index";
            }

            //Se valida el dia de la reserva
            if (fechaAtencion < fechaHoy) {
                
                modelo.addAttribute("mensaje", "No se puede realizar la reserva porque el día de la reserva está en pasado.");
                inicio(modelo);
                return "index";
            }

            //Se valida si la reserva es para el día actual.
            if (fechaAtencion == fechaHoy) {
                
                modelo.addAttribute("mensaje", "No se puede realizar una reserva para el mismo día.");
                inicio(modelo);
                return "index";
            }

            //Se valida el horario de atención del restaurante
            if(!(horaAtencion > 1029 && horaAtencion < 2031)){
                
                modelo.addAttribute("mensaje", "No se puede realizar la reserva porque la hora escogida no corresponde al horario de atención.");
                inicio(modelo);
                return "index";
            }
                        
            //Se valida si el usuario tiene un producto en el carrito
            if (this.pedidoActual.getEntradas().isEmpty() && this.pedidoActual.getPlatosFuertes().isEmpty() &&
                this.pedidoActual.getPostres().isEmpty() && this.pedidoActual.getBebidas().isEmpty() ) {
                
                modelo.addAttribute("mensaje", "Debe agregar un producto al carrito de compras para realizar la reserva.");
                inicio(modelo);
                return "index";
            } 

            //Si pasa todas las validaciones, se agregan los datos de la reserva al pedido
            this.pedidoActual.setFecha_reserva(fecha);
            this.pedidoActual.setHora_reserva(hora);

            //Se guarda el pedido en la base de datos
            pedidoServicio.guardarPedido(this.pedidoActual, this.entradasBd, this.platosBd, this.postresBd, this.bebidasBd);
            modelo.addAttribute("mensajeOk", "El pedido se realizó exitosamente. Gracias por escogernos!");

            //Se restablece el pedido
            var usuario = this.pedidoActual.getUsuario();
            this.pedidoActual = new Pedido();
            this.pedidoActual.setUsuario(usuario);
            
        
            inicio(modelo);
            return "index";
    }
    @GetMapping("/agregar_entrada/{id}")
    public String agregarEntrada(@PathVariable("id") Integer id, Model modelo){

        var entradas = this.pedidoActual.getEntradas();

        //Se valida la restricción de cantidad máxima de entradas
        if(entradaServicio.validarCantidadEntradas(entradas)){
            modelo.addAttribute("mensajeLimite", "No puede seleccionar más de cinco entradas en un pedido.");
            
            menuEntradas(modelo);
            return "entradas";
        }

        var entrada = entradas.stream().filter(m -> m.getId() == id).findFirst().get();
        var posicion = entradas.indexOf(entrada);
        var cantidadNueva = entrada.getCantidad() + 1;
        
        entrada.setCantidad(cantidadNueva);
        entradas.set(posicion, entrada);
        entradasBd.add(entrada);
        
        modelo.addAttribute("mensaje", "La entrada '" + entrada.getNombre() + "' se le adicionó una unidad. Aquí puedes ver otras opciones de entrada.");
        
        menuEntradas(modelo);
        return "entradas";
    }

    @GetMapping("/agregar_plato/{id}")
    public String agregarPlato(@PathVariable("id") Integer id, Model modelo){

        var platos = this.pedidoActual.getPlatosFuertes();

        //Se valida la restricción de cantidad máxima de platos
        if(platoFuerteServicio.validarCantidadPlatos(platos)){
            modelo.addAttribute("mensajeLimite", "No puede seleccionar más de cinco platos fuertes en un pedido.");
            
            menuPlatosFuertes(modelo);
            return "platos_fuertes";
        }

        var plato = platos.stream().filter(m -> m.getId() == id).findFirst().get();
        var posicion = platos.indexOf(plato);
        var cantidadNueva = plato.getCantidad() + 1;

        plato.setCantidad(cantidadNueva);
        platos.set(posicion, plato);
        platosBd.add(plato);
        
        modelo.addAttribute("mensaje", "El plato fuerte '" + plato.getNombre() + "' se le adicionó una unidad. Aquí puedes ver otras opciones de plato fuerte.");
        
        menuPlatosFuertes(modelo);
        return "platos_fuertes";
    }

    @GetMapping("/agregar_postre/{id}")
    public String agregarPostre(@PathVariable("id") Integer id, Model modelo){

        var postres = this.pedidoActual.getPostres();

        //Se valida la restricción de cantidad máxima de postres
        if(postreServicio.validarCantidadPostres(postres)){
            modelo.addAttribute("mensajeLimite", "No puede seleccionar más de cinco postres en un pedido.");
            
            menuPostres(modelo);
            return "postres";
        }

        var postre = postres.stream().filter(m -> m.getId() == id).findFirst().get();
        var posicion = postres.indexOf(postre);
        var cantidadNueva = postre.getCantidad() + 1;

        postre.setCantidad(cantidadNueva);
        postres.set(posicion, postre);
        postresBd.add(postre);
        
        modelo.addAttribute("mensaje", "El postre '" + postre.getNombre() + "' se le adicionó una unidad. Aquí puedes ver otras opciones de postre.");
        menuPostres(modelo);
        return "postres";
    }

    @GetMapping("/agregar_bebida/{id}")
    public String agregarBebida(@PathVariable("id") Integer id, Model modelo){

        var bebidas = this.pedidoActual.getBebidas();

        //Se valida la restricción de cantidad máxima de bebidas
        if(bebidaServicio.validarCantidadBebidas(bebidas)){
            modelo.addAttribute("mensajeLimite", "No puede seleccionar más de cinco bebidas en un pedido.");
            
            menuBebidas(modelo);
            return "bebidas";
        }
        
        var bebida = bebidas.stream().filter(m -> m.getId() == id).findFirst().get();
        var posicion = bebidas.indexOf(bebida);
        var cantidadNueva = bebida.getCantidad() + 1;

        bebida.setCantidad(cantidadNueva);
        bebidas.set(posicion, bebida);
        bebidasBd.add(bebida);
        
        modelo.addAttribute("mensaje", "La bebida '" + bebida.getNombre() + "' se le adicionó una unidad. Aquí puedes ver otras opciones de bebida.");
        menuBebidas(modelo);
        return "bebidas";
    }

    @GetMapping("/disminuir_entrada/{id}")
    public String disminuirEntrada(@PathVariable("id") Integer id, Model modelo){

        var entradas = this.pedidoActual.getEntradas();
        var entrada = entradas.stream().filter(m -> m.getId() == id).findFirst().get();
        
        //Se evalua la cantidad del producto
        if(entrada.getCantidad() > 1){
            var posicion = entradas.indexOf(entrada);
            var cantidadNueva = entrada.getCantidad() - 1;

            entrada.setCantidad(cantidadNueva);
            entradas.set(posicion, entrada);
            modelo.addAttribute("mensaje", "Se eliminó una unidad de la entrada '" + entrada.getNombre() + "'. Aquí puedes ver otras opciones de entrada.");
            
        } else{
            eliminarEntradaDeCarrito(id, modelo);
        }

        menuEntradas(modelo);
            return "entradas";
    }

    @GetMapping("/disminuir_plato/{id}")
    public String disminuirPlato(@PathVariable("id") Integer id, Model modelo){

        var platos = this.pedidoActual.getPlatosFuertes();
        var plato = platos.stream().filter(m -> m.getId() == id).findFirst().get();
        
        //Se evalua la cantidad del producto
        if(plato.getCantidad() > 1){
            var posicion = platos.indexOf(plato);
            var cantidadNueva = plato.getCantidad() - 1;

            plato.setCantidad(cantidadNueva);
            platos.set(posicion, plato);
            modelo.addAttribute("mensaje", "Se eliminó una unidad del plato fuerte '" + plato.getNombre() + "'. Aquí puedes ver otras opciones de plato fuerte.");

        } else{
            eliminarPlatoDeCarrito(id, modelo);
        }

        menuEntradas(modelo);
            return "entradas";
    }

    @GetMapping("/disminuir_postre/{id}")
    public String disminuirPostre(@PathVariable("id") Integer id, Model modelo){

        var postres = this.pedidoActual.getPostres();
        var postre = postres.stream().filter(m -> m.getId() == id).findFirst().get();
        
        //Se evalua la cantidad del producto
        if(postre.getCantidad() > 1){
            var posicion = postres.indexOf(postre);
            var cantidadNueva = postre.getCantidad() - 1;

            postre.setCantidad(cantidadNueva);
            postres.set(posicion, postre);
            modelo.addAttribute("mensaje", "Se eliminó una unidad del postre '" + postre.getNombre() + "'. Aquí puedes ver otras opciones de postre.");

        } else{
            eliminarPostreDeCarrito(id, modelo);
        }

        menuPostres(modelo);
            return "postres";
    }

    @GetMapping("/disminuir_bebida/{id}")
    public String disminuirBebida(@PathVariable("id") Integer id, Model modelo){

        var bebidas = this.pedidoActual.getBebidas();
        var bebida = bebidas.stream().filter(m -> m.getId() == id).findFirst().get();
        
        //Se evalua la cantidad del producto
        if(bebida.getCantidad() > 1){
            var posicion = bebidas.indexOf(bebida);
            var cantidadNueva = bebida.getCantidad() - 1;

            bebida.setCantidad(cantidadNueva);
            bebidas.set(posicion, bebida);
            modelo.addAttribute("mensaje", "Se eliminó una unidad de la bebida '" + bebida.getNombre() + "'. Aquí puedes ver otras opciones de bebida.");

        } else{
            eliminarBebidaDeCarrito(id, modelo);
        }

        menuBebidas(modelo);
            return "bebidas";
    }

//------------------------------------Listado de pedidos-------------------------------------------------------------------
    
    @GetMapping("/pedidos")
    public String listarPedidos(Model modelo){
        
        var usuario = this.pedidoActual.getUsuario();
        modelo.addAttribute("nombre", usuario.getNombre());

        //Se verifica si el usuario es cliente
        if(usuario.getTipoUsuario().equals("cliente")){
            
            var pedidos = pedidoServicio.listarPedidosPorCliente(usuario.getId());
            modelo.addAttribute("pedidos", pedidos);

        } 
        //Si el usuario es administrador se ejecuta este bloque
        else if (usuario.getTipoUsuario().equals("administrador")) {
            
            var pedidos = pedidoServicio.listarPedidosPorEntregar();
            modelo.addAttribute("pedidos", pedidos);
            modelo.addAttribute("admin", usuario.getTipoUsuario());
        }

        return "pedidos";
    }

    @GetMapping("entregar_pedido/{id}")
    public String cambiarEstadoPedido(@PathVariable("id") Integer id, Model modelo){
        pedidoServicio.actualizarEstadoPedido(id);
        
        listarPedidos(modelo);
        return "pedidos";
    }


}


