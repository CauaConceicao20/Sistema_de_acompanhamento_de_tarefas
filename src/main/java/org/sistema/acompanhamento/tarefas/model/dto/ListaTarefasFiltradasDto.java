package org.sistema.acompanhamento.tarefas.model.dto;

import org.sistema.acompanhamento.tarefas.model.Tarefa;

public class ListaTarefasFiltradasDto {
    private String nome;
    private String descricao;
    private String nomeFuncionario;

    public ListaTarefasFiltradasDto(Tarefa tarefa, String nomeFuncionario) {
        this.nome = tarefa.getNome();
        this.descricao = tarefa.getDescricao();
        this.nomeFuncionario = nomeFuncionario;
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

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }
}
