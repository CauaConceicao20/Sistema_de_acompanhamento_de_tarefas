package org.sistema.acompanhamento.tarefas.model;

import org.sistema.acompanhamento.tarefas.model.enums.Cargo;

public class Supervisor extends Usuario {

    public Supervisor() {
        super();
    }

    public Supervisor(String nome, String email, String senha, String cpf, String telefone, Cargo cargo) {
        super(nome, email, senha, cpf, telefone, cargo);
    }
}
