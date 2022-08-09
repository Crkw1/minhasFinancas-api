package com.br.project.minhasFinancas.service.impl;

import com.br.project.minhasFinancas.exception.RegraNegocioException;
import com.br.project.minhasFinancas.model.entity.Usuario;
import com.br.project.minhasFinancas.model.repository.UsuarioRepository;
import com.br.project.minhasFinancas.service.UsuarioService;
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
    public void validarEmail(String email) {
        boolean existe = repository.existsByEmail(email);
        if (existe) {
            throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
        }

    }

    @Override
    public Optional<Usuario> obterPorId(Long id) {
        return Optional.empty();
    }
}
