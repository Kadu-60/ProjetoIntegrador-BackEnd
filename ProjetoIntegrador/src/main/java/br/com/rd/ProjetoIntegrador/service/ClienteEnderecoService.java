package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.ClienteEnderecoKey;
import br.com.rd.ProjetoIntegrador.model.dto.ClienteDTO;
import br.com.rd.ProjetoIntegrador.model.dto.ClienteEnderecoDTO;
import br.com.rd.ProjetoIntegrador.model.dto.ClienteEnderecoKeyDTO;
import br.com.rd.ProjetoIntegrador.model.dto.EnderecoDTO;
import br.com.rd.ProjetoIntegrador.model.entity.Cliente;
import br.com.rd.ProjetoIntegrador.model.entity.ClienteEndereco;
import br.com.rd.ProjetoIntegrador.model.entity.Endereco;
import br.com.rd.ProjetoIntegrador.repository.ClienteEnderecoRepository;
import br.com.rd.ProjetoIntegrador.repository.ClienteRepository;
import br.com.rd.ProjetoIntegrador.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteEnderecoService {

    @Autowired
    ClienteEnderecoRepository clienteEnderecoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EnderecoRepository enderecoRepository;

    public ClienteEnderecoDTO create (ClienteEnderecoDTO dto){
        if(dto.getClienteEnderecoKey()!=null){
            if(dto.getClienteEnderecoKey().getCliente().getId_Cliente()!=null){
                dto.getClienteEnderecoKey().setCliente(this.clienteToDto(this.clienteRepository.findById(dto.getClienteEnderecoKey().getCliente().getId_Cliente()).get()));
            }
            if(dto.getClienteEnderecoKey().getEndereco().getId_endereco()!=null){
                dto.getClienteEnderecoKey().setEndereco(this.businessToDto(this.enderecoRepository.findById(dto.getClienteEnderecoKey().getEndereco().getId_endereco()).get()));
            }else{
                dto.getClienteEnderecoKey().setEndereco(this.createEndereco(dto.getClienteEnderecoKey().getEndereco()));
            }
        }
        ClienteEndereco bus = this.dtoToBusiness(dto);

        if(this.clienteEnderecoRepository.getByIdCliente(dto.getClienteEnderecoKey().getCliente().getId_Cliente()).isEmpty()){
            bus.setEnderecoPrincipal(true);
            bus.setEnderecoEntrega(true);
        }
        ClienteEnderecoDTO retorno = this.businessToDTO(this.clienteEnderecoRepository.save(bus));
        return retorno;
    }

    private EnderecoDTO createEndereco(EnderecoDTO dto){
        Endereco bus = this.dtoToBusiness(dto);
        return this.businessToDto(this.enderecoRepository.save(bus));
    }

    public List<ClienteEnderecoDTO> findByIdCliente(Long id){
        List<ClienteEndereco> bus = this.clienteEnderecoRepository.getByIdCliente(id);
        return this.listToDto(bus);
    }
    public List<ClienteEnderecoDTO> updateEndEntrega(Long id_cliente, Long id_Endereco){
        List<ClienteEndereco> bus = this.clienteEnderecoRepository.getByIdCliente(id_cliente);
        ClienteEnderecoKey key = new ClienteEnderecoKey();
        key.setCliente(this.clienteRepository.findById(id_cliente).get());
        key.setEndereco(this.enderecoRepository.findById(id_Endereco).get());
        if(this.clienteEnderecoRepository.existsById(key)){
            for(ClienteEndereco item: bus){
                if(item.getEnderecoEntrega()){
                    item.setEnderecoEntrega(false);
                    this.clienteEnderecoRepository.save(item);
                }
                if(item.getClienteEnderecoKey().getEndereco().getId_endereco()==id_Endereco){
                    item.setEnderecoEntrega(true);
                    this.clienteEnderecoRepository.save(item);
                }
            }
        }
        return this.listToDto(this.clienteEnderecoRepository.getByIdCliente(id_cliente));
    }

    public List<ClienteEnderecoDTO> updateEndPrincipal(Long id_cliente, Long id_Endereco){
        List<ClienteEndereco> bus = this.clienteEnderecoRepository.getByIdCliente(id_cliente);
        ClienteEnderecoKey key = new ClienteEnderecoKey();
        key.setCliente(this.clienteRepository.findById(id_cliente).get());
        key.setEndereco(this.enderecoRepository.findById(id_Endereco).get());
        if(this.clienteEnderecoRepository.existsById(key)){
            for(ClienteEndereco item: bus){
                if(item.getEnderecoPrincipal()){
                    item.setEnderecoPrincipal(false);
                    this.clienteEnderecoRepository.save(item);
                }
                if(item.getClienteEnderecoKey().getEndereco().getId_endereco()==id_Endereco){
                    item.setEnderecoPrincipal(true);
                    this.clienteEnderecoRepository.save(item);
                }
            }
        }
        return this.listToDto(this.clienteEnderecoRepository.getByIdCliente(id_cliente));
    }


    //caso seja necessário alterar o endereco deve se utilizado o endpoint da endereco controller

    public void delete(Long id_cliente, Long id_endereco){
        Cliente cliente = new Cliente();
        Endereco endereco = new Endereco();
        if(clienteRepository.existsById(id_cliente)&&enderecoRepository.existsById(id_endereco)){
            cliente = this.clienteRepository.findById(id_cliente).get();
            endereco = this.enderecoRepository.findById(id_endereco).get();
            ClienteEnderecoKey eck = new ClienteEnderecoKey();
            eck.setEndereco(endereco);
            eck.setCliente(cliente);
            if(this.clienteEnderecoRepository.existsById(eck)){
                this.clienteEnderecoRepository.deleteById(eck);
            }
        }
    }




    private List<ClienteEnderecoDTO> listToDto(List<ClienteEndereco> bus){
        List<ClienteEnderecoDTO> dto = new ArrayList<>();
        for(ClienteEndereco item : bus){
            dto.add(this.businessToDTO(item));
        }
        return dto;
    }

    private ClienteEnderecoDTO businessToDTO(ClienteEndereco bus){
        ClienteEnderecoDTO dto = null;
        if(bus.getClienteEnderecoKey()!=null) {
            dto = new ClienteEnderecoDTO();
            dto.setClienteEnderecoKey(new ClienteEnderecoKeyDTO());
            dto.getClienteEnderecoKey().setEndereco(this.businessToDto(bus.getClienteEnderecoKey().getEndereco()));
            dto.getClienteEnderecoKey().setCliente(this.clienteToDto(bus.getClienteEnderecoKey().getCliente()));
            dto.setEnderecoPrincipal(bus.getEnderecoPrincipal());
            dto.setEnderecoEntrega(bus.getEnderecoEntrega());
        }
        return dto;
    }

    private ClienteEndereco dtoToBusiness(ClienteEnderecoDTO dto){
        ClienteEndereco bus = null;
        if(dto.getClienteEnderecoKey()!=null){
            bus = new ClienteEndereco();
            bus.setClienteEnderecoKey(new ClienteEnderecoKey());
            bus.getClienteEnderecoKey().setEndereco(this.dtoToBusiness(dto.getClienteEnderecoKey().getEndereco()));
            bus.getClienteEnderecoKey().setCliente(this.dtoToCliente(dto.getClienteEnderecoKey().getCliente()));
            bus.setEnderecoEntrega(dto.getEnderecoEntrega());
            bus.setEnderecoPrincipal(dto.getEnderecoPrincipal());
        }
        return bus;
    }

    private Endereco dtoToBusiness(EnderecoDTO dto){
        Endereco bus = new Endereco();
        bus.setId_endereco(dto.getId_endereco());
        bus.setBairro(dto.getBairro());
        bus.setCep(dto.getCep());
        bus.setCidade(dto.getCidade());
        bus.setComplemento(dto.getComplemento());
        bus.setEstado(dto.getEstado());
        bus.setNumero(dto.getNumero());
        bus.setPonto_referencia(dto.getPonto_referencia());
        bus.setRua(dto.getRua());
        return bus;
    }

    private EnderecoDTO businessToDto(Endereco bus){
        EnderecoDTO dto = new EnderecoDTO();
        dto.setId_endereco(bus.getId_endereco());
        dto.setBairro(bus.getBairro());
        dto.setCep(bus.getCep());
        dto.setCidade(bus.getCidade());
        dto.setComplemento(bus.getComplemento());
        dto.setEstado(bus.getEstado());
        dto.setNumero(bus.getNumero());
        dto.setPonto_referencia(bus.getPonto_referencia());
        dto.setRua(bus.getRua());
        return dto;
    }

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
        businessCliente.setId_Cliente(dtoCliente.getId_Cliente());
        businessCliente.setNome(dtoCliente.getNome());
        businessCliente.setCpf(dtoCliente.getCpf());
        businessCliente.setDataNascimento(dtoCliente.getDataNascimento());
        businessCliente.setEmail(dtoCliente.getEmail());
        businessCliente.setTelefone(dtoCliente.getTelefone());
        return businessCliente;
    }
}
