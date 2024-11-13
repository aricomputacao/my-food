package br.com.alurafood.pedito.dominio.service;

import br.com.alurafood.pedito.dominio.enumeration.Status;
import br.com.alurafood.pedito.dominio.model.dto.PedidoDto;
import br.com.alurafood.pedito.dominio.model.entity.Pedido;
import br.com.alurafood.pedito.dominio.model.mapper.PedidoMapper;
import br.com.alurafood.pedito.dominio.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
public class PedidoService {

    private  PedidoRepository repository;
    private  PedidoMapper pedidoMapper;

    public List<PedidoDto> obterTodos() {
        return repository.findAll().stream()
                .map(p -> pedidoMapper.entityToDto(p)).collect(Collectors.toList());
    }

    public PedidoDto obterPorId(Long id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return pedidoMapper.entityToDto(pedido);
    }

    public PedidoDto criarPedido(PedidoDto dto) {
        Pedido pedido = pedidoMapper.dtoToEntity(dto);

        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(Status.REALIZADO);
        pedido.getItens().forEach(item -> item.setPedido(pedido));
        Pedido salvo = repository.save(pedido);

        return pedidoMapper.entityToDto(pedido);
    }

    public PedidoDto atualizaStatus(Long id, Status dto) {

        Pedido pedido = repository.porIdComItens(id);

        if (pedido == null) {
            throw new EntityNotFoundException();
        }

        pedido.setStatus(dto);
        repository.atualizaStatus(dto, pedido);
        return pedidoMapper.entityToDto(pedido);
    }

    public void aprovaPagamentoPedido(Long id) {
        Pedido pedido = repository.porIdComItens(id);
        if (pedido == null) {
            throw new EntityNotFoundException();
        }
        pedido.setStatus(Status.PAGO);
        log.info("****** Pedido: {} confirmando com sucesso.", pedido.getId());
        repository.atualizaStatus(Status.PAGO, pedido);
    }
}
