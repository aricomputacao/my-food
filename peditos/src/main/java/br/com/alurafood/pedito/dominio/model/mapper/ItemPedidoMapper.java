package br.com.alurafood.pedito.dominio.model.mapper;

import br.com.alurafood.pedito.dominio.model.dto.ItemPedidoDto;
import br.com.alurafood.pedito.dominio.model.entity.ItemPedido;
import org.mapstruct.Mapper;

@Mapper
public interface ItemPedidoMapper {
    ItemPedido dtoToEntity(ItemPedidoDto dto);

    ItemPedidoDto entityToDto(ItemPedido entity);

}
