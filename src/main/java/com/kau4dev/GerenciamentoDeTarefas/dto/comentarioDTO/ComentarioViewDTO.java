package com.kau4dev.GerenciamentoDeTarefas.dto.comentarioDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComentarioViewDTO {

    private Integer idComentario;

    private String texto;

    private String nomeUsuario;

    private LocalDateTime dataCriacao;

}
