package br.com.rd.ProjetoIntegrador.model.dto;

import br.com.rd.ProjetoIntegrador.model.entity.MetodoPag;
import lombok.Data;

import java.util.Date;

@Data
public class PedidoDTO {
    private Long id;
    private Double subtotal;
    private Double total;
    private Date dataDeCriacao;
    private Double frete;
    private Boolean finalizado;

    private MetodoPagDTO metodoPag;
    private ClienteDTO cliente;
    private NfDTO nf;
    private ParcelamentoDTO pagamento;
    private StatusPedidoDTO status;
    private EnderecoDTO endereco;
    private CartaoDTO cartao;

    public PedidoDTO(){}
}
