package es.grupoO.FastFood.mapper;

import es.grupoO.FastFood.dto.ClienteLoginDTO;
import es.grupoO.FastFood.model.entity.Cliente;
import es.grupoO.FastFood.model.valueobject.Pair;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteLoginMapper {
    @Mapping(target = "idCliente", source = "cliente.idCliente")
    @Mapping(target = "email", source = "cliente.email")
    @Mapping(target = "token", source = "token")
    ClienteLoginDTO toDTO(Cliente cliente, String token);

    default ClienteLoginDTO fromPair(Pair<Cliente, String> data) {
        return this.toDTO(data.getFirst(), data.getSecond());
    }
}
