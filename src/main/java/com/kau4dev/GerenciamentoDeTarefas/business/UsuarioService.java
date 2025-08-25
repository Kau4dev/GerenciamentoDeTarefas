package com.kau4dev.GerenciamentoDeTarefas.business;

import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioUpdateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioViewDTO;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Usuario;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.UsuarioRepository;
import com.kau4dev.GerenciamentoDeTarefas.mapper.UsuarioMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    public UsuarioService(UsuarioRepository repository, UsuarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public UsuarioViewDTO salvarUsuario(UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuario = mapper.toEntity(usuarioCreateDTO);
        Usuario usuarioSalvo = repository.saveAndFlush(usuario);
        return mapper.toViewDTO(usuarioSalvo);
    }

    public List<UsuarioViewDTO> listarUsuarios() {
        List<Usuario> usuarios = repository.findAll();
        return usuarios.stream()
                .map(mapper::toViewDTO)
                .toList();
    }

    public UsuarioViewDTO buscarUsuarioPorId(Integer idUsuario) {
        Usuario usuario = repository.findById(idUsuario).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado com o id: " + idUsuario)
        );
        return mapper.toViewDTO(usuario);
    }

    public UsuarioViewDTO atualizarUsuario(Integer idUsuario, UsuarioUpdateDTO usuarioUpdateDTO) {
        Usuario usuarioEntity = repository.findById(idUsuario).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado com o id: " + idUsuario)
        );
        mapper.updateEntityFromDTO(usuarioUpdateDTO, usuarioEntity);
        Usuario usuarioAtualizado = repository.saveAndFlush(usuarioEntity);
        return mapper.toViewDTO(usuarioAtualizado);
    }

    public void deletarUsuario(Integer idUsuario) {
          repository.findById(idUsuario).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado com o id: " + idUsuario)
        );
        repository.deleteById(idUsuario);
    }

}

