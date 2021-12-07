package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.entity.Cliente;
import br.com.rd.ProjetoIntegrador.model.entity.EmailModel;
import br.com.rd.ProjetoIntegrador.repository.ClienteRepository;
import br.com.rd.ProjetoIntegrador.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cadastroCliente")
public class LoginController {
    private final ClienteRepository repository;
    private final PasswordEncoder encoder;
    @Autowired
    EmailService emailService;

    public LoginController(ClienteRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<Cliente>> listarTodos(){
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping("/salvar")
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) {
//            Criando o Email de criacao de conta
        EmailModel em = new EmailModel();
        em.setEmailFrom("projetodevbrew@gmail.com");
        em.setEmailTo(cliente.getEmail());
        em.setSubject("Criação de conta");
        em.setText("Muito obrigado por criar sua conta com a gente");
        em.setOwnerRef("projetodevbrew@gmail.com");
        this.emailService.sendEmail(em);
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("\t\t\tEMAIL ENVIADO");
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------------");
//            Criando o Email de criacao de conta
        cliente.setPassword(encoder.encode(cliente.getPassword()));
        return ResponseEntity.ok(repository.save(cliente));
    }
    @PutMapping("/alterarSenha")
    public ResponseEntity<Cliente> alterarSenha(@RequestBody  Cliente cliente){

        if(repository.existsById(cliente.getId_Cliente()) && cliente.getPassword()!=null ){
            Cliente c2 = repository.getById(cliente.getId_Cliente());
            c2.setPassword(encoder.encode(cliente.getPassword()));
            return ResponseEntity.ok(repository.save(c2));
        }

        return null;
    }

    @GetMapping("/validarSenha")
    public ResponseEntity<Boolean> validarSenha(@RequestParam String email,
                                                @RequestParam String password) {

        Optional<Cliente> op = repository.findByEmail(email);
        if (op.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        Cliente usuario = op.get();
        boolean valid = encoder.matches(password, usuario.getPassword());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);
    }
    @PutMapping("/alterarSenha/{id}")
    public ResponseEntity<Cliente> alterarSenha(@RequestBody  Cliente cliente, @PathVariable("id") Long id){

        if(repository.existsById(id) && cliente.getPassword()!=null ){
            Cliente c2 = repository.getById(id);
            c2.setPassword(encoder.encode(cliente.getPassword()));
            return ResponseEntity.ok(repository.save(c2));
        }

        return null;
    }

    @GetMapping("/validarToken")
    public void validartoken(){

    }
}
