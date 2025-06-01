package org.sistema.acompanhamento.tarefas.services;

import org.sistema.acompanhamento.tarefas.exception.RelatorioNotFoundException;
import org.sistema.acompanhamento.tarefas.model.Funcionario;
import org.sistema.acompanhamento.tarefas.model.dto.*;
import org.sistema.acompanhamento.tarefas.repository.RelatorioRepository;
import org.sistema.acompanhamento.tarefas.repository.TarefaRepository;
import org.sistema.acompanhamento.tarefas.repository.UsuarioRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RelatorioService {

    private final UsuarioRepository usuarioRepository;
    private final RelatorioRepository relatorioRepository;
    private final TarefaRepository tarefaRepository;

    public RelatorioService() {
        this.usuarioRepository = new UsuarioRepository();
        this.relatorioRepository = new RelatorioRepository();
        this.tarefaRepository = new TarefaRepository();
    }

    public RelatorioTarefasCadastradasSupervisorDto gerarRelatorioPorSupervisor(int supervisorId) throws SQLException {
        List<Map<String, Object>> registros = relatorioRepository.buscarDadosDoSupervisor(supervisorId);

        if (registros.isEmpty()) {
            throw new RelatorioNotFoundException("Nenhum dado encontrado para o supervisor informado.");
        }

        Map<String, Object> primeiro = registros.get(0);
        SupervisorTarefasDto supervisor = new SupervisorTarefasDto();
        supervisor.setSupervisorId((Integer) primeiro.get("supervisor_id"));
        supervisor.setSupervisorNome((String) primeiro.get("supervisor_nome"));
        supervisor.setTarefasCadastradas(new ArrayList<>());

        for (Map<String, Object> registro : registros) {
            Object tarefaIdObj = registro.get("tarefa_id");
            if (tarefaIdObj != null) {
                ListTarefaRelatorioDto tarefa = new ListTarefaRelatorioDto();
                tarefa.setTarefaId((Integer) tarefaIdObj);
                tarefa.setNome((String) registro.get("tarefa_nome"));
                tarefa.setDescricao((String) registro.get("descricao"));
                tarefa.setFuncionarioNome((String) registro.get("funcionario_nome"));
                tarefa.setStatus((String) registro.get("status"));
                supervisor.getTarefasCadastradas().add(tarefa);
            }
        }

        RelatorioTarefasCadastradasSupervisorDto relatorio = new RelatorioTarefasCadastradasSupervisorDto();
        relatorio.setRelatorio("Tarefas cadastradas por supervisores");
        relatorio.setTotalSupervisores(1);
        relatorio.setDados(List.of(supervisor));

        return relatorio;
    }

    public RelatorioTarefasPendentesDto gerarRelatorioDeTarefasPendentes() throws SQLException {
        List<ListTarefaRelatorioDto> tarefasPendentes = tarefaRepository.buscarTarefasPendentesRelatorio();

        RelatorioTarefasPendentesDto relatorio = new RelatorioTarefasPendentesDto();
        relatorio.setRelatorio("Tarefas pendentes");
        relatorio.setTotalTarefas(tarefasPendentes.size());
        relatorio.setTarefas(tarefasPendentes);

        return relatorio;
    }

    public RelatorioFuncionariosSemPendenciasDto gerarRelatorioFuncionariosSemTarefas() throws SQLException {
        List<ListaUsuariosDto> funcionariosDto = new ArrayList<>();
        List<Funcionario> funcionarios = usuarioRepository.listarFuncionariosSemTarefasPendentes();

        for(Funcionario funcionario : funcionarios) {
            ListaUsuariosDto funcionarioDto = new ListaUsuariosDto(funcionario, null);
            funcionariosDto.add(funcionarioDto);
        }

        RelatorioFuncionariosSemPendenciasDto relatorio = new RelatorioFuncionariosSemPendenciasDto();
        relatorio.setRelatorio("Funcionários sem tarefas pendentes");
        relatorio.setTotalFuncionarios(funcionarios.size());
        relatorio.setFuncionarios(funcionariosDto);

        return relatorio;
    }
}