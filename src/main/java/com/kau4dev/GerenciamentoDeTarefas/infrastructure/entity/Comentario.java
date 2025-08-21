package com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "texto", nullable = false, length = 500)
    @NotBlank(message = "O texto do comentário não pode ser vazio")
    @Size(max = 500, message = "O texto do comentário não pode ter mais de 500 caracteres")
    private String texto;

    @Column(name = "data_criacao", nullable = false)
    private java.time.LocalDateTime dataCriacao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_comentario_usuario"))
    @NotBlank(message = "O usuário não pode ser vazio")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tarefa_id", foreignKey = @ForeignKey(name = "fk_comentario_tarefa"))
    @NotBlank(message = "A tarefa não pode ser vazia")
    private Tarefa tarefa;

}



