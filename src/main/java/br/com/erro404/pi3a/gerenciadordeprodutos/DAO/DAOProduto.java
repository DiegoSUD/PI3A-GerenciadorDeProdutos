package br.com.erro404.pi3a.gerenciadordeprodutos.DAO;

import br.com.erro404.pi3a.gerenciadordeprodutos.classes.Categoria;
import br.com.erro404.pi3a.gerenciadordeprodutos.classes.Produto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DAOProduto {

    private static Connection obterConexao() throws ClassNotFoundException, SQLException {
        //1A) Registrar drive JDBC
        Class.forName("com.mysql.jdbc.Driver");

        //1B) Abrir conexão com o BD
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/produtobd", "root", "");
    }

    public static long incluir(Produto produto) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO produto (NOME, DESCRICAO, PRECO_COMPRA, PRECO_VENDA, QUANTIDADE, DT_CADASTRO)"
                + "VALUES (?, ?, ?, ?, ?, ?) ";
        long idProduto = 0;

        try (Connection conn = obterConexao()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, produto.getNome());
                stmt.setString(2, produto.getDescricao());
                stmt.setDouble(3, produto.getPrecoCompra());
                stmt.setDouble(4, produto.getPrecoVenda());
                stmt.setInt(5, produto.getQuantidade());
                Timestamp dataBD = new Timestamp(produto.getDataCadastro().getTime());
                stmt.setTimestamp(6, dataBD);
                stmt.executeUpdate();

                try (ResultSet chave = stmt.getGeneratedKeys()) {
                    if (chave.next()) {
                        idProduto = chave.getLong(1);
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
        return idProduto;
    }

    public static void incluirProdutoCat(Categoria categoria) throws ClassNotFoundException, SQLException {
        String queryCat = "INSERT INTO produto_categoria (ID_PRODUTO, ID_CATEGORIA) VALUES (?, ?)";

        try (Connection conn = obterConexao()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt2 = conn.prepareStatement(queryCat)) {
                stmt2.setLong(1, categoria.getIdProduto());
                stmt2.setInt(2, categoria.getId());
                stmt2.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        }
    }

    public static List<Produto> listar() throws SQLException, Exception {
        String query = "SELECT * FROM PRODUTOBD.PRODUTO";
        List<Produto> listaProdutos = null;
        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    if (listaProdutos == null) {
                        listaProdutos = new ArrayList<Produto>();
                    }
                    Produto p = new Produto();
                    p.setId(rs.getInt("ID"));
                    p.setNome(rs.getString("NOME"));
                    p.setDescricao(rs.getString("DESCRICAO"));
                    p.setPrecoCompra(rs.getDouble("PRECO_COMPRA"));
                    p.setPrecoVenda(rs.getDouble("PRECO_VENDA"));
                    p.setQuantidade(rs.getInt("QUANTIDADE"));
                    p.setDataCadastro(rs.getDate("DT_CADASTRO"));
                    listaProdutos.add(p);
                }
            }
        }
        return listaProdutos;
    }

    public static List<Produto> procurar(String valor) throws SQLException, Exception {
        String query = "SELECT * FROM PRODUTOBD.PRODUTO WHERE NOME LIKE ?";
        List<Produto> listaProdutos = null;
        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + valor + "%");
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    if (listaProdutos == null) {
                        listaProdutos = new ArrayList<>();
                    }
                    Produto p = new Produto();
                    p.setId(rs.getInt("ID"));
                    p.setNome(rs.getString("NOME"));
                    p.setDescricao(rs.getString("DESCRICAO"));
                    p.setPrecoCompra(rs.getDouble("PRECO_COMPRA"));
                    p.setPrecoVenda(rs.getDouble("PRECO_VENDA"));
                    p.setQuantidade(rs.getInt("QUANTIDADE"));
                    p.setDataCadastro(rs.getDate("DT_CADASTRO"));
                    listaProdutos.add(p);
                }
            }
        }
        return listaProdutos;
    }

    public static Produto obter(int id) throws SQLException, Exception {
        String query = "SELECT * FROM PRODUTOBD.PRODUTO WHERE ID ?";
        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Produto p = new Produto();
                    p.setId(rs.getInt("ID"));
                    p.setNome(rs.getString("NOME"));
                    p.setDescricao(rs.getString("DESCRICAO"));
                    p.setPrecoCompra(rs.getDouble("PRECO_COMPRA"));
                    p.setPrecoVenda(rs.getDouble("PRECO_VENDA"));
                    p.setQuantidade(rs.getInt("QUANTIDADE"));
                    p.setDataCadastro(rs.getDate("DT_CADASTRO"));
                    return p;
                }
            }
        }
        return null;
    }    
}
