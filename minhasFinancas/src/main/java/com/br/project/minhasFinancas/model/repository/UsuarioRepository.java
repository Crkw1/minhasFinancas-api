package com.br.project.minhasFinancas.model.repository;

import com.br.project.minhasFinancas.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    // Outra forma de buscar o email para validação;
    boolean existsByEmail(String email);

}
