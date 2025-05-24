package org.sistema.acompanhamento.tarefas.repository;

import org.sistema.acompanhamento.tarefas.model.Usuario;

public class LoginService {

    UsuarioRepository usuarioRepository = new UsuarioRepository();

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
        else if (usuario == null) {
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
