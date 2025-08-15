package com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false, length = 255)
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarefa> tarefa;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentario;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

}
