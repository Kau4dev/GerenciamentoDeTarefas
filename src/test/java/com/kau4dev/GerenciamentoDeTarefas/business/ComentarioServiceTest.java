package com.kau4dev.GerenciamentoDeTarefas.business;

import com.kau4dev.GerenciamentoDeTarefas.dto.comentarioDTO.ComentarioCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO.TarefaCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Tarefa;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Usuario;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.ComentarioRepository;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.TarefaRepository;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.UsuarioRepository;
import com.kau4dev.GerenciamentoDeTarefas.mapper.ComentarioMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class ComentarioServiceTest {

    @Mock
    ComentarioRepository comentarioRepository;

    @Mock
    TarefaRepository tarefaRepository;

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    ComentarioMapper comentarioMapper;

    @InjectMocks
    private ComentarioService comentarioService;

    @Nested
    class CriarComentario {

        @Test
        @DisplayName("Deve criar um comentário com sucesso")
        void deveCriarComentarioComSucesso() {

            var idTarefa = 1;
            var idUsuario = 1;
            var idComentario = 1;

            var usuarioCreateDTO = new UsuarioCreateDTO();
            usuarioCreateDTO.setNome("Kauã");
            usuarioCreateDTO.setEmail("kaua@gmail.com");
            usuarioCreateDTO.setSenha("S123456");

            var usuarioEntity = Usuario.builder()
                    .id(idUsuario)
                    .nome("Kauã")
                    .email("kaua@gmail.com")
                    .senha("S123456")
                    .build();

            var tarefaCreateDTO = new TarefaCreateDTO();
            tarefaCreateDTO.setIdTarefa(idTarefa);
            tarefaCreateDTO.setIdUsuario(idUsuario);
            tarefaCreateDTO.setTitulo("Título da Tarefa");
            tarefaCreateDTO.setStatus(null);

            var tarefaEntity = Tarefa.builder()
                    .id(idTarefa)
                    .titulo("Título da Tarefa")
                    .usuario(usuarioEntity)
                    .build();

            var comentarioCreateDTO = new ComentarioCreateDTO();
            comentarioCreateDTO.setIdTarefa(idTarefa);
            comentarioCreateDTO.setTexto("Comentário de teste");
            comentarioCreateDTO.setDataCriacao(null);
            comentarioCreateDTO.setIdUsuario(idUsuario);

            var comentarioEntity = new com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Comentario();
            comentarioEntity.setId(idComentario);
            comentarioEntity.setTexto("Comentário de teste");
            comentarioEntity.setTarefa(tarefaEntity);
            comentarioEntity.setUsuario(usuarioEntity);
            comentarioEntity.setDataCriacao(null);

            var comentarioViewDTO = new com.kau4dev.GerenciamentoDeTarefas.dto.comentarioDTO.ComentarioViewDTO();
            comentarioViewDTO.setIdComentario(idComentario);
            comentarioViewDTO.setTexto("Comentário de teste");
            comentarioViewDTO.setNomeUsuario(usuarioEntity.getNome());

            doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findById(idUsuario);
            doReturn(Optional.of(tarefaEntity)).when(tarefaRepository).findById(idTarefa);
            doReturn(comentarioEntity).when(comentarioMapper).toEntity(comentarioCreateDTO);
            doReturn(comentarioEntity).when(comentarioRepository).saveAndFlush(comentarioEntity);
            doReturn(comentarioViewDTO).when(comentarioMapper).toViewDTO(comentarioEntity);

            var output = comentarioService.criarComentario(idTarefa, idUsuario, comentarioCreateDTO);

            assertEquals(idComentario, output.getIdComentario());
            assertEquals("Comentário de teste", output.getTexto());
            assertEquals(usuarioEntity.getNome(), output.getNomeUsuario());
        }


        @Test
        @DisplayName("Deve lançar exceção quando o usuário não for encontrado")
        void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {
            var idTarefa = 1;
            var idUsuario = 1;

            var comentarioCreateDTO = new ComentarioCreateDTO();
            comentarioCreateDTO.setIdTarefa(idTarefa);
            comentarioCreateDTO.setTexto("Comentário de teste");
            comentarioCreateDTO.setDataCriacao(null);
            comentarioCreateDTO.setIdUsuario(idUsuario);

            doReturn(Optional.empty()).when(usuarioRepository).findById(idUsuario);

            try {
                comentarioService.criarComentario(idTarefa, idUsuario, comentarioCreateDTO);
            } catch (RuntimeException e) {
                assertEquals("Usuário não encontrado com o id: " + idUsuario, e.getMessage());
            }

        }

        @Test
        @DisplayName("Deve lançar exceção quando a tarefa não for encontrada")
        void deveLancarExcecaoQuandoTarefaNaoForEncontrada() {
            var idTarefa = 1;
            var idUsuario = 1;

            var usuarioCreateDTO = new UsuarioCreateDTO();
            usuarioCreateDTO.setNome("Kauã");
            usuarioCreateDTO.setEmail("kaua@gmail.com");
            usuarioCreateDTO.setSenha("S123456");

            var usuarioEntity = Usuario.builder()
                    .id(idUsuario)
                    .nome("Kauã")
                    .email("kaua@gmail.com")
                    .senha("S123456")
                    .build();

            var comentarioCreateDTO = new ComentarioCreateDTO();
            comentarioCreateDTO.setIdTarefa(idTarefa);
            comentarioCreateDTO.setTexto("Comentário de teste");
            comentarioCreateDTO.setDataCriacao(null);
            comentarioCreateDTO.setIdUsuario(idUsuario);

            doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findById(idUsuario);
            doReturn(Optional.empty()).when(tarefaRepository).findById(idTarefa);

            RuntimeException thrown = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
                comentarioService.criarComentario(idTarefa, idUsuario, comentarioCreateDTO);
            });
            System.err.println("Mensagem da exceção: [" + thrown.getMessage() + "]");
            assertEquals("Tarefa não encontrada com o id: " + idTarefa, thrown.getMessage());

        }

    }

    @Nested
    class ListarComentarios {
        @Test
        @DisplayName("Deve listar comentários com sucesso")
        void deveListarComentariosComSucesso() {
            var idTarefa = 1;
            var idUsuario = 1;
            var idComentario = 1;

            var usuarioEntity = Usuario.builder()
                    .id(idUsuario)
                    .nome("Kauã")
                    .email("kaua@gmail.com")
                    .senha("S123456")
                    .build();

            var tarefaEntity = Tarefa.builder()
                    .id(idTarefa)
                    .titulo("Título da Tarefa")
                    .usuario(usuarioEntity)
                    .build();

            var comentarioEntity = new com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Comentario();
            comentarioEntity.setId(idComentario);
            comentarioEntity.setTexto("Comentário de teste");
            comentarioEntity.setTarefa(tarefaEntity);
            comentarioEntity.setUsuario(usuarioEntity);
            comentarioEntity.setDataCriacao(null);

            var comentarioViewDTO = new com.kau4dev.GerenciamentoDeTarefas.dto.comentarioDTO.ComentarioViewDTO();
            comentarioViewDTO.setIdComentario(idComentario);
            comentarioViewDTO.setTexto("Comentário de teste");
            comentarioViewDTO.setNomeUsuario(usuarioEntity.getNome());

            doReturn(Optional.of(tarefaEntity)).when(tarefaRepository).findById(idTarefa);
            doReturn(java.util.List.of(comentarioEntity)).when(comentarioRepository).findByTarefaId(idTarefa);
            doReturn(comentarioViewDTO).when(comentarioMapper).toViewDTO(comentarioEntity);

            var output = comentarioService.listarComentarios(idTarefa);
            assertEquals(1, output.size());
            assertEquals(idComentario, output.get(0).getIdComentario());
            assertEquals("Comentário de teste", output.get(0).getTexto());
            assertEquals(usuarioEntity.getNome(), output.get(0).getNomeUsuario());
        }

        @Test
        @DisplayName("Deve lançar exceção ao listar comentários de tarefa inexistente")
        void deveLancarExcecaoAoListarComentariosDeTarefaInexistente() {
            var idTarefa = 1;
            doReturn(Optional.empty()).when(tarefaRepository).findById(idTarefa);
            try {
                comentarioService.listarComentarios(idTarefa);
            } catch (RuntimeException e) {
                assertEquals("Tarefa não encontrada com o id: " + idTarefa, e.getMessage());
            }
        }
    }

    @Nested
    class DeletarComentario {


        @Test
        @DisplayName("Deve deletar comentário com sucesso")
        void deveDeletarComentarioComSucesso() {
            var idTarefa = 1;
            var idUsuario = 1;
            var idComentario = 1;

            var usuarioEntity = Usuario.builder()
                    .id(idUsuario)
                    .nome("Kauã")
                    .email("kaua@gmail.com")
                    .senha("S123456")
                    .build();

            var tarefaEntity = Tarefa.builder()
                    .id(idTarefa)
                    .titulo("Título da Tarefa")
                    .usuario(usuarioEntity)
                    .build();

            var comentarioEntity = new com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Comentario();
            comentarioEntity.setId(idComentario);
            comentarioEntity.setTexto("Comentário de teste");
            comentarioEntity.setTarefa(tarefaEntity);
            comentarioEntity.setUsuario(usuarioEntity);
            comentarioEntity.setDataCriacao(null);

            var comentarioViewDTO = new com.kau4dev.GerenciamentoDeTarefas.dto.comentarioDTO.ComentarioViewDTO();
            comentarioViewDTO.setIdComentario(idComentario);
            comentarioViewDTO.setTexto("Comentário de teste");
            comentarioViewDTO.setNomeUsuario(usuarioEntity.getNome());

            doReturn(Optional.of(tarefaEntity)).when(tarefaRepository).findById(idTarefa);
            doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findById(idUsuario);
            doReturn(Optional.of(comentarioEntity)).when(comentarioRepository).findById(idComentario);

            comentarioService.deletarComentario(idTarefa, idUsuario, idComentario);

        }

        @Test
        @DisplayName("Deve lançar exceção ao deletar comentário de tarefa inexistente")
        void deveLancarExcecaoAoDeletarComentarioDeTarefaInexistente() {
            var idTarefa = 1;
            var idUsuario = 1;
            var idComentario = 1;
            doReturn(Optional.empty()).when(tarefaRepository).findById(idTarefa);
            try {
                comentarioService.deletarComentario(idTarefa, idUsuario, idComentario);
            } catch (RuntimeException e) {
                assertEquals("Tarefa não encontrada com o id: " + idTarefa, e.getMessage());
            }
        }

        @Test
        @DisplayName("Deve lançar exceção ao deletar comentário de usuário inexistente")
        void deveLancarExcecaoAoDeletarComentarioDeUsuarioInexistente() {
            var idTarefa = 1;
            var idUsuario = 1;
            var idComentario = 1;
            var tarefaEntity = Tarefa.builder().id(idTarefa).build();
            doReturn(Optional.of(tarefaEntity)).when(tarefaRepository).findById(idTarefa);
            doReturn(Optional.empty()).when(usuarioRepository).findById(idUsuario);
            try {
                comentarioService.deletarComentario(idTarefa, idUsuario, idComentario);
            } catch (RuntimeException e) {
                assertEquals("Usuário não encontrado com o id: " + idUsuario, e.getMessage());
            }
        }

        @Test
        @DisplayName("Deve lançar exceção ao deletar comentário inexistente")
        void deveLancarExcecaoAoDeletarComentarioInexistente() {
            var idTarefa = 1;
            var idUsuario = 1;
            var idComentario = 1;
            var tarefaEntity = Tarefa.builder().id(idTarefa).build();
            var usuarioEntity = Usuario.builder().id(idUsuario).build();
            doReturn(Optional.of(tarefaEntity)).when(tarefaRepository).findById(idTarefa);
            doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findById(idUsuario);
            doReturn(Optional.empty()).when(comentarioRepository).findById(idComentario);
            try {
                comentarioService.deletarComentario(idTarefa, idUsuario, idComentario);
            } catch (RuntimeException e) {
                assertEquals("Comentário não encontrado com o id: " + idComentario, e.getMessage());
            }
        }

        @Test
        @DisplayName("Deve lançar exceção ao deletar comentário que não pertence ao usuário")
        void deveLancarExcecaoAoDeletarComentarioDeOutroUsuario() {
            var idTarefa = 1;
            var idUsuario = 1;
            var idComentario = 1;
            var usuarioEntity = Usuario.builder().id(idUsuario).build();
            var outroUsuario = Usuario.builder().id(2).build();
            var tarefaEntity = Tarefa.builder().id(idTarefa).build();
            var comentarioEntity = new com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Comentario();
            comentarioEntity.setId(idComentario);
            comentarioEntity.setUsuario(outroUsuario);
            doReturn(Optional.of(tarefaEntity)).when(tarefaRepository).findById(idTarefa);
            doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findById(idUsuario);
            doReturn(Optional.of(comentarioEntity)).when(comentarioRepository).findById(idComentario);
            try {
                comentarioService.deletarComentario(idTarefa, idUsuario, idComentario);
            } catch (RuntimeException e) {
                assertEquals("Comentário não pertence ao usuário com id: " + idUsuario, e.getMessage());
            }
        }
    }
}
