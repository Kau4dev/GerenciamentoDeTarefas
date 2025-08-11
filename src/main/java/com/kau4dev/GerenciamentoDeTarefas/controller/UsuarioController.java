package com.kau4dev.GerenciamentoDeTarefas.controller;


import com.kau4dev.GerenciamentoDeTarefas.business.UsuarioService;
import com.kau4dev.GerenciamentoDeTarefas.dto.UsuarioDTO;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entitys.Usuario;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    @PostMapping("/criarUsuario")
    public ResponseEntity<Void> criarUsuario(@RequestBody UsuarioDTO usuarioDTO){
        var usuario = new Usuario(
                usuarioDTO.getNome(),
                usuarioDTO.getEmail(),
                usuarioDTO.getSenha()
        );
        usuarioService.salvarUsuario(usuario);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listarUsuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/buscarUsuarioPorId/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable Integer id){
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/atualizarUsuario/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioDTO usuarioDTO){
        var usuario = new Usuario(
                usuarioDTO.getNome(),
                usuarioDTO.getEmail(),
                usuarioDTO.getSenha()
        );
        usuarioService.atualizarUsuario(id, usuario);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deletarUsuario/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer id){
        usuarioService.deletarUsuario(id);
        return ResponseEntity.ok().build();
    }
    



}
