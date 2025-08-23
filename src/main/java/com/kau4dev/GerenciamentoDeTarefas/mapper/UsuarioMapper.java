package com.kau4dev.GerenciamentoDeTarefas.mapper;


import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioUpdateDTO;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Usuario;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(source = "id", target = "idUsuario")
    UsuarioCreateDTO toDTO(Usuario usuario);

    @InheritInverseConfiguration
    Usuario toEntity(UsuarioCreateDTO usuarioCreateDTO);

    @Mapping(source = "id", target = "idUsuario")
    UsuarioUpdateDTO toUpdateDTO(Usuario usuario);

    @InheritInverseConfiguration
    Usuario toEntity(UsuarioUpdateDTO usuarioUpdateDTO);


}
