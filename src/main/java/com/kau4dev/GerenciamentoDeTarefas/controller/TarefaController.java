package com.kau4dev.GerenciamentoDeTarefas.controller;

import com.kau4dev.GerenciamentoDeTarefas.business.TarefaService;
import com.kau4dev.GerenciamentoDeTarefas.dto.TarefaDTO;
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

    @PostMapping
    public ResponseEntity<Void> criarTarefa(@PathVariable("idUsuario") Integer idUsuario, @RequestBody TarefaDTO tarefaDTO){
        tarefaService.criarTarefa(idUsuario,tarefaDTO);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<TarefaDTO>> listarTarefa(@PathVariable Integer idUsuario){
        List<TarefaDTO> tarefas = tarefaService.listarTarefas(idUsuario);
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("/{idTarefa}")
    public ResponseEntity<TarefaDTO> buscarTarefaPorId(@PathVariable Integer idUsuario, @PathVariable Integer idTarefa){
        TarefaDTO tarefa = tarefaService.buscarTarefaPorId(idUsuario, idTarefa);
        return ResponseEntity.ok(tarefa);
    }

    @PutMapping("/{idTarefa}")
    public ResponseEntity<TarefaDTO> atualizarTarefa(@PathVariable Integer idUsuario, @PathVariable Integer idTarefa, @RequestBody TarefaDTO tarefaDTO){
        return ResponseEntity.ok(tarefaService.atualizarTarefa(idUsuario, idTarefa, tarefaDTO));
    }

    @PatchMapping("/{idTarefa}/status")
    public ResponseEntity<TarefaDTO> alteraStatusTarefa(@PathVariable Integer idUsuario, @PathVariable Integer idTarefa, @RequestBody TarefaDTO tarefaDTO){
        return ResponseEntity.ok(tarefaService.alteraStatusTarefa(idUsuario, idTarefa, tarefaDTO));
    }

    @DeleteMapping("/{idTarefa}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Integer idUsuario, @PathVariable Integer idTarefa){
        tarefaService.deletarTarefa(idUsuario, idTarefa);
        return ResponseEntity.status(204).build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleNotFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleBadRequest(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = error.getObjectName();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }


}
