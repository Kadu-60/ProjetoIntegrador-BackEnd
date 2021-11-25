package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.model.dto.FormularioContatoDTO;
import br.com.rd.ProjetoIntegrador.model.entity.EmailModel;
import br.com.rd.ProjetoIntegrador.model.entity.FormularioContato;
import br.com.rd.ProjetoIntegrador.repository.FormularioContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FormularioContatoService {

    @Autowired
    FormularioContatoRepository formularioContatoRepository;
    @Autowired
    EmailService emailService;

    public FormularioContatoDTO createFormularioContato(FormularioContatoDTO formulariocontato) {
        FormularioContato newFormularioContato = this.dtoToBusiness(formulariocontato);
        newFormularioContato = formularioContatoRepository.save(newFormularioContato);
//            Criando email do formulário contato
        EmailModel em = new EmailModel();
        em.setEmailFrom("projetodevbrew@gmail.com");
        em.setEmailTo("projetodevbrew@gmail.com");
        em.setSubject("Contato - "+newFormularioContato.getTipo_de_contato()+" - "+newFormularioContato.getAssunto());
        em.setText("Mensagem enviada: \n "+newFormularioContato.getMensagem()
                + " \nNome do contatante:"+newFormularioContato.getNome()
                + " \nNumero de contato: "+newFormularioContato.getTelefone()
                + " \nEmail para contato:"+newFormularioContato.getEmail());
        em.setOwnerRef("projetodevbrew@gmail.com");
        this.emailService.sendEmail(em);
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("\t\t\tEMAIL ENVIADO");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
//            Criando email do formulário contato
//            Criando email do formulário contato
         em = new EmailModel();
        em.setEmailFrom("projetodevbrew@gmail.com");
        em.setEmailTo(newFormularioContato.getEmail());
        em.setSubject("Contato - Agradecimento de contato");
        em.setText("Muito Obrigado "+newFormularioContato.getNome()+", \nPelo seu contato, Assim que possivel " +
                "iremos te responder");
        em.setOwnerRef("projetodevbrew@gmail.com");
        this.emailService.sendEmail(em);
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("\t\t\tEMAIL ENVIADO");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
//            Criando email do formulário contato
        return this.businessToDto(newFormularioContato);
    }

    public List<FormularioContatoDTO> findAllFormularioContato() {
        List<FormularioContato> allList = formularioContatoRepository.findAll();
        return this.listToDto(allList);
    }

    private List<FormularioContatoDTO> listToDto(List<FormularioContato> list) {
        List<FormularioContatoDTO> listDto = new ArrayList<FormularioContatoDTO>();
        for (FormularioContato f : list) {
            listDto.add(this.businessToDto(f));
        }
        return listDto;
    }

    public FormularioContatoDTO updateById(FormularioContatoDTO dto, Long id) {
        Optional<FormularioContato> op = formularioContatoRepository.findById(id);

        if (op.isPresent()) {
            FormularioContato obj = op.get();

            if (dto.getNome() != null) {
                obj.setNome(dto.getNome());
            }
            if (dto.getTelefone() != null) {
                obj.setTelefone(dto.getTelefone());
            }
            if (dto.getEmail() != null) {
                obj.setEmail(dto.getEmail());
            }
            if (dto.getTipo_de_contato() != null) {
                obj.setTipo_de_contato(dto.getTipo_de_contato());
            }
            if (dto.getAssunto() != null) {
                obj.setAssunto(dto.getAssunto());
            }
            if (dto.getMensagem() != null) {
                obj.setMensagem(dto.getMensagem());
            }

            formularioContatoRepository.save(obj);
            return businessToDto(obj);

        }
        return null;
    }

    public void deleteById(Long id) {
        if (formularioContatoRepository.existsById(id)) {
            formularioContatoRepository.deleteById(id);
        }
    }


    public FormularioContato dtoToBusiness(FormularioContatoDTO dto) {
        FormularioContato business = new FormularioContato();
        business.setNome(dto.getNome());
        business.setTelefone(dto.getTelefone());
        business.setEmail(dto.getEmail());
        business.setTipo_de_contato(dto.getTipo_de_contato());
        business.setAssunto(dto.getAssunto());
        business.setMensagem(dto.getMensagem());
        return business;
    }

    public FormularioContatoDTO businessToDto(FormularioContato business) {
        FormularioContatoDTO dto = new FormularioContatoDTO();
        dto.setId(business.getId());
        dto.setNome(business.getNome());
        dto.setTelefone(business.getTelefone());
        dto.setEmail(business.getEmail());
        dto.setTipo_de_contato(business.getTipo_de_contato());
        dto.setAssunto(business.getAssunto());
        dto.setMensagem(business.getMensagem());
        return dto;
    }

    public FormularioContatoDTO searchById (Long id) {
        Optional<FormularioContato> business = formularioContatoRepository.findById(id);

        if(business.isPresent()){
            return businessToDto(business.get());
        }
        return null;
    }
}
