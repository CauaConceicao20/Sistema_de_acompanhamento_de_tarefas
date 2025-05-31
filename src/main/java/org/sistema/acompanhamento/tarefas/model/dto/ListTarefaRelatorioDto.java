package org.sistema.acompanhamento.tarefas.model.dto;

public class ListTarefaRelatorioDto {
    private int tarefaId;
    private String nome;
    private String descricao;
    private String status;
    private String funcionarioNome;

    public int getTarefaId() {
        return tarefaId;
    }

    public void setTarefaId(int tarefaId) {
        this.tarefaId = tarefaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFuncionarioNome() {
        return funcionarioNome;
    }

    public void setFuncionarioNome(String funcionarioNome) {
        this.funcionarioNome = funcionarioNome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


// getters e setters
}