package com.kau4dev.GerenciamentoDeTarefas.controller;


import com.kau4dev.GerenciamentoDeTarefas.business.AuthenticationService;
import com.kau4dev.GerenciamentoDeTarefas.dto.AuthenticationDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticação", description = "Endpoints de autenticação de usuários")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(
        summary = "Autentica um usuário e retorna um token JWT",
        description = "Realiza login e retorna um token JWT para autenticação nas próximas requisições.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = AuthenticationDTO.class),
                examples = @ExampleObject(value = "{\"login\": \"email@exemplo.com\", \"senha\": \"SuaSenha123\"}")
            )
        )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida", content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\"}")
            )),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"erro\":\"Dados inválidos\"}")
            )),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{\"erro\":\"Credenciais inválidas\"}")
            ))
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        LoginResponseDTO response = authenticationService.login(authenticationDTO);
        return ResponseEntity.ok(response);
    }
}
