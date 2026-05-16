package es.grupoO.FastFood.mapper;

import es.grupoO.FastFood.dto.RepartidorLoginDTO;
import es.grupoO.FastFood.model.entity.Repartidor;
import es.grupoO.FastFood.model.valueobject.Pair;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RepartidorLoginMapper {
    @Mapping(target = "idRepartidor", source = "repartidor.idRepartidor")
    @Mapping(target = "email", source = "repartidor.email")
    @Mapping(target = "token", source = "token")
    RepartidorLoginDTO toDTO(Repartidor repartidor, String token);

    default RepartidorLoginDTO fromPair(Pair<Repartidor, String> data) {
        return this.toDTO(data.getFirst(), data.getSecond());
    }
}
