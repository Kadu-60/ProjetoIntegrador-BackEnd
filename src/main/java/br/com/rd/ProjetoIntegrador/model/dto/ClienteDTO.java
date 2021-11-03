package br.com.rd.ProjetoIntegrador.model.dto;

import lombok.Data;

import java.util.Date;
@Data
public class ClienteDTO {
    private Long id_Cliente;
    private String email;
    private String cpf;
    private String nome;
    private Date dataNascimento;
    private String telefone;

    public ClienteDTO(){}

}
