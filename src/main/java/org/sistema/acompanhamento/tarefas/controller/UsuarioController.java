package org.sistema.acompanhamento.tarefas.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sistema.acompanhamento.tarefas.model.Usuario;
import org.sistema.acompanhamento.tarefas.model.dto.ListaUsuariosDto;
import org.sistema.acompanhamento.tarefas.services.UsuarioService;

import java.util.List;

public class UsuarioController extends HttpServlet {

    UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Usuario> usuarios = usuarioService.listaUsuarios();

            List<ListaUsuariosDto> listDto = usuarios.stream()
                    .map(usuario -> new ListaUsuariosDto(usuario))
                    .toList();

            Gson gson = new Gson();
            String json = gson.toJson(listDto);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
