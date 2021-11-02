package br.com.rd.ProjetoIntegrador.model.dto;

import br.com.rd.ProjetoIntegrador.model.entity.Cliente;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Data
public class NfDTO {
    private Long id_nf;
    private Long chave_acesso;
    private String serie;
    private Date emissao;
    private Double subtotal;
    private Double total;
    private ClienteDTO cliente;

    public NfDTO(){}
}
