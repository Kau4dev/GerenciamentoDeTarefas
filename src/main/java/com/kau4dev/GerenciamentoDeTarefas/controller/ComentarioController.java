package com.kau4dev.GerenciamentoDeTarefas.controller;

import com.kau4dev.GerenciamentoDeTarefas.business.ComentarioService;
import com.kau4dev.GerenciamentoDeTarefas.dto.ComentarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuario/{idUsuario}/tarefas/{idTarefa}/comentarios")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioService;

    @PostMapping
    public ResponseEntity<Void> criarComentario(@PathVariable("idTarefa") Integer idTarefa, @PathVariable("idUsuario") Integer idUsuario, @RequestBody ComentarioDTO comentarioDTO){
        comentarioService.criarComentario(idTarefa, idUsuario, comentarioDTO);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<ComentarioDTO>> listarComentarios(@PathVariable("idUsuario") Integer idUsuario, @PathVariable("idTarefa") Integer idTarefa){
        List<ComentarioDTO> comentarios = comentarioService.listarComentarios(idTarefa);
        return ResponseEntity.ok(comentarios);

    }

    @DeleteMapping("/{idComentario}")
    public ResponseEntity<Void> deletarComentario(@PathVariable Integer idTarefa, @PathVariable Integer idUsuario, @PathVariable Integer idComentario){
        comentarioService.deletarComentario( idTarefa, idUsuario, idComentario);
        return ResponseEntity.status(204).build();
    }

}
