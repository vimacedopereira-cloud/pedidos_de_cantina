package unisa.pedidos_de_cantina.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unisa.pedidos_de_cantina.model.Pedido;
import unisa.pedidos_de_cantina.model.Produto;
import unisa.pedidos_de_cantina.repository.PedidoRepository;
import unisa.pedidos_de_cantina.repository.ProdutoRepository;

import java.time.LocalDateTime;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public void registrarPedido(Pedido pedido, Produto produto) {
        // Regra de negócio: O produto precisa ter stock positivo
        if (produto.getEstoqueDisponivel() <= 0) {
            throw new IllegalArgumentException("Erro: Produto sem stock disponível.");
        }

        // Regra de negócio: O preço do produto é copiado para o pedido no momento da criação
        pedido.setPrecoRegistrado(produto.getPreco());
        pedido.setData(LocalDateTime.now());
        pedido.setSituacao("ATIVO");

        // Regra de negócio: Baixar uma unidade do stock
        produto.setEstoqueDisponivel(produto.getEstoqueDisponivel() - 1);

        // Gravar a nova quantidade de stock na base de dados
        produtoRepository.atualizarEstoque(produto);

        // Gravar o pedido na base de dados
        pedidoRepository.registrar(pedido);
    }

    @Transactional
    public void cancelarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);
        
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado.");
        }
        if ("CANCELADO".equals(pedido.getSituacao())) {
            throw new IllegalArgumentException("O pedido já se encontra cancelado.");
        }

        // 1. Muda a situação para CANCELADO
        pedidoRepository.atualizarSituacao(pedidoId, "CANCELADO");

        // 2. Devolve +1 ao stock do produto
        Produto produto = pedido.getProduto();
        produto.setEstoqueDisponivel(produto.getEstoqueDisponivel() + 1);
        produtoRepository.atualizarEstoque(produto);
    }
}