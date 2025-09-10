package com.kau4dev.GerenciamentoDeTarefas.business;

import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Usuario;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private AuthorizationService authorizationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authorizationService = new AuthorizationService(usuarioRepository);
    }

    @Test
    @DisplayName("Deve retornar UserDetails quando o usuário existe")
    void deveRetornarUserDetailsQuandoUsuarioExiste() {
        Usuario usuario = Usuario.builder()
                .id(1)
                .nome("Kauã")
                .email("kaua@gmail.com")
                .senha("senha123")
                .build();
        Mockito.when(usuarioRepository.findByEmail("kaua@gmail.com")).thenReturn(usuario);

        UserDetails userDetails = authorizationService.loadUserByUsername("kaua@gmail.com");
        assertNotNull(userDetails);
        assertEquals("kaua@gmail.com", userDetails.getUsername());
        assertEquals("senha123", userDetails.getPassword());
    }

    @Test
    @DisplayName("Deve lançar UsernameNotFoundException quando o usuário não existe")
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        Mockito.when(usuarioRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> authorizationService.loadUserByUsername("naoexiste@gmail.com"));
    }
}