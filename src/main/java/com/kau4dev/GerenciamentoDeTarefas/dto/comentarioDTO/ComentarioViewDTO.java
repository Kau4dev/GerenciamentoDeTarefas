package com.kau4dev.GerenciamentoDeTarefas.dto.comentarioDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComentarioViewDTO {

    private String Texto;

    private String NomeUsuario;

    private LocalDateTime dataCriacao;

}
