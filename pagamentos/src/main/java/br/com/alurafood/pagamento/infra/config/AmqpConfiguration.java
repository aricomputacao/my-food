package br.com.alurafood.pagamento.infra.config;

import org.springframework.amqp.core.FanoutExchange;
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
//    @Bean
//    public Queue criarFila(){
//        // return  new Queue("pagamento.concluido", false);
//        return QueueBuilder.nonDurable("pagamento.concluido").build();
//    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(PAGAMENTOS_EXCHANGE);
    }
    @Bean
    public RabbitAdmin criaRabbitAdmin(ConnectionFactory conexao) {
        return new RabbitAdmin(conexao);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
    }

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
}
