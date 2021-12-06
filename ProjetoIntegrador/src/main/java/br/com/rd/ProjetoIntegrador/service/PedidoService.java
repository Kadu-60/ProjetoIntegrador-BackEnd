package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.Item_Nf_Key;
import br.com.rd.ProjetoIntegrador.model.dto.*;
import br.com.rd.ProjetoIntegrador.model.entity.*;
import br.com.rd.ProjetoIntegrador.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ParcelamentoRepository parcelamentoRepository;
    @Autowired
    StatusPedidoRepository statusPedidoRepository;
    @Autowired
    NfRepository nfRepository;
    @Autowired
    ItemPedidoService item_pedidoService;
    @Autowired
    EstoqueService estoqueService;
    @Autowired
    PrecoService precoService;
    @Autowired
    Item_NfRepository item_nfRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    MetodoPagRepository metodoPagRepository;

    public PedidoDTO create(PedidoDTO dto){
        Pedido pedido = this.dtoToBusiness(dto);
        if(pedido.getCliente() != null){
            Long id = pedido.getCliente().getId_Cliente();
            if(id!= null){
                Cliente m = this.clienteRepository.getById(id);
                pedido.setCliente(m);
            }
        }
        if(pedido.getParcelamento()!= null){
            Long id = pedido.getParcelamento().getId_parcelamento();
            if(id!=null){
                Parcelamento m = this.parcelamentoRepository.getById(id);
                pedido.setParcelamento(m);
            }
        }
        if(pedido.getMetodoPag()!= null){
            Long id = pedido.getMetodoPag().getId_metodoPag();
            if(id!=null){
                MetodoPag m = this.metodoPagRepository.getById(id);
                pedido.setMetodoPag(m);
            }
        }
        if(pedido.getStatusEntrega()!=null){
            Long id = pedido.getStatusEntrega().getId_status_pedido();
            if(id!=null){
                StatusPedido m = this.statusPedidoRepository.getById(id);
                pedido.setStatusEntrega(m);
            }
        }
        pedido.setDataDeCriacao(new Date());
        pedido.setTotal(15d);
        pedido.setSubtotal(0d);
        pedido.setFinalizado(false);
        pedido = this.pedidoRepository.save(pedido);
//            Criando o Email de confirmação de Pedido
        EmailModel em = new EmailModel();
        em.setEmailFrom("projetodevbrew@gmail.com");
        em.setEmailTo(pedido.getCliente().getEmail());
        em.setSubject("Pedido "+pedido.getId());
        em.setText("É ótimo descobrir que os nossos clientes prezam pela qualidade e bom atendimento. \nObrigado por comprar com conosco.");
        em.setOwnerRef("projetodevbrew@gmail.com");
        this.emailService.sendEmail(em);
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("\t\t\tEMAIL ENVIADO");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
//            Criando o Email de confirmação de Pedido
        return this.businessToDTO(pedido);
    }

    public PedidoDTO updateStatusById(StatusPedidoDTO sp, Long id){
        Optional<Pedido> op = pedidoRepository.findById(id);

        if(op.isPresent()){
            Pedido obj = op.get();
            if(sp.getId_status_pedido()!= null) {
                obj.setStatusEntrega(this.statusPedidoRepository.getById(sp.getId_status_pedido()));
            }else{
                StatusPedido spbus = new StatusPedido();
                spbus.setEstado_pedido(sp.getEstado_pedido());
                obj.setStatusEntrega(spbus);
            }
            pedidoRepository.save(obj);
            return businessToDTO(obj);
        }
        return null;
    }
    public NfDTO gerarNf(PedidoDTO pedidoDTO){
        if(pedidoRepository.existsById(pedidoDTO.getId())){

            Pedido pedido = pedidoRepository.findById(pedidoDTO.getId()).get();
            if(pedido.getNf()==null){
                Nf business = new Nf();
                business.setSerie(""+pedido.getId());
                business.setChave_acesso(pedido.getId());
                business.setSubtotal(pedido.getSubtotal());
                business.setTotal(pedido.getTotal());
                business.setCliente(pedido.getCliente());
                business.setEmissao(new Date());
                business = nfRepository.save(business);
                pedido.setNf(business);
                pedido.setFinalizado(true);
                this.pedidoRepository.save(pedido);
//            Conversão de nf business to nf dto
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
                    dto.setCliente(clienteDTO);
                }
//            Conversão de nf business to nf dto
//            Criando os Itens NF apartir dos itens pedido
                List<Item_pedidoDTO> listItens = this.item_pedidoService.findByidpedido(pedido.getId());
                for(Item_pedidoDTO item : listItens){
                    Item_Nf item_nf = new Item_Nf();
                    item_nf.setItem_nf_key(new Item_Nf_Key());
                    item_nf.getItem_nf_key().setId_item(item.getItem_pedido_key().getItem());
                    item_nf.getItem_nf_key().setNf(business);
                    item_nf.setProduto(this.dtoToBusiness(item.getProduto()));
                    item_nf.setCofins(0d);
                    item_nf.setIcms(0d);
                    item_nf.setValor_unitario(this.precoService.findLastPriceById_produto(item.getProduto().getId_produto()).getValor_preco());
                    item_nf.setValor_total((this.precoService.findLastPriceById_produto(item.getProduto().getId_produto()).getValor_preco()*item.getQuantidade_produto()));
                    item_nf.setQuantidade_produto(item.getQuantidade_produto());
                    item_nf.setDesconto_produto(0d);
                    this.item_nfRepository.save(item_nf);
                }
//            Criando os Itens NF apartir dos itens pedido
//            Criando o Email de confirmação de Pedido
                EmailModel em = new EmailModel();
                em.setEmailFrom("projetodevbrew@gmail.com");
                em.setEmailTo(pedido.getCliente().getEmail());
                em.setSubject("Pedido "+pedido.getId());
                em.setText("É ótimo descobrir que os nossos clientes prezam pela qualidade e bom atendimento. \nObrigado por comprar com conosco.");
                em.setOwnerRef("projetodevbrew@gmail.com");
                this.emailService.sendEmail(em);
                System.out.println("-------------------------------------------------------------------------------------");
                System.out.println("-------------------------------------------------------------------------------------");
                System.out.println("\t\t\tEMAIL ENVIADO");
                System.out.println("-------------------------------------------------------------------------------------");
                System.out.println("-------------------------------------------------------------------------------------");
//            Criando o Email de confirmação de Pedido
                return dto;
            }else{
                Nf business = pedido.getNf();
//            Conversão de nf business to nf dto
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
                    dto.setCliente(clienteDTO);
                }
//            Conversão de nf business to nf dto
                return dto;
            }

        }
        return null;
    }
    public void canceleById(Long id){
        if(this.pedidoRepository.existsById(id)){
            Pedido pedido= this.pedidoRepository.getById(id);
            pedido.setSubtotal(0d);//setando subtotal pra 0
            pedido.setTotal(0d);//setando total pra 0
            pedido.setFinalizado(true);//setando finalizado para true para que não se possa mais inserir nenhum item a este pedido
            pedido.getStatusEntrega().setEstado_pedido("Cancelado");
            this.pedidoRepository.save(pedido);
            List<Item_pedidoDTO> ip= this.item_pedidoService.findByidpedido(id);
            for(Item_pedidoDTO item: ip){
                ProdutoDTO p = item.getProduto();
                int qtd = item.getQuantidade_produto();
                EstoqueDTO estoque = this.estoqueService.getById(p.getId_produto());
                estoque.setQuantidade((estoque.getQuantidade()+qtd));
                this.estoqueService.update(estoque,p.getId_produto());
            }

        }

    }

    public List<PedidoDTO> findAll(){
        return this.listToDTO(pedidoRepository.findAll());
    }
    public PedidoDTO findById(Long id){
        Optional<Pedido> op = pedidoRepository.findById(id);
        if(op.isPresent()){
            return businessToDTO(op.get());
        }
        return null;
    }

    public List<PedidoDTO> findByIdcliente(Long id){
        return this.listToDTO(this.pedidoRepository.findByIdCliente(id));
    }

    public List<PedidoDTO> listToDTO(List<Pedido> list){
        List<PedidoDTO> listdto = new ArrayList<>();
        for(Pedido p: list){
            listdto.add(this.businessToDTO(p));
        }
        return listdto;
    }



    private Produto dtoToBusiness (ProdutoDTO dto){
        Produto business = new Produto();
        business.setId_produto(dto.getId_produto());
        business.setNome_produto(dto.getNome_produto());
        business.setDescricao(dto.getDescricao());
        business.setIbu(dto.getIbu());
        business.setCor(dto.getCor());
        business.setTeor(dto.getTeor());
        business.setQuantidade_ml(dto.getQuantidade_ml());
        business.setEan(dto.getEan());
        business.setDestaque(dto.getDestaque());
        business.setDataDeCriacao(dto.getDataDeCriacao());
        business.setFoto(dto.getFoto());

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

    private Pedido dtoToBusiness(PedidoDTO dto){
        Pedido bus = new Pedido();

        bus.setTotal(dto.getTotal());
        bus.setSubtotal(dto.getSubtotal());
        bus.setDataDeCriacao(dto.getDataDeCriacao());
        bus.setFrete(dto.getFrete());
        bus.setFinalizado(dto.getFinalizado());
        if(dto.getCartao() != null){
            Cartao m = new Cartao();
            if(dto.getCartao().getId_Cartao() != null){
                m.setId_Cartao(dto.getId());
            }else{
                m.setNome(dto.getCartao().getNome());
                m.setNumero(dto.getCartao().getNumero());
                m.setValidade(dto.getCartao().getValidade());
            }
            bus.setCartao(m);
        }
        if(dto.getCliente() != null){
            Cliente m = new Cliente();
            if(dto.getCliente().getId_Cliente() != null){
                m.setId_Cliente(dto.getCliente().getId_Cliente());
            }else{
                m.setCpf(dto.getCliente().getCpf());
                m.setEmail(dto.getCliente().getEmail());
                m.setDataNascimento(dto.getCliente().getDataNascimento());
                m.setNome(dto.getCliente().getNome());
                m.setTelefone(dto.getCliente().getTelefone());
            }
            bus.setCliente(m);
        }
        if(dto.getEndereco() != null){
            Endereco m = new Endereco();
            if(dto.getEndereco().getId_endereco() != null){
                m.setId_endereco(dto.getEndereco().getId_endereco());
            }else{
                m.setComplemento(dto.getEndereco().getComplemento());
                m.setBairro(dto.getEndereco().getBairro());
                m.setCidade(dto.getEndereco().getCidade());
                m.setCep(dto.getEndereco().getCep());
                m.setRua(dto.getEndereco().getRua());
                m.setPonto_referencia(dto.getEndereco().getPonto_referencia());
                m.setEstado(dto.getEndereco().getEstado());
                m.setNumero(dto.getEndereco().getNumero());
                if(dto.getEndereco().getDestinatario()!=null){
                    m.setDestinatario(dto.getEndereco().getDestinatario());
                }else{
                    m.setDestinatario(dto.getCliente().getNome());
                }

            }
            bus.setEndereco(m);
        }
        if(dto.getPagamento() != null){
            Parcelamento m = new Parcelamento();
            if(dto.getPagamento().getId_parcelamento() != null){
                m.setId_parcelamento(dto.getPagamento().getId_parcelamento());
            }
            bus.setParcelamento(m);
        }
        if(dto.getMetodoPag() != null){
            MetodoPag m = new MetodoPag();
            if(dto.getMetodoPag().getId_metodoPag() != null){
                m.setId_metodoPag(dto.getMetodoPag().getId_metodoPag());
            }
            bus.setMetodoPag(m);
        }
        if(dto.getNf() != null){
            Nf m = new Nf();
            if(dto.getNf().getId_nf() != null){
                m.setId_nf(dto.getNf().getId_nf());
            }else{
                Cliente businessCliente = new Cliente();
                businessCliente.setNome(dto.getCliente().getNome());
                businessCliente.setCpf(dto.getCliente().getCpf());
                businessCliente.setDataNascimento(dto.getCliente().getDataNascimento());
                businessCliente.setEmail(dto.getCliente().getEmail());
                businessCliente.setTelefone(dto.getCliente().getTelefone());
                m.setCliente(businessCliente);
                m.setChave_acesso(dto.getNf().getChave_acesso());
                m.setSubtotal(dto.getNf().getSubtotal());
                m.setTotal(dto.getNf().getTotal());
                m.setEmissao(dto.getNf().getEmissao());
                m.setSerie(dto.getNf().getSerie());

            }
            bus.setNf(m);
        }
        if(dto.getStatus() != null){
            StatusPedido m = new StatusPedido();
            if(dto.getStatus().getId_status_pedido()!=null){
                m.setId_status_pedido(dto.getStatus().getId_status_pedido());
            }else{
                m.setEstado_pedido(dto.getStatus().getEstado_pedido());
            }
            bus.setStatusEntrega(m);
        }
        return bus;
    }


    private PedidoDTO businessToDTO(Pedido bus){
        PedidoDTO dto = new PedidoDTO();
        dto.setId(bus.getId());

        dto.setTotal(bus.getTotal());
        dto.setSubtotal(bus.getSubtotal());
        dto.setDataDeCriacao(bus.getDataDeCriacao());
        dto.setFrete(bus.getFrete());
        dto.setFinalizado(bus.getFinalizado());
        if(bus.getNf()!=null){
            dto.setNf(new NfDTO());
            dto.getNf().setCliente(new ClienteDTO());
            dto.getNf().setId_nf(bus.getNf().getId_nf());
            dto.getNf().getCliente().setId_Cliente(bus.getCliente().getId_Cliente());
            dto.getNf().getCliente().setTelefone(bus.getCliente().getTelefone());
            dto.getNf().getCliente().setEmail(bus.getCliente().getEmail());
            dto.getNf().getCliente().setNome(bus.getCliente().getNome());
            dto.getNf().getCliente().setCpf(bus.getCliente().getCpf());
            dto.getNf().getCliente().setDataNascimento(bus.getCliente().getDataNascimento());
            dto.getNf().setTotal(bus.getNf().getTotal());
            dto.getNf().setSubtotal(bus.getNf().getSubtotal());
            dto.getNf().setEmissao(bus.getNf().getEmissao());
            dto.getNf().setSerie(bus.getNf().getSerie());
            dto.getNf().setChave_acesso(bus.getNf().getChave_acesso());
        }
        if(bus.getCartao()!= null) {
            CartaoDTO cartao = new CartaoDTO();
            cartao.setId_Cartao(bus.getCartao().getId_Cartao());
            cartao.setNome(bus.getCartao().getNome());
            cartao.setNumero(bus.getCartao().getNumero());
            cartao.setValidade(bus.getCartao().getValidade());
            dto.setCartao(cartao);
        }
        if(bus.getCliente()!= null) {
            ClienteDTO cliente = new ClienteDTO();
            cliente.setId_Cliente(bus.getCliente().getId_Cliente());
            cliente.setNome(bus.getCliente().getNome());
            cliente.setCpf(bus.getCliente().getCpf());
            cliente.setEmail(bus.getCliente().getEmail());
            cliente.setTelefone(bus.getCliente().getTelefone());
            cliente.setDataNascimento(bus.getCliente().getDataNascimento());
            dto.setCliente(cliente);
        }
        if(bus.getEndereco()!= null) {
            EnderecoDTO endereco = new EnderecoDTO();
            endereco.setId_endereco(bus.getEndereco().getId_endereco());
            endereco.setRua(bus.getEndereco().getRua());
            endereco.setPonto_referencia(bus.getEndereco().getPonto_referencia());
            endereco.setEstado(bus.getEndereco().getEstado());
            endereco.setComplemento(bus.getEndereco().getComplemento());
            endereco.setCidade(bus.getEndereco().getCidade());
            endereco.setNumero(bus.getEndereco().getNumero());
            endereco.setCep(bus.getEndereco().getCep());
            endereco.setBairro(bus.getEndereco().getBairro());
            endereco.setDestinatario(bus.getEndereco().getDestinatario());
            dto.setEndereco(endereco);
        }
        if(bus.getParcelamento() != null) {
            ParcelamentoDTO formaPagamento = new ParcelamentoDTO();
            formaPagamento.setId_parcelamento(bus.getParcelamento().getId_parcelamento());
            formaPagamento.setParcelamento(bus.getParcelamento().getParcelamento());
            formaPagamento.setQtdParcelas(bus.getParcelamento().getQtdParcelas());
            dto.setPagamento(formaPagamento);
        }
        if(bus.getMetodoPag() != null) {
            MetodoPagDTO metodoPag = new MetodoPagDTO();
            metodoPag.setId_metodoPag(bus.getMetodoPag().getId_metodoPag());
            metodoPag.setMetodoPag(bus.getMetodoPag().getMetodoPag());
            dto.setMetodoPag(metodoPag);
        }
        if(bus.getStatusEntrega()!= null) {
            StatusPedidoDTO statusPedido = new StatusPedidoDTO();
            statusPedido.setId_status_pedido(bus.getStatusEntrega().getId_status_pedido());
            statusPedido.setEstado_pedido(bus.getStatusEntrega().getEstado_pedido());
            dto.setStatus(statusPedido);
        }
        return dto;
    }

}
