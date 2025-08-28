package com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UsuarioRepository extends JpaRepository<Usuario, Integer> {

    boolean existsByEmail(String email);

}
