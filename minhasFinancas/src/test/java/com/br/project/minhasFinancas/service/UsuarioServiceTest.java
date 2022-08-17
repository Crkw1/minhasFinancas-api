package com.br.project.minhasFinancas.service;

import com.br.project.minhasFinancas.exception.RegraNegocioException;
import com.br.project.minhasFinancas.model.entity.Usuario;
import com.br.project.minhasFinancas.model.repository.UsuarioRepository;
import com.br.project.minhasFinancas.service.impl.UsuarioServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
    UsuarioService service;
    @MockBean
    UsuarioRepository repository;

    @BeforeEach
    public void setUp() {
        service = new UsuarioServiceImpl(repository);
    }

    @Test
    public void deveValidarEmail() {

        //Segundo cenario usando mockito
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);

            /* Primeiro cenario usando test
            repository.deleteAll(); */


            // ação
            service.validarEmail("email@email.com");
    }
    @Test
    public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado () {
        // Segundo Cenario de teste
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);

        // Primeiro cenario de teste.
        // Usuario usuario = Usuario.builder()
           //             .nome("usuario")
             //           .email("email@email.com")
               //         .build();
        // repository.save(usuario);*/

        // Primeira ação de teste
        // service.validarEmail("email@email.com");



    }

}
