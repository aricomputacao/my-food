package br.com.alurafood.pagamento.application.controller;

import br.com.alurafood.pagamento.application.util.UriUtil;
import br.com.alurafood.pagamento.dominio.model.dto.PagamentoDto;
import br.com.alurafood.pagamento.dominio.model.mapper.PagamentoMapper;
import br.com.alurafood.pagamento.dominio.service.PagamentoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final PagamentoMapper pagamentoMapper;
    private final RabbitTemplate rabbitTemplate;

    @PostMapping
    public ResponseEntity<PagamentoDto> cadastrar(@RequestBody @Valid PagamentoDto pagamentoDto) {
            pagamentoDto = pagamentoService.salvar(pagamentoDto);
//        Message message = new Message(("Criei um pagamento com o id" + pagamentoDto.id()).getBytes());
        rabbitTemplate.convertAndSend("pagamentos.ex","",pagamentoDto);
        return ResponseEntity.created(UriUtil.createUriWithId(pagamentoDto.id())).body(pagamentoDto);
    }

    @GetMapping()
    public Page<PagamentoDto> consultarTodos(@PageableDefault(size = 10)Pageable paginacao){
        return pagamentoService.consultarTodos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> consultarPor(@PathVariable @NotNull Long id){
        return ResponseEntity.ok(pagamentoService.consultarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDto> atualizar(@PathVariable @NotNull Long id,
                                                  @RequestBody @Valid PagamentoDto pagamentoDto){
       pagamentoDto = pagamentoService.atualizar(id, pagamentoDto);
       return ResponseEntity.ok(pagamentoDto);
    }

    @PatchMapping("/{id}/confirmar")
    @CircuitBreaker(name = "atualizaPedido", fallbackMethod = "pagamentoAutorizadoComIntegracaoPendente")
    public void confirmarPagamento(@PathVariable @NotNull Long id){
        pagamentoService.confirmarPagamento(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDto> remover(@PathVariable @NotNull Long id){
        pagamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    public void pagamentoAutorizadoComIntegracaoPendente(Long id, Exception e){
        pagamentoService.alterarStatus(id);
    }
}
