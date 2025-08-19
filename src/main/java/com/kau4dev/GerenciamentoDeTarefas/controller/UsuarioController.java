package com.kau4dev.GerenciamentoDeTarefas.controller;


import com.kau4dev.GerenciamentoDeTarefas.business.UsuarioService;
import com.kau4dev.GerenciamentoDeTarefas.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

     //nao pega o corpo da requisição, apenas o idUsuario
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Integer idUsuario, @RequestBody UsuarioDTO usuarioDTO){
        UsuarioDTO atualizado = usuarioService.atualizarUsuario(idUsuario, usuarioDTO);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer idUsuario){
        usuarioService.deletarUsuario(idUsuario);
        return ResponseEntity.ok().build();
    }
    



}
