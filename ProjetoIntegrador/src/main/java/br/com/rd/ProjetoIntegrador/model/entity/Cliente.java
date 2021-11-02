package br.com.rd.ProjetoIntegrador.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Cliente;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column(nullable = false)
    private String cpf;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private Date dataNascimento;
    @Column(nullable = false)
    private String telefone;



}
