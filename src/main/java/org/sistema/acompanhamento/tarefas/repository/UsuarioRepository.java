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

    /*
    public Usuario createUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email, senha, cpf, telefone, cargo) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = DataBaseConnection.createConnection();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setString(1, usuario.getNome());
            pstm.setString(2, usuario.getEmail());
            pstm.setString(3, usuario.getSenha());
            pstm.setString(4, usuario.getCpf());
            pstm.setString(5, usuario.getTelefone());
            pstm.setString(6, usuario.getCargo().toString());

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
        return usuario;
    }
*/
    public List<Usuario> findAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "SELECT * FROM usuario";

        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = DataBaseConnection.createConnection();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rset = pstm.executeQuery();

            while (rset.next()) {
                Usuario usuario = mapUsuarioFromResultSet(rset);
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

    public List<Funcionario> findAllFuncionarios() {
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
                Funcionario usuarioFuncionario = (Funcionario) mapUsuarioFromResultSet(rset);
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

    public List<Supervisor> findAllSupervisores() {
        String sql = "SELECT * FROM usuario WHERE cargo = 'SUPERVISOR'";

        List<Supervisor> usuariosSupervisores = new ArrayList<>();

        try (Connection conn = DataBaseConnection.createConnection(); PreparedStatement pstm = conn.prepareStatement(sql); ResultSet rset = pstm.executeQuery()) {

            while (rset.next()) {
                Supervisor usuarioSupervisor = (Supervisor) mapUsuarioFromResultSet(rset);
                usuariosSupervisores.add(usuarioSupervisor);
            }
            return usuariosSupervisores;

        } catch (Exception e) {
            e.printStackTrace();
            return usuariosSupervisores;
        }
    }

    public List<Gerente> findAllGerentes() {
        String sql = "SELECT * FROM usuario WHERE cargo = 'GERENTE'";

        List<Gerente> usuariosGerentes = new ArrayList<>();

        try (Connection conn = DataBaseConnection.createConnection(); PreparedStatement pstm = conn.prepareStatement(sql); ResultSet rset = pstm.executeQuery()) {

            while (rset.next()) {
                Gerente usuarioGerente = (Gerente) mapUsuarioFromResultSet(rset);
                usuariosGerentes.add(usuarioGerente);
            }
            return usuariosGerentes;

        } catch (Exception e) {
            e.printStackTrace();
            return usuariosGerentes;
        }
    }

    public Usuario findByEmailUsuarios(String email) {
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
                usuario = mapUsuarioFromResultSet(rset);
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

    public List<Funcionario> findAllEmployeesWithTasks() {
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
                funcionarios.add((Funcionario) mapUsuarioFromResultSet(rset));
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

    public Usuario findById(Long id) {
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
                usuario = mapUsuarioFromResultSet(rset);
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

    public List<Funcionario> findAllFuncionariosSemTarefasPendentes() {
       String sql = "SELECT u.* FROM usuario u LEFT JOIN tarefa t ON u.id = t.funcionario_id WHERE u.cargo = 'FUNCIONARIO' AND (t.status IS NULL OR t.status != 'PENDENTE')";

        List<Funcionario> funcionarios = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {
            conn = DataBaseConnection.createConnection();
            pstm = conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while (rset.next()) {
                Funcionario funcionario = (Funcionario) mapUsuarioFromResultSet(rset);
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

    public List<Funcionario> findAllFuncionariosBySupervisorId(Long supervisorId) {
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
                Funcionario funcionario = (Funcionario) mapUsuarioFromResultSet(rset);
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

    private Usuario mapUsuarioFromResultSet(ResultSet rset) throws Exception {
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