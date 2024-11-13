package br.com.alurafood.pagamento.application.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("pedidos-ms")
public interface PedidoClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/api/v1/pedidos/{id}/pago")
    void atualizarPagamento(@PathVariable Long id);
}
