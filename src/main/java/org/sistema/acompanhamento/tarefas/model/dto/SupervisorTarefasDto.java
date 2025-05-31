package org.sistema.acompanhamento.tarefas.model.dto;

import java.util.List;

public class SupervisorTarefasDto {
    private int supervisorId;
    private String supervisorNome;
    private List<ListTarefaRelatorioDto> tarefasCadastradas;

    public SupervisorTarefasDto() {

    }

    public int getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(int supervisorId) {
        this.supervisorId = supervisorId;
    }

    public String getSupervisorNome() {
        return supervisorNome;
    }

    public void setSupervisorNome(String supervisorNome) {
        this.supervisorNome = supervisorNome;
    }

    public List<ListTarefaRelatorioDto> getTarefasCadastradas() {
        return tarefasCadastradas;
    }

    public void setTarefasCadastradas(List<ListTarefaRelatorioDto> tarefasCadastradas) {
        this.tarefasCadastradas = tarefasCadastradas;
    }
}
