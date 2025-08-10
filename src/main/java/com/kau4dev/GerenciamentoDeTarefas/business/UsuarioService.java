package com.kau4dev.GerenciamentoDeTarefas.business;

import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entitys.Usuario;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void salvarUsuario(Usuario usuario) {
        repository.save(usuario);

    }

    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }

    public Usuario buscarUsuarioPorId(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("usuario não encontrado com o id: " + id)
        );
    }

    public void atualizarUsuario(Integer id, Usuario usuario) {
        Usuario usuarioEntity = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado com o id: " + id)
        );
        Usuario usuarioAtualizado = Usuario.builder()
                .id(usuarioEntity.getId())
                .nome(usuario.getNome() != null ? usuario.getNome() : usuarioEntity.getNome())
                .email(usuario.getEmail() != null ? usuario.getEmail() : usuarioEntity.getEmail())
                .senha(usuario.getSenha() != null ? usuario.getSenha() : usuarioEntity.getSenha())
                .build();

    }

    public void deletarUsuario(Integer id){
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado com o id: " + id);
        }
        repository.deleteById(id);
    }

}

