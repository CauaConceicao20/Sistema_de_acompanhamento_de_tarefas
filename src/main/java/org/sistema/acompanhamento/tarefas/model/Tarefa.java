package org.sistema.acompanhamento.tarefas.model;

import org.sistema.acompanhamento.tarefas.model.enums.StatusTarefa;

public class Tarefa {

    private String nome;
    private String descricao;
    private StatusTarefa status;
    private Long supervisorId;
    private Long funcionarioId;


    public Tarefa(String nome, String descricao, StatusTarefa status, Long supervisorId, Long funcionarioId) {
        this.nome = nome;
        this.descricao = descricao;
        this.status = status;
        this.supervisorId = supervisorId;
        this.funcionarioId = funcionarioId;
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
