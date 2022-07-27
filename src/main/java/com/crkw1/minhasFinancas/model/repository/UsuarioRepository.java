package com.crkw1.minhasFinancas.model.repository;

import com.crkw1.minhasFinancas.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // forma didatica de explicação para a usar o findBy
    //   Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);

}
