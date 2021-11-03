package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.dto.ParcelamentoDTO;
import br.com.rd.ProjetoIntegrador.service.ParcelamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parcelas")
public class ParcelamentoController {

    @Autowired
    ParcelamentoService parcelamentoService;

    @PostMapping
    public ParcelamentoDTO criarParcelamento (@RequestBody ParcelamentoDTO parcelamento) {
        return parcelamentoService.criarParcelamento(parcelamento);
    }

    @GetMapping
    public List<ParcelamentoDTO> findAllParcelas() {
        return parcelamentoService.findAllParcelas();
    }

    @DeleteMapping("/{id_Parcelamento}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluirParcelamento (@PathVariable("id_Parcelamento") Long id_Parcelamento) {
        parcelamentoService.excluirParcelamento(id_Parcelamento);
    }

    @PutMapping("/{id_Parcelamento}")
    public ParcelamentoDTO atualizarParcelasPorId (@RequestBody ParcelamentoDTO parcelamentoDTO, @PathVariable ("id_Parcelamento") Long id_Parcelamento){
        return  parcelamentoService.atualizarParcelasPorId(parcelamentoDTO, id_Parcelamento);
    }

}