package com.kau4dev.GerenciamentoDeTarefas.business;

import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioUpdateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioViewDTO;
import com.kau4dev.GerenciamentoDeTarefas.exception.UsuarioException.UsuarioJaExisteException;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Usuario;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.UsuarioRepository;
import com.kau4dev.GerenciamentoDeTarefas.mapper.UsuarioMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioService usuarioService;

    @Nested
    class SalvarUsuario {

        @Test
        @DisplayName("Deve salvar um usuário com sucesso")
        void DeveSalvarUmUsuarioComSucesso() throws UsuarioJaExisteException {
            // Arrange
            var usuarioCreateDTO = new UsuarioCreateDTO();
            usuarioCreateDTO.setNome("Kauã");
            usuarioCreateDTO.setEmail("kaua@gmail.com");
            usuarioCreateDTO.setSenha("S123456");

            var usuarioEntity = Usuario.builder()
                    .id(1)
                    .nome("Kauã")
                    .email("kaua@gmail.com")
                    .senha("S123456")
                    .build();

            var usuarioViewDTO = new UsuarioViewDTO();
            usuarioViewDTO.setIdUsuario(1);
            usuarioViewDTO.setNome("Kauã");
            usuarioViewDTO.setEmail("kaua@gmail.com");

            doReturn(usuarioEntity).when(usuarioMapper).toEntity(any(UsuarioCreateDTO.class));
            doReturn(usuarioEntity).when(usuarioRepository).saveAndFlush(any(Usuario.class));
            doReturn(usuarioViewDTO).when(usuarioMapper).toViewDTO(any(Usuario.class));

            // Act
            var output = usuarioService.salvarUsuario(usuarioCreateDTO);

            // Assert
            assertNotNull(output);
            assertEquals("Kauã", output.getNome());
            assertEquals("kaua@gmail.com", output.getEmail());
        }
        @Test
        @DisplayName("Deve lançar exceção ao tentar salvar usuário com email já existente")
        void DeveLancarExcecaoAoTentarSalvarUsuarioComEmailJaExistente() {
            // Arrange
            var usuarioCreateDTO = new UsuarioCreateDTO();
            usuarioCreateDTO.setNome("Kauã");
            usuarioCreateDTO.setEmail("kaua@gmail.com");
            usuarioCreateDTO.setSenha("S123456");

            var usuarioEntity = Usuario.builder()
                    .id(1)
                    .nome("Kauã")
                    .email("kaua@gmail.com")
                    .senha("S123456")
                    .build();
            doReturn(usuarioEntity).when(usuarioMapper).toEntity(any(UsuarioCreateDTO.class));
            doReturn(true).when(usuarioRepository).existsByEmail(usuarioCreateDTO.getEmail());

            // Act & Assert
            UsuarioJaExisteException exception = assertThrows(UsuarioJaExisteException.class, () -> usuarioService.salvarUsuario(usuarioCreateDTO));
            assertEquals("Já existe um usuário cadastrado com o email: " + usuarioCreateDTO.getEmail(), exception.getMessage());
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar salvar usuário com email nulo")
        void DeveLancarExcecaoAoTentarSalvarUsuarioComEmailNulo() {
            // Arrange
            var usuarioCreateDTO = new UsuarioCreateDTO();
            usuarioCreateDTO.setNome("Kauã");
            usuarioCreateDTO.setEmail(null);
            usuarioCreateDTO.setSenha("S123456");

            var usuarioEntity = Usuario.builder()
                    .id(1)
                    .nome("Kauã")
                    .email(null)
                    .senha("S123456")
                    .build();
            doReturn(usuarioEntity).when(usuarioMapper).toEntity(any(UsuarioCreateDTO.class));
            doReturn(true).when(usuarioRepository).existsByEmail(usuarioCreateDTO.getEmail());

            // Act & Assert
            UsuarioJaExisteException exception = assertThrows(UsuarioJaExisteException.class, () -> usuarioService.salvarUsuario(usuarioCreateDTO));
            assertEquals("Já existe um usuário cadastrado com o email: " + usuarioCreateDTO.getEmail(), exception.getMessage());
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar salvar usuário nulo")
        void DeveLancarExcecaoAoTentarSalvarUsuarioNulo() {
            // Arrange
            var usuarioCreateDTO = new UsuarioCreateDTO();
            usuarioCreateDTO.setNome(null);
            usuarioCreateDTO.setEmail(null);
            usuarioCreateDTO.setSenha(null);
            var usuarioEntity = Usuario.builder()
                    .id(1)
                    .nome(null)
                    .email(null)
                    .senha(null)
                    .build();
            doReturn(usuarioEntity).when(usuarioMapper).toEntity(any(UsuarioCreateDTO.class));
            doReturn(true).when(usuarioRepository).existsByEmail(usuarioCreateDTO.getEmail());
            // Act & Assert
            UsuarioJaExisteException exception = assertThrows(UsuarioJaExisteException.class, () -> usuarioService.salvarUsuario(usuarioCreateDTO));
            assertEquals("Já existe um usuário cadastrado com o email: " + usuarioCreateDTO.getEmail(), exception.getMessage());
        }

    }

    @Nested
    class ListarUsuarios {

        @Test
        @DisplayName("Deve listar usuários com sucesso")
        void DeveListarUsuariosComSucesso() {
            // Arrange
            var usuarioEntity1 = Usuario.builder()
                    .id(1)
                    .nome("Kauã")
                    .email("kaua@gmail.com")
                    .senha("S123456")
                    .build();

            var usuarioEntity2 = Usuario.builder()
                    .id(2)
                    .nome("Kauã victor")
                    .email("kaua1@gmail.com")
                    .senha("S123456")
                    .build();

            var usuarioViewDTO1 = new UsuarioViewDTO();
            usuarioViewDTO1.setIdUsuario(1);
            usuarioViewDTO1.setNome("Kauã");
            usuarioViewDTO1.setEmail("kaua@gmail.com");

            var usuarioViewDTO2 = new UsuarioViewDTO();
            usuarioViewDTO2.setIdUsuario(2);
            usuarioViewDTO2.setNome("Kauã victor");
            usuarioViewDTO2.setEmail("kaua1@gmail.com");

            doReturn(List.of(usuarioEntity1, usuarioEntity2)).when(usuarioRepository).findAll();
            doReturn(usuarioViewDTO1).when(usuarioMapper).toViewDTO(usuarioEntity1);
            doReturn(usuarioViewDTO2).when(usuarioMapper).toViewDTO(usuarioEntity2);
            // Act
            var output = usuarioService.listarUsuarios();
            // Assert
            assertNotNull(output);
            assertEquals(2, output.size());
            assertEquals("Kauã", output.get(0).getNome());
            assertEquals("kaua@gmail.com", output.get(0).getEmail());
            assertEquals("Kauã victor", output.get(1).getNome());
            assertEquals("kaua1@gmail.com", output.get(1).getEmail());

        }

        @Test
        @DisplayName("Deve retornar lista vazia quando não houver usuários")
        void DeveRetornarListaVaziaQuandoNaoHouverUsuarios() {
            // Arrange
            doReturn(List.of()).when(usuarioRepository).findAll();
            // Act
            var output = usuarioService.listarUsuarios();
            // Assert
            assertNotNull(output);
            assertTrue(output.isEmpty());
        }

    }

    @Nested
    class BuscarUsuarioPorId {

        @Test
        @DisplayName("Deve buscar usuário por ID com sucesso")
        void DeveBuscarUsuarioPorIdComSucesso() {
            // Arrange
            var usuarioEntity = Usuario.builder()
                    .id(1)
                    .nome("Kauã")
                    .email("kaua@gmail.com")
                    .senha("S123456")
                    .build();

            var usuarioViewDTO = new UsuarioViewDTO();
            usuarioViewDTO.setIdUsuario(1);
            usuarioViewDTO.setNome("Kauã");
            usuarioViewDTO.setEmail("kaua@gmail.com");

            doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findById(1);
            doReturn(usuarioViewDTO).when(usuarioMapper).toViewDTO(any(Usuario.class));
            // Act
            var output = usuarioService.buscarUsuarioPorId(1);
            // Assert
            assertNotNull(output);
            assertEquals("Kauã", output.getNome());
            assertEquals("kaua@gmail.com", output.getEmail());

        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar buscar usuário por ID inexistente")
        void DeveLancarExcecaoAoTentarBuscarUsuarioPorIdInexistente() {
            // Arrange
            doReturn(Optional.empty()).when(usuarioRepository).findById(1);
            // Act & Assert
            RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioService.buscarUsuarioPorId(1));
            assertEquals("Usuário não encontrado com o id: " + 1, exception.getMessage());
        }


    }

    @Nested
    class AtualizarUsuario {

        @Test
        @DisplayName("Deve atualizar usuário com sucesso")
        void DeveAtualizarUsuarioComSucesso() {
            // Arrange
            var usuarioUpdateDTO = new UsuarioUpdateDTO();
            usuarioUpdateDTO.setNome("Kauã Victor");
            usuarioUpdateDTO.setSenha("S123456");

            var usuarioEntity = Usuario.builder()
                    .id(1)
                    .nome("Kauã")
                    .email("kaua1@gmail.com")
                    .senha("S123456")
                    .build();

            var usuarioEntityAtualizado = Usuario.builder()
                    .id(1)
                    .nome("Kauã Victor")
                    .email("kaua@gmail.com")
                    .build();

            var usuarioViewDTO = new UsuarioViewDTO();
            usuarioViewDTO.setIdUsuario(1);
            usuarioViewDTO.setNome("Kauã Victor");
            usuarioViewDTO.setEmail("kaua@gmail.com");

            doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findById(1);
            doAnswer(invocation -> {
                UsuarioUpdateDTO dto = invocation.getArgument(0);
                Usuario entity = invocation.getArgument(1);
                entity.setNome(dto.getNome());
                entity.setSenha(dto.getSenha());
                return null;
            }).when(usuarioMapper).updateEntityFromDTO(any(UsuarioUpdateDTO.class), any(Usuario.class));
            doReturn(usuarioEntityAtualizado).when(usuarioRepository).saveAndFlush(any(Usuario.class));
            doReturn(usuarioViewDTO).when(usuarioMapper).toViewDTO(any(Usuario.class));
            // Act
            var output = usuarioService.atualizarUsuario(1, usuarioUpdateDTO);
            // Assert
            assertNotNull(output);
            assertEquals("Kauã Victor", output.getNome());
            assertEquals("kaua@gmail.com", output.getEmail());

        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar atualizar usuário inexistente")
        void DeveLancarExcecaoAoTentarAtualizarUsuarioInexistente() {
            // Arrange
            var usuarioUpdateDTO = new UsuarioUpdateDTO();
            usuarioUpdateDTO.setNome("Novo Nome");
            usuarioUpdateDTO.setSenha("NovaSenha");
            doReturn(Optional.empty()).when(usuarioRepository).findById(99);
            // Act & Assert
            RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioService.atualizarUsuario(99, usuarioUpdateDTO));
            assertEquals("Usuário não encontrado com o id: 99", exception.getMessage());
        }
    }

    @Nested
    class RemoverUsuario {

        @Test
        @DisplayName("Deve remover usuário com sucesso")
        void DeveRemoverUsuarioComSucesso() {
            // Arrange
            var usuarioEntity = Usuario.builder()
                    .id(1)
                    .nome("Kauã")
                    .email("kaua@gmail.com")
                    .senha("S123456")
                    .build();
            doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findById(1);
            // Act & Assert
            assertDoesNotThrow(() -> usuarioService.deletarUsuario(1));
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar remover usuário inexistente")
        void DeveLancarExcecaoAoTentarRemoverUsuarioInexistente() {
            // Arrange
            doReturn(Optional.empty()).when(usuarioRepository).findById(99);
            // Act & Assert
            RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioService.deletarUsuario(99));
            assertEquals("Usuário não encontrado com o id: 99", exception.getMessage());
        }
    }

}