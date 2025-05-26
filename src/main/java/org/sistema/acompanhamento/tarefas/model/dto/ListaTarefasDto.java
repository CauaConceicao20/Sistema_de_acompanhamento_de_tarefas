package org.sistema.acompanhamento.tarefas.model.dto;

import org.sistema.acompanhamento.tarefas.model.Tarefa;
import org.sistema.acompanhamento.tarefas.model.enums.StatusTarefa;

public class ListaTarefasDto {
    private Long id;
    private String nome;
    private String descricao;
    private StatusTarefa status;
    private Long supervisorId;
    private Long funcionarioId;

    public ListaTarefasDto(Tarefa tarefa) {
        this.id = tarefa.getId();
        this.nome = tarefa.getNome();
        this.descricao = tarefa.getDescricao();
        this.status = tarefa.getStatus();
        this.supervisorId = tarefa.getSupervisorId();
        this.funcionarioId = tarefa.getFuncionarioId();

    }
}
