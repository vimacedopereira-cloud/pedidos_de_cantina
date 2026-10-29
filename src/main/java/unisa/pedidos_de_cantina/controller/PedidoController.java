package unisa.pedidos_de_cantina.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unisa.pedidos_de_cantina.model.Pedido;
import unisa.pedidos_de_cantina.repository.PedidoRepository;
import unisa.pedidos_de_cantina.service.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoService pedidoService, PedidoRepository pedidoRepository) {
        this.pedidoService = pedidoService;
        this.pedidoRepository = pedidoRepository;
    }

    // Rota GET /pedidos (Listar histórico de pedidos)
    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        return ResponseEntity.ok(pedidoRepository.listarTodos());
    }

    // Rota POST /pedidos (Registrar pedido e baixar uma unidade do estoque)
    @PostMapping
    public ResponseEntity<String> registrarPedido(@RequestBody Pedido pedido) {
        try {
            pedidoService.registrarPedido(pedido, pedido.getProduto());
            return ResponseEntity.status(HttpStatus.CREATED).body("Pedido registado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao processar o pedido.");
        }
    }

    // Rota PUT /pedidos/{id}/cancelar (Cancelar pedido e devolver stock)
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<String> cancelarPedido(@PathVariable Long id) {
        try {
            pedidoService.cancelarPedido(id);
            return ResponseEntity.ok("Pedido cancelado com sucesso. Stock devolvido!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno ao cancelar pedido.");
        }
    }
}