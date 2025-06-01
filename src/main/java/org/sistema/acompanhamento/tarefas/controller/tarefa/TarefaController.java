package org.sistema.acompanhamento.tarefas.controller.tarefa;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.sistema.acompanhamento.tarefas.exception.TarefaNotFoundException;
import org.sistema.acompanhamento.tarefas.model.Tarefa;
import org.sistema.acompanhamento.tarefas.model.dto.CadastroTarefaDto;
import org.sistema.acompanhamento.tarefas.model.dto.ListaTarefasDto;
import org.sistema.acompanhamento.tarefas.model.dto.MessageResponseDto;
import org.sistema.acompanhamento.tarefas.model.enums.Cargo;
import org.sistema.acompanhamento.tarefas.services.TarefaService;
import org.sistema.acompanhamento.tarefas.util.ControllerUtils;

import java.io.IOException;
import java.util.List;

public class TarefaController extends HttpServlet {

    private final TarefaService tarefaService;
    private final Gson gson = new Gson();

    public TarefaController() {
        this.tarefaService = new TarefaService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = ControllerUtils.validarSessao(req, resp);
            if (session == null) return;

            if (!ControllerUtils.validarCargo(session, Cargo.SUPERVISOR, resp)) return;

            Long usuarioId = (Long) session.getAttribute("id");
            CadastroTarefaDto cadastroTarefaDto = gson.fromJson(req.getReader(), CadastroTarefaDto.class);
            tarefaService.criaTarefa(new Tarefa(usuarioId, cadastroTarefaDto));
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Tarefa cadastrada com sucesso")));

        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(new MessageResponseDto(e.getMessage())));
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Erro interno do servidor")));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = ControllerUtils.validarSessao(req, resp);
            if (session == null) return;

            if (!ControllerUtils.validarCargo(session, Cargo.FUNCIONARIO, resp)) return;

            Long usuarioId = (Long) session.getAttribute("id");
            List<Tarefa> tarefas = tarefaService.listaTarefasDeFuncionario(usuarioId);

            List<ListaTarefasDto> tarefasDto = tarefas.stream()
                    .map(ListaTarefasDto::new)
                    .toList();

            String json = gson.toJson(tarefasDto);
            resp.getWriter().write(json);

        } catch (TarefaNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write(gson.toJson(new MessageResponseDto(e.getMessage())));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Erro interno do servidor")));
        }
    }
}