package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.entity.Cliente;
import br.com.rd.ProjetoIntegrador.repository.ClienteRepository;
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
        cliente.setPassword(encoder.encode(cliente.getPassword()));
        return ResponseEntity.ok(repository.save(cliente));
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
}
