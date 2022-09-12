package co.utp.misiontic.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controlador {

    @GetMapping("/")
    public String inicio(){
        return "index";
    }
}
