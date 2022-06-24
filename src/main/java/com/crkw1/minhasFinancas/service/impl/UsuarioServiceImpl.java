package com.crkw1.minhasFinancas.service.impl;

import com.crkw1.minhasFinancas.exception.RegraNegocioException;
import com.crkw1.minhasFinancas.model.entity.Usuario;
import com.crkw1.minhasFinancas.model.repository.UsuarioRepository;
import com.crkw1.minhasFinancas.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;

    @Autowired
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
    public void validarUsuario(String email) {

    }

    @Override
    public void validarEmail(String email) {
       boolean existe = repository.existsByEmail(email);
       if (existe) {
           throw new RegraNegocioException("Já existe um usuario cadastrado com este email.");
       }

    }

    @Override
    public Optional<Usuario> obterPorId(Long id) {
        return Optional.empty();
    }
}
