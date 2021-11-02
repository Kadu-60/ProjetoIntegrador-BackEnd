package br.com.rd.ProjetoIntegrador.model.dto;

import lombok.Data;

@Data
public class EnderecoDTO {
    private Long id_endereco;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String cep;
    private String numero;
    private String complemento;
    private String ponto_referencia;

    public EnderecoDTO(){}
}
