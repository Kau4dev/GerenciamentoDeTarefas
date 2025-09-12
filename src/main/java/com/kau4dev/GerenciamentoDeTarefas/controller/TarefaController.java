package com.kau4dev.GerenciamentoDeTarefas.controller;

import com.kau4dev.GerenciamentoDeTarefas.business.TarefaService;
import com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO.TarefaCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO.TarefaUpdateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO.TarefaViewDTO;
import com.kau4dev.GerenciamentoDeTarefas.exception.tarefaException.TarefaNaoPodeSerNuloException;
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

@Tag(name = "Tarefas", description = "Operações relacionadas a tarefas de usuários")
@RestController
@RequestMapping("usuarios/{idUsuario}/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @Operation(summary = "Cria uma nova tarefa para um usuário específico", description = "Cria uma nova tarefa associada a um usuário existente.",
            parameters = @Parameter(name = "idUsuario", description = "ID do usuário", example = "1"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                            required = true,
                            content = @Content(
                                    schema = @Schema(implementation = TarefaCreateDTO.class),
                                    examples = @ExampleObject(value = "{\"titulo\":\"Estudar Swagger\",\"descricao\":\"Melhorar documentação\"}")
                            )
                    )
            )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping
    public ResponseEntity<TarefaViewDTO> criarTarefa(@PathVariable Integer idUsuario, @RequestBody @Valid TarefaCreateDTO tarefaCreateDTO) throws TarefaNaoPodeSerNuloException {
        TarefaViewDTO tarefaCriada = tarefaService.criarTarefa(idUsuario, tarefaCreateDTO);
        return ResponseEntity.status(201).body(tarefaCriada);
    }

    @Operation(summary = "Lista todas as tarefas de um usuário específico", description = "Retorna uma lista de todas as tarefas associadas a um usuário existente.",
            parameters = @Parameter(name = "idUsuario", description = "ID do usuário", example = "1"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping
    public ResponseEntity<List<TarefaViewDTO>> listarTarefa(@PathVariable Integer idUsuario){
        List<TarefaViewDTO> tarefas = tarefaService.listarTarefas(idUsuario);
        return ResponseEntity.ok(tarefas);
    }

    @Operation(summary = "Busca uma tarefa pelo ID para um usuário específico", description = "Busca uma tarefa pelo seu identificador único associada a um usuário existente.",
            parameters = {
                    @Parameter(name = "idUsuario", description = "ID do usuário", example = "1"),
                    @Parameter(name = "idTarefa", description = "ID da tarefa", example = "10")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Tarefa ou usuário não encontrado")
    })
    @GetMapping("/{idTarefa}")
    public ResponseEntity<TarefaViewDTO> buscarTarefaPorId(@PathVariable Integer idUsuario, @PathVariable Integer idTarefa){
        TarefaViewDTO tarefa = tarefaService.buscarTarefaPorId(idUsuario, idTarefa);
        return ResponseEntity.ok(tarefa);
    }

    @Operation(summary = "Atualiza uma tarefa existente para um usuário específico", description = "Atualiza os dados de uma tarefa já cadastrada associada a um usuário existente.",
            parameters = {
                    @Parameter(name = "idUsuario", description = "ID do usuário", example = "1"),
                    @Parameter(name = "idTarefa", description = "ID da tarefa", example = "10")},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = TarefaUpdateDTO.class),
                            examples = @ExampleObject(value = "{\"titulo\":\"Novo título\",\"descricao\":\"Nova descrição\"}")
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Tarefa ou usuário não encontrado")
    })
    @PutMapping("/{idTarefa}")
    public ResponseEntity<TarefaViewDTO> atualizarTarefa(@PathVariable Integer idUsuario, @PathVariable Integer idTarefa, @RequestBody @Valid TarefaUpdateDTO tarefaupdateDTO){
        return ResponseEntity.ok(tarefaService.atualizarTarefa(idUsuario, idTarefa, tarefaupdateDTO));
    }

    @Operation(summary = "Altera o status de uma tarefa específica para um usuário específico", description = "Atualiza o status de uma tarefa associada a um usuário existente.",
            parameters = {
                    @Parameter(name = "idUsuario", description = "ID do usuário", example = "1"),
                    @Parameter(name = "idTarefa", description = "ID da tarefa", example = "10")},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = TarefaUpdateDTO.class),
                            examples = @ExampleObject(value = "{\"status\":\"CONCLUIDA\"}")
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status da tarefa alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Tarefa ou usuário não encontrado")
    })
    @PatchMapping("/{idTarefa}/status")
    public ResponseEntity<TarefaViewDTO> alteraStatusTarefa(@PathVariable Integer idUsuario, @PathVariable Integer idTarefa, @RequestBody @Valid TarefaUpdateDTO tarefaUpdateDTO){
        return ResponseEntity.ok(tarefaService.alteraStatusTarefa(idUsuario, idTarefa, tarefaUpdateDTO));
    }

    @Operation(summary = "Deleta uma tarefa pelo ID para um usuário específico", description = "Remove uma tarefa do sistema pelo seu ID associada a um usuário existente.",
            parameters = {
                    @Parameter(name = "idUsuario", description = "ID do usuário", example = "1"),
                    @Parameter(name = "idTarefa", description = "ID da tarefa", example = "10")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Tarefa ou usuário não encontrado")
    })
    @DeleteMapping("/{idTarefa}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Integer idUsuario, @PathVariable Integer idTarefa){
        tarefaService.deletarTarefa(idUsuario, idTarefa);
        return ResponseEntity.status(204).build();
    }
}
