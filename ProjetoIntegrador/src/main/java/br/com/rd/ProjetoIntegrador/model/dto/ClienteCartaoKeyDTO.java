package br.com.rd.ProjetoIntegrador.model.dto;

import lombok.Data;

@Data
public class ClienteCartaoKeyDTO {
    ClienteDTO cliente;
    CartaoDTO cartao;
}
