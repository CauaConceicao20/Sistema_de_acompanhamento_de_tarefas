package org.sistema.acompanhamento.tarefas.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


@WebListener
public class DataBaseConnection implements ServletContextListener {

    private static final String URL = "jdbc:sqlite:banco-de-dados.db";

    public static Connection createConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection conn = DriverManager.getConnection(URL)) {
                executeSqlScript(conn, "script.sql");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeSqlScript(Connection conn, String resourcePath) throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new RuntimeException("Arquivo SQL não encontrado: " + resourcePath);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder sql = new StringBuilder();
            String line;
            Statement stmt = conn.createStatement();

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--")) {
                    continue;
                }
                sql.append(line);
                if (line.endsWith(";")) {
                    String command = sql.toString();
                    stmt.execute(command);
                    sql.setLength(0);
                }
            }
            stmt.close();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}