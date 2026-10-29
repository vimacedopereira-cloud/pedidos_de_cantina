package unisa.pedidos_de_cantina.service;

import org.junit.jupiter.api.Test;
import unisa.pedidos_de_cantina.model.Pedido;
import unisa.pedidos_de_cantina.model.Produto;
import unisa.pedidos_de_cantina.repository.PedidoRepository;
import unisa.pedidos_de_cantina.repository.ProdutoRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class PedidoServiceTest {

    @Test
    public void deveLancarExcecaoQuandoProdutoNaoTemEstoque() {
        // 1. Preparação dos dados (Arrange)
        PedidoRepository pedidoRepositoryMock = mock(PedidoRepository.class);
        ProdutoRepository produtoRepositoryMock = mock(ProdutoRepository.class);
        
        PedidoService pedidoService = new PedidoService(pedidoRepositoryMock, produtoRepositoryMock);

        Produto produtoSemEstoque = new Produto();
        produtoSemEstoque.setId(5L);
        produtoSemEstoque.setNome("Bolo");
        produtoSemEstoque.setEstoqueDisponivel(0); // Cenário de fronteira/erro

        Pedido pedido = new Pedido();

        // 2. Execução (Act) e 3. Verificação (Assert)
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.registrarPedido(pedido, produtoSemEstoque);
        });

        assertEquals("Erro: Produto sem stock disponível.", exception.getMessage());
    }
}