package org.sistema.acompanhamento.tarefas.model.dto;

import org.sistema.acompanhamento.tarefas.model.Usuario;
import org.sistema.acompanhamento.tarefas.model.enums.Cargo;

public class ListaUsuariosDto {
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private Cargo cargo;

    public ListaUsuariosDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.cpf = usuario.getCpf();
        this.telefone = usuario.getTelefone();
        this.cargo = usuario.getCargo();
    }
}