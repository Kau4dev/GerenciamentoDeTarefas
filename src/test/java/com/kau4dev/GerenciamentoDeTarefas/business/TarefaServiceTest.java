package com.kau4dev.GerenciamentoDeTarefas.business;

import com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO.TarefaCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO.TarefaUpdateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO.TarefaViewDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.exception.TarefaException.TarefaNaoPodeSerNuloException;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Tarefa;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Usuario;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.enums.StatusTarefa;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.TarefaRepository;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.UsuarioRepository;
import com.kau4dev.GerenciamentoDeTarefas.mapper.TarefaMapper;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    TarefaRepository tarefaRepository;

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    TarefaMapper tarefaMapper;

    @InjectMocks
    private TarefaService tarefaService;

    @Nested
    class criarTarefa {

        @Test
        @DisplayName("Deve criar uma tarefa com sucesso")
        void deveCriarTarefaComSucesso() throws TarefaNaoPodeSerNuloException {

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

            var tarefaCreateDTO = new TarefaCreateDTO();
            tarefaCreateDTO.setIdTarefa(1);
            tarefaCreateDTO.setIdUsuario(1);
            tarefaCreateDTO.setTitulo("Título da Tarefa");
            tarefaCreateDTO.setStatus(StatusTarefa.valueOf("PENDENTE"));

            var tarefaEntity = Tarefa.builder()
                    .id(1)
                    .titulo("Título da Tarefa")
                    .status(StatusTarefa.PENDENTE)
                    .usuario(usuarioEntity)
                    .build();

            var tarefaViewDTO = new TarefaViewDTO();
            tarefaViewDTO.setIdTarefa(1);
            tarefaViewDTO.setTitulo("Título da Tarefa");
            tarefaViewDTO.setStatus(StatusTarefa.valueOf("PENDENTE"));

            doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findById(1);
            doReturn(tarefaEntity).when(tarefaMapper).toEntity(tarefaCreateDTO);
            doReturn(tarefaEntity).when(tarefaRepository).saveAndFlush(tarefaEntity);
            doReturn(tarefaViewDTO).when(tarefaMapper).toViewDTO(tarefaEntity);

            var output = tarefaService.criarTarefa(1, tarefaCreateDTO);

            assertNotNull(output);
            assertEquals("Título da Tarefa", output.getTitulo());
            assertEquals(StatusTarefa.PENDENTE, output.getStatus());

        }

        @Test
        @DisplayName("Deve lançar exceção quando DTO for nulo")
        void deveLancarExcecaoQuandoDTOForNulo() {
            TarefaNaoPodeSerNuloException exception = assertThrows(TarefaNaoPodeSerNuloException.class, () -> tarefaService.criarTarefa(1, null));
            assertEquals("Falha ao converter DTO para entidade Tarefa", exception.getMessage());
        }

        @Test
        @DisplayName("Deve lançar exceção quando usuário não for encontrado")
        void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {
            var tarefaCreateDTO = new TarefaCreateDTO();
            tarefaCreateDTO.setIdTarefa(1);
            tarefaCreateDTO.setIdUsuario(1);
            tarefaCreateDTO.setTitulo("Título da Tarefa");
            tarefaCreateDTO.setStatus(StatusTarefa.valueOf("PENDENTE"));

            doReturn(new Tarefa()).when(tarefaMapper).toEntity(tarefaCreateDTO);
            doReturn(Optional.empty()).when(usuarioRepository).findById(1);

            RuntimeException exception = assertThrows(RuntimeException.class, () -> tarefaService.criarTarefa(1, tarefaCreateDTO));

            assertEquals("Usuário não encontrado com o id: 1", exception.getMessage());
        }

        @Test
        @DisplayName("Deve receber um status nulo e passar como PENDENTE")
        void deveReceberUmStatusNuloEPassarComoPendente() throws TarefaNaoPodeSerNuloException {

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

            var tarefaCreateDTO = new TarefaCreateDTO();
            tarefaCreateDTO.setIdTarefa(1);
            tarefaCreateDTO.setIdUsuario(1);
            tarefaCreateDTO.setTitulo("Título da Tarefa");
            tarefaCreateDTO.setStatus(null);

            var tarefaEntity = Tarefa.builder()
                    .id(1)
                    .titulo("Título da Tarefa")
                    .status(StatusTarefa.PENDENTE)
                    .usuario(usuarioEntity)
                    .build();

            var tarefaViewDTO = new TarefaViewDTO();
            tarefaViewDTO.setIdTarefa(1);
            tarefaViewDTO.setTitulo("Título da Tarefa");
            tarefaViewDTO.setStatus(StatusTarefa.PENDENTE);

            doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findById(1);
            doReturn(tarefaEntity).when(tarefaMapper).toEntity(tarefaCreateDTO);
            doReturn(tarefaEntity).when(tarefaRepository).saveAndFlush(tarefaEntity);
            doReturn(tarefaViewDTO).when(tarefaMapper).toViewDTO(tarefaEntity);

            var output = tarefaService.criarTarefa(1, tarefaCreateDTO);

            assertNotNull(output);
            assertEquals("Título da Tarefa", output.getTitulo());
            assertEquals(StatusTarefa.PENDENTE, output.getStatus());

        }

        @Test
        @DisplayName("Deve lançar exceção quando ocorrer erro ao salvar tarefa")
        void deveLancarExcecaoQuandoOcorrerErroAoSalvarTarefa() {

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

            var tarefaCreateDTO = new TarefaCreateDTO();
            tarefaCreateDTO.setIdTarefa(1);
            tarefaCreateDTO.setIdUsuario(1);
            tarefaCreateDTO.setTitulo("Título da Tarefa");
            tarefaCreateDTO.setStatus(StatusTarefa.valueOf("PENDENTE"));

            var tarefaEntity = Tarefa.builder()
                    .id(1)
                    .titulo("Título da Tarefa")
                    .status(StatusTarefa.PENDENTE)
                    .usuario(usuarioEntity)
                    .build();

            doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findById(1);
            doReturn(tarefaEntity).when(tarefaMapper).toEntity(tarefaCreateDTO);
            org.mockito.Mockito.doThrow(new RuntimeException("Erro ao salvar a tarefa"))
                    .when(tarefaRepository).saveAndFlush(tarefaEntity);

            var exception = assertThrows(RuntimeException.class, () -> {
                tarefaService.criarTarefa(1, tarefaCreateDTO);
            });

            assertEquals("Erro ao salvar a tarefa", exception.getMessage());

        }

    }

    @Nested
    class listarTarefasPorUsuario {

        @Test
        @DisplayName("Deve listar tarefas de um usuário com sucesso")
        void deveListarTarefasDeUmUsuarioComSucesso() {
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

            var tarefaEntity = Tarefa.builder()
                    .id(1)
                    .titulo("Título da Tarefa")
                    .status(StatusTarefa.PENDENTE)
                    .usuario(usuarioEntity)
                    .build();

            var tarefaEntity2 = Tarefa.builder()
                    .id(2)
                    .titulo("Título da Tarefa segunda")
                    .status(StatusTarefa.PENDENTE)
                    .usuario(usuarioEntity)
                    .build();

            var tarefaViewDTO = new TarefaViewDTO();
            tarefaViewDTO.setIdTarefa(1);
            tarefaViewDTO.setTitulo("Título da Tarefa");
            tarefaViewDTO.setStatus(StatusTarefa.PENDENTE);

            var tarefaViewDTO2 = new TarefaViewDTO();
            tarefaViewDTO2.setIdTarefa(2);
            tarefaViewDTO2.setTitulo("Título da Tarefa segunda");
            tarefaViewDTO2.setStatus(StatusTarefa.PENDENTE);

            doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findById(1);
            doReturn(List.of(tarefaEntity, tarefaEntity2)).when(tarefaRepository).findByUsuarioId(1);
            doReturn(tarefaViewDTO).when(tarefaMapper).toViewDTO(tarefaEntity);
            doReturn(tarefaViewDTO2).when(tarefaMapper).toViewDTO(tarefaEntity2);

            var output = tarefaService.listarTarefas(1);

            assertNotNull(output);
            assertEquals(2, output.size());
            assertEquals("Título da Tarefa", output.get(0).getTitulo());
            assertEquals("Título da Tarefa segunda", output.get(1).getTitulo());
        }

        @Test
        @DisplayName("Deve laçar exceção se usúario for nulo")
        void deveLancarExcecaoSeUsuarioForNulo() {
            RuntimeException exception = assertThrows(RuntimeException.class, () -> tarefaService.listarTarefas(1));
            assertEquals("Usuário não encontrado com o id: 1", exception.getMessage());
        }



        @Test
        @DisplayName("Deve lançar exceção ao tentar listar tarefas de usuário inexistente")
        void deveLancarExcecaoAoListarTarefasDeUsuarioInexistente() {

            doReturn(Optional.empty()).when(usuarioRepository).findById(99);

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                tarefaService.listarTarefas(99);
            });

            assertEquals("Usuário não encontrado com o id: 99", exception.getMessage());
        }

        @Test
        @DisplayName("Deve lançar exceção se idUsuario for nulo ao listar tarefas")
        void deveLancarExcecaoSeIdUsuarioForNuloAoListarTarefas() {
            RuntimeException exception = assertThrows(RuntimeException.class, () -> tarefaService.listarTarefas(null));
            assertEquals("ID do usuário não pode ser nulo", exception.getMessage());
        }
    }

    @Nested
    class buscarTarefaPorId {


        @Test
        @DisplayName("Deve buscar uma tarefa por ID com sucesso")
        void deveBuscarTarefaPorIdComSucesso() {
            var usuarioCreateDTO = new UsuarioCreateDTO();
            usuarioCreateDTO.setIdUsuario(1);
            usuarioCreateDTO.setNome("Kauã");
            usuarioCreateDTO.setEmail("kaua@gmail.com");
            usuarioCreateDTO.setSenha("S123456");

            var usuarioEntity = Usuario.builder()
                    .id(1)
                    .nome("Kauã")
                    .email("kaua@gmail.com")
                    .senha("S123456")
                    .build();

            var tarefaCreateDTO = new TarefaCreateDTO();
            tarefaCreateDTO.setIdTarefa(1);
            tarefaCreateDTO.setIdUsuario(1);
            tarefaCreateDTO.setTitulo("Título da Tarefa");
            tarefaCreateDTO.setStatus(StatusTarefa.valueOf("PENDENTE"));

            var tarefaEntity = Tarefa.builder()
                    .id(1)
                    .titulo("Título da Tarefa")
                    .status(StatusTarefa.PENDENTE)
                    .usuario(usuarioEntity)
                    .build();

            var tarefaViewDTO = new TarefaViewDTO();
            tarefaViewDTO.setIdTarefa(1);
            tarefaViewDTO.setTitulo("Título da Tarefa");
            tarefaViewDTO.setStatus(StatusTarefa.PENDENTE);

            doReturn(Optional.of(tarefaEntity)).when(tarefaRepository).findByIdAndUsuarioId(1, 1);
            doReturn(tarefaViewDTO).when(tarefaMapper).toViewDTO(tarefaEntity);

            var output = tarefaService.buscarTarefaPorId(1, 1);

            assertNotNull(output);
            assertEquals("Título da Tarefa", output.getTitulo());
            assertEquals(StatusTarefa.PENDENTE, output.getStatus());


        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar buscar tarefa inexistente")
        void deveLancarExcecaoAoTentarBuscarTarefaInexistente() {

            doReturn(Optional.empty()).when(tarefaRepository).findByIdAndUsuarioId(99, 1);

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                tarefaService.buscarTarefaPorId(1, 99);
            });

            assertEquals("Tarefa não encontrada com o id: 99", exception.getMessage());
        }

    }

    @Nested
    class atualizarTarefa {

        @Test
        @DisplayName("Deve atualizar uma tarefa com sucesso")
        void deveAtualizarUmaTarefaComSucesso() {

            int usuarioId = 1;
            int tarefaId = 1;

            var usuarioEntity = Usuario.builder()
                    .id(usuarioId)
                    .nome("Kauã")
                    .email("kaua@gmail.com")
                    .senha("S123456")
                    .build();

            var tarefaEntity = Tarefa.builder()
                    .id(tarefaId)
                    .titulo("Título da Tarefa")
                    .status(StatusTarefa.PENDENTE)
                    .usuario(usuarioEntity)
                    .build();

            var tarefaUpdateDTO = new TarefaUpdateDTO();
            tarefaUpdateDTO.setTitulo("Título da Tarefa Atualizada");
            tarefaUpdateDTO.setStatus(StatusTarefa.PENDENTE);

            var tarefaViewDTOAtualizada = new TarefaViewDTO();
            tarefaViewDTOAtualizada.setIdTarefa(tarefaId);
            tarefaViewDTOAtualizada.setTitulo("Título da Tarefa Atualizada");
            tarefaViewDTOAtualizada.setStatus(StatusTarefa.PENDENTE);

            doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findById(1);
            doReturn(Optional.of(tarefaEntity)).when(tarefaRepository).findByIdAndUsuarioId(tarefaId, usuarioId);
            doAnswer(invocation -> {
                TarefaUpdateDTO dto = invocation.getArgument(0);
                Tarefa entity = invocation.getArgument(1);
                entity.setTitulo(dto.getTitulo());
                entity.setStatus(dto.getStatus());
                return null;
            }).when(tarefaMapper).updateEntityFromDTO(any(TarefaUpdateDTO.class), any(Tarefa.class));
            doReturn(tarefaEntity).when(tarefaRepository).saveAndFlush(any(Tarefa.class));
            doReturn(tarefaViewDTOAtualizada).when(tarefaMapper).toViewDTO(tarefaEntity);

            var output = tarefaService.atualizarTarefa(usuarioId, tarefaId, tarefaUpdateDTO);

            assertNotNull(output);
            assertEquals("Título da Tarefa Atualizada", output.getTitulo());
            assertEquals(StatusTarefa.PENDENTE, output.getStatus());
        }

        @Test
        @DisplayName("Deve lançar exceção se usuário não for encontrado")
        void deveLancarExcecaoSeUsuarioNaoForEncontrado() {
            var tarefaUpdateDTO = new TarefaUpdateDTO();
            tarefaUpdateDTO.setTitulo("Título da Tarefa Atualizada");
            tarefaUpdateDTO.setStatus(StatusTarefa.PENDENTE);

            doReturn(Optional.empty()).when(usuarioRepository).findById(1);

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                tarefaService.atualizarTarefa(1, 1, tarefaUpdateDTO);
            });

            assertEquals("Usuário não encontrado com o id: 1", exception.getMessage());
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar atualizar tarefa inexistente")
        void deveLancarExcecaoAoTentarAtualizarTarefaInexistente() {
            var tarefaUpdateDTO = new TarefaUpdateDTO();
            tarefaUpdateDTO.setTitulo("Título da Tarefa Atualizada");
            tarefaUpdateDTO.setStatus(StatusTarefa.PENDENTE);

            doReturn(Optional.of(Usuario.builder().id(1).build())).when(usuarioRepository).findById(1);
            doReturn(Optional.empty()).when(tarefaRepository).findByIdAndUsuarioId(99, 1);

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                tarefaService.atualizarTarefa(1, 99, tarefaUpdateDTO);
            });

            assertEquals("Tarefa não encontrada com o id: 99", exception.getMessage());
        }
    }


    @Nested
    class alteraStatusTarefa {

        @Test
        @DisplayName("Deve alterar o status de uma tarefa com sucesso")
        void deveAlterarStatusDeUmaTarefaComSucesso() {
            int usuarioId = 1;
            int tarefaId = 1;

            var usuarioEntity = Usuario.builder()
                    .id(usuarioId)
                    .nome("Kauã")
                    .email("kaua@gmail.com")
                    .senha("S123456")
                    .build();

            var tarefaEntity = Tarefa.builder()
                    .id(tarefaId)
                    .titulo("Título da Tarefa")
                    .status(StatusTarefa.PENDENTE)
                    .usuario(usuarioEntity)
                    .build();

            var tarefaUpdateDTO = new TarefaUpdateDTO();
            tarefaUpdateDTO.setStatus(StatusTarefa.CONCLUIDA);

            var tarefaViewDTOAtualizada = new TarefaViewDTO();
            tarefaViewDTOAtualizada.setIdTarefa(tarefaId);
            tarefaViewDTOAtualizada.setStatus(StatusTarefa.CONCLUIDA);

            doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findById(1);
            doReturn(Optional.of(tarefaEntity)).when(tarefaRepository).findByIdAndUsuarioId(tarefaId, usuarioId);
            doAnswer(invocation -> {
                TarefaUpdateDTO dto = invocation.getArgument(0);
                Tarefa entity = invocation.getArgument(1);
                entity.setTitulo(dto.getTitulo());
                entity.setStatus(dto.getStatus());
                return null;
            }).when(tarefaMapper).updateEntityFromDTO(any(TarefaUpdateDTO.class), any(Tarefa.class));
            doReturn(tarefaEntity).when(tarefaRepository).saveAndFlush(any(Tarefa.class));
            doReturn(tarefaViewDTOAtualizada).when(tarefaMapper).toViewDTO(tarefaEntity);

            var output = tarefaService.alteraStatusTarefa(usuarioId, tarefaId, tarefaUpdateDTO);

            assertNotNull(output);
            assertEquals(StatusTarefa.CONCLUIDA, output.getStatus());
        }

        @Test
        @DisplayName("Deve lançar exceção se usuário não for encontrado")
        void deveLancarExcecaoSeUsuarioNaoForEncontrado() {
            var tarefaUpdateDTO = new TarefaUpdateDTO();
            tarefaUpdateDTO.setStatus(StatusTarefa.PENDENTE);

            doReturn(Optional.empty()).when(usuarioRepository).findById(1);

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                tarefaService.atualizarTarefa(1, 1, tarefaUpdateDTO);
            });

            assertEquals("Usuário não encontrado com o id: 1", exception.getMessage());
        }

        @Test
        @DisplayName("Deve salvar data de conclusão ao alterar status para CONCLUIDA")
        void deveSalvarDataDeConclusaoAoAlterarStatusParaConcluida() {
            int usuarioId = 1;
            int tarefaId = 1;

            var usuarioEntity = Usuario.builder()
                    .id(usuarioId)
                    .nome("Kauã")
                    .email("kaua@gmail.com")
                    .senha("S123456")
                    .build();

            var tarefaEntity = Tarefa.builder()
                    .id(tarefaId)
                    .titulo("Título da Tarefa")
                    .status(StatusTarefa.PENDENTE)
                    .usuario(usuarioEntity)
                    .build();

            var tarefaUpdateDTO = new TarefaUpdateDTO();
            tarefaUpdateDTO.setStatus(StatusTarefa.CONCLUIDA);

            var tarefaViewDTOAtualizada = new TarefaViewDTO();
            tarefaViewDTOAtualizada.setIdTarefa(tarefaId);
            tarefaViewDTOAtualizada.setStatus(StatusTarefa.CONCLUIDA);
            tarefaViewDTOAtualizada.setDataConclusao(String.valueOf(tarefaEntity.getDataConclusao()));


            doReturn(Optional.of(usuarioEntity)).when(usuarioRepository).findById(1);
            doReturn(Optional.of(tarefaEntity)).when(tarefaRepository).findByIdAndUsuarioId(tarefaId, usuarioId);
            doAnswer(invocation -> {
                TarefaUpdateDTO dto = invocation.getArgument(0);
                Tarefa entity = invocation.getArgument(1);
                entity.setTitulo(dto.getTitulo());
                entity.setStatus(dto.getStatus());
                return null;
            }).when(tarefaMapper).updateEntityFromDTO(any(TarefaUpdateDTO.class), any(Tarefa.class));
            doReturn(tarefaEntity).when(tarefaRepository).saveAndFlush(any(Tarefa.class));
            doReturn(tarefaViewDTOAtualizada).when(tarefaMapper).toViewDTO(tarefaEntity);

            var output = tarefaService.alteraStatusTarefa(usuarioId, tarefaId, tarefaUpdateDTO);

            assertNotNull(output);
            assertEquals(StatusTarefa.CONCLUIDA, output.getStatus());
            assertNotNull(tarefaEntity.getDataConclusao());

        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar alterar status de tarefa inexistente")
        void deveLancarExcecaoAoTentarAlterarStatusDeTarefaInexistente() {
            var tarefaUpdateDTO = new TarefaUpdateDTO();
            tarefaUpdateDTO.setStatus(StatusTarefa.CONCLUIDA);

            doReturn(Optional.of(Usuario.builder().id(1).build())).when(usuarioRepository).findById(1);
            doReturn(Optional.empty()).when(tarefaRepository).findByIdAndUsuarioId(99, 1);

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                tarefaService.alteraStatusTarefa(1, 99, tarefaUpdateDTO);
            });

            assertEquals("Tarefa não encontrada com o id: 99", exception.getMessage());
        }

        @Test
        @DisplayName("Deve lançar exceção se status for nulo ao alterar status da tarefa")
        void deveLancarExcecaoSeStatusForNuloAoAlterarStatusTarefa() {
            int usuarioId = 1;
            int tarefaId = 1;
            var tarefaUpdateDTO = new TarefaUpdateDTO();
            tarefaUpdateDTO.setStatus(null);
            doReturn(Optional.of(Usuario.builder().id(usuarioId).build())).when(usuarioRepository).findById(usuarioId);
            doReturn(Optional.of(Tarefa.builder().id(tarefaId).usuario(Usuario.builder().id(usuarioId).build()).build()))
                    .when(tarefaRepository).findByIdAndUsuarioId(tarefaId, usuarioId);
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                tarefaService.alteraStatusTarefa(usuarioId, tarefaId, tarefaUpdateDTO);
            });
            assertEquals("Status inválido ou não fornecido", exception.getMessage());
        }

    }

    @Nested
    class deletarTarefa {

        @Test
        @DisplayName("Deve deletar uma tarefa com sucesso")
        void deveDeletarUmaTarefaComSucesso() {
            int usuarioId = 1;
            int tarefaId = 1;

            var usuarioEntity = Usuario.builder()
                    .id(usuarioId)
                    .nome("Kauã")
                    .email("kaua@gmail.com")
                    .senha("S123456")
                    .build();

            var tarefaEntity = Tarefa.builder()
                    .id(tarefaId)
                    .titulo("Título da Tarefa")
                    .status(StatusTarefa.PENDENTE)
                    .usuario(usuarioEntity)
                    .build();

            doReturn(Optional.of(Usuario.builder().id(1).build())).when(usuarioRepository).findById(1);
            doReturn(Optional.of(tarefaEntity)).when(tarefaRepository).findByIdAndUsuarioId(tarefaId, usuarioId);
            doNothing().when(tarefaRepository).delete(tarefaEntity);

            assertDoesNotThrow(() -> tarefaService.deletarTarefa(usuarioId, tarefaId));
            verify(tarefaRepository, times(1)).delete(tarefaEntity);
        }

        @Test
        @DisplayName("Deve lançar exceção se usuário não for encontrado")
        void deveLancarExcecaoSeUsuarioNaoForEncontrado() {
            doReturn(Optional.empty()).when(usuarioRepository).findById(1);

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                tarefaService.deletarTarefa(1, 1);
            });

            assertEquals("Usuário não encontrado com o id: 1", exception.getMessage());
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar deletar tarefa inexistente")
        void deveLancarExcecaoAoTentarDeletarTarefaInexistente() {

            doReturn(Optional.of(Usuario.builder().id(1).build())).when(usuarioRepository).findById(1);
            doReturn(Optional.empty()).when(tarefaRepository).findByIdAndUsuarioId(99, 1);

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                tarefaService.deletarTarefa(1, 99);
            });

            assertEquals("Tarefa não encontrada com o id: 99", exception.getMessage());
        }
    }
}
