package br.com.rd.ProjetoIntegrador.model.dto.Card.Interfaces;

import java.util.Date;

public interface CardProdutoDTOInterface {

    public Long getId_Produto();

    public String getFoto();

    public String getNome_Produto();

    public String getDescricao();

    public Double getValor_Preco();

    public Date getDataDeCriacao();

    public Boolean getDestaque();
}
