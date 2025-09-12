package com.kau4dev.GerenciamentoDeTarefas.controller;

import com.kau4dev.GerenciamentoDeTarefas.business.ComentarioService;
import com.kau4dev.GerenciamentoDeTarefas.dto.comentarioDTO.ComentarioCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.comentarioDTO.ComentarioViewDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Operation(summary = "Cria um novo comentário para uma tarefa específica de um usuário específico", description = "Cria um novo comentário associado a uma tarefa existente de um usuário existente.",
            parameters = {
                @Parameter(name = "idUsuario", description = "ID do usuário", example = "1"),
                @Parameter(name = "idTarefa", description = "ID da tarefa", example = "10")
            },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody (
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
    public ResponseEntity<Void> criarComentario(@PathVariable Integer idUsuario, @PathVariable Integer idTarefa, @RequestBody @Valid ComentarioCreateDTO comentarioCreateDTO){
        comentarioService.criarComentario(idTarefa, idUsuario, comentarioCreateDTO);
        return ResponseEntity.status(201).build();
    }

    @Operation(summary = "Lista todos os comentários de uma tarefa específica de um usuário específico", description = "Retorna uma lista de todos os comentários associados a uma tarefa existente de um usuário existente.",
            parameters = {
                @Parameter(name = "idUsuario", description = "ID do usuário", example = "1"),
                @Parameter(name = "idTarefa", description = "ID da tarefa", example = "10")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de comentários retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário ou tarefa não encontrado")
    })
    @GetMapping
    public ResponseEntity<List<ComentarioViewDTO>> listarComentarios(@PathVariable("idUsuario") Integer idUsuario, @PathVariable("idTarefa") Integer idTarefa){
        List<ComentarioViewDTO> comentarios = comentarioService.listarComentarios(idTarefa);
        return ResponseEntity.ok(comentarios);
    }

    @Operation(summary = "Deleta um comentário específico de uma tarefa específica de um usuário específico", description = "Deleta um comentário pelo seu identificador único associado a uma tarefa existente de um usuário existente.",
            parameters = {
                @Parameter(name = "idUsuario", description = "ID do usuário", example = "1"),
                @Parameter(name = "idTarefa", description = "ID da tarefa", example = "10"),
                @Parameter(name = "idComentario", description = "ID do comentário", example = "100")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comentário deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário, tarefa ou comentário não encontrado")
    })
    @DeleteMapping("/{idComentario}")
    public ResponseEntity<Void> deletarComentario(@PathVariable Integer idUsuario, @PathVariable Integer idTarefa, @PathVariable Integer idComentario){
        comentarioService.deletarComentario( idTarefa, idUsuario, idComentario);
        return ResponseEntity.status(204).build();
    }
}
