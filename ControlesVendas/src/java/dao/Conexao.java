package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static Connection conexao;
    
    public static Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/pedidosvenda";
            String usuario = "brunoMartins";
            String senha = "1234";
            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão estabelecida...");
        } catch (Exception e) {
            System.out.println("erro" + e);
        }
        return conexao;
        
    }
    
    public static void desconectar() {
        try{
            if (conexao != null) {
                conexao.close();
                System.out.println("Conexão fechada");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão" + e.getMessage());
        }
    }
    
}
