package br.com.rd.ProjetoIntegrador.model.dto;

import lombok.Data;

@Data
public class MarcaDTO {
    private Long id_marca;
    private String nome;

    public MarcaDTO(){}
}
