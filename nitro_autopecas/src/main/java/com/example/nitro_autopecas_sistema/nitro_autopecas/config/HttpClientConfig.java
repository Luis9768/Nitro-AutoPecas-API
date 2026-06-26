package com.example.nitro_autopecas_sistema.nitro_autopecas.config;

import com.example.nitro_autopecas_sistema.nitro_autopecas.infra.client.BrasilApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClientConfig {

    @Bean
    public BrasilApiClient brasilApiClient() {
        // Inicializa o RestClient nativo com a URL base da API
        RestClient restClient = RestClient.builder()
                .baseUrl("https://brasilapi.com.br/api")
                .build();

        // Cria o adaptador e a fábrica de proxies declarativos do Spring 6
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        // Retorna a implementação da interface criada dinamicamente pelo Spring
        return factory.createClient(BrasilApiClient.class);
    }
}