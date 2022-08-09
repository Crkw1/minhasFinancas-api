package com.br.project.minhasFinancas.model.repository;

import com.br.project.minhasFinancas.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Test
    public void deveVerificarAExistenciaDeUmEmail() {
        // cenario
        Usuario usuario = Usuario.builder()
                .nome("usuario")
                .email("usuario@email.com")
                .build();
        repository.save(usuario);

        //acão / execução

        boolean result = repository.existsByEmail("usuario@email.com");

        //verificação
        Assertions.assertThat(result).isTrue();

    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
        //cenario
        repository.deleteAll();

        //ação
        boolean result = repository.existsByEmail("usuario@email.com");

        //verificar
        Assertions.assertThat(result).isFalse();
    }

}
