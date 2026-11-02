package br.com.mecaniQA.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexaoMySql {

    private ConexaoMySql() {
    }

    public static Connection abrir() throws SQLException {
        String url = System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:3306/mecaniqa");
        String usuario = System.getenv().getOrDefault("DB_USER", "root");
        String senha = System.getenv().getOrDefault("DB_PASSWORD", "");

        return DriverManager.getConnection(url, usuario, senha);
    }
}
