package br.com.alurafood.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("pedidos-ms", r -> r.path("/api/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://PEDIDOS-MS"))
                .route("pagamentos-ms", r -> r.path("/api/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://PAGAMENTOS-MS"))
                .build();
    }

    public static void main(String[] args) {

        SpringApplication.run(GatewayApplication.class, args);


    }


}
