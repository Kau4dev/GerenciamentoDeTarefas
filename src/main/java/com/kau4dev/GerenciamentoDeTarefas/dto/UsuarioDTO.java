package com.kau4dev.GerenciamentoDeTarefas.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Integer idUsuario;
    private String nome;
    private String email;
    private String senha;

}
