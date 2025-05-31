package org.sistema.acompanhamento.tarefas.model.dto;

import java.util.List;

public class RelatorioTarefasPendentesDto {

    private String relatorio;
    private int totalTarefas;
    private List<ListTarefaRelatorioDto> tarefas;

    public String getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    public int getTotalTarefas() {
        return totalTarefas;
    }

    public void setTotalTarefas(int totalTarefas) {
        this.totalTarefas = totalTarefas;
    }

    public List<ListTarefaRelatorioDto> getTarefas() {
        return tarefas;
    }
    public void setTarefas(List<ListTarefaRelatorioDto> tarefas) {
        this.tarefas = tarefas;
    }
}