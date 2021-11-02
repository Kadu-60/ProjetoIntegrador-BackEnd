package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.model.dto.CartaoDTO;
import br.com.rd.ProjetoIntegrador.model.entity.Cartao;
import br.com.rd.ProjetoIntegrador.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartaoService {
    @Autowired
    CartaoRepository cartaoRepository;

    public CartaoDTO adicionarCartao(CartaoDTO cartao){
//
        Cartao novoCartao = this.dtoToBusiness(cartao);
        novoCartao = cartaoRepository.save(novoCartao);
        return this.businessToDTO(novoCartao);
    }

    public List<CartaoDTO> listarTodos(List<Cartao> lista){
        List<CartaoDTO> listaDTO = new ArrayList<>();
        for (Cartao cartao: lista) {
            CartaoDTO novoCartao = this.businessToDTO(cartao);
            listaDTO.add(novoCartao);
        }

        return listaDTO;
    }

    public List<CartaoDTO> listaDTO(){
        List<Cartao> listaCartao = cartaoRepository.findAll();
        return this.listarTodos(listaCartao);
    }

    public CartaoDTO buscaPorId(Long id){
        Optional<Cartao> op = cartaoRepository.findById(id);
        if(op.isPresent()){
            return this.businessToDTO(op.get());
        }
        return null;
    }

    public CartaoDTO atualizarPorId(CartaoDTO dto, Long id){
        Optional<Cartao> op = cartaoRepository.findById(id);
        if(op.isPresent()){
            Cartao cartao = op.get();
            if(dto.getNome() != null){
                cartao.setNome(dto.getNome());
            }
            if(dto.getNumero() != null){
                cartao.setNumero(dto.getNumero());
            }
            if(dto.getValidade() != null){
                cartao.setValidade(dto.getValidade());
            }
            cartaoRepository.save(cartao);
            return this.businessToDTO(cartao);
        }
        return null;
    }

    public void deletarPorId(Long id){
        cartaoRepository.deleteById(id);
    }

    private CartaoDTO businessToDTO (Cartao business){
        CartaoDTO dto = new CartaoDTO();
        dto.setId_Cartao(business.getId_Cartao());
        dto.setNome(business.getNome());
        dto.setNumero(business.getNumero());
        dto.setValidade(business.getValidade());

        return dto;
    }

    private Cartao dtoToBusiness(CartaoDTO dto){
        Cartao business = new Cartao();
        business.setNumero(dto.getNumero());
        business.setNome(dto.getNome());
        business.setValidade(dto.getValidade());

        return business;
    }
}
