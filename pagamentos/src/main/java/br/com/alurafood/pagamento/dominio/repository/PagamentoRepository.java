package br.com.alurafood.pagamento.dominio.repository;

import br.com.alurafood.pagamento.dominio.model.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
