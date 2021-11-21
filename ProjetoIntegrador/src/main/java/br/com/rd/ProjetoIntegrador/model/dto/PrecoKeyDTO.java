package br.com.rd.ProjetoIntegrador.model.dto;

import lombok.Data;

import java.util.Date;
@Data
public class PrecoKeyDTO {
    private Date data_vigencia;
    private ProdutoDTO produto;
}
