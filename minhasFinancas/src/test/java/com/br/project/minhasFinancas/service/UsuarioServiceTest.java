package com.br.project.minhasFinancas.service;

import com.br.project.minhasFinancas.exception.ErroAutenticacao;
import com.br.project.minhasFinancas.exception.RegraNegocioException;
import com.br.project.minhasFinancas.model.entity.Usuario;
import com.br.project.minhasFinancas.model.repository.UsuarioRepository;
import com.br.project.minhasFinancas.service.impl.UsuarioServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
    @SpyBean
    UsuarioServiceImpl service;
    @MockBean
    UsuarioRepository repository;

    @Test
    public void deveSalvarUmUsuario() {
        //cenario
        Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
        Usuario usuario = Usuario
                .builder()
                .id(1l)
                .nome("nome")
                .email("email@email.com")
                .senha("senha")
                .build();
        Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        //ação
        Usuario usuarioSalvo = service.salvarUsuario(new Usuario());

        //verificação
        Assertions.assertThat(usuarioSalvo).isNotNull();
        Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
        Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
        Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
        Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");

    }
    @Test
    public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
        //cenario
        String email = "email@email.com";
        Usuario usuario = Usuario.builder()
                .email(email)
                .build();
        Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail(email);

        //ação
        org.junit.jupiter.api.Assertions.assertThrows(RegraNegocioException.class,
                () -> service.salvarUsuario(usuario));

        //verificação
        Mockito.verify(repository, Mockito.never() ).save(usuario);

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
    public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {

        // cenario
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        //ação
        // service.autenticar("email@email.com", "senha");

        Throwable exception = Assertions.catchThrowable( () -> service.autenticar("email@email.com", "senha"));

        //verificação
        Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Usuário não encontrado.");

    }
    @Test
    public void deveLancerErroQuandoASenhaNaoBater() {

        // cenario
        String senha = "senha";
        Usuario usuario = Usuario
                .builder()
                .email("email@email.com")
                .senha("senha")
                .build();
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        //ação
        // Primeira ideia to teste
        // service.autenticar("email@email.com", "123" );
        Throwable exception = Assertions.catchThrowable( () -> service.autenticar("email@email.com", "123"));
        Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha incorreta.");

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

        // ação
        Throwable exception = Assertions.catchThrowable( () -> service.validarEmail("email@email.com"));
        Assertions.assertThat(exception).isInstanceOf(RegraNegocioException.class).hasMessage("Já existe um usuário cadastrado com este email.");
    }
}
