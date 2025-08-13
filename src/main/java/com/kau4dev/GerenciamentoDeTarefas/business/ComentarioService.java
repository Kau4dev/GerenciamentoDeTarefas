package com.kau4dev.GerenciamentoDeTarefas.business;

import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entitys.Comentario;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.repository.ComentarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService {

    private ComentarioRepository comentarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository) {
        this.comentarioRepository = comentarioRepository;
    }

    public void criarComentario(Comentario comentario) {
        comentarioRepository.saveAndFlush(comentario);

    }

    public List<Comentario> listarComentarios() {
        return comentarioRepository.findAll();
    }

    public void deletarComentario(Integer id){
        if (!comentarioRepository.existsById(id)) {
            throw new RuntimeException("Comentário não encontrado com o id: " + id);
        }
        comentarioRepository.deleteById(id);
    }

}
