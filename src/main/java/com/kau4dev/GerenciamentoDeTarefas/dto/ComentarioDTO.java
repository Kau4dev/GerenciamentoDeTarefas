package com.kau4dev.GerenciamentoDeTarefas.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComentarioDTO {
    private Integer idComentario;
    private String texto;
    private LocalDateTime dataCriacao;
    private Integer idTarefa;
    private Integer idUsuario;

}
