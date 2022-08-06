package com.br.project.minhasFinancas.service;

import com.br.project.minhasFinancas.model.entity.Usuario;

public interface JwtService {

    String gerarToken(Usuario usuario);

    Claims obterClaims(String token) throws ExpiredJwtException;

    boolean isTokenValido(String token);

    String obterLoginUsuario( String token );

}
