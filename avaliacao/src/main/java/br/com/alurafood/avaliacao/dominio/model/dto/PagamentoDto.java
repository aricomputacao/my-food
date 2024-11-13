package br.com.alurafood.avaliacao.dominio.model.dto;

import br.com.alurafood.avaliacao.dominio.enumeration.StatusPagamento;

import java.math.BigDecimal;

public record PagamentoDto(
        Long id,
        BigDecimal valor,
        String nome,
        String numero,
        String expiracao,
        String codigo,
        StatusPagamento status,
        Long formaDePagamentoId,
        Long pedidoId
) {
}
