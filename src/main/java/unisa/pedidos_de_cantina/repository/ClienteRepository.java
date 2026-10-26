package unisa.pedidos_de_cantina.repository;

import org.springframework.stereotype.Repository;
import unisa.pedidos_de_cantina.model.Cliente;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class ClienteRepository {

    private final DataSource dataSource;

    public ClienteRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void cadastrar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, email) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar cliente na base de dados", e);
        }
    }
}