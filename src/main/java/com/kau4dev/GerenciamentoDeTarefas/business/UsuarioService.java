package com.kau4dev.GerenciamentoDeTarefas.business;

import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioUpdateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioViewDTO;
import com.kau4dev.GerenciamentoDeTarefas.exception.UsuarioException.IdUsuarioNaoEncontradoException;
import com.kau4dev.GerenciamentoDeTarefas.exception.UsuarioException.UsuarioJaExisteException;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Usuario;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.UsuarioRepository;
import com.kau4dev.GerenciamentoDeTarefas.mapper.UsuarioMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, UsuarioMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioViewDTO salvarUsuario(UsuarioCreateDTO usuarioCreateDTO) throws UsuarioJaExisteException {
        Usuario usuario = mapper.toEntity(usuarioCreateDTO);
        if (repository.existsByEmail(usuario.getEmail())) {
            throw new UsuarioJaExisteException ("Já existe um usuário cadastrado com o email: " + usuario.getEmail());
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
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
                () -> new IdUsuarioNaoEncontradoException("Usuário não encontrado com o id: " + idUsuario)
        );
        return mapper.toViewDTO(usuario);
    }

    public UsuarioViewDTO atualizarUsuario(Integer idUsuario, UsuarioUpdateDTO usuarioUpdateDTO) {
        Usuario usuarioEntity = repository.findById(idUsuario).orElseThrow(
                () -> new IdUsuarioNaoEncontradoException("Usuário não encontrado com o id: " + idUsuario)
        );
        mapper.updateEntityFromDTO(usuarioUpdateDTO, usuarioEntity);
        Usuario usuarioAtualizado = repository.saveAndFlush(usuarioEntity);
        return mapper.toViewDTO(usuarioAtualizado);
    }

    public void deletarUsuario(Integer idUsuario) {
          repository.findById(idUsuario).orElseThrow(
                () -> new IdUsuarioNaoEncontradoException("Usuário não encontrado com o id: " + idUsuario)
        );
        repository.deleteById(idUsuario);
    }

}
