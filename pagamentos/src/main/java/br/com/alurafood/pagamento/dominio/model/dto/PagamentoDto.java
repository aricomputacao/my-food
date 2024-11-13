package br.com.alurafood.pagamento.dominio.model.dto;

import br.com.alurafood.pagamento.dominio.enumeration.Status;

import java.math.BigDecimal;

public record PagamentoDto(
        Long id,
        BigDecimal valor,
        String nome,
        String numero,
        String expiracao,
        String codigo,
        Status status,
        Long formaDePagamentoId,
        Long pedidoId
) {
}
