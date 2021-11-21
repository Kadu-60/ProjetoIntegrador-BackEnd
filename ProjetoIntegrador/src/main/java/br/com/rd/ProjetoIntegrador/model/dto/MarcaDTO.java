package br.com.rd.ProjetoIntegrador.model.dto;

import lombok.Data;

@Data
public class MarcaDTO {
    private Long id_marca;
    private String nome;
    private String img;
    private String desc;

    public MarcaDTO(){}
}
