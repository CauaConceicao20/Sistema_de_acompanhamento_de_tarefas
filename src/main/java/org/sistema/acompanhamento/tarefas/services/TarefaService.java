package org.sistema.acompanhamento.tarefas.services;

import org.sistema.acompanhamento.tarefas.exception.TarefaNotFoundException;
import org.sistema.acompanhamento.tarefas.model.Tarefa;
import org.sistema.acompanhamento.tarefas.model.enums.StatusTarefa;
import org.sistema.acompanhamento.tarefas.repository.TarefaRepository;

import java.util.List;

public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService() {
        this.tarefaRepository = new TarefaRepository();
    }

    public Tarefa criaTarefa(Tarefa tarefa) {
        return tarefaRepository.criarTarefa(tarefa);
    }

    public Tarefa buscaTarefaPorId(Long id) {
        Tarefa tarefa = tarefaRepository.buscarTarefaPorId(id);
        if (tarefa == null) {
            throw new TarefaNotFoundException("Tarefa não encontrada");
        }
        return tarefa;
    }

    public List<Tarefa> listaTarefas() {
        List<Tarefa> tarefas = tarefaRepository.listarTarefas();
        if (tarefas == null || tarefas.isEmpty()) {
            throw new TarefaNotFoundException("Nenhuma tarefa encontrada");
        }
        return tarefas;
    }

    public List<Tarefa> listaTarefasDeFuncionario(Long funcionarioId) {
        List<Tarefa> tarefas = tarefaRepository.listarTarefasPorFuncionarioId(funcionarioId);
        if (tarefas == null || tarefas.isEmpty()) {
            throw new TarefaNotFoundException("Nenhuma tarefa encontrada para o funcionário");
        }
        return tarefas;
    }

    public List<Tarefa> listaTarefasConcluidas(Long supervisorId) {
        List<Tarefa> tarefas = tarefaRepository.listarTarefasConcluidas(supervisorId);
        if (tarefas == null || tarefas.isEmpty()) {
            throw new TarefaNotFoundException("Nenhuma tarefa concluída encontrada");
        }
        return tarefas;
    }

    public List<Tarefa> listaTarefasPendentes(Long supervisorId) {
        List<Tarefa> tarefas = tarefaRepository.listarTarefasPendentes(supervisorId);
        if (tarefas == null || tarefas.isEmpty()) {
            throw new TarefaNotFoundException("Nenhuma tarefa pendente encontrada");
        }
        return tarefas;
    }

    public void atualizaStatusTarefaPraConcluida(Long id) {
        Tarefa tarefa = buscaTarefaPorId(id);
        tarefaRepository.atualizarStatusTarefa(id, StatusTarefa.CONCLUIDA);
    }

    public void atualizaStatusTarefaPraPendente(Long id) {
        Tarefa tarefa = buscaTarefaPorId(id);
        tarefaRepository.atualizarStatusTarefa(id, StatusTarefa.PENDENTE);
    }
}