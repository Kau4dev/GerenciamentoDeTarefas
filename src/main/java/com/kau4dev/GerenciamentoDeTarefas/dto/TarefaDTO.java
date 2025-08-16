package com.kau4dev.GerenciamentoDeTarefas.dto;

import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.enums.StatusTarefa;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TarefaDTO {
    private Integer idTarefa;
    private String titulo;
    private String descricao;
    private StatusTarefa status;
    private Integer idUsuario;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataConclusao;
}
