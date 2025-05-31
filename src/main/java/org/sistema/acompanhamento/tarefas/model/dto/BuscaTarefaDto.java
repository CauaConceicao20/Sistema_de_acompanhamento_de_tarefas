package org.sistema.acompanhamento.tarefas.model.dto;

import org.sistema.acompanhamento.tarefas.model.Tarefa;

public class BuscaTarefaDto {
    Long funcionarioId;

    public BuscaTarefaDto(Tarefa tarefa) {
        this.funcionarioId = tarefa.getFuncionarioId();
    }

    public Long getFuncionarioId() {
        return funcionarioId;
    }
    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }
}
