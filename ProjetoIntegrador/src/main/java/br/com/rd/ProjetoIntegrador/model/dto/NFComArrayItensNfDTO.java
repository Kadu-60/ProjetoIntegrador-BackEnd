package br.com.rd.ProjetoIntegrador.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class NFComArrayItensNfDTO {
    NfDTO nf;
    List<Item_Nf_DTO> item_nf;
}
