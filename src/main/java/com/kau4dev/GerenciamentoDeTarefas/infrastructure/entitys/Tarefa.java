package com.kau4dev.GerenciamentoDeTarefas.infrastructure.entitys;


import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entitys.enums.StatusTarefa;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "fk_tarefa_usuario"))
    private Usuario usuario;

    @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentario;

    public Tarefa(String titulo, String descricao, String status, Integer usuario) {
            this.titulo = titulo;
            this.descricao = descricao;
            this.status = StatusTarefa.valueOf(status);
            this.dataCriacao = java.time.LocalDateTime.now();
    }
}
