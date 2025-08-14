package com.kau4dev.GerenciamentoDeTarefas.controller;

import com.kau4dev.GerenciamentoDeTarefas.business.ComentarioService;
import com.kau4dev.GerenciamentoDeTarefas.dto.ComentarioDTO;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entitys.Comentario;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.TarefaRepository;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuarios/tarefas/{id}/comentarios")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioService;
    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Void> criarComentario(@PathVariable("id") Integer tarefaId, @RequestBody ComentarioDTO comentarioDTO){
        var tarefa = tarefaRepository.findById(comentarioDTO.getTarefa())
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        var usuario = usuarioRepository.findById(comentarioDTO.getUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        var comentario = new Comentario(
                comentarioDTO.getTexto(),
                tarefa,
                usuario
        );
        comentarioService.criarComentario(comentario);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Comentario>> listarComentarios(@PathVariable("id") Integer id){
        List<Comentario> comentarios = comentarioService.listarComentarios();
        return ResponseEntity.ok(comentarios);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarComentario(@PathVariable("id") Integer id){
        comentarioService.deletarComentario(id);
        return ResponseEntity.ok().build();
    }

}
