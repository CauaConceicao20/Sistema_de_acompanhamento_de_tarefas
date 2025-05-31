package org.sistema.acompanhamento.tarefas.controller.usuario;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.sistema.acompanhamento.tarefas.model.Funcionario;
import org.sistema.acompanhamento.tarefas.model.dto.ListaUsuariosDto;
import org.sistema.acompanhamento.tarefas.model.dto.MessageResponseDto;
import org.sistema.acompanhamento.tarefas.services.UsuarioService;

import java.io.IOException;
import java.util.List;


public class FuncionarioController extends HttpServlet {

    private final UsuarioService usuarioService;

    public FuncionarioController() {
        this.usuarioService = new UsuarioService();
    }

    Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        try {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("id") == null) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.getWriter().write(gson.toJson(new MessageResponseDto("Usuário não autenticado")));
                return;
            }
            Long usuarioId = (Long) session.getAttribute("id");

            List<Funcionario> funcionarios = usuarioService.listaFuncionariosDeUmSupervisorEspecifico(usuarioId);

            if (funcionarios.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write(gson.toJson(new MessageResponseDto("Nenhum funcionário encontrado")));
            } else {
                List<ListaUsuariosDto> listDto = funcionarios.stream()
                        .map(funcionario -> new ListaUsuariosDto(funcionario, null))
                        .toList();
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

