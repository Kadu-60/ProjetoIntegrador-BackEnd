package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.model.dto.StatusPedidoDTO;
import br.com.rd.ProjetoIntegrador.model.entity.StatusPedido;
import br.com.rd.ProjetoIntegrador.repository.StatusPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatusPedidoService {

    @Autowired
    StatusPedidoRepository statusPedidoRepository;

    public StatusPedidoDTO addStatus(StatusPedidoDTO status){
        StatusPedido novoStatus = this.dtoToBusiness(status);
        novoStatus = statusPedidoRepository.save(novoStatus);
        return this.businessToDTO(novoStatus);
    }

    public List<StatusPedidoDTO> listarTodos(List<StatusPedido> lista){
        List<StatusPedidoDTO> listaDTO = new ArrayList<>();

        for (StatusPedido status: lista){
            StatusPedidoDTO novoStatus = this.businessToDTO(status);
            listaDTO.add(novoStatus);
        }

        return listaDTO;
    }

    public List<StatusPedidoDTO> listaDTO(){
        List<StatusPedido> listaStatus = statusPedidoRepository.findAll();
        return this.listarTodos(listaStatus);
    }

    public StatusPedidoDTO buscarPorId(Long id){
        Optional<StatusPedido> op = statusPedidoRepository.findById(id);
        if (op.isPresent()){
            return this.businessToDTO(op.get());
        }
        return null;
    }

    public StatusPedidoDTO atualizarPorId(StatusPedidoDTO dto, long id){
        Optional<StatusPedido> op = statusPedidoRepository.findById(id);
        if (op.isPresent()){
            StatusPedido statusPedido = op.get();
            if (dto.getEstado_pedido() != null){
                statusPedido.setEstado_pedido(dto.getEstado_pedido());
            }
            statusPedidoRepository.save(statusPedido);
            return this.businessToDTO(statusPedido);
        }
        return null;
    }

    public void deletarPorId(Long id){
        statusPedidoRepository.deleteById(id);
    }

    private StatusPedido dtoToBusiness(StatusPedidoDTO dto){
        StatusPedido business = new StatusPedido();
        business.setEstado_pedido(dto.getEstado_pedido());

        return business;
    }

    private StatusPedidoDTO businessToDTO(StatusPedido business){
        StatusPedidoDTO dto = new StatusPedidoDTO();
        dto.setId_status_pedido(business.getId_status_pedido());
        dto.setEstado_pedido(business.getEstado_pedido());

        return dto;
    }
}
