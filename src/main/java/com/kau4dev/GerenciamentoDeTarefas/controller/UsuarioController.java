package com.kau4dev.GerenciamentoDeTarefas.controller;

import com.kau4dev.GerenciamentoDeTarefas.business.UsuarioService;
import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioUpdateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioViewDTO;
import com.kau4dev.GerenciamentoDeTarefas.exception.usuarioException.UsuarioJaExisteException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Usuários", description = "Operações relacionadas a usuários")
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Cria um novo usuário", description = "Cria um novo usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"erro\":\"Email já cadastrado\"}")
            )),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping
    public ResponseEntity<Void> criarUsuario(
            @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UsuarioCreateDTO.class),
                            examples = @ExampleObject(value = "{\"nome\":\"João\",\"email\":\"joao@email.com\",\"senha\":\"Senha123\"}")
                    )
            )
            @Valid UsuarioCreateDTO usuarioCreateDTO) throws UsuarioJaExisteException {
        usuarioService.salvarUsuario(usuarioCreateDTO);
        return ResponseEntity.status(201).build();
    }

    @Operation(summary = "Lista todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioViewDTO>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @Operation(summary = "Busca um usuário pelo ID", description = "Busca um usuário pelo seu identificador único.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioViewDTO> buscarUsuarioPorId(
            @Parameter(description = "ID do usuário", example = "1")
            @PathVariable Integer idUsuario) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(idUsuario));
    }

    @Operation(summary = "Atualiza um usuário existente", description = "Atualiza os dados de um usuário já cadastrado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"erro\":\"Dados inválidos\"}")
            )),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioViewDTO> atualizarUsuario(
            @Parameter(description = "ID do usuário", example = "1")
            @PathVariable Integer idUsuario,
            @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UsuarioUpdateDTO.class),
                            examples = @ExampleObject(value = "{\"nome\":\"João Atualizado\",\"senha\":\"NovaSenha123\"}")
                    )
            )
            @Valid UsuarioUpdateDTO usuarioUpdateDTO) {
        UsuarioViewDTO atualizado = usuarioService.atualizarUsuario(idUsuario, usuarioUpdateDTO);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(summary = "Deleta um usuário pelo ID", description = "Remove um usuário do sistema pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> deletarUsuario(
            @Parameter(description = "ID do usuário", example = "1")
            @PathVariable Integer idUsuario) {
        usuarioService.deletarUsuario(idUsuario);
        return ResponseEntity.status(204).build();
    }

}