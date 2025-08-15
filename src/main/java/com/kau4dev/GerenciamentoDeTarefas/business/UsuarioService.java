package com.kau4dev.GerenciamentoDeTarefas.business;

import com.kau4dev.GerenciamentoDeTarefas.dto.UsuarioDTO;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entitys.Usuario;
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

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = mapper.toEntity(usuarioDTO);
        Usuario usuarioSalvo = repository.save(usuario);
        return mapper.toDTO(usuarioSalvo);
    }

    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = repository.findAll();
        return usuarios.stream()
                .map(mapper::toDTO)
                .toList();
    }

    public UsuarioDTO buscarUsuarioPorId(Integer idUsuario) {
        Usuario usuario = repository.findById(idUsuario).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado com o id: " + idUsuario)
        );
        return mapper.toDTO(usuario);
    }

    public UsuarioDTO atualizarUsuario(Integer idUsuario, UsuarioDTO usuarioDTO) {
        Usuario usuarioEntity = repository.findById(idUsuario).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado com o id: " + idUsuario)
        );
        usuarioEntity.setNome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : usuarioEntity.getNome());
        usuarioEntity.setEmail(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : usuarioEntity.getEmail());
        usuarioEntity.setSenha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : usuarioEntity.getSenha());
        Usuario usuarioAtualizado = repository.saveAndFlush(usuarioEntity);
        return mapper.toDTO(usuarioAtualizado);
    }

    public void deletarUsuario(Integer idUsuario) {
          repository.findById(idUsuario).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado com o id: " + idUsuario)
        );
        repository.deleteById(idUsuario);
    }

}

