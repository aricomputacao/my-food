package br.com.alurafood.avaliacao.infra.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AmqpConfiguration {

    private static final String PAGAMENTOS_EXCHANGE = "pagamentos.ex";
    private static final String PAGAMENTO_EXCHANGE_DLX = "pagamentos-dlx";
    private static final String PAGAMENTO_DETALHE_AVALICAO_FILA = "pagamentos.detalhes-avaliacao";
    private static final String PAGAMENTO_DETALHE_AVALICAO_FILA_DQL = "pagamentos.detalhes-avaliacao-dlq";

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Queue filaDetalhesAvaliacao() {
        return QueueBuilder
                //"pagamentos.detalhes-avaliacao
                .nonDurable(PAGAMENTO_DETALHE_AVALICAO_FILA)
                //pagamentos-dlx
                .deadLetterExchange(PAGAMENTO_EXCHANGE_DLX)
                .build();
    }

    @Bean
    public Queue filaDlqDetalhesAvaliacao() {
        return QueueBuilder
                //pagamentos.detalhes-avaliacao-dlq
                .nonDurable(PAGAMENTO_DETALHE_AVALICAO_FILA_DQL)
                .build();
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder
                .fanoutExchange(PAGAMENTOS_EXCHANGE)
                .build();
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        return ExchangeBuilder
                .fanoutExchange(PAGAMENTO_EXCHANGE_DLX)
                .build();
    }

    @Bean
    public Binding bindPagamentoPedido() {
        return BindingBuilder
                .bind(filaDetalhesAvaliacao())
                .to(fanoutExchange());
    }

    @Bean
    public Binding bindDlxPagamentoPedido() {
        return BindingBuilder
                .bind(filaDlqDetalhesAvaliacao())
                .to(deadLetterExchange());
    }

    @Bean
    public RabbitAdmin criaRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

}
