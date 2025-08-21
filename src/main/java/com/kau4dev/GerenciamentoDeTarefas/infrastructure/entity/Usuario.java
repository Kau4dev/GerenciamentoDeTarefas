package com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Não pode ser vazio")
    @Size(max = 100, message = "O nome não pode ter mais de 100 caracteres")
    @Pattern(regexp = "^[A-ZÀ-Ý][a-zA-ZÀ-ÿ\\s]*$",
            message = "O nome deve começar com letra maiúscula e conter apenas letras e espaços")
    private String nome;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Email inválido")
    @NotBlank(message = "Não pode ser vazio")
    @Size(max = 255,
            message = "O email não pode ter mais de 255 caracteres")
    private String email;

    @Column(name = "senha", nullable = false, length = 255)
    @NotBlank(message = "Não pode ser vazio")
    @Size(min = 6, max = 255,
            message = "A senha deve ter entre 6 e 255 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,255}$",
            message = "A senha deve conter pelo menos uma letra maiúscula, uma letra minúscula e um dígito")
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarefa> tarefa;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentario;

}
