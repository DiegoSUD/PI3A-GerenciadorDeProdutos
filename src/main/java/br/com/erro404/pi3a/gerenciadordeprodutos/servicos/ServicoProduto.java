package br.com.erro404.pi3a.gerenciadordeprodutos.servicos;

import br.com.erro404.pi3a.gerenciadordeprodutos.DAO.DAOProduto;
import br.com.erro404.pi3a.gerenciadordeprodutos.classes.Categoria;
import br.com.erro404.pi3a.gerenciadordeprodutos.classes.Produto;
import br.com.erro404.pi3a.gerenciadordeprodutos.exceptions.DataSourceException;
import br.com.erro404.pi3a.gerenciadordeprodutos.exceptions.ExceptionProduto;
import br.com.erro404.pi3a.gerenciadordeprodutos.validadores.ValidadorProduto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicoProduto {

    public static void cadastrarProduto(Produto produto, ArrayList<Categoria> categorias)
            throws ExceptionProduto, SQLException, ClassNotFoundException {
        try {
            ValidadorProduto.validar(produto);
            long idProduto = DAOProduto.incluir(produto);
            for (int i = 0; i < categorias.size(); i++) {
                categorias.get(i).setIdProduto(idProduto);
                DAOProduto.incluirProdutoCat(categorias.get(i));
            }

        } catch (Exception ex) {
            throw ex;
        }
    }

    public static List<Produto> procurarProdutos(String valor) throws ExceptionProduto, DataSourceException, Exception {
        try {
            if (valor == null) {
                return DAOProduto.listar();
            } else {
                return DAOProduto.procurar(valor);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataSourceException("Erro na fonte de dados", e);
        }
    }

    public static Produto obterProduto(Integer id) throws ExceptionProduto, DataSourceException, Exception {
        try {
            return DAOProduto.obter(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataSourceException("Erro na fonte de dados", e);
        }
    }

}
