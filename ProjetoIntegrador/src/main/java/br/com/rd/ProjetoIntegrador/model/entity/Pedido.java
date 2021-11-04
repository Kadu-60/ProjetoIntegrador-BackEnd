package br.com.rd.ProjetoIntegrador.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Double subtotal;
    @Column(nullable = false)
    private Double total;
    @Column(nullable = false)
    private Date dataDeCriacao;
    @Column(nullable = false)
    private Double frete;
    @Column(nullable = false)
    private Boolean finalizado;





    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_nf", nullable = true)
    private Nf nf;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pagamento")
    private Parcelamento parcelamento;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_status_pedido")
    private StatusPedido statusPedido;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cartao")
    private Cartao cartao;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Cliente")
    private Cliente cliente;

}
