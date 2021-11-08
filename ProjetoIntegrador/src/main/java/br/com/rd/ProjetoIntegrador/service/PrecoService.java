package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.PrecoKey;
import br.com.rd.ProjetoIntegrador.model.dto.*;
import br.com.rd.ProjetoIntegrador.model.entity.*;
import br.com.rd.ProjetoIntegrador.repository.PrecoRepository;
import br.com.rd.ProjetoIntegrador.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class PrecoService {
    @Autowired
    PrecoRepository precoRepository;
    @Autowired
    ProdutoRepository produtoRepository;


    public Preco_VendaDTO create(Preco_VendaDTO dto){
        Preco_venda bus= this.dtoToBusiness(dto);
        if(bus.getPrecoKey()!=null){
            bus.getPrecoKey().setData_vigencia(new Date());
            if(this.produtoRepository.existsById(dto.getPrecoKey().getProduto().getId_produto())) {
                bus.getPrecoKey().setProduto(this.produtoRepository.getById(dto.getPrecoKey().getProduto().getId_produto()));
            }
            return this.businessToDto(this.precoRepository.save(bus));
        }
        return null;
    }

    public Preco_VendaDTO findLastPriceById_produto(Long id_produto){
        return this.businessToDto(this.precoRepository.findLastPriceById_produto(id_produto));
    }

    public List<Preco_VendaDTO> findAllById_produto(Long id_produto){
        return this.listToDTo(this.precoRepository.findAllById_produto(id_produto));
    }

    public List<Preco_VendaDTO> findAll(){
        return this.listToDTo(this.precoRepository.findAll());
    }

    private List<Preco_VendaDTO> listToDTo(List<Preco_venda> list){
        List<Preco_VendaDTO> dto = new ArrayList<>();
        for (Preco_venda p : list){
            dto.add(this.businessToDto(p));
        }
        return dto;
    }


    private Preco_venda dtoToBusiness(Preco_VendaDTO dto){
        Preco_venda bus = new Preco_venda();
        bus.setValor_preco(dto.getValor_preco());
        if(dto.getPrecoKey()!=null){
            bus.setPrecoKey(new PrecoKey());
            if(dto.getPrecoKey().getProduto()!=null){
                bus.getPrecoKey().setProduto(this.dtoToBusiness(dto.getPrecoKey().getProduto()));
            }
            if(dto.getPrecoKey().getData_vigencia()!=null){
                bus.getPrecoKey().setData_vigencia(dto.getPrecoKey().getData_vigencia());
            }
        }
        return bus;
    }

    private Preco_VendaDTO businessToDto(Preco_venda bus){
        Preco_VendaDTO dto = new Preco_VendaDTO();
        dto.setValor_preco(bus.getValor_preco());
        if(bus.getPrecoKey()!= null){
            dto.setPrecoKey(new PrecoKeyDTO());
            if(bus.getPrecoKey().getProduto()!=null){
                dto.getPrecoKey().setProduto(this.businessToDto(bus.getPrecoKey().getProduto()));
            }
            if(bus.getPrecoKey().getData_vigencia()!=null){
                dto.getPrecoKey().setData_vigencia(bus.getPrecoKey().getData_vigencia());
            }
        }
        return dto;
    }

    public  ProdutoDTO businessToDto(Produto business){
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId_produto(business.getId_produto());
        dto.setNome_produto(business.getNome_produto());
        dto.setDescricao(business.getDescricao());
        dto.setIbu(business.getIbu());
        dto.setCor(business.getCor());
        dto.setTeor(business.getTeor());
        dto.setQuantidade_ml(business.getQuantidade_ml());
        dto.setEan(business.getEan());
        dto.setDestaque(business.getDestaque());
        dto.setDataDeCriacao(business.getDataDeCriacao());

        if (business.getFamilia() != null){
            FamiliaDTO familiaDTO = new FamiliaDTO();
            familiaDTO.setId_familia(business.getFamilia().getId_familia());
            familiaDTO.setDescricao(business.getFamilia().getDescricao());
            dto.setFamilia(familiaDTO);
        }

        if (business.getCategoria() != null){
            CategoriaDTO categoriaDTO = new CategoriaDTO();
            categoriaDTO.setId_Categoria(business.getCategoria().getId_Categoria());
            categoriaDTO.setNome(business.getCategoria().getNome());
            dto.setCategoria(categoriaDTO);
        }

        if (business.getMarca() != null){
            MarcaDTO marcaDTO = new MarcaDTO();
            marcaDTO.setId_marca(business.getMarca().getId_marca());
            marcaDTO.setNome(business.getMarca().getNome());
            dto.setMarca(marcaDTO);
        }

        if (business.getPrato() != null){
            PratoDTO pratoDTO = new PratoDTO();
            pratoDTO.setId_prato(business.getPrato().getId_prato());
            pratoDTO.setDescricao(business.getPrato().getDescricao());
            dto.setPrato(pratoDTO);
        }
        return dto;
    }

    private Produto dtoToBusiness (ProdutoDTO dto){
        Produto business = new Produto();

        business.setNome_produto(dto.getNome_produto());
        business.setDescricao(dto.getDescricao());
        business.setIbu(dto.getIbu());
        business.setCor(dto.getCor());
        business.setTeor(dto.getTeor());
        business.setQuantidade_ml(dto.getQuantidade_ml());
        business.setEan(dto.getEan());
        business.setDestaque(dto.getDestaque());
        business.setDataDeCriacao(dto.getDataDeCriacao());

        if (dto.getFamilia() != null){
            Familia f = new Familia();
            if (dto.getFamilia().getId_familia() != null){
                f.setId_familia(dto.getFamilia().getId_familia());
            }else{
                f.setDescricao(dto.getFamilia().getDescricao());
            }
            business.setFamilia(f);
        }
        if (dto.getCategoria() != null){
            Categoria c = new Categoria();
            if (dto.getCategoria().getId_Categoria() != null){
                c.setId_Categoria(dto.getCategoria().getId_Categoria());
            }else {
                c.setNome(dto.getCategoria().getNome());
            }
            business.setCategoria(c);
        }

        if (dto.getMarca() != null){
            Marca m = new Marca();
            if (dto.getMarca().getId_marca() != null){
                m.setId_marca(dto.getMarca().getId_marca());
            }else  {
                m.setNome(dto.getMarca().getNome());
            }
            business.setMarca(m);
        }
        if (dto.getPrato() != null){
            Prato p = new Prato();
            if (dto.getPrato().getId_prato() != null){
                p.setId_prato(dto.getPrato().getId_prato());
            }else  {
                p.setDescricao(dto.getPrato().getDescricao());
            }
            business.setPrato(p);
        }
        return business;
    }


}
