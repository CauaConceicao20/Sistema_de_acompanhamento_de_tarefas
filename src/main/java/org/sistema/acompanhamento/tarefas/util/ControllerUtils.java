package org.sistema.acompanhamento.tarefas.util;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.sistema.acompanhamento.tarefas.model.dto.MessageResponseDto;
import org.sistema.acompanhamento.tarefas.model.enums.Cargo;

import java.io.IOException;

public class ControllerUtils {

    private static final Gson gson = new Gson();

    public static HttpSession validarSessao(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Usuário não autenticado")));
            return null;
        }
        return session;
    }

    public static boolean validarCargo(HttpSession session, Cargo cargoEsperado, HttpServletResponse resp) throws IOException {
        Cargo cargo = (Cargo) session.getAttribute("cargo");
        if (cargo != cargoEsperado) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Usuário não autorizado")));
            return false;
        }
        return true;
    }

    public static Long lerIdPath(HttpServletRequest req, HttpServletResponse resp, String msgErro) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.length() <= 1) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(new MessageResponseDto(msgErro)));
            return null;
        }
        try {
            return Long.parseLong(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("ID inválido")));
            return null;
        }
    }
}