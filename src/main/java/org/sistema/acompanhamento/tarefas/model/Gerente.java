package org.sistema.acompanhamento.tarefas.model;

import org.sistema.acompanhamento.tarefas.model.enums.Cargo;

public class Gerente extends Usuario {

    public Gerente() {
        super();
    }

    public Gerente(String nome, String email, String senha, String cpf, String telefone, Cargo cargo) {
        super(nome, email, senha, cpf, telefone, cargo);
    }


}
