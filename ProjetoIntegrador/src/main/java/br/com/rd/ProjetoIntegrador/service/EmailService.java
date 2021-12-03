package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.model.entity.EmailModel;
import br.com.rd.ProjetoIntegrador.model.enums.StatusEmail;
import br.com.rd.ProjetoIntegrador.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        try{
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);


            String mailContent ="<h2>"+emailModel.getText()+"</h2>"+"<hr><img src ='cid:logoImage' style='margin: 0 auto'/> <hr>\n";

            helper.setFrom(emailModel.getEmailFrom());
            helper.setTo(emailModel.getEmailTo());
            helper.setSubject(emailModel.getSubject());
            helper.setText(mailContent, true);

            ClassPathResource resource = new ClassPathResource("/static/images/footer.png");
            helper.addInline("logoImage", resource);

            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e){
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {
            return emailRepository.save(emailModel);
        }
    }

    public Page<EmailModel> findAll(Pageable pageable) {
        return  emailRepository.findAll(pageable);
    }

    public Optional<EmailModel> findById(UUID emailId) {
        return emailRepository.findById(emailId);
    }
}

