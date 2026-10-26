package unisa.pedidos_de_cantina.repository;

import org.springframework.stereotype.Repository;
import unisa.pedidos_de_cantina.model.Produto;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProdutoRepository {

    private final DataSource dataSource;

    public ProdutoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void cadastrar(Produto produto) {
        String sql = "INSERT INTO produtos (nome, preco, estoque_disponivel) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getEstoqueDisponivel());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar produto na base de dados", e);
        }
    }

    // Novo método para listar os produtos (SELECT)
    public List<Produto> listar() {
        String sql = "SELECT id, nome, preco, estoque_disponivel FROM produtos";
        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getLong("id"));
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getDouble("preco"));
                p.setEstoqueDisponivel(rs.getInt("estoque_disponivel"));
                produtos.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos", e);
        }
        return produtos;
    }
}