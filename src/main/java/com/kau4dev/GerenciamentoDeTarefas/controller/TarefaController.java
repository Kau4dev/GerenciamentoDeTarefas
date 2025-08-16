package com.kau4dev.GerenciamentoDeTarefas.controller;

import com.kau4dev.GerenciamentoDeTarefas.business.TarefaService;
import com.kau4dev.GerenciamentoDeTarefas.dto.TarefaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuarios/{idUsuario}/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<Void> criarTarefa(@PathVariable Integer idUsuario, @RequestBody TarefaDTO tarefaDTO){
        tarefaService.criarTarefa(idUsuario,tarefaDTO);
        return ResponseEntity.ok().build();

    }

    @GetMapping
    public ResponseEntity<List<TarefaDTO>> listarTarefa(@PathVariable Integer idUsuario){
        List<TarefaDTO> tarefas = tarefaService.listarTarefas();
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("/{idTarefa}")
    public ResponseEntity<TarefaDTO> buscarTarefaPorId(@PathVariable Integer idUsuario, @PathVariable Integer idTarefa){
        TarefaDTO tarefa = tarefaService.buscarTarefaPorId(idTarefa);
        return ResponseEntity.ok(tarefa);
    }

    @PutMapping("/{idTarefa}")
    public ResponseEntity<TarefaDTO> atualizarTarefa(@PathVariable Integer idUsuario, @PathVariable Integer idTarefa, @RequestBody TarefaDTO tarefaDTO){
        return ResponseEntity.ok(tarefaService.atualizarTarefa(idTarefa, tarefaDTO));
    }

    @PatchMapping("/{idTarefa}/status")
    public ResponseEntity<TarefaDTO> alteraStatusTarefa(@PathVariable Integer idUsuario, @PathVariable Integer idTarefa, @RequestBody TarefaDTO tarefaDTO){
        return ResponseEntity.ok(tarefaService.alteraStatusTarefa(idTarefa, tarefaDTO));
    }

    @DeleteMapping("/{idTarefa}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Integer idUsuario, @PathVariable Integer idTarefa){
        tarefaService.deletarTarefa(idUsuario, idTarefa);
        return ResponseEntity.noContent().build();
    }

    
}
