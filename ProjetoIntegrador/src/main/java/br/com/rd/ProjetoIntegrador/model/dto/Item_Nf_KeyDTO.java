package br.com.rd.ProjetoIntegrador.model.dto;

import br.com.rd.ProjetoIntegrador.model.entity.Nf;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Data
public class Item_Nf_KeyDTO {
    private Long id_item;
    private NfDTO nf;
}
