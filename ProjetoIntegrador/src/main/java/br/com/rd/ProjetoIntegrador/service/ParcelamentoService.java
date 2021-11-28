package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.model.dto.ParcelamentoDTO;
import br.com.rd.ProjetoIntegrador.model.entity.Parcelamento;
import br.com.rd.ProjetoIntegrador.repository.ParcelamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParcelamentoService {

    @Autowired
    ParcelamentoRepository parcelamentoRepository;

    private ParcelamentoDTO parcelamentoToDto (Parcelamento businessParcelas) {
        ParcelamentoDTO dtoParcelas = new ParcelamentoDTO();
        dtoParcelas.setId_parcelamento(businessParcelas.getId_parcelamento());
        dtoParcelas.setParcelamento(businessParcelas.getParcelamento());
        dtoParcelas.setQtdParcelas(businessParcelas.getQtdParcelas());
        return dtoParcelas;
    }

    private Parcelamento dtoToParcelamento (ParcelamentoDTO dtoParcelas) {
        Parcelamento businessParcelas = new Parcelamento();
        businessParcelas.setId_parcelamento(dtoParcelas.getId_parcelamento());
        businessParcelas.setParcelamento(dtoParcelas.getParcelamento());
        businessParcelas.setQtdParcelas(dtoParcelas.getQtdParcelas());
        return businessParcelas;
    }

    public ParcelamentoDTO criarParcelamento (ParcelamentoDTO parcelamento) {
        Parcelamento novoParcelamento = this.dtoToParcelamento(parcelamento);
        novoParcelamento = parcelamentoRepository.save(novoParcelamento);
        return this.parcelamentoToDto(novoParcelamento);
    }

    public void excluirParcelamento (Long id_Parcelamento) {
        if (parcelamentoRepository.existsById(id_Parcelamento)) {
            parcelamentoRepository.deleteById(id_Parcelamento);
        }
    }
    public List<ParcelamentoDTO> findAllParcelas(){
        List<Parcelamento> allParcelamento = parcelamentoRepository.findAll();
        return this.listToParcelamentoDto(allParcelamento);
    }

    private List<ParcelamentoDTO> listToParcelamentoDto(List<Parcelamento> listaParcelas) {
        List<ParcelamentoDTO> listParcelamentoDto = new ArrayList<ParcelamentoDTO>();
        for (Parcelamento pc : listaParcelas) {
            listParcelamentoDto.add(this.parcelamentoToDto(pc));
        }
        return listParcelamentoDto;
    }

    public ParcelamentoDTO atualizarParcelasPorId (ParcelamentoDTO parcelamentoDTO, Long id_Parcelamento) {
        Optional<Parcelamento> op = parcelamentoRepository.findById(id_Parcelamento);

        if (op.isPresent()) {
            Parcelamento pc = op.get();
            if (parcelamentoDTO != null) {
                pc.setParcelamento(parcelamentoDTO.getParcelamento());
            }
            parcelamentoRepository.save(pc);
            return parcelamentoToDto(pc);
        }
        return null;
    }

}
