package org.sistema.acompanhamento.tarefas.controller.usuario;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sistema.acompanhamento.tarefas.model.Usuario;
import org.sistema.acompanhamento.tarefas.model.dto.ListaUsuariosDto;
import org.sistema.acompanhamento.tarefas.model.dto.MessageResponseDto;
import org.sistema.acompanhamento.tarefas.services.UsuarioService;

import java.io.IOException;
import java.util.List;

public class UsuarioController extends HttpServlet {

    private UsuarioService usuarioService;

    public UsuarioController() {
        this.usuarioService = new UsuarioService();
    }

    Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            List<Usuario> usuarios = usuarioService.listaUsuarios();

            if (usuarios.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write(gson.toJson(new MessageResponseDto("Nenhum usuário encontrado")));
            }
            else {
                List<ListaUsuariosDto> listDto = usuarios.stream()
                        .map(usuario -> new ListaUsuariosDto(usuario, null)).toList();

                String json = gson.toJson(listDto);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(new MessageResponseDto("Erro interno no servidor")));
        }
    }
}
