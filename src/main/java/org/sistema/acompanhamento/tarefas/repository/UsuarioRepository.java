package org.sistema.acompanhamento.tarefas.repository;

import org.sistema.acompanhamento.tarefas.model.Funcionario;
import org.sistema.acompanhamento.tarefas.model.Gerente;
import org.sistema.acompanhamento.tarefas.model.Supervisor;
import org.sistema.acompanhamento.tarefas.model.Usuario;
import org.sistema.acompanhamento.tarefas.model.enums.Cargo;
import org.sistema.acompanhamento.tarefas.util.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    public List<Usuario> listarTodosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuario";

        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = DataBaseConnection.createConnection();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rset = pstm.executeQuery();

            while (rset.next()) {
                Usuario usuario = mapearUsuarioDoResultSet(rset);
                usuarios.add(usuario);
            }
            return usuarios;
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

    public List<Funcionario> listarTodosFuncionarios() {
        String sql = "SELECT * FROM usuario WHERE cargo = 'FUNCIONARIO'";

        List<Funcionario> usuariosFuncionarios = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;
        try {
            conn = DataBaseConnection.createConnection();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while (rset.next()) {
                Funcionario usuarioFuncionario = (Funcionario) mapearUsuarioDoResultSet(rset);
                usuariosFuncionarios.add(usuarioFuncionario);
            }
            return usuariosFuncionarios;

        } catch (Exception e) {
            e.printStackTrace();
            return usuariosFuncionarios;
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (rset != null) {
                    rset.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Supervisor> listarTodosSupervisores() {
        String sql = "SELECT * FROM usuario WHERE cargo = 'SUPERVISOR'";

        List<Supervisor> usuariosSupervisores = new ArrayList<>();

        try (Connection conn = DataBaseConnection.createConnection(); PreparedStatement pstm = conn.prepareStatement(sql); ResultSet rset = pstm.executeQuery()) {

            while (rset.next()) {
                Supervisor usuarioSupervisor = (Supervisor) mapearUsuarioDoResultSet(rset);
                usuariosSupervisores.add(usuarioSupervisor);
            }
            return usuariosSupervisores;

        } catch (Exception e) {
            e.printStackTrace();
            return usuariosSupervisores;
        }
    }

    public List<Gerente> listarTodosGerentes() {
        String sql = "SELECT * FROM usuario WHERE cargo = 'GERENTE'";

        List<Gerente> usuariosGerentes = new ArrayList<>();

        try (Connection conn = DataBaseConnection.createConnection(); PreparedStatement pstm = conn.prepareStatement(sql); ResultSet rset = pstm.executeQuery()) {

            while (rset.next()) {
                Gerente usuarioGerente = (Gerente) mapearUsuarioDoResultSet(rset);
                usuariosGerentes.add(usuarioGerente);
            }
            return usuariosGerentes;

        } catch (Exception e) {
            e.printStackTrace();
            return usuariosGerentes;
        }
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = DataBaseConnection.createConnection();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, email);
            ResultSet rset = pstm.executeQuery();

            Usuario usuario = null;
            if (rset.next()) {
                usuario = mapearUsuarioDoResultSet(rset);
            }
            return usuario;

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

    public List<Funcionario> listarFuncionariosComTarefas() {
        String sql = "SELECT u.* FROM usuario u INNER JOIN tarefa t ON u.id = t.funcionario_id WHERE u.cargo = 'FUNCIONARIO'";
        List<Funcionario> funcionarios = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;
        try {
            conn = DataBaseConnection.createConnection();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while (rset.next()) {
                funcionarios.add((Funcionario) mapearUsuarioDoResultSet(rset));
            }
            return funcionarios;

        } catch (Exception e) {
            e.printStackTrace();
            return funcionarios;
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (rset != null) {
                    rset.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Usuario buscarPorId(Long id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = DataBaseConnection.createConnection();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setLong(1, id);
            ResultSet rset = pstm.executeQuery();

            Usuario usuario = null;
            if (rset.next()) {
                usuario = mapearUsuarioDoResultSet(rset);
            }
            return usuario;

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

    public List<Funcionario> listarFuncionariosSemTarefasPendentes() {
        String sql = "SELECT u.*\n" +
                "FROM usuario u\n" +
                "WHERE u.cargo = 'FUNCIONARIO'\n" +
                "  AND NOT EXISTS (\n" +
                "    SELECT 1\n" +
                "    FROM tarefa t\n" +
                "    WHERE t.funcionario_id = u.id\n" +
                "      AND t.status = 'PENDENTE'\n" +
                "  );";

        List<Funcionario> funcionarios = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {
            conn = DataBaseConnection.createConnection();
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while (rset.next()) {
                Funcionario funcionario = (Funcionario) mapearUsuarioDoResultSet(rset);
                funcionarios.add(funcionario);
            }

            return funcionarios;
        } catch (Exception e) {
            e.printStackTrace();
            return funcionarios;
        } finally {
            try {
                if (pstm != null) pstm.close();
                if (rset != null) rset.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Funcionario> listarFuncionariosPorSupervisorId(Long supervisorId) {
        String sql = "SELECT * FROM usuario WHERE cargo = 'FUNCIONARIO' AND supervisor_id = ?";

        List<Funcionario> funcionarios = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {
            conn = DataBaseConnection.createConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setLong(1, supervisorId);
            rset = pstm.executeQuery();

            while (rset.next()) {
                Funcionario funcionario = (Funcionario) mapearUsuarioDoResultSet(rset);
                funcionarios.add(funcionario);
            }

            return funcionarios;
        } catch (Exception e) {
            e.printStackTrace();
            return funcionarios;
        } finally {
            try {
                if (pstm != null) pstm.close();
                if (rset != null) rset.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Usuario mapearUsuarioDoResultSet(ResultSet rset) throws Exception {
        String cargoStr = rset.getString("cargo");
        Cargo cargo = Cargo.valueOf(cargoStr);

        Usuario usuario;
        switch (cargo) {
            case GERENTE:
                usuario = new Gerente();
                break;
            case SUPERVISOR:
                usuario = new Supervisor();
                break;
            case FUNCIONARIO:
                usuario = new Funcionario();
                break;
            default:
                throw new IllegalArgumentException("Cargo desconhecido: " + cargoStr);
        }

        usuario.setId(rset.getLong("id"));
        usuario.setNome(rset.getString("nome"));
        usuario.setEmail(rset.getString("email"));
        usuario.setSenha(rset.getString("senha"));
        usuario.setCpf(rset.getString("cpf"));
        usuario.setTelefone(rset.getString("telefone"));
        usuario.setCargo(cargo);

        return usuario;
    }
}