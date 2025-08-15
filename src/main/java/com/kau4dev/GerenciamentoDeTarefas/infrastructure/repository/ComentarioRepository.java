package com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository;

import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {

}
