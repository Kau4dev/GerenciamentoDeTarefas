package com.kau4dev.GerenciamentoDeTarefas.business;

import com.kau4dev.GerenciamentoDeTarefas.config.security.TokenService;
import com.kau4dev.GerenciamentoDeTarefas.dto.AuthenticationDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.LoginResponseDTO;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthenticationService {

    private  TokenService tokenService;

    private AuthenticationManager authenticationManager;

    public AuthenticationService(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public LoginResponseDTO login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        var usuarioLogin = new UsernamePasswordAuthenticationToken(
                authenticationDTO.login(),
                authenticationDTO.senha()
        );
        var authentication = authenticationManager.authenticate(usuarioLogin);
        var token = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return new LoginResponseDTO(token);
    }
}
