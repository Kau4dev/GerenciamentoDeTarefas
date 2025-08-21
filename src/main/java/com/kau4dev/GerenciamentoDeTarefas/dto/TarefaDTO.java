package com.kau4dev.GerenciamentoDeTarefas.dto;

import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.enums.StatusTarefa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TarefaDTO {

    private Integer idTarefa;

    @NotBlank(message = "O título não pode ser vazio")
    @Size(max = 100, message = "O título não pode ter mais de 100 caracteres")
    private String titulo;

    @Size(max = 255, message = "A descrição não pode ter mais de 255 caracteres")
    private String descricao;

    @NotBlank(message = "O status não pode ser vazio")
    private StatusTarefa status;

    @NotBlank(message = "O usuário não pode ser vazio")
    private Integer idUsuario;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataConclusao;
}
