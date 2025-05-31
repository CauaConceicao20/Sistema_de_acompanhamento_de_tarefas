package org.sistema.acompanhamento.tarefas.controller.tarefa;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.sistema.acompanhamento.tarefas.model.Funcionario;
import org.sistema.acompanhamento.tarefas.model.Tarefa;
import org.sistema.acompanhamento.tarefas.model.Usuario;
import org.sistema.acompanhamento.tarefas.model.dto.ListaTarefasFiltradasDto;
import org.sistema.acompanhamento.tarefas.model.dto.MessageResponseDto;
import org.sistema.acompanhamento.tarefas.model.enums.StatusTarefa;
import org.sistema.acompanhamento.tarefas.services.TarefaService;
import org.sistema.acompanhamento.tarefas.services.UsuarioService;

import java.io.IOException;
import java.util.List;

public class TarefasConcluidasController extends HttpServlet {

    private final TarefaService tarefaService;
    private final UsuarioService usuarioService;
    private final Gson gson = new Gson();

    public TarefasConcluidasController() {
        this.tarefaService = new TarefaService();
        this.usuarioService = new UsuarioService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = req.getSession(false);
            if (session == null) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write(gson.toJson(new MessageResponseDto("Usuário não autenticado")));
                return;
            }

            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.length() <= 1) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson(new MessageResponseDto("ID do funcionário não fornecido")));
                return;
            }

            long funcionarioId;
            try {
                funcionarioId = Long.parseLong(pathInfo.substring(1));
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson(new MessageResponseDto("ID do funcionário inválido")));
                return;
            }

            List<Tarefa> tarefas = tarefaService.listaTarefasConcluidas(funcionarioId);
            if (tarefas.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write(gson.toJson(new MessageResponseDto("Nenhuma tarefa encontrada")));
                return;
            }

            Usuario usuario = usuarioService.buscaPorId(funcionarioId);

            if(usuario instanceof Funcionario) {
                List<ListaTarefasFiltradasDto> tarefasDto = tarefas.stream()
                        .map(t -> new ListaTarefasFiltradasDto(t, usuario.getNome()))
                        .toList();
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(gson.toJson(tarefasDto));
            }else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson(new MessageResponseDto("Usuário não é um funcionário")));
            }

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
            HttpSession session = req.getSession(false);
            if (session == null) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write(gson.toJson(new MessageResponseDto("Usuário não autenticado")));
                return;
            }

            String pathInfo = req.getPathInfo(); // espera algo como "/1"
            if (pathInfo == null || pathInfo.equals("/") || pathInfo.length() <= 1) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson(new MessageResponseDto("ID da tarefa não fornecido")));
                return;
            }

            long tarefaId;
            try {
                tarefaId = Long.parseLong(pathInfo.substring(1)); // Remove a barra inicial
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write(gson.toJson(new MessageResponseDto("ID da tarefa inválido")));
                return;
            }

            Tarefa tarefa = tarefaService.buscaTarefaPorId(tarefaId);
            if (tarefa == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write(gson.toJson(new MessageResponseDto("Tarefa não encontrada")));
                return;
            }

            if (tarefa.getStatus() == StatusTarefa.CONCLUIDA) {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                resp.getWriter().write(gson.toJson(new MessageResponseDto("A tarefa já está concluída")));
                return;
            }

            tarefaService.atualizaStatusTarefaPraConcluida(tarefaId);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Tarefa concluída com sucesso")));

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Erro interno do servidor")));
            e.printStackTrace();
        }
    }
}
