package org.sistema.acompanhamento.tarefas.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sistema.acompanhamento.tarefas.model.Usuario;
import org.sistema.acompanhamento.tarefas.model.dto.CadastroUsuarioDto;
import org.sistema.acompanhamento.tarefas.model.dto.MessageResponseDto;
import org.sistema.acompanhamento.tarefas.model.enums.Cargo;
import org.sistema.acompanhamento.tarefas.services.UsuarioService;

import java.io.IOException;

public class CadastroController extends HttpServlet {

    UsuarioService usuarioService = new UsuarioService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            CadastroUsuarioDto usuarioDto = new CadastroUsuarioDto(req.getParameter("nome"),
                    req.getParameter("email"), req.getParameter("senha"), req.getParameter("cpf"),
                    req.getParameter("telefone"), null, Cargo.valueOf(req.getParameter("cargo"))
            );

            Usuario usuario = usuarioService.descobreCargoDeUsuario(usuarioDto);

            usuarioService.createUsuario(usuario);

            Gson gson = new Gson();
            String json = gson.toJson(usuarioDto);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);

        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Acesso Negado")));
            e.printStackTrace();

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Algo inesperado aconteceu")));
        }
    }
}

