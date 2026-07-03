package com.example.nitro_autopecas_sistema.nitro_autopecas.config.httpClient;

import com.example.nitro_autopecas_sistema.nitro_autopecas.infra.client.BrasilApiClient;
import com.example.nitro_autopecas_sistema.nitro_autopecas.infra.client.ViaCepClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClientConfig {

    @Bean
    public BrasilApiClient brasilApiClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl("https://brasilapi.com.br/api")
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(BrasilApiClient.class);
    }
    @Bean
    public ViaCepClient viaCepClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl("https://viacep.com.br")
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(ViaCepClient.class);
    }
}