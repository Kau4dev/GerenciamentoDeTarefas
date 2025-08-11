package com.kau4dev.GerenciamentoDeTarefas.infrastructure.entitys;


import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entitys.enums.StatusTarefa;
import jakarta.persistence.*;
import lombok.*;


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
    private String titulo;

    @Column(name="descricao", length = 255)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusTarefa status;

    @Column(name = "data_criacao", nullable = false)
    private java.time.LocalDateTime dataCriacao;

    @Column(name = "data_conclusao")
    private java.time.LocalDateTime dataConclusao;

    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

}
