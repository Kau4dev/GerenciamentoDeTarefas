package com.kau4dev.GerenciamentoDeTarefas.controller;

import com.kau4dev.GerenciamentoDeTarefas.business.TarefaService;
import com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO.TarefaCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO.TarefaUpdateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO.TarefaViewDTO;
import com.kau4dev.GerenciamentoDeTarefas.exception.tarefaException.TarefaNaoPodeSerNuloException;
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
@RequestMapping("usuarios/{idUsuario}/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @Operation(summary = "Cria uma nova tarefa para um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping
    public ResponseEntity<Void> criarTarefa(@PathVariable Integer idUsuario, @RequestBody @Valid TarefaCreateDTO tarefaCreateDTO) throws TarefaNaoPodeSerNuloException {
        tarefaService.criarTarefa(idUsuario, tarefaCreateDTO);
        return ResponseEntity.status(201).build();
    }

    @Operation(summary = "Lista todas as tarefas de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping
    public ResponseEntity<List<TarefaViewDTO>> listarTarefa(@PathVariable Integer idUsuario){
        List<TarefaViewDTO> tarefas = tarefaService.listarTarefas(idUsuario);
        return ResponseEntity.ok(tarefas);
    }

    @Operation(summary = "Busca uma tarefa pelo ID para um usuário específico")
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

    @Operation(summary = "Atualiza uma tarefa existente para um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Tarefa ou usuário não encontrado")
    })
    @PutMapping("/{idTarefa}")
    public ResponseEntity<TarefaViewDTO> atualizarTarefa(@PathVariable Integer idUsuario, @PathVariable Integer idTarefa, @RequestBody @Valid TarefaUpdateDTO tarefaupdateDTO){
        return ResponseEntity.ok(tarefaService.atualizarTarefa(idUsuario, idTarefa, tarefaupdateDTO));
    }

    @Operation(summary = "Altera o status de uma tarefa específica para um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status da tarefa alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Tarefa ou usuário não encontrado")
    })
    @PatchMapping("/{idTarefa}/status")
    public ResponseEntity<TarefaViewDTO> alteraStatusTarefa(@PathVariable Integer idUsuario, @PathVariable Integer idTarefa, @RequestBody @Valid TarefaUpdateDTO tarefaUpdateDTO){
        return ResponseEntity.ok(tarefaService.alteraStatusTarefa(idUsuario, idTarefa, tarefaUpdateDTO));
    }

    @Operation(summary = "Deleta uma tarefa pelo ID para um usuário específico")
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
