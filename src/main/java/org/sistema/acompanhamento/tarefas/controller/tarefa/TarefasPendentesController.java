package org.sistema.acompanhamento.tarefas.controller.tarefa;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.sistema.acompanhamento.tarefas.exception.TarefaNotFoundException;
import org.sistema.acompanhamento.tarefas.exception.UsuarioNotFoundException;
import org.sistema.acompanhamento.tarefas.model.Funcionario;
import org.sistema.acompanhamento.tarefas.model.Tarefa;
import org.sistema.acompanhamento.tarefas.model.Usuario;
import org.sistema.acompanhamento.tarefas.model.dto.ListaTarefasFiltradasDto;
import org.sistema.acompanhamento.tarefas.model.dto.MessageResponseDto;
import org.sistema.acompanhamento.tarefas.model.enums.Cargo;
import org.sistema.acompanhamento.tarefas.model.enums.StatusTarefa;
import org.sistema.acompanhamento.tarefas.services.TarefaService;
import org.sistema.acompanhamento.tarefas.services.UsuarioService;
import org.sistema.acompanhamento.tarefas.util.ControllerUtils;

import java.io.IOException;
import java.util.List;

public class TarefasPendentesController extends HttpServlet {

    private final TarefaService tarefaService;
    private final UsuarioService usuarioService;
    private final Gson gson = new Gson();

    public TarefasPendentesController() {
        this.tarefaService = new TarefaService();
        this.usuarioService = new UsuarioService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = ControllerUtils.validarSessao(req, resp);
            if (session == null) return;

            if (!ControllerUtils.validarCargo(session, Cargo.SUPERVISOR, resp)) return;

            Long funcionarioId = ControllerUtils.lerIdPath(req, resp, "ID do funcionário não fornecido");
            if (funcionarioId == null) return;

            List<Tarefa> tarefas = tarefaService.listaTarefasPendentes(funcionarioId);
            Usuario usuario = usuarioService.buscaPorId(funcionarioId);

            if (usuario instanceof Funcionario) {
                List<ListaTarefasFiltradasDto> tarefasDto = tarefas.stream()
                        .map(t -> new ListaTarefasFiltradasDto(t, usuario.getNome()))
                        .toList();
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(gson.toJson(tarefasDto));
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson(new MessageResponseDto("Usuário não é um funcionário")));
            }

        } catch (TarefaNotFoundException | UsuarioNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write(gson.toJson(new MessageResponseDto(e.getMessage())));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Erro interno do servidor")));
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = ControllerUtils.validarSessao(req, resp);
            if (session == null) return;

            if (!ControllerUtils.validarCargo(session, Cargo.FUNCIONARIO, resp)) return;

            Long tarefaId = ControllerUtils.lerIdPath(req, resp, "ID da tarefa não fornecido");
            if (tarefaId == null) return;

            Tarefa tarefa = tarefaService.buscaTarefaPorId(tarefaId);
            if (tarefa.getStatus() == StatusTarefa.PENDENTE) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson(new MessageResponseDto("A tarefa já está como Pendente.")));
                return;
            }

            tarefaService.atualizaStatusTarefaPraPendente(tarefaId);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Tarefa marcada como pendente com sucesso")));

        } catch (TarefaNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write(gson.toJson(new MessageResponseDto(e.getMessage())));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Erro interno do servidor")));
            e.printStackTrace();
        }
    }
}