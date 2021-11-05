package br.com.rd.ProjetoIntegrador.service;

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
        if(pedido.getStatusEntrega()!=null){
            Long id = pedido.getStatusEntrega().getId_status_pedido();
            if(id!=null){
                StatusPedido m = this.statusPedidoRepository.getById(id);
                pedido.setStatusEntrega(m);
            }
        }
        pedido.setDataDeCriacao(new Date());
        pedido = this.pedidoRepository.save(pedido);
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

            Pedido pedido = pedidoRepository.getById(pedidoDTO.getId());
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
    public void deleteById(Long id){
        if(pedidoRepository.existsById(id)){
            pedidoRepository.deleteById(id);
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
            dto.setEndereco(endereco);
        }
        if(bus.getParcelamento() != null) {
            ParcelamentoDTO formaPagamento = new ParcelamentoDTO();
            formaPagamento.setId_parcelamento(bus.getParcelamento().getId_parcelamento());
            formaPagamento.setParcelamento(bus.getParcelamento().getParcelamento());
            dto.setPagamento(formaPagamento);
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
