package br.com.rd.ProjetoIntegrador.model.dto;

import lombok.Data;


@Data
public class ClienteEnderecoDTO {
    ClienteEnderecoKeyDTO clienteEnderecoKey;
    Boolean enderecoPrincipal;
    Boolean enderecoEntrega;
}
