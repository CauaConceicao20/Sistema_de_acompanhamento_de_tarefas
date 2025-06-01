package org.sistema.acompanhamento.tarefas.controller.usuario;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.sistema.acompanhamento.tarefas.exception.UsuarioNotFoundException;
import org.sistema.acompanhamento.tarefas.model.Supervisor;
import org.sistema.acompanhamento.tarefas.model.dto.ListaUsuariosDto;
import org.sistema.acompanhamento.tarefas.model.dto.MessageResponseDto;
import org.sistema.acompanhamento.tarefas.model.enums.Cargo;
import org.sistema.acompanhamento.tarefas.services.UsuarioService;
import org.sistema.acompanhamento.tarefas.util.ControllerUtils;

import java.io.IOException;
import java.util.List;

public class SupervisorController extends HttpServlet {

    private final UsuarioService usuarioService;
    private final Gson gson = new Gson();

    public SupervisorController() {
        this.usuarioService = new UsuarioService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = ControllerUtils.validarSessao(req, resp);
            if (session == null) return;

            if (!ControllerUtils.validarCargo(session, Cargo.GERENTE, resp)) return;

            List<Supervisor> supervisores = usuarioService.listaSupervisores();
            List<ListaUsuariosDto> listDto = supervisores.stream()
                    .map(supervisor -> new ListaUsuariosDto(supervisor, null))
                    .toList();
            String json = gson.toJson(listDto);
            resp.getWriter().write(json);

        } catch (UsuarioNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write(gson.toJson(new MessageResponseDto(e.getMessage())));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Erro interno no servidor")));
        }
    }
}