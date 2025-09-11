package com.kau4dev.GerenciamentoDeTarefas.controller;

import com.kau4dev.GerenciamentoDeTarefas.business.ComentarioService;
import com.kau4dev.GerenciamentoDeTarefas.dto.comentarioDTO.ComentarioCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.comentarioDTO.ComentarioViewDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Comentários", description = "Operações relacionadas a comentários em tarefas")
@RestController
@RequestMapping("usuarios/{idUsuario}/tarefas/{idTarefa}/comentarios")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioService;

    @Operation(summary = "Cria um novo comentário para uma tarefa específica de um usuário específico",
        requestBody = @RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = ComentarioCreateDTO.class),
                examples = @ExampleObject(value = "{\"texto\":\"Ótima tarefa!\"}")
            )
        )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comentário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário ou tarefa não encontrado")
    })
    @PostMapping
    public ResponseEntity<Void> criarComentario(
            @Parameter(description = "ID do usuário", example = "1")
            @PathVariable Integer idUsuario,
            @Parameter(description = "ID da tarefa", example = "10")
            @PathVariable Integer idTarefa,
            @RequestBody @Valid ComentarioCreateDTO comentarioCreateDTO){
        comentarioService.criarComentario(idTarefa, idUsuario, comentarioCreateDTO);
        return ResponseEntity.status(201).build();
    }

    @Operation(summary = "Lista todos os comentários de uma tarefa específica de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de comentários retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário ou tarefa não encontrado")
    })
    @GetMapping
    public ResponseEntity<List<ComentarioViewDTO>> listarComentarios(
            @Parameter(description = "ID do usuário", example = "1")
            @PathVariable("idUsuario") Integer idUsuario,
            @Parameter(description = "ID da tarefa", example = "10")
            @PathVariable("idTarefa") Integer idTarefa){
        List<ComentarioViewDTO> comentarios = comentarioService.listarComentarios(idTarefa);
        return ResponseEntity.ok(comentarios);
    }

    @Operation(summary = "Deleta um comentário específico de uma tarefa específica de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comentário deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário, tarefa ou comentário não encontrado")
    })
    @DeleteMapping("/{idComentario}")
    public ResponseEntity<Void> deletarComentario(
            @Parameter(description = "ID do usuário", example = "1")
            @PathVariable Integer idUsuario,
            @Parameter(description = "ID da tarefa", example = "10")
            @PathVariable Integer idTarefa,
            @Parameter(description = "ID do comentário", example = "100")
            @PathVariable Integer idComentario){
        comentarioService.deletarComentario( idTarefa, idUsuario, idComentario);
        return ResponseEntity.status(204).build();
    }
}
