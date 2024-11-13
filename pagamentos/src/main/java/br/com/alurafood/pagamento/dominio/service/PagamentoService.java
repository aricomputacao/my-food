package br.com.alurafood.pagamento.dominio.service;

import br.com.alurafood.pagamento.application.http.PedidoClient;
import br.com.alurafood.pagamento.dominio.enumeration.Status;
import br.com.alurafood.pagamento.dominio.model.dto.PagamentoDto;
import br.com.alurafood.pagamento.dominio.model.entity.Pagamento;
import br.com.alurafood.pagamento.dominio.model.mapper.PagamentoMapper;
import br.com.alurafood.pagamento.dominio.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
public class PagamentoService {

    private PagamentoRepository pagamentoRepository;
    private PagamentoMapper pagamentoMapper;
    private PedidoClient pedidoClient;

    public PagamentoDto salvar(PagamentoDto dto) {
        Pagamento pagamento = pagamentoMapper.dtoToEntity(dto);
        return pagamentoMapper.entityToDto(pagamentoRepository.save(pagamento));
    }

    public PagamentoDto atualizar(Long id, PagamentoDto dto) {
        Pagamento pagamentoExistente = pagamentoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        Pagamento pagamento = pagamentoMapper.dtoToEntity(dto);
        pagamento.setId(id);
        log.info("Atualizando pagamento: {}", pagamento);
        return pagamentoMapper.entityToDto(pagamentoRepository.save(pagamento));
    }

    public void deletar(Long id) {
        log.info("Deletando pagamento: {}", id);
        pagamentoRepository.deleteById(id);
    }

    public Page<PagamentoDto> consultarTodos(Pageable pageable) {
        return pagamentoRepository
                .findAll(pageable)
                .map(pagamento -> pagamentoMapper.entityToDto(pagamento));
    }

    public PagamentoDto consultarPorId(Long id) {
        return pagamentoMapper.entityToDto(pagamentoRepository.findById(id).
                orElseThrow(EntityNotFoundException::new));
    }

    public void confirmarPagamento(Long id){
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);

        if (!pagamento.isPresent()) {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO);
        pagamentoRepository.save(pagamento.get());
        log.info("****** Entrando para confirmar pagamento: {} do pedido: {} ", id, pagamento.get().getPedidoId());
        pedidoClient.atualizarPagamento(pagamento.get().getPedidoId());
    }

    public void alterarStatus(Long id){
        Optional<Pagamento> pagamento = pagamentoRepository.findById(id);

        if (!pagamento.isPresent()) {
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.CONFIRMADO_SEM_INTEGRACAO);
        pagamentoRepository.save(pagamento.get());
        log.info("****** Entrando para confirmar sem integração pagamento: {} do pedido: {} ", id, pagamento.get().getPedidoId());
    }
}
