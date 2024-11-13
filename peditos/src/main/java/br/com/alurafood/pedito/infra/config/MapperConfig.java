package br.com.alurafood.pedito.infra.config;

import br.com.alurafood.pedito.dominio.model.mapper.ItemPedidoMapper;
import br.com.alurafood.pedito.dominio.model.mapper.PedidoMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public PedidoMapper pedidoMapper() {
        return Mappers.getMapper(PedidoMapper.class);
    }

    @Bean
    public ItemPedidoMapper itemPedidoMapper() {
        return Mappers.getMapper(ItemPedidoMapper.class);
    }


}