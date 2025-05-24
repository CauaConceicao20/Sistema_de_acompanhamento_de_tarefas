package org.sistema.acompanhamento.tarefas.model;

import org.sistema.acompanhamento.tarefas.model.enums.Cargo;
import org.sistema.acompanhamento.tarefas.model.enums.StatusTarefa;

public class Funcionario extends Usuario {

    private Long supervisorId;

    public Funcionario() {
        super();
    }

    public Funcionario(String nome, String email, String senha, String cpf, String telefone, Cargo cargo, Long supervisorId) {
        super(nome, email, senha, cpf, telefone, cargo);
        this.supervisorId = supervisorId;
    }

    public void marcaTarefaComoConcluida(Tarefa tarefa) {
        tarefa.atualizaStatus(StatusTarefa.CONCLUIDA);
    }

    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long supervisorId) {
        this.supervisorId = supervisorId;
    }
}
