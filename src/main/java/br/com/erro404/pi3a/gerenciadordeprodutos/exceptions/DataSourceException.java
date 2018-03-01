package br.com.erro404.pi3a.gerenciadordeprodutos.exceptions;

/**
 *
 * @author Erro404
 */
public class DataSourceException extends Exception {

    public DataSourceException(String message) {
        super(message);
    }

    public DataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSourceException(Throwable cause) {
        super(cause);
    }
}
