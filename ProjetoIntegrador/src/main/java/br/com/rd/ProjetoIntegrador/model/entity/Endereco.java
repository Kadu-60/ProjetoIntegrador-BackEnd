package br.com.rd.ProjetoIntegrador.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_endereco;
    @Column(nullable = false)
    @NotBlank(message = "Estado is mandatory")
    private String estado;
    @Column(nullable = false)
    @NotBlank(message = "Cidade is mandatory")
    private String cidade;
    @Column(nullable = false)
    @NotBlank(message = "Bairro is mandatory")
    private String bairro;
    @Column(nullable = false)
    @NotBlank(message = "Rua is mandatory")
    private String rua;
    @Column(nullable = false)
    private String cep;
    @Column(nullable = false)
    @NotBlank(message = "Numero is mandatory")
    private String numero;
    @Column(nullable = false)
    private String complemento;
    @Column(nullable = false)
    private String ponto_referencia;
    @Column(nullable = false)
    private String destinatario;


}
