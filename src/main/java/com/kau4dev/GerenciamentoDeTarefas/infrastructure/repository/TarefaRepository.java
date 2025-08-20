package com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository;

import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {

    List<Tarefa> findByUsuarioId(Integer usuarioId);

    Optional<Tarefa> findByIdAndUsuarioId(Integer id, Integer usuarioId);


}
