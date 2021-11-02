package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.Estoquekey;
import br.com.rd.ProjetoIntegrador.model.dto.*;
import br.com.rd.ProjetoIntegrador.model.entity.*;
import br.com.rd.ProjetoIntegrador.repository.EstoqueRepository;
import br.com.rd.ProjetoIntegrador.repository.PratoRepository;
import br.com.rd.ProjetoIntegrador.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstoqueService {
    @Autowired
    EstoqueRepository estoqueRepository;
    @Autowired
    ProdutoRepository produtoRepository;

    public EstoqueDTO create(EstoqueDTO dto){
        Estoque bus = this.dtoToBusiness(dto);
        if(bus!=null){
            if(bus.getEstoquekey()!=null){
                if(bus.getEstoquekey().getProduto()!=null){
                    if(produtoRepository.existsById(bus.getEstoquekey().getProduto().getId_produto())){
                        bus.getEstoquekey().setProduto(produtoRepository.getById(bus.getEstoquekey().getProduto().getId_produto()));
                        return this.busToDTO(estoqueRepository.save(bus));
                    }
                }
            }
        }
        return null;
    }

    public EstoqueDTO update(EstoqueDTO dto, Long id_produto){
        if(produtoRepository.existsById(id_produto)){
            Estoque bus = new Estoque();
            bus.setQuantidade(dto.getQuantidade());
            bus.setEstoquekey(new Estoquekey());
            bus.getEstoquekey().setProduto(this.produtoRepository.getById(id_produto));
            return this.busToDTO(estoqueRepository.save(bus));

        }
        return null;
    }

    public EstoqueDTO getById(Long id_produto){

        if(this.produtoRepository.existsById(id_produto)){
            Produto produto=this.produtoRepository.getById(id_produto);
            Estoquekey ek = new Estoquekey();
            ek.setProduto(produto);
            if(estoqueRepository.existsById(ek)){
                return this.busToDTO(estoqueRepository.getById(ek));
            }
        }

        return null;
    }

    public void delete(Long id_produto){
        if(this.produtoRepository.existsById(id_produto)){
            Produto produto=this.produtoRepository.getById(id_produto);
            Estoquekey ek = new Estoquekey();
            ek.setProduto(produto);
            if(estoqueRepository.existsById(ek)){
                estoqueRepository.deleteById(ek);
            }
        }
    }

    public List<EstoqueDTO> findAll(){
        return this.listToDto(this.estoqueRepository.findAll());
    }

    private List<EstoqueDTO> listToDto(List<Estoque> bus){
        List<EstoqueDTO> dto = new ArrayList<>();
        for (Estoque e : bus){
            dto.add(this.busToDTO(e));
        }
        return dto;
    }


    private EstoqueDTO busToDTO(Estoque bus){
        EstoqueDTO dto = new EstoqueDTO();
        if(bus.getEstoquekey()!=null){
            dto.setEstoqueKey(new EstoqueKeyDTO());
            if(bus.getEstoquekey().getProduto()!=null){
                ProdutoDTO produtoDTO = new ProdutoDTO();
                produtoDTO.setId_produto(bus.getEstoquekey().getProduto().getId_produto());
                produtoDTO.setNome_produto(bus.getEstoquekey().getProduto().getNome_produto());
                produtoDTO.setDescricao(bus.getEstoquekey().getProduto().getDescricao());
                produtoDTO.setIbu(bus.getEstoquekey().getProduto().getIbu());
                produtoDTO.setCor(bus.getEstoquekey().getProduto().getCor());
                produtoDTO.setTeor(bus.getEstoquekey().getProduto().getTeor());
                produtoDTO.setQuantidade_ml(bus.getEstoquekey().getProduto().getQuantidade_ml());
                produtoDTO.setEan(bus.getEstoquekey().getProduto().getEan());
                produtoDTO.setDestaque(bus.getEstoquekey().getProduto().getDestaque());
                produtoDTO.setDataDeCriacao(bus.getEstoquekey().getProduto().getDataDeCriacao());

                if (bus.getEstoquekey().getProduto().getFamilia() != null){
                    FamiliaDTO familiaDTO = new FamiliaDTO();
                    familiaDTO.setId_familia(bus.getEstoquekey().getProduto().getFamilia().getId_familia());
                    familiaDTO.setDescricao(bus.getEstoquekey().getProduto().getFamilia().getDescricao());
                    produtoDTO.setFamilia(familiaDTO);
                }

                if (bus.getEstoquekey().getProduto().getCategoria() != null){
                    CategoriaDTO categoriaDTO = new CategoriaDTO();
                    categoriaDTO.setId_Categoria(bus.getEstoquekey().getProduto().getCategoria().getId_Categoria());
                    categoriaDTO.setNome(bus.getEstoquekey().getProduto().getCategoria().getNome());
                    produtoDTO.setCategoria(categoriaDTO);
                }

                if (bus.getEstoquekey().getProduto().getMarca() != null){
                    MarcaDTO marcaDTO = new MarcaDTO();
                    marcaDTO.setId_marca(bus.getEstoquekey().getProduto().getMarca().getId_marca());
                    marcaDTO.setNome(bus.getEstoquekey().getProduto().getMarca().getNome());
                    produtoDTO.setMarca(marcaDTO);
                }

                if (bus.getEstoquekey().getProduto().getPrato() != null){
                    PratoDTO pratoDTO = new PratoDTO();
                    pratoDTO.setId_prato(bus.getEstoquekey().getProduto().getPrato().getId_prato());
                    pratoDTO.setDescricao(bus.getEstoquekey().getProduto().getPrato().getDescricao());
                    produtoDTO.setPrato(pratoDTO);
                }
                dto.getEstoqueKey().setProduto(produtoDTO);
                dto.setQuantidade(bus.getQuantidade());
                return dto;
            }
        }
        return null;
    }

    private Estoque dtoToBusiness(EstoqueDTO dto){
        Estoque bus = new Estoque();
        if(dto.getEstoqueKey()!= null){
            bus.setEstoquekey(new Estoquekey());
            if(dto.getEstoqueKey().getProduto()!=null){
                Produto produto = new Produto();
                produto.setId_produto(dto.getEstoqueKey().getProduto().getId_produto());
                produto.setNome_produto(dto.getEstoqueKey().getProduto().getNome_produto());
                produto.setDescricao(dto.getEstoqueKey().getProduto().getDescricao());
                produto.setIbu(dto.getEstoqueKey().getProduto().getIbu());
                produto.setCor(dto.getEstoqueKey().getProduto().getCor());
                produto.setTeor(dto.getEstoqueKey().getProduto().getTeor());
                produto.setQuantidade_ml(dto.getEstoqueKey().getProduto().getQuantidade_ml());
                produto.setEan(dto.getEstoqueKey().getProduto().getEan());
                produto.setDestaque(dto.getEstoqueKey().getProduto().getDestaque());
                produto.setDataDeCriacao(dto.getEstoqueKey().getProduto().getDataDeCriacao());

                if (dto.getEstoqueKey().getProduto().getFamilia() != null){
                    Familia f = new Familia();
                    if (dto.getEstoqueKey().getProduto().getFamilia().getId_familia() != null){
                        f.setId_familia(dto.getEstoqueKey().getProduto().getFamilia().getId_familia());
                    }else{
                        f.setDescricao(dto.getEstoqueKey().getProduto().getFamilia().getDescricao());
                    }
                    produto.setFamilia(f);
                }
                if (dto.getEstoqueKey().getProduto().getCategoria() != null){
                    Categoria c = new Categoria();
                    if (dto.getEstoqueKey().getProduto().getCategoria().getId_Categoria() != null){
                        c.setId_Categoria(dto.getEstoqueKey().getProduto().getCategoria().getId_Categoria());
                    }else {
                        c.setNome(dto.getEstoqueKey().getProduto().getCategoria().getNome());
                    }
                    produto.setCategoria(c);
                }

                if (dto.getEstoqueKey().getProduto().getMarca() != null){
                    Marca m = new Marca();
                    if (dto.getEstoqueKey().getProduto().getMarca().getId_marca() != null){
                        m.setId_marca(dto.getEstoqueKey().getProduto().getMarca().getId_marca());
                    }else  {
                        m.setNome(dto.getEstoqueKey().getProduto().getMarca().getNome());
                    }
                    produto.setMarca(m);
                }
                if (dto.getEstoqueKey().getProduto().getPrato() != null){
                    Prato p = new Prato();
                    if (dto.getEstoqueKey().getProduto().getPrato().getId_prato() != null){
                        p.setId_prato(dto.getEstoqueKey().getProduto().getPrato().getId_prato());
                    }else  {
                        p.setDescricao(dto.getEstoqueKey().getProduto().getPrato().getDescricao());
                    }
                    produto.setPrato(p);
                }
                bus.getEstoquekey().setProduto(produto);
                bus.setQuantidade(dto.getQuantidade());
                return bus;
            }
        }
        return null;
    }
}
