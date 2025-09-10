package com.kau4dev.GerenciamentoDeTarefas.business;

import com.kau4dev.GerenciamentoDeTarefas.config.security.TokenService;
import com.kau4dev.GerenciamentoDeTarefas.dto.AuthenticationDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.LoginResponseDTO;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenService tokenService;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService(authenticationManager, tokenService);
    }

    @Test
    @DisplayName("Deve autenticar e retornar token com sucesso")
    void deveAutenticarERetornarTokenComSucesso() {
        AuthenticationDTO dto = new AuthenticationDTO("email@email.com", "senha123");
        Usuario usuario = Usuario.builder().id(1).email("email@email.com").senha("senha123").build();
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(usuario);
        when(tokenService.gerarToken(usuario)).thenReturn("token-jwt");

        LoginResponseDTO response = authenticationService.login(dto);
        assertNotNull(response);
        assertEquals("token-jwt", response.token());
    }

    @Test
    @DisplayName("Deve lançar exceção se autenticação falhar")
    void deveLancarExcecaoSeAutenticacaoFalhar() {
        AuthenticationDTO dto = new AuthenticationDTO("email@email.com", "senha123");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException("Falha na autenticação"));
        assertThrows(RuntimeException.class, () -> authenticationService.login(dto));
    }
}
