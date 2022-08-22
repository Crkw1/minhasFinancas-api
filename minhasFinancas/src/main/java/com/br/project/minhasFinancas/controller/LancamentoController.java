package com.br.project.minhasFinancas.controller;

import com.br.project.minhasFinancas.dto.LancamentoDTO;
import com.br.project.minhasFinancas.exception.RegraNegocioException;
import com.br.project.minhasFinancas.model.entity.Lancamento;
import com.br.project.minhasFinancas.model.entity.Usuario;
import com.br.project.minhasFinancas.model.enums.StatusLancamento;
import com.br.project.minhasFinancas.model.enums.TipoLancamento;
import com.br.project.minhasFinancas.service.LancamentoService;
import com.br.project.minhasFinancas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
@RequiredArgsConstructor
public class LancamentoController {

    private final LancamentoService service;
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity salvar(@RequestBody LancamentoDTO dto) {
        try{

            Lancamento entidade = converter(dto);
            entidade = service.salvar(entidade);
            return new ResponseEntity(entidade, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity buscar(
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "mes", required = false) Integer mes,
            @RequestParam(value = "ano", required = false) Integer ano,
            @RequestParam("usuario") Long idUsuario) {
        Lancamento lancamentoFiltro = new Lancamento();
        lancamentoFiltro.setDescricao(descricao);
        lancamentoFiltro.setMes(mes);
        lancamentoFiltro.setAno(ano);

       Optional<Usuario> usuario = usuarioService.obterPorId(idUsuario);
       if (usuario.isPresent()) {
           return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Usuário não encontrado");
       } else {
           lancamentoFiltro.setUsuario(usuario.get());
       }
        List<Lancamento> lancamentos = service.buscar(lancamentoFiltro);
       return ResponseEntity.ok(lancamentos);

    }


    @DeleteMapping
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        return service.obterPorId(id).map( entidade -> {
            service.deletar(entidade);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(() ->
                new ResponseEntity("Lançamento não encontrado na base de dados.",HttpStatus.BAD_REQUEST));
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody LancamentoDTO dto) {
           return service.obterPorId(id).map( entity -> {
               try {

                   Lancamento lancamento = converter(dto);
                   lancamento.setId(entity.getId());
                   service.atualizar(lancamento);
                   return ResponseEntity.ok(lancamento);
               }catch (RegraNegocioException e) {
                   return ResponseEntity.badRequest().body(e.getMessage());
               }
        }).orElseGet(() ->
                   new ResponseEntity("Lançamento não encontrado na base de dados.",HttpStatus.BAD_REQUEST));

    }

    private Lancamento converter(LancamentoDTO dto) {
        Lancamento lancamento = new Lancamento();
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setMes(dto.getMes());
        lancamento.setAno(dto.getAno());
        lancamento.setValor(dto.getValor());

       Usuario usuario = usuarioService.obterPorId(dto.getUsuario()).
                orElseThrow( () -> new RegraNegocioException("Usuário não encontrado por Id informado."));
        lancamento.setUsuario(usuario);

        if(dto.getTipo() != null) {
            lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        }

        if(dto.getStatus() != null) {
            lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        }
        return lancamento;
    }
}
