package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.model.dto.ClienteDTO;
import br.com.rd.ProjetoIntegrador.model.dto.NfDTO;
import br.com.rd.ProjetoIntegrador.model.entity.Cliente;
import br.com.rd.ProjetoIntegrador.model.entity.Nf;
import br.com.rd.ProjetoIntegrador.repository.ClienteRepository;
import br.com.rd.ProjetoIntegrador.repository.NfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NfService {

    @Autowired
    NfRepository nfRepository;

    @Autowired
    ClienteRepository clienteRepository;

    public NfDTO addNf (NfDTO nf){
        nf.setEmissao(new Date());
        Nf novaNf = this.dtoToBusiness(nf);

        if (novaNf.getCliente() != null){
            Long id = novaNf.getCliente().getId_Cliente();
            Cliente cliente;
            if (id != null){
                cliente = this.clienteRepository.getById(id);
            }else{
                cliente = this.clienteRepository.save(novaNf.getCliente());
            }
            novaNf.setCliente(cliente);
        }


        novaNf = nfRepository.save(novaNf);
        return this.businessToDTO(novaNf);
    }

    public List<NfDTO> listarTodas(List<Nf> lista){
        List<NfDTO> listaDTO = new ArrayList<>();
        for (Nf nf: lista){
            NfDTO novaNf = this.businessToDTO(nf);
            listaDTO.add(novaNf);
        }
        return listaDTO;
    }

    public List<NfDTO> listaDTO(){
        List<Nf> listaNf = nfRepository.findAll();
        return this.listarTodas(listaNf);
    }

    public NfDTO buscarPorId(Long id){
        Optional<Nf> op = nfRepository.findById(id);
        if (op.isPresent()){
            return this.businessToDTO(op.get());
        }
        return null;
    }

    public NfDTO atualizarPorId(NfDTO dto, Long id){
        Optional<Nf> op = nfRepository.findById(id);
        if (op.isPresent()){
            Nf nf = op.get();
            if (dto.getChave_acesso() != null){
                nf.setChave_acesso(dto.getChave_acesso());
            }
            if (dto.getSerie() != null){
                nf.setSerie(dto.getSerie());
            }
            if (dto.getEmissao() != null){
                nf.setEmissao(dto.getEmissao());
            }
            if (dto.getSubtotal() != null){
                nf.setSubtotal(dto.getSubtotal());
            }
            if (dto.getTotal() != null){
                nf.setTotal(dto.getTotal());
            }
            if (dto.getCliente() != null){
                Long idCliente = nf.getCliente().getId_Cliente();
                Cliente cliente = this.clienteRepository.getById(idCliente);
                if (dto.getCliente().getNome() != null){
                    cliente.setNome(dto.getCliente().getNome());
                }
                if (dto.getCliente().getCpf() != null){
                    cliente.setCpf(dto.getCliente().getCpf());
                }
                if (dto.getCliente().getTelefone() != null){
                    cliente.setTelefone(dto.getCliente().getTelefone());
                }
                if (dto.getCliente().getEmail() != null){
                    cliente.setEmail(dto.getCliente().getEmail());
                }
                if (dto.getCliente().getDataNascimento() != null){
                    cliente.setDataNascimento(dto.getCliente().getDataNascimento());
                }
                this.clienteRepository.save(cliente);
                nf.setCliente(cliente);


            }
            nfRepository.save(nf);
            return this.businessToDTO(nf);
        }
        return null;
    }

//    public void deletarPorId(Long id){
//        nfRepository.deleteById(id);
//    }não se deve deletar uma NF

    private NfDTO businessToDTO (Nf business){
        NfDTO dto = new NfDTO();
        dto.setId_nf(business.getId_nf());
        dto.setChave_acesso(business.getChave_acesso());
        dto.setSerie(business.getSerie());
        dto.setEmissao(business.getEmissao());
        dto.setSubtotal(business.getSubtotal());
        dto.setTotal(business.getTotal());
        if (business.getCliente() != null){
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setId_Cliente(business.getCliente().getId_Cliente());
            clienteDTO.setNome(business.getCliente().getNome());
            clienteDTO.setCpf(business.getCliente().getCpf());
            clienteDTO.setEmail(business.getCliente().getEmail());
            clienteDTO.setTelefone(business.getCliente().getTelefone());
            clienteDTO.setDataNascimento(business.getCliente().getDataNascimento());
        }

        return dto;
    }

    private Nf dtoToBusiness (NfDTO dto){
        Nf business = new Nf();
        business.setChave_acesso(dto.getChave_acesso());
        business.setSerie(dto.getSerie());
        business.setEmissao(dto.getEmissao());
        business.setSubtotal(dto.getSubtotal());
        business.setTotal(dto.getTotal());
        if (dto.getCliente() != null){
            Cliente cliente = new Cliente();
            if (dto.getCliente().getId_Cliente() != null){
                cliente.setId_Cliente(dto.getCliente().getId_Cliente());
            }else{
                cliente.setNome(dto.getCliente().getNome());
                cliente.setCpf(dto.getCliente().getCpf());
                cliente.setEmail(dto.getCliente().getEmail());
                cliente.setTelefone(dto.getCliente().getTelefone());
                cliente.setDataNascimento(dto.getCliente().getDataNascimento());
            }
            business.setCliente(cliente);
        }
        return business;

    }
}
