package br.com.alurafood.pagamento.dominio.model.mapper;

import br.com.alurafood.pagamento.dominio.model.dto.PagamentoDto;
import br.com.alurafood.pagamento.dominio.model.entity.Pagamento;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PagamentoMapper {

    Pagamento dtoToEntity(PagamentoDto dto);

    PagamentoDto entityToDto(Pagamento entity);

    List<PagamentoDto> toDtoList(List<Pagamento> mesas);
}

