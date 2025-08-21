package com.kau4dev.GerenciamentoDeTarefas.controller;

import com.kau4dev.GerenciamentoDeTarefas.business.ComentarioService;
import com.kau4dev.GerenciamentoDeTarefas.dto.ComentarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("usuario/{idUsuario}/tarefas/{idTarefa}/comentarios")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioService;

    @Operation(summary = "Cria um novo comentário para uma tarefa específica de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comentário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário ou tarefa não encontrado")
    })
    @PostMapping
    public ResponseEntity<Void> criarComentario(@PathVariable Integer idTarefa, @PathVariable Integer idUsuario, @RequestBody @Valid ComentarioDTO comentarioDTO){
        comentarioService.criarComentario(idTarefa, idUsuario, comentarioDTO);
        return ResponseEntity.status(201).build();
    }

    @Operation(summary = "Lista todos os comentários de uma tarefa específica de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de comentários retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário ou tarefa não encontrado")
    })
    @GetMapping
    public ResponseEntity<List<ComentarioDTO>> listarComentarios(@PathVariable("idUsuario") Integer idUsuario, @PathVariable("idTarefa") Integer idTarefa){
        List<ComentarioDTO> comentarios = comentarioService.listarComentarios(idTarefa);
        return ResponseEntity.ok(comentarios);

    }

    @Operation(summary = "Deleta um comentário específico de uma tarefa específica de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comentário deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário, tarefa ou comentário não encontrado")
    })
    @DeleteMapping("/{idComentario}")
    public ResponseEntity<Void> deletarComentario(@PathVariable Integer idTarefa, @PathVariable Integer idUsuario, @PathVariable Integer idComentario){
        comentarioService.deletarComentario( idTarefa, idUsuario, idComentario);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Manipulador de exceções para recursos não encontrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleNotFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @Operation(summary = "Manipulador de exceções para requisições inválidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleBadRequest(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }


}
