package org.sistema.acompanhamento.tarefas.model.dto;

import org.sistema.acompanhamento.tarefas.model.enums.StatusTarefa;

public class CadastroTarefaDto {
    private String nome;
    private String descricao;
    private Long funcionarioId;

    public CadastroTarefaDto() {
    }

    public CadastroTarefaDto(String nome, String descricao, Long funcionarioId) {
        this.nome = nome;
        this.descricao = descricao;
        this.funcionarioId = funcionarioId;
        validar();
    }

    public void validar() {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da tarefa é obrigatório.");
        }
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição da tarefa é obrigatória.");
        }
        if (funcionarioId == null || funcionarioId < 1) {
            throw new IllegalArgumentException("Funcionário é obrigatório.");
        }
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

    public Long getFuncionarioId() {
        return funcionarioId;
    }
    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }
}
