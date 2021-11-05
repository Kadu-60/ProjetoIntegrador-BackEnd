package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.Item_pedido_key;
import br.com.rd.ProjetoIntegrador.model.dto.*;
import br.com.rd.ProjetoIntegrador.model.entity.*;
import br.com.rd.ProjetoIntegrador.repository.EstoqueRepository;
import br.com.rd.ProjetoIntegrador.repository.Item_pedidoRepository;
import br.com.rd.ProjetoIntegrador.repository.PedidoRepository;
import br.com.rd.ProjetoIntegrador.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Item_pedidoService {
    @Autowired
    Item_pedidoRepository item_pedidoRepository;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    EstoqueService estoqueService;


    public Item_pedidoDTO create(Item_pedidoDTO item_pedidoDTO){
        Item_pedido item_pedido = this.dtoToBusiness(item_pedidoDTO);
        if(item_pedidoDTO.getProduto() != null){
            Long id = item_pedidoDTO.getProduto().getId_produto();
            if(id!=null && this.produtoRepository.existsById(id)){
                Produto m = this.produtoRepository.getById(id);
                item_pedido.setProduto(m);
            }
        }
        if(item_pedidoDTO.getItem_pedido_key()!=null){
            item_pedido.setItem_pedido_key(new Item_pedido_key());
            if(item_pedidoDTO.getItem_pedido_key().getPedido()!=null){
                if(this.pedidoRepository.existsById(item_pedidoDTO.getItem_pedido_key().getPedido().getId())){
                    item_pedido.getItem_pedido_key().setPedido(this.pedidoRepository.getById(item_pedidoDTO.getItem_pedido_key().getPedido().getId()));
                }
            }
            if(item_pedidoDTO.getItem_pedido_key().getItem()!=null){
                item_pedido.getItem_pedido_key().setId_item(item_pedidoDTO.getItem_pedido_key().getItem());
            }
        }
        if(this.consultaEstoque(item_pedido.getProduto().getId_produto(), item_pedido.getQuantidade_produto())){
            return this.businessToDto(this.item_pedidoRepository.save(item_pedido));
        }else{
            return null;
        }

    }
    private Boolean consultaEstoque(Long id_produto, Integer quantidade){
        Integer qtdEmEstoque=this.estoqueService.getById(id_produto).getQuantidade();
        Boolean b = qtdEmEstoque>=quantidade;
        if(b){
            EstoqueDTO estoqueDTO=new EstoqueDTO();
            Integer qtdAtual = qtdEmEstoque-quantidade;
            estoqueDTO.setQuantidade(qtdAtual);
            this.estoqueService.update(estoqueDTO, id_produto);
        }
        return b;
    }
//    public Item_pedidoDTO update(Item_pedidoDTO item_pedidoDTO, Item_pedido_keyDTO item_pedido_keyDTO){
//        Item_pedido item_pedido = this.dtoToBusiness(item_pedidoDTO);
//        item_pedido.getItem_pedido_key().setId_item(item_pedido_keyDTO.getItem());
//        if(item_pedido_keyDTO.getPedido()!=null){
//            if(item_pedido_keyDTO.getPedido().getId()!=null){
//                if(this.pedidoRepository.existsById(item_pedido_keyDTO.getPedido().getId())){
//                    item_pedido.getItem_pedido_key().setPedido(this.pedidoRepository.getById(item_pedido_keyDTO.getPedido().getId()));
//                }
//            }
//        }
//        if(item_pedido.getItem_pedido_key()!=null){
//            if(item_pedido.getItem_pedido_key().getPedido()!=null){
//                if(item_pedido.getItem_pedido_key().getId_item()!=null){
//                    if(item_pedido.getItem_pedido_key().getPedido().getId()!=null){
//                        return this.businessToDto(this.item_pedidoRepository.save(item_pedido));
//                    }
//                }
//            }
//        }
//        return null;
//    }
    public Item_pedidoDTO findById(Item_pedido_keyDTO item_pedido_keyDTO){
        Item_pedido_key item_pedido_key = new Item_pedido_key();
        if(item_pedido_keyDTO.getPedido()!=null){
            if(item_pedido_keyDTO.getPedido().getId()!=null){
                if(this.pedidoRepository.existsById(item_pedido_keyDTO.getPedido().getId())){
                    item_pedido_key.setPedido(this.pedidoRepository.getById(item_pedido_keyDTO.getPedido().getId()));
                }
            }
        }
        if(item_pedido_keyDTO.getItem()!=null && item_pedido_key.getPedido()!=null){
            item_pedido_key.setId_item(item_pedido_keyDTO.getItem());
            if(this.item_pedidoRepository.existsById(item_pedido_key)) {
                return this.businessToDto(this.item_pedidoRepository.getById(item_pedido_key));
            }
        }

        return null;
    }
    public void delete(Item_pedido_keyDTO dto){
        Item_pedido_key bus = new Item_pedido_key();
        bus.setPedido(new Pedido());
        bus.getPedido().setId(dto.getPedido().getId());
        bus.setId_item(dto.getItem());
        if(this.item_pedidoRepository.existsById(bus)){
            this.item_pedidoRepository.deleteById(bus);
        }
    }
    public List<Item_pedidoDTO> findByidpedido(Long id){
        return this.litsToDTO(this.item_pedidoRepository.findById_pedido(id));
    }
    public List<Item_pedidoDTO> findAll(){
        return this.litsToDTO(this.item_pedidoRepository.findAll());
    }

    private List<Item_pedidoDTO> litsToDTO(List<Item_pedido> item_pedido){
        List<Item_pedidoDTO> list = new ArrayList<>();
        for(Item_pedido ip: item_pedido){
            list.add(this.businessToDto(ip));
        }
        return list;
    }


    private Item_pedidoDTO businessToDto(Item_pedido bussines){
        Item_pedidoDTO pedidoDTO = new Item_pedidoDTO();
        pedidoDTO.setQuantidade_produto(bussines.getQuantidade_produto());
        pedidoDTO.setDesconto_unitario(bussines.getDesconto_unitario());
        pedidoDTO.setValor_total(bussines.getValor_total());
        pedidoDTO.setValor_unitario(bussines.getValor_unitario());
        if(bussines.getProduto()!=null){
            ProdutoDTO dto = new ProdutoDTO();
            dto.setId_produto(bussines.getProduto().getId_produto());
            dto.setNome_produto(bussines.getProduto().getNome_produto());
            dto.setDescricao(bussines.getProduto().getDescricao());
            dto.setIbu(bussines.getProduto().getIbu());
            dto.setCor(bussines.getProduto().getCor());
            dto.setTeor(bussines.getProduto().getTeor());
            dto.setQuantidade_ml(bussines.getProduto().getQuantidade_ml());
            dto.setEan(bussines.getProduto().getEan());
            dto.setDestaque(bussines.getProduto().getDestaque());
            dto.setDataDeCriacao(bussines.getProduto().getDataDeCriacao());

            if (bussines.getProduto().getFamilia() != null){
                FamiliaDTO familiaDTO = new FamiliaDTO();
                familiaDTO.setId_familia(bussines.getProduto().getFamilia().getId_familia());
                familiaDTO.setDescricao(bussines.getProduto().getFamilia().getDescricao());
                dto.setFamilia(familiaDTO);
            }

            if (bussines.getProduto().getCategoria() != null){
                CategoriaDTO categoriaDTO = new CategoriaDTO();
                categoriaDTO.setId_Categoria(bussines.getProduto().getCategoria().getId_Categoria());
                categoriaDTO.setNome(bussines.getProduto().getCategoria().getNome());
                dto.setCategoria(categoriaDTO);
            }

            if (bussines.getProduto().getMarca() != null){
                MarcaDTO marcaDTO = new MarcaDTO();
                marcaDTO.setId_marca(bussines.getProduto().getMarca().getId_marca());
                marcaDTO.setNome(bussines.getProduto().getMarca().getNome());
                dto.setMarca(marcaDTO);
            }

            if (bussines.getProduto().getPrato() != null){
                PratoDTO pratoDTO = new PratoDTO();
                pratoDTO.setId_prato(bussines.getProduto().getPrato().getId_prato());
                pratoDTO.setDescricao(bussines.getProduto().getPrato().getDescricao());
                dto.setPrato(pratoDTO);
            }
            pedidoDTO.setProduto(dto);
        }
        if(bussines.getItem_pedido_key()!=null){
            pedidoDTO.setItem_pedido_key(new Item_pedido_keyDTO());
            if(bussines.getItem_pedido_key().getId_item()!=null){
                pedidoDTO.getItem_pedido_key().setItem(bussines.getItem_pedido_key().getId_item());
            }
            if(bussines.getItem_pedido_key().getPedido()!=null){
                PedidoDTO dto = new PedidoDTO();
                dto.setId(bussines.getItem_pedido_key().getPedido().getId());
                dto.setTotal(bussines.getItem_pedido_key().getPedido().getTotal());
                dto.setSubtotal(bussines.getItem_pedido_key().getPedido().getSubtotal());
                dto.setDataDeCriacao(bussines.getItem_pedido_key().getPedido().getDataDeCriacao());
                dto.setFrete(bussines.getItem_pedido_key().getPedido().getFrete());
                if(bussines.getItem_pedido_key().getPedido().getCartao()!= null) {
                    CartaoDTO cartao = new CartaoDTO();
                    cartao.setId_Cartao(bussines.getItem_pedido_key().getPedido().getCartao().getId_Cartao());
                    cartao.setNome(bussines.getItem_pedido_key().getPedido().getCartao().getNome());
                    cartao.setNumero(bussines.getItem_pedido_key().getPedido().getCartao().getNumero());
                    cartao.setValidade(bussines.getItem_pedido_key().getPedido().getCartao().getValidade());
                    dto.setCartao(cartao);
                }
                if(bussines.getItem_pedido_key().getPedido().getCliente()!= null) {
                    ClienteDTO cliente = new ClienteDTO();
                    cliente.setId_Cliente(bussines.getItem_pedido_key().getPedido().getCliente().getId_Cliente());
                    cliente.setNome(bussines.getItem_pedido_key().getPedido().getCliente().getNome());
                    cliente.setCpf(bussines.getItem_pedido_key().getPedido().getCliente().getCpf());
                    cliente.setEmail(bussines.getItem_pedido_key().getPedido().getCliente().getEmail());
                    cliente.setTelefone(bussines.getItem_pedido_key().getPedido().getCliente().getTelefone());
                    cliente.setDataNascimento(bussines.getItem_pedido_key().getPedido().getCliente().getDataNascimento());
                    dto.setCliente(cliente);
                }
                if(bussines.getItem_pedido_key().getPedido().getEndereco()!= null) {
                    EnderecoDTO endereco = new EnderecoDTO();
                    endereco.setId_endereco(bussines.getItem_pedido_key().getPedido().getEndereco().getId_endereco());
                    endereco.setRua(bussines.getItem_pedido_key().getPedido().getEndereco().getRua());
                    endereco.setPonto_referencia(bussines.getItem_pedido_key().getPedido().getEndereco().getPonto_referencia());
                    endereco.setEstado(bussines.getItem_pedido_key().getPedido().getEndereco().getEstado());
                    endereco.setComplemento(bussines.getItem_pedido_key().getPedido().getEndereco().getComplemento());
                    endereco.setCidade(bussines.getItem_pedido_key().getPedido().getEndereco().getCidade());
                    endereco.setNumero(bussines.getItem_pedido_key().getPedido().getEndereco().getNumero());
                    endereco.setCep(bussines.getItem_pedido_key().getPedido().getEndereco().getCep());
                    endereco.setBairro(bussines.getItem_pedido_key().getPedido().getEndereco().getBairro());
                    dto.setEndereco(endereco);
                }
                if(bussines.getItem_pedido_key().getPedido().getParcelamento() != null) {
                    ParcelamentoDTO formaPagamento = new ParcelamentoDTO();
                    formaPagamento.setId_parcelamento(bussines.getItem_pedido_key().getPedido().getParcelamento().getId_parcelamento());
                    formaPagamento.setParcelamento(bussines.getItem_pedido_key().getPedido().getParcelamento().getParcelamento());
                    dto.setPagamento(formaPagamento);
                }
                if(bussines.getItem_pedido_key().getPedido().getStatusEntrega()!= null) {
                    StatusPedidoDTO statusPedido = new StatusPedidoDTO();
                    statusPedido.setId_status_pedido(bussines.getItem_pedido_key().getPedido().getStatusEntrega().getId_status_pedido());
                    statusPedido.setEstado_pedido(bussines.getItem_pedido_key().getPedido().getStatusEntrega().getEstado_pedido());
                    dto.setStatus(statusPedido);
                }
                pedidoDTO.getItem_pedido_key().setPedido(dto);
            }

        }
        return pedidoDTO;

    }

    private Item_pedido dtoToBusiness(Item_pedidoDTO dto){
        Item_pedido bus = new Item_pedido();
        bus.setQuantidade_produto(dto.getQuantidade_produto());
        bus.setDesconto_unitario(dto.getDesconto_unitario());
        bus.setValor_unitario(dto.getValor_unitario());
        bus.setValor_total(dto.getValor_total());
        if(dto.getItem_pedido_key()!=null){
            bus.setItem_pedido_key(new Item_pedido_key());
            if(dto.getItem_pedido_key().getItem()!=null){
                bus.getItem_pedido_key().setId_item(dto.getItem_pedido_key().getItem());
            }
            if(dto.getItem_pedido_key().getPedido()!=null){
                Pedido pedidobus = new Pedido();
                pedidobus.setId(dto.getItem_pedido_key().getPedido().getId());
                pedidobus.setTotal(dto.getItem_pedido_key().getPedido().getTotal());
                pedidobus.setSubtotal(dto.getItem_pedido_key().getPedido().getSubtotal());
                pedidobus.setDataDeCriacao(dto.getItem_pedido_key().getPedido().getDataDeCriacao());
                if(dto.getItem_pedido_key().getPedido().getCartao() != null){
                    Cartao m = new Cartao();
                    if(dto.getItem_pedido_key().getPedido().getCartao().getId_Cartao() != null){
                        m.setId_Cartao(dto.getItem_pedido_key().getPedido().getId());
                    }else{
                        m.setNome(dto.getItem_pedido_key().getPedido().getCartao().getNome());
                        m.setNumero(dto.getItem_pedido_key().getPedido().getCartao().getNumero());
                        m.setValidade(dto.getItem_pedido_key().getPedido().getCartao().getValidade());
                    }
                    pedidobus.setCartao(m);
                }
                if(dto.getItem_pedido_key().getPedido().getCliente() != null){
                    Cliente m = new Cliente();
                    if(dto.getItem_pedido_key().getPedido().getCliente().getId_Cliente() != null){
                        m.setId_Cliente(dto.getItem_pedido_key().getPedido().getCliente().getId_Cliente());
                    }else{
                        m.setCpf(dto.getItem_pedido_key().getPedido().getCliente().getCpf());
                        m.setEmail(dto.getItem_pedido_key().getPedido().getCliente().getEmail());
                        m.setDataNascimento(dto.getItem_pedido_key().getPedido().getCliente().getDataNascimento());
                        m.setNome(dto.getItem_pedido_key().getPedido().getCliente().getNome());
                        m.setTelefone(dto.getItem_pedido_key().getPedido().getCliente().getTelefone());
                    }
                    pedidobus.setCliente(m);
                }
                if(dto.getItem_pedido_key().getPedido().getEndereco() != null){
                    Endereco m = new Endereco();
                    if(dto.getItem_pedido_key().getPedido().getEndereco().getId_endereco() != null){
                        m.setId_endereco(dto.getItem_pedido_key().getPedido().getEndereco().getId_endereco());
                    }else{
                        m.setComplemento(dto.getItem_pedido_key().getPedido().getEndereco().getComplemento());
                        m.setBairro(dto.getItem_pedido_key().getPedido().getEndereco().getBairro());
                        m.setCidade(dto.getItem_pedido_key().getPedido().getEndereco().getCidade());
                        m.setCep(dto.getItem_pedido_key().getPedido().getEndereco().getCep());
                        m.setRua(dto.getItem_pedido_key().getPedido().getEndereco().getRua());
                        m.setPonto_referencia(dto.getItem_pedido_key().getPedido().getEndereco().getPonto_referencia());
                        m.setEstado(dto.getItem_pedido_key().getPedido().getEndereco().getEstado());
                        m.setNumero(dto.getItem_pedido_key().getPedido().getEndereco().getNumero());
                    }
                    pedidobus.setEndereco(m);
                }
                if(dto.getItem_pedido_key().getPedido().getPagamento() != null){
                    Parcelamento m = new Parcelamento();
                    if(dto.getItem_pedido_key().getPedido().getPagamento().getId_parcelamento() != null){
                        m.setId_parcelamento(dto.getItem_pedido_key().getPedido().getPagamento().getId_parcelamento());
                    }else{
                        m.setId_parcelamento(dto.getItem_pedido_key().getPedido().getPagamento().getId_parcelamento());
                    }
                    pedidobus.setParcelamento(m);
                }
                if(dto.getItem_pedido_key().getPedido().getNf() != null){
                    Nf m = new Nf();
                    if(dto.getItem_pedido_key().getPedido().getNf().getId_nf() != null){
                        m.setId_nf(dto.getItem_pedido_key().getPedido().getNf().getId_nf());
                    }else{
                        Cliente businessCliente = new Cliente();
                        businessCliente.setNome(dto.getItem_pedido_key().getPedido().getCliente().getNome());
                        businessCliente.setCpf(dto.getItem_pedido_key().getPedido().getCliente().getCpf());
                        businessCliente.setDataNascimento(dto.getItem_pedido_key().getPedido().getCliente().getDataNascimento());
                        businessCliente.setEmail(dto.getItem_pedido_key().getPedido().getCliente().getEmail());
                        businessCliente.setTelefone(dto.getItem_pedido_key().getPedido().getCliente().getTelefone());
                        m.setCliente(businessCliente);
                        m.setChave_acesso(dto.getItem_pedido_key().getPedido().getNf().getChave_acesso());
                        m.setSubtotal(dto.getItem_pedido_key().getPedido().getNf().getSubtotal());
                        m.setTotal(dto.getItem_pedido_key().getPedido().getNf().getTotal());
                        m.setEmissao(dto.getItem_pedido_key().getPedido().getNf().getEmissao());
                        m.setSerie(dto.getItem_pedido_key().getPedido().getNf().getSerie());

                    }
                    pedidobus.setNf(m);
                }
                if(dto.getItem_pedido_key().getPedido().getStatus() != null){
                    StatusPedido m = new StatusPedido();
                    if(dto.getItem_pedido_key().getPedido().getStatus().getId_status_pedido()!=null){
                        m.setId_status_pedido(dto.getItem_pedido_key().getPedido().getStatus().getId_status_pedido());
                    }else{
                        m.setEstado_pedido(dto.getItem_pedido_key().getPedido().getStatus().getEstado_pedido());
                    }
                    pedidobus.setStatusEntrega(m);
                }
                bus.getItem_pedido_key().setPedido(pedidobus);
            }
        }
        if(dto.getProduto()!=null){
            Produto business = new Produto();
            business.setNome_produto(dto.getProduto().getNome_produto());
            business.setDescricao(dto.getProduto().getDescricao());
            business.setIbu(dto.getProduto().getIbu());
            business.setCor(dto.getProduto().getCor());
            business.setTeor(dto.getProduto().getTeor());
            business.setQuantidade_ml(dto.getProduto().getQuantidade_ml());
            business.setEan(dto.getProduto().getEan());
            business.setDestaque(dto.getProduto().getDestaque());
            business.setDataDeCriacao(dto.getProduto().getDataDeCriacao());

            if (dto.getProduto().getFamilia() != null){
                Familia f = new Familia();
                if (dto.getProduto().getFamilia().getId_familia() != null){
                    f.setId_familia(dto.getProduto().getFamilia().getId_familia());
                }else{
                    f.setDescricao(dto.getProduto().getFamilia().getDescricao());
                }
                business.setFamilia(f);
            }
            if (dto.getProduto().getCategoria() != null){
                Categoria c = new Categoria();
                if (dto.getProduto().getCategoria().getId_Categoria() != null){
                    c.setId_Categoria(dto.getProduto().getCategoria().getId_Categoria());
                }else {
                    c.setNome(dto.getProduto().getCategoria().getNome());
                }
                business.setCategoria(c);
            }

            if (dto.getProduto().getMarca() != null){
                Marca m = new Marca();
                if (dto.getProduto().getMarca().getId_marca() != null){
                    m.setId_marca(dto.getProduto().getMarca().getId_marca());
                }else  {
                    m.setNome(dto.getProduto().getMarca().getNome());
                }
                business.setMarca(m);
            }
            if (dto.getProduto().getPrato() != null){
                Prato p = new Prato();
                if (dto.getProduto().getPrato().getId_prato() != null){
                    p.setId_prato(dto.getProduto().getPrato().getId_prato());
                }else  {
                    p.setDescricao(dto.getProduto().getPrato().getDescricao());
                }
                business.setPrato(p);
            }
            bus.setProduto(business);
        }
        return bus;
    }
}
