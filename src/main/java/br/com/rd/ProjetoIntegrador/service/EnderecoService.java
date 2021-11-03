package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.model.dto.EnderecoDTO;
import br.com.rd.ProjetoIntegrador.model.entity.Endereco;
import br.com.rd.ProjetoIntegrador.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;

    public EnderecoDTO create(EnderecoDTO dto){
        Endereco bus = this.dtoToBusiness(dto);
        return this.businessToDto(this.enderecoRepository.save(bus));
    }
    public List<EnderecoDTO> findAll(){
        List<Endereco> responseList = this.enderecoRepository.findAll();
        return this.listToDTO(responseList);
    }

    public EnderecoDTO findById(Long id){
        Optional<Endereco> endereco = this.enderecoRepository.findById(id);
        EnderecoDTO response = null;
        if(endereco.isPresent()){
            response = businessToDto(endereco.get());
        }
        return response;
    }

    public EnderecoDTO updateById(Long id, EnderecoDTO dto){
        Optional<Endereco> find = this.enderecoRepository.findById(id);
        if (find.isPresent()){
            if(find.get() instanceof Endereco){
                Endereco e = find.get();
                e.setId_endereco(id);
                if(dto.getNumero() != null){
                    e.setNumero(dto.getNumero());
                }
                if(dto.getRua() != null){
                    e.setRua(dto.getRua());
                }
                if(dto.getBairro() != null){
                    e.setBairro(dto.getBairro());
                }
                if(dto.getCidade() != null){
                    e.setCidade(dto.getCidade());
                }
                if(dto.getCep() != null){
                    e.setCep(dto.getCep());
                }
                if(dto.getPonto_referencia() != null){
                    e.setPonto_referencia(dto.getPonto_referencia());
                }
                if(dto.getComplemento() != null){
                    e.setComplemento(dto.getComplemento());
                }
                this.enderecoRepository.save(e);
                return this.businessToDto(e);
            }
        }
        return null;
    }

    public  void deleteById(Long id){
        if(this.enderecoRepository.existsById(id)){
            this.enderecoRepository.deleteById(id);
        }
    }

    private List<EnderecoDTO> listToDTO(List<Endereco> list){
        List<EnderecoDTO> dto = new ArrayList<>();
        for(Endereco e:list){
            dto.add(this.businessToDto(e));
        }
        return dto;
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
}
