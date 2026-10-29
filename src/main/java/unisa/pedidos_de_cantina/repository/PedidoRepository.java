package unisa.pedidos_de_cantina.repository;

import org.springframework.stereotype.Repository;
import unisa.pedidos_de_cantina.model.Cliente;
import unisa.pedidos_de_cantina.model.Pedido;
import unisa.pedidos_de_cantina.model.Produto;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PedidoRepository {

    private final DataSource dataSource;

    public PedidoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void registrar(Pedido pedido) {
        String sql = "INSERT INTO pedidos (cliente_id, produto_id, preco_registrado, data_registro, situacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, pedido.getCliente().getId());
            stmt.setLong(2, pedido.getProduto().getId());
            stmt.setDouble(3, pedido.getPrecoRegistrado());
            stmt.setTimestamp(4, Timestamp.valueOf(pedido.getData()));
            stmt.setString(5, pedido.getSituacao());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar pedido na base de dados", e);
        }
    }

    public List<Pedido> listarTodos() {
        String sql = "SELECT p.id, p.preco_registrado, p.data_registro, p.situacao, " +
                     "c.id AS cliente_id, c.nome AS cliente_nome, c.email AS cliente_email, " +
                     "pr.id AS produto_id, pr.nome AS produto_nome, pr.preco AS produto_preco, pr.estoque_disponivel " +
                     "FROM pedidos p " +
                     "JOIN clientes c ON p.cliente_id = c.id " +
                     "JOIN produtos pr ON p.produto_id = pr.id " +
                     "ORDER BY p.id";

        List<Pedido> pedidos = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("cliente_id"));
                cliente.setNome(rs.getString("cliente_nome"));
                cliente.setEmail(rs.getString("cliente_email"));

                Produto produto = new Produto();
                produto.setId(rs.getLong("produto_id"));
                produto.setNome(rs.getString("produto_nome"));
                produto.setPreco(rs.getDouble("produto_preco"));
                produto.setEstoqueDisponivel(rs.getInt("estoque_disponivel"));

                Pedido pedido = new Pedido();
                pedido.setId(rs.getLong("id"));
                pedido.setCliente(cliente);
                pedido.setProduto(produto);
                pedido.setPrecoRegistrado(rs.getDouble("preco_registrado"));
                pedido.setData(rs.getTimestamp("data_registro").toLocalDateTime());
                pedido.setSituacao(rs.getString("situacao"));

                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pedidos no banco de dados", e);
        }

        return pedidos;
    }
    public Pedido buscarPorId(Long id) {
        String sql = "SELECT p.id, p.preco_registrado, p.data_registro, p.situacao, " +
                     "c.id AS cliente_id, " +
                     "pr.id AS produto_id, pr.estoque_disponivel " +
                     "FROM pedidos p " +
                     "JOIN clientes c ON p.cliente_id = c.id " +
                     "JOIN produtos pr ON p.produto_id = pr.id " +
                     "WHERE p.id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("cliente_id"));

                Produto produto = new Produto();
                produto.setId(rs.getLong("produto_id"));
                produto.setEstoqueDisponivel(rs.getInt("estoque_disponivel"));

                Pedido pedido = new Pedido();
                pedido.setId(rs.getLong("id"));
                pedido.setCliente(cliente);
                pedido.setProduto(produto);
                pedido.setSituacao(rs.getString("situacao"));
                return pedido;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pedido por ID", e);
        }
    }

    public void atualizarSituacao(Long id, String situacao) {
        String sql = "UPDATE pedidos SET situacao = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, situacao);
            stmt.setLong(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar situação do pedido", e);
        }
    }
}