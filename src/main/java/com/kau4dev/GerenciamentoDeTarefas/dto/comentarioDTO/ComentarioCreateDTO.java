package com.kau4dev.GerenciamentoDeTarefas.dto.comentarioDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComentarioCreateDTO {

    private Integer idComentario;

    @NotBlank(message = "O texto do comentário não pode ser vazio")
    @Size(max = 500, message = "O texto do comentário não pode ter mais de 500 caracteres")
    @Pattern(regexp = "^[A-ZÀ-Ý][a-zA-ZÀ-ÿ\\s]*$",
            message = "O texto deve começar com letra maiúscula e conter apenas letras e espaços")
    private String texto;

    @NotNull(message = "O ID da tarefa não pode ser vazio")
    private Integer idTarefa;

    @NotNull(message = "O usuário não pode ser vazio")
    private Integer idUsuario;

    private LocalDateTime dataCriacao;

}
