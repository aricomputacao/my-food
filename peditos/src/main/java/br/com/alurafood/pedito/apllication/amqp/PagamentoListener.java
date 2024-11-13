package br.com.alurafood.pedito.apllication.amqp;

import br.com.alurafood.pedito.dominio.model.dto.PagamentoDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PagamentoListener {
    @RabbitListener(queues = "pagamentos.detalhes-pedido")
    public void recebeMensagem(PagamentoDto pagamentoDto) {
        String mensagem = """
                Dados do pagamento: %s
                Número do pedido: %s
                Valor R$: %s
                """.formatted(pagamentoDto.id(), pagamentoDto.numero(), pagamentoDto.valor());
        System.out.println("Recebi a mensagem " + mensagem);
    }
}
