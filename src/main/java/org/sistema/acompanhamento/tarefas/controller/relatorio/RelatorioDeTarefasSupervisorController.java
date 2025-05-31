package org.sistema.acompanhamento.tarefas.controller.relatorio;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.sistema.acompanhamento.tarefas.model.dto.MessageResponseDto;
import org.sistema.acompanhamento.tarefas.model.dto.RelatorioTarefasCadastradasSupervisorDto;
import org.sistema.acompanhamento.tarefas.model.enums.Cargo;
import org.sistema.acompanhamento.tarefas.services.RelatorioService;

import java.io.IOException;
import java.sql.SQLException;

public class RelatorioDeTarefasSupervisorController extends HttpServlet {

    private final RelatorioService relatorioService;

    Gson gson = new Gson();

    public RelatorioDeTarefasSupervisorController() {
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

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("ID do supervisor não fornecido")));
            return;
        }

        int supervisorId;
        try {
            supervisorId = Integer.parseInt(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("ID inválido")));
            return;
        }

        try {
            RelatorioTarefasCadastradasSupervisorDto relatorio = relatorioService.gerarRelatorioPorSupervisor(supervisorId);

            if (relatorio.getDados().isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                resp.getWriter().write(gson.toJson(new MessageResponseDto("Nenhum supervisor encontrado")));
                return;
            }

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(relatorio));
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Erro ao gerar relatório")));
        }
    }
}
