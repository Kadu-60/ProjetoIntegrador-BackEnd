package br.com.rd.ProjetoIntegrador.model.dto;

import br.com.rd.ProjetoIntegrador.model.dto.ClienteDTO;
import br.com.rd.ProjetoIntegrador.model.dto.EnderecoDTO;
import lombok.Data;

@Data
public class ClienteEnderecoKeyDTO {
    ClienteDTO cliente;
    EnderecoDTO endereco;
}
