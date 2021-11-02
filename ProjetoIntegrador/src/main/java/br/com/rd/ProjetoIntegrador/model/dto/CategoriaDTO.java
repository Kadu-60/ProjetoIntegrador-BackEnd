package br.com.rd.ProjetoIntegrador.model.dto;

import lombok.Data;

@Data
public class CategoriaDTO {
    private Long id_Categoria;
    private String nome;

    public CategoriaDTO(){}
}
