package org.sistema.acompanhamento.tarefas.controller.auth;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sistema.acompanhamento.tarefas.model.Usuario;
import org.sistema.acompanhamento.tarefas.model.dto.CadastroUsuarioDto;
import org.sistema.acompanhamento.tarefas.model.dto.MessageResponseDto;
import org.sistema.acompanhamento.tarefas.repository.UsuarioRepository;
import org.sistema.acompanhamento.tarefas.services.UsuarioService;

import java.io.IOException;

public class CadastroController extends HttpServlet {

    private final UsuarioService usuarioService;

    public CadastroController() {
        this.usuarioService = new UsuarioService();
    }
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            CadastroUsuarioDto usuarioDto = gson.fromJson(req.getReader(), CadastroUsuarioDto.class);

            Usuario usuario = usuarioService.descobreCargoDeUsuario(usuarioDto);

            usuarioService.createUsuario(usuario);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(gson.toJson(usuarioDto));

        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(new MessageResponseDto(e.getMessage())));
            e.printStackTrace();

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Erro interno do servidor")));
            e.printStackTrace();
        }
    }
}

