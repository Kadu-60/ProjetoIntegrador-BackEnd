package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.model.dto.ClienteDTO;
import br.com.rd.ProjetoIntegrador.model.entity.Cliente;
import br.com.rd.ProjetoIntegrador.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    private ClienteDTO clienteToDto (Cliente businessCliente) {
        ClienteDTO dtoCliente = new ClienteDTO();
        dtoCliente.setId_Cliente(businessCliente.getId_Cliente());
        dtoCliente.setNome(businessCliente.getNome());
        dtoCliente.setCpf(businessCliente.getCpf());
        dtoCliente.setDataNascimento(businessCliente.getDataNascimento());
        dtoCliente.setEmail(businessCliente.getEmail());
        dtoCliente.setTelefone(businessCliente.getTelefone());
        return dtoCliente;
    }

    private Cliente dtoToCliente (ClienteDTO dtoCliente) {
        Cliente businessCliente = new Cliente();
        businessCliente.setNome(dtoCliente.getNome());
        businessCliente.setCpf(dtoCliente.getCpf());
        businessCliente.setDataNascimento(dtoCliente.getDataNascimento());
        businessCliente.setEmail(dtoCliente.getEmail());
        businessCliente.setTelefone(dtoCliente.getTelefone());
        return businessCliente;
    }

    public ClienteDTO criarCliente (ClienteDTO cliente) {
        Cliente novoCliente = this.dtoToCliente(cliente);
        novoCliente = clienteRepository.save(novoCliente);
        return this.clienteToDto(novoCliente);
    }

    public void excluirCliente (Long id_Cliente) {
        if (clienteRepository.existsById(id_Cliente)) {
            clienteRepository.deleteById(id_Cliente);
        }
    }

    public List<ClienteDTO> findAllCliente(){
        List<Cliente> allCliente = clienteRepository.findAll();
        return this.listToClienteDto(allCliente);
    }

    public List<ClienteDTO> listToClienteDto(List<Cliente> listaCliente){
        List<ClienteDTO> listClienteDto = new ArrayList<ClienteDTO>();
        for (Cliente cl : listaCliente){
            listClienteDto.add(this.clienteToDto(cl));
        }
        return listClienteDto;
    }

    public ClienteDTO findById(Long id){
        Optional<Cliente> op = clienteRepository.findById(id);
        if(op.isPresent()){
            return clienteToDto(op.get());
        }
        return null;
    }

    public ClienteDTO atualizarClientePorId (ClienteDTO clienteDTO, Long id_Cliente) {
        Optional<Cliente> op = clienteRepository.findById(id_Cliente);

        if (op.isPresent()) {
            Cliente cl = op.get();
            if (clienteDTO.getNome() != null) {
                cl.setNome(clienteDTO.getNome());
            }

            if (clienteDTO.getCpf() != null) {
                cl.setCpf(clienteDTO.getCpf());
            }

            if (clienteDTO.getDataNascimento() != null) {
                cl.setDataNascimento(clienteDTO.getDataNascimento());
            }

            if (clienteDTO.getTelefone() != null) {
                cl.setTelefone(clienteDTO.getTelefone());
            }

            clienteRepository.save(cl);
            return clienteToDto(cl);
        }
        return null;
    }
}
