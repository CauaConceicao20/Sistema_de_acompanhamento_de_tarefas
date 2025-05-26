package org.sistema.acompanhamento.tarefas.services;

import org.sistema.acompanhamento.tarefas.model.Usuario;
import org.sistema.acompanhamento.tarefas.repository.UsuarioRepository;

public class LoginService {

   private final UsuarioRepository usuarioRepository;

    public LoginService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public Usuario login(String email, String senha) {
        Usuario usuario = validaCredencias(email, senha);
        if(usuario == null) {
            System.out.println("usuario igual a null");
            return null;
        }
        return usuario;
    }

    private Usuario validaCredencias(String email, String senha) {

        Usuario usuario = usuarioRepository.findByEmailUsuarios(email);

        if(usuario == null || senha == null) {
            System.out.println("Erro ao logar");
            return null;
        }

        else if (!usuario.getSenha().equals(senha)) {
            System.out.println("Erro ao logar");
            return null;
        }

        return usuario;
    }

}
