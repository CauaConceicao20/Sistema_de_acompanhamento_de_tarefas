package org.sistema.acompanhamento.tarefas.services;

import org.sistema.acompanhamento.tarefas.exception.UsuarioNotFoundException;
import org.sistema.acompanhamento.tarefas.model.Funcionario;
import org.sistema.acompanhamento.tarefas.model.Supervisor;
import org.sistema.acompanhamento.tarefas.model.Usuario;
import org.sistema.acompanhamento.tarefas.repository.UsuarioRepository;

import java.util.List;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public List<Usuario> listaUsuarios() {
        List<Usuario> usuarios = usuarioRepository.listarTodosUsuarios();
        if (usuarios == null || usuarios.isEmpty()) {
            throw new UsuarioNotFoundException("Nenhum usuário encontrado");
        }
        return usuarios;
    }

    public List<Funcionario> listaFuncionarios() {
        List<Funcionario> funcionarios = usuarioRepository.listarTodosFuncionarios();
        if (funcionarios == null || funcionarios.isEmpty()) {
            throw new UsuarioNotFoundException("Nenhum funcionário encontrado");
        }
        return funcionarios;
    }

    public List<Supervisor> listaSupervisores() {
        List<Supervisor> supervisores = usuarioRepository.listarTodosSupervisores();
        if (supervisores == null || supervisores.isEmpty()) {
            throw new UsuarioNotFoundException("Nenhum supervisor encontrado");
        }
        return supervisores;
    }

    public List<Funcionario> listaFuncionariosDeUmSupervisorEspecifico(Long id) {
        List<Funcionario> funcionarios = usuarioRepository.listarFuncionariosPorSupervisorId(id);
        if (funcionarios == null || funcionarios.isEmpty()) {
            throw new UsuarioNotFoundException("Nenhum funcionário encontrado para o supervisor");
        }
        return funcionarios;
    }

    public List<Funcionario> listaFuncionariosComTarefas() {
        List<Funcionario> funcionarios = usuarioRepository.listarFuncionariosComTarefas();
        if (funcionarios == null || funcionarios.isEmpty()) {
            throw new UsuarioNotFoundException("Nenhum funcionário com tarefas encontrado");
        }
        return funcionarios;
    }

    public Usuario buscaPorId(Long id) {
        Usuario usuario = usuarioRepository.buscarPorId(id);
        if (usuario == null) {
            throw new UsuarioNotFoundException("Usuário não encontrado");
        }
        return usuario;
    }
}