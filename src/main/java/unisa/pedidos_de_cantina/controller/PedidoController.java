package unisa.pedidos_de_cantina.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unisa.pedidos_de_cantina.model.Pedido;
import unisa.pedidos_de_cantina.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Rota: POST /pedidos (Registrar pedido e baixar uma unidade do estoque)
    @PostMapping
    public ResponseEntity<String> registrarPedido(@RequestBody Pedido pedido) {
        try {
            // O service vai coordenar as regras de stock e transação
            pedidoService.registrarPedido(pedido, pedido.getProduto());
            return ResponseEntity.status(HttpStatus.CREATED).body("Pedido registado com sucesso!");
        } catch (IllegalArgumentException e) {
            // Resposta HTTP coerente para dados inválidos ou sem stock (regra da AVC)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar o pedido.");
        }
    }
}