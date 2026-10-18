package unisa.pedidos_de_cantina.repository;

import org.springframework.stereotype.Repository;
import unisa.pedidos_de_cantina.model.Produto;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class ProdutoRepository {

    private final DataSource dataSource;

    public ProdutoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void cadastrar(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco, estoque_disponivel) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getEstoqueDisponivel());
            
            stmt.executeUpdate();

        } catch (SQLException e) {
            // Repassar como exceção de tempo de execução para não suprimir o erro
            throw new RuntimeException("Erro ao cadastrar produto na base de dados", e);
        }
    }
}