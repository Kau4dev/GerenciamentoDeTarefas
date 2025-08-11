package com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository;

import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entitys.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {

}
