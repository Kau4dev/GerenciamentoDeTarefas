package com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity;


import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.enums.StatusTarefa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tarefas")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @Column(name="titulo", nullable = false, length = 100)
    @NotBlank(message = "O título não pode ser vazio")
    @Size(max = 100, message = "O título não pode ter mais de 100 caracteres")
    @Pattern(regexp = "^[A-ZÀ-Ý][a-zA-ZÀ-ÿ\\s]*$",
            message = "O título deve começar com letra maiúscula e conter apenas letras e espaços")
    private String titulo;

    @Column(name="descricao", length = 255)
    @Size(max = 255, message = "A descrição não pode ter mais de 255 caracteres")
    @Pattern(regexp = "^[A-ZÀ-Ý][a-zA-ZÀ-ÿ\\s]*$",
            message = "O nome deve começar com letra maiúscula e conter apenas letras e espaços")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull(message = "O status não pode ser vazio")
    private StatusTarefa status;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_tarefa_usuario"))
    @NotNull(message = "O usuário não pode ser nulo")
    private Usuario usuario;

    @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentario;

}
