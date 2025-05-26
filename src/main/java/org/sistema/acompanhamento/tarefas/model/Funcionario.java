package org.sistema.acompanhamento.tarefas.model;

import org.sistema.acompanhamento.tarefas.model.enums.Cargo;
import org.sistema.acompanhamento.tarefas.model.enums.StatusTarefa;

public class Funcionario extends Usuario {

    public Funcionario() {
        super();
    }

    public Funcionario(String nome, String email, String senha, String cpf, String telefone, Cargo cargo) {
        super(nome, email, senha, cpf, telefone, cargo);
    }

    public void marcaTarefaComoConcluida(Tarefa tarefa) {
        tarefa.atualizaStatus(StatusTarefa.CONCLUIDA);
    }
}