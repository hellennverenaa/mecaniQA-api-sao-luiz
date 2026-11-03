package br.com.mecaniQA.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexaoMySql {

    private ConexaoMySql() {
    }

    public static Connection abrir() throws SQLException {
        String url = obterConfiguracao("DB_URL", "jdbc:mysql://localhost:3306/mecaniqa");
        String usuario = obterConfiguracao("DB_USER", "root");
        String senha = obterConfiguracao("DB_PASSWORD", "");

        return DriverManager.getConnection(url, usuario, senha);
    }

    private static String obterConfiguracao(String chave, String valorPadrao) {
        return System.getProperty(chave, System.getenv().getOrDefault(chave, valorPadrao));
    }
}
