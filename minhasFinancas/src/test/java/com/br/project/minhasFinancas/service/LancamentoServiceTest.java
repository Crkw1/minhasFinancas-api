package com.br.project.minhasFinancas.service;

import com.br.project.minhasFinancas.exception.RegraNegocioException;
import com.br.project.minhasFinancas.model.entity.Lancamento;
import com.br.project.minhasFinancas.model.entity.Usuario;
import com.br.project.minhasFinancas.model.enums.StatusLancamento;
import com.br.project.minhasFinancas.model.repository.LancamentoRepository;
import com.br.project.minhasFinancas.model.repository.LancamentoRepositoryTest;
import com.br.project.minhasFinancas.service.impl.LancamentoServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LancamentoServiceTest {

    @SpyBean
    LancamentoServiceImpl service;
    @MockBean
    LancamentoRepository repository;

    @Test
    public void deveSalvarUmLancamento() {
        Lancamento lancamentoSalvar = LancamentoRepositoryTest.criarLancamento();
        Mockito.doNothing().when(service).validar(lancamentoSalvar);

        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
        lancamentoSalvo.setId(1l);
        lancamentoSalvo.setStatus(StatusLancamento.EFETIVADO);
        Mockito.when(repository.save(lancamentoSalvar)).thenReturn(lancamentoSalvo);
        Lancamento lancamento = service.salvar(lancamentoSalvar);

        assertThat(lancamento.getId() ).isEqualTo(lancamentoSalvo.getId());
        assertThat(lancamento.getStatus() ).isEqualTo(StatusLancamento.EFETIVADO);

    }

    @Test
    public void naoDeveSalvarUmLancamentoQuandoHouverErroDeValidacao() {
        Lancamento lancamentoSalvar = LancamentoRepositoryTest.criarLancamento();
        Mockito.doThrow(RegraNegocioException.class).when(service).validar(lancamentoSalvar);

        Assertions.catchThrowableOfType( () ->  service.salvar(lancamentoSalvar),
                RegraNegocioException.class);

        Mockito.verify(repository, Mockito.never()).save(lancamentoSalvar);
    }

    @Test
    public void deveAtualizarUmLancamento() {
        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
        lancamentoSalvo.setId(1l);
        lancamentoSalvo.setStatus(StatusLancamento.PENDENTE);

        Mockito.doNothing().when(service).validar(lancamentoSalvo);

        Mockito.when(repository.save(lancamentoSalvo)).thenReturn(lancamentoSalvo);

        service.atualizar(lancamentoSalvo);

        Mockito.verify(repository, Mockito.times(1)).save(lancamentoSalvo);

    }

    @Test
    public void deveLancarUmErroAoTentarAtualizarUmLancamentoQueAindaNaoFoiSalvo () {
        Lancamento lancamentoSalvar = LancamentoRepositoryTest.criarLancamento();

        Assertions.catchThrowableOfType( () ->  service.atualizar(lancamentoSalvar),
                NullPointerException.class);

        Mockito.verify(repository, Mockito.never()).save(lancamentoSalvar);
    }

    @Test
    public void deveDeletarUmLancamento() {
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(1l);

        service.deletar(lancamento);

        Mockito.verify(repository).delete(lancamento);

    }

    @Test
    public void deveLancarErroAoTentarDeletarUmLancamentoQueNaoFoiSalvo() {

        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();

        Assertions.catchThrowableOfType(() -> service.deletar(lancamento),
                NullPointerException.class);

        Mockito.verify(repository, Mockito.never()).delete(lancamento);

    }

    @Test
    public void deveFiltrarLancamentos() {
        // cenario
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(1l);

        List<Lancamento> lista = Arrays.asList(lancamento);

        Mockito.when(repository.findAll(Mockito.any(Example.class))).thenReturn(lista);

        //execução
        List<Lancamento> resultado = service.buscar(lancamento);

        //verificação
        assertThat(resultado).isNotEmpty().hasSize(1).contains(lancamento);

    }

    @Test
    public void deveAtualizarStatusDeUmLancamento() {
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(1l);
        lancamento.setStatus(StatusLancamento.PENDENTE);

        StatusLancamento novoStatus = StatusLancamento.EFETIVADO;
        Mockito.doReturn(lancamento).when(service).atualizar(lancamento);

        service.atualizarStatus(lancamento, novoStatus);

        assertThat(lancamento.getStatus()).isEqualTo(novoStatus);
        Mockito.verify(service).atualizar(lancamento);

    }

    @Test
    public void deveObterUmLancamentoPorId() {
        Long id = 1l;
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(1l);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(lancamento));

        Optional<Lancamento> resultado = service.obterPorId(id);

        assertThat(resultado.isPresent()).isTrue();


    }

    @Test
    public void deveRetornarVazioQuandoOLancamentoNaoExiste() {
        Long id = 1l;
        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
        lancamento.setId(1l);

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<Lancamento> resultado = service.obterPorId(id);

        assertThat(resultado.isPresent()).isFalse();

    }

    @Test
    public void deveLancarErrosAoValidarUmLancamento() {
        Lancamento lancamento = new Lancamento();

        Throwable erro = catchThrowable( () -> service.validar(lancamento) );
        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe uma Descrição válida.");

        lancamento.setDescricao("");

        erro = catchThrowable( () -> service.validar(lancamento) );
        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe uma Descrição válida.");

        lancamento.setDescricao("Salario");

        erro = catchThrowable( () -> service.validar(lancamento) );
        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Mês válido.");

        lancamento.setAno(0);

        erro = catchThrowable( () -> service.validar(lancamento) );
        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Mês válido.");

        lancamento.setAno(13);

        erro = catchThrowable( () -> service.validar(lancamento) );
        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Mês válido.");

        lancamento.setMes(1);

        erro = catchThrowable( () -> service.validar(lancamento) );
        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Ano válido.");

        lancamento.setAno(202);

        erro = catchThrowable( () -> service.validar(lancamento) );
        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Ano válido.");

        lancamento.setAno(2020);

        erro = catchThrowable( () -> service.validar(lancamento) );
        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Usuário.");

        lancamento.setUsuario(new Usuario());

        erro = catchThrowable( () -> service.validar(lancamento) );
        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Usuário.");

        lancamento.getUsuario().setId(1l);

        erro = catchThrowable( () -> service.validar(lancamento) );
        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Valor válido.");

        lancamento.setValor(BigDecimal.ZERO);

        erro = catchThrowable( () -> service.validar(lancamento) );
        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Valor válido.");

        lancamento.setValor(BigDecimal.valueOf(1));

        erro = catchThrowable( () -> service.validar(lancamento) );
        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um tipo de Lançamento.");



    }

}
