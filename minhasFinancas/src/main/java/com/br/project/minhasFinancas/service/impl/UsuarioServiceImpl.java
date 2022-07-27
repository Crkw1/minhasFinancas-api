package com.br.project.minhasFinancas.service.impl;

import com.br.project.minhasFinancas.model.entity.Usuario;
import com.br.project.minhasFinancas.model.repository.UsuarioRepository;
import com.br.project.minhasFinancas.service.UsuarioService;

import java.util.Optional;

public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        super();
        this.repository = repository;
    }


    @Override
    public Usuario autenticar(String email, String senha) {
        return null;
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        return null;
    }

    @Override
    public void validarEmail(String email) {

    }

    @Override
    public Optional<Usuario> obterPorId(Long id) {
        return Optional.empty();
    }
}
