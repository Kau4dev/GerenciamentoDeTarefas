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


}
