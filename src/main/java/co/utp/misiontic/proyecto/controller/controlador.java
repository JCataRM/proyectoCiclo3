package co.utp.misiontic.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controlador {

    @GetMapping("/")
    public String inicio(){
        return "index";
    }
    @GetMapping("/entradas")
    public String menuEntradas(){
        return "entradas";
    }
    @GetMapping("/platos_fuertes")
    public String menuPlatosFuertes(){
        return "platos_fuertes";
    }
    @GetMapping("/postres")
    public String menuPostres(){
        return "postres";
    }
    @GetMapping("/bebidas")
    public String menuBebidas(){
        return "bebidas";
    }
}
