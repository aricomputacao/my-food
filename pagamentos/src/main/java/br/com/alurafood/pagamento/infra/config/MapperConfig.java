package br.com.alurafood.pagamento.infra.config;

import br.com.alurafood.pagamento.dominio.model.mapper.PagamentoMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public PagamentoMapper pagamentoMapper() {
        return Mappers.getMapper(PagamentoMapper.class);
    }


}