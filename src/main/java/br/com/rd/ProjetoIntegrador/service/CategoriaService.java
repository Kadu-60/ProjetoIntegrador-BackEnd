package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.model.dto.CategoriaDTO;
import br.com.rd.ProjetoIntegrador.model.entity.Categoria;
import br.com.rd.ProjetoIntegrador.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public CategoriaDTO addCategoria(CategoriaDTO categoria){
        Categoria novaCategoria = this.dtoToBusiness(categoria);
        categoriaRepository.save(novaCategoria);
        return this.businessToDTO(novaCategoria);

    }

    public List<CategoriaDTO> listarTodas(List<Categoria> lista){
        List<CategoriaDTO> listaDTO = new ArrayList<>();
        for (Categoria categoria: lista) {
            CategoriaDTO novaCategoria = this.businessToDTO(categoria);
            listaDTO.add(novaCategoria);
        }

        return listaDTO;
    }

    public List<CategoriaDTO> listaDTO(){
        List<Categoria> listaCategoria = categoriaRepository.findAll();
        return this.listarTodas(listaCategoria);
    }

    public CategoriaDTO buscarPorId(Long id){
        Optional<Categoria> op = categoriaRepository.findById(id);
        if(op.isPresent()){
            return businessToDTO(op.get());
        }
        return null;
    }

    public CategoriaDTO atualizarPorId(CategoriaDTO dto, Long id){
        Optional<Categoria> op = categoriaRepository.findById(id);
        if (op.isPresent()){
            Categoria categoria = op.get();
            if (dto.getNome() != null){
                categoria.setNome(dto.getNome());
            }

            categoriaRepository.save(categoria);
            return this.businessToDTO(categoria);
        }
        return null;
    }

    public void deletarPorId(Long id){
        categoriaRepository.deleteById(id);
    }

    private Categoria dtoToBusiness(CategoriaDTO dto){
        Categoria business = new Categoria();
        business.setNome(dto.getNome());

        return business;
    }

    private CategoriaDTO businessToDTO(Categoria business){
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId_Categoria(business.getId_Categoria());
        dto.setNome(business.getNome());

        return dto;
    }
}
