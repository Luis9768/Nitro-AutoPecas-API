package com.example.nitro_autopecas_sistema.nitro_autopecas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class NitroAutopecasApplication {

	public static void main(String[] args) {
		SpringApplication.run(NitroAutopecasApplication.class, args);
	}
	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager(
				"categorias",
				"clientes",
				"fornecedores",
				"funcionarios",
				"pecas"
		);
	}

}
