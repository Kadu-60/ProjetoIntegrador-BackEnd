package br.com.rd.ProjetoIntegrador.model.dto;

import lombok.Data;

import java.util.Date;
@Data
public class CartaoDTO {
    private Long id_Cartao;
    private String nome;
    private String numero;
    private Date validade;

    public CartaoDTO(){}
}
