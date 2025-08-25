package com.kau4dev.GerenciamentoDeTarefas.mapper;


import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioCreateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioUpdateDTO;
import com.kau4dev.GerenciamentoDeTarefas.dto.usuarioDTO.UsuarioViewDTO;
import com.kau4dev.GerenciamentoDeTarefas.infrastructure.entity.Usuario;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @InheritInverseConfiguration
    Usuario toEntity(UsuarioCreateDTO usuarioCreateDTO);

    @Mapping(source = "id", target = "idUsuario")
    UsuarioViewDTO toViewDTO(Usuario usuario);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    void updateEntityFromDTO(UsuarioUpdateDTO dto, @MappingTarget Usuario entity);

}
