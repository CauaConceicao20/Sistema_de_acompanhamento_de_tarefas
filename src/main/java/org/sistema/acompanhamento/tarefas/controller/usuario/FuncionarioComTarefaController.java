package org.sistema.acompanhamento.tarefas.controller.usuario;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.sistema.acompanhamento.tarefas.exception.UsuarioNotFoundException;
import org.sistema.acompanhamento.tarefas.model.Funcionario;
import org.sistema.acompanhamento.tarefas.model.dto.ListaUsuariosDto;
import org.sistema.acompanhamento.tarefas.model.dto.MessageResponseDto;
import org.sistema.acompanhamento.tarefas.services.UsuarioService;
import org.sistema.acompanhamento.tarefas.util.ControllerUtils;

import java.io.IOException;
import java.util.List;

public class FuncionarioComTarefaController extends HttpServlet {

    private final UsuarioService usuarioService;
    private final Gson gson = new Gson();

    public FuncionarioComTarefaController() {
        this.usuarioService = new UsuarioService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = ControllerUtils.validarSessao(req, resp);
            if (session == null) return;

            Long usuarioId = (Long) session.getAttribute("id");

            List<Funcionario> funcionarios = usuarioService.listaFuncionariosComTarefas();
            List<ListaUsuariosDto> listDto = funcionarios.stream()
                    .map(funcionario -> new ListaUsuariosDto(funcionario, usuarioId))
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