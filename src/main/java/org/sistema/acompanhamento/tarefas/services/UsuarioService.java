package org.sistema.acompanhamento.tarefas.services;

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
        return usuarioRepository.findAllUsuarios();
    }

    public List<Funcionario> listaFuncionarios() {
        return usuarioRepository.findAllFuncionarios();
    }

    public List<Supervisor> listaSupervisores() {
        return usuarioRepository.findAllSupervisores();
    }

    public  List<Funcionario> listaFuncionariosDeUmSupervisorEspecifico(Long id) {
        return usuarioRepository.findAllFuncionariosBySupervisorId(id);
    }

    public List<Funcionario> listaFuncionariosComTarefas() {
        return usuarioRepository.findAllEmployeesWithTasks();
    }

    public Usuario buscaPorId(Long id) {
        return usuarioRepository.findById(id);
    }
}