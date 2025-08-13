package com.kau4dev.GerenciamentoDeTarefas.dto;

import lombok.Data;

@Data
public class ComentarioDTO {
    private String texto;
    private Integer tarefa;
    private Integer usuario;

}
