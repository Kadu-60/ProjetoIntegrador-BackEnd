package br.com.rd.ProjetoIntegrador.model.dto;

import lombok.Data;

@Data
public class ParcelamentoDTO {
    private Long id_parcelamento;
    private String parcelamento;
    private Integer qtdParcelas;

    public ParcelamentoDTO(){}
}
