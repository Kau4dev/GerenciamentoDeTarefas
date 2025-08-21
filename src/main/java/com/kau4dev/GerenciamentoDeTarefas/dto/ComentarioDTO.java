package com.kau4dev.GerenciamentoDeTarefas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComentarioDTO {

    private Integer idComentario;

    @NotBlank(message = "O texto do comentário não pode ser vazio")
    @Size(max = 500, message = "O texto do comentário não pode ter mais de 500 caracteres")
    private String texto;

    private Integer idTarefa;

    private Integer idUsuario;

    private LocalDateTime dataCriacao;


}
