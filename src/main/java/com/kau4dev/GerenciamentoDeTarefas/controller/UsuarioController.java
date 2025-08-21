package com.kau4dev.GerenciamentoDeTarefas.controller;


import com.kau4dev.GerenciamentoDeTarefas.business.UsuarioService;
import com.kau4dev.GerenciamentoDeTarefas.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Void> criarUsuario(@RequestBody UsuarioDTO usuarioDTO){
        usuarioService.salvarUsuario(usuarioDTO);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@PathVariable Integer idUsuario){
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(idUsuario));
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Integer idUsuario, @RequestBody UsuarioDTO usuarioDTO){
        UsuarioDTO atualizado = usuarioService.atualizarUsuario(idUsuario, usuarioDTO);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer idUsuario){
        usuarioService.deletarUsuario(idUsuario);
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
