package com.kau4dev.GerenciamentoDeTarefas.controller;

import com.kau4dev.GerenciamentoDeTarefas.business.TarefaService;
import com.kau4dev.GerenciamentoDeTarefas.dto.TarefaDTO;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Tarefa;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuarios/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<Void> criarTarefa(@RequestBody TarefaDTO tarefaDTO){
        var tarefa = new Tarefa(
                tarefaDTO.getTitulo(),
                tarefaDTO.getDescricao(),
                tarefaDTO.getStatus(),
                tarefaDTO.getUsuario()
        );
        tarefaService.criarTarefa(tarefa);
        return ResponseEntity.ok().build();

    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTarefa(){
        List<Tarefa> tarefas = tarefaService.listarTarefas();
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarTarefaPorId(@PathVariable Integer id){
        Tarefa tarefa = tarefaService.buscarTarefaPorId(id);
        return ResponseEntity.ok(tarefa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Integer id, @RequestBody TarefaDTO tarefaDTO){
        var tarefa = new Tarefa(
                tarefaDTO.getTitulo(),
                tarefaDTO.getDescricao(),
                tarefaDTO.getStatus(),
                tarefaDTO.getUsuario()
        );
        tarefaService.atualizarTarefa(id, tarefa);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Tarefa> alteraStatusTarefa(@PathVariable Integer id, @RequestBody TarefaDTO tarefaDTO){
        var tarefa = new Tarefa(
                null,
                null,
                tarefaDTO.getStatus(),
                null
        );
        tarefaService.alteraStatusTarefa(id, tarefa);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Tarefa> deletarTarefa(@PathVariable Integer id){
        tarefaService.deletarTarefa(id);
        return ResponseEntity.ok().build();
    }

    
}
