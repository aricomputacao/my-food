package br.com.alurafood.pedito.dominio.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoDto {

    private Long id;
    private Integer quantidade;
    private String descricao;
}
