package com.example.nitro_autopecas_sistema.nitro_autopecas.service;

import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.itemVendaDto.DadosItemVendaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.vendaDto.DadosCriarVendaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.dto.vendaDto.DadosDetalhamentoVendaDto;
import com.example.nitro_autopecas_sistema.nitro_autopecas.entity.*;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.ClienteRepository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.FuncionarioRepository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.PecaRepository;
import com.example.nitro_autopecas_sistema.nitro_autopecas.repository.VendaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class VendaService {

    @Autowired
    VendaRepository repository;
    @Autowired
    FuncionarioRepository funcionarioRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    PecaRepository pecaRepository;


    public DadosDetalhamentoVendaDto adicionar(DadosCriarVendaDto dto) {
        Venda venda = new Venda();

        venda.setStatus(StatusVenda.REALIZADA);

        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o ID: " + dto.clienteId()));
        venda.setCliente(cliente);

        Funcionario funcionario = funcionarioRepository.findById(dto.funcionarioId())
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com o ID: " + dto.funcionarioId()));
        venda.setFuncionario(funcionario);

        BigDecimal valorTotalDaVenda = BigDecimal.ZERO;

        for (DadosItemVendaDto itemDto : dto.itens()) {
            Peca peca = pecaRepository.findById(itemDto.pecaId())
                    .orElseThrow(() -> new EntityNotFoundException("Peça não encontrada com o ID: " + itemDto.pecaId()));

            if (peca.getQuantidadeEstoque() < itemDto.quantidade()) {
                throw new IllegalArgumentException("Estoque insuficiente para a peça: " + peca.getNome() + ". Disponível: " + peca.getQuantidadeEstoque());
            }

            peca.setQuantidadeEstoque(peca.getQuantidadeEstoque() - itemDto.quantidade());

            ItemVenda item = new ItemVenda();
            item.setPeca(peca);
            item.setQuantidade(itemDto.quantidade());
            item.setPrecoUnitario(peca.getPrecoVenda());

            item.setVenda(venda);
            venda.getItens().add(item);

            BigDecimal subtotal = peca.getPrecoVenda().multiply(new BigDecimal(itemDto.quantidade()));
            valorTotalDaVenda = valorTotalDaVenda.add(subtotal);
        }

        venda.setValorTotal(valorTotalDaVenda);

        repository.save(venda);
        return new DadosDetalhamentoVendaDto(venda);
    }

    public void cancelarVenda(Long id){
        Venda vendaBanco = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Venda não encontrada."));
        vendaBanco.setStatus(StatusVenda.CANCELADA);
        repository.save(vendaBanco);
    }
}
