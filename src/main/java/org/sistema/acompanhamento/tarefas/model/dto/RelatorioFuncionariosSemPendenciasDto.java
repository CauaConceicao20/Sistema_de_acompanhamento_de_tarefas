package org.sistema.acompanhamento.tarefas.model.dto;

import java.util.List;

public class RelatorioFuncionariosSemPendenciasDto {

    private String relatorio;
    private int totalFuncionarios;
    private List<ListaUsuariosDto> funcionarios;

    public String getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    public int getTotalFuncionarios() {
        return totalFuncionarios;
    }

    public void setTotalFuncionarios(int totalFuncionarios) {
        this.totalFuncionarios = totalFuncionarios;
    }

    public List<ListaUsuariosDto> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<ListaUsuariosDto> funcionarios) {
        this.funcionarios = funcionarios;
    }
}
