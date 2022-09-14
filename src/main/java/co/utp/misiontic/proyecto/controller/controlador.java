package co.utp.misiontic.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import co.utp.misiontic.proyecto.service.*;


@Controller
public class controlador {

    private EntradaServicio entradaServicio;
    private BebidaServicio bebidaServicio;
    private PostreServicio postreServicio;
    private PlatoFuerteServicio platoFuerteServicio;

    public controlador(EntradaServicio entradaServicio, BebidaServicio bebidaServicio,
     PostreServicio postreServicio, PlatoFuerteServicio platoFuerteServicio) {
        this.entradaServicio = entradaServicio;
        this.bebidaServicio = bebidaServicio;
        this.postreServicio = postreServicio;
        this.platoFuerteServicio = platoFuerteServicio;
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
    
}
