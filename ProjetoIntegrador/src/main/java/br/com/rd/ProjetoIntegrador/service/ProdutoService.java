package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.model.dto.*;
import br.com.rd.ProjetoIntegrador.model.entity.*;
import br.com.rd.ProjetoIntegrador.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    FamiliaRepository familiaRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    MarcaRepository marcaRepository;
    @Autowired
    PratoRepository pratoRepository;


    public ProdutoDTO addProduto(ProdutoDTO produto){
        Produto newProduto = this.dtoToBusiness(produto);

        Long id_familia = newProduto.getFamilia().getId_familia();
        if (id_familia != null){
            Familia f = this.familiaRepository.getById(id_familia);
            newProduto.setFamilia(f);
        }
        Long id_categoria = newProduto.getCategoria().getId_Categoria();
        if (id_categoria != null){
            Categoria c = this.categoriaRepository.getById(id_categoria);
            newProduto.setCategoria(c);
        }
        Long id_marca = newProduto.getMarca().getId_marca();
        if (id_marca != null){
            Marca m = this.marcaRepository.getById(id_marca);
            newProduto.setMarca(m);
        }
        Long id_prato = newProduto.getPrato().getId_prato();
        if (id_marca != null){
            Prato p = this.pratoRepository.getById(id_prato);
            newProduto.setPrato(p);
        }
        newProduto.setDataDeCriacao(new Date());
        newProduto = produtoRepository.save(newProduto);
        return this.businessToDto(newProduto);
    }

    public CardProdutoDTO findCardProdutoById_produto(Long id){
        try{
            ResultSet rs =  this.produtoRepository.findCardProdutoById_produto(id);
            CardProdutoDTO card = new CardProdutoDTO();
            while(rs.next()) {
                System.out.println(rs.getString("nome_produto"));
                card.setId_produto(rs.getLong("id_produto"));
                card.setFoto(rs.getString("foto"));
                card.setValor_preco(rs.getDouble("valor_preco"));
                card.setNome(rs.getString("nome_produto"));
                card.setDesc(rs.getString("descricao"));
            }
            return card;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }
    public List<ProdutoDTO> findAllProduto(){
        List<Produto> allList = produtoRepository.findAll();
        return this.listToProdutoDto(allList);
    }


    public List<ProdutoDTO> listToProdutoDto(List<Produto> list){
        List<ProdutoDTO> listDto = new ArrayList<ProdutoDTO>();
        for (Produto p : list){
            listDto.add(this.businessToDto(p));
        }
        return listDto;
    }

    public ProdutoDTO searchByProdutoId(Long id){
        Optional<Produto> op = produtoRepository.findById(id);

        if(op.isPresent()){
            return businessToDto(op.get());
        }
        return null;
    }
    public List<ProdutoDTO> buscaAvan(Long id_cat, Long id_marca, Long id_fam, Long id_prato){
        String id_catSt;
        String id_marcaSt;
        String id_famSt;
        String id_pratoSt;
        if(id_cat==-1l){
            id_catSt = "> 0";
        }else{
            id_catSt = "= "+id_cat;
        }

        if(id_marca==-1l){
            id_marcaSt = "> 0";
        }else{
            id_marcaSt = "= "+id_marca;
        }

        if(id_fam==-1l){
            id_famSt = "> 0";
        }else{
            id_famSt = "= "+id_fam;
        }

        if(id_prato==-1l){
            id_pratoSt = "> 0";
        }else{
            id_pratoSt = "= "+id_prato;
        }
        return this.listToProdutoDto(this.produtoRepository.buscaAvancada(id_catSt, id_marcaSt, id_famSt, id_pratoSt));
    }

    public ProdutoDTO updateByProdutoId(ProdutoDTO dto, Long id){
        Optional<Produto> op = produtoRepository.findById(id);

        if(op.isPresent()){
            Produto pt = op.get();
            if (dto.getNome_produto() != null) {
                pt.setNome_produto(dto.getNome_produto());
            }
            if (dto.getDescricao() != null) {
                pt.setDescricao(dto.getDescricao());
            }
            if (dto.getIbu() != null) {
                pt.setIbu(dto.getIbu());
            }
            if (dto.getCor() != null) {
                pt.setCor(dto.getCor());
            }
            if (dto.getTeor() != null) {
                pt.setTeor(dto.getTeor());
            }
            if (dto.getQuantidade_ml() != null) {
                pt.setQuantidade_ml(dto.getQuantidade_ml());
            }
            if (dto.getEan() != null) {
                pt.setEan(dto.getEan());
            }
            if (dto.getDestaque() != null){
                pt.setDestaque(dto.getDestaque());
            }

            if (dto.getFamilia() != null){
                Familia familia = new Familia();
                if (dto.getFamilia().getId_familia() != null){
                    familia.setId_familia(dto.getFamilia().getId_familia());
                    if (familiaRepository.existsById(pt.getFamilia().getId_familia())){
                        pt.setFamilia(familiaRepository.getById(dto.getFamilia().getId_familia()));
                    }else{
                        familia.setDescricao(dto.getFamilia().getDescricao());
                        pt.setFamilia(familia);
                        familiaRepository.save(pt.getFamilia());
                    }
                }
            }
            if (dto.getCategoria() != null){
                Categoria categoria = new Categoria();
                if (dto.getCategoria().getId_Categoria() != null){
                    categoria.setId_Categoria(dto.getCategoria().getId_Categoria());
                    if (categoriaRepository.existsById(pt.getCategoria().getId_Categoria())){
                        pt.setCategoria(categoriaRepository.getById(dto.getCategoria().getId_Categoria()));
                    }else{
                        categoria.setNome(dto.getCategoria().getNome());
                        pt.setCategoria(categoria);
                        categoriaRepository.save(pt.getCategoria());
                    }
                }
            }
            if (dto.getMarca() != null){
                Marca marca = new Marca();
                if (dto.getMarca().getId_marca() != null){
                    marca.setId_marca(dto.getMarca().getId_marca());
                    if (marcaRepository.existsById(pt.getMarca().getId_marca())){
                        pt.setMarca(marcaRepository.getById(dto.getMarca().getId_marca()));
                    }else{
                        marca.setNome(dto.getMarca().getNome());
                        pt.setMarca(marca);
                        marcaRepository.save(pt.getMarca());
                    }
                }
            }
            if (dto.getPrato() != null){
                Prato prato = new Prato();
                if (dto.getPrato().getId_prato() != null){
                    prato.setId_prato(dto.getPrato().getId_prato());
                    if (pratoRepository.existsById(pt.getPrato().getId_prato())){
                        pt.setPrato(pratoRepository.getById(dto.getPrato().getId_prato()));
                    }else{
                        prato.setDescricao(dto.getPrato().getDescricao());
                        pt.setPrato(prato);
                        pratoRepository.save(pt.getPrato());
                    }
                }
            }
                produtoRepository.save(pt);
                return  businessToDto(pt);
        }
        return null;
    }

    public void deleteByProdutoId(Long id){
        if (produtoRepository.existsById(id)){
            produtoRepository.deleteById(id);
        }
    }

    public List<ProdutoDTO> listaProdutoMarca(Long id){
    List<Produto> produtoList = produtoRepository.findByMarca(id);
    return listToProdutoDto(produtoList);
    }

    public List<ProdutoDTO> listaProdutoCategoria(Long id){
        List<Produto> produtoList = produtoRepository.findByCategoria(id);
        return listToProdutoDto(produtoList);
    }

    public List<ProdutoDTO> listaProdutoFamilia(Long id){
        List<Produto> produtoList = produtoRepository.findByFamilia(id);
        return listToProdutoDto(produtoList);
    }

    public  List<ProdutoDTO> buscaPorDestaque(){
        List<Produto> listaProduto = produtoRepository.findByDestaqueTrue();
        return listToProdutoDto(listaProduto);
    }

    public List<ProdutoDTO> buscaPorNovidade(){
        return listToProdutoDto(this.produtoRepository.buscaNovidades());
    }

    public List<ProdutoDTO> listaProdutoPrato(Long id){
        List<Produto> produtoList = produtoRepository.findByPrato(id);
        return listToProdutoDto(produtoList);
    }
    public List<ProdutoDTO> pesquisarProdutos(String nome_produto){
        List<Produto> produtoList = produtoRepository.pesquisarProdutos(nome_produto);
        return listToProdutoDto(produtoList);
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




}
