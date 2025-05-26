package org.sistema.acompanhamento.tarefas.services;

import org.sistema.acompanhamento.tarefas.model.Funcionario;
import org.sistema.acompanhamento.tarefas.model.Gerente;
import org.sistema.acompanhamento.tarefas.model.Supervisor;
import org.sistema.acompanhamento.tarefas.model.Usuario;
import org.sistema.acompanhamento.tarefas.model.dto.CadastroUsuarioDto;
import org.sistema.acompanhamento.tarefas.repository.UsuarioRepository;

import java.util.List;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.createUsuario(usuario);
    }

    public Usuario descobreCargoDeUsuario(CadastroUsuarioDto dto) {
        switch (dto.getCargo()) {
            case FUNCIONARIO:
                return new Funcionario(dto.getNome(), dto.getEmail(), dto.getSenha(),
                        dto.getCpf(), dto.getTelefone(), dto.getCargo());
            case GERENTE:
                return new Gerente(dto.getNome(), dto.getEmail(), dto.getSenha(),
                        dto.getCpf(), dto.getTelefone(), dto.getCargo());
            case SUPERVISOR:
                return new Supervisor(dto.getNome(), dto.getEmail(), dto.getSenha(),
                        dto.getCpf(), dto.getTelefone(), dto.getCargo());
            default:
                System.out.println("VAI LANCAR EXCEÇÃO DPS");
                break;
        }
        return null;
    }

    public List<Usuario> listaUsuarios() {
        return usuarioRepository.findAllUsuarios();
    }

    public List<Funcionario> listaFuncionarios() {
        return usuarioRepository.findAllFuncionarios();
    }

    public List<Funcionario> listaFuncionariosComTarefas() {
        return usuarioRepository.findAllEmployeesWithTasks();
    }
}