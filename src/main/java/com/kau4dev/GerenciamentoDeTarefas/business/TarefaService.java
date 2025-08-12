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

    public void atualizarTarefa(Integer id, Tarefa tarefa) {
        Tarefa tarefaEntity = tarefaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Tarefa não encontrada com o id: " + id)
        );
        Tarefa tarefaAtualizada = Tarefa.builder()
                .id(tarefaEntity.getId())
                .titulo(tarefa.getTitulo() != null ? tarefa.getTitulo() : tarefaEntity.getTitulo())
                .descricao(tarefa.getDescricao() != null ? tarefa.getDescricao() : tarefaEntity.getDescricao())
                .status(tarefa.getStatus() != null ? tarefa.getStatus() : tarefaEntity.getStatus())
                .dataCriacao(tarefaEntity.getDataCriacao())
                .usuario(tarefa.getUsuario() != null ? tarefa.getUsuario() : tarefaEntity.getUsuario())
                .build();
        tarefaRepository.saveAndFlush(tarefaAtualizada);
    }

    public void alteraStatusTarefa(Integer id, Tarefa tarefa) {
        Tarefa tarefaEntity = tarefaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Tarefa não encontrada com o id: " + id)
        );
        Tarefa tarefaAtualizada = Tarefa.builder()
                .id(tarefaEntity.getId())
                .titulo(tarefaEntity.getTitulo())
                .descricao(tarefaEntity.getDescricao())
                .status(tarefa.getStatus() != null ? tarefa.getStatus() : tarefaEntity.getStatus())
                .dataCriacao(tarefaEntity.getDataCriacao())
                .dataConclusao(tarefa.getStatus() != null && tarefa.getStatus().name().equals("CONCLUIDA") ? java.time.LocalDateTime.now() : tarefaEntity.getDataConclusao())
                .usuario(tarefaEntity.getUsuario())
                .build();
        tarefaRepository.saveAndFlush(tarefaAtualizada);
    }

    public void deletarTarefa(Integer id){
        if (!tarefaRepository.existsById(id)){
            throw new RuntimeException("Tarefa não encontrada com o id: " + id);
        }
        tarefaRepository.deleteById(id);
    }



}
