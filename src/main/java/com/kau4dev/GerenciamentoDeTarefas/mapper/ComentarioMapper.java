package com.kau4dev.GerenciamentoDeTarefas.mapper;

import com.kau4dev.GerenciamentoDeTarefas.dto.comentarioDTO.ComentarioCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Comentario;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Tarefa;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {

    @Mapping(source = "id", target = "idComentario")
    @Mapping(source = "usuario.id", target = "idUsuario")
    @Mapping(source = "tarefa.id", target = "idTarefa")
    ComentarioCreateDTO toDTO(Comentario comentario);

    @Mapping(source = "idComentario", target = "id")
    @Mapping(source = "idUsuario", target = "usuario", qualifiedByName = "mapUsuario")
    @Mapping(source = "idTarefa", target = "tarefa", qualifiedByName = "mapTarefa")
    Comentario toEntity(ComentarioCreateDTO dto);

    @Named("mapUsuario")
    default Usuario mapUsuario(Integer idUsuario) {
        return idUsuario == null ? null : Usuario.builder().id(idUsuario).build();
    }

    @Named("mapTarefa")
    default Tarefa mapTarefa(Integer idTarefa) {
        return idTarefa == null ? null : Tarefa.builder().id(idTarefa).build();
    }

}
