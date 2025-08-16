package com.kau4dev.GerenciamentoDeTarefas.business;

import com.kau4dev.GerenciamentoDeTarefas.dto.TarefaDTO;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Tarefa;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Usuario;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.TarefaRepository;
import com.kau4dev.GerenciamentoDeTarefas.mapper.TarefaMapper;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.enums.StatusTarefa;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository repository;
    private final TarefaMapper mapper;

    public TarefaService(TarefaRepository repository, TarefaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public TarefaDTO criarTarefa(Usuario usuario, TarefaDTO tarefaDTO) {
        Tarefa tarefa = mapper.toEntity(tarefaDTO);
        tarefa.setIdUsuario(usuario);
        tarefa.setDataCriacao(LocalDateTime.now());
        Tarefa tarefaSalva = repository.saveAndFlush(tarefa);
        return mapper.toDTO(tarefaSalva);
    }

    public List<TarefaDTO> listarTarefas() {
        List<Tarefa> tarefas = repository.findAll();
        return tarefas.stream()
                .map(mapper::toDTO)
                .toList();
    }

    public TarefaDTO buscarTarefaPorId(Integer idTarefa) {
        Tarefa tarefa = repository.findById(idTarefa).orElseThrow(
                () -> new RuntimeException("Tarefa não encontrada com o id: " + idTarefa)
        );
        return mapper.toDTO(tarefa);
    }

    public TarefaDTO atualizarTarefa(Integer idTarefa, TarefaDTO tarefaDTO) {
        Tarefa tarefaEntity = repository.findById(idTarefa).orElseThrow(
                () -> new RuntimeException("Tarefa não encontrada com o id: " + idTarefa)
        );
        tarefaEntity.setTitulo(tarefaDTO.getTitulo() != null ? tarefaDTO.getTitulo() : tarefaEntity.getTitulo());
        tarefaEntity.setDescricao(tarefaDTO.getDescricao() != null ? tarefaDTO.getDescricao() : tarefaEntity.getDescricao());
        tarefaEntity.setStatus(tarefaDTO.getStatus() != null ? StatusTarefa.valueOf(tarefaDTO.getStatus().name()) : tarefaEntity.getStatus());
        Tarefa tarefaAtualizada = repository.saveAndFlush(tarefaEntity);
        return mapper.toDTO(tarefaAtualizada);
    }

    public TarefaDTO alteraStatusTarefa(Integer idTarefa, TarefaDTO tarefaDTO) {
        Tarefa tarefaEntity = repository.findById(idTarefa).orElseThrow(
                () -> new RuntimeException("Tarefa não encontrada com o id: " + idTarefa)
        );
        if(tarefaDTO.getStatus() != null) {
            tarefaEntity.setStatus(StatusTarefa.valueOf(tarefaDTO.getStatus().name()));
            if(tarefaEntity.getStatus() == StatusTarefa.CONCLUIDA) {
                tarefaEntity.setDataConclusao(LocalDateTime.now());
            }
        } else {
            throw new RuntimeException("Status inválido ou não fornecido");
        }
        return mapper.toDTO(tarefaEntity);
    }

    public void deletarTarefa(Integer idUsuario, Integer idTarefa) {
       repository.findById(idTarefa).orElseThrow(
           () -> new RuntimeException("Tarefa não encontrada com o id: " + idTarefa)
       );
       repository.deleteById(idTarefa);
   }

}
