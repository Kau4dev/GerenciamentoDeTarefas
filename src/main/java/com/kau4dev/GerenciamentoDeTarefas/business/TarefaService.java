package com.kau4dev.GerenciamentoDeTarefas.business;

import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entitys.Tarefa;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public void criarTarefa(Tarefa tarefa) {
        tarefaRepository.saveAndFlush(tarefa);

    }

    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    public Tarefa buscarTarefaPorId(Integer id) {
        return tarefaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Tarefa não encontrada com o id: " + id)
        );
    }



}
