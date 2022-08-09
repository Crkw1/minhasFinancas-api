package com.br.project.minhasFinancas.service;

import com.br.project.minhasFinancas.model.repository.UsuarioRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository repository;

    @Test(expected = Test.None.class)
    public void deveValidarEmail() {
        //cenario
        repository.deleteAll();

        //ação
        usuarioService.validarEmail("email@email.com");

    }

}
