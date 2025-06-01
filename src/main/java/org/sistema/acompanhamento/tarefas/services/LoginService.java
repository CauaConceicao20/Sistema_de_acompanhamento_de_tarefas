package org.sistema.acompanhamento.tarefas.services;

import org.sistema.acompanhamento.tarefas.model.Usuario;
import org.sistema.acompanhamento.tarefas.repository.UsuarioRepository;
import org.sistema.acompanhamento.tarefas.exception.LoginException;

public class LoginService {

    private final UsuarioRepository usuarioRepository;

    public LoginService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public Usuario login(String email, String senha) {
        return validaCredencias(email, senha);
    }

    private Usuario validaCredencias(String email, String senha) {
        Usuario usuario = usuarioRepository.buscarUsuarioPorEmail(email);

        if (usuario == null || senha == null) {
            throw new LoginException("Usuário ou senha inválidos");
        }

        if (!usuario.getSenha().equals(senha)) {
            throw new LoginException("senha inválida");
        }

        return usuario;
    }
}