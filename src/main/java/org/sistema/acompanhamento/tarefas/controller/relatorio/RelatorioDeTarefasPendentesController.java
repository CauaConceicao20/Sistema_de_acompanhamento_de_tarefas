package org.sistema.acompanhamento.tarefas.controller.relatorio;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.sistema.acompanhamento.tarefas.model.dto.MessageResponseDto;
import org.sistema.acompanhamento.tarefas.model.dto.RelatorioTarefasPendentesDto;
import org.sistema.acompanhamento.tarefas.model.enums.Cargo;
import org.sistema.acompanhamento.tarefas.services.RelatorioService;

import java.io.IOException;
import java.sql.SQLException;

public class RelatorioDeTarefasPendentesController extends HttpServlet {

    private final RelatorioService relatorioService;
    private final Gson gson = new Gson();

    public RelatorioDeTarefasPendentesController() {
        this.relatorioService = new RelatorioService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Usuário não autenticado")));
            return;
        }

        Cargo cargo = (Cargo) session.getAttribute("cargo");
        if (cargo != Cargo.GERENTE) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Usuário não autorizado")));
            return;
        }

        try {
            RelatorioTarefasPendentesDto relatorio = relatorioService.gerarRelatorioDeTarefasPendentes();

            if (relatorio.getTarefas().isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                resp.getWriter().write(gson.toJson(new MessageResponseDto("Nenhuma tarefa pendente encontrada")));
                return;
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(relatorio));
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Erro ao gerar relatório de tarefas pendentes")));
        }
    }
}
