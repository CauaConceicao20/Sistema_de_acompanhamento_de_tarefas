package org.sistema.acompanhamento.tarefas.repository;

import org.sistema.acompanhamento.tarefas.model.Tarefa;
import org.sistema.acompanhamento.tarefas.model.dto.ListTarefaRelatorioDto;
import org.sistema.acompanhamento.tarefas.model.enums.StatusTarefa;
import org.sistema.acompanhamento.tarefas.util.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TarefaRepository {

    public Tarefa createTarefa(Tarefa tarefa) {
        String sql = "INSERT INTO tarefa (nome, descricao, status, supervisor_id, funcionario_id) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = DataBaseConnection.createConnection();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setString(1, tarefa.getNome());
            pstm.setString(2, tarefa.getDescricao());
            pstm.setString(3, tarefa.getStatus().toString());
            pstm.setLong(4, tarefa.getSupervisorId());
            pstm.setLong(5, tarefa.getFuncionarioId());
            pstm.execute();

            return tarefa;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Tarefa buscaTarefaPorId(Long id) {
        String sql = "SELECT * FROM tarefa WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {
            conn = DataBaseConnection.createConnection();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setLong(1, id);

            rset = pstm.executeQuery();

            if (rset.next()) {
                return mapTarefaFromResultSet(rset);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Tarefa> listaTarefas() {
        String sql = "SELECT * FROM tarefa";

        Connection conn = null;
        PreparedStatement pstm = null;

        List<Tarefa> tarefas = new ArrayList<>();

        try {
            conn = DataBaseConnection.createConnection();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            ResultSet rset = pstm.executeQuery();

            while (rset.next()) {
                tarefas.add(mapTarefaFromResultSet(rset));
            }

            return tarefas;
        } catch (Exception e) {
            e.printStackTrace();
            return tarefas;
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Tarefa> listaTarefasConcluidas(Long funcionarioId) {
        String sql = "SELECT * FROM tarefa WHERE funcionario_id = ? AND status = 'CONCLUIDA'";

        Connection conn = null;
        PreparedStatement pstm = null;

        List<Tarefa> tarefas = new ArrayList<>();

        try {
            conn = DataBaseConnection.createConnection();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setLong(1, funcionarioId);

            ResultSet rset = pstm.executeQuery();

            while (rset.next()) {
                tarefas.add(mapTarefaFromResultSet(rset));
            }

            return tarefas;
        } catch (Exception e) {
            e.printStackTrace();
            return tarefas;
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Tarefa> listaTarefasPendentes(Long funcionarioId) {
        String sql = "SELECT * FROM tarefa WHERE funcionario_id = ? AND status = 'PENDENTE'";

        Connection conn = null;
        PreparedStatement pstm = null;

        List<Tarefa> tarefas = new ArrayList<>();

        try {
            conn = DataBaseConnection.createConnection();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setLong(1, funcionarioId);

            ResultSet rset = pstm.executeQuery();

            while (rset.next()) {
                tarefas.add(mapTarefaFromResultSet(rset));
            }

            return tarefas;
        } catch (Exception e) {
            e.printStackTrace();
            return tarefas;
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Tarefa> listaTarefasByFuncionarioId(Long funcionarioId) {
        String sql = "SELECT * FROM tarefa WHERE funcionario_id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        List<Tarefa> tarefas = new ArrayList<>();

        try {
            conn = DataBaseConnection.createConnection();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setLong(1, funcionarioId);

            ResultSet rset = pstm.executeQuery();

            while (rset.next()) {
                tarefas.add(mapTarefaFromResultSet(rset));
            }

            return tarefas;
        } catch (Exception e) {
            e.printStackTrace();
            return tarefas;
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateStatusTarefa(Long tarefaId, StatusTarefa status) {
        String sql = "UPDATE tarefa SET status = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = DataBaseConnection.createConnection();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, status.toString());
            pstm.setLong(2, tarefaId);
            pstm.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<ListTarefaRelatorioDto> buscarTarefasPendentes() throws SQLException {
        String sql = """
                    SELECT t.id AS tarefa_id, t.nome, t.descricao, t.status,
                           u.nome AS funcionario_nome
                    FROM tarefa t
                    INNER JOIN usuario u ON t.funcionario_id = u.id
                    WHERE t.status = 'PENDENTE'
                """;

        List<ListTarefaRelatorioDto> tarefas = new ArrayList<>();

        try (
                Connection conn = DataBaseConnection.createConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                ListTarefaRelatorioDto dto = new ListTarefaRelatorioDto();
                dto.setTarefaId(rs.getInt("tarefa_id"));
                dto.setNome(rs.getString("nome"));
                dto.setDescricao(rs.getString("descricao"));
                dto.setStatus(rs.getString("status"));
                dto.setFuncionarioNome(rs.getString("funcionario_nome"));
                tarefas.add(dto);
            }
        }
        return tarefas;
    }


    private Tarefa mapTarefaFromResultSet(ResultSet rset) throws Exception {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(rset.getLong("id"));
        tarefa.setNome(rset.getString("nome"));
        tarefa.setDescricao(rset.getString("descricao"));
        tarefa.setStatus(StatusTarefa.valueOf(rset.getString("status")));
        tarefa.setFuncionarioId(rset.getLong("funcionario_id"));
        tarefa.setSupervisorId(rset.getLong("supervisor_id"));
        return tarefa;
    }
}
