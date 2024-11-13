package br.com.alurafood.avaliacao.apllication.amqp;

import br.com.alurafood.avaliacao.dominio.model.dto.PagamentoDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoListener {
    @RabbitListener(queues = "pagamentos.detalhes-avaliacao")
    public void recebeMensagem(@Payload PagamentoDto pagamento) {

        if (pagamento.numero().equals("0000")) {
//            throw new RuntimeException("não consegui processar");
        }
        String mensagem = """
                Necessário criar registro de avaliação para o pedido: %s
                Id do pagamento: %s
                Nome do cliente: %s
                Valor R$: %s
                Status: %s 
                """.formatted(pagamento.id(),
                pagamento.id(),
                pagamento.nome(),
                pagamento.valor(),
                pagamento.status());

        System.out.println(mensagem);
    }
}
