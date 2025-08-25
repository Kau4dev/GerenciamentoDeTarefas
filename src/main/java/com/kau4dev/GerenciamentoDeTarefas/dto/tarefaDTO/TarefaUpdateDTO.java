package com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO;

import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.enums.StatusTarefa;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TarefaUpdateDTO {

    @Size(max = 100, message = "O título não pode ter mais de 100 caracteres")
    @Pattern(regexp = "^[A-ZÀ-Ý][a-zA-ZÀ-ÿ\\s]*$",
            message = "O título deve começar com letra maiúscula e conter apenas letras e espaços")
    private String titulo;

    @Size(max = 255, message = "A descrição não pode ter mais de 255 caracteres")
    @Pattern(regexp = "^[A-ZÀ-Ý][A-Za-zÀ-ÿ0-9\\s]*$",
           message = "A descrição deve começar com letra maiúscula e conter apenas letras, números e espaços") private String Descricao;
    private String descricao;

    private StatusTarefa status;

    private LocalDateTime dataConclusao;


}
