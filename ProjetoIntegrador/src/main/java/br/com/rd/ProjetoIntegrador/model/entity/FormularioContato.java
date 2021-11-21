package br.com.rd.ProjetoIntegrador.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class FormularioContato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank(message = "nome is mandatory")
    private String nome;
    @Column(nullable = true)
    @NotBlank(message = "telefone is mandatory")
    private String telefone;
    @Column(nullable = false)
    @NotBlank(message = "email is mandatory")
    private String email;
    @Column(nullable = false)
    @NotBlank(message = "tipo_de_contato is mandatory")
    private String tipo_de_contato;
    @Column(nullable = false)
    @NotBlank(message = "assunto is mandatory")
    private String assunto;
    @Column(nullable = false)
    @NotBlank(message = "mensagem is mandatory")
    private String mensagem;

}

