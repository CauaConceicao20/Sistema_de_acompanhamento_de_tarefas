package org.sistema.acompanhamento.tarefas.model.dto;

import org.sistema.acompanhamento.tarefas.model.Funcionario;
import org.sistema.acompanhamento.tarefas.model.Usuario;
import org.sistema.acompanhamento.tarefas.model.enums.Cargo;

public class ListaUsuariosDto {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private Long supervisorId;
    private Cargo cargo;

    public ListaUsuariosDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.cpf = usuario.getCpf();
        this.telefone = usuario.getTelefone();
        if(usuario instanceof Funcionario) {
            this.supervisorId = (long) ((Funcionario) usuario).getSupervisorId();
        }
        this.cargo = usuario.getCargo();
    }
}
