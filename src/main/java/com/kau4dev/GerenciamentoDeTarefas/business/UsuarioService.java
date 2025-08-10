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

    public void atualizarUsuario(Usuario usuario) {
        if (usuario.getId() == null || !repository.existsById(usuario.getId())) {
            throw new RuntimeException("Usuário não encontrado com o id: " + usuario.getId());
        }
        repository.save(usuario);

    }

    public void deletarUsuario(Integer id){
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado com o id: " + id);
        }
        repository.deleteById(id);
    }

}

