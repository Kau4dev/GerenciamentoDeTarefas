package com.kau4dev.GerenciamentoDeTarefas.mapper;


import com.kau4dev.GerenciamentoDeTarefas.dto.UsuarioDTO;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Usuario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(source = "id", target = "idUsuario")
    UsuarioDTO toDTO(Usuario usuario);

    @InheritInverseConfiguration
    Usuario toEntity(UsuarioDTO usuarioDTO);

}
