package br.com.alurafood.pedito.dominio.model.mapper;

import br.com.alurafood.pedito.dominio.model.dto.PedidoDto;
import br.com.alurafood.pedito.dominio.model.entity.Pedido;
import org.mapstruct.Mapper;

@Mapper
public interface PedidoMapper {

    Pedido dtoToEntity(PedidoDto dto);

    PedidoDto entityToDto(Pedido entity);

}
