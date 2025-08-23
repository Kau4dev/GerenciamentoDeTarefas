package com.kau4dev.GerenciamentoDeTarefas.business;

import com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO.TarefaCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO.TarefaUpdateDTO;
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

    public TarefaCreateDTO criarTarefa(Integer idUsuario, TarefaCreateDTO tarefaCreateDTO) {
        Tarefa tarefa = mapper.toEntity(tarefaCreateDTO);
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado com o id: " + idUsuario)
        );
        tarefa.setUsuario(usuario);
        tarefa.setDataCriacao(LocalDateTime.now());
        Tarefa tarefaSalva = repository.saveAndFlush(tarefa);
        return mapper.toDTO(tarefaSalva);
    }

    public List<TarefaCreateDTO> listarTarefas(Integer idusuario) {
        List<Tarefa> tarefas = repository.findByUsuarioId(idusuario);
        return tarefas.stream()
                .map(mapper::toDTO)
                .toList();
    }

    public TarefaCreateDTO buscarTarefaPorId(Integer idUsuario, Integer idTarefa) {
        Tarefa tarefa = repository.findByIdAndUsuarioId(idTarefa, idUsuario).orElseThrow(
                () -> new RuntimeException("Tarefa não encontrada com o id: " + idTarefa)
        );
        return mapper.toDTO(tarefa);
    }

    public TarefaUpdateDTO atualizarTarefa(Integer idUsuario, Integer idTarefa, TarefaUpdateDTO tarefaupdateDTO) {
        Tarefa tarefaEntity = repository.findByIdAndUsuarioId(idTarefa, idUsuario).orElseThrow(
                () -> new RuntimeException("Tarefa não encontrada com o id: " + idTarefa)
        );
        tarefaEntity.setTitulo(tarefaupdateDTO.getTitulo() != null ? tarefaupdateDTO.getTitulo() : tarefaEntity.getTitulo());
        tarefaEntity.setDescricao(tarefaupdateDTO.getDescricao() != null ? tarefaupdateDTO.getDescricao() : tarefaEntity.getDescricao());
        Tarefa tarefaAtualizada = repository.saveAndFlush(tarefaEntity);
        return mapper.toUpdateDTO(tarefaAtualizada);
    }

    public TarefaUpdateDTO alteraStatusTarefa(Integer idUsuario, Integer idTarefa, TarefaUpdateDTO tarefaUpdateDTO) {
        Tarefa tarefaEntity = repository.findByIdAndUsuarioId(idTarefa, idUsuario).orElseThrow(
                () -> new RuntimeException("Tarefa não encontrada com o id: " + idTarefa)
        );
        if (tarefaUpdateDTO.getStatus() != null) {
            tarefaEntity.setStatus(StatusTarefa.valueOf(tarefaUpdateDTO.getStatus().name()));
            if (tarefaEntity.getStatus() == StatusTarefa.CONCLUIDA) {
                tarefaEntity.setDataConclusao(LocalDateTime.now());
            } else {
                tarefaEntity.setDataConclusao(null);
            }
        } else {
            throw new RuntimeException("Status inválido ou não fornecido");
        }
        Tarefa tarefaAtualizada = repository.saveAndFlush(tarefaEntity);
        return mapper.toUpdateDTO(tarefaAtualizada);
    }

    public void deletarTarefa(Integer idUsuario, Integer idTarefa) {
        Tarefa tarefa = repository.findByIdAndUsuarioId(idTarefa, idUsuario).orElseThrow(
                () -> new RuntimeException("Tarefa não encontrada com o id: " + idTarefa)
        );
        repository.delete(tarefa);
    }
}
