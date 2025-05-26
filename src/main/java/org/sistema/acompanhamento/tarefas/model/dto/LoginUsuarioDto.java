package org.sistema.acompanhamento.tarefas.model.dto;

public class LoginUsuarioDto {
    private String email;
    private String senha;

    public LoginUsuarioDto(String email, String senha) {
        this.email = email;
        this.senha = senha;
        validar();
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    private void validar() {
        if (email == null || email.isBlank() || senha == null || senha.isBlank()) {
            throw new IllegalArgumentException("Email e senha são obrigatórios.");
        }
    }
}
