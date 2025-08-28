package com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO;

import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.enums.StatusTarefa;
import lombok.Data;

@Data
public class TarefaViewDTO {

    private Integer idTarefa;

    private String titulo;

    private String descricao;

    private StatusTarefa status;

    private String dataCriacao;

    private String dataConclusao;

}