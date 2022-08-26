package com.br.project.minhasFinancas.service;

import com.br.project.minhasFinancas.model.entity.Lancamento;
import com.br.project.minhasFinancas.model.enums.StatusLancamento;
import com.br.project.minhasFinancas.model.repository.LancamentoRepository;
import com.br.project.minhasFinancas.model.repository.LancamentoRepositoryTest;
import com.br.project.minhasFinancas.service.impl.LancamentoServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

        Assertions.assertThat(lancamento.getId() ).isEqualTo(lancamentoSalvo.getId());
        Assertions.assertThat(lancamento.getStatus() ).isEqualTo(StatusLancamento.EFETIVADO);


    }

    @Test
    public void naoDeveSalvarUmLancamentoQuandoHouverErroDeValidacao() {


    }


}
