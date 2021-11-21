package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.model.dto.MarcaDTO;
import br.com.rd.ProjetoIntegrador.model.entity.Marca;
import br.com.rd.ProjetoIntegrador.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MarcaService {

    @Autowired
    MarcaRepository marcaRepository;

    public MarcaDTO addMarca(MarcaDTO marca){
        Marca novaMarca = this.dtoToBusiness(marca);
        novaMarca = marcaRepository.save(novaMarca);
        return this.businessToDTO(novaMarca);
    }

    public List<MarcaDTO> listarTodas(List<Marca> lista){
        List<MarcaDTO> listaDTO = new ArrayList<>();
        for (Marca marca: lista) {
            MarcaDTO novaMarca = this.businessToDTO(marca);
            listaDTO.add(novaMarca);

        }

        return listaDTO;
    }

    public List<MarcaDTO> listaDTO(){
        List<Marca> listaMarca = marcaRepository.findAll();
        return this.listarTodas(listaMarca);
    }

    public MarcaDTO buscarPorId(Long id){
        Optional<Marca> op = marcaRepository.findById(id);
        if(op.isPresent()){
            return this.businessToDTO(op.get());
        }
        return null;
    }

    public MarcaDTO atualizarPorId(MarcaDTO dto, Long id){
        Optional<Marca> op = marcaRepository.findById(id);
        if (op.isPresent()){
            Marca marca = op.get();
            if (dto.getNome() != null){
                marca.setNome(dto.getNome());
            }
            marcaRepository.save(marca);
            return this.businessToDTO(marca);
        }
        return null;
    }

    public void deletarPorId(Long id){
        marcaRepository.deleteById(id);
    }

    private Marca dtoToBusiness (MarcaDTO dto){
        Marca business = new Marca();
        business.setNome(dto.getNome());
        return business;
    }

    private MarcaDTO businessToDTO (Marca business){
        MarcaDTO dto = new MarcaDTO();
        dto.setId_marca(business.getId_marca());
        dto.setNome(business.getNome());
        dto.setImg(business.getImg());
        dto.setDesc(business.getDescricao());

        return dto;
    }
}
