package unisa.pedidos_de_cantina.repository;

import org.springframework.stereotype.Repository;
import unisa.pedidos_de_cantina.model.Pedido;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

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
}