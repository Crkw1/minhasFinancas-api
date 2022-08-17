package com.br.project.minhasFinancas.service;

import com.br.project.minhasFinancas.model.entity.Usuario;
import com.br.project.minhasFinancas.model.repository.UsuarioRepository;
import com.br.project.minhasFinancas.service.impl.UsuarioServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;


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
    public void deveAutenticarUmUsuarioComSucesso() {
        //cenario
        String email = "email@email.com";
        String senha = "senha";

        Usuario usuario = Usuario.builder()
                .email(email)
                .senha(senha)
                .id(1l)
                .build();

        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

        //ação
        Usuario result = service.autenticar(email,senha);

        // verificação
        Assertions.assertThat(result).isNotNull();

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
