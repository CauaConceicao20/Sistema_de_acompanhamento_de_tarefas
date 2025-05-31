package org.sistema.acompanhamento.tarefas.model.dto;

import java.util.List;

public class RelatorioTarefasCadastradasSupervisorDto {
    private String relatorio;
    private int totalSupervisores;
    private List<SupervisorTarefasDto> dados;

    public String getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    public int getTotalSupervisores() {
        return totalSupervisores;
    }

    public void setTotalSupervisores(int totalSupervisores) {
        this.totalSupervisores = totalSupervisores;
    }

    public List<SupervisorTarefasDto> getDados() {
        return dados;
    }

    public void setDados(List<SupervisorTarefasDto> dados) {
        this.dados = dados;
    }
}
