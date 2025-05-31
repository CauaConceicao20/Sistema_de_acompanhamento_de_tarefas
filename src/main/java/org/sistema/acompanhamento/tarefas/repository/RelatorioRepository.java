package org.sistema.acompanhamento.tarefas.repository;
import org.sistema.acompanhamento.tarefas.util.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioRepository {

    public List<Map<String, Object>> buscarDadosSupervisor(int supervisorId) throws SQLException {
        String sql = """
        SELECT u.id AS supervisor_id, 
               u.nome AS supervisor_nome,
               t.id AS tarefa_id, 
               t.nome AS tarefa_nome, 
               t.descricao, 
               t.status,
               f.nome AS funcionario_nome  -- novo campo
        FROM usuario u
        LEFT JOIN tarefa t ON u.id = t.supervisor_id
        LEFT JOIN usuario f ON f.id = t.funcionario_id  -- join com funcionário
        WHERE u.cargo = 'SUPERVISOR' AND u.id = ?
    """;

        List<Map<String, Object>> resultados = new ArrayList<>();

        try (
                Connection conn = DataBaseConnection.createConnection();
                PreparedStatement pstm = conn.prepareStatement(sql)
        ) {
            pstm.setInt(1, supervisorId);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("supervisor_id", rs.getInt("supervisor_id"));
                    row.put("supervisor_nome", rs.getString("supervisor_nome"));
                    row.put("tarefa_id", rs.getObject("tarefa_id")); // pode ser null
                    row.put("tarefa_nome", rs.getString("tarefa_nome"));
                    row.put("descricao", rs.getString("descricao"));
                    row.put("status", rs.getString("status"));
                    row.put("funcionario_nome", rs.getString("funcionario_nome")); // novo campo
                    resultados.add(row);
                }
            }
        }

        return resultados;
    }
}

