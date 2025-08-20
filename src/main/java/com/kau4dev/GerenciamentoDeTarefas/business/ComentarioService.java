package com.kau4dev.GerenciamentoDeTarefas.business;

import com.kau4dev.GerenciamentoDeTarefas.dto.ComentarioDTO;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Comentario;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Tarefa;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Usuario;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.ComentarioRepository;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.TarefaRepository;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.UsuarioRepository;
import com.kau4dev.GerenciamentoDeTarefas.mapper.ComentarioMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComentarioService {

    private ComentarioRepository repository;
    private ComentarioMapper comentarioMapper;
    private TarefaRepository tarefaRepository;
    private UsuarioRepository usuarioRepository;

    public ComentarioService(ComentarioRepository repository, ComentarioMapper comentarioMapper, TarefaRepository tarefaRepository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.comentarioMapper  = comentarioMapper;
        this.tarefaRepository = tarefaRepository;
        this.usuarioRepository = usuarioRepository;
}
    public ComentarioDTO criarComentario(Integer idTarefa, Integer idUsuario, ComentarioDTO comentarioDTO) {
        Comentario comentario = comentarioMapper.toEntity(comentarioDTO);
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado com o id: " + idUsuario)
        );
        comentario.setUsuario(usuario);
        Tarefa tarefa = tarefaRepository.findById(idTarefa).orElseThrow(
                () -> new RuntimeException("Tarefa não encontrada com o id: " + idTarefa)
        );
        comentario.setTarefa(tarefa);
        comentario.setDataCriacao(LocalDateTime.now());
        Comentario comentarioSalvo = repository.saveAndFlush(comentario);
        return comentarioMapper.toDTO(comentarioSalvo);

    }

    public List<ComentarioDTO> listarComentarios(Integer idTarefa) {
        List<Comentario> comentarios = repository.findByTarefaId(idTarefa);
        return comentarios.stream()
                .map(comentarioMapper::toDTO)
                .toList();
    }

    public void deletarComentario(Integer idTarefa, Integer idUsuario, Integer idComentario) {
        tarefaRepository.findById(idTarefa).orElseThrow(
                () -> new RuntimeException("Tarefa não encontrada com o id: " + idTarefa)
        );
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado com o id: " + idUsuario)
        );
        Comentario comentario = repository.findById(idComentario).orElseThrow(
                () -> new RuntimeException("Comentário não encontrado com o id: " + idComentario)
        );
        if (!comentario.getUsuario().getId().equals(idUsuario)) {
            throw new RuntimeException("Comentário não pertence ao usuário com id: " + idUsuario);
        }

        repository.delete(comentario);
        //pq mais seguro?
    }


}
