package es.grupoO.FastFood.mapper;

import es.grupoO.FastFood.dto.RestauranteLoginDTO;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.valueobject.Pair;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RestauranteLoginMapper {
    @Mapping(target = "token", source = "token")
    RestauranteLoginDTO toDTO(Restaurante restaurante, String token);

    default RestauranteLoginDTO fromPair(Pair<Restaurante, String> data) {
        return this.toDTO(data.getFirst(), data.getSecond());
    }
}
