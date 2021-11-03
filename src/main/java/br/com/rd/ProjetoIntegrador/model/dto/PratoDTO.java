package br.com.rd.ProjetoIntegrador.model.dto;

import lombok.Data;

@Data
public class PratoDTO {
    private Long id_prato;
    private String descricao;

    public PratoDTO(){}
}
