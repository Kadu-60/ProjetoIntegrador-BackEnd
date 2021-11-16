package br.com.rd.ProjetoIntegrador.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
@Entity
@Data
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Cartao;
    @Column(nullable = false)
    @NotBlank(message = "nome is mandatory")
    private String nome;
    @Column(nullable = false)
    @NotBlank(message = "numero is mandatory")
    private String numero;
    @Column(nullable = false)
    private Date validade;

}
