package com.kau4dev.GerenciamentoDeTarefas.mapper;

import com.kau4dev.GerenciamentoDeTarefas.dto.TarefaDTO;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Tarefa;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    @Mapping(source = "id", target = "idTarefa")
    @Mapping(source = "usuario.id", target = "idUsuario")
    TarefaDTO toDTO(Tarefa tarefa);

    @Mapping(source = "idTarefa", target = "id")
    @Mapping(source = "idUsuario", target = "usuario.id")
    Tarefa toEntity(TarefaDTO tarefaDTO);
}
