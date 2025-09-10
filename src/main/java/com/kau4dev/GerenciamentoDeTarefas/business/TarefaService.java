package com.kau4dev.GerenciamentoDeTarefas.business;

import com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO.TarefaCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO.TarefaUpdateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO.TarefaViewDTO;
import com.kau4dev.GerenciamentoDeTarefas.exception.tarefaException.IdTarefaNaoEncontradoException;
import com.kau4dev.GerenciamentoDeTarefas.exception.tarefaException.StatusInvalidoException;
import com.kau4dev.GerenciamentoDeTarefas.exception.tarefaException.TarefaNaoPodeSerNuloException;
import com.kau4dev.GerenciamentoDeTarefas.exception.usuarioException.IdUsuarioNaoEncontradoException;
import com.kau4dev.GerenciamentoDeTarefas.exception.usuarioException.UsuarioNaoPodeSerNuloException;
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

    public TarefaViewDTO criarTarefa(Integer idUsuario, TarefaCreateDTO tarefaCreateDTO) throws TarefaNaoPodeSerNuloException {
        Tarefa tarefa = mapper.toEntity(tarefaCreateDTO);
        if (tarefa == null) {
            throw new TarefaNaoPodeSerNuloException("Falha ao converter DTO para entidade Tarefa");
        }
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new IdUsuarioNaoEncontradoException("Usuário não encontrado com o id: " + idUsuario)
        );
        tarefa.setUsuario(usuario);
        tarefa.setDataCriacao(LocalDateTime.now());
        if (tarefa.getStatus() == null) {
            tarefa.setStatus(StatusTarefa.PENDENTE);
        }
        Tarefa tarefaSalva = repository.saveAndFlush(tarefa);
        return mapper.toViewDTO(tarefaSalva);
    }

    public List<TarefaViewDTO> listarTarefas(Integer idUsuario) {
        if (idUsuario == null) {
            throw new UsuarioNaoPodeSerNuloException("ID do usuário não pode ser nulo");
        }
        usuarioRepository.findById(idUsuario).orElseThrow((
                () -> new IdUsuarioNaoEncontradoException("Usuário não encontrado com o id: " + idUsuario)
        ));
        List<Tarefa> tarefas = repository.findByUsuarioId(idUsuario);
        return tarefas.stream()
                .map(mapper::toViewDTO)
                .toList();
    }

    public TarefaViewDTO buscarTarefaPorId(Integer idUsuario, Integer idTarefa) {
        Tarefa tarefa = repository.findByIdAndUsuarioId(idTarefa, idUsuario).orElseThrow(
                () -> new IdTarefaNaoEncontradoException("Tarefa não encontrada com o id: " + idTarefa)
        );
        return mapper.toViewDTO(tarefa);
    }

    public TarefaViewDTO atualizarTarefa(Integer idUsuario, Integer idTarefa, TarefaUpdateDTO tarefaupdateDTO) {
        usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new IdUsuarioNaoEncontradoException("Usuário não encontrado com o id: " + idUsuario)
        );
        Tarefa tarefaEntity = repository.findByIdAndUsuarioId(idTarefa, idUsuario).orElseThrow(
                () -> new IdTarefaNaoEncontradoException("Tarefa não encontrada com o id: " + idTarefa)
        );
        tarefaEntity.setDataConclusao(null);
        mapper.updateEntityFromDTO(tarefaupdateDTO, tarefaEntity);
        Tarefa tarefaAtualizada = repository.saveAndFlush(tarefaEntity);
        return mapper.toViewDTO(tarefaAtualizada);
    }

    public TarefaViewDTO alteraStatusTarefa(Integer idUsuario, Integer idTarefa, TarefaUpdateDTO tarefaUpdateDTO) {
        usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new IdUsuarioNaoEncontradoException("Usuário não encontrado com o id: " + idUsuario)
        );
        Tarefa tarefaEntity = repository.findByIdAndUsuarioId(idTarefa, idUsuario).orElseThrow(
                () -> new IdTarefaNaoEncontradoException("Tarefa não encontrada com o id: " + idTarefa)
        );
        if (tarefaUpdateDTO.getStatus() != null) {
            tarefaEntity.setStatus(StatusTarefa.valueOf(tarefaUpdateDTO.getStatus().name()));
            if (tarefaEntity.getStatus() == StatusTarefa.CONCLUIDA) {
                tarefaEntity.setDataConclusao(LocalDateTime.now());
            } else {
                tarefaEntity.setDataConclusao(null);
            }
        } else {
            throw new StatusInvalidoException("Status inválido ou não fornecido");
        }
        mapper.updateEntityFromDTO(tarefaUpdateDTO, tarefaEntity);
        Tarefa tarefaAtualizada = repository.saveAndFlush(tarefaEntity);
        return mapper.toViewDTO(tarefaAtualizada);
    }

    public void deletarTarefa(Integer idUsuario, Integer idTarefa) {
        usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new IdUsuarioNaoEncontradoException("Usuário não encontrado com o id: " + idUsuario)
        );
        Tarefa tarefa = repository.findByIdAndUsuarioId(idTarefa, idUsuario).orElseThrow(
                () -> new IdTarefaNaoEncontradoException("Tarefa não encontrada com o id: " + idTarefa)
        );
        repository.delete(tarefa);
    }
}
