package com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity;

import jakarta.persistence.*;
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
    private String texto;

    @Column(name = "data_criacao", nullable = false)
    private java.time.LocalDateTime dataCriacao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_comentario_usuario"))
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tarefa_id", foreignKey = @ForeignKey(name = "fk_comentario_tarefa"))
    private Tarefa tarefa;

}



