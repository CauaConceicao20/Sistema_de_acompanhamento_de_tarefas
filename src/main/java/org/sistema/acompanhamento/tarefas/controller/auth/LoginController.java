package org.sistema.acompanhamento.tarefas.controller.auth;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.sistema.acompanhamento.tarefas.model.Usuario;
import org.sistema.acompanhamento.tarefas.model.dto.MessageResponseDto;
import org.sistema.acompanhamento.tarefas.model.dto.LoginUsuarioDto;
import org.sistema.acompanhamento.tarefas.services.LoginService;
import org.sistema.acompanhamento.tarefas.exception.LoginException;

import java.io.IOException;

public class LoginController extends HttpServlet {

    private final LoginService loginService;

    public LoginController() {
        this.loginService = new LoginService();
    }
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            LoginUsuarioDto loginUsuarioDto = gson.fromJson(req.getReader(), LoginUsuarioDto.class);

            Usuario usuario = loginService.login(loginUsuarioDto.getEmail(), loginUsuarioDto.getSenha());

            HttpSession session = req.getSession(true);
            session.setAttribute("id", usuario.getId());
            session.setAttribute("cargo", usuario.getCargo());

            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("mensagem", "Acesso Permitido");
            responseJson.addProperty("cargo", usuario.getCargo().name());
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(responseJson.toString());

        } catch (LoginException e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write(gson.toJson(new MessageResponseDto(e.getMessage())));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Erro interno do servidor")));
            e.printStackTrace();
        }
    }
}