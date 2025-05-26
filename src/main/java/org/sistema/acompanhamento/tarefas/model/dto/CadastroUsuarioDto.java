package org.sistema.acompanhamento.tarefas.model.dto;

import org.sistema.acompanhamento.tarefas.model.Usuario;
import org.sistema.acompanhamento.tarefas.model.enums.Cargo;

import java.util.regex.Pattern;

public class CadastroUsuarioDto {

    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String telefone;
    private Cargo cargo;

    public CadastroUsuarioDto(String nome, String email, String senha, String cpf, String telefone, Long supervisorId, Cargo cargo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.telefone = telefone;
        this.cargo = cargo;
        validar();
    }

    public void validar() {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }

        if (email == null || !Pattern.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-z]{2,}$", email)) {
            throw new IllegalArgumentException("Email inválido.");
        }

        if (senha == null || senha.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 6 caracteres.");
        }

        if (cpf == null || !Pattern.matches("^\\d{11}$", cpf)) {
            throw new IllegalArgumentException("CPF inválido. Deve conter exatamente 11 dígitos numéricos.");
        }

        if (telefone == null || !Pattern.matches("^\\d{10,11}$", telefone)) {
            throw new IllegalArgumentException("Telefone inválido. Deve conter 10 ou 11 dígitos numéricos.");
        }

        if (cargo == null) {
            throw new IllegalArgumentException("Cargo não pode ser nulo.");
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}
