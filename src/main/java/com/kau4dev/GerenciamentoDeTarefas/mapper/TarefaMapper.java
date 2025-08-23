package com.kau4dev.GerenciamentoDeTarefas.mapper;

import com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO.TarefaCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.tarefaDTO.TarefaUpdateDTO;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Tarefa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    @Mapping(source = "id", target = "idTarefa")
    @Mapping(source = "usuario.id", target = "idUsuario")
    TarefaCreateDTO toDTO(Tarefa tarefa);

    @Mapping(source = "idTarefa", target = "id")
    @Mapping(source = "idUsuario", target = "usuario.id")
    Tarefa toEntity(TarefaCreateDTO tarefaCreateDTO);

    @Mapping(source = "id", target = "idTarefa")
    @Mapping(source = "usuario.id", target = "idUsuario")
    TarefaUpdateDTO toUpdateDTO(Tarefa tarefa);

    @Mapping(source = "idTarefa", target = "id")
    @Mapping(source = "idUsuario", target = "usuario.id")
    Tarefa toEntity(TarefaUpdateDTO tarefaUpdateDTO);
}
