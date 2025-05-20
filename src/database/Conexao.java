package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados MySQL.
 */
public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/escola";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";
    private static Connection conexao;

    
    /**
     * Obtém uma conexão com o banco de dados.
     * @return Connection objeto de conexão com o banco de dados
     * @throws SQLException se ocorrer um erro ao conectar com o banco de dados
     */
    public static Connection getConexao() throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Tentando conectar ao banco de dados...");
                System.out.println("URL: " + URL);
                System.out.println("Usuário: " + USUARIO);
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            } catch (ClassNotFoundException e) {
                System.err.println("Erro: Driver MySQL não encontrado!");
                System.err.println("Verifique se o arquivo mysql-connector-java está no classpath do projeto.");
                throw new SQLException("Driver MySQL não encontrado", e);
            } catch (SQLException e) {
                System.err.println("Erro ao conectar ao banco de dados!");
                System.err.println("Mensagem de erro: " + e.getMessage());
                System.err.println("Código de erro: " + e.getErrorCode());
                System.err.println("Estado SQL: " + e.getSQLState());
                throw e;
            }
        }
        return conexao;
    }

    /**
     * Fecha a conexão com o banco de dados.
     */
    public static void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }

    /**
     * Testa a conexão com o banco de dados.
     * @return true se a conexão foi bem sucedida, false caso contrário
     */
    public static boolean testarConexao() {
        try {
            Connection conn = getConexao();
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
            return false;
        }
    }
} 