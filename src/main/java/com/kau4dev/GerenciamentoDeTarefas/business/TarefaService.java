package com.kau4dev.GerenciamentoDeTarefas.business;

import com.kau4dev.GerenciamentoDeTarefas.dto.TarefaDTO;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Tarefa;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Usuario;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.TarefaRepository;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.UsuarioRepository;
import com.kau4dev.GerenciamentoDeTarefas.mapper.TarefaMapper;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.enums.StatusTarefa;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository repository;
    private final TarefaMapper mapper;
    private final UsuarioRepository usuarioRepository;

    public TarefaService(TarefaRepository repository, TarefaMapper mapper, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.usuarioRepository = usuarioRepository;
    }

    public TarefaDTO criarTarefa(Integer idUsuario, TarefaDTO tarefaDTO) {
        Tarefa tarefa = mapper.toEntity(tarefaDTO);
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado com o id: " + idUsuario)
        );
        tarefa.setUsuario(usuario);
        tarefa.setDataCriacao(LocalDateTime.now());
        Tarefa tarefaSalva = repository.saveAndFlush(tarefa);
        return mapper.toDTO(tarefaSalva);
    }

    public List<TarefaDTO> listarTarefas(Integer idusuario) {
        List<Tarefa> tarefas = repository.findByUsuarioId(idusuario);
        return tarefas.stream()
                .map(mapper::toDTO)
                .toList();
    }

    public TarefaDTO buscarTarefaPorId(Integer idUsuario, Integer idTarefa) {
        Tarefa tarefa = repository.findByIdAndUsuarioId(idTarefa, idUsuario).orElseThrow(
                () -> new RuntimeException("Tarefa não encontrada com o id: " + idTarefa)
        );
        return mapper.toDTO(tarefa);
    }

    public TarefaDTO atualizarTarefa(Integer idUsuario, Integer idTarefa, TarefaDTO tarefaDTO) {
        Tarefa tarefaEntity = repository.findByIdAndUsuarioId(idTarefa, idUsuario).orElseThrow(
                () -> new RuntimeException("Tarefa não encontrada com o id: " + idTarefa)
        );
        tarefaEntity.setTitulo(tarefaDTO.getTitulo() != null ? tarefaDTO.getTitulo() : tarefaEntity.getTitulo());
        tarefaEntity.setDescricao(tarefaDTO.getDescricao() != null ? tarefaDTO.getDescricao() : tarefaEntity.getDescricao());
        tarefaEntity.setStatus(tarefaDTO.getStatus() != null ? StatusTarefa.valueOf(tarefaDTO.getStatus().name()) : tarefaEntity.getStatus());
        Tarefa tarefaAtualizada = repository.saveAndFlush(tarefaEntity);
        return mapper.toDTO(tarefaAtualizada);
    }

    //ADICIONAR PRA COMPLETAR OS DADOS
    public TarefaDTO alteraStatusTarefa(Integer idUsuario, Integer idTarefa, TarefaDTO tarefaDTO) {
        Tarefa tarefaEntity = repository.findByIdAndUsuarioId(idTarefa, idUsuario).orElseThrow(
                () -> new RuntimeException("Tarefa não encontrada com o id: " + idTarefa)
        );
        if (tarefaDTO.getStatus() != null) {
            tarefaEntity.setStatus(StatusTarefa.valueOf(tarefaDTO.getStatus().name()));
            if (tarefaEntity.getStatus() == StatusTarefa.CONCLUIDA) {
                tarefaEntity.setDataConclusao(LocalDateTime.now());
            }
        } else {
            throw new RuntimeException("Status inválido ou não fornecido");
        }
        Tarefa tarefaAtualizada = repository.saveAndFlush(tarefaEntity);
        return mapper.toDTO(tarefaAtualizada);
    }

    public void deletarTarefa(Integer idUsuario, Integer idTarefa) {
        Tarefa tarefa = repository.findByIdAndUsuarioId(idTarefa, idUsuario).orElseThrow(
                () -> new RuntimeException("Tarefa não encontrada com o id: " + idTarefa)
        );
        repository.delete(tarefa);
    }
}
