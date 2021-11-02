package br.com.rd.ProjetoIntegrador.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProdutoDTO {
    private Long id_produto;
    private String nome_produto;
    private String descricao;
    private Integer ibu;
    private String cor;
    private Double teor;
    private Integer quantidade_ml;
    private String ean;
    private Boolean destaque;
    private Date dataDeCriacao;

    private FamiliaDTO familia;
    private CategoriaDTO categoria;
    private MarcaDTO marca;
    private PratoDTO prato;


    public ProdutoDTO(){}
}
