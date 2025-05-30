package org.sistema.acompanhamento.tarefas.services;

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
        return tarefaRepository.createTarefa(tarefa);
    }

    public Tarefa buscaTarefaPorId(Long id) {
        return tarefaRepository.buscaTarefaPorId(id);
    }

    public List<Tarefa> listaTarefas() {
        return tarefaRepository.listaTarefas();
    }

    public List<Tarefa> listaTarefasDeFuncionario(Long funcionarioId) {
        return tarefaRepository.listaTarefasByFuncionarioId(funcionarioId);
    }

    public List<Tarefa> listaTarefasConcluidas(Long supervisorId) {
        return tarefaRepository.listaTarefasConcluidas(supervisorId);
    }

    public List<Tarefa> listaTarefasPendentes(Long supervisorId) {
        return tarefaRepository.listaTarefasPendentes(supervisorId);
    }

    public void atualizaStatusTarefaPraConcluida(Long id) {
        tarefaRepository.updateStatusTarefa(id, StatusTarefa.CONCLUIDA);
    }

    public void atualizaStatusTarefaPraPendente(Long id) {
        tarefaRepository.updateStatusTarefa(id, StatusTarefa.PENDENTE);
    }
}
