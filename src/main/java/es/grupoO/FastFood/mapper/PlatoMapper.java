package es.grupoO.FastFood.mapper;

import es.grupoO.FastFood.dto.PlatoDTO;
import es.grupoO.FastFood.model.entity.Plato;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlatoMapper {
    @Mapping(target = "idPlato", source = "plato.idPlato")
    @Mapping(target = "nombre", source = "plato.nombre")
    @Mapping(target = "nombreRestaurante", source = "plato.restaurante.nombre")
    @Mapping(target = "categoriaRestaurante", source = "plato.restaurante.categoria")
    @Mapping(target = "categoriaPlato", source = "plato.categoriaPlato")
    @Mapping(target = "precio", expression = "java(plato.getPrecio())")
    PlatoDTO toDto(Plato plato);

    List<PlatoDTO> toDtoList(List<Plato> platos);
}
