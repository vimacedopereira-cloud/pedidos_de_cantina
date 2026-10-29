public java.util.List<Pedido> listarTodos() {
        String sql = "SELECT p.id, p.preco_registrado, p.data_registro, p.situacao, " +
                     "c.id AS cliente_id, c.nome AS cliente_nome, c.email AS cliente_email, " +
                     "pr.id AS produto_id, pr.nome AS produto_nome, pr.preco AS produto_preco, pr.estoque_disponivel " +
                     "FROM pedidos p " +
                     "JOIN clientes c ON p.cliente_id = c.id " +
                     "JOIN produtos pr ON p.produto_id = pr.id " +
                     "ORDER BY p.id";

        java.util.List<Pedido> pedidos = new java.util.ArrayList<>();

        try (java.sql.Connection conn = dataSource.getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
             java.sql.ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                unisa.pedidos_de_cantina.model.Cliente cliente = new unisa.pedidos_de_cantina.model.Cliente();
                cliente.setId(rs.getLong("cliente_id"));
                cliente.setNome(rs.getString("cliente_nome"));
                cliente.setEmail(rs.getString("cliente_email"));

                unisa.pedidos_de_cantina.model.Produto produto = new unisa.pedidos_de_cantina.model.Produto();
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
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Erro ao buscar pedidos no banco de dados", e);
        }

        return pedidos;
    }