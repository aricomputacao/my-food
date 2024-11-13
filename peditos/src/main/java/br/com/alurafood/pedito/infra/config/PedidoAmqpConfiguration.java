package br.com.alurafood.pedito.infra.config;

import com.rabbitmq.client.AMQP;
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
public class PedidoAmqpConfiguration {
    private static final String PAGAMENTOS_EXCHANGE = "pagamentos.ex";
    private static final String PAGAMENTOS_DETALHE_PEDIDO_QUEUE = "pagamentos.detalhes-pedido";

    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return  rabbitTemplate;
    }

    @Bean
    public Queue filaDetalhesPedido() {
        return QueueBuilder
                .nonDurable(PAGAMENTOS_DETALHE_PEDIDO_QUEUE)
                .build();
    }

//    @Bean
//    public Queue filaDetalhesPedido2() {
//        return QueueBuilder
//                .nonDurable("pagamentos.detalhes2-pedido")
//                .build();
//    }

    //
    @Bean
    public FanoutExchange fanoutExchange() {

        return ExchangeBuilder
                .fanoutExchange(PAGAMENTOS_EXCHANGE)
                .build();
    }

//    @Bean
//    public FanoutExchange fanoutExchange2() {
//        return ExchangeBuilder
//                .fanoutExchange("pedidos.ex")
//                .build();
//    }

    @Bean
    public Binding bindPagamentoPedido() {
        return BindingBuilder
                .bind(filaDetalhesPedido())
                .to(fanoutExchange());
    }

//    @Bean
//    public Binding bindPagamentoPedido2(FanoutExchange fanoutExchange) {
//        return BindingBuilder
//                .bind(filaDetalhesPedido2())
//                .to(fanoutExchange2());
//    }

    @Bean
    public RabbitAdmin criaRabbitAdmin(ConnectionFactory conexao) {
        return new RabbitAdmin(conexao);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
    }
}
