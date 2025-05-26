package org.sistema.acompanhamento.tarefas.repository;

import org.sistema.acompanhamento.tarefas.model.Tarefa;
import org.sistema.acompanhamento.tarefas.model.enums.StatusTarefa;
import org.sistema.acompanhamento.tarefas.util.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    public List<Tarefa> listaTarefasConcluidas(Long supervisorId) {
        String sql = "SELECT * FROM tarefa WHERE supervisor_id = ? AND status = 'CONCLUIDA'";

        Connection conn = null;
        PreparedStatement pstm = null;

        List<Tarefa> tarefas = new ArrayList<>();

        try {
            conn = DataBaseConnection.createConnection();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setLong(1, supervisorId);

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

    public List<Tarefa> listaTarefasPendentes(Long supervisorId) {
        String sql = "SELECT * FROM tarefa WHERE supervisor_id = ? AND status = 'PENDENTE'";

        Connection conn = null;
        PreparedStatement pstm = null;

        List<Tarefa> tarefas = new ArrayList<>();

        try {
            conn = DataBaseConnection.createConnection();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setLong(1, supervisorId);

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
