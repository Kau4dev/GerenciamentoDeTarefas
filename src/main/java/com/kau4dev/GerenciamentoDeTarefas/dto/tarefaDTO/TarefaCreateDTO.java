package com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO;

import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.enums.StatusTarefa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TarefaCreateDTO {

    private Integer idTarefa;

    @NotBlank(message = "O título não pode ser vazio")
    @Size(max = 100, message = "O título não pode ter mais de 100 caracteres")
    @Pattern(regexp = "^[A-ZÀ-Ý][a-zA-ZÀ-ÿ\\s]*$",
            message = "O título deve começar com letra maiúscula e conter apenas letras e espaços")
    private String titulo;

    @Size(max = 255, message = "A descrição não pode ter mais de 255 caracteres")
    @Pattern(regexp = "^[A-ZÀ-Ý][A-Za-zÀ-ÿ0-9\\s]*$",
            message = "A descrição deve começar com letra maiúscula e conter apenas letras, números e espaços")
    private String descricao;

    @NotNull(message = "O status não pode ser nulo")
    private StatusTarefa status;

    @NotNull(message = "O usuário não pode ser nulo")
    private Integer idUsuario;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataConclusao;
}
