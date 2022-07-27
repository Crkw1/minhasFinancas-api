package com.br.project.minhasFinancas.model.repository;

import com.br.project.minhasFinancas.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
