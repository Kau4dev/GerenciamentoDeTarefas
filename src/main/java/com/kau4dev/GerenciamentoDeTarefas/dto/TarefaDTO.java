package com.kau4dev.GerenciamentoDeTarefas.dto;

import lombok.Data;

@Data
public class TarefaDTO {
    private String titulo;
    private String descricao;
    private String status;
    private Integer usuario;
}
