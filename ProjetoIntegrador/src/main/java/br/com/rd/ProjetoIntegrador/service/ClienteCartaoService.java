package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.ClienteCartaoKey;
import br.com.rd.ProjetoIntegrador.model.dto.CartaoDTO;
import br.com.rd.ProjetoIntegrador.model.dto.ClienteCartaoDTO;
import br.com.rd.ProjetoIntegrador.model.dto.ClienteCartaoKeyDTO;
import br.com.rd.ProjetoIntegrador.model.dto.ClienteDTO;
import br.com.rd.ProjetoIntegrador.model.entity.Cartao;
import br.com.rd.ProjetoIntegrador.model.entity.Cliente;
import br.com.rd.ProjetoIntegrador.model.entity.ClienteCartao;
import br.com.rd.ProjetoIntegrador.repository.CartaoRepository;
import br.com.rd.ProjetoIntegrador.repository.ClienteCartaoRepository;
import br.com.rd.ProjetoIntegrador.repository.ClienteRepository;
import ch.qos.logback.core.joran.util.beans.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteCartaoService {
    @Autowired
    ClienteCartaoRepository clienteCartaoRepository;
    @Autowired
    CartaoRepository cartaoRepository;
    @Autowired
    ClienteRepository clienteRepository;


    public ClienteCartaoDTO create(ClienteCartaoDTO dto){
        ClienteCartao bus = this.dtoToBus(dto);

        if(bus.getClienteCartaoKey()!=null){
            if(bus.getClienteCartaoKey().getCliente()==null){
                return null;
            }
            if(bus.getClienteCartaoKey().getCartao()!=null){
                if(bus.getClienteCartaoKey().getCartao().getId_Cartao()!=null){
                    bus.getClienteCartaoKey().setCartao(this.cartaoRepository.findById(bus.getClienteCartaoKey().getCartao().getId_Cartao()).get());
                }else{
                    bus.getClienteCartaoKey().setCartao(this.cartaoRepository.save(bus.getClienteCartaoKey().getCartao()));
                }
            }
        }
        bus =this.clienteCartaoRepository.save(bus);
        if(bus.getPrincipal()){
            this.tornarPrincipal(bus.getClienteCartaoKey().getCliente().getId_Cliente(),bus.getClienteCartaoKey().getCartao().getId_Cartao());
        }
        return this.busToDTO(bus);
    }

    public List<ClienteCartaoDTO> findById_Cliente(Long id_cliente){
        List<ClienteCartao> bus = this.clienteCartaoRepository.findAllByIdCliente(id_cliente);
        return this.listToDTO(bus);
    }

    public List<ClienteCartaoDTO> tornarPrincipal(Long id_cliente,Long id_cartao){
        ClienteCartaoKey cck = new ClienteCartaoKey();
        cck.setCartao(new Cartao());
        cck.getCartao().setId_Cartao(id_cartao);
        cck.setCliente(new Cliente());
        cck.getCliente().setId_Cliente(id_cliente);
        if(clienteCartaoRepository.existsById(cck)){
            List<ClienteCartao> bus = this.clienteCartaoRepository.findAllByIdCliente(id_cliente);

            for(ClienteCartao item: bus){
                if(item.getPrincipal()){
                    item.setPrincipal(false);
                }
                if(item.getClienteCartaoKey().getCartao().getId_Cartao()==id_cartao && item.getClienteCartaoKey().getCliente().getId_Cliente()==id_cliente){
                    item.setPrincipal(true);
                }
                this.clienteCartaoRepository.save(item);
            }
            return this.listToDTO(bus);
        }
        return null;
    }

    public void delete(Long id_cliente, Long id_cartao){
        Cliente cliente = new Cliente();
        Cartao cartao = new Cartao();
        if(clienteRepository.existsById(id_cliente)&& cartaoRepository.existsById(id_cartao)){
            cliente = clienteRepository.findById(id_cliente).get();
            cartao = cartaoRepository.findById(id_cartao).get();
            ClienteCartaoKey cck = new ClienteCartaoKey();
            cck.setCliente(cliente);
            cck.setCartao(cartao);
            if(this.clienteCartaoRepository.existsById(cck)){
                boolean check = this.clienteCartaoRepository.findById(cck).get().getPrincipal();
                this.clienteCartaoRepository.deleteById(cck);
                if(check){
                    List<ClienteCartao> list = this.clienteCartaoRepository.findAllByIdCliente(id_cliente);

                    for(ClienteCartao item: list){
                        if(check){
                            item.setPrincipal(true);
                            this.clienteCartaoRepository.save(item);
                            check = false;
                        }
                    }
                }


            }

        }

    }



    private List<ClienteCartaoDTO> listToDTO(List<ClienteCartao> bus){
        List<ClienteCartaoDTO> dto = new ArrayList<>();

        for(ClienteCartao item:bus){

            dto.add(this.busToDTO(item));
        }
        return dto;
    }

    private ClienteCartao dtoToBus(ClienteCartaoDTO dto){
        ClienteCartao bus = new ClienteCartao();
        bus.setPrincipal(dto.getPrincipal());
        bus.setClienteCartaoKey(new ClienteCartaoKey());
        bus.getClienteCartaoKey().setCliente(new Cliente());
        bus.getClienteCartaoKey().setCartao(new Cartao());
        if(dto.getClienteCartaoKey()!=null){
            if(dto.getClienteCartaoKey().getCliente()!=null){
                BeanUtils.copyProperties(dto.getClienteCartaoKey().getCliente(),bus.getClienteCartaoKey().getCliente());
            }
            if(dto.getClienteCartaoKey().getCartao()!=null){
                BeanUtils.copyProperties( dto.getClienteCartaoKey().getCartao(), bus.getClienteCartaoKey().getCartao());
            }
        }
        return bus;
    }

    private ClienteCartaoDTO busToDTO(ClienteCartao bus){
        ClienteCartaoDTO dto = new ClienteCartaoDTO();
        dto.setPrincipal(bus.getPrincipal());
        dto.setClienteCartaoKey(new ClienteCartaoKeyDTO());
        dto.getClienteCartaoKey().setCliente(new ClienteDTO());
        dto.getClienteCartaoKey().setCartao(new CartaoDTO());
        if(dto.getClienteCartaoKey()!=null){
            if(dto.getClienteCartaoKey().getCliente()!=null){
                BeanUtils.copyProperties(bus.getClienteCartaoKey().getCliente(), dto.getClienteCartaoKey().getCliente());
            }
            if(dto.getClienteCartaoKey().getCartao()!=null){
                BeanUtils.copyProperties(bus.getClienteCartaoKey().getCartao(), dto.getClienteCartaoKey().getCartao());
            }
        }
        return dto;
    }


}
