package br.com.rd.ProjetoIntegrador.model.dto;

import lombok.Data;

@Data
public class CardProdutoDTO {
    private Long id_produto;
    private String foto;
    private String nome;
    private String desc;
    private Double valor_preco;
}
