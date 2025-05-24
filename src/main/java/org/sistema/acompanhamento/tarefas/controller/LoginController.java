package org.sistema.acompanhamento.tarefas.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.sistema.acompanhamento.tarefas.model.Usuario;
import org.sistema.acompanhamento.tarefas.model.dto.MessageResponseDto;
import org.sistema.acompanhamento.tarefas.model.dto.LoginUsuarioDto;
import org.sistema.acompanhamento.tarefas.services.LoginService;

import java.io.IOException;

public class LoginController extends HttpServlet {

    LoginService loginService = new LoginService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        LoginUsuarioDto loginUsuarioDto = new LoginUsuarioDto(req.getParameter("email"),
                req.getParameter("senha"));

        if (loginUsuarioDto.getEmail() == null || loginUsuarioDto.getSenha() == null
        || loginUsuarioDto.getEmail().isBlank() || loginUsuarioDto.getSenha().isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Email e senha são obrigatórios")));
            return;
        }

        try {
            Usuario usuario = loginService.login(loginUsuarioDto.getEmail(), loginUsuarioDto.getSenha());
            HttpSession session = req.getSession();
            session.setAttribute("id", usuario.getId());
            session.setAttribute("cargo", usuario.getCargo());

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Acesso Permitido")));

        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Acesso Negado")));

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Erro interno do servidor")));
            e.printStackTrace();
        }
    }
}
