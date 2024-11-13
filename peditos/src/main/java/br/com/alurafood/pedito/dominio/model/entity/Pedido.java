package br.com.alurafood.pedito.dominio.model.entity;

import br.com.alurafood.pedito.dominio.enumeration.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime dataHora;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "pedido_item", // Nome da tabela de junção
            uniqueConstraints = @UniqueConstraint(name = "uk_item_pedido", columnNames = {"item_id"}),
            joinColumns = @JoinColumn(name = "pedido_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "fk_pedido")), // FK para Pedido
            inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "fk_item") // FK para ItemDoPedido
            ))
    private List<ItemPedido> itens = new ArrayList<>();
}
