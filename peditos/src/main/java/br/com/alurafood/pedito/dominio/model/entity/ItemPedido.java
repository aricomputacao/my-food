package br.com.alurafood.pedito.dominio.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "item_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private Integer quantidade;

    private String descricao;

    @NotNull
    @ManyToOne(optional=false)
    @JoinColumn(name = "pedido_id", referencedColumnName = "id", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_pedido"))
    private Pedido pedido;

}
