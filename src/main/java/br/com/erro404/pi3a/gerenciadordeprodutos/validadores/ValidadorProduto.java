package br.com.erro404.pi3a.gerenciadordeprodutos.validadores;

import br.com.erro404.pi3a.gerenciadordeprodutos.classes.Produto;
import br.com.erro404.pi3a.gerenciadordeprodutos.exceptions.ExceptionProduto;

public class ValidadorProduto {

    public static void validar(Produto produto) throws ExceptionProduto {
        if (produto == null) {
            throw new ExceptionProduto("Instrumento não foi informado!");
        }
        if (produto.getNome() == null || "".equals(produto.getNome())) {
            throw new ExceptionProduto("Nome não informado!");
        }
        if (produto.getDescricao() == null || "".equals(produto.getDescricao())) {
            throw new ExceptionProduto("Descrição não informada!");
        }
        if (produto.getPrecoCompra() == null || (produto.getPrecoCompra() < 0)) {
            throw new ExceptionProduto("Preço de compra não informado!");
        }
        if (produto.getPrecoVenda() == null || (produto.getPrecoVenda() < 0)) {
            throw new ExceptionProduto("Preço de venda não informado!");
        }
        if (produto.getQuantidade() == null || (produto.getQuantidade() < 0)) {
            throw new ExceptionProduto("Quantidade não informada!");
        }
        
    }

}
