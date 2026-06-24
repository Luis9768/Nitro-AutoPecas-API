package com.example.nitro_autopecas_sistema.nitro_autopecas.controller;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.autenticacaoDto.DadosAutenticacao;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.autenticacaoDto.DadosTokenJWT;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.Usuario;
import com.example.nitro_autopecas_sistema.nitro_autopecas.config.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        try{

            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());// Cria o token de dados (login/senha)

            // Autentica no banco
            var authentication = manager.authenticate(authenticationToken);

            // Gera o JWT
            var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

            // Devolve
            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
