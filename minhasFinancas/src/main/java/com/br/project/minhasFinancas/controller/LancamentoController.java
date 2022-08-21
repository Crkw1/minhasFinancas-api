package com.br.project.minhasFinancas.controller;

import com.br.project.minhasFinancas.dto.LancamentoDTO;
import com.br.project.minhasFinancas.model.entity.Lancamento;
import com.br.project.minhasFinancas.service.LancamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {

    private LancamentoService service;
    public LancamentoController(LancamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody LancamentoDTO dto) {

        return null;
    }
    private Lancamento converter(LancamentoDTO dto) {
        Lancamento lancamento = new Lancamento();
        return lancamento;
    }
}
