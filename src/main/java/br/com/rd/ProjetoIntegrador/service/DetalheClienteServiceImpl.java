package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.data.DetalheClienteData;
import br.com.rd.ProjetoIntegrador.model.entity.Cliente;
import br.com.rd.ProjetoIntegrador.repository.ClienteRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DetalheClienteServiceImpl implements UserDetailsService {
    private final ClienteRepository repository;

    public DetalheClienteServiceImpl(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Cliente> op = repository.findByEmail(s);
        if (op.isEmpty()){
            throw new UsernameNotFoundException("Usuario '" + s + "' não encontrado");
        }
        return new DetalheClienteData(op);
    }
}
