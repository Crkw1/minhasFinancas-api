package com.br.project.minhasFinancas.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    @GetMapping("/teste")
    public String ola() {
        return "Ola mundo!";
    }
}
