package org.sistema.acompanhamento.tarefas.model;

import org.sistema.acompanhamento.tarefas.model.dto.CadastroTarefaDto;
import org.sistema.acompanhamento.tarefas.model.enums.StatusTarefa;

public class Tarefa {

    private Long id;
    private String nome;
    private String descricao;
    private StatusTarefa status;
    private Long supervisorId;
    private Long funcionarioId;

    public Tarefa() {
    }

    public Tarefa(String nome, String descricao, Long supervisorId, Long funcionarioId) {
        this.nome = nome;
        this.descricao = descricao;
        this.status = StatusTarefa.PENDENTE;
        this.supervisorId = supervisorId;
        this.funcionarioId = funcionarioId;
    }

    public Tarefa(Long supervisorId, CadastroTarefaDto cadastroTarefaDto) {
        this.nome = cadastroTarefaDto.getNome();
        this.descricao = cadastroTarefaDto.getDescricao();
        this.status = StatusTarefa.PENDENTE;
        this.supervisorId = supervisorId;
        this.funcionarioId = cadastroTarefaDto.getFuncionarioId();
    }

    public void atualizaStatus(StatusTarefa novoStatus) {
        this.status = novoStatus;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public StatusTarefa getStatus() {
        return status;
    }

    public void setStatus(StatusTarefa status) {
        this.status = status;
    }

    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public Long getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }
}
