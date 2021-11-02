package br.com.rd.ProjetoIntegrador.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class FormularioContato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = true)
    private Integer telefone;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String tipo_de_contato;
    @Column(nullable = false)
    private String assunto;
    @Column(nullable = false)
    private String mensagem;

}

